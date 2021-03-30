package com.reminder.service;

import com.reminder.model.Reminder;
import com.reminder.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import sun.rmi.runtime.Log;

public class ReminderEmailService {

    @Autowired
    private MailSender mailSender;

    private static final Logger LOG = LoggerFactory.getLogger(ReminderEmailService.class);

    public void sendMail(Reminder reminder) {
        String emailMessage = String.format("Dear %s, we would like to remind you about your task: %s - %s with due date: %s",
                reminder.getUser(),
                reminder.getTaskName(),
                reminder.getDescription(),
                DateUtil.getLocalDateTimeFromEpochMilli(reminder.getDueDate()));


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(reminder.getEmail());
        message.setText(emailMessage);
        message.setFrom("${spring.mail.username}");
        message.setSubject("Reminder Email About Your Task from Rajesh's Hotel Inc.");

        try {
            this.mailSender.send(message);
        }
        catch (MailException ex) {
            LOG.error(ex.getMessage());
        }
    }

}
