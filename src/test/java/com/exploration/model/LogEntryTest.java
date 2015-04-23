package com.exploration.model;

import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class LogEntryTest {

    @Test
    public void shouldHaveTimeSet() {
        //given
        LogEntry logEntry = new LogEntry();
        Date date = new Date();
        //when
        logEntry.setTime(date);
        //then
        assertThat(logEntry.getTime()).isEqualTo(date);
    }

    @Test
    public void shouldHaveMethodSet() {
        //given
        LogEntry logEntry = new LogEntry();
        HTTPMethod method = HTTPMethod.GET;
        //when
        logEntry.setMethod(method);
        //then
        assertThat(logEntry.getMethod()).isEqualTo(method);
    }

    @Test
    public void shouldHavePathSet() {
        //given
        LogEntry logEntry = new LogEntry();
        String path = "/";
        //when
        logEntry.setPath(path);
        //then
        assertThat(logEntry.getPath()).isEqualTo(path);
    }

    @Test
    public void shouldHaveProtocolSet() {
        //given
        LogEntry logEntry = new LogEntry();
        String protocol = "HTTP/1.0";
        //when
        logEntry.setProtocol(protocol);
        //then
        assertThat(logEntry.getProtocol()).isEqualTo(protocol);
    }

    @Test
    public void shouldHaveStatusCodeSet() {
        //given
        LogEntry logEntry = new LogEntry();
        int statusCode = 200;
        //when
        logEntry.setStatusCode(statusCode);
        //then
        assertThat(logEntry.getStatusCode()).isEqualTo(statusCode);
    }

    @Test
    public void shouldHaveUserSet() {
        //given
        LogEntry logEntry = new LogEntry();
        String user = "5.5.5.5";
        //when
        logEntry.setUser(user);
        //then
        assertThat(logEntry.getUser()).isEqualTo(user);
    }
}
