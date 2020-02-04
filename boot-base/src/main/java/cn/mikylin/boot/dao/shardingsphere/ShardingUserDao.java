package cn.mikylin.boot.dao.shardingsphere;

import cn.mikylin.boot.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ShardingUserDao {

    /*
        drop table if exists user;
        create table user (
            `user_id` bigint(20) not null comment '用户 id', -- AUTO_INCREMENT
            `user_name` varchar(100) not null comment '用户名',
            `password` varchar(100) not null comment '密码',
            `age` int(3) default null comment '年龄',
            `sex` tinyint(1) not null default '2' comment '性别 0-男 1-女 2-未知',
            `notes` varchar(200) default null comment '备注',
            `status` smallint(2) default '0' comment '状态 0-正常 1-被删除',
            `create_time` timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
            `update_time` timestamp not null default CURRENT_TIMESTAMP on update
                                            CURRENT_TIMESTAMP comment '更新时间',
            PRIMARY KEY (`user_id`),
            KEY `username` (`user_name`,`status`)
        ) ENGINE=InnoDB default CHARSET=utf8 COMMENT='用户表';
     */

    @Select({"<script>",
            "select * from user a where user_id = #{user_id} limit 1 ",
            "</script>"})
    UserEntity selectById(@Param("user_id") Long userId);


    @Select({"<script>",
            "select count(*) from user ",
            "</script>"})
    Long selectCount();


    @Insert({"<script>",
            "insert into user ( ",
            "  `user_id`, ",
            "  `user_name`, ",
            "  `password`, ",
            "  `age`, ",
            "  `sex`, ",
            "  `notes`, ",
            "  `status` ",
            ") values ( ",
            "  #{userId}, ",
            "  #{userName}, ",
            "  #{password}, ",
            "  #{age}, ",
            "  #{sex}, ",
            "  #{notes}, ",
            "  #{status} ",
            ")",
            "</script>"})
    void insert(UserEntity e);
}
