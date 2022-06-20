package cn.tedu.csmall.product.webapi.service;

import cn.tedu.csmall.common.ex.ServiceException;
import cn.tedu.csmall.pojo.dto.CategoryAddNewDTO;
import cn.tedu.csmall.pojo.vo.CategoryDetailsVO;
import cn.tedu.csmall.pojo.vo.CategorySimpleListItemVO;
import cn.tedu.csmall.product.service.ICategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CategoryServiceTests {

    @Autowired
    ICategoryService service;

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testAddNewSuccessfully() {
        CategoryAddNewDTO category = new CategoryAddNewDTO();
        category.setName("大屏智能手机");
        category.setParentId(10L);
        category.setIcon("未上传类别图标");
        category.setKeywords("未设置关键字");
        category.setSort(88);
        category.setIsDisplay(1);
        assertDoesNotThrow(() -> {
            service.addNew(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testAddNewFailBecauseNameDuplicate() {
        CategoryAddNewDTO category = new CategoryAddNewDTO();
        category.setName("类别001");
        assertThrows(ServiceException.class, () -> {
            service.addNew(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql"})
    public void testAddNewFailBecauseParentNotFound() {
        CategoryAddNewDTO category = new CategoryAddNewDTO();
        category.setName("类别115514");
        category.setParentId(-1L);
        assertThrows(ServiceException.class, () -> {
            service.addNew(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testListByParentId() {
        // 测试数据
        Long parentId = 1L;
        // 执行测试，获取查询结果
        List<CategorySimpleListItemVO> list = service.listByParentId(parentId);
        // 查看结果
        System.out.println("查询结果数量：" + list.size());
        for (CategorySimpleListItemVO item : list) {
            System.out.println(item);
        }
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testGetDetailsByIdSuccessfully() {
        // 测试数据
        Long id = 1L;
        // 断言不抛出异常
        assertDoesNotThrow(() -> {
            service.getDetailsById(id);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql"})
    public void testGetDetailsByIdFailBecauseNotFound() {
        // 测试数据
        Long id = -1L;
        // 断言抛出异常
        assertThrows(ServiceException.class, () -> {
            service.getDetailsById(id);
        });
    }

    /**
     * 6.26作业
     **/
    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testDisableByIdSuccessfully() {
        Long id = 1L;
        assertDoesNotThrow(() -> {
            service.disableById(id);
        });
    }

    @Test
    @Sql("classpath:truncate.sql")
    public void testDisableByIdFailBecauseNotFound() {
        Long id = -1L;
        assertThrows(ServiceException.class, () -> {
            service.disableById(id);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testDisableByIdFailBecauseIsDisable() {
        Long id = 16L;
        assertThrows(ServiceException.class, () -> {
            service.disableById(id);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testEnableByIdSuccessfully() {
        Long id = 16L;
        assertDoesNotThrow(() -> {
            service.enableById(id);
        });
    }

    @Test
    @Sql("classpath:truncate.sql")
    public void testEnableByIdFailBecauseNotFound() {
        Long id = -1L;
        assertThrows(ServiceException.class, () -> {
            service.enableById(id);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testEnableByIdFailBecauseIsDisable() {
        Long id = 1L;
        assertThrows(ServiceException.class, () -> {
            service.enableById(id);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testNotDisplayByIdSuccessfully() {
        Long id = 1L;
        assertDoesNotThrow(() -> {
            service.notDisplayById(id);
        });
    }

    @Test
    @Sql("classpath:truncate.sql")
    public void testNotDisplayByIdFailBecauseNotFound() {
        Long id = -1L;
        assertThrows(ServiceException.class, () -> {
            service.notDisplayById(id);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testNotDisplayByIdFailBecauseIsNotDisplay() {
        Long id = 16L;
        assertThrows(ServiceException.class, () -> {
            service.notDisplayById(id);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testIsDisplayByIdSuccessfully() {
        Long id = 16L;
        assertDoesNotThrow(() -> {
            service.isDisplayById(id);
        });
    }

    @Test
    @Sql("classpath:truncate.sql")
    public void testIsDisplayByIdFailBecauseNotFound() {
        Long id = -1L;
        assertThrows(ServiceException.class, () -> {
            service.isDisplayById(id);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testIsDisplayByIdFailBecauseIsIsDisplay() {
        Long id = 1L;
        assertThrows(ServiceException.class, () -> {
            service.isDisplayById(id);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testDeleteByIdSuccessfully() {
        // 测试数据
        Long id = 6L;
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            service.deleteById(id);
        });
    }

    @Test
    @Sql("classpath:truncate.sql")
    public void testDeleteByIdFailBecauseNotFound() {
        // 测试数据
        Long id = -1L;
        // 断言会抛出异常
        assertThrows(ServiceException.class, () -> {
            service.deleteById(id);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testDeleteByIdFailBecauseIsParent() {
        // 测试数据
        Long id = 1L;
        // 断言会抛出异常
        assertThrows(ServiceException.class, () -> {
            service.deleteById(id);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testUpdateByIdSuccessfully() {
        // 测试数据
        CategoryDetailsVO category = new CategoryDetailsVO();
        category.setId(1l);
        category.setName("YJ");
        category.setIcon("干杯");
        category.setSort(233);
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            service.updateById(category);
        });
    }

    @Test
    @Sql("classpath:truncate.sql")
    public void testUpdateByIdFailBecauseNotFound() {
        // 测试数据
        CategoryDetailsVO category = new CategoryDetailsVO();
        category.setId(-1l);
        category.setName("YJ");
        category.setIcon("干杯");
        category.setSort(233);
        // 断言会抛出异常
        assertThrows(ServiceException.class, () -> {
            service.updateById(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testUpdateByIdFailBecauseNameDuplicate() {
        // 测试数据
        CategoryDetailsVO category = new CategoryDetailsVO();
        category.setId(1l);
        category.setName("类别002");
        category.setIcon("干杯");
        category.setSort(233);
        // 断言会抛出异常
        assertThrows(ServiceException.class, () -> {
            service.updateById(category);
        });
    }

}
