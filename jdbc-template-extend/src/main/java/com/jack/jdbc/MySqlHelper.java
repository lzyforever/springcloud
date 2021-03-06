package com.jack.jdbc;

/**
 * MySQL DBHelper实现
 */
public class MySqlHelper implements IDBHelper {

    @Override
    public String wrapToPageSql(String sql, int start, int limit) {
        return sql + " limit " + start + "," + limit;
    }

    @Override
    public String wrapToOrderBySql(Orders[] orders) {
        if (orders != null && orders.length > 0) {
            StringBuilder orderStr = new StringBuilder(" order by ");
            for (Orders order : orders) {
                orderStr.append(order.getName());
                orderStr.append(" ");
                orderStr.append(order.getType().getType());
                orderStr.append(",");
            }
            String value = orderStr.delete(orderStr.length() - 1, orderStr.length()).toString();
            return value;
        }
        return "";
    }
}
