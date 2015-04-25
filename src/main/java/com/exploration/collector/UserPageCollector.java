package com.exploration.collector;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.exploration.model.LogEntry;
import com.exploration.model.User;

public class UserPageCollector {
	
	Map<String, Integer> mostPopularPages;
	List<LogEntry> logs;
	List<User> users;
	
	public UserPageCollector(Map<String, Integer> mostPopularPages,
			List<LogEntry> logs) {
		super();
		this.mostPopularPages = mostPopularPages;
		this.logs = logs;
	}
	
	public void setVisitsOnMostPopularPagesForUsers(List<User> users) {
		for(User user: users) {
			user.setVisitedPages(collectVisitsForMostPopular(user));;
		}
	}

	private Map<String, Boolean> collectVisitsForMostPopular(User user) {
		Map<String, Boolean> visitedPagesMap = user.getVisitedPages();
		Iterator iter = mostPopularPages.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String, Boolean> entry = (Entry<String, Boolean>) iter.next();
			for(LogEntry log : logs){
				if(log.getUser().equals(user.getName())){
					if(log.getPath().equals(entry.getKey())){
						visitedPagesMap.put(entry.getKey(), true);
					} else {
						visitedPagesMap.put(entry.getKey(), false);
					}
				}
			}
		}
		return visitedPagesMap;
	}
}
