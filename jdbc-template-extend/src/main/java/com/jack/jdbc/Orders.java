package com.jack.jdbc;

public class Orders {

    private String name;
    private OrderType type;

    public Orders() {
        super();
    }

    public Orders(String name, OrderType type) {
        super();
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    /**
     * 排序类型
     */
    public enum OrderType {
        /** 正序 */
        ASC("asc"),
        /** 倒序 */
        DESC("desc");

        private String type;

        OrderType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
