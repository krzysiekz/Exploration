package com.exploration.collector;

import com.exploration.model.LogEntry;
import com.exploration.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserCollector {

    public List<User> collect(List<LogEntry> logEntries) {
        List<User> users = new ArrayList<>();
        for (LogEntry logEntry : logEntries) {
            if(!containsUser(users, logEntry.getUser())) {
                users.add(new User(logEntry.getUser()));
            }
        }
        return users;
    }

    private boolean containsUser(List<User> users, String user) {
        for (User u : users) {
            if(u.getName().equals(user)) {
                return true;
            }
        }
        return false;
    }
}
