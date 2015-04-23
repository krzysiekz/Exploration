package com.exploration.filter.impl;

import com.exploration.filter.LogEntryFilter;
import com.exploration.model.LogEntry;

public class GraphicalFilesFilter implements LogEntryFilter {

    private static final String IMAGE_PATTERN =
            "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

    @Override
    public boolean isValid(LogEntry logEntry) {
        return !logEntry.getPath().matches(IMAGE_PATTERN);
    }
}
