package com.exploration.filter.impl;

import com.exploration.filter.LogEntryFilter;
import com.exploration.model.LogEntry;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GraphicalFilesFilterTest {
    @Test
    public void shouldReturnFalseIfPathContainsGraphicalExt() {
        //given
        LogEntry logEntry = mock(LogEntry.class);
        LogEntryFilter filter = new GraphicalFilesFilter();
        //when
        when(logEntry.getPath()).thenReturn("/bu/bu.jpg");
        boolean result = filter.isValid(logEntry);
        //then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueIfPathDoesNotContainGraphicalExt() {
        //given
        LogEntry logEntry = mock(LogEntry.class);
        LogEntryFilter filter = new GraphicalFilesFilter();
        //when
        when(logEntry.getPath()).thenReturn("/bu/");
        boolean result = filter.isValid(logEntry);
        //then
        assertThat(result).isTrue();
    }

}
