package uma.SMC;
public class Time {
  public Integer hour=0;
  public Integer minute=0;
  public Integer second=0;
  public long msecond=0;
  String str;
  public Time(){
  }
  public String toString(){
    str=hour + ":" + minute+ ":"+ second;
    return str;
  }
  public Integer getSecond(){
    Cloner cloner=new Cloner();
    second=cloner.deepClone(second);
    return second;
  }
  public Integer getMinute(){
    Cloner cloner=new Cloner();
    minute=cloner.deepClone(minute);
    return minute;
  }
  public String getStr(){
    Cloner cloner=new Cloner();
    str=cloner.deepClone(str);
    return str;
  }
  public Integer getHour(){
    Cloner cloner=new Cloner();
    hour=cloner.deepClone(hour);
    return hour;
  }
}
