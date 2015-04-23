package com.exploration.filter;

import com.exploration.model.LogEntry;

public interface LogEntryFilter {
    boolean isValid(LogEntry logEntry);
}
