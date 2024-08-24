package com.example.demo;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {

    @Test
    public void testThreadLocal() {
        //提供Threadlocal对象
        ThreadLocal tl = new ThreadLocal();
        //开启两个线程
        new Thread(()->{
            tl.set("lc");
            System.out.println(Thread.currentThread().getName()+tl.get());
            System.out.println(Thread.currentThread().getName()+tl.get());
            System.out.println(Thread.currentThread().getName()+tl.get());
        },"第一线程").start();

        new Thread(()->{
            tl.set("zc");
            System.out.println(Thread.currentThread().getName()+tl.get());
            System.out.println(Thread.currentThread().getName()+tl.get());
            System.out.println(Thread.currentThread().getName()+tl.get());
        },"第二线程").start();
    }
}
