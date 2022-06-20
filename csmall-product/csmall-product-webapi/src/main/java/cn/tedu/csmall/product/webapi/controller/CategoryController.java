package cn.tedu.csmall.product.webapi.controller;

import cn.tedu.csmall.common.web.JsonResult;
import cn.tedu.csmall.pojo.dto.CategoryAddNewDTO;
import cn.tedu.csmall.pojo.vo.CategoryDetailsVO;
import cn.tedu.csmall.pojo.vo.CategorySimpleListItemVO;
import cn.tedu.csmall.product.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories", produces = "application/json; charset=utf-8")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/add-new")
    public JsonResult<Void> addNew(@Validated CategoryAddNewDTO categoryAddNewDTO) {
        categoryService.addNew(categoryAddNewDTO);
        return JsonResult.ok();
    }

    @GetMapping("/list-by-parent")
    public JsonResult<List<CategorySimpleListItemVO>> listByParentId(Long parentId) {
        List<CategorySimpleListItemVO> list = categoryService.listByParentId(parentId);
        return JsonResult.ok(list);
    }

    @GetMapping("/{id}")
    public JsonResult<CategoryDetailsVO> getDetailsById(@PathVariable Long id) {
        CategoryDetailsVO category = categoryService.getDetailsById(id);
        return JsonResult.ok(category);
    }

    /**
     * 6.26作业
     **/

    @GetMapping("/disable")
    public JsonResult<Void> disableById(Long id) {
        categoryService.disableById(id);
        return JsonResult.ok();
    }

    @GetMapping("/enable")
    public JsonResult<Void> enableById(Long id) {
        categoryService.enableById(id);
        return JsonResult.ok();
    }

    @GetMapping("/notDisplay")
    public JsonResult<Void> notDisplay(Long id) {
        categoryService.notDisplayById(id);
        return JsonResult.ok();
    }

    @GetMapping("/isDisplay")
    public JsonResult<Void> isDisplay(Long id) {
        categoryService.isDisplayById(id);
        return JsonResult.ok();
    }

    @GetMapping("/delete")
    public JsonResult<Void> deleteById(Long id) {
        categoryService.deleteById(id);
        return JsonResult.ok();
    }

    @GetMapping("/update")
    public JsonResult<Void> updateById(CategoryDetailsVO category) {
        categoryService.updateById(category);
        return JsonResult.ok();
    }

    @GetMapping("/cache/rebuild")
    public JsonResult<Void> rebuildCache() {
        categoryService.preloadCache();
        return JsonResult.ok();
    }


}
