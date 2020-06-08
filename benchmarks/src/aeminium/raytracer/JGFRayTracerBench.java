package aeminium.raytracer.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFRayTracerBench extends RayTracer implements JGFSection3 {
  public ReentrantReadWriteLock checksumLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock datasizes_height_lights_numobjects_prim_scene_size_widthLock=new ReentrantReadWriteLock();
  @Perm(requires="share(size) in alive",ensures="share(size) in alive") public void JGFsetsize(  int size){
    datasizes_height_lights_numobjects_prim_scene_size_widthLock.writeLock().lock();
    this.size=size;
    datasizes_height_lights_numobjects_prim_scene_size_widthLock.writeLock().unlock();
  }
  @Perm(requires="share(width) * share(height) * immutable(datasizes) * pure(size) * share(scene) * share(numobjects) in alive",ensures="share(width) * share(height) * immutable(datasizes) * pure(size) * share(scene) * share(numobjects) in alive") public void JGFinitialise(){
    JGFInstrumentor.startTimer("Section3:RayTracer:Init");
    datasizes_height_lights_numobjects_prim_scene_size_widthLock.writeLock().lock();
    width=height=datasizes[size];
    scene=createScene();
    setScene(scene);
    numobjects=scene.getObjects();
    datasizes_height_lights_numobjects_prim_scene_size_widthLock.writeLock().unlock();
    JGFInstrumentor.stopTimer("Section3:RayTracer:Init");
  }
  @Perm(requires="pure(width) * pure(height) * immutable(width) in alive",ensures="pure(width) * pure(height) * immutable(width) in alive") public void JGFapplication(){
    JGFInstrumentor.startTimer("Section3:RayTracer:Run");
    datasizes_height_lights_numobjects_prim_scene_size_widthLock.readLock().lock();
    Interval interval=new Interval(0,width,height,0,height,1);
    datasizes_height_lights_numobjects_prim_scene_size_widthLock.readLock().unlock();
    render(interval);
    JGFInstrumentor.stopTimer("Section3:RayTracer:Run");
  }
  @Perm(requires="pure(checksum) * pure(size) in alive",ensures="pure(checksum) * pure(size) in alive") public void JGFvalidate(){
    long refval[]={2788957,31033217};
    checksumLock.readLock().lock();
    datasizes_height_lights_numobjects_prim_scene_size_widthLock.readLock().lock();
    long dev=checksum - refval[size];
    if (dev != 0) {
      System.out.println("Validation failed");
      System.out.println("Pixel checksum = " + checksum);
      System.out.println("Reference value = " + refval[size]);
    }
    datasizes_height_lights_numobjects_prim_scene_size_widthLock.readLock().unlock();
    checksumLock.readLock().unlock();
  }
  @Perm(requires="unique(scene) * unique(lights) * unique(prim) in alive",ensures="unique(scene) * unique(lights) * unique(prim) in alive") public void JGFtidyup(){
    datasizes_height_lights_numobjects_prim_scene_size_widthLock.writeLock().lock();
    scene=null;
    lights=null;
    prim=null;
    datasizes_height_lights_numobjects_prim_scene_size_widthLock.writeLock().unlock();
    System.gc();
  }
  @Perm(requires="unique(numobjects) * unique(width) * unique(height) * unique(width) in alive",ensures="unique(numobjects) * unique(width) * unique(height) * unique(width) in alive") public void JGFrun(  int size){
    JGFInstrumentor.addTimer("Section3:RayTracer:Total","Solutions",size);
    JGFInstrumentor.addTimer("Section3:RayTracer:Init","Objects",size);
    JGFInstrumentor.addTimer("Section3:RayTracer:Run","Pixels",size);
    datasizes_height_lights_numobjects_prim_scene_size_widthLock.writeLock().lock();
    JGFsetsize(size);
    JGFInstrumentor.startTimer("Section3:RayTracer:Total");
    JGFinitialise();
    JGFapplication();
    JGFvalidate();
    JGFtidyup();
    JGFInstrumentor.stopTimer("Section3:RayTracer:Total");
    JGFInstrumentor.addOpsToTimer("Section3:RayTracer:Init",(double)numobjects);
    JGFInstrumentor.addOpsToTimer("Section3:RayTracer:Run",(double)(width * height));
    datasizes_height_lights_numobjects_prim_scene_size_widthLock.writeLock().unlock();
    JGFInstrumentor.addOpsToTimer("Section3:RayTracer:Total",1);
    JGFInstrumentor.printTimer("Section3:RayTracer:Init");
    JGFInstrumentor.printTimer("Section3:RayTracer:Run");
    JGFInstrumentor.printTimer("Section3:RayTracer:Total");
  }
  public ReentrantReadWriteLock getChecksumLock(){
    Cloner cloner=new Cloner();
    checksumLock=cloner.deepClone(checksumLock);
    Cloner cloner=new Cloner();
    checksumLock=cloner.deepClone(checksumLock);
    return checksumLock;
  }
  public ReentrantReadWriteLock getDatasizes_height_lights_numobjects_prim_scene_size_widthLock(){
    Cloner cloner=new Cloner();
    datasizes_height_lights_numobjects_prim_scene_size_widthLock=cloner.deepClone(datasizes_height_lights_numobjects_prim_scene_size_widthLock);
    Cloner cloner=new Cloner();
    datasizes_height_lights_numobjects_prim_scene_size_widthLock=cloner.deepClone(datasizes_height_lights_numobjects_prim_scene_size_widthLock);
    return datasizes_height_lights_numobjects_prim_scene_size_widthLock;
  }
}
