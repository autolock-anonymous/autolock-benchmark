package top.liebes.test;



import top.liebes.entity.FileUtil;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PutTakeTest {
    protected ExecutorService pool;
    protected CyclicBarrier barrier;
    protected static final AtomicInteger putSum = new AtomicInteger(0); //for testing
    protected static final AtomicInteger takeSum = new AtomicInteger(0);//for testing

    public static int numberOfThreads = 5;

    public static int numberOfProduceAndConsumePerLoop = 10;

    public static final Object lock = new Object();

    protected FileUtil fileUtil = new FileUtil();

    public static void main(String[] args) throws Exception {
        java.util.ArrayList<ResInfo> resList = new java.util.ArrayList<>();
        for(int k = 2; k <= 10; k += 2){
            for(int j = 2; j <= 10; j *= 2){
                ResInfo info = new ResInfo();
                System.out.println("start with " + k + " threads, " + j + " loops");
                info.setThreads(k);
                info.setLoops(j);
                numberOfThreads = k;
                numberOfProduceAndConsumePerLoop = j;

                long startTime = System.currentTimeMillis();

                new PutTakeTest2().test();
//                System.out.println("array list total cost : " + (System.currentTimeMillis() - startTime));
                info.setTime(System.currentTimeMillis() - startTime);
//                System.out.println(PutTakeTest2.putSum.get() + " " + PutTakeTest2.takeSum.get());

                startTime = System.currentTimeMillis();
                new PutTakeTest().test(); // sample parameters
//                System.out.println("array list total cost with lock : " + (System.currentTimeMillis() - startTime));
                info.setTimeWithLock(System.currentTimeMillis() - startTime);
                putSum.set(0); takeSum.set(0);
                resList.add(info);
            }
        }
        for(int i = 0; i < resList.size(); i ++){
            System.out.print(resList.get(i).getTime() + (i == resList.size() - 1 ? "\n" : "\t"));
        }
        for(int i = 0; i < resList.size(); i ++){
            System.out.print(resList.get(i).getTimeWithLock() + (i == resList.size() - 1 ? "\n" : "\t"));
        }
    }
    
    public PutTakeTest() {
        this.barrier = new CyclicBarrier(numberOfThreads + 1);
    }

    void test() {
        this.pool = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < numberOfThreads; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
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
                for(int i = 0; i < numberOfProduceAndConsumePerLoop; i ++){
                    synchronized (lock){
                        fileUtil.getFile("qwert");
                    }
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
                    synchronized (lock){
                        if(! fileUtil.handle()){
                            i --;
                        }
                    }
                }
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}