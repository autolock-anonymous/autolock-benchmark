package nullappender.entity;
import org.apache.log4j.spi.LoggingEvent;
/** 
 * A NullAppender merely exists, it never outputs a message to any device.  
 * @author Ceki G&uuml;lc&uml;
 */
public class NullAppender extends AppenderSkeleton {
  private static NullAppender instance=new NullAppender();
  public NullAppender(){
  }
  /** 
 * There are no options to acticate.
 */
  public void activateOptions(){
  }
  /** 
 * Whenever you can, use this method to retreive an instance instead of instantiating a new one with <code>new</code>.
 */
  public NullAppender getInstance(){
    Cloner cloner=new Cloner();
    instance=cloner.deepClone(instance);
    return instance;
  }
  public void close(){
  }
  /** 
 * Does not do anything. 
 */
  public void doAppend(  LoggingEvent event){
  }
  /** 
 * Does not do anything. 
 */
  protected void append(  LoggingEvent event){
  }
  /** 
 * NullAppenders do not need a layout.  
 */
  public boolean requiresLayout(){
    return false;
  }
}
