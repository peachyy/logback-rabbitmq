package com.peachyy.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/6.</p>
 */
public class Test1 {

    public static Logger log = LoggerFactory.getLogger(Test1.class.getName());

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            log.debug("测试一下1{}", i);
            log.debug("测试一下2{}", i);
            log.debug("测试一下3{}", i);
        }

    }
}
