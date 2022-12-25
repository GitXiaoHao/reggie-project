-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`
(
    `id`          bigint(20)     NOT NULL COMMENT '主键',
    `name`        varchar(50) COLLATE utf8_bin  DEFAULT NULL COMMENT '名称',
    `image`       varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
    `user_id`     bigint(20)     NOT NULL COMMENT '主键',
    `dish_id`     bigint(20)                    DEFAULT NULL COMMENT '菜品id',
    `setmeal_id`  bigint(20)                    DEFAULT NULL COMMENT '套餐id',
    `dish_flavor` varchar(50) COLLATE utf8_bin  DEFAULT NULL COMMENT '口味',
    `number`      int(11)        NOT NULL       DEFAULT '1' COMMENT '数量',
    `amount`      decimal(10, 2) NOT NULL COMMENT '金额',
    `create_time` datetime                      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='购物车';

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------
