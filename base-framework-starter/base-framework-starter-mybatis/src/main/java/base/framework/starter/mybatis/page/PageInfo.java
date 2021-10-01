package base.framework.starter.mybatis.page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.ListUtils;

import java.util.List;

/**
 * @author yzm
 * @date 2021/10/1 - 21:10
 */
@JsonIgnoreProperties(value = {"startRow", "endRow", "prePage", "nextPage", "isFirstPage",
        "isLastPage", "hasPreviousPage", "navigatePages", "navigatepageNums", "navigateFirstPage",
        "navigateLastPage", "firstPage", "lastPage"})
public class PageInfo<T> extends com.github.pagehelper.PageInfo<T> implements PageResult<T> {

    private static final long serialVersionUID = 1L;

    public PageInfo(List<T> list) {
        super(list);
    }

    /**
     * 包装Page对象
     * <p>
     * 应对只有hasNext，却没进行count查询的场景<br>
     * 在进行查询时需要查询比rows多一条记录，然后根据查询结果的size是否比rows多判断是否有下一页
     *
     * @param list
     * @param pageQuery
     */
    public PageInfo(List<T> list, PageQuery pageQuery) {
        int startRow = pageQuery.getOffset();
        int rows = pageQuery.getRows();
        int pageNum = pageQuery.getPage();
        List<T> realList = Lists.newArrayList(list);
        if (list.size() > rows) {
            realList = ListUtils.partition(realList, rows).get(0);
            setHasNextPage(true);
        } else {
            setHasNextPage(false);
        }
        setStartRow(startRow);
        setList(realList);
        setPageNum(pageNum);
        setPageSize(rows);
        setSize(realList.size());
    }

    /**
     * 应对只有hasNext，却没进行count查询的场景
     * 在进行查询时需要查询比rows多一条记录，然后根据查询结果的size是否比rows多判断是否有下一页
     */
    public static void hasNextPage(PageQuery pageQuery) {
        int startRow = pageQuery.getOffset();
        int rows = pageQuery.getRows();
        PageHelper.offsetPage(startRow, rows + 1, false);
    }

}
