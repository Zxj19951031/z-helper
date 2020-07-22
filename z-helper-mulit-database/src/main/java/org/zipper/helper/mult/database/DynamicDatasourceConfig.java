package org.zipper.helper.mult.database;



import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 基于ali druid的多数据源配置
 *
 * @author zhuxj
 */
@Component
@ConfigurationProperties(prefix = "spring")
public class DynamicDatasourceConfig {

    @NestedConfigurationProperty
    private final Map<Object, Object> datasource = new HashMap<>();

    public void setDatasource(Map<String, Properties> properties) {
        properties.forEach((k, prop) -> {
            Properties p = new Properties();
            prop.forEach((key, value) -> p.put("druid." + key, value));
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.configFromPropety(p);
            this.datasource.put(k, druidDataSource);
            DynamicDataSourceContextHolder.dataSourceIds.add(k);
        });
    }

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     *
     * @return DataSource
     * @see DataSource
     */
    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(datasource.get("master"));
        dynamicDataSource.setTargetDataSources(datasource);
        dynamicDataSource.afterPropertiesSet();
        return dynamicDataSource;
    }

    /**
     * 配置@Transactional注解事物
     *
     * @return PlatformTransactionManager
     */
    @Bean(name = "dynamicTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
