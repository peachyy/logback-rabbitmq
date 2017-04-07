package com.peachyy.logback.encoder;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
import com.peachyy.logback.exception.EncoderException;

import java.io.Serializable;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/6.</p>
 */
public abstract class BaseReturnEncoder<S, T extends Serializable> extends ContextAwareBase
        implements ReturnValueEncoder<S, T>, LifeCycle {

    private boolean started = false;
    protected Layout<S> layout;
    protected String pattern;

    @Override
    public T doEncode(S event) throws EncoderException {
        checkLayout();
        return encode(event);
    }

    private void checkLayout() {
        if (layout == null) {
            PatternLayout tmp = new PatternLayout();
            tmp.setContext(context);
            tmp.setPattern(pattern);
            layout = (Layout<S>) tmp;
            layout.start();
        }
    }

    protected abstract T encode(S event) throws EncoderException;

    @Override
    public void start() {
        this.started = true;
    }

    @Override
    public void stop() {
        this.started = false;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    public void setLayout(Layout<S> layout) {
        this.layout = layout;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
