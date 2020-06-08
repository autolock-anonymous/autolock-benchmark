package jomp.sor.withlock;
import jomp.jgfutil.JGFInstrumentor;
import jomp.sor.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFSORBenchSizeB {
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void main(  String argv[]){
    JGFSORBench sor=new JGFSORBench();
    sor.JGFrun(1);
  }
}
