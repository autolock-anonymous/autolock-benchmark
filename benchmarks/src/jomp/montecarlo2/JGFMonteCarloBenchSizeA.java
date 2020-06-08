package jomp.montecarlo2.withlock;
import jomp.jgfutil.*;
import java.io.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFMonteCarloBenchSizeA {
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void main(  String argv[]){
    JGFMonteCarloBench mc=new JGFMonteCarloBench();
    mc.JGFrun(0);
  }
}
