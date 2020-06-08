package aeminium.raytracer.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public abstract class Primitive implements java.io.Serializable {
  public Surface surf=new Surface();
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void setColor(  double r,  double g,  double b){
  }
  public abstract Vec normal(  Vec pnt);
  public abstract Isect intersect(  Ray ry);
  public abstract String toString();
  public abstract Vec getCenter();
  public abstract void setCenter(  Vec c);
  public Surface getSurf(){
    Cloner cloner=new Cloner();
    surf=cloner.deepClone(surf);
    Cloner cloner=new Cloner();
    surf=cloner.deepClone(surf);
    return surf;
  }
}
