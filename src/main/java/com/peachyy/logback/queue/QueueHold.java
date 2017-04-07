package com.peachyy.logback.queue;

import com.peachyy.logback.factory.RabbitMqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/6.</p>
 */
public class QueueHold {
    private Connection connection = null;
    private Channel channel = null;

    private final static QueueHold INSTANCE = new QueueHold();

    private QueueHold() {

    }

    public static QueueHold getInstance() {
        return INSTANCE;
    }

    public void sendQueue(RabbitMqFactory factory, String queueName, String msg) {
        sendQueue(factory, queueName, msg.getBytes());
    }

    private void checkCollection(RabbitMqFactory factory) {
        if (connection == null) {
            try {
                connection = factory.newConnection();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        } else if (!connection.isOpen()) {
            try {
                // connection.close();
                connection = null;
                connection = factory.newConnection();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkChannel(RabbitMqFactory factory) {
        if (channel == null) {
            try {
                channel = connection.createChannel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (!channel.isOpen()) {
            try {
                //  channel.close();
                channel = null;
                channel = connection.createChannel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendQueue(RabbitMqFactory factory, String queueName, byte[] msg) {
        try {
            checkCollection(factory);
            checkChannel(factory);
            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void close() {
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
