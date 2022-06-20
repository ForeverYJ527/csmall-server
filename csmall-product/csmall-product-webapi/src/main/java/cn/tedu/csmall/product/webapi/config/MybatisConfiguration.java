package cn.tedu.csmall.product.webapi.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.tedu.csmall.product.webapi.mapper")
public class MybatisConfiguration {

}
