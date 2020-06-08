package nullappender.entity;

import org.apache.log4j.Priority;

public class Main{
    public static void main(String[] args) {
        NullAppender var0 = new NullAppender();
        NullAppender var1 = var0.getInstance();
        NullAppender var2 = var1.getInstance();
        Priority var3 = Priority.FATAL;
        var2.setThreshold( var3);
        final boolean var4 = var2.isAsSevereAsThreshold( var3);
        var2.setThreshold( null);
        final boolean var5 = var2.isAsSevereAsThreshold( var3);
        var2.setThreshold( var3);
        NullAppender vart = new NullAppender();
        Priority var41 = vart.getThreshold();
        var2.setThreshold(var41);
        var2.setThreshold(var41);
        Priority var51 = org.apache.log4j.Priority.FATAL;
        final boolean var6 = var2.isAsSevereAsThreshold(var51);
        var2.setThreshold(var41);
        final org.apache.log4j.Priority var7 = org.apache.log4j.Priority.toPriority(-3);
        final boolean var8 = var2.isAsSevereAsThreshold(var7);
    }
}