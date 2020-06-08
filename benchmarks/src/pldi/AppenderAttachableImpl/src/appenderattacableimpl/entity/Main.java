package appenderattacableimpl.entity;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
public class Main {
  public static void main(  String[] args){
    AppenderAttachableImpl attach=new AppenderAttachableImpl();
    ConsoleAppender appender=new ConsoleAppender();
    attach.addAppender(appender);
    attach.removeAppender((org.apache.log4j.Appender)null);
    final boolean flag=attach.isAttached(appender);
    AppenderAttachableImpl var3=new AppenderAttachableImpl();
    Appender var4=var3.getAppender("");
    attach.removeAppender(var4);
    boolean var5=attach.isAttached(appender);
    attach.removeAllAppenders();
  }
}
