package com.peachyy.logback;

import ch.qos.logback.classic.spi.LoggingEvent;
import com.peachyy.logback.queue.QueueHold;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/6.</p>
 */
public class RabbitMQAppender extends RabbitMQBase<LoggingEvent> {



    @Override
    protected void append(LoggingEvent eventObject) {
        byte[] b = encoder.doEncode(eventObject);
        // System.out.println(new String(b));
        QueueHold.getInstance().setContext(context);
        QueueHold.getInstance().sendQueue(getQueue(), b);
        // QueueHold.getInstance().close();
    }


}
