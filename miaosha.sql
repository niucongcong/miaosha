/*
 Navicat Premium Data Transfer

 Source Server         : miaosha
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : miaosha

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 03/01/2021 21:21:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alluser
-- ----------------------------
DROP TABLE IF EXISTS `alluser`;
CREATE TABLE `alluser`  (
  `id` tinyint NOT NULL AUTO_INCREMENT COMMENT '用户的id  递增',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户的name',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `role_id` int NULL DEFAULT NULL COMMENT '角色  分为学生 宿舍管理员 admin',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alluser
-- ----------------------------
INSERT INTO `alluser` VALUES (8, 'student', 'cd73502828457d15655bbd7a63fb0bc8', 3);
INSERT INTO `alluser` VALUES (9, 'admin', '21232f297a57a5a743894a0e4a801fc3', 1);
INSERT INTO `alluser` VALUES (10, 'assistant', 'f549cd73f694aa6f5541b4ae30894eea', 2);

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `price` double(10, 0) NOT NULL DEFAULT 0,
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `sales` int NOT NULL DEFAULT 0,
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `item_id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (8, 'iphone99', 800, '最好用的苹果手机', 84361, 'https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01JLfHaf29zFowFOYfg_!!1714128138.jpg');
INSERT INTO `item` VALUES (9, 'iphone8', 600, '第二好用的苹果手机', 106, 'https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01JLfHaf29zFowFOYfg_!!1714128138.jpg');
INSERT INTO `item` VALUES (10, 'iphone9', 601, '第二好用的苹果手机', 89, 'https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01JLfHaf29zFowFOYfg_!!1714128138.jpg');
INSERT INTO `item` VALUES (11, 'iphone10', 602, '第二好用的苹果手机', 90, 'https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01JLfHaf29zFowFOYfg_!!1714128138.jpg');
INSERT INTO `item` VALUES (12, 'iphone11', 603, '第二好用的苹果手机', 91, 'https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01JLfHaf29zFowFOYfg_!!1714128138.jpg');
INSERT INTO `item` VALUES (13, 'iphone12', 604, '第二好用的苹果手机', 92, 'https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01JLfHaf29zFowFOYfg_!!1714128138.jpg');
INSERT INTO `item` VALUES (14, 'iphone13', 605, '第二好用的苹果手机', 93, 'https://img.alicdn.com/bao/uploaded/i3/2202871297035/O1CN01Pjx6eT21q4vj67MDr_!!0-item_pic.jpg');
INSERT INTO `item` VALUES (15, 'iphone14', 606, '第二好用的苹果手机', 94, 'https://img.alicdn.com/bao/uploaded/i4/1923056651/O1CN01Zdodp41z0CrEmtny0_!!1923056651.jpg');
INSERT INTO `item` VALUES (16, 'iphone15', 607, '第二好用的苹果手机', 95, 'https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01JLfHaf29zFowFOYfg_!!1714128138.jpg');
INSERT INTO `item` VALUES (17, 'iphone16', 608, '第二好用的苹果手机', 96, 'https://img.alicdn.com/bao/uploaded/i3/2202871297035/O1CN01Pjx6eT21q4vj67MDr_!!0-item_pic.jpg');
INSERT INTO `item` VALUES (18, 'iphone17', 609, '第二好用的苹果手机', 97, 'https://img.alicdn.com/bao/uploaded/i3/2202871297035/O1CN01Pjx6eT21q4vj67MDr_!!0-item_pic.jpg');
INSERT INTO `item` VALUES (19, 'iphone18', 610, '第二好用的苹果手机', 98, 'https://img.alicdn.com/bao/uploaded/i4/1923056651/O1CN01Zdodp41z0CrEmtny0_!!1923056651.jpg');
INSERT INTO `item` VALUES (20, 'iphone19', 611, '第二好用的苹果手机', 99, 'https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01JLfHaf29zFowFOYfg_!!1714128138.jpg');
INSERT INTO `item` VALUES (21, 'iphone20', 612, '第二好用的苹果手机', 100, 'https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01JLfHaf29zFowFOYfg_!!1714128138.jpg');
INSERT INTO `item` VALUES (22, 'iphone21', 613, '第二好用的苹果手机', 101, 'https://img.alicdn.com/bao/uploaded/i4/1923056651/O1CN01Zdodp41z0CrEmtny0_!!1923056651.jpg');
INSERT INTO `item` VALUES (23, 'iphone22', 614, '第二好用的苹果手机', 102, 'https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01JLfHaf29zFowFOYfg_!!1714128138.jpg');
INSERT INTO `item` VALUES (24, 'iphone23', 615, '第二好用的苹果手机', 103, 'https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01JLfHaf29zFowFOYfg_!!1714128138.jpg');
INSERT INTO `item` VALUES (25, 'iphone24', 616, '第二好用的苹果手机', 104, 'https://img.alicdn.com/bao/uploaded/i4/1923056651/O1CN01Zdodp41z0CrEmtny0_!!1923056651.jpg');
INSERT INTO `item` VALUES (26, 'iphone25', 617, '第二好用的苹果手机', 105, 'https://img.alicdn.com/bao/uploaded/i4/1923056651/O1CN01Zdodp41z0CrEmtny0_!!1923056651.jpg');
INSERT INTO `item` VALUES (27, 'iphone26', 618, '第二好用的苹果手机', 106, 'https://img.alicdn.com/bao/uploaded/i4/1923056651/O1CN01Zdodp41z0CrEmtny0_!!1923056651.jpg');
INSERT INTO `item` VALUES (28, 'iphone27', 619, '第二好用的苹果手机', 107, 'https://img.alicdn.com/bao/uploaded/i1/2208761139323/O1CN01RqvpDz2Ijz9Ay2TWh_!!0-item_pic.jpg');
INSERT INTO `item` VALUES (29, 'iphone28', 620, '第二好用的苹果手机', 108, 'https://img.alicdn.com/bao/uploaded/i4/1923056651/O1CN01Zdodp41z0CrEmtny0_!!1923056651.jpg');
INSERT INTO `item` VALUES (30, 'iphone29', 621, '第二好用的苹果手机', 109, 'https://img.alicdn.com/bao/uploaded/i3/1714128138/O1CN01JLfHaf29zFowFOYfg_!!1714128138.jpg');
INSERT INTO `item` VALUES (31, 'iphone30', 622, '第二好用的苹果手机', 110, 'https://img.alicdn.com/bao/uploaded/i1/2208761139323/O1CN01RqvpDz2Ijz9Ay2TWh_!!0-item_pic.jpg');
INSERT INTO `item` VALUES (32, 'iphone31', 623, '第二好用的苹果手机', 111, 'https://img.alicdn.com/bao/uploaded/i4/1923056651/O1CN01Zdodp41z0CrEmtny0_!!1923056651.jpg');
INSERT INTO `item` VALUES (33, 'iphone32', 624, '第二好用的苹果手机', 119, 'https://img.alicdn.com/bao/uploaded/i1/2208761139323/O1CN01RqvpDz2Ijz9Ay2TWh_!!0-item_pic.jpg');

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock` int NOT NULL DEFAULT 0,
  `item_id` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `item_id_index`(`item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_stock
-- ----------------------------
INSERT INTO `item_stock` VALUES (38, 157734, 8);
INSERT INTO `item_stock` VALUES (39, 10184, 9);
INSERT INTO `item_stock` VALUES (40, 20301, 10);
INSERT INTO `item_stock` VALUES (41, 20410, 11);
INSERT INTO `item_stock` VALUES (42, 20510, 12);
INSERT INTO `item_stock` VALUES (43, 20610, 13);
INSERT INTO `item_stock` VALUES (44, 20711, 14);
INSERT INTO `item_stock` VALUES (45, 20811, 15);
INSERT INTO `item_stock` VALUES (46, 20911, 16);
INSERT INTO `item_stock` VALUES (47, 21011, 17);
INSERT INTO `item_stock` VALUES (48, 21111, 18);
INSERT INTO `item_stock` VALUES (49, 21211, 19);
INSERT INTO `item_stock` VALUES (50, 21311, 20);
INSERT INTO `item_stock` VALUES (51, 21411, 21);
INSERT INTO `item_stock` VALUES (52, 21511, 22);
INSERT INTO `item_stock` VALUES (53, 21611, 23);
INSERT INTO `item_stock` VALUES (54, 21711, 24);
INSERT INTO `item_stock` VALUES (55, 21811, 25);
INSERT INTO `item_stock` VALUES (56, 21911, 26);
INSERT INTO `item_stock` VALUES (57, 22011, 27);
INSERT INTO `item_stock` VALUES (58, 22111, 28);
INSERT INTO `item_stock` VALUES (59, 22211, 29);
INSERT INTO `item_stock` VALUES (60, 22311, 30);
INSERT INTO `item_stock` VALUES (61, 22411, 31);
INSERT INTO `item_stock` VALUES (62, 22511, 32);
INSERT INTO `item_stock` VALUES (63, 21911, 33);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` int NOT NULL DEFAULT 0,
  `item_id` int NOT NULL DEFAULT 0,
  `item_price` double NOT NULL DEFAULT 0,
  `amount` int NOT NULL DEFAULT 0,
  `order_price` double NOT NULL DEFAULT 0,
  `promo_id` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('2020121808423600', 23, 9, 600, 4, 2400, 0);
INSERT INTO `order_info` VALUES ('2020121808423700', 23, 8, 100, 4, 400, 1);
INSERT INTO `order_info` VALUES ('2021010308423800', 24, 8, 100, 1, 100, 16);
INSERT INTO `order_info` VALUES ('2021010308423900', 24, 8, 100, 4, 400, 16);
INSERT INTO `order_info` VALUES ('2021010308424000', 24, 8, 100, 1, 100, 16);
INSERT INTO `order_info` VALUES ('2021010308424100', 24, 8, 100, 1, 100, 16);
INSERT INTO `order_info` VALUES ('2021010308424200', 24, 8, 100, 1, 100, 16);
INSERT INTO `order_info` VALUES ('2021010308424300', 24, 8, 800, 1, 800, 0);
INSERT INTO `order_info` VALUES ('2021010308424400', 24, 8, 800, 5, 4000, 0);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `permissionname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'add');
INSERT INTO `permission` VALUES (2, 'delete');
INSERT INTO `permission` VALUES (3, 'choose');

-- ----------------------------
-- Table structure for promo
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `start_date` datetime(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `item_id` int NOT NULL DEFAULT 0,
  `promo_item_price` double NOT NULL DEFAULT 0,
  `end_date` datetime(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of promo
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin');
INSERT INTO `role` VALUES (2, 'assistant');
INSERT INTO `role` VALUES (3, 'student');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `rid` int NULL DEFAULT NULL,
  `pid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 1, 3);
INSERT INTO `role_permission` VALUES (4, 2, 1);
INSERT INTO `role_permission` VALUES (5, 2, 2);
INSERT INTO `role_permission` VALUES (6, 3, 3);

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `current_value` int NOT NULL DEFAULT 0,
  `step` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sequence_info
-- ----------------------------
INSERT INTO `sequence_info` VALUES ('order_info', 84245, 1);

-- ----------------------------
-- Table structure for stock_log
-- ----------------------------
DROP TABLE IF EXISTS `stock_log`;
CREATE TABLE `stock_log`  (
  `stock_log_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `item_id` int NOT NULL DEFAULT 0,
  `amount` int NOT NULL DEFAULT 0,
  `status` int NOT NULL DEFAULT 0 COMMENT '//1表示初始状态，2表示下单扣减库存成功，3表示下单回滚',
  PRIMARY KEY (`stock_log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock_log
-- ----------------------------
INSERT INTO `stock_log` VALUES ('1dbfc2ee02b5424d90f5afde14ddce6a', 8, 1, 2);
INSERT INTO `stock_log` VALUES ('308c51883bb24d73b2da1f1dfe0037b9', 8, 1, 3);
INSERT INTO `stock_log` VALUES ('4d0624c8211445d89cd7983455bc62d5', 8, 1, 2);
INSERT INTO `stock_log` VALUES ('93cb2773d9cc427ca7bcf93341445aa6', 8, 4, 2);
INSERT INTO `stock_log` VALUES ('97c0347a336647b59357284b4ad39706', 8, 1, 2);
INSERT INTO `stock_log` VALUES ('afdb25a54d924f468381d594c08f6a13', 8, 5, 2);
INSERT INTO `stock_log` VALUES ('b8f5229c9d0f41668df4fcdb10fbf491', 8, 1, 2);
INSERT INTO `stock_log` VALUES ('c4394c927c3a4b419b55b6d3130cdc97', 8, 1, 2);
INSERT INTO `stock_log` VALUES ('ec4f8aaf913c47e49ee7f63f1ce98fcb', 8, 4, 2);
INSERT INTO `stock_log` VALUES ('ee97247dda334cafbed0e43211579599', 9, 4, 2);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `gender` tinyint NOT NULL DEFAULT 0 COMMENT '//1代表男性，2代表女性',
  `age` int NOT NULL DEFAULT 0,
  `telphone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `register_mode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '//byphone,bywechat,byalipay',
  `third_party_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `telphone_unique_index`(`telphone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, '第一个用户', 1, 30, '13521234859', 'byphone', '');
INSERT INTO `user_info` VALUES (15, 'teambition', 1, 20, '1312345678', 'byphone', '');
INSERT INTO `user_info` VALUES (20, '82030', 1, 1, '11111122', 'byphone', '');
INSERT INTO `user_info` VALUES (21, 'hzl', 1, 31, '13671573214', 'byphone', '');
INSERT INTO `user_info` VALUES (22, 'testuser', 1, 20, '13562514273', 'byphone', '');
INSERT INTO `user_info` VALUES (23, 'json', 1, 1, 'admin', 'byPhone', '');
INSERT INTO `user_info` VALUES (24, '牛聪聪', 1, 25, '15620285161', 'byPhone', '');
INSERT INTO `user_info` VALUES (28, '怡宝', 2, 21, '18931011545', 'byPhone', '');
INSERT INTO `user_info` VALUES (29, '怡宝', 2, 21, '18817203701', 'byPhone', '');

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `user_id` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES (1, 'ddlsjfjfjfjlf', 1);
INSERT INTO `user_password` VALUES (9, '4QrcOUm6Wau+VuBX8g+IPg==', 15);
INSERT INTO `user_password` VALUES (11, 'xMpCOKC5I4INzFCab3WEmw==', 20);
INSERT INTO `user_password` VALUES (12, '4QrcOUm6Wau+VuBX8g+IPg==', 21);
INSERT INTO `user_password` VALUES (13, '4QrcOUm6Wau+VuBX8g+IPg==', 22);
INSERT INTO `user_password` VALUES (14, '21232f297a57a5a743894a0e4a801fc3', 23);
INSERT INTO `user_password` VALUES (15, '5c20141cc5975b54e6c2d0fe3bc1cad1', 24);
INSERT INTO `user_password` VALUES (16, 'dc71f87eb8ae2c8f5383deee78ca6d56', 28);
INSERT INTO `user_password` VALUES (17, '205a116aa036f47eaa7a25bf65906b39', 29);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NULL DEFAULT NULL,
  `rid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 2, 2);
INSERT INTO `user_role` VALUES (3, 3, 3);

SET FOREIGN_KEY_CHECKS = 1;
