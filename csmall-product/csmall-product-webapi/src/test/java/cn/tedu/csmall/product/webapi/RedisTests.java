package cn.tedu.csmall.product.webapi;

import cn.tedu.csmall.pojo.vo.CategoryDetailsVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTests {

    @Autowired
    RedisTemplate<String, Serializable> redisTemplate;

    @Test
    void testSetValue() {
        redisTemplate.opsForValue().set("name", "YJ");
    }

    @Test
    void testSetValueTTL() {
        redisTemplate.opsForValue()
                .set("name", "GY", 60, TimeUnit.SECONDS);
    }

    @Test
    void testSetObjectValue() {
        CategoryDetailsVO category = new CategoryDetailsVO();
        category.setId(65L);
        category.setIsParent(1);
        category.setDepth(1);
        category.setName("水果");
        redisTemplate.opsForValue()
                .set("category", category);
    }

    @Test
    void testGetValue() {
        // 当key存在时，可获取到有效值
        // 当key不存在时，获取到的结果将是null
        Serializable name = redisTemplate.opsForValue()
                .get("name");
        System.out.println("get value --> " + name);
    }

    @Test
    void testGetObjectValue() {
        // 当key存在时，可获取到有效值
        // 当key不存在时，获取到的结果将是null
        Serializable serializable = redisTemplate.opsForValue()
                .get("category");
        System.out.println("get value --> " + serializable);
        if (serializable != null) {
            CategoryDetailsVO category = (CategoryDetailsVO) serializable;
            System.out.println("get value --> " + category);
        }
    }

    @Test
    void testDeleteKey() {
        // 删除key时，将返回“是否成功删除”
        // 当key存在时，将返回true
        // 当key不存在时，将返回false
        Boolean result = redisTemplate.delete("name");
        System.out.println("result --> " + result);
    }

    @Test
    void testRightPushList() {
        // 存入List时，需要redisTemplate.opsForList()得到针对List的操作器
        // 通过rightPush()可以向Redis中的List追加数据
        // 每次调用rightPush()时使用的key必须是同一个，才能把多个数据放到同一个List中
        List<CategoryDetailsVO> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            CategoryDetailsVO category = new CategoryDetailsVO();
            category.setName("类别00" + i);
            list.add(category);
        }

        String key = "categoryList";
        for (CategoryDetailsVO category : list) {
            redisTemplate.opsForList().rightPush(key, category);
        }
    }

    @Test
    void testListSize() {
        // 获取List的长度，即List中的元素数量
        String key = "categoryList";
        Long size = redisTemplate.opsForList().size(key);
        System.out.println("size --> " + size);
    }

    @Test
    void testRange() {
        // 调用opsForList()后再调用range(String key, long start, long end)方法取出List中的若干个数据，将得到List
        // long start：起始下标（结果中将包含）
        // long end：结束下标（结果中将包含），如果需要取至最后一个元素，可使用-1作为此参数值
        String key = "categoryList";
        List<Serializable> range = redisTemplate.opsForList().range(key, 0, -1);
        for (Serializable serializable : range) {
            System.out.println(serializable);
        }
    }

    @Test
    void testKeys() {
        // 调用keys()方法可以找出匹配模式的所有key
        // 在模式中，可以使用星号作为通配符
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }

}
