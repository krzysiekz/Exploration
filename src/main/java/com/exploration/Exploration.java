package com.exploration;

import com.exploration.collector.LogEntriesCollector;
import com.exploration.collector.SessionCollector;
import com.exploration.collector.UserCollector;
import com.exploration.collector.UserPageCollector;
import com.exploration.filter.FilterChecker;
import com.exploration.filter.LogEntryFilter;
import com.exploration.filter.impl.GetMethodFilter;
import com.exploration.filter.impl.GraphicalFilesFilter;
import com.exploration.filter.impl.StatusCode200Filter;
import com.exploration.logreader.LogReader;
import com.exploration.logreader.LogReaderImpl;
import com.exploration.model.LogEntry;
import com.exploration.model.Session;
import com.exploration.model.User;
import com.exploration.output.impl.impl.ArffGeneratorImpl;
import com.exploration.session.SessionAttributesSetter;
import com.exploration.stats.PathStatsCalculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Exploration {

    private static final Logger LOGGER = LogManager.getLogger(Exploration.class);
    private static final String WRONG_NUMBER_OF_ARGUMENTS = "Wrong number of program arguments.";
    private static final String FILE_DOES_NOT_EXIST = "File {0} does not exist.";
    private static final String READ_LOG_ENTRIES = "Read {0} valid log entries.";
    private static final String IDENTIFIED_USERS = "Identified {0} users.";
    private static final String IDENTIFIED_PATHS = "Identified {0} paths.";
    private static final String IDENTIFIED_MOST_POPULAR_PATHS = "Identified {0} most popular paths.";
    private static final String IDENTIFIED_ALL_SESSIONS = "Identified {0} sessions.";
    private static final String IDENTIFIED_VALID_SESSIONS = "Identified {0} valid sessions.";
    private static final String POPULAR_PATH_STATS = "{0}, Views: {1}, Percent: {2}";
    private static final String SESSION_OUTPUT_FILE_SAVED = "Session output file saved to {0}";
    private static final String USER_OUTPUT_FILE_SAVED = "User output file saved to {0}";

    public static void main(String[] args) {
        if (args.length != 3) {
            LOGGER.error(WRONG_NUMBER_OF_ARGUMENTS);
            return;
        }
        File inputFile = new File(args[0]);
        if (!inputFile.exists()) {
            LOGGER.error(MessageFormat.format(FILE_DOES_NOT_EXIST, args[0]));
            return;
        }

        try (InputStream is = new FileInputStream(inputFile)) {
            LogReader logReader = new LogReaderImpl(is);
            List<LogEntryFilter> filters = getLogEntryFilters();
            FilterChecker filterChecker = new FilterChecker(filters);
            List<LogEntry> logEntries = new LogEntriesCollector(logReader, filterChecker).collect();
            LOGGER.info(MessageFormat.format(READ_LOG_ENTRIES, logEntries.size()));
            List<User> users = new UserCollector().collect(logEntries);
            LOGGER.info(MessageFormat.format(IDENTIFIED_USERS, users.size()));
            displayUsers(users);
            PathStatsCalculator pathStatsCalculator = new PathStatsCalculator();
            pathStatsCalculator.calculate(logEntries);
            Map<String, Integer> allPaths = pathStatsCalculator.getResults();
            LOGGER.info(MessageFormat.format(IDENTIFIED_PATHS, allPaths.size()));
            Map<String, Integer> mostPopularPaths = pathStatsCalculator.getMostPopular(0.5);
            LOGGER.info(MessageFormat.format(IDENTIFIED_MOST_POPULAR_PATHS, mostPopularPaths.size()));
            displayPaths(pathStatsCalculator, mostPopularPaths);
            List<Session> sessions = new SessionCollector(30 * 60).collect(logEntries);
            LOGGER.info(MessageFormat.format(IDENTIFIED_ALL_SESSIONS, sessions.size()));
            sessions = new SessionAttributesSetter().populate(sessions, mostPopularPaths);
            LOGGER.info(MessageFormat.format(IDENTIFIED_VALID_SESSIONS, sessions.size()));
			UserPageCollector upCollector = new UserPageCollector(mostPopularPaths, logEntries);
            upCollector.setVisitsOnMostPopularPagesForUsers(users);
            writeResultsToArffFile(args[1], sessions);
            writeResultsForUsersToArffFile(args[2], users);
        } catch (IOException | ParseException e) {
            LOGGER.error(e);
        }
    }

    private static void writeResultsToArffFile(String outputFileName, List<Session> sessions) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(outputFileName)) {
            new ArffGeneratorImpl().generate(fileOutputStream, sessions);
            LOGGER.info(MessageFormat.format(SESSION_OUTPUT_FILE_SAVED, outputFileName));
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
    
    private static void writeResultsForUsersToArffFile(String outputFileName, List<User> users) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(outputFileName)) {
            new ArffGeneratorImpl().generateUserFiles(fileOutputStream, users);
            LOGGER.info(MessageFormat.format(USER_OUTPUT_FILE_SAVED, outputFileName));
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    private static void displayPaths(PathStatsCalculator pathStatsCalculator, Map<String, Integer> mostPopularPaths) {
        for (String path : mostPopularPaths.keySet()) {
            LOGGER.info(MessageFormat.format(POPULAR_PATH_STATS,
                    path, mostPopularPaths.get(path),
                    ((double) mostPopularPaths.get(path)) / (double) pathStatsCalculator.getTotalCount() * 100.0));
        }
    }

    private static List<LogEntryFilter> getLogEntryFilters() {
        List<LogEntryFilter> filters = new ArrayList<>();
        filters.add(new GetMethodFilter());
        filters.add(new StatusCode200Filter());
        filters.add(new GraphicalFilesFilter());
        return filters;
    }

    private static void displayUsers(List<User> users) {
        for (User user : users) {
            LOGGER.info(user.getName());
        }
    }
}