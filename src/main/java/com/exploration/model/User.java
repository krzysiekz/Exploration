package com.exploration.model;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class User {

	private static final String TO_STRING_PATTERN = "{0},{1}";
	private String name;
	private Map<String, Boolean> visitedPages;

	public User(String name) {
		this.name = name;
		this.visitedPages = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public Map<String, Boolean> getVisitedPages() {
		return visitedPages;
	}

	public void setVisitedPages(Map<String, Boolean> visitedPages) {
		this.visitedPages = visitedPages;
	}

	@Override
	public String toString() {
		return MessageFormat.format(TO_STRING_PATTERN, name,
				StringUtils.join(visitedPages.values(), ','));
	}
}
