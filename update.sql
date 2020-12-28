use dmall;
drop table if exists `web_log`;
create table `web_log`(
                          `id` bigint auto_increment comment '主键id',
                          `ip` varchar(64) not null comment 'ip',
                          `ip_address` varchar(64) comment 'ip地址解析',
                          `url` varchar(256) not null comment '请求路径',
                          `log_name` varchar(256) not null comment '操作日志名称',
                          `create_by` varchar(50) not null comment '操作人',
                          `create_time` datetime default current_timestamp comment '日志创建时间',
                          `update_time` datetime default current_timestamp comment '日志更新时间',
                          `request_args` json comment '请求参数',
                          `response` json comment '响应参数',
                          `status` tinyint not null default 0 comment '日志状态，0-正常，1-删除',
                          `exception` text comment '异常信息',
                          `time_consuming` int(8) not null comment '请求耗时',
                          primary key `web_log_id`(`id`)
)engine = innodb charset = utf8 comment '接口请求日志记录表';

alter table `web_log` add `exception` text comment '异常信息';
alter table `web_log` modify `request_args` json comment '请求参数';
alter table `web_log` modify `response` json comment '响应参数';
alter table `web_log` add `url` varchar(256) not null comment '请求路径';
alter table `web_log` add `time_consuming` int(8) not null comment '请求耗时';
create index `idx_log_name_create_time_ip` on `web_log`(`log_name`,`create_time`,`ip`);