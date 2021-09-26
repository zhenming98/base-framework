package base.framework.starter.mybatis.service;

import base.framework.starter.mybatis.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author yzm
 * @date 2021/9/25 - 15:21
 */
public interface ServiceInterface<T extends BaseEntity, ID extends Serializable> {

    /**
     * 所有数据列表
     *
     * @return List<T>
     */
    public List<T> getAll();

    /**
     * 根据ID获取对象
     *
     * @param id
     * @return 对象<T>
     */
    public T getById(ID id);

    /**
     * 根据ID删除对象
     *
     * @param id
     */
    public void deleteById(ID id);

    /**
     * 保存对象
     * 如果ID存在则更新，否则新增
     *
     * @param o
     */
    public void save(T o);

}
