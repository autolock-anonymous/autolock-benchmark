package jomp.lufact.withlock;
import jomp.jgfutil.JGFInstrumentor;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFLUFactBenchSizeB {
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void main(  String argv[]){
    JGFLUFactBench lub=new JGFLUFactBench();
    lub.JGFrun(1);
  }
}
