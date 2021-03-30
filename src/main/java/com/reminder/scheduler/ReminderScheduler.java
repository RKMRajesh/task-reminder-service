package com.reminder.scheduler;

import com.reminder.model.Reminder;
import com.reminder.model.ReminderStatus;
import com.reminder.repository.ReminderRepository;
import com.reminder.service.ReminderEmailService;
import com.reminder.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReminderScheduler {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private ReminderEmailService reminderEmailService;

    private static final Logger LOG = LoggerFactory.getLogger(ReminderScheduler.class);

    @Scheduled(cron = "${reminder.scheduler.cron}")
    public void sendReminderEmail() {
        LOG.info("Reminder scheduler initiated...");
        Iterable<Reminder> reminders = reminderRepository.findAll();
        reminders.forEach(this::sendEmail);
        LOG.info("Reminder scheduler completed...");
    }

    public void sendEmail(Reminder reminder) {
        LocalDateTime reminderDate = DateUtil.getLocalDateTimeFromEpochMilli(reminder.getDueDate());
        LOG.info("Reminder date found: {}", reminderDate);
        LocalDateTime currentDate = LocalDateTime.now();
        LOG.info("Current date: {}", currentDate);
//        reminderDate.minusMinutes(30);
        //if (currentDate.plusMinutes(31).isAfter(reminderDate) && reminderDate.minusMinutes(29).isAfter(currentDate)) { // plusMinutes(30).equals(currentDate) isAfter(currentDate)) {
        if (reminderDate.isBefore(currentDate.plusMinutes(5)) && reminder.getStatus().name().equals("PENDING")) {
            reminderEmailService.sendMail(reminder);
            reminder.setStatus(ReminderStatus.COMPLETED);
            reminderRepository.save(reminder); //this will update the reminder's status
            LOG.info("Reminder sent to user: {} with email: {}", reminder.getUser(), reminder.getEmail());
        } else {
            LOG.info("No reminders to be sent out");
        }

    }


}
