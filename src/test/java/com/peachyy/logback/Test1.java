package com.peachyy.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * <p>描述:</p>
 *
 * @author Tao xs
 * @since 2.0
 * <p>Created by Tao xs on 2017/4/6.</p>
 */
public class Test1 {

    public static Logger log = LoggerFactory.getLogger(Test1.class.getName());

    //ExecutorService executors = Executors.newFixedThreadPool(2000);
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            log.debug("测试一下1{}", i);
            log.debug("测试一下2{}", i);
            log.debug("测试一下3{}", i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void test2() {
        for (int i = 0; i < 5000; i++) {
            final String a = Objects.toString(i);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    log.debug("测试一下{}", a);
                }
            }).start();
        }
//        try {
//            Thread.sleep(111111111);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
