package com.peachyy.logback.utils;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/10.</p>
 */
public class RabitInfos {


    protected String host = "localhost";
    protected int port = 5672;
    protected String username = "guest";
    protected String password = "guest";
    protected String virtual = "/";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVirtual() {
        return virtual;
    }

    public void setVirtual(String virtual) {
        this.virtual = virtual;
    }
}
