package com.peachyy.logback.encoder;

import com.peachyy.logback.exception.EncoderException;

import java.io.Serializable;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/6.</p>
 */
public interface ReturnValueEncoder<S, T extends Serializable> {

    T doEncode(S event) throws EncoderException;
}
