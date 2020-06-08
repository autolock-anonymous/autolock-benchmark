package aeminium.raytracer.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Surface implements java.io.Serializable {
  public ReentrantReadWriteLock ior_kd_ks_kt_shineLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock colorLock=new ReentrantReadWriteLock();
  public Vec color;
  public double kd;
  public double ks;
  public double shine;
  public double kt;
  public double ior;
  @Perm(requires="unique(kd) * unique(ks) * unique(shine) * unique(kt) * unique(ior) in alive",ensures="unique(kd) * unique(ks) * unique(shine) * unique(kt) * unique(ior) in alive") public Surface(){
    ior_kd_ks_kt_shineLock.writeLock().lock();
    kd=1.0;
    ks=0.0;
    shine=0.0;
    kt=0.0;
    ior=1.0;
    ior_kd_ks_kt_shineLock.writeLock().unlock();
  }
  @Perm(requires="immutable(color) in alive",ensures="immutable(color) in alive") public String toString(){
    return "Surface { color=" + color + "}";
  }
  public ReentrantReadWriteLock getColorLock(){
    Cloner cloner=new Cloner();
    colorLock=cloner.deepClone(colorLock);
    Cloner cloner=new Cloner();
    colorLock=cloner.deepClone(colorLock);
    return colorLock;
  }
  public ReentrantReadWriteLock getIor_kd_ks_kt_shineLock(){
    Cloner cloner=new Cloner();
    ior_kd_ks_kt_shineLock=cloner.deepClone(ior_kd_ks_kt_shineLock);
    Cloner cloner=new Cloner();
    ior_kd_ks_kt_shineLock=cloner.deepClone(ior_kd_ks_kt_shineLock);
    return ior_kd_ks_kt_shineLock;
  }
  public Vec getColor(){
    Cloner cloner=new Cloner();
    color=cloner.deepClone(color);
    Cloner cloner=new Cloner();
    color=cloner.deepClone(color);
    return color;
  }
}
