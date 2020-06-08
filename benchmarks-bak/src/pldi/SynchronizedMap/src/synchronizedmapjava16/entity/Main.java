package synchronizedmapjava16.entity;

public class Main{
    public static void main(String[] args) {
        Collections.SynchronizedMap var0 = Factory.createSyncMap();
        var0.clear();
        boolean var1 = var0.remove( var0,  null);
        var0.clear();
        String var2 = var0.toString();
        boolean var3 = var0.equals( null);
        Object var4 = new java.lang.Object();
        Object var5 = var0.getOrDefault( var2,  var4);
        Object var6 = var0.getOrDefault( var0,  var4);
        Object var7 = var0.put( var5,  var2);
        Object var8 = var0.put( var6,  var0);
        Object var9 = var0.put( var4,  var4);

        Collections.SynchronizedMap var41 = Factory.createSyncMap();
        Object var51 = var0.put(var2, var41);
        Object var61 = var0.getOrDefault(var41, var51);
        Object var71 = var0.getOrDefault(var0, var61);
        Object var81 = var0.getOrDefault(var41, var51);
        Object var91 = new Object();
        Object var101 = var0.getOrDefault(var91, var2);
    }
}