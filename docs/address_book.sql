DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book`
(
    `id`            bigint(20)                   NOT NULL COMMENT '主键',
    `user_id`       bigint(20)                   NOT NULL COMMENT '用户id',
    `consignee`     varchar(50) COLLATE utf8_bin NOT NULL COMMENT '收货人',
    `sex`           tinyint(4)                   NOT NULL COMMENT '性别 0 女 1 男',
    `phone`         varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号',
    `province_code` varchar(12) CHARACTER SET utf8mb4     DEFAULT NULL COMMENT '省级区划编号',
    `province_name` varchar(32) CHARACTER SET utf8mb4     DEFAULT NULL COMMENT '省级名称',
    `city_code`     varchar(12) CHARACTER SET utf8mb4     DEFAULT NULL COMMENT '市级区划编号',
    `city_name`     varchar(32) CHARACTER SET utf8mb4     DEFAULT NULL COMMENT '市级名称',
    `district_code` varchar(12) CHARACTER SET utf8mb4     DEFAULT NULL COMMENT '区级区划编号',
    `district_name` varchar(32) CHARACTER SET utf8mb4     DEFAULT NULL COMMENT '区级名称',
    `detail`        varchar(200) CHARACTER SET utf8mb4    DEFAULT NULL COMMENT '详细地址',
    `label`         varchar(100) CHARACTER SET utf8mb4    DEFAULT NULL COMMENT '标签',
    `is_default`    tinyint(1)                   NOT NULL DEFAULT '0' COMMENT '默认 0 否 1是',
    `create_time`   datetime                     NOT NULL COMMENT '创建时间',
    `update_time`   datetime                     NOT NULL COMMENT '更新时间',
    `create_user`   bigint(20)                   NOT NULL COMMENT '创建人',
    `update_user`   bigint(20)                   NOT NULL COMMENT '修改人',
    `is_deleted`    int(11)                      NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='地址管理';

-- ----------------------------
-- Records of address_book
-- ----------------------------
INSERT INTO `address_book`
VALUES ('1417414526093082626', '1417012167126876162', '小明', '1', '13812345678', null, null, null, null, null, null,
        '昌平区金燕龙办公楼', '公司', '1', '2021-07-20 17:22:12', '2021-07-20 17:26:33', '1417012167126876162',
        '1417012167126876162', '0');
INSERT INTO `address_book`
VALUES ('1417414926166769666', '1417012167126876162', '小李', '1', '13512345678', null, null, null, null, null, null,
        '测试', '家', '0', '2021-07-20 17:23:47', '2021-07-20 17:23:47', '1417012167126876162', '1417012167126876162',
        '0');
