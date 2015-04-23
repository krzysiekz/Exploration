package com.exploration.logreader;

import com.exploration.model.LogEntry;

import java.io.IOException;
import java.text.ParseException;

public interface LogReader {
    LogEntry read() throws IOException, ParseException;
}
