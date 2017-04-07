package com.peachyy.logback;

import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.peachyy.logback.encoder.RabbitMQencoder;
import com.peachyy.logback.factory.RabbitMqFactory;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/6.</p>
 */
public abstract class RabbitMQBase<T> extends UnsynchronizedAppenderBase<T> {

    protected RabbitMQencoder encoder = null;

    protected String host = "localhost";
    protected int port = 5672;
    protected String username = "guest";
    protected String password = "guest";
    protected String virtual = "/";
    RabbitMqFactory rabbitMqFactory = null;

    public RabbitMQBase() {

    }

    @Override
    public void start() {
        super.start();
        createRabbitMqFactory();
    }

    @Override
    public void stop() {
        super.stop();
    }

    public RabbitMqFactory createRabbitMqFactory() {
        if (rabbitMqFactory == null) {
            synchronized (this) {
                if (rabbitMqFactory == null) {
                    rabbitMqFactory = new RabbitMqFactory(
                            host, port, username, password, virtual
                    );
                }
            }
        }
        return rabbitMqFactory;
    }

    public void setEncoder(RabbitMQencoder encoder) {
        this.encoder = encoder;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVirtual(String virtual) {
        this.virtual = virtual;
    }

}
