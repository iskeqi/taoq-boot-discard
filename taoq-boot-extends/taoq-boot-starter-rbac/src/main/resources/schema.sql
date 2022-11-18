create database if not exists `taoq-boot` default charset utf8mb4 collate utf8mb4_0900_ai_ci;

create table if not exists `sys_role`  (
  `id` bigint unsigned not null auto_increment comment '主键id',
  `name` varchar(32)  default null comment '角色名称',
  `permiss` varchar(32)   default null comment '权限标识',
  `type` char(1)  default null comment '角色类型[N:内置 Z:自定义]',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  primary key (`id`)
) engine = innodb character set = utf8mb4 collate = utf8mb4_0900_ai_ci comment = '角色表';

create table if not exists `sys_menu`  (
  `id` bigint unsigned not null auto_increment comment '主键id',
  `name` varchar(32)  default null comment '名称',
  `url` varchar(256)  default null comment '请求url地址',
  `icon` varchar(32)  default null comment 'icon图标',
  `type` char(1)  default null comment '菜单类型[C:目录 M:菜单 B:按钮]',
  `permiss` varchar(256)  default null comment '权限标识',
  `parent_id` bigint null default null comment '父级id[根节点parent_id为0]',
  `order_num` int null default null comment '排序字段',
  `func_type` char(1)  default 'N' comment '菜单功能类型[N:内置 Z:自定义]',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  primary key (`id`)
) engine = innodb character set = utf8mb4 collate = utf8mb4_0900_ai_ci comment = '菜单权限表';

create table if not exists `sys_role_menu`  (
  `role_id` bigint unsigned not null comment '角色id',
  `menu_id` bigint unsigned not null comment '菜单id',
  primary key (`role_id`, `menu_id`)
) engine = innodb character set = utf8mb4 collate = utf8mb4_0900_ai_ci comment = '角色-菜单关联表';

create table if not exists `sys_account_role`  (
  `account` varchar(32)  not null comment '用户唯一标识符',
  `role_id` bigint unsigned not null comment '角色id',
  primary key (`account`, `role_id`)
) engine = innodb character set = utf8mb4 collate = utf8mb4_0900_ai_ci comment = '用户-角色关联表';

create table `sys_func_field` (
  `id` bigint unsigned not null auto_increment comment '主键id',
  `name` varchar(32) default null comment '字段名称',
  `code` varchar(32)  default null comment '字段编码[小写驼峰]',
  `echo_name` varchar(32)  default null comment '回显字段编码[小写驼峰]',
  `menu_id` bigint unsigned default null comment '菜单id',
  `create` tinyint default '1' comment '是否可新增[false:不可以 true:可以]',
  `update` tinyint default '1' comment '是否可编辑[false:不可以 true:可以]',
  `show` tinyint default '1' comment '是否可展示[false:不可以 true:可以]',
  `multiple` tinyint default '0' comment '是否多选[false:不可以 true:可以]',
  `query_type` varchar(64)  default null comment '查询类型[使用英文逗号分隔,为null时不支持查询]',
  `type` varchar(32)  default null comment '字段类型[INPUT:文本 NUMBER:数值 DATE:日期 DATETIME:日期时间 TIME:时间 DICT:字典 HTTP:接口]',
  `dict_type_code` varchar(32) default null comment '字典类型编码',
  `request_param` json default null comment '请求参数json字符串\r\n示例：\r\n{\r\n    "menuId": "56",\r\n    "code": "stationId",\r\n    "param": {\r\n        "mapId": "${mapId}"\r\n    }\r\n}',
  `order_num` int default null comment '排序字段',
  `deleted` tinyint unsigned default '0' comment '是否删除[false:未删除 true:已删除]',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '修改时间',
  primary key (`id`),
  unique key `uk_menu_id_code` (`menu_id`,`code`) using btree
) engine = innodb character set = utf8mb4 collate = utf8mb4_0900_ai_ci comment = '功能字段表';
