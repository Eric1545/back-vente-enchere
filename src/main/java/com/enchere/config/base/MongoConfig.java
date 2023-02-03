package com.enchere.config.base;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//@Configuration
//@EnableTransactionManagement
////@PropertySource({"classpath:application.properties"})
//@EnableJpaRepositories(
//        basePackages = "com.enchere.mongo.repos",
//        entityManagerFactoryRef = "mongoEntityManagerFactory",
//        transactionManagerRef = "mongoTransactionManager")
public class MongoConfig {
    /*@Autowired
    private Environment env;

    @Bean(name = "mongoDatasource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mongodb.jdbc.MongoDriver");
        dataSource.setUrl("jdbc:mongodb://containers-us-west-125.railway.app:7555");
        dataSource.setSchema("enchere");
        dataSource.setUsername("mongo");
        dataSource.setPassword("ClIdcQNJCHBXLx6Td3nJ");
//        DataSourceBuilder<?> dataSource = DataSourceBuilder.create();
//        dataSource.driverClassName("com.mongodb.jdbc.MongoDriver");
//        dataSource.url("jdbc:mongodb://containers-us-west-125.railway.app:7555");
//        dataSource.username("mongo");
//        dataSource.password("ClIdcQNJCHBXLx6Td3nJ");
//        dataSource.
//        dataSource
//        dataSource.setUrl(env.getProperty("postgresql://postgres:muWcYv3UjOvmQyqrcSWu@containers-us-west-102.railway.app:6347/railway"));
//        dataSource.setUsername(env.getProperty(""));
//        dataSource.setPassword(env.getProperty("jdbc.pass"));

//        return dataSource.build();
        return dataSource;
    }

    @Bean(name = "mongoEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("mongoDatasource") DataSource dataSource) {
        Map<String, String> property = new HashMap<>();
        property.put("database", "enchere");
        return
                builder
                        .dataSource(dataSource)
                        .packages("com.enchere.mongo.models")
                        .persistenceUnit("Mongo")
                        .properties(property)
                        .build();
    }
    @Bean(name = "mongoTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("mongoEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }*/
}
