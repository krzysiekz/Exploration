package com.exploration.model;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Session {
    private static final String TO_STRING_PATTERN = "{0,number,#},{1,number,#},{2,number,#},{3}";
    private String id;
    private List<LogEntry> logEntries;
    private long time;
    private Map<String, Boolean> flagsForPaths;

    public Session(String id) {
        this.id = id;
        logEntries = new ArrayList<>();
        flagsForPaths = new LinkedHashMap<>();
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

    public long getAverageTimeForAction() {
        return time / getNumberOfActions();
    }

    public Map<String, Boolean> getFlagsForPaths() {
        return flagsForPaths;
    }

    private boolean containsPath(String path) {
        for (LogEntry logEntry : logEntries) {
            if(logEntry.getPath().equals(path)) {
                return true;
            }
        }
        return false;
    }

    public void setFlag(String path) {
        flagsForPaths.put(path, containsPath(path));
    }

    @Override
    public String toString() {
        return MessageFormat.format(TO_STRING_PATTERN,
                time, getNumberOfActions(), getAverageTimeForAction(),
                StringUtils.join(flagsForPaths.values(), ','));
    }
}
