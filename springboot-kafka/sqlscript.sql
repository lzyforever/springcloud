## 创建数据库表

# sql 生产消息一致表
CREATE TABLE `kafka_in_msg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num` bigint(20) NOT NULL COMMENT '档案号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`content` varchar(200) COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `in_num` (`num`) USING BTREE COMMENT '服务唯一标识'
) ENGINE=InnoDB AUTO_INCREMENT=372 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='消息消费成功表 幂等性';

# sql 消费消息一致表
CREATE TABLE `kafka_out_msg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num` bigint(20) NOT NULL COMMENT '档案号',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '处理标记 0 失败 1 成功',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`content` varchar(200) COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `out_num` (`num`) USING BTREE COMMENT '服务唯一标识'
) ENGINE=InnoDB AUTO_INCREMENT=715 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='任务添加进kafka队列 幂等性';
