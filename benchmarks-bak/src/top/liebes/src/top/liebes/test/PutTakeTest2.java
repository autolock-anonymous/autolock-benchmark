package top.liebes.test;



import top.liebes.entity.withlock.FileUtil;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PutTakeTest2 {
    protected ExecutorService pool;
    protected CyclicBarrier barrier;
    protected static final AtomicInteger putSum = new AtomicInteger(0); //for testing
    protected static final AtomicInteger takeSum = new AtomicInteger(0);//for testing

    public static final Object lock = new Object();

    protected FileUtil fileUtil = new FileUtil();

    public PutTakeTest2() {
        this.barrier = new CyclicBarrier(PutTakeTest.numberOfThreads * 2 + 1);
    }

    public static void main(String[] args) {
        new PutTakeTest2().test();
    }

    void test() {
        this.pool = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < PutTakeTest.numberOfThreads; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
//                pool.execute(new Consumer());
            }
            barrier.await(); // wait for all threads to be ready
            barrier.await(); // wait for all threads to finish
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            this.pool.shutdown();
        }
    }

    class Producer implements Runnable {
        public void run() {
            try {
                barrier.await();
                for(int i = 0; i < PutTakeTest.numberOfProduceAndConsumePerLoop; i ++){
                    fileUtil.getFile("123");
//                    putSum.getAndAdd(1);
                }
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Consumer implements Runnable {
        public void run() {
            try {
                barrier.await();
                for(int i = 0; i < PutTakeTest.numberOfProduceAndConsumePerLoop; i ++){
                    if(! fileUtil.handle()){
                        i --;
                    }
                }
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}