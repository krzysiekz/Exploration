package com.exploration.collector;

import com.exploration.model.LogEntry;
import com.exploration.model.Session;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionCollectorTest {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @Test
    public void shouldCollectSessionsProperly() throws ParseException {
        //given
        int sessionOffsetInSeconds = 30 * 60;
        List<LogEntry> logEntries = new ArrayList<>();
        logEntries.add(createLogEntry("user1", "2015-04-10 10:00"));
        logEntries.add(createLogEntry("user1", "2015-04-10 10:05"));
        logEntries.add(createLogEntry("user1", "2015-04-10 10:36"));
        logEntries.add(createLogEntry("user2", "2015-04-10 10:36"));
        SessionCollector sessionCollector = new SessionCollector(sessionOffsetInSeconds);
        //when
        List<Session> sessions = sessionCollector.collect(logEntries);
        //then
        assertThat(sessions).hasSize(3);
        assertThat(sessions.get(0).getId()).isEqualTo("user1:1");
        assertThat(sessions.get(0).getLogEntries()).hasSize(2);
        assertThat(sessions.get(1).getId()).isEqualTo("user1:2");
        assertThat(sessions.get(1).getLogEntries()).hasSize(1);
        assertThat(sessions.get(2).getId()).isEqualTo("user2:1");
        assertThat(sessions.get(2).getLogEntries()).hasSize(1);
    }

    @Test
    public void shouldCollectSessionsProperlyForUnsortedSessions() throws ParseException {
        //given
        int sessionOffsetInSeconds = 30 * 60;
        List<LogEntry> logEntries = new ArrayList<>();
        logEntries.add(createLogEntry("user1", "2015-04-10 10:00"));
        logEntries.add(createLogEntry("user1", "2015-04-10 10:05"));
        logEntries.add(createLogEntry("user2", "2015-04-10 10:36"));
        logEntries.add(createLogEntry("user1", "2015-04-10 10:36"));
        SessionCollector sessionCollector = new SessionCollector(sessionOffsetInSeconds);
        //when
        List<Session> sessions = sessionCollector.collect(logEntries);
        //then
        assertThat(sessions).hasSize(3);
        assertThat(sessions.get(0).getId()).isEqualTo("user1:1");
        assertThat(sessions.get(0).getLogEntries()).hasSize(2);
        assertThat(sessions.get(1).getId()).isEqualTo("user1:2");
        assertThat(sessions.get(1).getLogEntries()).hasSize(1);
        assertThat(sessions.get(2).getId()).isEqualTo("user2:1");
        assertThat(sessions.get(2).getLogEntries()).hasSize(1);
    }

    private LogEntry createLogEntry(String user, String time) throws ParseException {
        LogEntry logEntry = new LogEntry();
        logEntry.setUser(user);
        logEntry.setTime(format.parse(time));
        return logEntry;
    }
}