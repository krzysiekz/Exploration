package com.exploration.filter;

import com.exploration.model.LogEntry;

import java.util.List;

public class FilterChecker {
    private final List<LogEntryFilter> filters;

    public FilterChecker(List<LogEntryFilter> filters) {
        this.filters = filters;
    }

    public boolean check(LogEntry logEntry) {
        for (LogEntryFilter filter : filters) {
            if(!filter.isValid(logEntry)) {
                return false;
            }
        }
        return true;
    }
}
