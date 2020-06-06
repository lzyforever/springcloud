package com.jack.jdbc;

/**
 * DB工具类接口
 */
public interface IDBHelper {
    /**
     * 包装分页SQL
     * @param sql
     * @param start
     * @param limit
     * @return
     */
    String wrapToPageSql(String sql, int start, int limit);

    /**
     * 包装排序SQL
     * @param orders
     * @return
     */
    String wrapToOrderBySql(Orders[] orders);
}
