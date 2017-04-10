package com.peachyy.logback.queue;

import ch.qos.logback.core.spi.ContextAwareBase;
import com.peachyy.logback.factory.RabbitMqFactory;
import com.peachyy.logback.utils.RabitInfos;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.Closeable;
import java.io.IOException;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/6.</p>
 */
public class QueueHold extends ContextAwareBase {


    private static QueueHold INSTANCE = null;
    private static RabitInfos infos = null;
    private QueueHold() {

    }

    public void init() {
        if (factory == null) {
            factory = RabbitMqFactory.getInstace(infos.getHost(),
                    infos.getPort(), infos.getUsername(), infos.getPassword(), infos.getVirtual());
        }
    }

    public void setInfos(RabitInfos infos) {
        QueueHold.infos = infos;
    }

    public static QueueHold getInstance() {
        if (INSTANCE == null) {
            synchronized (QueueHold.class) {
                if (INSTANCE == null) {
                    INSTANCE = new QueueHold();
                }
            }
        }
        return INSTANCE;
    }

    public static RabbitMqFactory factory;

    public void sendQueue(String queueName, String msg) {
        sendQueue(queueName, msg.getBytes());
    }

    //    private void checkCollection(RabbitMqFactory factory) {
//        if (connection == null) {
//            try {
//                synchronized (this) {
//                    if (connection == null)
//                        connection = factory.newConnection();
//                }
//
//            } catch (Exception e) {
//                addError("创建 connection失败1[" + e.getMessage() + "]", e);
//            }
//        } else if (!connection.isOpen()) {
//            try {
//                // connection.close();
//                connection = null;
//                synchronized (this) {
//                    if (connection == null)
//                        connection = factory.newConnection();
//                }
//
//            } catch (Exception e) {
//                addError("创建 connection失败2[" + e.getMessage() + "]", e);
//            }
//        }
//    }
//
//    private void checkChannel(RabbitMqFactory factory) {
//        if (channel == null) {
//            try {
//                synchronized (this) {
//                    if (channel == null)
//                        channel = connection.createChannel();
//                }
//
//            } catch (IOException e) {
//                addError("创建 channel失败1 [" + e.getMessage() + "]", e);
//            }
//        } else if (!channel.isOpen()) {
//            try {
//                //  channel.close();
//                channel = null;
//                synchronized (this) {
//                    if (channel == null)
//                        channel = connection.createChannel();
//                }
//
//            } catch (IOException e) {
//                addError("创建 channel失败2 [" + e.getMessage() + "]", e);
//            }
//        }
//    }
    public void sendQueue(String queueName, byte[] msg) {
        Connection connection = null;
        Channel channel = null;
        try {
            try {
                connection = factory.newConnection();
            } catch (Exception e) {
                //e.printStackTrace();
                addError("发送 connection [" + e.getMessage() + "]", e);
            }
            //checkCollection(factory);
            // checkChannel(factory);
            channel = connection.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, msg);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            abortQuietly(channel);
            addError("发送 connection [" + e.getMessage() + "]", e);
            // throw new RuntimeException(String.format("发送日志队列%s失败", queueName));
        } finally {
            close(connection);
            close(channel);
        }
    }

    public void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                //
            }
        }
    }

    public void close(Channel channel) {
        if (channel != null) {
            try {
                channel.close();
            } catch (Exception e) {
                //
            }
        }
    }

    public static void abortQuietly(Channel closeable) {
        try {
            if (closeable != null) {
                closeable.abort();
            }
        } catch (Exception ioe) {
            // ignore
        }
    }
}
