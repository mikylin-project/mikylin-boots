package cn.mikylin.boot.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

/**
 * Shardingsphere 数据库 mybatis 配置
 */
@MapperScan(basePackages = "cn.mikylin.boot.dao.shardingsphere",
        sqlSessionFactoryRef = "shardingSqlSessionFactory")
@Configuration
@ConditionalOnProperty(name = "names",prefix = "spring.shardingsphere.datasource")
public class ShardingsphereDataSourceConfig {

    @Bean(name = "shardingSqlSessionFactory")
    public SqlSessionFactory sf(@Qualifier("shardingDataSource") DataSource ds) throws Exception {
        SqlSessionFactoryBean sf = new SqlSessionFactoryBean();
        sf.setDataSource(ds);
        return sf.getObject();
    }
}
