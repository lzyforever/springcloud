package com.jack.jdbc;

import com.jack.jdbc.utils.PageBean;

/**
 * 分页参数封装
 */
public class PageQueryParam {
    private int page;
    private int start;
    private int limit;
    private String sort;
    private boolean asc;

    public PageQueryParam() {
        super();
    }

    public PageQueryParam(int page, int limit) {
        setPage(page);
        setLimit(limit);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        if (this.limit > 0) {
            this.start = PageBean.page2Start(page, limit);
        }
    }

    public int getStart() {
        if (this.start < 0) {
            return 0;
        }
        return start;
    }

    public void setStart(int start) {
        this.start = start;
        if (this.limit > 0) {
            this.page = PageBean.start2Page(this.start, this.limit);
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
        if (this.page > 0) {
            this.start = PageBean.page2Start(this.page, this.limit);
        }
        if (this.start > 0) {
            this.page = PageBean.start2Page(this.start, this.limit);
        }
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }
}
