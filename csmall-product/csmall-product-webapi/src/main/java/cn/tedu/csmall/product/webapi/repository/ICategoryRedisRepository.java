package cn.tedu.csmall.product.webapi.repository;

import cn.tedu.csmall.pojo.vo.CategoryDetailsVO;

import java.util.List;

public interface ICategoryRedisRepository {

    String KEY_CATEGORY_ITEM_PREFIX = "categories:item:";

    /**
     * 类别列表的KEY
     */
    String KEY_CATEGORY_LIST = "categories:list";

    // 将类别详情存入到Redis中
    void save(CategoryDetailsVO category);

    // 根据类别id获取类别详情
    CategoryDetailsVO getDetailsById(Long id);

    /**
     * 判断是否存在id对应的缓存数据
     *
     * @param id 类别id
     * @return 存在则返回true，否则返回false
     */
    boolean exists(Long id);

    /**
     * 向缓存中写入某id对应的空数据（null），此方法主要用于解决缓存穿透问题
     *
     * @param id 类别id
     */
    void saveEmptyValue(Long id);

    void save(List<CategoryDetailsVO> categories);

    /**
     * 删除Redis中各独立存储的类别数据
     */
    void deleteAllItem();

    /**
     * 删除Redis中的类别列表
     *
     * @return 如果成功删除，则返回true，否则返回false
     */
    Boolean deleteList();

}
