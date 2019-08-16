package cn.mikylin.boots.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserEntity {

    private Long id;
    private String name;
    private Integer age;
    private String notes;
    private Date createtime;
    private Date updatetime;

}