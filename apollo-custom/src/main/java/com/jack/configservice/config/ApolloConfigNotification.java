package com.jack.configservice.config;

/**
 * 模拟Apollo配置通知
 */
public class ApolloConfigNotification {
    /** 命名空间 */
    private String namespaceName;
    /** 服务ID */
    private long notificationId;

    public ApolloConfigNotification(){}

    public ApolloConfigNotification(String namespaceName, long notificationId) {
        this.namespaceName = namespaceName;
        this.notificationId = notificationId;
    }

    public String getNamespaceName() {
        return namespaceName;
    }

    public void setNamespaceName(String namespaceName) {
        this.namespaceName = namespaceName;
    }

    public long getNotificationId() {
        return notificationId;
    }

    @Override
    public String toString() {
        return "ApolloConfigNotification{" +
                "namespaceName='" + namespaceName + '\'' +
                ", notificationId=" + notificationId +
                '}';
    }
}
