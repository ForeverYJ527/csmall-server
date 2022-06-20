package cn.tedu.csmall.product.service;

import cn.tedu.csmall.pojo.dto.CategoryAddNewDTO;
import cn.tedu.csmall.pojo.vo.CategoryDetailsVO;
import cn.tedu.csmall.pojo.vo.CategorySimpleListItemVO;

import java.util.List;

public interface ICategoryService {

    void addNew(CategoryAddNewDTO categoryAddNewDTO);

    List<CategorySimpleListItemVO> listByParentId(Long parentId);

    CategoryDetailsVO getDetailsById(Long id);

    void preloadCache();

    /**
     * 6.26作业
     **/
    void disableById(Long id);

    void enableById(Long id);

    void notDisplayById(Long id);

    void isDisplayById(Long id);

    void deleteById(Long id);

    void updateById(CategoryDetailsVO category);

}
