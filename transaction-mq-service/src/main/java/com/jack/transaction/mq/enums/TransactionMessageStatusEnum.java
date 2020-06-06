package com.jack.transaction.mq.enums;

/**
 * 消息状态枚举类
 */
public enum TransactionMessageStatusEnum {
    /**
     * 等待消费
     */
    WAITING(0),
    /**
     * 已消费
     */
    OVER(1),
    /**
     * 死亡
     */
    DIE(2);

    private int status;

    TransactionMessageStatusEnum(int status) {
        this.status = status;
    }

    public static TransactionMessageStatusEnum parse(int status) {
        for (TransactionMessageStatusEnum statusEnum : values()) {
            if (statusEnum.getStatus() == status) {
                return statusEnum;
            }
        }
        return null;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
