package cn.tedu.csmall.product.webapi.repository.impl;

import cn.tedu.csmall.pojo.vo.CategoryDetailsVO;
import cn.tedu.csmall.product.webapi.repository.ICategoryRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Repository
public class CategoryRedisRepository implements ICategoryRedisRepository {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override
    public void save(CategoryDetailsVO category) {
        String key = KEY_CATEGORY_ITEM_PREFIX + category.getId();
        redisTemplate.opsForValue().set(key, category);
    }

    @Override
    public void save(List<CategoryDetailsVO> categories) {
        for (CategoryDetailsVO category : categories) {
            redisTemplate.opsForList().rightPush(KEY_CATEGORY_LIST, category);
        }
    }

    @Override
    public CategoryDetailsVO getDetailsById(Long id) {
        String key = KEY_CATEGORY_ITEM_PREFIX + id;
        Serializable result = redisTemplate.opsForValue().get(key);
        if (result == null) {
            return null;
        } else {
            CategoryDetailsVO category = (CategoryDetailsVO) result;
            return category;
        }
    }

    @Override
    public boolean exists(Long id) {
        String key = KEY_CATEGORY_ITEM_PREFIX + id;
        return redisTemplate.hasKey(key);
    }

    @Override
    public void saveEmptyValue(Long id) {
        String key = KEY_CATEGORY_ITEM_PREFIX + id;
        redisTemplate.opsForValue().set(key, null);
    }

    @Override
    public void deleteAllItem() {
        Set<String> keys = redisTemplate.keys(KEY_CATEGORY_ITEM_PREFIX + "*");
        redisTemplate.delete(keys);
    }

    @Override
    public Boolean deleteList() {
        return redisTemplate.delete(KEY_CATEGORY_LIST);
    }

}
