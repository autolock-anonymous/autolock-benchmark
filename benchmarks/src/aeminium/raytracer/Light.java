package aeminium.raytracer.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Light implements java.io.Serializable {
  public ReentrantReadWriteLock brightness_posLock=new ReentrantReadWriteLock();
  public Vec pos;
  public double brightness;
  @Perm(requires="no permission in alive",ensures="no permission in alive") public Light(){
  }
  @Perm(requires="unique(pos) * unique(brightness) in alive",ensures="unique(pos) * unique(brightness) in alive") public Light(  double x,  double y,  double z,  double brightness){
    brightness_posLock.writeLock().lock();
    this.pos=new Vec(x,y,z);
    this.brightness=brightness;
    brightness_posLock.writeLock().unlock();
  }
  public ReentrantReadWriteLock getBrightness_posLock(){
    Cloner cloner=new Cloner();
    brightness_posLock=cloner.deepClone(brightness_posLock);
    Cloner cloner=new Cloner();
    brightness_posLock=cloner.deepClone(brightness_posLock);
    return brightness_posLock;
  }
  public Vec getPos(){
    Cloner cloner=new Cloner();
    pos=cloner.deepClone(pos);
    Cloner cloner=new Cloner();
    pos=cloner.deepClone(pos);
    return pos;
  }
}
