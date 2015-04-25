package com.exploration.output.impl.impl;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import com.exploration.model.Session;
import com.exploration.model.User;
import com.exploration.output.impl.ArffGenerator;

public class ArffGeneratorImpl implements ArffGenerator {
    @Override
    public void generate(OutputStream outputStream, List<Session> sessions) {
        if(!sessions.isEmpty()) {
            final PrintStream printStream = new PrintStream(outputStream);
            writeArffHeader(printStream, sessions);
            writeData(printStream, sessions);
        }
    }
    
    @Override
	public void generateUserFiles(OutputStream outputStream, List<User> users) {
    	if(!users.isEmpty()) {
            final PrintStream printStream = new PrintStream(outputStream);
            writeArffHeaderForUserFiles(printStream, users);
            writeUserData(printStream, users);
        }
		
	}

    private void writeData(PrintStream printStream, List<Session> sessions) {
        for (Session session : sessions) {
            printStream.println(session.toString());
        }
    }
    
    private void writeUserData(PrintStream printStream, List<User> users) {
        for (User user: users) {
            printStream.println(user.toString());
        }
    }

    private void writeArffHeader(PrintStream printWriter, List<Session> sessions) {
        printWriter.println("@relation clark_net_sessions");
        printWriter.println();
        printWriter.println("@attribute 'time' numeric");
        printWriter.println("@attribute 'number_of_actions' numeric");
        printWriter.println("@attribute 'average_time_for_action' numeric");
        Map<String, Boolean> mostPopular = sessions.get(0).getFlagsForPaths();
        for (String path : mostPopular.keySet()) {
            printWriter.printf("@attribute '%s' {true, false}", path);
            printWriter.println();
        }
        printWriter.println();
        printWriter.println("@data");
    }
    
    private void writeArffHeaderForUserFiles(PrintStream printWriter, List<User> users) {
    	 printWriter.println("@relation clark_net_users");
         printWriter.println();
         printWriter.println("@attribute 'user' string");
         Map<String, Boolean> mostPopularPagesVisited = users.get(0).getVisitedPages();
         for (String path : mostPopularPagesVisited.keySet()) {
             printWriter.printf("@attribute '%s' {true, false}", path);
             printWriter.println();
         }
         printWriter.println();
         printWriter.println("@data");
    }

	
}
