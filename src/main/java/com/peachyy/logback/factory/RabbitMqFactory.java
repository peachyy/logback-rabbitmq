package com.peachyy.logback.factory;

import com.rabbitmq.client.ConnectionFactory;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/6.</p>
 */
public class RabbitMqFactory extends ConnectionFactory {

    public RabbitMqFactory() {

    }

    public RabbitMqFactory(String host, int port, String username, String password,
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
