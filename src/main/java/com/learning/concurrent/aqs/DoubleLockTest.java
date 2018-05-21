package com.learning.concurrent.aqs;
//package com.demo.concurrent.aqs;
//
//import org.junit.Test;
//
//public class DoubleLockTest {
//    
//    private DoubleLock lock = new DoubleLock();
//    
//    
//    @Test
//    public void test() {
//        Thread t1 = new Thread(new Task());
//        t1.start();
//        Thread t2 = new Thread(new Task());
//        t2.start();
//        Thread t3 = new Thread(new Task());
//        t3.start();
//        
//        while(true) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    
//    private class Task implements Runnable {
//        
//        public Task() {
//            
//        }
//        
//        @Override
//        public void run() {
//            lock.lock();
//            System.out.println(Thread.currentThread().getName() + "thread get lock");
//            try {
//                Thread.sleep(11000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            lock.unlock();
//        }
//        
//    }
//}
