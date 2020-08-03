create table if not exists tb_auth_member
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    sex         int          null,
    mobile      varchar(11)  null,
    create_time datetime     null,
    update_time datetime     null,
    status      int          null
);

create table if not exists tb_auth_menu
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    regex_uri   varchar(255) null,
    pid         int          not null,
    level       int          null,
    sort        int          not null,
    code        varchar(255) null,
    metadata    text         null,
    is_func     tinyint(1)   null,
    full_name   varchar(255) null,
    create_time datetime     null,
    update_time datetime     null,
    status      int          null
)
    comment '权限菜单' charset = utf8;

create table if not exists tb_auth_organization
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    sort        int          null,
    pid         int          null,
    code        varchar(255) null,
    create_time datetime     null,
    update_time datetime     null,
    status      int          null
);

create table if not exists tb_auth_relation_org_member
(
    org_id   int null,
    memberId int null
);

create table if not exists tb_auth_relation_org_member_role
(
    org_id    int null,
    member_id int null,
    role_id   int null
);

create table if not exists tb_auth_relation_role_menu
(
    role_id int null,
    menu_id int null
);

create table if not exists tb_auth_role
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    `desc`      varchar(255) null,
    level       int          null,
    sort        int          null,
    create_time datetime     null,
    update_time datetime     null,
    status      int          null
);

