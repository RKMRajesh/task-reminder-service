package com.reminder.controller;

import com.reminder.model.Reminder;
import com.reminder.repository.ReminderRepository;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reminder-service/v1")
public class ReminderController {

    private static final Logger LOG = LoggerFactory.getLogger(ReminderController.class);

    // we want to use the CRUD methods from the interface ReminderRepository. But, since we cannot instantiate an interace, we will use the @Autowired annotation
    // to have Spring create an instance of the class it had created earlier when he were creating the ReminderRepositroy interface. That way we can use the CRUD methods
    @Autowired //instance will be created by framework and will be passed/wired here. this is nothing but dependency injection/inversion of control
    private ReminderRepository reminderRepository;

    @PostMapping("/reminders")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Reminder> createReminder(@Valid @RequestBody Reminder reminder) {
        LOG.info("Received request to create Reminder:{}", reminder);
        Reminder newReminder = reminderRepository.save(reminder);
        LOG.info("A new Reminder object is saved.");
        ResponseEntity<Reminder> responseEntity = new ResponseEntity<>(newReminder, HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping("/reminders/{id}")
    public ResponseEntity<Reminder> getReminderById(@PathVariable("id") Integer id) {
        LOG.info("Received request to get Reminder with ID:{}", id);
        Optional<Reminder> resultReminder = reminderRepository.findById(id);
        LOG.info("Received Reminder with ID:{}", id);
        ResponseEntity<Reminder> responseEntity = resultReminder.isPresent() ?
                new ResponseEntity<>(resultReminder.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @GetMapping("/reminders")
    public ResponseEntity<Iterable<Reminder>> getReminders() {
        LOG.info("Received request to get all Reminders");
        Iterable<Reminder> reminders = reminderRepository.findAll();
        LOG.info("Retreived all Reminders from DB");
        return new ResponseEntity<>(reminders, HttpStatus.OK);
    }

    @PutMapping("/reminders/{id}")
    public ResponseEntity<Reminder> updateReminder(@PathVariable("id") Integer id, @RequestBody Reminder reminder) {
        LOG.info("Received request to update Reminder with ID:{}", id);
        reminder.setId(id);
        Reminder updatedReminder = reminderRepository.save(reminder);
        LOG.info("Reminder object is updated.");
        ResponseEntity<Reminder> responseEntity = new ResponseEntity<>(updatedReminder, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("/reminders/{id}")
    public ResponseEntity<Reminder> deleteReminder(@PathVariable("id") Integer id) {
        LOG.info("Received request to delete Reminder with ID:{}", id);
        reminderRepository.deleteById(id);
        LOG.info("Reminder object with ID {} deleted.", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
