package com.peachyy.logback.jmx;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/7.</p>
 */
public interface LogbackRabbitmqMBean {

    String rabbitmqHost();

    int rabbitPort();

    String rabbitmqUser();

    String rabbitVhost();


}
