package com.exploration.session;

import com.exploration.model.LogEntry;
import com.exploration.model.Session;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionAttributesSetterTest {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Test
    public void shouldRemoveSessionsWithOneElement() {
        //given
        Map<String, Integer> mostPopular = new LinkedHashMap<>();
        List<Session> sessions = new ArrayList<>();
        Session session = new Session("user:1");
        session.addEntry(new LogEntry());
        sessions.add(session);
        SessionAttributesSetter attributesSetter = new SessionAttributesSetter();
        //when
        List<Session> sessionsReturned = attributesSetter.populate(sessions, mostPopular);
        //then
        assertThat(sessionsReturned).isEmpty();
    }

    @Test
    public void shouldSetSessionTime() throws ParseException {
        //given
        Map<String, Integer> mostPopular = new LinkedHashMap<>();
        List<Session> sessions = new ArrayList<>();
        Session session = new Session("user:1");
        session.addEntry(createLogEntry("2015-04-10 10:00:00", "a.htm"));
        session.addEntry(createLogEntry("2015-04-10 10:00:15", "a.htm"));
        sessions.add(session);
        SessionAttributesSetter attributesSetter = new SessionAttributesSetter();
        //when
        List<Session> sessionsReturned = attributesSetter.populate(sessions, mostPopular);
        //then
        assertThat(sessionsReturned).hasSize(1);
        assertThat(sessionsReturned.get(0).getTime()).isEqualTo(15);
    }

    @Test
    public void shouldSetFlagsForPaths() throws ParseException {
        //given
        Map<String, Integer> mostPopular = new LinkedHashMap<>();
        mostPopular.put("a.htm", 5);
        mostPopular.put("b.htm", 4);
        mostPopular.put("c.htm", 2);
        List<Session> sessions = new ArrayList<>();
        Session session = new Session("user:1");
        session.addEntry(createLogEntry("2015-04-10 10:00:00", "a.htm"));
        session.addEntry(createLogEntry("2015-04-10 10:00:15", "b.htm"));
        sessions.add(session);
        SessionAttributesSetter attributesSetter = new SessionAttributesSetter();
        //when
        List<Session> sessionsReturned = attributesSetter.populate(sessions, mostPopular);
        //then
        assertThat(sessionsReturned).hasSize(1);
        assertThat(sessionsReturned.get(0).getFlagsForPaths()).hasSize(3);
        assertThat(sessionsReturned.get(0).getFlagsForPaths().get("a.htm")).isTrue();
        assertThat(sessionsReturned.get(0).getFlagsForPaths().get("b.htm")).isTrue();
        assertThat(sessionsReturned.get(0).getFlagsForPaths().get("c.htm")).isFalse();
    }

    private LogEntry createLogEntry(String time, String path) throws ParseException {
        LogEntry logEntry = new LogEntry();
        logEntry.setPath(path);
        logEntry.setTime(format.parse(time));
        return logEntry;
    }
}
