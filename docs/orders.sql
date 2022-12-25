-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`
(
    `id`              bigint(20)     NOT NULL COMMENT '主键',
    `number`          varchar(50) COLLATE utf8_bin  DEFAULT NULL COMMENT '订单号',
    `status`          int(11)        NOT NULL       DEFAULT '1' COMMENT '订单状态 1待付款，2待派送，3已派送，4已完成，5已取消',
    `user_id`         bigint(20)     NOT NULL COMMENT '下单用户',
    `address_book_id` bigint(20)     NOT NULL COMMENT '地址id',
    `order_time`      datetime       NOT NULL COMMENT '下单时间',
    `checkout_time`   datetime       NOT NULL COMMENT '结账时间',
    `pay_method`      int(11)        NOT NULL       DEFAULT '1' COMMENT '支付方式 1微信,2支付宝',
    `amount`          decimal(10, 2) NOT NULL COMMENT '实收金额',
    `remark`          varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
    `phone`           varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `address`         varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `user_name`       varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `consignee`       varchar(255) COLLATE utf8_bin DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='订单表';

-- ----------------------------
-- Records of orders
-- ----------------------------
