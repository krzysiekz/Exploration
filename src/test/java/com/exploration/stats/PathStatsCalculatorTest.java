package com.exploration.stats;

import com.exploration.model.LogEntry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PathStatsCalculatorTest {
    @Test
    public void shouldCalculateStatisticsProperly() {
        //given
        List<LogEntry> logEntries = new ArrayList<>();
        logEntries.add(createLogEntry("a.htm"));
        logEntries.add(createLogEntry("/"));
        logEntries.add(createLogEntry("b.htm"));
        logEntries.add(createLogEntry("/"));
        PathStatsCalculator calculator = new PathStatsCalculator();
        //when
        calculator.calculate(logEntries);
        Map<String, Integer> results = calculator.getResults();
        //then
        assertThat(results).hasSize(3);
        assertThat(results.get("/")).isEqualTo(2);
        assertThat(results.get("a.htm")).isEqualTo(1);
        assertThat(results.get("b.htm")).isEqualTo(1);
        assertThat(calculator.getTotalCount()).isEqualTo(4);
    }

    @Test
    public void shouldReturnMostPopularPaths() {
        //given
        List<LogEntry> logEntries = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            logEntries.add(createLogEntry("a.htm"));
            logEntries.add(createLogEntry("b.htm"));
        }
        logEntries.add(createLogEntry("c.htm"));
        PathStatsCalculator calculator = new PathStatsCalculator();
        //when
        calculator.calculate(logEntries);
        double percent = 0.5;
        Map<String, Integer> results = calculator.getMostPopular(percent);
        //then
        assertThat(results).hasSize(2);
        assertThat(results.get("a.htm")).isEqualTo(200);
        assertThat(results.get("b.htm")).isEqualTo(200);
    }

    private LogEntry createLogEntry(String path) {
        LogEntry logEntry = new LogEntry();
        logEntry.setPath(path);
        return logEntry;
    }
}
