package com.exploration.session;

import com.exploration.model.LogEntry;
import com.exploration.model.Session;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionAttributesSetterTest {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Test
    public void shouldRemoveSessionsWithOneElement() {
        //given
        List<Session> sessions = new ArrayList<>();
        Session session = new Session("user:1");
        session.addEntry(new LogEntry());
        sessions.add(session);
        SessionAttributesSetter attributesSetter = new SessionAttributesSetter();
        //when
        List<Session> sessionsReturned = attributesSetter.populate(sessions);
        //then
        assertThat(sessionsReturned).isEmpty();
    }

    @Test
    public void shouldSetSessionTime() throws ParseException {
        //given
        List<Session> sessions = new ArrayList<>();
        Session session = new Session("user:1");
        session.addEntry(createLogEntry("2015-04-10 10:00:00"));
        session.addEntry(createLogEntry("2015-04-10 10:00:15"));
        sessions.add(session);
        SessionAttributesSetter attributesSetter = new SessionAttributesSetter();
        //when
        List<Session> sessionsReturned = attributesSetter.populate(sessions);
        //then
        assertThat(sessionsReturned).hasSize(1);
        assertThat(sessionsReturned.get(0).getTime()).isEqualTo(15);
    }

    private LogEntry createLogEntry(String time) throws ParseException {
        LogEntry logEntry = new LogEntry();
        logEntry.setTime(format.parse(time));
        return logEntry;
    }
}
