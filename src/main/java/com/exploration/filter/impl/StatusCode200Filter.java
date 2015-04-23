package com.exploration.filter.impl;

import com.exploration.filter.LogEntryFilter;
import com.exploration.model.LogEntry;

public class StatusCode200Filter implements LogEntryFilter {
    @Override
    public boolean isValid(LogEntry logEntry) {
        return logEntry.getStatusCode() == 200;
    }
}
