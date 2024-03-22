/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.100.100
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : 192.168.100.100:3306
 Source Schema         : chok

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 06/02/2024 22:58:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '盐值',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `real_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '真实姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '电话号码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态: \r\n0: 正常 \r\n1: 删除 ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '$2a$10$E0dUqSYjJnSsFosTaR0R2.S65kI1ROx5W/7ogV/yC3KlsG0F7Sn76', '564b7b66-0665-43fe-a9a5-4c667e35ac59', '', '', '13333333333', '', '2023-07-15 13:12:47', '2023-11-19 13:38:36', 0);

-- ----------------------------
-- Table structure for common_dict
-- ----------------------------
DROP TABLE IF EXISTS `common_dict`;
CREATE TABLE `common_dict`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NOT NULL COMMENT '父ID \r\n0: 顶层',
  `dict_type` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类型',
  `dict_label` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典标签',
  `dict_value` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典键值',
  `dict_sort` int NOT NULL DEFAULT 1 COMMENT '字典排序',
  `insert_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态: \r\n0: 正常 \r\n1: 删除 ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of common_dict
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` bigint UNSIGNED NOT NULL COMMENT '父ID; 0: 根节点',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名: \r\n路由命名规则: AaBbCc, \r\n按钮权限命名规则: a-b-btn, \r\n特殊权限命名规则: a:b:c',
  `title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图标名',
  `permission_type` tinyint NOT NULL COMMENT '权限类型: \r\n1: 页面 \r\n2: 按钮 \r\n3: 特殊权限 ',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路由路径: \r\n/开头为父路径 \r\n否则为子路径',
  `redirect` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路由重定向路径',
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路由组件路径: \"@views/\"对应数据库\"/\", 空串自动匹配',
  `affix` tinyint(1) NOT NULL DEFAULT 0 COMMENT '路由是否固定: \r\n0: 否 \r\n1: 是 ',
  `sort` int NOT NULL DEFAULT 1 COMMENT '排序asc',
  `hide` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏: \r\n0: 否 \r\n1: 是 ',
  `requires_auth` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否需要权限控制: \r\n0: 否; \r\n1: 是; ',
  `keep_alive` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否缓存页面: \r\n0: 否; \r\n1: 是; ',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态: \r\n0: 正常 \r\n1: 删除 ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '页面权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 0, 'dashboard', '仪表盘', 'mdi:monitor-dashboard', 1, '/dashboard', '', 'basic', 0, 1, 0, 1, 1, '2023-11-15 08:38:01', '2023-11-15 08:38:01', 0);
INSERT INTO `menu` VALUES (2, 1, 'dashboard_analysis', '分析页', 'icon-park-outline:analysis', 1, '/dashboard/analysis', '', 'self', 0, 1, 0, 1, 1, '2023-11-15 08:38:45', '2023-11-15 08:38:45', 0);
INSERT INTO `menu` VALUES (3, 0, 'system', '系统管理', 'ic:round-desktop-mac', 1, '/system', '', 'basic', 0, 1, 0, 1, 1, '2023-11-13 09:29:32', '2023-11-15 09:11:40', 0);
INSERT INTO `menu` VALUES (4, 3, 'system_menu', '菜单管理', 'ic:baseline-web-asset', 1, '/system/menu', '', 'self', 0, 1, 0, 1, 1, '2023-11-13 09:44:01', '2023-11-18 10:48:04', 0);
INSERT INTO `menu` VALUES (17, 0, 'aa', 'aaa', '', 1, '/aa', '', 'basic', 0, 1, 0, 1, 1, '2023-11-18 14:51:37', '2023-11-18 15:02:57', 0);
INSERT INTO `menu` VALUES (18, 17, 'aa_bbb', 'bbb', 'bb', 1, '/aa/bbb', '', 'self', 0, 1, 0, 1, 1, '2023-11-18 14:57:05', '2023-11-18 15:16:41', 0);
INSERT INTO `menu` VALUES (19, 17, 'aa_cc', 'cc', 'cc', 1, '/aa/cc', 'cc', 'self', 0, 1, 0, 1, 1, '2023-11-18 14:59:36', '2023-11-19 04:21:52', 0);

-- ----------------------------
-- Table structure for oauth2_registered_client
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_registered_client`;
CREATE TABLE `oauth2_registered_client`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端ID',
  `client_id_issued_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `client_secret` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端密钥',
  `client_secret_expires_at` datetime NULL DEFAULT NULL,
  `client_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端名称',
  `client_authentication_methods` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '认证方式, 多个,隔开: \r\nclient_secret_basic',
  `authorization_grant_types` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权方式, 多个,隔开: \r\n授权码: authorization_code',
  `redirect_uris` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回调地址, 多个,隔开',
  `scopes` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作用域, 多个,隔开',
  `client_settings` json NOT NULL,
  `token_settings` json NOT NULL,
  `post_logout_redirect_uris` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_client_id`(`client_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth2_registered_client
-- ----------------------------
INSERT INTO `oauth2_registered_client` VALUES (1, 'web-client', '2023-07-01 17:16:55', '{bcrypt}$2a$10$E0dUqSYjJnSsFosTaR0R2.S65kI1ROx5W/7ogV/yC3KlsG0F7Sn76', NULL, '03ef62a2-de94-4257-a99d-63b1d7e2a4c4', 'client_secret_basic', 'refresh_token,client_credentials,authorization_code', 'http://localhost:3200,https://cn.bing.com', 'pc.read,pc.write', '{\"settings.client.require-proof-key\": false, \"settings.client.require-authorization-consent\": true}', '{\"settings.token.access-token-format\": null, \"settings.token.reuse-refresh-tokens\": true, \"settings.token.access-token-time-to-live\": 60, \"settings.token.refresh-token-time-to-live\": 36000, \"settings.token.id-token-signature-algorithm\": \"RS256\", \"settings.token.authorization-code-time-to-live\": 300}', NULL);
INSERT INTO `oauth2_registered_client` VALUES (2, 'oidc-client', '2023-01-22 16:01:39', '{bcrypt}$2a$10$E0dUqSYjJnSsFosTaR0R2.S65kI1ROx5W/7ogV/yC3KlsG0F7Sn76', NULL, 'b4124c67-951e-4b99-b3a0-891ac39be945', 'client_secret_basic', 'refresh_token,client_credentials,authorization_code', 'http://127.0.0.1:8080/authorized,http://127.0.0.1:8080/login/oauth2/code/oidc-client-oidc,https://cn.bing.com', 'openid,client.create,client.read', '{\"settings.client.require-proof-key\": false, \"settings.client.require-authorization-consent\": true}', '{\"settings.token.access-token-format\": null, \"settings.token.reuse-refresh-tokens\": true, \"settings.token.access-token-time-to-live\": 60, \"settings.token.refresh-token-time-to-live\": 36000, \"settings.token.id-token-signature-algorithm\": \"RS256\", \"settings.token.authorization-code-time-to-live\": 300}', NULL);

-- ----------------------------
-- Table structure for rel_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `rel_admin_role`;
CREATE TABLE `rel_admin_role`  (
  `admin_id` bigint UNSIGNED NOT NULL,
  `role_id` bigint UNSIGNED NOT NULL,
  PRIMARY KEY (`admin_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rel_admin_role
-- ----------------------------
INSERT INTO `rel_admin_role` VALUES (1, 1);

-- ----------------------------
-- Table structure for rel_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `rel_role_menu`;
CREATE TABLE `rel_role_menu`  (
  `role_id` bigint UNSIGNED NOT NULL,
  `menu_id` bigint UNSIGNED NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色页面权限关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rel_role_menu
-- ----------------------------
INSERT INTO `rel_role_menu` VALUES (1, 1);
INSERT INTO `rel_role_menu` VALUES (1, 2);
INSERT INTO `rel_role_menu` VALUES (1, 3);
INSERT INTO `rel_role_menu` VALUES (1, 4);

-- ----------------------------
-- Table structure for rel_user_role
-- ----------------------------
DROP TABLE IF EXISTS `rel_user_role`;
CREATE TABLE `rel_user_role`  (
  `user_id` bigint UNSIGNED NOT NULL,
  `role_id` bigint UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rel_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `role_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态: \r\n0: 正常 \r\n1: 删除 ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ADMIN', '管理员', '系统管理员角色', '2023-07-15 13:13:25', '2023-07-15 13:13:25', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '盐值',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `real_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '真实姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '电话号码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除状态: \r\n0: 正常 \r\n1: 删除 ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
