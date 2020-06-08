package jomp.search.withlock;
import jomp.jgfutil.JGFInstrumentor;
import jomp.search.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFSearchBenchSizeA {
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void main(  String argv[]){
    JGFSearchBench sb=new JGFSearchBench();
    sb.JGFrun(0);
  }
}
