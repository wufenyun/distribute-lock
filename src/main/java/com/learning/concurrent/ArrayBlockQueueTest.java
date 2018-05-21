/**
 * Package: com.app.concurrent
 * Description: 
 */
package com.learning.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Test;

/**
 * Description:  
 * Date: 2017年8月4日 下午3:58:59
 * @author wufenyun 
 */
public class ArrayBlockQueueTest {

    private ArrayBlockingQueue<String> abQueue = new ArrayBlockingQueue<>(10);
    
    @Test
    public void test() throws InterruptedException {
        Producer producer = new Producer(abQueue);
        Consumer consumer1 = new Consumer(abQueue);
        Consumer consumer2 = new Consumer(abQueue);
        new Thread(consumer1).start();
        new Thread(producer).start();
        new Thread(consumer2).start();
        Thread.sleep(100000);
    }
    
    class Producer implements Runnable{
        
        private BlockingQueue<String> queue;
        
        private Producer(BlockingQueue<String> queue) {
            this.queue = queue;
        }
        
        public void produceByAdd() {
            for(int i=0;i<100;i++) {
                System.out.println("producer produce:" + new String("hello"+i));
                queue.add(new String("hello"+i));
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        public void produceByPut() {
            for(int i=0;i<100;i++) {
                System.out.println("producer produce:" + new String("hello"+i));
                try {
                    queue.put(new String("hello"+i));
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        public void produceByOffer() {
            for(int i=0;i<100;i++) {
                System.out.println("producer produce:" + new String("hello"+i));
                queue.offer(new String("hello"+i));
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            produceByAdd();
        }
    }
    
    class Consumer implements Runnable {
        
        private BlockingQueue<String> queue;
        
        private Consumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }
        
        public void consumByTake() {
            try {
                while(true) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " consum:" + queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        public void consumByPoll() {
            while(true) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " consum:" + queue.poll());
            }
        }
        
        public void consumByPeek() {
            while(true) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " consum:" + queue.peek());
            }
        }

        @Override
        public void run() {
            consumByTake();
        }
    }
}
