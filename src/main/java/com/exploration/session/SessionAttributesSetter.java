package com.exploration.session;

import com.exploration.model.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SessionAttributesSetter {
    public List<Session> populate(List<Session> sessions, Map<String, Integer> mostPopular) {
        removeSessionsWithOneEntry(sessions);
        setSessionTime(sessions);
        setFlagsForPaths(sessions, mostPopular);
        return sessions;
    }

    private void setFlagsForPaths(List<Session> sessions, Map<String, Integer> mostPopular) {
        for (Session session : sessions) {
            for (String path : mostPopular.keySet()) {
                session.setFlag(path);
            }
        }
    }

    private void setSessionTime(List<Session> sessions) {
        for (Session session : sessions) {
            long timeDifference = getDateDiff(session.getLogEntries().get(0).getTime(),
                    session.getLogEntries().get(session.getLogEntries().size()-1).getTime(),
                    TimeUnit.SECONDS);
            session.setTime(timeDifference);
        }
    }

    private void removeSessionsWithOneEntry(List<Session> sessions) {
        List<Session> toRemove = new ArrayList<>();
        for (Session session : sessions) {
            if(session.getLogEntries().size()==1) {
                toRemove.add(session);
            }
        }
        sessions.removeAll(toRemove);
    }

    public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMilliseconds = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
    }
}
