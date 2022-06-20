package cn.tedu.csmall.product.webapi.service;

import cn.tedu.csmall.common.ex.ServiceException;
import cn.tedu.csmall.common.web.State;
import cn.tedu.csmall.pojo.dto.CategoryAddNewDTO;
import cn.tedu.csmall.pojo.entity.Category;
import cn.tedu.csmall.pojo.vo.CategoryDetailsVO;
import cn.tedu.csmall.pojo.vo.CategorySimpleListItemVO;
import cn.tedu.csmall.pojo.vo.CategorySimpleVO;
import cn.tedu.csmall.product.service.ICategoryService;
import cn.tedu.csmall.product.webapi.mapper.CategoryMapper;
import cn.tedu.csmall.product.webapi.repository.ICategoryRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ICategoryRedisRepository categoryRedisRepository;

    @Override
    public void addNew(CategoryAddNewDTO categoryAddNewDTO) {
        String name = categoryAddNewDTO.getName();
        CategorySimpleVO queryResult = categoryMapper.getByName(name);
        if (queryResult != null) {
            throw new ServiceException(State.ERR_CATEGORY_NAME_DUPLICATE, "添加类别失败，名称（" + name + "）已存在！");
        }

        Long parentId = categoryAddNewDTO.getParentId();
        Integer depth = 1;
        CategorySimpleVO parentCategory = null;
        if (parentId != 0) {
            parentCategory = categoryMapper.getById(parentId);
            if (parentCategory == null) {
                throw new ServiceException(State.ERR_CATEGORY_NOT_FOUND, "添加类别失败，父级类别不存在！");
            }
            depth = parentCategory.getDepth() + 1;
        }

        Category category = new Category();
        BeanUtils.copyProperties(categoryAddNewDTO, category);
        category.setDepth(depth);
        category.setEnable(1);
        category.setIsParent(0);
        LocalDateTime now = LocalDateTime.now();
        category.setGmtCreate(now);
        category.setGmtModified(now);

        int row = categoryMapper.insert(category);
        if (row != 1) {
            throw new ServiceException(State.ERR_INSERT, "添加类别失败，服务器忙（" + State.ERR_INSERT.getValue() + "），请稍后再次尝试！");
        }

        if (parentId != 0 && parentCategory != null && parentCategory.getIsParent() == 0) {
            row = categoryMapper.updateIsParentById(parentId, 1);
            if (row != 1) {
                throw new ServiceException(State.ERR_UPDATE, "添加类别失败，服务器忙（" + State.ERR_UPDATE.getValue() + "），请稍后再次尝试！");
            }
        }

    }

    @Override
    public List<CategorySimpleListItemVO> listByParentId(Long parentId) {
        return categoryMapper.listByParentId(parentId);
    }

    @Override
    public CategoryDetailsVO getDetailsById(Long id) {
        // ===== 以下是原有代码，只从数据库中获取数据 =====
        // CategoryDetailsVO category = categoryMapper.getDetailsById(id);
        // if (category == null) {
        //     throw new ServiceException(State.ERR_CATEGORY_NOT_FOUND,
        //             "获取类别详情失败，尝试访问的数据不存在！");
        // }
        // return category;

        // ===== 以下是新的业务，将从Redis中获取数据 =====
        log.debug("根据id（{}）获取类别详情……", id);
        // 从repository中调用方法，根据id获取缓存的数据
        // 判断缓存中是否存在与此id对应的key
        boolean exists = categoryRedisRepository.exists(id);
        if (exists) {
            // 有：表示明确的存入过某数据，此数据可能是有效数据，也可能是null
            // -- 判断此key对应的数据是否为null
            CategoryDetailsVO cacheResult = categoryRedisRepository.getDetailsById(id);
            if (cacheResult == null) {
                // -- 是：表示明确的存入了null值，则此id对应的数据确实不存在，则抛出异常
                log.warn("在缓存中存在此id（）对应的Key，却是null值，则抛出异常", id);
                throw new ServiceException(State.ERR_CATEGORY_NOT_FOUND,
                        "获取类别详情失败，尝试访问的数据不存在！");
            } else {
                // -- 否：表示明确的存入了有效数据，则返回此数据即可
                return cacheResult;
            }
        }

        // 缓存中没有此id匹配的数据
        // 从mapper中调用方法，根据id获取数据库的数据
        log.debug("没有命中缓存，则从数据库查询数据……");
        CategoryDetailsVO dbResult = categoryMapper.getDetailsById(id);
        // 判断从数据库中获取的结果是否为null
        if (dbResult == null) {
            // 是：数据库也没有此数据，先向缓存中写入错误数据，再抛出异常
            log.warn("数据库中也无此数据（id={}），先向缓存中写入错误数据", id);
            categoryRedisRepository.saveEmptyValue(id);
            log.warn("抛出异常");
            throw new ServiceException(State.ERR_CATEGORY_NOT_FOUND,
                    "获取类别详情失败，尝试访问的数据不存在！");
        }

        // 将从数据库中查询到的结果存入到缓存中
        log.debug("已经从数据库查询到匹配的数据，将数据存入缓存……");
        categoryRedisRepository.save(dbResult);
        // 返回查询结果
        log.debug("返回查询到数据：{}", dbResult);
        return dbResult;
    }

    @Override
    public void preloadCache() {
        log.debug("删除缓存中的类别列表……");
        categoryRedisRepository.deleteList();
        log.debug("删除缓存中的各独立的类别数据……");
        categoryRedisRepository.deleteAllItem();

        log.debug("从数据库查询类别列表……");
        List<CategoryDetailsVO> list = categoryMapper.list();

        for (CategoryDetailsVO category : list) {
            log.debug("查询结果：{}", category);
            log.debug("将当前类别存入到Redis：{}", category);
            categoryRedisRepository.save(category);
        }

        log.debug("将类别列表写入到Redis……");
        categoryRedisRepository.save(list);
        log.debug("将类别列表写入到Redis完成！");
    }

    /**
     * 6.26作业
     **/
    @Override
    public void disableById(Long id) {
        CategoryDetailsVO category = categoryMapper.getDetailsById(id);
        if (category == null) {
            throw new ServiceException(State.ERR_CATEGORY_NOT_FOUND, "获取类别详情失败，尝试访问的数据不存在！");

        }
        if (category.getEnable() == 0) {
            throw new ServiceException(State.ERR_CATEGORY_IS_DISABLE, "已禁用，客户端再禁用");
        }
        int row = categoryMapper.disableById(category.getId());
        if (row != 1) {
            throw new ServiceException(State.ERR_UPDATE, "更新类别失败，服务器忙（" + State.ERR_UPDATE.getValue() + "），请稍后再次尝试！");
        }

    }

    @Override
    public void enableById(Long id) {
        CategoryDetailsVO category = categoryMapper.getDetailsById(id);
        if (category == null) {
            throw new ServiceException(State.ERR_CATEGORY_NOT_FOUND, "获取类别详情失败，尝试访问的数据不存在！");

        }
        if (category.getEnable() == 1) {
            throw new ServiceException(State.ERR_CATEGORY_IS_ENABLE, "已启用，客户端再启用");
        }
        int row = categoryMapper.enableById(category.getId());
        if (row != 1) {
            throw new ServiceException(State.ERR_UPDATE, "更新类别失败，服务器忙（" + State.ERR_UPDATE.getValue() + "），请稍后再次尝试！");
        }

    }

    @Override
    public void notDisplayById(Long id) {
        CategoryDetailsVO category = categoryMapper.getDetailsById(id);
        if (category == null) {
            throw new ServiceException(State.ERR_CATEGORY_NOT_FOUND, "获取类别详情失败，尝试访问的数据不存在！");

        }
        if (category.getIsDisplay() == 0) {
            throw new ServiceException(State.ERR_CATEGORY_IS_NOT_DISPLAY, "已隐藏，客户端再隐藏");
        }
        int row = categoryMapper.notDisplayById(category.getId());
        if (row != 1) {
            throw new ServiceException(State.ERR_UPDATE, "更新类别失败，服务器忙（" + State.ERR_UPDATE.getValue() + "），请稍后再次尝试！");
        }
    }

    @Override
    public void isDisplayById(Long id) {
        CategoryDetailsVO category = categoryMapper.getDetailsById(id);
        if (category == null) {
            throw new ServiceException(State.ERR_CATEGORY_NOT_FOUND, "获取类别详情失败，尝试访问的数据不存在！");

        }
        if (category.getIsDisplay() == 1) {
            throw new ServiceException(State.ERR_CATEGORY_IS_IS_DISPLAY, "已显示，客户端再显示");
        }
        int row = categoryMapper.isDisplayById(category.getId());
        if (row != 1) {
            throw new ServiceException(State.ERR_UPDATE, "更新类别失败，服务器忙（" + State.ERR_UPDATE.getValue() + "），请稍后再次尝试！");
        }
    }

    @Override
    public void deleteById(Long id) {
        CategorySimpleVO category = categoryMapper.getById(id);
        if (category == null) {
            throw new ServiceException(State.ERR_CATEGORY_NOT_FOUND, "获取类别详情失败，尝试访问的数据不存在！");
        }
        if (category.getIsParent() == 1) {
            throw new ServiceException(State.ERR_DELETE, "删除类别失败，该类是父类，请先删除子类！");
        }
        if (category.getParentId() != 0) {
            List<CategorySimpleListItemVO> list = categoryMapper.listByParentId(category.getParentId());
            if (list.size() == 1) {
                int row = categoryMapper.updateIsParentById(category.getParentId(), 0);
                if (row != 1) {
                    throw new ServiceException(State.ERR_UPDATE, "更新类别失败，服务器忙（" + State.ERR_UPDATE.getValue() + "），请稍后再次尝试！");
                }
            }
        }
        categoryMapper.deleteById(id);
    }

    @Override
    public void updateById(CategoryDetailsVO category) {
        CategorySimpleVO categorySimpleVO = categoryMapper.getById(category.getId());
        if (categorySimpleVO == null) {
            throw new ServiceException(State.ERR_CATEGORY_NOT_FOUND, "获取类别详情失败，尝试访问的数据不存在！");
        }
        categorySimpleVO = categoryMapper.getByName(category.getName());
        if (categorySimpleVO != null) {
            throw new ServiceException(State.ERR_CATEGORY_NAME_DUPLICATE, "添加类别失败，名称（" + category.getName() + "）已存在！");
        }
        categoryMapper.updateById(category);
    }

}
