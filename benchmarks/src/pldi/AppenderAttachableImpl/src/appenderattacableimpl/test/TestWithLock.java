package appenderattacableimpl.test;


import appenderattacableimpl.entity.withlock.AppenderAttachableImpl;

public class TestWithLock {
    public static void main(String[] args){
        AppenderAttachableImpl var0 = new AppenderAttachableImpl();
        org.apache.log4j.ConsoleAppender var1 = new org.apache.log4j.ConsoleAppender();
        var0.addAppender(var1);
        var0.removeAppender((org.apache.log4j.Appender) null);
        boolean var2 = var0.isAttached(var1);
        AppenderAttachableImpl var3 = new AppenderAttachableImpl();
        org.apache.log4j.Appender var4 = var3.getAppender("");
        var0.removeAppender(var4);
        boolean var5 = var0.isAttached(var1);

        Thread thread1 = new Thread(() -> {
            var0.removeAllAppenders();
            var0.removeAllAppenders();
        });

        Thread thread2 = new Thread(() -> {
            var0.removeAllAppenders();
            var0.removeAllAppenders();
        });

        thread1.start();
        thread2.start();
        try{
            thread1.join();
            thread2.join();
        }
        catch (Exception e){

        }
        finally {
        }
    }
}
