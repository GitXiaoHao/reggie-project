-- ----------------------------
-- Table structure for setmeal_dish
-- ----------------------------
DROP TABLE IF EXISTS `setmeal_dish`;
CREATE TABLE `setmeal_dish`
(
    `id`          bigint(20)                   NOT NULL COMMENT '主键',
    `setmeal_id`  varchar(32) COLLATE utf8_bin NOT NULL COMMENT '套餐id ',
    `dish_id`     varchar(32) COLLATE utf8_bin NOT NULL COMMENT '菜品id',
    `name`        varchar(32) COLLATE utf8_bin          DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
    `price`       decimal(10, 2)                        DEFAULT NULL COMMENT '菜品原价（冗余字段）',
    `copies`      int(11)                      NOT NULL COMMENT '份数',
    `sort`        int(11)                      NOT NULL DEFAULT '0' COMMENT '排序',
    `create_time` datetime                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                     NOT NULL COMMENT '更新时间',
    `create_user` bigint(20)                   NOT NULL COMMENT '创建人',
    `update_user` bigint(20)                   NOT NULL COMMENT '修改人',
    `is_deleted`  int(11)                      NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='套餐菜品关系';

-- ----------------------------
-- Records of setmeal_dish
-- ----------------------------
INSERT INTO `setmeal_dish`
VALUES ('1415580119052894209', '1415580119015145474', '1397862198033297410', '老火靓汤', '49800.00', '1', '0',
        '2021-07-15 15:52:55', '2021-07-15 15:52:55', '1415576781934608386', '1415576781934608386', '0');
INSERT INTO `setmeal_dish`
VALUES ('1415580119061282817', '1415580119015145474', '1413342036832100354', '北冰洋', '500.00', '1', '0',
        '2021-07-15 15:52:55', '2021-07-15 15:52:55', '1415576781934608386', '1415576781934608386', '0');
INSERT INTO `setmeal_dish`
VALUES ('1415580119069671426', '1415580119015145474', '1413385247889891330', '米饭', '200.00', '1', '0',
        '2021-07-15 15:52:55', '2021-07-15 15:52:55', '1415576781934608386', '1415576781934608386', '0');
