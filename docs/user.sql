-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`        bigint(20)                    NOT NULL COMMENT '主键',
    `name`      varchar(50) COLLATE utf8_bin  DEFAULT NULL COMMENT '姓名',
    `phone`     varchar(100) COLLATE utf8_bin NOT NULL COMMENT '手机号',
    `sex`       varchar(2) COLLATE utf8_bin   DEFAULT NULL COMMENT '性别',
    `id_number` varchar(18) COLLATE utf8_bin  DEFAULT NULL COMMENT '身份证号',
    `avatar`    varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
    `status`    int(11)                       DEFAULT '0' COMMENT '状态 0:禁用，1:正常',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='用户信息';

