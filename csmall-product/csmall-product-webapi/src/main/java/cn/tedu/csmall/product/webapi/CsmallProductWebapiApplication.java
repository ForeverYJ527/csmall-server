package cn.tedu.csmall.product.webapi;

import cn.tedu.csmall.common.config.CsmallCommonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CsmallCommonConfiguration.class})
public class CsmallProductWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsmallProductWebapiApplication.class, args);
    }

}
