package com.exploration.logreader;

import com.exploration.model.HTTPMethod;
import com.exploration.model.LogEntry;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class LogReaderImplTest {

    @Test
    public void shouldReturnProperLogEntries() throws ParseException, IOException {
        //given
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        String log = "204.249.225.59 - - [28/Aug/1995:00:00:34 -0400] \"GET /pub/rmharris/catalogs/dawsocat/intro.html HTTP/1.0\" 200 3542";
        InputStream inputStream = new ByteArrayInputStream(log.getBytes());
        LogReader reader = new LogReaderImpl(inputStream);
        //when
        LogEntry logEntry = reader.read();
        //then
        assertThat(logEntry).isNotNull();
        assertThat(logEntry.getMethod()).isEqualTo(HTTPMethod.GET);
        assertThat(logEntry.getPath()).isEqualTo("/pub/rmharris/catalogs/dawsocat/intro.html");
        assertThat(logEntry.getProtocol()).isEqualTo("HTTP/1.0");
        assertThat(logEntry.getStatusCode()).isEqualTo(200);
        assertThat(logEntry.getTime()).isEqualTo(format.parse("28/Aug/1995:00:00:34 -0400"));
    }

    @Test
    public void shouldReturnProperLogEntriesForDomainName() throws ParseException, IOException {
        //given
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        String log = "access9.accsyst.com - - [28/Aug/1995:00:00:35 -0400] \"GET /pub/robert/past99.gif HTTP/1.0\" 200 4993";
        InputStream inputStream = new ByteArrayInputStream(log.getBytes());
        LogReader reader = new LogReaderImpl(inputStream);
        //when
        LogEntry logEntry = reader.read();
        //then
        assertThat(logEntry).isNotNull();
        assertThat(logEntry.getMethod()).isEqualTo(HTTPMethod.GET);
        assertThat(logEntry.getPath()).isEqualTo("/pub/robert/past99.gif");
        assertThat(logEntry.getProtocol()).isEqualTo("HTTP/1.0");
        assertThat(logEntry.getStatusCode()).isEqualTo(200);
        assertThat(logEntry.getTime()).isEqualTo(format.parse("28/Aug/1995:00:00:35 -0400"));
    }

    @Test
    public void shouldReturnProperLogEntriesForDomainNameWithSpecialCharacter() throws ParseException, IOException {
        //given
        SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
        String log = "F180-152.net.wisc.edu - - [28/Aug/1995:00:06:41 -0400] \"GET /pub/job/vk/vendela.html HTTP/1.0\" 200 14961";
        InputStream inputStream = new ByteArrayInputStream(log.getBytes());
        LogReader reader = new LogReaderImpl(inputStream);
        //when
        LogEntry logEntry = reader.read();
        //then
        assertThat(logEntry).isNotNull();
        assertThat(logEntry.getMethod()).isEqualTo(HTTPMethod.GET);
        assertThat(logEntry.getPath()).isEqualTo("/pub/job/vk/vendela.html");
        assertThat(logEntry.getProtocol()).isEqualTo("HTTP/1.0");
        assertThat(logEntry.getStatusCode()).isEqualTo(200);
        assertThat(logEntry.getTime()).isEqualTo(format.parse("28/Aug/1995:00:06:41 -0400"));
    }
}