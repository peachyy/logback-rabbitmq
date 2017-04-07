package com.peachyy.logback.queue;

import ch.qos.logback.core.spi.ContextAwareBase;
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
public class QueueHold extends ContextAwareBase {
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
                synchronized (this) {
                    if (connection == null)
                        connection = factory.newConnection();
                }

            } catch (Exception e) {
                addError("创建 connection失败1[" + e.getMessage() + "]", e);
            }
        } else if (!connection.isOpen()) {
            try {
                // connection.close();
                connection = null;
                synchronized (this) {
                    if (connection == null)
                        connection = factory.newConnection();
                }

            } catch (Exception e) {
                addError("创建 connection失败2[" + e.getMessage() + "]", e);
            }
        }
    }

    private void checkChannel(RabbitMqFactory factory) {
        if (channel == null) {
            try {
                synchronized (this) {
                    if (channel == null)
                        channel = connection.createChannel();
                }

            } catch (IOException e) {
                addError("创建 channel失败1 [" + e.getMessage() + "]", e);
            }
        } else if (!channel.isOpen()) {
            try {
                //  channel.close();
                channel = null;
                synchronized (this) {
                    if (channel == null)
                        channel = connection.createChannel();
                }

            } catch (IOException e) {
                addError("创建 channel失败2 [" + e.getMessage() + "]", e);
            }
        }
    }
    public void sendQueue(RabbitMqFactory factory, String queueName, byte[] msg) {
        try {
            try {
                connection = factory.newConnection();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            //checkCollection(factory);
            // checkChannel(factory);
            channel = connection.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, msg);
        } catch (IOException e) {
            throw new RuntimeException(String.format("发送日志队列%s失败", queueName));
        } finally {
            close();
        }
    }

    public void close() {
        if (channel != null) {
            try {
                if (channel.isOpen())
                    channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                if (connection.isOpen())
                    connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        channel = null;
        connection = null;
    }
}
