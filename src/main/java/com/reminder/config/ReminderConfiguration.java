package com.reminder.config;

import com.reminder.scheduler.ReminderScheduler;
import com.reminder.service.ReminderEmailService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@EnableJpaRepositories("com.reminder.repository")
@EntityScan("com.reminder.model")
@EnableScheduling
@Configuration
public class ReminderConfiguration {

    @Bean("reminderScheduler")
    public ReminderScheduler reminderScheduler() {
        return new ReminderScheduler();
    }

    @Bean
    public ReminderEmailService reminderEmailService() {
        return new ReminderEmailService();
    }


}
