package base.framework.starter.mybatis.page;

import java.io.Serializable;

/**
 * @author yzm
 * @date 2021/10/1 - 21:03
 */
public class PageQuery implements Serializable {

    private static final long serialVersionUID = -632470285934872642L;
    private Integer page = 1;
    private Integer rows = 10;
    private Integer offset;

    private boolean allowPage = true;

    public Integer getOffset() {
        return (page - 1) * rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public boolean isAllowPage() {
        return allowPage;
    }

    public void setAllowPage(boolean allowPage) {
        this.allowPage = allowPage;
    }
}
