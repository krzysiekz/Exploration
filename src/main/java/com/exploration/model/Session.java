package com.exploration.model;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private String id;
    private List<LogEntry> logEntries;
    private long time;

    public Session(String id) {
        this.id = id;
        logEntries = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<LogEntry> getLogEntries() {
        return logEntries;
    }

    public void addEntry(LogEntry logEntry) {
        logEntries.add(logEntry);
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Integer getNumberOfActions() {
        return logEntries.size();
    }
}
