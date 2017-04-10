package com.peachyy.logback.factory;

import com.peachyy.logback.RabbitMQBase;
import com.peachyy.logback.utils.NamedThreadFactory;
import com.rabbitmq.client.ConnectionFactory;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/6.</p>
 */
public class RabbitMqFactory extends ConnectionFactory {

    private static RabbitMqFactory rabbitMqFactory = null;

    private RabbitMqFactory() {
    }

    public static RabbitMqFactory getInstace(String host, int port, String username, String password, String virtual) {
        if (rabbitMqFactory == null) {
            synchronized (RabbitMqFactory.class) {
                if (rabbitMqFactory == null) {
                    //ExecutorService service = Executors.newFixedThreadPool(500);
                    rabbitMqFactory = new RabbitMqFactory(
                            host, port, username, password, virtual
                    );
                    //rabbitMqFactory.setSharedExecutor(service);
                    rabbitMqFactory.setConnectionTimeout(15000);// 15秒
                    rabbitMqFactory.setRequestedHeartbeat(60);
                    rabbitMqFactory.setThreadFactory(new NamedThreadFactory(RabbitMQBase.THREAD_POLL_NAME, true));
                }
            }
        }
        return rabbitMqFactory;
    }

    private RabbitMqFactory(String host, int port, String username, String password,
                            String virtualHost) {
        setHost(host);
        setPort(port);
        setUsername(username);
        setPassword(password);
        setVirtualHost(virtualHost);
        //允许自动恢复
        setAutomaticRecoveryEnabled(true);
        // attempt recovery every 5 seconds
        setNetworkRecoveryInterval(1000);
        setTopologyRecoveryEnabled(true);
    }

}
