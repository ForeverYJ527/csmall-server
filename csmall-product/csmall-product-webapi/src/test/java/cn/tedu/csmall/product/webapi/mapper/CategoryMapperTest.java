package cn.tedu.csmall.product.webapi.mapper;

import cn.tedu.csmall.pojo.entity.Category;
import cn.tedu.csmall.pojo.vo.CategoryDetailsVO;
import cn.tedu.csmall.pojo.vo.CategorySimpleListItemVO;
import cn.tedu.csmall.pojo.vo.CategorySimpleVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryMapperTest {

    @Autowired
    CategoryMapper mapper;

    @Test
    @Sql("classpath:truncate.sql")
    public void testInsert() {
        Category category = new Category();
        category.setName("iphone");
        assertDoesNotThrow(() -> {
            int row = mapper.insert(category);
            assertEquals(1, row);
            assertEquals(1, category.getId());
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testGetByNameSuccessfully() {
        String name = "类别005";
        assertDoesNotThrow(() -> {
            CategorySimpleVO category = mapper.getByName(name);
            assertNotNull(category);
        });
    }

    @Test
    @Sql("classpath:truncate.sql")
    public void testGetByNameFailBecauseNotFound() {
        String name = "类别114514";
        assertDoesNotThrow(() -> {
            CategorySimpleVO category = mapper.getByName(name);
            assertNull(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testGetByIdSuccessfully() {
        Long id = 1L;
        assertDoesNotThrow(() -> {
            CategorySimpleVO category = mapper.getById(id);
            assertNotNull(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testGetByIdFailBecauseNotFound() {
        Long id = -1L;
        assertDoesNotThrow(() -> {
            CategorySimpleVO category = mapper.getById(id);
            assertNull(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testUpdateIsParentIdSuccessfully() {
        Long id = 1L;
        Integer isParent = 1;
        assertDoesNotThrow(() -> {
            int rows = mapper.updateIsParentById(id, isParent);
            assertEquals(1, rows);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testUpdateIsParentIdFailBecauseNotFound() {
        Long id = -1L;
        Integer isParent = 1;
        assertDoesNotThrow(() -> {
            int rows = mapper.updateIsParentById(id, isParent);
            assertEquals(0, rows);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testListByParentId() {
        // 测试数据
        Long parentId = 1L;
        // 执行测试，获取查询结果
        List<CategorySimpleListItemVO> list = mapper.listByParentId(parentId);
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
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            // 执行查询
            CategoryDetailsVO category = mapper.getDetailsById(id);
            // 断言查询结果不为null
            assertNotNull(category);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql"})
    public void testGetDetailsByIdFailBecauseNotFound() {
        // 测试数据
        Long id = -1L;
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            // 执行查询
            CategoryDetailsVO category = mapper.getDetailsById(id);
            // 断言查询结果为null
            assertNull(category);
        });
    }

    /**
     * 6.26作业
     **/
    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testNotDisplayByIdSuccessfully() {
        // 测试数据
        Long id = 1L;
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            int row = mapper.notDisplayById(id);
            assertEquals(1, row);
        });
    }

    @Test
    @Sql("classpath:truncate.sql")
    public void testNotDisplayByIdFailBecauseNotFound() {
        // 测试数据
        Long id = -1L;
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            int row = mapper.notDisplayById(id);
            assertEquals(0, row);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testIsDisplayByIdSuccessfully() {
        // 测试数据
        Long id = 1L;
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            int row = mapper.isDisplayById(id);
            assertEquals(1, row);
        });
    }

    @Test
    @Sql("classpath:truncate.sql")
    public void testIsDisplayByIdFailBecauseNotFound() {
        // 测试数据
        Long id = -1L;
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            int row = mapper.isDisplayById(id);
            assertEquals(0, row);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testDisableByIdSuccessfully() {
        // 测试数据
        Long id = 1L;
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            int row = mapper.disableById(id);
            assertEquals(1, row);
        });
    }

    @Test
    @Sql("classpath:truncate.sql")
    public void testDisableByIdFailBecauseNotFound() {
        // 测试数据
        Long id = -1L;
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            int row = mapper.disableById(id);
            assertEquals(0, row);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testEnableByIdSuccessfully() {
        // 测试数据
        Long id = 1L;
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            int row = mapper.enableById(id);
            assertEquals(1, row);
        });
    }

    @Test
    @Sql("classpath:truncate.sql")
    public void testEnableByIdFailBecauseNotFound() {
        // 测试数据
        Long id = -1L;
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            int row = mapper.enableById(id);
            assertEquals(0, row);
        });
    }

    @Test
    @Sql({"classpath:truncate.sql", "classpath:insert_data.sql"})
    public void testDeleteByIdSuccessfully() {
        // 测试数据
        Long id = 1L;
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            int row = mapper.deleteById(id);
            assertEquals(1, row);
        });
    }

    @Test
    @Sql("classpath:truncate.sql")
    public void testDeleteByIdFailBecauseNotFound() {
        // 测试数据
        Long id = -1L;
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            int row = mapper.deleteById(id);
            assertEquals(0, row);
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
            int row = mapper.updateById(category);
            assertEquals(1, row);
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
        // 断言不会抛出异常
        assertDoesNotThrow(() -> {
            int row = mapper.updateById(category);
            assertEquals(0, row);
        });
    }

}
