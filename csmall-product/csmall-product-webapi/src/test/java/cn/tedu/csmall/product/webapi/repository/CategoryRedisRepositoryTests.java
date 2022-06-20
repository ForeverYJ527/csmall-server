package cn.tedu.csmall.product.webapi.repository;

import cn.tedu.csmall.pojo.vo.CategoryDetailsVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryRedisRepositoryTests {

    @Autowired
    ICategoryRedisRepository repository;

    @Test
    void testGetDetailsByIdSuccessfully() {
        testSave();

        Long id = 10L;
        CategoryDetailsVO category = repository.getDetailsById(id);
        Assertions.assertNotNull(category);
    }

    @Test
    void testGetDetailsByIdReturnNull() {
        Long id = -1L;
        CategoryDetailsVO category = repository.getDetailsById(id);
        Assertions.assertNull(category);
    }

    private void testSave() {
        CategoryDetailsVO category = new CategoryDetailsVO();
        category.setId(10L);
        category.setName("家用电器");

        Assertions.assertDoesNotThrow(() -> {
            repository.save(category);
        });
    }
}
