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
}