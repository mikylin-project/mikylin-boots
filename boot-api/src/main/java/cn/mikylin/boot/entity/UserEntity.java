package cn.mikylin.boot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserEntity implements Serializable {

    private Long userId; // 用户 id
    private String userName; // 用户名
    private String password; // 密码
    private Integer status; // 状态
    private Integer age; // 年龄
    private Integer sex; // 性别
    private String notes; // 用户备注

    @Override
    public String toString() {
        return "user_id : " + userId + " user_name : " + userName;
    }
}
