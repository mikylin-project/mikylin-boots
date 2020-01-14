package cn.mikylin.boot.entity;

import lombok.Data;

@Data
public class UserEntity {

    private Long userId; // 用户 id
    private String userName; // 用户名
    private String password; // 密码
    private Integer age; // 年龄
    private Integer sex; // 性别
    private String notes; // 用户备注
}
