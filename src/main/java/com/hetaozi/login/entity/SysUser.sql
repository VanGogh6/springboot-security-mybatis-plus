-- auto Generated on 2020-09-10
-- DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user
(
    id         BIGINT(15)         NOT NULL AUTO_INCREMENT COMMENT '主键',
    username   VARCHAR(50) UNIQUE NOT NULL DEFAULT '' COMMENT '用户名',
    `password` VARCHAR(50)        NOT NULL DEFAULT '' COMMENT '密码',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT 'sys_user';
