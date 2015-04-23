package com.exploration.stats;

import com.exploration.model.LogEntry;

import java.util.*;

public class PathStatsCalculator {
    private Map<String, Integer> results;
    private Integer totalCount;

    public PathStatsCalculator() {
        results = new LinkedHashMap<>();
        totalCount = 0;
    }

    public void calculate(List<LogEntry> logEntries) {
        for (LogEntry logEntry : logEntries) {
            if(results.containsKey(logEntry.getPath())) {
                results.put(logEntry.getPath(), results.get(logEntry.getPath())+1);
            } else {
                results.put(logEntry.getPath(), 1);
            }
            totalCount++;
        }
        results = new LinkedHashMap<>(sortByValues(results));
    }

    public Map<String, Integer> getResults() {
        return results;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    private Map<String, Integer> sortByValues(final Map<String, Integer> map) {
        Comparator<String> valueComparator =  new Comparator<String>() {
            public int compare(String s1, String s2) {
                int compare = map.get(s2).compareTo(map.get(s1));
                if (compare == 0) return 1;
                else return compare;
            }
        };
        Map<String, Integer> sortedByValues = new TreeMap<>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    public Map<String, Integer> getMostPopular(double percent) {
        Map<String, Integer> resultsMostPopular =  new LinkedHashMap<>();
        for (String path : results.keySet()) {
            if(((double)results.get(path))/(double)totalCount * 100.0 > percent) {
                resultsMostPopular.put(path, results.get(path));
            }
        }
        return resultsMostPopular;
    }
}