package com.peachyy.logback;

import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.peachyy.logback.encoder.RabbitMQencoder;
import com.peachyy.logback.jmx.DefaultLogbackRabbit;
import com.peachyy.logback.queue.QueueHold;
import com.peachyy.logback.utils.RabitInfos;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/6.</p>
 */
public abstract class RabbitMQBase<T> extends UnsynchronizedAppenderBase<T> {
    public static final String THREAD_POLL_NAME = "RabbitMQAppender";
    public static final String LOGBACK_MQ_QUEUE = "logback_rabbitmq_queue";
    protected RabbitMQencoder encoder = null;

    protected String host = "localhost";
    protected int port = 5672;
    protected String username = "guest";
    protected String password = "guest";
    protected String virtual = "/";
    protected boolean spring = false;//是否是spring环境
    protected boolean registerMbean = true;
    protected String queue = LOGBACK_MQ_QUEUE;


    public RabbitMQBase() {

    }

    @Override
    public void start() {
        super.start();
        RabitInfos infos = new RabitInfos();
        infos.setHost(host);
        infos.setVirtual(virtual);
        infos.setUsername(username);
        infos.setPassword(password);
        infos.setPort(port);
        QueueHold.getInstance().setInfos(infos);
        QueueHold.getInstance().init();
        if (registerMbean) {
            registerJmx();
        }
    }

    @Override
    public void stop() {
        super.stop();
        //RabbitMqFactory.getInstace(host,port,username,password,virtual);
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

    public void setSpring(boolean spring) {
        this.spring = spring;
    }

    private void registerJmx() {
        Class me = this.getClass();
        String name = me.getPackage().getName() + ":type=" + me.getSimpleName();
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName oname = new ObjectName(name);
            DefaultLogbackRabbit mbean = new DefaultLogbackRabbit();
            mbean.setHost(host);
            mbean.setPort(port);
            mbean.setUsername(username);
            mbean.setVirtual(virtual);
            mbs.registerMBean(mbean, oname);
        } catch (Exception e) {
            //skip
            // e.printStackTrace();
        }
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        if (queue != null && !queue.trim().equals("")) {
            this.queue = queue;
        }
    }
}
