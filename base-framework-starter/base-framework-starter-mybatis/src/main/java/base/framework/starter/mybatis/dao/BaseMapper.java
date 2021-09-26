package base.framework.starter.mybatis.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Author yzm
 * @Date 2021/9/25 - 15:18
 */
//public interface BaseMapper<T> extends com.baomidou.mybatisplus.mapper.BaseMapper<T> {
//
//}

public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
