package com.peachyy.logback.jmx;

public class DefaultLogbackRabbit implements LogbackRabbitmqMBean {
    private String host;
    private int port;
    private String username;
    private String virtual;

    @Override
    public String rabbitmqHost() {
        return host;
    }

    @Override
    public int rabbitPort() {
        return port;
    }

    @Override
    public String rabbitmqUser() {
        return username;
    }

    @Override
    public String rabbitVhost() {
        return virtual;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setVirtual(String virtual) {
        this.virtual = virtual;
    }
}