package loggerjava141;

public class Main{
    public static void main(String[] args) {
        final Logger var0 = Logger.getLogger("mFAPv5I^");
        var0.warning(null);
        var0.setFilter(null);
        final MyFilter var1 = new MyFilter();
        var0.setFilter( var1);
        var0.warning("");
    }
}