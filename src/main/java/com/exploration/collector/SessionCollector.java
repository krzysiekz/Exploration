package com.exploration.collector;

import com.exploration.model.LogEntry;
import com.exploration.model.Session;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SessionCollector {
    private static final String ID_PATTERN = "{0}:{1}";
    private final int sessionOffsetInSeconds;

    public SessionCollector(int sessionOffsetInSeconds) {
        this.sessionOffsetInSeconds = sessionOffsetInSeconds;
    }

    public List<Session> collect(List<LogEntry> logEntries) {
        logEntries = sortLogEntries(logEntries);
        List<Session> sessions = new ArrayList<>();
        LogEntry previousLogEntry = new LogEntry();
        int currentId = 1;
        Session currentSession = null;
        for (LogEntry logEntry : logEntries) {
            if(!logEntry.getUser().equals(previousLogEntry.getUser())) {
                currentId = 1;
                currentSession = createNewSession(sessions, currentId, logEntry);
                currentId++;
            } else if(isNewSession(previousLogEntry, logEntry)) {
                currentSession = createNewSession(sessions, currentId, logEntry);
                currentId++;
            }
            currentSession.addEntry(logEntry);
            previousLogEntry = logEntry;
        }
        return sessions;
    }

    private List<LogEntry> sortLogEntries(final List<LogEntry> logEntries) {
        Collections.sort(logEntries, new Comparator<LogEntry>() {
            @Override
            public int compare(LogEntry logEntry, LogEntry secondLogEntry) {
                return new CompareToBuilder()
                        .append(logEntry.getUser(), secondLogEntry.getUser())
                        .append(logEntry.getTime(), secondLogEntry.getTime())
                        .toComparison();
            }
        });
        return logEntries;
    }

    private Session createNewSession(List<Session> sessions, int currentId, LogEntry logEntry) {
        Session currentSession = new Session(MessageFormat.format(ID_PATTERN, logEntry.getUser(), currentId));
        sessions.add(currentSession);
        return currentSession;
    }

    private boolean isNewSession(LogEntry previousLogEntry, LogEntry logEntry) {
        return previousLogEntry.getTime() != null &&
                    logEntry.getTime().getTime() - previousLogEntry.getTime().getTime() > 1000 * sessionOffsetInSeconds;
    }
}