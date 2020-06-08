package aeminium.raytracer.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Sphere extends Primitive implements java.io.Serializable {
  public ReentrantReadWriteLock D_P_c_enter_prim_r_r2_surf_tLock=new ReentrantReadWriteLock();
  Vec c;
  double r, r2;
  @Perm(requires="unique(c) * unique(r) * unique(r2) in alive",ensures="unique(c) * unique(r) * unique(r2) in alive") public Sphere(  Vec center,  double radius){
    D_P_c_enter_prim_r_r2_surf_tLock.writeLock().lock();
    c=center;
    r=radius;
    r2=r * r;
    D_P_c_enter_prim_r_r2_surf_tLock.writeLock().unlock();
  }
  @Perm(requires="pure(c) * immutable(P) * pure(D) * none(r2) * immutable(t) * full(enter) * full(prim) * pure(surf) * immutable(surf) in alive",ensures="pure(c) * immutable(P) * pure(D) * none(r2) * immutable(t) * full(enter) * full(prim) * pure(surf) * immutable(surf) in alive") public Isect intersect(  Ray ry){
    Vec v=new Vec();
    double b, disc, t;
    Isect ip;
    try {
      D_P_c_enter_prim_r_r2_surf_tLock.writeLock().lock();
      v.sub2(c,ry.P);
      b=Vec.dot(v,ry.D);
      disc=b * b - Vec.dot(v,v) + r2;
      if (disc < 0.0) {
        return null;
      }
      disc=Math.sqrt(disc);
      t=(b - disc < 1e-6) ? b + disc : b - disc;
      if (t < 1e-6) {
        return null;
      }
      ip=new Isect();
      ip.t=t;
      ip.enter=Vec.dot(v,v) > r2 + 1e-6 ? 1 : 0;
      ip.prim=this;
      ip.surf=surf;
    }
  finally {
      D_P_c_enter_prim_r_r2_surf_tLock.writeLock().unlock();
    }
    return ip;
  }
  @Perm(requires="pure(c) in alive",ensures="pure(c) in alive") public Vec normal(  Vec p){
    Vec r;
    D_P_c_enter_prim_r_r2_surf_tLock.readLock().lock();
    r=Vec.sub(p,c);
    D_P_c_enter_prim_r_r2_surf_tLock.readLock().unlock();
    r.normalize();
    return r;
  }
  @Perm(requires="pure(c) * none(r) in alive",ensures="pure(c) * none(r) in alive") public String toString(){
    try {
      D_P_c_enter_prim_r_r2_surf_tLock.readLock().lock();
      return "Sphere {" + c.toString() + ","+ r+ "}";
    }
  finally {
      D_P_c_enter_prim_r_r2_surf_tLock.readLock().unlock();
    }
  }
  @Perm(requires="pure(c) in alive",ensures="pure(c) in alive") public Vec getCenter(){
    try {
      D_P_c_enter_prim_r_r2_surf_tLock.readLock().lock();
      return c;
    }
  finally {
      D_P_c_enter_prim_r_r2_surf_tLock.readLock().unlock();
    }
  }
  @Perm(requires="full(c) in alive",ensures="full(c) in alive") public void setCenter(  Vec c){
    D_P_c_enter_prim_r_r2_surf_tLock.writeLock().lock();
    this.c=c;
    D_P_c_enter_prim_r_r2_surf_tLock.writeLock().unlock();
  }
  public ReentrantReadWriteLock getD_P_c_enter_prim_r_r2_surf_tLock(){
    Cloner cloner=new Cloner();
    D_P_c_enter_prim_r_r2_surf_tLock=cloner.deepClone(D_P_c_enter_prim_r_r2_surf_tLock);
    Cloner cloner=new Cloner();
    D_P_c_enter_prim_r_r2_surf_tLock=cloner.deepClone(D_P_c_enter_prim_r_r2_surf_tLock);
    return D_P_c_enter_prim_r_r2_surf_tLock;
  }
  public Vec getC(){
    Cloner cloner=new Cloner();
    c=cloner.deepClone(c);
    Cloner cloner=new Cloner();
    c=cloner.deepClone(c);
    return c;
  }
}
