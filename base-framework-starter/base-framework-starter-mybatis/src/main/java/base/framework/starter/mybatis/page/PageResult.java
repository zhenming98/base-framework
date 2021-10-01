package base.framework.starter.mybatis.page;

import java.io.Serializable;
import java.util.List;

/**
 * @Author yzm
 * @Date 2021/10/1 - 21:04
 */
public interface PageResult<T> extends Serializable {

    int getPageNum();

    int getPageSize();

    int getSize();

    long getTotal();

    int getPages();

    List<T> getList();

    boolean isHasNextPage();
}
