package com.exploration.filter.impl;

import com.exploration.filter.LogEntryFilter;
import com.exploration.model.LogEntry;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatusCode200FilterTest {
    @Test
    public void shouldReturnTrueIfStatusCode200() {
        //given
        LogEntry logEntry = mock(LogEntry.class);
        LogEntryFilter filter = new StatusCode200Filter();
        //when
        when(logEntry.getStatusCode()).thenReturn(200);
        boolean result = filter.isValid(logEntry);
        //then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseIfStatusCodeNot200() {
        //given
        LogEntry logEntry = mock(LogEntry.class);
        LogEntryFilter filter = new StatusCode200Filter();
        //when
        when(logEntry.getStatusCode()).thenReturn(300);
        boolean result = filter.isValid(logEntry);
        //then
        assertThat(result).isFalse();
    }
}
