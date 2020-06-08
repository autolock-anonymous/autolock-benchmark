package jomp.moldyn.withlock;
import jomp.jgfutil.JGFInstrumentor;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFMolDynBenchSizeA {
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void main(  String argv[]){
    JGFMolDynBench mold=new JGFMolDynBench();
    mold.JGFrun(0);
  }
}
