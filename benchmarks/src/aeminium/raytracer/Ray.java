package aeminium.raytracer.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
final public class Ray {
  public ReentrantReadWriteLock zLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock yLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock D_PLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock xLock=new ReentrantReadWriteLock();
  public Vec P, D;
  @Perm(requires="unique(P) * none(x) * none(y) * none(z) * unique(D) in alive",ensures="unique(P) * none(x) * none(y) * none(z) * unique(D) in alive") public Ray(  Vec pnt,  Vec dir){
    D_PLock.writeLock().lock();
    P=new Vec(pnt.x,pnt.y,pnt.z);
    D=new Vec(dir.x,dir.y,dir.z);
    D.normalize();
    D_PLock.writeLock().unlock();
  }
  @Perm(requires="unique(P) * unique(D) in alive",ensures="unique(P) * unique(D) in alive") public Ray(){
    D_PLock.writeLock().lock();
    P=new Vec();
    D=new Vec();
    D_PLock.writeLock().unlock();
  }
  @Perm(requires="immutable(P) * pure(x) * pure(D) * pure(y) * pure(z) in alive",ensures="immutable(P) * pure(x) * pure(D) * pure(y) * pure(z) in alive") public Vec point(  double t){
    try {
      zLock.readLock().lock();
      yLock.readLock().lock();
      xLock.readLock().lock();
      D_PLock.readLock().lock();
      return new Vec(P.x + D.x * t,P.y + D.y * t,P.z + D.z * t);
    }
  finally {
      D_PLock.readLock().unlock();
      xLock.readLock().unlock();
      yLock.readLock().unlock();
      zLock.readLock().unlock();
    }
  }
  @Perm(requires="immutable(P) * pure(D) in alive",ensures="immutable(P) * pure(D) in alive") public String toString(){
    try {
      D_PLock.readLock().lock();
      return "{" + P.toString() + " -> "+ D.toString()+ "}";
    }
  finally {
      D_PLock.readLock().unlock();
    }
  }
  public Vec getP(){
    Cloner cloner=new Cloner();
    P=cloner.deepClone(P);
    Cloner cloner=new Cloner();
    P=cloner.deepClone(P);
    return P;
  }
  public ReentrantReadWriteLock getYLock(){
    Cloner cloner=new Cloner();
    yLock=cloner.deepClone(yLock);
    Cloner cloner=new Cloner();
    yLock=cloner.deepClone(yLock);
    return yLock;
  }
  public ReentrantReadWriteLock getZLock(){
    Cloner cloner=new Cloner();
    zLock=cloner.deepClone(zLock);
    Cloner cloner=new Cloner();
    zLock=cloner.deepClone(zLock);
    return zLock;
  }
  public ReentrantReadWriteLock getXLock(){
    Cloner cloner=new Cloner();
    xLock=cloner.deepClone(xLock);
    Cloner cloner=new Cloner();
    xLock=cloner.deepClone(xLock);
    return xLock;
  }
  public ReentrantReadWriteLock getD_PLock(){
    Cloner cloner=new Cloner();
    D_PLock=cloner.deepClone(D_PLock);
    Cloner cloner=new Cloner();
    D_PLock=cloner.deepClone(D_PLock);
    return D_PLock;
  }
}
