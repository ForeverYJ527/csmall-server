package cn.tedu.csmall.product.webapi.mapper;

import cn.tedu.csmall.pojo.entity.Category;
import cn.tedu.csmall.pojo.vo.CategoryDetailsVO;
import cn.tedu.csmall.pojo.vo.CategorySimpleListItemVO;
import cn.tedu.csmall.pojo.vo.CategorySimpleVO;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    int insert(Category category);

    CategorySimpleVO getByName(String name);

    CategorySimpleVO getById(Long id);

    int updateIsParentById(@Param("id") Long id, @Param("isParent") Integer isParent);

    List<CategorySimpleListItemVO> listByParentId(Long parentId);

    CategoryDetailsVO getDetailsById(Long id);

    List<CategoryDetailsVO> list();

    /**
     * 6.26作业
     **/
    int disableById(Long id);

    int enableById(Long id);

    int notDisplayById(Long id);

    int isDisplayById(Long id);

    int deleteById(Long id);

    int updateById(CategoryDetailsVO category);


}
