package com.exploration.output.impl;

import java.io.OutputStream;
import java.util.List;

import com.exploration.model.Session;
import com.exploration.model.User;

public interface ArffGenerator {
    void generate(OutputStream outputStream, List<Session> sessions);
    
    void generateUserFiles(OutputStream outputStream, List<User> users);
}
