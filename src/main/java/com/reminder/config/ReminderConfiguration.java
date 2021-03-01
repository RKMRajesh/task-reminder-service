package com.reminder.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

@EnableJpaRepositories("com.reminder.repository")
@EntityScan("com.reminder.model")
@Configuration
public class ReminderConfiguration {

//    @Bean(name = "entityManagerFactory")
//    public LocalSessionFactoryBean sessionFactory() {
//        return new LocalSessionFactoryBean();
//    }
//
//    @Bean
//    public DataSource datasource() {
//        return DataSourceBuilder.create()
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .url("jdbc:mysql://localhost:3306/testdb")
//                .username("root")
//                .password("root")
//                .build();
//    }
}
