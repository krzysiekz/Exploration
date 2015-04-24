package com.exploration.output.impl;

import com.exploration.model.Session;

import java.io.OutputStream;
import java.util.List;

public interface ArffGenerator {
    void generate(OutputStream outputStream, List<Session> sessions);
}
