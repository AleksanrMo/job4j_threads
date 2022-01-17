package ru.job4j.concurrent.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(() ->
           send(String.format("Notification %s to email %s.", user.getUserName(), user.getEmail()),
                   String.format("Add a new event to %s.", user.getEmail()), user.getEmail()));
        close();
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {

    }


}