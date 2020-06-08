package jomp.euler.withlock;
import jomp.jgfutil.JGFInstrumentor;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFEulerBenchSizeA {
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void main(  String argv[]){
    JGFEulerBench eb=new JGFEulerBench();
    eb.JGFrun(0);
  }
}
