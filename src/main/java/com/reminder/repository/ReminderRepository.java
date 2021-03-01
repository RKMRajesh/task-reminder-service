package com.reminder.repository;

import com.reminder.model.Reminder;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Id;
import java.util.Optional;

public interface ReminderRepository extends CrudRepository<Reminder, Integer> { // by extending CrudReposiroty Spring will create a class and implement all the methods we tell it to
                                                                                // we just need to declare the CRUD methods we want like below

    @Override
    Reminder save(Reminder entity); // will save into DB

    @Override
    Optional<Reminder> findById(Integer id);

    @Override
    void deleteById(Integer id);

    @Override
    Iterable<Reminder> findAll();
}
