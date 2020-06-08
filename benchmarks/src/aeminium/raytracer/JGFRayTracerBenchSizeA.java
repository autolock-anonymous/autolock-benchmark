package aeminium.raytracer.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFRayTracerBenchSizeA {
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void main(  String argv[]){
    JGFRayTracerBench rtb=new JGFRayTracerBench();
    rtb.JGFrun(0);
  }
}
