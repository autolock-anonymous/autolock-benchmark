package jomp.montecarlo2.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class DemoException extends java.lang.Exception {
  public static ReentrantReadWriteLock DEBUGLock=new ReentrantReadWriteLock();
  public static boolean DEBUG=true;
  @Perm(requires="none(DEBUG) in alive",ensures="none(DEBUG) in alive") public DemoException(){
    super();
    if (DEBUG) {
      printStackTrace();
    }
  }
  @Perm(requires="none(DEBUG) in alive",ensures="none(DEBUG) in alive") public DemoException(  String s){
    super(s);
    if (DEBUG) {
      printStackTrace();
    }
  }
  @Perm(requires="none(DEBUG) in alive",ensures="none(DEBUG) in alive") public DemoException(  int ierr){
    super(String.valueOf(ierr));
    if (DEBUG) {
      printStackTrace();
    }
  }
  public static ReentrantReadWriteLock getDEBUGLock(){
    Cloner cloner=new Cloner();
    DEBUGLock=cloner.deepClone(DEBUGLock);
    return DEBUGLock;
  }
}
