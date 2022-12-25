-- ----------------------------
-- Table structure for setmeal
-- ----------------------------
DROP TABLE IF EXISTS `setmeal`;
CREATE TABLE `setmeal`
(
    `id`          bigint(20)                   NOT NULL COMMENT '主键',
    `category_id` bigint(20)                   NOT NULL COMMENT '菜品分类id',
    `name`        varchar(64) COLLATE utf8_bin NOT NULL COMMENT '套餐名称',
    `price`       decimal(10, 2)               NOT NULL COMMENT '套餐价格',
    `status`      int(11)                               DEFAULT NULL COMMENT '状态 0:停用 1:启用',
    `code`        varchar(32) COLLATE utf8_bin          DEFAULT NULL COMMENT '编码',
    `description` varchar(512) COLLATE utf8_bin         DEFAULT NULL COMMENT '描述信息',
    `image`       varchar(255) COLLATE utf8_bin         DEFAULT NULL COMMENT '图片',
    `create_time` datetime                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                     NOT NULL COMMENT '更新时间',
    `create_user` bigint(20)                   NOT NULL COMMENT '创建人',
    `update_user` bigint(20)                   NOT NULL COMMENT '修改人',
    `is_deleted`  int(11)                      NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_setmeal_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='套餐';

-- ----------------------------
-- Records of setmeal
-- ----------------------------
INSERT INTO `setmeal`
VALUES ('1415580119015145474', '1413386191767674881', '儿童套餐A计划', '4000.00', '1', '', '',
        '61d20592-b37f-4d72-a864-07ad5bb8f3bb.jpg', '2021-07-15 15:52:55', '2021-07-15 15:52:55', '1415576781934608386',
        '1415576781934608386', '0');
