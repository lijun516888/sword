/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost
 Source Database       : amooly

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : utf-8

 Date: 09/25/2018 23:07:39 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `SYS_PERMISSION`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_PERMISSION`;
CREATE TABLE "SYS_PERMISSION" (
  "ID" bigint(20) NOT NULL AUTO_INCREMENT,
  "TYPE" varchar(255) DEFAULT NULL,
  "VALUE" varchar(255) DEFAULT NULL,
  "ICON" varchar(255) DEFAULT NULL,
  "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY ("ID")
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `SYS_PERMISSION`
-- ----------------------------
BEGIN;
INSERT INTO `SYS_PERMISSION` VALUES ('1', 'URL', '/manage/login.html', null, '2018-09-08 08:06:56', '2018-09-08 08:06:56'), ('2', 'URL', '/manage/data/list.html', null, '2018-09-08 08:06:56', '2018-09-08 08:06:56'), ('3', 'URL', '/manage/data/json.html', null, '2018-09-08 08:06:56', '2018-09-08 08:06:56');
COMMIT;

-- ----------------------------
--  Table structure for `SYS_ROLE`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE`;
CREATE TABLE "SYS_ROLE" (
  "ID" bigint(20) NOT NULL AUTO_INCREMENT,
  "NAME" varchar(255) DEFAULT NULL,
  "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY ("ID")
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `SYS_ROLE`
-- ----------------------------
BEGIN;
INSERT INTO `SYS_ROLE` VALUES ('1', '管理员', '2018-09-08 08:06:55', '2018-09-08 08:06:56');
COMMIT;

-- ----------------------------
--  Table structure for `SYS_ROLE_PERMISSION`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE_PERMISSION`;
CREATE TABLE "SYS_ROLE_PERMISSION" (
  "ID" bigint(20) NOT NULL AUTO_INCREMENT,
  "ROLE_ID" bigint(20) DEFAULT NULL,
  "PERMISSION_ID" bigint(20) DEFAULT NULL,
  "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY ("ID")
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `SYS_ROLE_PERMISSION`
-- ----------------------------
BEGIN;
INSERT INTO `SYS_ROLE_PERMISSION` VALUES ('1', '1', '1', '2018-09-08 08:06:56', '2018-09-08 08:06:56'), ('2', '1', '2', '2018-09-08 08:06:56', '2018-09-08 08:06:56'), ('3', '1', '3', '2018-09-08 08:06:56', '2018-09-08 08:06:56');
COMMIT;

-- ----------------------------
--  Table structure for `SYS_ROLE_USER`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE_USER`;
CREATE TABLE "SYS_ROLE_USER" (
  "ID" bigint(20) NOT NULL AUTO_INCREMENT,
  "USER_ID" bigint(20) DEFAULT NULL,
  "ROLE_ID" bigint(20) DEFAULT NULL,
  "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY ("ID")
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `SYS_ROLE_USER`
-- ----------------------------
BEGIN;
INSERT INTO `SYS_ROLE_USER` VALUES ('1', '1', '1', '2018-09-08 08:06:56', '2018-09-08 08:06:56');
COMMIT;

-- ----------------------------
--  Table structure for `SYS_USER`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER`;
CREATE TABLE "SYS_USER" (
  "ID" bigint(20) NOT NULL AUTO_INCREMENT,
  "USER_NAME" varchar(255) DEFAULT NULL,
  "PASSWORD" varchar(255) DEFAULT NULL,
  "SALT" varchar(255) DEFAULT NULL,
  "STATUS" varchar(255) DEFAULT NULL,
  "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY ("ID")
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `SYS_USER`
-- ----------------------------
BEGIN;
INSERT INTO `SYS_USER` VALUES ('1', 'sa', 'c3eba57ba67727bcc54ae47e559dc0321d4d38fd', 'b3acaa5c71ef4780', '1', '2018-09-08 08:06:12', '2018-09-08 08:06:30');
COMMIT;

-- ----------------------------
--  Table structure for `t_order_0`
-- ----------------------------
DROP TABLE IF EXISTS `t_order_0`;
CREATE TABLE "t_order_0" (
  "ID" bigint(20) NOT NULL AUTO_INCREMENT,
  "NAME" varchar(64) DEFAULT NULL,
  "tid" int(11) DEFAULT NULL,
  PRIMARY KEY ("ID")
) ENGINE=InnoDB AUTO_INCREMENT=251478030793310210 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_order_0`
-- ----------------------------
BEGIN;
INSERT INTO `t_order_0` VALUES ('251478030793310209', 'A', '2');
COMMIT;

-- ----------------------------
--  Table structure for `t_order_1`
-- ----------------------------
DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE "t_order_1" (
  "ID" bigint(20) NOT NULL AUTO_INCREMENT,
  "NAME" varchar(64) DEFAULT NULL,
  "tid" int(11) DEFAULT NULL,
  PRIMARY KEY ("ID")
) ENGINE=InnoDB AUTO_INCREMENT=251478030860419074 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_order_1`
-- ----------------------------
BEGIN;
INSERT INTO `t_order_1` VALUES ('251478030860419073', 'A', '3');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
