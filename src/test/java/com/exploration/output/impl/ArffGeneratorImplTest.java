package com.exploration.output.impl;

import com.exploration.model.LogEntry;
import com.exploration.model.Session;
import com.exploration.output.impl.impl.ArffGeneratorImpl;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArffGeneratorImplTest {

    @Test
    public void shouldGenerateProperContent() throws UnsupportedEncodingException {
        //given
        System.setProperty("line.separator", "\r\n");
        String expected = prepareExpected();
        List<Session> sessions = new ArrayList<>();
        Session session = new Session("user:1");
        session.setTime(20);
        session.addEntry(createLogEntry("a.htm"));
        session.addEntry(createLogEntry("c.htm"));
        session.setFlag("a.htm");
        session.setFlag("b.htm");
        session.setFlag("c.htm");
        sessions.add(session);
        OutputStream outputStream = new ByteArrayOutputStream();
        ArffGenerator arffGenerator = new ArffGeneratorImpl();
        //when
        arffGenerator.generate(outputStream, sessions);
        //then
        String result = ((ByteArrayOutputStream)outputStream).toString("UTF-8");
        assertThat(result).isEqualTo(expected);

    }

    private LogEntry createLogEntry(String path) {
        LogEntry logEntry = new LogEntry();
        logEntry.setPath(path);
        return logEntry;
    }

    private String prepareExpected() {
        String expected = "@relation clark_net_sessions\r\n\r\n"
                + "@attribute 'time' numeric\r\n"
                + "@attribute 'number_of_actions' numeric\r\n"
                + "@attribute 'average_time_for_action' numeric\r\n"
                + "@attribute 'a.htm' {true, false}\r\n"
                + "@attribute 'b.htm' {true, false}\r\n"
                + "@attribute 'c.htm' {true, false}\r\n\r\n"
                + "@data\r\n"
                + "20,2,10,true,false,true\r\n";
        return expected;
    }
}
