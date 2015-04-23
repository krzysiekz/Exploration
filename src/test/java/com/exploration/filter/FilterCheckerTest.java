package com.exploration.filter;

import com.exploration.filter.impl.GetMethodFilter;
import com.exploration.filter.impl.GraphicalFilesFilter;
import com.exploration.filter.impl.StatusCode200Filter;
import com.exploration.model.HTTPMethod;
import com.exploration.model.LogEntry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterCheckerTest {

    @Test
    public void shouldReturnFalseForInvalidEntry() {
        //given
        List<LogEntryFilter> filters = getLogEntryFilters();
        LogEntry logEntry = new LogEntry();
        logEntry.setStatusCode(300);
        logEntry.setPath("/");
        logEntry.setMethod(HTTPMethod.GET);
        logEntry.setTime(new Date());
        logEntry.setUser("5.5.5.5");
        FilterChecker checker = new FilterChecker(filters);
        //when
        boolean result = checker.check(logEntry);
        //then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForValidEntry() {
        //given
        List<LogEntryFilter> filters = getLogEntryFilters();
        LogEntry logEntry = new LogEntry();
        logEntry.setStatusCode(200);
        logEntry.setPath("/");
        logEntry.setMethod(HTTPMethod.GET);
        logEntry.setTime(new Date());
        logEntry.setUser("5.5.5.5");
        FilterChecker checker = new FilterChecker(filters);
        //when
        boolean result = checker.check(logEntry);
        //then
        assertThat(result).isTrue();
    }

    private List<LogEntryFilter> getLogEntryFilters() {
        List<LogEntryFilter> filters = new ArrayList<>();
        filters.add(new GetMethodFilter());
        filters.add(new GraphicalFilesFilter());
        filters.add(new StatusCode200Filter());
        return filters;
    }
}