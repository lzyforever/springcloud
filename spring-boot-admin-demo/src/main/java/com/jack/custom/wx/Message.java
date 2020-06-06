package com.jack.custom.wx;

public class Message {
    // 消息类型，此时固定为：text
    private String msgtype;
    // 企业应用的id，整型。企业内部开发，可在应用的设置页面查看；第三方服务商，可通过接口 获取企业授权信息 获取该参数值
    private String agentid;
    // 指定接收消息的成员，成员ID列表（多个接收者用‘|’分隔，最多支持1000个） 特殊情况：指定为”@all”，则向该企业应用的全部成员发送
    private String touser;
    // 指定接收消息的部门，部门ID列表，多个接收者用‘|’分隔，最多支持100个。 当touser为”@all”时忽略本参数
    private String toparty;
    // 指定接收消息的标签，标签ID列表，多个接收者用‘|’分隔，最多支持100个 当touser为”@all”时忽略本参数
    private String totag;
    // 表示是否是保密消息，0表示否，1表示是，默认0
    private String safe;
    // 表示是否开启id转译，0表示否，1表示是，默认0
    private String enable_id_trans;
    // 表示是否开启重复消息检查，0表示否，1表示是，默认0
    private String enable_duplicate_check;
    // 表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
    private String duplicate_check_interval;
    // 消息内容，最长不超过2048个字节，超过将截断（支持id转译）
    private MessageInfo text;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public MessageInfo getText() {
        return text;
    }

    public void setText(MessageInfo text) {
        this.text = text;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getToparty() {
        return toparty;
    }

    public void setToparty(String toparty) {
        this.toparty = toparty;
    }

    public String getTotag() {
        return totag;
    }

    public void setTotag(String totag) {
        this.totag = totag;
    }

    public String getSafe() {
        return safe;
    }

    public void setSafe(String safe) {
        this.safe = safe;
    }

    public String getEnable_id_trans() {
        return enable_id_trans;
    }

    public void setEnable_id_trans(String enable_id_trans) {
        this.enable_id_trans = enable_id_trans;
    }

    public String getEnable_duplicate_check() {
        return enable_duplicate_check;
    }

    public void setEnable_duplicate_check(String enable_duplicate_check) {
        this.enable_duplicate_check = enable_duplicate_check;
    }

    public String getDuplicate_check_interval() {
        return duplicate_check_interval;
    }

    public void setDuplicate_check_interval(String duplicate_check_interval) {
        this.duplicate_check_interval = duplicate_check_interval;
    }
}

