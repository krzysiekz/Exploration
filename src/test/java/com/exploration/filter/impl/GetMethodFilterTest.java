package com.exploration.filter.impl;

import com.exploration.filter.LogEntryFilter;
import com.exploration.model.HTTPMethod;
import com.exploration.model.LogEntry;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetMethodFilterTest {
    @Test
    public void shouldReturnFalseIfMethodNotGet() {
        //given
        LogEntry logEntry = mock(LogEntry.class);
        LogEntryFilter filter = new GetMethodFilter();
        //when
        when(logEntry.getMethod()).thenReturn(HTTPMethod.POST);
        boolean result = filter.isValid(logEntry);
        //then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueIfMethodGet() {
        //given
        LogEntry logEntry = mock(LogEntry.class);
        LogEntryFilter filter = new GetMethodFilter();
        //when
        when(logEntry.getMethod()).thenReturn(HTTPMethod.GET);
        boolean result = filter.isValid(logEntry);
        //then
        assertThat(result).isTrue();
    }
}
