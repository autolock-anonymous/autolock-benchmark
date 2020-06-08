package timeseries.entity;

import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimePeriod;

public class Main{
    public static void main(String[] args) {
        final TimeSeries var0 = new TimeSeries("S");
        final TimeSeries var1 = var0.createCopy( null,  null);
        final Day var2 = new Day();
        var0.add( var2, null);

        final Day var11 = new Day();
        final TimeSeries var21 = var0.createCopy( var11,  var11);
        final TimeSeries var31 = var0.createCopy( var11,  null);

        RegularTimePeriod v = new Day();
        var0.update(v, 1);
        var0.getDataPair1(v);
    }
}