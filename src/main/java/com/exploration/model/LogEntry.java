package com.exploration.model;

import java.util.Date;

public class LogEntry {
    private Date time;
    private HTTPMethod method;
    private String path;
    private String protocol;
    private int statusCode;
    private String user;

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setMethod(HTTPMethod method) {
        this.method = method;
    }

    public HTTPMethod getMethod() {
        return method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return  statusCode;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }
}