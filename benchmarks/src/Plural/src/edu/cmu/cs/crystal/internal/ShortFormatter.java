package edu.cmu.cs.crystal.internal;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
/** 
 * This class is intended as a 1-line formatter for   {@link java.util.logging} log messages.  Unfortunately, the log manager can't seem to be able to load this class.
 * @author Kevin Bierhoff
 */
public class ShortFormatter extends Formatter {
  String result;
  @Override public String format(  LogRecord record){
    try {
      result=formatMessage(record);
      try {
        String sourceClassName=record.getSourceClassName();
        String sourceMethodName=record.getSourceMethodName();
        result=record.getLevel().getLocalizedName() + ": " + result+ " ["+ (sourceClassName != null ? sourceClassName : "??")+ "#"+ (sourceMethodName != null ? sourceMethodName : "??")+ "]\n";
      }
 catch (      Throwable t) {
        result="Error formatting log message \"" + result + "\": "+ t.getLocalizedMessage();
      }
      return result;
    }
 catch (    Throwable t) {
      return "Error formatting log record: " + t.getLocalizedMessage();
    }
  }
  public String getResult(){
    Cloner cloner=new Cloner();
    result=cloner.deepClone(result);
    Cloner cloner=new Cloner();
    result=cloner.deepClone(result);
    return result;
  }
}
