package base.framework.starter.mybatis.service;

import base.framework.starter.mybatis.dao.BaseMapper;
import base.framework.starter.mybatis.entity.BaseEntity;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.annotation.Resource;
import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author yzm
 * @date 2021/9/25 - 15:18
 */
public class BaseService<T extends BaseEntity, ID extends Serializable, DAO extends BaseMapper<T>> implements ServiceInterface<T, ID> {

    @Resource
    public DAO dao;

    @Override
    public List<T> getAll() {
        return dao.selectAll();
    }

    @Override
    public T getById(ID id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public void deleteById(ID id) {
        dao.deleteByPrimaryKey(id);
    }

    @Override
    public void save(T o) {
        boolean hasPrimaryKeyValue = false;
        List<Field> idFields = FieldUtils.getFieldsListWithAnnotation(o.getClass(), Id.class);
        for (Field idField : idFields) {
            idField.setAccessible(true);
            try {
                Object idFieldValue = idField.get(o);
                if (idFieldValue != null) {
                    hasPrimaryKeyValue = true;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (hasPrimaryKeyValue) {
            dao.updateByPrimaryKey(o);
        } else {
            dao.insert(o);
        }
    }
}
