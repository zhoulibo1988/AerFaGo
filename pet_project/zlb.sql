/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : zlb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-04-12 15:51:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_config_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_config_dictionary`;
CREATE TABLE `sys_config_dictionary` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `dict_pvalue` int(12) NOT NULL COMMENT '父级id',
  `dict_name` varchar(255) NOT NULL COMMENT '字典名称',
  `dict_value` varchar(255) NOT NULL COMMENT '字典值',
  `curr_type` int(10) NOT NULL DEFAULT '0' COMMENT '1失效 0正常',
  `creat_by` bigint(14) DEFAULT NULL COMMENT '字典创建者,可以引用公司 用户 aPP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='数据字典';

-- ----------------------------
-- Records of sys_config_dictionary
-- ----------------------------
INSERT INTO `sys_config_dictionary` VALUES ('1', '-1', '接入支付类型', 'PAY_TYPE', '0', null);
INSERT INTO `sys_config_dictionary` VALUES ('2', '1', '支付宝', 'PAY_TYPE_ALIPAY', '0', null);
INSERT INTO `sys_config_dictionary` VALUES ('3', '1', '微信支付', 'PAY_TYPE_WEIXIN', '0', null);
INSERT INTO `sys_config_dictionary` VALUES ('6', '1', '威富通微信wap支付', 'PAY_TYPE_WFT_WEIXIN', '0', null);
INSERT INTO `sys_config_dictionary` VALUES ('7', '1', '支付宝app支付', 'PAY_TYPE_ALIPAY_APP', '0', null);
INSERT INTO `sys_config_dictionary` VALUES ('8', '1', '支付宝条码支付', 'PAY_TYPE_ALIPAY_SCAN', '0', null);
INSERT INTO `sys_config_dictionary` VALUES ('9', '1', '微信刷卡支付', 'PAY_TYPE_WEIXIN_SCAN', '0', null);
INSERT INTO `sys_config_dictionary` VALUES ('10', '1', '支付宝web支付', 'PAY_TYPE_ALIPAY_WEB', '0', null);
INSERT INTO `sys_config_dictionary` VALUES ('11', '1', '有氧支付宝app支付', 'PAY_TYPE_ALIPAY_YY', '0', null);

-- ----------------------------
-- Table structure for sys_config_sms
-- ----------------------------
DROP TABLE IF EXISTS `sys_config_sms`;
CREATE TABLE `sys_config_sms` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(13) DEFAULT NULL COMMENT '执行操作的用户id',
  `sms_type` int(5) NOT NULL DEFAULT '2' COMMENT '类型 (1注册 2绑定手机 3找回支付密码 4提现 5切换账户 6设置密码)',
  `phone_number` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '接收短信的手机号',
  `sms_vercode` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '该条短信的验证码',
  `sms_contents` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '短信的内容',
  `sms_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '短信的唯一标识符',
  `is_verification` int(1) DEFAULT '0' COMMENT '验证码是否已经验证 （0-未验证；1-已经验证；2-过期）',
  `sms_effectiveTime` int(5) DEFAULT '0' COMMENT '短信有效时间（一般验证码验证使用，默认单位 秒）',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='手机短信信息表';

-- ----------------------------
-- Records of sys_config_sms
-- ----------------------------
INSERT INTO `sys_config_sms` VALUES ('1', '203', '3', '13046863212', null, null, '9971', '2', '300', '2017-03-03 15:37:00', null);
INSERT INTO `sys_config_sms` VALUES ('2', '203', '3', '13046863212', null, null, '3738', '2', '300', '2017-03-03 15:39:25', null);
INSERT INTO `sys_config_sms` VALUES ('3', '203', '3', '13046863212', null, null, '1334', '2', '300', '2017-03-03 15:40:45', null);
INSERT INTO `sys_config_sms` VALUES ('4', '203', '3', '18682475275', null, null, '4901', '2', '300', '2017-03-03 15:43:43', null);
INSERT INTO `sys_config_sms` VALUES ('5', '203', '3', '18682475275', null, null, '1260', '2', '300', '2017-03-03 15:45:26', null);
INSERT INTO `sys_config_sms` VALUES ('6', '203', '3', '13046863212', null, null, '8735', '2', '300', '2017-03-03 16:01:23', null);
INSERT INTO `sys_config_sms` VALUES ('7', '203', '3', '13046863212', null, null, '4742', '2', '300', '2017-03-07 16:14:22', null);
INSERT INTO `sys_config_sms` VALUES ('8', '203', '3', '13046863212', null, null, '9163', '2', '300', '2017-03-10 15:31:42', null);
INSERT INTO `sys_config_sms` VALUES ('9', '203', '3', '13537673785', null, null, '8903', '2', '300', '2017-03-11 09:51:10', null);
INSERT INTO `sys_config_sms` VALUES ('10', '203', '3', '13008817918', null, null, '5750', '2', '300', '2017-03-13 17:23:16', null);
INSERT INTO `sys_config_sms` VALUES ('11', '203', '3', '13046863212', null, null, '8051', '2', '300', '2017-03-13 17:41:26', null);
INSERT INTO `sys_config_sms` VALUES ('12', '203', '3', '15903629398', null, null, '3166', '2', '300', '2017-03-13 17:43:39', null);
INSERT INTO `sys_config_sms` VALUES ('13', '203', '3', '15903629398', null, null, '6266', '2', '300', '2017-03-14 10:23:33', null);
INSERT INTO `sys_config_sms` VALUES ('14', '203', '3', '15903629398', null, null, '8810', '2', '300', '2017-03-14 18:36:58', null);
INSERT INTO `sys_config_sms` VALUES ('15', '203', '3', '15903629398', null, null, '2532', '2', '300', '2017-03-14 18:36:58', null);
INSERT INTO `sys_config_sms` VALUES ('16', '203', '3', '15903629398', null, null, '5951', '2', '300', '2017-03-15 10:41:01', null);
INSERT INTO `sys_config_sms` VALUES ('17', '203', '3', '15903629398', null, null, '1074', '2', '300', '2017-03-15 10:48:05', null);
INSERT INTO `sys_config_sms` VALUES ('18', '203', '3', '15903629398', null, null, '1843', '2', '300', '2017-03-15 15:46:48', null);
INSERT INTO `sys_config_sms` VALUES ('19', '203', '3', '13537673785', null, null, '1377', '2', '300', '2017-03-16 11:10:07', null);
INSERT INTO `sys_config_sms` VALUES ('20', '203', '3', '15903629398', null, null, '2478', '2', '300', '2017-03-16 11:12:44', null);
INSERT INTO `sys_config_sms` VALUES ('21', '203', '3', '13537673785', null, null, '9257', '2', '300', '2017-03-16 11:20:06', null);
INSERT INTO `sys_config_sms` VALUES ('22', '203', '3', '13537673785', null, null, '2164', '2', '300', '2017-03-16 11:51:50', null);
INSERT INTO `sys_config_sms` VALUES ('23', '203', '3', '13537673785', null, null, '3292', '2', '300', '2017-03-17 15:07:36', null);
INSERT INTO `sys_config_sms` VALUES ('24', '203', '3', '13537673785', null, null, '8912', '2', '300', '2017-03-17 15:09:22', null);
INSERT INTO `sys_config_sms` VALUES ('25', '203', '3', '13537673785', null, null, '2784', '2', '300', '2017-03-17 15:22:30', null);
INSERT INTO `sys_config_sms` VALUES ('26', '203', '3', '13537673785', null, null, '3522', '2', '300', '2017-03-17 15:25:20', null);
INSERT INTO `sys_config_sms` VALUES ('27', '203', '3', '13537673785', null, null, '9324', '2', '300', '2017-03-17 15:52:47', null);
INSERT INTO `sys_config_sms` VALUES ('28', '203', '3', '13537673785', null, null, '9182', '2', '300', '2017-03-17 15:58:32', null);
INSERT INTO `sys_config_sms` VALUES ('29', '203', '3', '13537673785', null, null, '4212', '2', '300', '2017-03-17 16:00:44', null);
INSERT INTO `sys_config_sms` VALUES ('30', '203', '3', '13537673785', null, null, '9825', '2', '300', '2017-03-17 16:02:17', null);
INSERT INTO `sys_config_sms` VALUES ('31', '203', '3', '13537673785', null, null, '7188', '2', '300', '2017-03-17 16:04:58', null);
INSERT INTO `sys_config_sms` VALUES ('32', '203', '3', '13537673785', null, null, '2261', '2', '300', '2017-03-17 16:06:52', null);
INSERT INTO `sys_config_sms` VALUES ('33', '203', '3', '13537673785', null, null, '8085', '2', '300', '2017-03-17 16:09:44', null);
INSERT INTO `sys_config_sms` VALUES ('34', '203', '3', '13537673785', null, null, '3487', '2', '300', '2017-03-17 16:11:25', null);
INSERT INTO `sys_config_sms` VALUES ('35', '203', '3', '13537673785', null, null, '9858', '2', '300', '2017-03-17 16:12:28', null);
INSERT INTO `sys_config_sms` VALUES ('36', '203', '3', '13537673785', null, null, '8620', '2', '300', '2017-03-17 16:15:42', null);
INSERT INTO `sys_config_sms` VALUES ('37', '203', '3', '13537673785', null, null, '2398', '2', '300', '2017-03-17 16:17:33', null);
INSERT INTO `sys_config_sms` VALUES ('38', '203', '3', '13537673785', null, null, '2298', '2', '300', '2017-03-22 10:32:19', null);
INSERT INTO `sys_config_sms` VALUES ('39', '203', '3', '13537673785', null, null, '8795', '2', '300', '2017-03-22 10:39:20', null);
INSERT INTO `sys_config_sms` VALUES ('40', '203', '3', '13537673785', null, null, '4390', '2', '300', '2017-03-22 10:44:06', null);
INSERT INTO `sys_config_sms` VALUES ('41', '203', '3', '13537673785', null, null, '6476', '2', '300', '2017-03-22 10:46:00', null);
INSERT INTO `sys_config_sms` VALUES ('42', '203', '3', '13537673785', null, null, '6088', '2', '300', '2017-03-22 10:48:13', null);
INSERT INTO `sys_config_sms` VALUES ('43', '203', '3', '13537673785', null, null, '4146', '2', '300', '2017-03-22 10:49:32', null);
INSERT INTO `sys_config_sms` VALUES ('44', '203', '3', '13537673785', null, null, '8352', '2', '300', '2017-03-22 10:50:34', null);
INSERT INTO `sys_config_sms` VALUES ('45', '203', '3', '13537673785', null, null, '1452', '2', '300', '2017-03-22 10:52:34', null);
INSERT INTO `sys_config_sms` VALUES ('46', '203', '3', '13537673785', null, null, '7277', '2', '300', '2017-03-22 10:57:10', null);
INSERT INTO `sys_config_sms` VALUES ('47', '203', '3', '13537673785', null, null, '3878', '2', '300', '2017-03-22 10:59:42', null);
INSERT INTO `sys_config_sms` VALUES ('48', '203', '3', '13537673785', null, null, '9454', '2', '300', '2017-03-22 11:01:28', null);
INSERT INTO `sys_config_sms` VALUES ('49', '203', '3', '13537673785', null, null, '3844', '2', '300', '2017-03-22 11:03:35', null);
INSERT INTO `sys_config_sms` VALUES ('50', '203', '3', '13537673785', null, null, '5414', '2', '300', '2017-03-22 11:05:05', null);
INSERT INTO `sys_config_sms` VALUES ('51', '203', '3', '13537673785', null, null, '2926', '2', '300', '2017-03-22 11:39:58', null);
INSERT INTO `sys_config_sms` VALUES ('52', '203', '3', '13537673785', null, null, '3070', '2', '300', '2017-03-22 11:48:42', null);
INSERT INTO `sys_config_sms` VALUES ('53', '203', '3', '13537673785', null, null, '3292', '2', '300', '2017-03-22 11:52:13', null);
INSERT INTO `sys_config_sms` VALUES ('54', '203', '3', '13537673785', null, null, '8925', '2', '300', '2017-03-22 14:11:08', null);
INSERT INTO `sys_config_sms` VALUES ('55', '203', '3', '13537673785', null, null, '1102', '2', '300', '2017-03-22 14:16:18', null);
INSERT INTO `sys_config_sms` VALUES ('56', '203', '3', '13537673785', null, null, '2097', '2', '300', '2017-03-22 14:18:51', null);
INSERT INTO `sys_config_sms` VALUES ('57', '203', '3', '13537673785', null, null, '9191', '2', '300', '2017-03-22 14:22:44', null);
INSERT INTO `sys_config_sms` VALUES ('58', '203', '3', '13537673785', null, null, '5434', '2', '300', '2017-03-22 14:53:06', null);
INSERT INTO `sys_config_sms` VALUES ('59', '203', '3', '13537673785', null, null, '7382', '2', '300', '2017-03-22 15:01:49', null);
INSERT INTO `sys_config_sms` VALUES ('60', '203', '3', '15903629398', null, null, '1367', '2', '300', '2017-03-22 15:07:57', null);
INSERT INTO `sys_config_sms` VALUES ('61', '203', '3', '13537673785', null, null, '7253', '2', '300', '2017-03-22 15:16:54', null);
INSERT INTO `sys_config_sms` VALUES ('62', '203', '3', '13823690977', null, null, '7998', '2', '300', '2017-03-22 15:39:23', null);
INSERT INTO `sys_config_sms` VALUES ('63', '203', '3', '13823690977', null, null, '8602', '2', '300', '2017-03-22 16:15:07', null);
INSERT INTO `sys_config_sms` VALUES ('64', '203', '3', '13823690977', null, null, '5204', '2', '300', '2017-03-22 17:18:25', null);
INSERT INTO `sys_config_sms` VALUES ('65', '203', '3', '15903629398', null, null, '7906', '2', '300', '2017-03-24 15:34:43', null);
INSERT INTO `sys_config_sms` VALUES ('66', '203', '3', '15903629398', null, null, '5861', '2', '300', '2017-03-24 16:03:55', null);
INSERT INTO `sys_config_sms` VALUES ('67', '203', '3', '15903629398', null, null, '9965', '2', '300', '2017-03-24 16:08:09', null);
INSERT INTO `sys_config_sms` VALUES ('68', '203', '3', '15903629398', null, null, '4637', '2', '300', '2017-03-24 16:11:09', null);
INSERT INTO `sys_config_sms` VALUES ('69', '203', '3', '15903629398', null, null, '7748', '2', '300', '2017-03-24 16:23:42', null);
INSERT INTO `sys_config_sms` VALUES ('70', '203', '3', '15903629398', null, null, '4161', '2', '300', '2017-03-24 16:27:52', null);
INSERT INTO `sys_config_sms` VALUES ('71', '203', '3', '15903629398', null, null, '6232', '2', '300', '2017-03-24 16:31:31', null);
INSERT INTO `sys_config_sms` VALUES ('72', '203', '3', '15903629398', null, null, '5535', '2', '300', '2017-03-28 10:37:43', null);
INSERT INTO `sys_config_sms` VALUES ('73', '203', '3', '15903629398', null, null, '5627', '2', '300', '2017-03-28 16:06:24', null);
INSERT INTO `sys_config_sms` VALUES ('74', '203', '3', '15903629398', null, null, '5879', '2', '300', '2017-03-28 16:18:41', null);
INSERT INTO `sys_config_sms` VALUES ('75', '203', '3', '15903629398', null, null, '5586', '2', '300', '2017-03-29 15:52:14', null);
INSERT INTO `sys_config_sms` VALUES ('76', '203', '3', '15903629398', null, null, '9747', '2', '300', '2017-03-29 15:54:44', null);
INSERT INTO `sys_config_sms` VALUES ('77', '203', '3', '15903629398', null, null, '4368', '2', '300', '2017-03-29 15:57:09', null);
INSERT INTO `sys_config_sms` VALUES ('78', '203', '3', '15903629398', null, null, '9917', '2', '300', '2017-03-29 16:00:28', null);
INSERT INTO `sys_config_sms` VALUES ('79', '203', '3', '15903629398', null, null, '3833', '2', '300', '2017-03-29 16:05:04', null);
INSERT INTO `sys_config_sms` VALUES ('80', '203', '3', '15903629398', null, null, '6585', '2', '300', '2017-03-29 18:19:52', null);
INSERT INTO `sys_config_sms` VALUES ('81', '203', '3', '15220183163', null, null, '9374', '2', '300', '2017-04-05 11:32:22', null);
INSERT INTO `sys_config_sms` VALUES ('82', '203', '3', '15220183163', null, null, '9135', '2', '300', '2017-04-05 11:33:47', null);
INSERT INTO `sys_config_sms` VALUES ('83', '203', '3', '15220183163', null, null, '8616', '2', '300', '2017-04-05 11:37:57', null);
INSERT INTO `sys_config_sms` VALUES ('84', '203', '3', '15220183163', null, null, '8038', '2', '300', '2017-04-05 11:39:00', null);
INSERT INTO `sys_config_sms` VALUES ('85', '203', '3', '15220183163', null, null, '7335', '2', '300', '2017-04-05 11:40:02', null);
INSERT INTO `sys_config_sms` VALUES ('86', '203', '3', '15220183163', null, null, '8748', '2', '300', '2017-04-05 11:42:17', null);
INSERT INTO `sys_config_sms` VALUES ('87', '203', '3', '15220183163', null, null, '8667', '2', '300', '2017-04-05 11:43:35', null);
INSERT INTO `sys_config_sms` VALUES ('88', '203', '3', '15220183163', null, null, '4372', '2', '300', '2017-04-05 14:06:28', null);
INSERT INTO `sys_config_sms` VALUES ('89', '203', '3', '15220183163', null, null, '1874', '2', '300', '2017-04-05 14:31:44', null);
INSERT INTO `sys_config_sms` VALUES ('90', '203', '3', '15220183163', null, null, '7774', '2', '300', '2017-04-05 15:05:58', null);
INSERT INTO `sys_config_sms` VALUES ('91', '203', '3', '15220183163', null, null, '5963', '2', '300', '2017-04-05 15:55:25', null);
INSERT INTO `sys_config_sms` VALUES ('92', '203', '3', '15220183163', null, null, '1767', '2', '300', '2017-04-05 16:18:14', null);
INSERT INTO `sys_config_sms` VALUES ('93', '203', '3', '15220183163', null, null, '5907', '0', '300', '2017-04-06 18:16:15', null);

-- ----------------------------
-- Table structure for sys_ucenter_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_ucenter_admin`;
CREATE TABLE `sys_ucenter_admin` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `adm_name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户登录名',
  `adm_type` varchar(16) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'NORMAL' COMMENT '用户类型: “SUPER”, “NORMAL”',
  `adm_password` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户登录密码',
  `adm_display_name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户显示名称',
  `adm_tel` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户电话号码',
  `adm_status` int(1) NOT NULL DEFAULT '0' COMMENT '用户当前状态 0-有效；1-无效 ',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='管理员信息表';

-- ----------------------------
-- Records of sys_ucenter_admin
-- ----------------------------
INSERT INTO `sys_ucenter_admin` VALUES ('60', 'admin', 'SUPER', 'c49800568f183789a5a4a9f3264a764d', '超级裤衩', '13088888888', '0', '2015-11-07 03:04:28', '2015-11-11 11:05:21');
INSERT INTO `sys_ucenter_admin` VALUES ('111', 'system', 'NORMAL', 'c49800568f183789a5a4a9f3264a764d', 'system', '18888888888', '0', '2016-06-16 10:46:33', '2016-12-06 10:47:09');
INSERT INTO `sys_ucenter_admin` VALUES ('127', 'zhoulibo', 'NORMAL', 'c49800568f183789a5a4a9f3264a764d', '周立波', '13046863212', '0', '2016-12-01 14:37:55', null);

-- ----------------------------
-- Table structure for sys_ucenter_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_ucenter_admin_role`;
CREATE TABLE `sys_ucenter_admin_role` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用ID（可以理解为那个系统）',
  `adm_id` bigint(13) NOT NULL COMMENT '管理员用户id（表 sys_ucenter__admin中id）',
  `rol_id` bigint(13) NOT NULL COMMENT '角色id',
  `exp_date` date DEFAULT NULL COMMENT '到期时间 ',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `create_user_by` bigint(13) DEFAULT NULL COMMENT '创建用户id',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  `update_user_by` bigint(13) DEFAULT NULL COMMENT '最后一次修改的用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='管理员用户角色信息表';

-- ----------------------------
-- Records of sys_ucenter_admin_role
-- ----------------------------
INSERT INTO `sys_ucenter_admin_role` VALUES ('126', '77', '84', '20', null, '2015-12-09 19:34:11', '60', '2015-12-09 19:34:12', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('132', '77', '83', '20', null, '2015-12-11 16:24:58', '82', '2015-12-11 16:24:58', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('133', '77', '82', '14', null, '2015-12-11 16:26:14', '82', '2015-12-11 16:26:15', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('134', '77', '86', '14', null, '2015-12-15 09:18:14', '82', '2015-12-15 09:18:14', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('135', '77', '87', '20', null, '2015-12-15 09:57:50', '82', '2015-12-15 09:57:50', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('139', '77', '91', '20', null, '2015-12-23 10:31:11', '82', '2015-12-23 10:31:11', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('140', '77', '85', '14', null, '2015-12-29 10:12:48', '82', '2015-12-29 10:12:48', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('143', '77', '92', '19', null, '2016-01-11 14:23:23', '92', '2016-01-11 14:23:23', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('144', '77', '92', '21', null, '2016-01-11 14:23:23', '92', '2016-01-11 14:23:23', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('147', '77', '90', '20', null, '2016-01-11 14:24:28', '92', '2016-01-11 14:24:29', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('149', '77', '81', '20', null, '2016-02-27 10:28:14', '81', '2016-02-27 10:28:14', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('155', '77', '94', '14', null, '2016-04-05 17:58:01', '85', '2016-04-05 17:58:02', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('156', '77', '94', '22', null, '2016-04-05 17:58:01', '85', '2016-04-05 17:58:02', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('158', '77', '95', '19', null, '2016-04-07 15:52:20', '89', '2016-04-07 15:52:20', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('159', '77', '95', '22', null, '2016-04-07 15:52:20', '89', '2016-04-07 15:52:20', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('160', '77', '96', '14', null, '2016-04-21 10:34:16', '85', '2016-04-21 10:34:16', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('161', '77', '97', '20', null, '0000-00-00 00:00:00', null, '2016-05-05 11:06:48', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('162', '77', '89', '14', null, '2016-05-11 15:19:42', '89', '2016-05-11 15:16:45', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('163', '77', '89', '20', null, '2016-05-11 15:19:42', '89', '2016-05-11 15:16:45', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('164', '77', '98', '23', null, '2016-05-12 17:43:18', '85', '2016-05-12 17:43:17', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('165', '77', '99', '20', null, '2016-05-13 09:59:30', '82', '2016-05-13 09:59:29', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('166', '77', '100', '23', null, '2016-05-13 19:42:31', '85', '2016-05-13 19:42:31', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('167', '77', '93', '14', null, '2016-05-14 16:26:47', '85', '2016-05-14 16:26:47', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('168', '77', '93', '21', null, '2016-05-14 16:26:47', '85', '2016-05-14 16:26:47', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('169', '77', '101', '14', null, '2016-05-18 10:09:41', '82', '2016-05-19 14:26:30', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('170', '77', '102', '20', null, '2016-05-19 11:34:01', '82', '2016-05-19 11:26:05', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('171', '77', '103', '20', null, '2016-05-19 19:30:16', '85', '2016-05-19 19:30:16', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('172', '77', '88', '19', null, '2016-05-21 10:12:07', '85', '2016-05-21 10:12:07', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('173', '77', '88', '21', null, '2016-05-21 10:12:07', '85', '2016-05-21 10:12:07', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('174', '77', '88', '22', null, '2016-05-21 10:12:07', '85', '2016-05-21 10:12:07', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('175', '77', '104', '19', null, '2016-05-21 11:10:04', '85', '2016-05-21 11:10:04', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('176', '77', '104', '21', null, '2016-05-21 11:10:04', '85', '2016-05-21 11:10:04', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('177', '77', '104', '22', null, '2016-05-21 11:10:04', '85', '2016-05-21 11:10:04', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('179', '77', '106', '20', null, '2016-05-22 17:37:36', '82', '2016-05-22 17:37:36', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('180', '77', '107', '24', null, '2016-05-23 15:07:40', '85', '2016-05-23 15:07:40', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('181', '77', '108', '20', null, '2016-05-24 11:58:35', '107', '2016-05-24 11:58:35', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('182', '77', '109', '20', null, '2016-05-24 16:25:28', '82', '2016-05-24 16:25:28', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('183', '77', '110', '20', null, '2016-06-15 10:32:11', '82', '2016-06-15 10:32:11', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('187', '1', '112', '14', null, '2016-09-08 16:50:03', '112', '2016-09-08 16:49:43', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('189', '1', '117', '14', null, '2016-09-09 09:57:46', '112', '2016-09-09 09:57:26', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('190', '1', '118', '14', null, '2016-09-09 09:58:29', '112', '2016-09-09 09:58:09', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('192', '1', '119', '14', null, '2016-09-10 14:30:20', '112', '2016-09-10 14:29:57', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('193', '1', '121', '14', null, '2016-09-12 10:12:16', '116', '2016-09-12 10:11:51', null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('195', '1', '120', '14', null, '2016-09-18 14:46:04', '116', null, null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('196', '1', '123', '14', null, '2016-09-22 15:03:34', '112', null, null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('197', '1', '124', '14', null, '2016-09-22 15:04:21', '112', null, null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('198', '1', '125', '14', null, '2016-09-26 14:36:01', '116', null, null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('199', '1', '126', '14', null, '2016-09-29 14:37:43', '116', null, null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('200', '1', '116', '14', null, '2016-10-21 16:06:26', '116', null, null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('203', '1', '111', '14', null, '2016-12-08 19:16:34', '127', null, null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('204', '1', '127', '14', null, '2016-12-08 19:16:38', '127', null, null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('206', '1', '128', '16', null, '2016-12-12 17:47:37', '127', null, null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('207', '1', '129', '14', null, '2016-12-16 15:19:20', '127', null, null);
INSERT INTO `sys_ucenter_admin_role` VALUES ('211', '2', '130', '17', null, '2017-02-10 16:37:30', '129', null, null);

-- ----------------------------
-- Table structure for sys_ucenter_apps
-- ----------------------------
DROP TABLE IF EXISTS `sys_ucenter_apps`;
CREATE TABLE `sys_ucenter_apps` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(120) COLLATE utf8_unicode_ci NOT NULL COMMENT '应用名称',
  `app_type` int(1) NOT NULL COMMENT '应用类型（1-app;2-pc;3-其他） ',
  `app_key` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '应用编码',
  `app_logo` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '应用logo',
  `app_desc` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '应用描述',
  `app_contacts_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系人姓名',
  `app_contacts_phone` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `create_user_by` bigint(13) NOT NULL COMMENT '创建用户id',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  `update_user_by` bigint(13) DEFAULT NULL COMMENT '最后一次修改的用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='流量合作商表';

-- ----------------------------
-- Records of sys_ucenter_apps
-- ----------------------------
INSERT INTO `sys_ucenter_apps` VALUES ('1', '全民金服', '1', '2a8f2091bcd793df31e9709613fda681', null, '全民金服官方App', null, null, '2016-08-30 19:35:05', '60', '2016-08-30 19:36:51', '60');
INSERT INTO `sys_ucenter_apps` VALUES ('2', '运营管理', '2', 'OPERATION_MANAGEMENT', null, null, null, null, '2017-01-17 11:34:19', '129', null, null);

-- ----------------------------
-- Table structure for sys_ucenter_files
-- ----------------------------
DROP TABLE IF EXISTS `sys_ucenter_files`;
CREATE TABLE `sys_ucenter_files` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件自增长ID',
  `file_md5` varchar(255) NOT NULL COMMENT '文件的MD5值',
  `file_url` varchar(255) NOT NULL COMMENT '文件地址',
  `file_type` varchar(50) DEFAULT NULL COMMENT '文件后缀',
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `crc32` varchar(32) DEFAULT NULL COMMENT '文件的crc32',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8mb4 COMMENT='文件表';

-- ----------------------------
-- Records of sys_ucenter_files
-- ----------------------------
INSERT INTO `sys_ucenter_files` VALUES ('172', '97c9f0085fc2738a9a5a715e3829c956', 'http://172.10.3.14/yizhan/aizichan/aa.apk', 'apk', '165699', '2016-10-21 09:47:51', 'a216ecc');
INSERT INTO `sys_ucenter_files` VALUES ('173', 'a873287a92c1b676a6c6633e31647680', 'http://172.10.3.14/yizhan/aizichan/0dd7912397dda144390c8fa9b0b7d0a20cf486a0.jpg', 'jpg', '17569', '2016-10-21 09:58:05', '23875fc7');
INSERT INTO `sys_ucenter_files` VALUES ('174', '608af5643f585a534fddfa75d862329e', 'http://172.10.3.14/yizhan/aizichan/apkmanager_2.8.1.apk', 'apk', '731742', '2016-10-20 19:39:37', '84cdbeb2');
INSERT INTO `sys_ucenter_files` VALUES ('175', '9e93e8b712591fe6b536df9667fe3a0d', 'http://172.10.3.14/yizhan/aizichan/1ad5ad6eddc451da266e16e5b4fd5266d0163227.jpg', 'jpg', '6787', '2016-10-20 19:39:51', '121523aa');
INSERT INTO `sys_ucenter_files` VALUES ('176', 'a576baf058d07bca706d016d1edeea8d', 'http://172.10.3.14/yizhan/aizichan/18d8bc3eb13533fa6ecb672faad3fd1f41345bb6.jpg', 'jpg', '52994', '2016-10-21 11:27:45', '162c76f1');
INSERT INTO `sys_ucenter_files` VALUES ('181', '17178440f393c3265bd128c3dc990e4a', 'http://172.10.3.14/yizhan/aizichan/爱点赚1.0.8.apk', 'apk', '11749021', '2016-10-20 23:36:24', 'ecbb54ca');
INSERT INTO `sys_ucenter_files` VALUES ('182', 'fbc276dc9d5df2b53739798124a3e224', 'http://172.10.3.14/yizhan/aizichan/AA.apk', 'apk', '38178006', '2016-10-21 14:48:18', 'b7148330');
INSERT INTO `sys_ucenter_files` VALUES ('183', 'f2604129921a327b1a158fab9b13af42', 'http://172.10.3.14/yizhan/aizichan/moji-release-0923_signed.apk', 'apk', '40671671', '2016-10-20 23:54:47', '5d44f382');
INSERT INTO `sys_ucenter_files` VALUES ('184', '2ab5584ec36d1f39bfac873eb80c19ad', 'http://172.10.3.14/yizhan/aizichan/yuepaoquan.apk', 'apk', '13420591', '2016-10-21 01:01:45', 'f2a22a50');
INSERT INTO `sys_ucenter_files` VALUES ('190', '0ba2bcdbbac7c942f6fc59bdc194675b', 'http://172.10.3.14/yizhan/aizichan/wangyixinwen_450.apk', 'apk', '26683456', '2016-10-22 10:09:17', '99e30178');
INSERT INTO `sys_ucenter_files` VALUES ('194', '6c993360c49d048f2018e87bccaf372c', 'http://172.10.3.14/yizhan/aizichan/magick.keystore', 'keystore', '2225', '2016-10-22 11:29:00', '8e9e733c');
INSERT INTO `sys_ucenter_files` VALUES ('196', '2e79636e34f31bbb7b0fb56a262f021c', 'http://172.10.3.14/yizhan/aizichan/2c8a86f757ea79fb0157ea8687a80001sign.apk', 'apk', '40790097', '2016-10-22 11:55:47', null);
INSERT INTO `sys_ucenter_files` VALUES ('197', '2300f8198950b208831f72c62e29bd59', 'http://172.10.3.14/yizhan/aizichan/xiaocao_task_normal.apk', 'apk', '14967202', '2016-10-22 14:14:07', '669cf33c');
INSERT INTO `sys_ucenter_files` VALUES ('198', '48c99494a821fc884f8cca8015c781fa', 'http://172.10.3.14/yizhan/aizichan/2c8a86f757eb07ad0157eb07eb1e0000sign.apk', 'apk', '14969485', '2016-10-22 14:16:35', '86662d6c');
INSERT INTO `sys_ucenter_files` VALUES ('199', '311ee53ec1991ff150d5f4d249563a8b', 'http://172.10.3.14/yizhan/aizichan/ff80808157eb1f670157eb1f67b30000.keystore', 'keystore', '1403', '2016-10-21 23:44:29', '74dbfea0');
INSERT INTO `sys_ucenter_files` VALUES ('200', 'f41055dfe4824cd9407437323a539fa2', 'http://172.10.3.14/yizhan/aizichan/何涛.apk', 'apk', '1015284', '2016-10-21 23:46:37', '5c9843c6');
INSERT INTO `sys_ucenter_files` VALUES ('201', 'f7e803ed8e758079ccbcdcec127c0517', 'http://172.10.3.14/yizhan/aizichan/ff80808157eb1f670157eb2491e80005sign.apk', 'apk', '1015521', '2016-10-21 23:47:15', '1b49e28d');
INSERT INTO `sys_ucenter_files` VALUES ('202', 'e12763c3a6281db5b8c4d26a840d6c4a', 'http://172.10.3.14/yizhan/aizichan/402881f757f5c57b0157f5c57d4b0000sign.apk', 'apk', '40789853', '2016-10-24 16:21:54', '85cfa042');

-- ----------------------------
-- Table structure for sys_ucenter_function
-- ----------------------------
DROP TABLE IF EXISTS `sys_ucenter_function`;
CREATE TABLE `sys_ucenter_function` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用ID（可以理解为那个系统）',
  `fid` bigint(13) NOT NULL DEFAULT '-1' COMMENT '父级ID',
  `fun_code` varchar(120) COLLATE utf8_unicode_ci NOT NULL COMMENT '功能编码',
  `fun_name` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT '功能名称',
  `fun_status` int(1) NOT NULL DEFAULT '0' COMMENT '功能当前状态 0-有效；1-无效 ',
  `fun_path` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '功能路径',
  `fun_remark` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `create_user_by` bigint(13) NOT NULL COMMENT '创建用户id',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  `update_user_by` bigint(13) DEFAULT NULL COMMENT '最后一次修改的用户id',
  `fun_sort` int(255) DEFAULT '0' COMMENT '显示-排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='功能信息表';

-- ----------------------------
-- Records of sys_ucenter_function
-- ----------------------------
INSERT INTO `sys_ucenter_function` VALUES ('1', '1', '-1', 'MANAGER_MENU_001', '系统管理', '0', '#########', '后台管理系统-系统管理', '2015-11-20 16:34:35', '60', '2017-04-12 10:13:05', '127', '14');
INSERT INTO `sys_ucenter_function` VALUES ('2', '1', '1', 'MANAGER_MENU_001_001', '管理员管理', '0', '/admin/goadminlist.do', '后台管理系统-系统管理-管理员管理', '2015-11-20 16:34:35', '60', '2017-04-12 10:13:38', '127', '6');
INSERT INTO `sys_ucenter_function` VALUES ('3', '1', '1', 'MANAGER_MENU_001_002', '应用管理', '0', '/admin/goapplist.do', '台管理系统-系统管理-应用管理', '2015-11-20 16:34:35', '60', '2017-04-12 10:13:48', '127', '5');
INSERT INTO `sys_ucenter_function` VALUES ('4', '1', '1', 'MANAGER_MENU_001_003', '角色管理', '0', '/roles/torolelist.do', '管理系统-系统管理-角色管理', '2015-11-20 16:34:35', '60', '2017-04-12 10:14:11', '127', '2');
INSERT INTO `sys_ucenter_function` VALUES ('5', '1', '1', 'MANAGER_MENU_001_004', '菜单管理', '0', '/function/gomenulist.do', '后台管理系统-系统管理-菜单管理', '2015-11-20 16:34:35', '60', '2017-04-12 10:14:25', '127', '7');
INSERT INTO `sys_ucenter_function` VALUES ('31', '1', '1', 'MANAGER_MENU_001_005', '更新管理', '0', '/appUpdate/updateInfoList.do', '后台管理系统-更新管理', '2016-10-17 09:41:39', '112', '2017-04-12 10:13:58', '127', '4');
INSERT INTO `sys_ucenter_function` VALUES ('72', '1', '1', 'MANAGER_MENU_001_006', '字典管理', '0', 'sysDic/dicList.do', '后台管理系统-更新管理', '2016-12-12 10:13:00', '127', '2017-04-12 10:13:26', '127', '1');

-- ----------------------------
-- Table structure for sys_ucenter_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_ucenter_permission`;
CREATE TABLE `sys_ucenter_permission` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `rol_id` bigint(13) NOT NULL COMMENT '角色id',
  `fun_id` bigint(13) NOT NULL COMMENT '功能id',
  `prm_params` varchar(2048) DEFAULT NULL COMMENT '参数-预留',
  `prm_remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `create_user_by` bigint(13) DEFAULT NULL COMMENT '创建用户id',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  `update_user_by` bigint(13) DEFAULT NULL COMMENT '最后一次修改的用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3287 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='权限列表';

-- ----------------------------
-- Records of sys_ucenter_permission
-- ----------------------------
INSERT INTO `sys_ucenter_permission` VALUES ('1549', '19', '11', null, null, '2016-05-20 15:30:15', '85', '2016-05-20 15:30:15', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1550', '19', '45', null, null, '2016-05-20 15:30:15', '85', '2016-05-20 15:30:15', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1551', '19', '79', null, null, '2016-05-20 15:30:15', '85', '2016-05-20 15:30:15', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1552', '19', '69', null, null, '2016-05-20 15:30:15', '85', '2016-05-20 15:30:15', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1553', '19', '1', null, null, '2016-05-20 15:30:15', '85', '2016-05-20 15:30:15', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1554', '19', '8', null, null, '2016-05-20 15:30:15', '85', '2016-05-20 15:30:15', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1555', '19', '9', null, null, '2016-05-20 15:30:15', '85', '2016-05-20 15:30:15', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1556', '19', '70', null, null, '2016-05-20 15:30:15', '85', '2016-05-20 15:30:15', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1557', '19', '82', null, null, '2016-05-20 15:30:15', '85', '2016-05-20 15:30:15', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1558', '19', '50', null, null, '2016-05-20 15:30:15', '85', '2016-05-20 15:30:15', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1559', '19', '51', null, null, '2016-05-20 15:30:15', '85', '2016-05-20 15:30:15', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1560', '19', '10', null, null, '2016-05-20 15:30:15', '85', '2016-05-20 15:30:15', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1688', '24', '79', null, null, '2016-05-23 16:41:51', '85', '2016-05-23 16:41:51', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1689', '24', '8', null, null, '2016-05-23 16:41:51', '85', '2016-05-23 16:41:51', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1690', '24', '80', null, null, '2016-05-23 16:41:51', '85', '2016-05-23 16:41:51', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1691', '24', '9', null, null, '2016-05-23 16:41:51', '85', '2016-05-23 16:41:51', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1692', '24', '82', null, null, '2016-05-23 16:41:51', '85', '2016-05-23 16:41:51', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1693', '24', '50', null, null, '2016-05-23 16:41:51', '85', '2016-05-23 16:41:51', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1694', '24', '51', null, null, '2016-05-23 16:41:51', '85', '2016-05-23 16:41:51', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1780', '22', '79', null, null, '2016-05-24 19:40:56', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1781', '22', '56', null, null, '2016-05-24 19:40:56', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1782', '22', '7', null, null, '2016-05-24 19:40:56', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1783', '22', '82', null, null, '2016-05-24 19:40:57', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1784', '22', '80', null, null, '2016-05-24 19:40:57', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1785', '22', '53', null, null, '2016-05-24 19:40:57', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1786', '22', '81', null, null, '2016-05-24 19:40:57', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1787', '22', '72', null, null, '2016-05-24 19:40:57', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1788', '22', '60', null, null, '2016-05-24 19:40:57', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1789', '22', '73', null, null, '2016-05-24 19:40:57', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1790', '22', '61', null, null, '2016-05-24 19:40:57', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1791', '22', '74', null, null, '2016-05-24 19:40:57', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1792', '22', '88', null, null, '2016-05-24 19:40:57', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1793', '22', '75', null, null, '2016-05-24 19:40:57', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1794', '22', '85', null, null, '2016-05-24 19:40:57', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('1795', '22', '89', null, null, '2016-05-24 19:40:57', '82', '2016-05-24 19:40:56', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2109', '20', '79', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2110', '20', '78', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2111', '20', '77', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2112', '20', '58', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2113', '20', '57', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2114', '20', '56', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2115', '20', '55', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2116', '20', '11', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2117', '20', '64', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2118', '20', '82', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2119', '20', '83', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2120', '20', '80', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2121', '20', '62', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2122', '20', '81', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2123', '20', '60', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2124', '20', '61', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2125', '20', '87', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2126', '20', '84', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2127', '20', '85', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2128', '20', '67', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2129', '20', '66', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2130', '20', '69', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2131', '20', '68', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2132', '20', '45', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2133', '20', '91', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2134', '20', '46', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2135', '20', '90', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2136', '20', '10', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2137', '20', '1', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2138', '20', '7', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2139', '20', '6', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2140', '20', '51', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2141', '20', '70', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2142', '20', '53', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2143', '20', '71', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2144', '20', '9', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2145', '20', '72', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2146', '20', '54', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2147', '20', '8', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2148', '20', '73', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2149', '20', '74', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2150', '20', '88', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2151', '20', '75', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2152', '20', '76', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2153', '20', '89', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2154', '20', '50', null, null, '2016-06-02 10:23:10', '82', '2016-06-02 10:22:57', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2155', '21', '66', null, null, '2016-06-02 10:23:46', '82', '2016-06-02 10:23:33', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2156', '21', '7', null, null, '2016-06-02 10:23:46', '82', '2016-06-02 10:23:33', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2157', '21', '6', null, null, '2016-06-02 10:23:46', '82', '2016-06-02 10:23:33', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2158', '21', '91', null, null, '2016-06-02 10:23:46', '82', '2016-06-02 10:23:33', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2159', '21', '90', null, null, '2016-06-02 10:23:46', '82', '2016-06-02 10:23:33', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2160', '21', '71', null, null, '2016-06-02 10:23:46', '82', '2016-06-02 10:23:33', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2161', '21', '9', null, null, '2016-06-02 10:23:46', '82', '2016-06-02 10:23:33', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2162', '21', '8', null, null, '2016-06-02 10:23:46', '82', '2016-06-02 10:23:33', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2163', '23', '79', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2164', '23', '78', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2165', '23', '77', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2166', '23', '57', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2167', '23', '56', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2168', '23', '55', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2169', '23', '11', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2170', '23', '64', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2171', '23', '82', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2172', '23', '83', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2173', '23', '80', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2174', '23', '62', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2175', '23', '81', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2176', '23', '60', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2177', '23', '86', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2178', '23', '61', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2179', '23', '87', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2180', '23', '85', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2181', '23', '66', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2182', '23', '68', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2183', '23', '91', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2184', '23', '46', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2185', '23', '90', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2186', '23', '10', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2187', '23', '7', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:54', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2188', '23', '6', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:55', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2189', '23', '51', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:55', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2190', '23', '53', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:55', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2191', '23', '71', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:55', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2192', '23', '9', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:55', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2193', '23', '54', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:55', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2194', '23', '8', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:55', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2195', '23', '88', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:55', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2196', '23', '89', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:55', null);
INSERT INTO `sys_ucenter_permission` VALUES ('2197', '23', '50', null, null, '2016-06-07 17:36:55', '85', '2016-06-07 17:36:55', null);
INSERT INTO `sys_ucenter_permission` VALUES ('3028', '15', '3', null, null, '2016-12-01 14:42:20', '112', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3029', '15', '2', null, null, '2016-12-01 14:42:20', '112', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3030', '15', '1', null, null, '2016-12-01 14:42:20', '112', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3031', '15', '5', null, null, '2016-12-01 14:42:20', '112', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3032', '15', '31', null, null, '2016-12-01 14:42:20', '112', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3033', '15', '4', null, null, '2016-12-01 14:42:20', '112', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3210', '16', '66', null, null, '2016-12-12 17:47:23', '127', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3211', '16', '67', null, null, '2016-12-12 17:47:23', '127', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3212', '16', '68', null, null, '2016-12-12 17:47:23', '127', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3213', '16', '69', null, null, '2016-12-12 17:47:23', '127', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3214', '16', '70', null, null, '2016-12-12 17:47:23', '127', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3215', '16', '60', null, null, '2016-12-12 17:47:23', '127', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3216', '16', '71', null, null, '2016-12-12 17:47:23', '127', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3217', '16', '61', null, null, '2016-12-12 17:47:23', '127', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3218', '16', '62', null, null, '2016-12-12 17:47:23', '127', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3219', '16', '63', null, null, '2016-12-12 17:47:23', '127', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3220', '16', '64', null, null, '2016-12-12 17:47:23', '127', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3221', '16', '65', null, null, '2016-12-12 17:47:23', '127', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3264', '14', '66', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3265', '14', '77', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3266', '14', '67', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3267', '14', '78', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3268', '14', '68', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3269', '14', '79', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3270', '14', '69', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3271', '14', '1', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3272', '14', '2', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3273', '14', '3', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3274', '14', '4', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3275', '14', '5', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3276', '14', '80', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3277', '14', '70', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3278', '14', '60', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3279', '14', '71', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3280', '14', '72', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3281', '14', '61', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3282', '14', '62', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3283', '14', '73', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3284', '14', '63', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3285', '14', '64', null, null, '2017-03-31 11:56:24', '129', null, null);
INSERT INTO `sys_ucenter_permission` VALUES ('3286', '14', '65', null, null, '2017-03-31 11:56:24', '129', null, null);

-- ----------------------------
-- Table structure for sys_ucenter_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_ucenter_role`;
CREATE TABLE `sys_ucenter_role` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用ID（可以理解为那个系统）',
  `rol_code` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色编码',
  `rol_name` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色名称',
  `rol_status` int(1) NOT NULL DEFAULT '0' COMMENT '用户当前状态 0-有效；1-无效 ',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `create_user_by` bigint(13) DEFAULT NULL COMMENT '创建用户id',
  `update_time` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  `update_user_by` bigint(13) DEFAULT NULL COMMENT '最后一次修改的用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_ucenter_role
-- ----------------------------
INSERT INTO `sys_ucenter_role` VALUES ('14', '1', 'MANAGER_ROLE_001', '系统后台管理员', '0', '2015-11-20 19:38:36', '60', '2016-09-02 17:26:19', '60');
INSERT INTO `sys_ucenter_role` VALUES ('16', '1', 'CESHIZU001', '测试组', '0', '2016-12-12 17:47:01', '127', null, null);
INSERT INTO `sys_ucenter_role` VALUES ('17', '2', 'ROLE_OPERATION', '运营组', '0', '2017-01-17 11:59:52', '129', null, null);

-- ----------------------------
-- Function structure for func_calcDistance
-- ----------------------------
DROP FUNCTION IF EXISTS `func_calcDistance`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `func_calcDistance`(  
    origLng DECIMAL(20,6), -- A经度 
    origLat DECIMAL(20,6), -- A纬度  
    longitude DECIMAL(20,6), -- B经度  
    latitude DECIMAL(20,6) -- B纬度  
) RETURNS double
BEGIN
      DECLARE result DOUBLE DEFAULT 0; 
      
      SET result = round(6378.138*2*asin(sqrt(pow(sin(
        (origLat*pi()/180-latitude*pi()/180)/2),2)+cos(origLat*pi()/180)*cos(latitude*pi()/180)*
        pow(sin( (origLng*pi()/180-longitude*pi()/180)/2),2)))*1000);
      
      RETURN result;  
     
 END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for fun_distance
-- ----------------------------
DROP FUNCTION IF EXISTS `fun_distance`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `fun_distance`(lat1 float,lng1 float,lat2 float,lng2 float) RETURNS float
BEGIN
set @num=6378.138*2*ASIN(SQRT(POW(SIN((lat1*PI()/180-lat2*PI()/180)/2),2)+COS(lat1*PI()/180)*COS(lat2*PI()/180)*POW(SIN((lng1*PI()/180-lng2*PI()/180)/2),2)))*1000;
RETURN @num;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for FUN_JW_DIST
-- ----------------------------
DROP FUNCTION IF EXISTS `FUN_JW_DIST`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `FUN_JW_DIST`(lng1 double(15,9), lat1 double(15, 9), lng2 double(15,9), lat2 double(15,9)) RETURNS int(11)
BEGIN
 DECLARE dist int;
 
 SET dist = round(6378.138*2*asin(sqrt(pow(sin((lat1*pi()/180-lat2*pi()/180)/2),2)+cos(lat1*pi()/180)*cos(lat2*pi()/180)* pow(sin((lng1*pi()/180-lng2*pi()/180)/2),2)))*1000);
 
 RETURN (dist);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getDistance
-- ----------------------------
DROP FUNCTION IF EXISTS `getDistance`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getDistance`(curLat DOUBLE, curLon DOUBLE, shopLat DOUBLE, shopLon DOUBLE) RETURNS double
BEGIN  
  DECLARE  dis DOUBLE;  
    set dis = ACOS(SIN((curLat * 3.141592653589793) / 180 ) * SIN((shopLat *3.141592653589793) / 180 ) + COS((curLat * 3.141592653589793) / 180 ) * COS((shopLat * 3.141592653589793) / 180 ) * COS((curLon * 3.141592653589793) / 180 - (shopLon *3.141592653589793) / 180 ) ) * 6378.137 ;  
    RETURN dis;  
END
;;
DELIMITER ;
