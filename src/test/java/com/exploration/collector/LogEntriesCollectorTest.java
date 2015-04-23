package com.exploration.collector;

import com.exploration.filter.FilterChecker;
import com.exploration.logreader.LogReader;
import com.exploration.model.LogEntry;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogEntriesCollectorTest {

    private LogEntry entry;
    private LogEntry entry2;
    private LogReader reader;
    private FilterChecker checker;
    private LogEntriesCollector collector;

    @Before
    public void setUp() {
        //given
        entry = mock(LogEntry.class);
        entry2 = mock(LogEntry.class);
        reader = mock(LogReader.class);
        checker = mock(FilterChecker.class);
        collector = new LogEntriesCollector(reader, checker);
    }

    @Test
    public void shouldCollectProperEntries() throws IOException, ParseException {
        //when
        when(reader.read()).thenReturn(entry, entry2, null);
        when(checker.check(entry)).thenReturn(true);
        when(checker.check(entry2)).thenReturn(true);
        List<LogEntry> entries = collector.collect();
        //then
        assertThat(entries).hasSize(2);
        assertThat(entries).containsOnly(entry, entry2);
    }

    @Test
    public void shouldCollectProperEntriesForInvalidEntry() throws IOException, ParseException {
        //when
        when(reader.read()).thenReturn(entry, entry2, null);
        when(checker.check(entry)).thenReturn(true);
        when(checker.check(entry2)).thenReturn(false);
        List<LogEntry> entries = collector.collect();
        //then
        assertThat(entries).hasSize(1);
        assertThat(entries).containsOnly(entry);
    }
}
