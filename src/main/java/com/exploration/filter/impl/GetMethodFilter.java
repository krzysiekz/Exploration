package com.exploration.filter.impl;

import com.exploration.filter.LogEntryFilter;
import com.exploration.model.HTTPMethod;
import com.exploration.model.LogEntry;

public class GetMethodFilter implements LogEntryFilter {
    @Override
    public boolean isValid(LogEntry logEntry) {
        return logEntry.getMethod().equals(HTTPMethod.GET);
    }
}
