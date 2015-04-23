package com.exploration.collector;

import com.exploration.model.LogEntry;
import com.exploration.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserCollectorTest {
    @Test
    public void shouldCollectUsers() {
        //given
        LogEntry entry = mock(LogEntry.class);
        LogEntry entry2 = mock(LogEntry.class);
        LogEntry entry3 = mock(LogEntry.class);
        List<LogEntry> logEntries = new ArrayList<>();
        logEntries.add(entry);
        logEntries.add(entry2);
        logEntries.add(entry3);
        UserCollector userCollector = new UserCollector();
        //when
        when(entry.getUser()).thenReturn("User1");
        when(entry2.getUser()).thenReturn("User1");
        when(entry3.getUser()).thenReturn("User2");
        List<User> users = userCollector.collect(logEntries);
        //then
        assertThat(users).hasSize(2);
        assertThat(users.get(0).getName()).isEqualTo("User1");
        assertThat(users.get(1).getName()).isEqualTo("User2");
    }
}
