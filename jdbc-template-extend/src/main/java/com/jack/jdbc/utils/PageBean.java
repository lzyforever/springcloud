package com.jack.jdbc.utils;

import java.util.Collections;
import java.util.List;

/**
 * 分页实体类
 */
public class PageBean<T> {
    /** 起始记录数 */
    private int start;

    /** 每页显示条数 */
    private int limit;

    /** 总记录数 */
    private long totalRecords;

    /** 当前页 */
    private int currentPage;

    /** 总页数 */
    private int totalPages;

    /** 查询结果对象集合 */
    private List<T> list;

    public static int page2Start(int page, int limit) {
        return page * limit - limit;
    }

    public static int start2Page(int start, int limit) {
        if (limit == 0) {
            return 0;
        }
        return (start + limit) / limit;
    }

    public static int calcPages(long totalRecords, int limit) {
        return (int)(totalRecords / limit) + (totalRecords % limit > 0 ? 1 : 0);
    }

    public PageBean() {
        super();
    }

    public PageBean(int start, int limit) {
        this.start = start;
        this.limit = limit;
        this.currentPage = start2Page(start, limit);
    }

    public PageBean(int start, int limit, List<T> list, long totalRecords) {
        this.start = start;
        this.limit = limit;
        this.list = list;
        this.totalRecords = totalRecords;
        this.currentPage = start2Page(start, limit);
        if (this.limit == 0) {
            this.totalPages = 0;
        } else {
            this.totalPages = (int)(this.totalRecords / this.limit) + (this.totalRecords % this.limit > 0 ? 1 : 0);
        }
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @SuppressWarnings("unchecked")
    public List<T> getList() {
        if (this.list == null) {
            return Collections.EMPTY_LIST;
        }
        return Collections.unmodifiableList(list);
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
