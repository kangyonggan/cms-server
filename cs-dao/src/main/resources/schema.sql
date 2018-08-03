DROP DATABASE IF EXISTS cs;

CREATE DATABASE cs
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

USE cs;


-- ----------------------------
--  Table structure for tb_user
-- ----------------------------
DROP TABLE
IF EXISTS tb_user;

CREATE TABLE tb_user
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  username     VARCHAR(32)                           NOT NULL
  COMMENT '用户名',
  name         VARCHAR(32)                           NOT NULL
  COMMENT '姓名',
  password     VARCHAR(64)                           NOT NULL
  COMMENT '密码',
  salt         VARCHAR(64)                           NOT NULL
  COMMENT '密码盐',
  avatar       VARCHAR(256)                          NOT NULL                    DEFAULT ''
  COMMENT '头像',
  status       TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '状态:{0:可用, 1:禁用}',
  created_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '用户表';
CREATE UNIQUE INDEX username_UNIQUE
  ON tb_user (username);

-- ----------------------------
--  Table structure for tb_role
-- ----------------------------
DROP TABLE
IF EXISTS tb_role;

CREATE TABLE tb_role
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '角色代码',
  name         VARCHAR(32)                           NOT NULL
  COMMENT '角色名称',
  status       TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '状态:{0:可用, 1:禁用}',
  created_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '角色表';
CREATE UNIQUE INDEX code_UNIQUE
  ON tb_role (code);

-- ----------------------------
--  Table structure for tb_menu
-- ----------------------------
DROP TABLE
IF EXISTS tb_menu;

CREATE TABLE tb_menu
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '菜单代码',
  pcode        VARCHAR(32)                           NOT NULL                    DEFAULT ''
  COMMENT '父菜单代码',
  sort         INT(11)                               NOT NULL                    DEFAULT 0
  COMMENT '菜单排序(从0开始)',
  icon         VARCHAR(128)                          NOT NULL                    DEFAULT ''
  COMMENT '菜单图标的样式',
  status       TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '状态:{0:可用, 1:禁用}',
  created_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '菜单表';
CREATE INDEX sort_ix
  ON tb_menu (sort);
CREATE UNIQUE INDEX code_UNIQUE
  ON tb_menu (code);

-- ----------------------------
--  Table structure for tb_user_role
-- ----------------------------
DROP TABLE
IF EXISTS tb_user_role;

CREATE TABLE tb_user_role
(
  username  VARCHAR(32) NOT NULL
  COMMENT '用户名',
  role_code VARCHAR(32) NOT NULL
  COMMENT '角色代码',
  PRIMARY KEY (username, role_code)
)
  COMMENT '用户角色表';

-- ----------------------------
--  Table structure for rtb_ole_menu
-- ----------------------------
DROP TABLE
IF EXISTS tb_role_menu;

CREATE TABLE tb_role_menu
(
  role_code VARCHAR(32) NOT NULL
  COMMENT '角色代码',
  menu_code VARCHAR(32) NOT NULL
  COMMENT '菜单代码',
  PRIMARY KEY (role_code, menu_code)
)
  COMMENT '角色菜单表';


-- ----------------------------
--  data for tb_user
-- ----------------------------
INSERT INTO tb_user
(username, name, password, salt)
VALUES
  ('admin', '管理员', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f'),
  ('guest', '客官', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f'),
  ('xiaotiao', '小跳', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f'),
  ('xiaoxin', '小新', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f'),
  ('xiaoxin2', '小新2', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f'),
  ('xiaoxin3', '小新3', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f'),
  ('xiaoxin4', '小新4', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f'),
  ('xiaoxin5', '小新5', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f'),
  ('xiaoxin6', '小新6', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f'),
  ('xiaoxin7', '小新7', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f'),
  ('xiaoxin8', '小新8', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f'),
  ('xiaoxin9', '小新9', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f'),
  ('xiaoxin10', '小新10', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f'),
  ('xiaoxin11', '小新11', 'e592a7cd1ea88a2abb369efe7fd7e3fb5f854030', 'bedabd281e33df9f');

INSERT INTO tb_role
(code, name)
VALUES
  ('ROLE_ADMIN', '管理员'),
  ('ROLE_USER', '普通用户');

INSERT INTO tb_user_role
(username, role_code)
VALUES
  ('admin', 'ROLE_ADMIN'),
  ('guest', 'ROLE_ADMIN');

INSERT INTO tb_menu (code, pcode, icon, sort)
VALUES
  ('system', '', 'gear-b', 0),
  ('user', 'system', '', 0),
  ('role', 'system', '', 1),
  ('menu', 'system', '', 2),
  ('person', '', 'person', 99),
  ('info', 'person', '', 0),
  ('article', 'person', '', 1),
  ('content', '', 'laptop', 1),
  ('articleCheck', 'content', '', 0),
  ('novel', 'content', '', 0);

INSERT INTO tb_role_menu (role_code, menu_code)
  SELECT
    'ROLE_ADMIN',
    code
  FROM tb_menu;
