package com.exploration.collector;

import com.exploration.filter.FilterChecker;
import com.exploration.logreader.LogReader;
import com.exploration.model.LogEntry;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class LogEntriesCollector {
    private final LogReader reader;
    private final FilterChecker checker;

    public LogEntriesCollector(LogReader reader, FilterChecker checker) {
        this.reader = reader;
        this.checker = checker;
    }

    public List<LogEntry> collect() throws IOException, ParseException {
        List<LogEntry> logEntries = new ArrayList<>();
        LogEntry entry;
        while((entry = reader.read()) != null) {
            if(checker.check(entry)) {
                logEntries.add(entry);
            }
        }
        return logEntries;
    }
}
