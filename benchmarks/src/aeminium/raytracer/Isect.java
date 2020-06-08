package aeminium.raytracer.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Isect {
  public double t;
  public int enter;
  public Primitive prim;
  public Surface surf;
  public Primitive getPrim(){
    Cloner cloner=new Cloner();
    prim=cloner.deepClone(prim);
    Cloner cloner=new Cloner();
    prim=cloner.deepClone(prim);
    return prim;
  }
  public Surface getSurf(){
    Cloner cloner=new Cloner();
    surf=cloner.deepClone(surf);
    Cloner cloner=new Cloner();
    surf=cloner.deepClone(surf);
    return surf;
  }
}
