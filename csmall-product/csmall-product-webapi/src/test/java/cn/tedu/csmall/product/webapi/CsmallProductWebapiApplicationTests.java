package cn.tedu.csmall.product.webapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class CsmallProductWebapiApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() {
    }

    @Test
    void testConnection() {
        Assertions.assertDoesNotThrow(() -> {
            dataSource.getConnection();
        });
    }

}
