package cn.mikylin.boots.dao;

import cn.mikylin.boots.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
public class UserDao {

    @Autowired
    JdbcComponent component;

    public List<UserEntity> findUsers(){
        String sql = "select * from user ";
        return component.findList(sql,new FindUserMapper());
    }


    class FindUserMapper implements RowMapper<UserEntity> {
        @Override
        public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserEntity entity = new UserEntity();
            entity.setId(rs.getLong("id"));
            entity.setAge(rs.getInt("age"));
            entity.setName(rs.getString("name"));
            entity.setNotes(rs.getString("notes"));
            entity.setCreatetime(rs.getDate("createtime"));
            return entity;
        }
    }


    public Long insert(UserEntity entity){
        String sql = "insert into user(name,age,notes,createtime) values (:name,:age,:notes,:createtime) ";
        return component.insert(sql,entity);
    }





}
