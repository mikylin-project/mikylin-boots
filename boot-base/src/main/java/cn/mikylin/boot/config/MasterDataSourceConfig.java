package cn.mikylin.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 主数据库 mybatis 配置
 */
@MapperScan(basePackages = "cn.mikylin.boot.dao.master", sqlSessionFactoryRef = "masterSqlSessionFactory")
@Configuration
public class MasterDataSourceConfig {

    @Value("${spring.datasource.master-url}")
    String url;
    @Value("${spring.datasource.master-username}")
    String user;
    @Value("${spring.datasource.master-password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driverClass;
    @Value("${spring.datasource.initial-size}")
    Integer initialSize;
    @Value("${spring.datasource.min-idle}")
    Integer minIdle;
    @Value("${spring.datasource.max-active}")
    Integer maxActive;
    @Value("${spring.datasource.max-wait}")
    Integer maxWait;
    @Value("${spring.datasource.filters}")
    String filters;


    @Bean(name = "masterDataSource")
    public DataSource ds() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(driverClass);
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setInitialSize(initialSize);
        ds.setMinIdle(minIdle);
        ds.setMaxActive(maxActive);
        ds.setMaxWait(maxWait);
        try {
            ds.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Bean(name = "masterTransactionManager")
    public DataSourceTransactionManager tm(@Qualifier("masterDataSource") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory sf(@Qualifier("masterDataSource") DataSource ds) throws Exception {
        SqlSessionFactoryBean sf = new SqlSessionFactoryBean();
        sf.setDataSource(ds);
        return sf.getObject();
    }

}
