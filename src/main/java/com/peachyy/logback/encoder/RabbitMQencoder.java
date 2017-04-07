package com.peachyy.logback.encoder;

import ch.qos.logback.classic.spi.LoggingEvent;
import com.peachyy.logback.exception.EncoderException;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/6.</p>
 */
public class RabbitMQencoder extends BaseReturnEncoder<LoggingEvent, byte[]> {


    @Override
    protected byte[] encode(LoggingEvent event) throws EncoderException {
        final String message = layout.doLayout(event);
        byte[] vb = message.getBytes();
        return vb;
    }
}
