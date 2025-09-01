package com.example.spring_01;

import org.junit.Test;

public class ThreadLoacalTest {
    @Test
    public void testThreadLocalSetAndGet(){
        ThreadLocal tl=new ThreadLocal();

        new Thread(()->{
            tl.set("xy");
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
        },"ns").start();
        new Thread(()->{
            tl.set("yc");
            System.out.println(Thread.currentThread().getName()+": "+tl.get());
        },"ls").start();
    }
}
