package cn.tedu.csmall.product.webapi.app;

import cn.tedu.csmall.product.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CachePreLoad implements ApplicationRunner {

    @Autowired
    private ICategoryService categoryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("CachePreLoad.run()");
        log.debug("准备执行缓存预热……");

        categoryService.preloadCache();

        log.debug("缓存预热完成！");
    }

}
