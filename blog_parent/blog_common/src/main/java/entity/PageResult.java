package entity;

import java.util.List;

/**
 * @ClassName PageResult
 * @Description TODO
 * @Author mic
 * @Date 2018/11/25 19:12
 * @Version 1.0
 **/
public class PageResult<T> {

    private long total;

    private List rows;

    public PageResult() {
    }

    public PageResult(long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
