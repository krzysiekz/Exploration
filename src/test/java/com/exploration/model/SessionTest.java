package com.exploration.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest {

    @Test
    public void shouldReturnProperNumberOfActions() {
        //given
        Session session = new Session("user:1");
        session.addEntry(new LogEntry());
        session.addEntry(new LogEntry());
        //when
        Integer numberOfActions = session.getNumberOfActions();
        //then
        assertThat(numberOfActions).isEqualTo(numberOfActions);
    }

    @Test
    public void shouldReturnProperAverageTimeForAction() {
        //given
        Session session = new Session("user:1");
        session.setTime(15);
        session.addEntry(new LogEntry());
        session.addEntry(new LogEntry());
        //when
        long averageTimeForAction = session.getAverageTimeForAction();
        //then
        assertThat(averageTimeForAction).isEqualTo(7);
    }

    @Test
    public void shouldSetFlagForPathProperly() {
        //given
        Session session = new Session("user:1");
        LogEntry logEntry = new LogEntry();
        logEntry.setPath("a.htm");
        session.addEntry(logEntry);
        //when
        session.setFlag("a.htm");
        //then
        assertThat(session.getFlagsForPaths()).hasSize(1);
        assertThat(session.getFlagsForPaths().get("a.htm")).isTrue();
    }

    @Test
    public void shouldSetFlagForPathProperlyWHenPathDOesNotExist() {
        //given
        Session session = new Session("user:1");
        LogEntry logEntry = new LogEntry();
        logEntry.setPath("b.htm");
        session.addEntry(logEntry);
        //when
        session.setFlag("a.htm");
        //then
        assertThat(session.getFlagsForPaths()).hasSize(1);
        assertThat(session.getFlagsForPaths().get("a.htm")).isFalse();
    }

    @Test
    public void shouldReturnProperToString() {
        //given
        Session session = new Session("user:1");
        session.setTime(20);
        session.addEntry(createLogEntry("a.htm"));
        session.addEntry(createLogEntry("c.htm"));
        session.setFlag("a.htm");
        session.setFlag("b.htm");
        session.setFlag("c.htm");
        //when
        String toString = session.toString();
        //then
        assertThat(toString).isEqualTo("20,2,10,true,false,true");
    }

    @Test
    public void shouldReturnProperToStringForLongNumbers() {
        //given
        Session session = new Session("user:1");
        session.setTime(2000);
        session.addEntry(createLogEntry("a.htm"));
        session.addEntry(createLogEntry("c.htm"));
        session.setFlag("a.htm");
        session.setFlag("b.htm");
        session.setFlag("c.htm");
        //when
        String toString = session.toString();
        //then
        assertThat(toString).isEqualTo("2000,2,1000,true,false,true");
    }

    private LogEntry createLogEntry(String path) {
        LogEntry logEntry = new LogEntry();
        logEntry.setPath(path);
        return logEntry;
    }
}