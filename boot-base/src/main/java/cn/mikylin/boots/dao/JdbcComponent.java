package cn.mikylin.boots.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * spring jdbc 简单封装
 */
@Component
@Slf4j
public class JdbcComponent {

    /**
     * 使用 ？ 通配符的 jdbc
     * 用于持久化一个数组
     */
    @Autowired
    JdbcTemplate template;

    /**
     * 可以使用 :xxxx 的通配符的 jdbc
     * 用于直接持久化一个 java entity
     */
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 查询一条数据
     */
    public <T> T findOne(String sql, List params, RowMapper<T> mapper){
        try {
            return template.queryForObject(sql,mapper,params.toArray());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * 查询列表数据
     */
    public <T> List<T> findList(String sql, List params, RowMapper<T> mapper){
        try {
            return template.query(sql,mapper,params.toArray());
        } catch (Exception e) {
            log.error("findList with param exception: e:{}",e);
            return null;
        }
    }

    /**
     * 查询列表数据
     */
    public <T> List<T> findList(String sql, RowMapper<T> mapper){
        try {
            return template.query(sql,mapper);
        } catch (Exception e) {
            log.error("findList exception: e:{}",e);
            return null;
        }
    }

    /**
     * 更新数据
     */
    public Integer update(String sql, List params){
        try {
            return template.update(sql, params.toArray());
        } catch (DataAccessException e) {
            log.error("update exception: e:{}",e);
        }
        return 0;
    }

    /**
     * 新增数据
     */
    public <T> Long insert(String sql,T t){
        // 用于获取新增的数据的 key
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            namedParameterJdbcTemplate.update(sql,new BeanPropertySqlParameterSource(t),keyHolder);
            Number key = keyHolder.getKey();
            if(key != null)
                return key.longValue();
        } catch (DataAccessException e) {
            log.error("insert exception: e:{}",e);
        }
        return null;
    }



}
