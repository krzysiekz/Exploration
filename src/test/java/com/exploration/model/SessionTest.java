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
}