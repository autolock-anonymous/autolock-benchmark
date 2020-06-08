package aeminium.raytracer.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Vec implements java.io.Serializable {
  public ReentrantReadWriteLock x_y_zLock=new ReentrantReadWriteLock();
  public double x;
  public double y;
  public double z;
  @Perm(requires="unique(x) * unique(y) * unique(z) in alive",ensures="unique(x) * unique(y) * unique(z) in alive") public Vec(  double a,  double b,  double c){
    x_y_zLock.writeLock().lock();
    x=a;
    y=b;
    z=c;
    x_y_zLock.writeLock().unlock();
  }
  @Perm(requires="unique(x) * unique(y) * unique(z) in alive",ensures="unique(x) * unique(y) * unique(z) in alive") public Vec(  Vec a){
    x_y_zLock.writeLock().lock();
    x=a.x;
    y=a.y;
    z=a.z;
    x_y_zLock.writeLock().unlock();
  }
  @Perm(requires="unique(x) * unique(y) * unique(z) in alive",ensures="unique(x) * unique(y) * unique(z) in alive") public Vec(){
    x_y_zLock.writeLock().lock();
    x=0.0;
    y=0.0;
    z=0.0;
    x_y_zLock.writeLock().unlock();
  }
  @Perm(requires="share(x) * share(y) * share(z) in alive",ensures="share(x) * share(y) * share(z) in alive") public final void add(  Vec a){
    x_y_zLock.writeLock().lock();
    x+=a.x;
    y+=a.y;
    z+=a.z;
    x_y_zLock.writeLock().unlock();
  }
  @Perm(requires="pure(x) * pure(y) * pure(z) in alive",ensures="pure(x) * pure(y) * pure(z) in alive") public static Vec adds(  double s,  Vec a,  Vec b){
    try {
      x_y_zLock.readLock().lock();
      return new Vec(s * a.x + b.x,s * a.y + b.y,s * a.z + b.z);
    }
  finally {
      x_y_zLock.readLock().unlock();
    }
  }
  @Perm(requires="share(x) * share(y) * share(z) in alive",ensures="share(x) * share(y) * share(z) in alive") public final void adds(  double s,  Vec b){
    x_y_zLock.writeLock().lock();
    x+=s * b.x;
    y+=s * b.y;
    z+=s * b.z;
    x_y_zLock.writeLock().unlock();
  }
  @Perm(requires="pure(x) * pure(y) * pure(z) in alive",ensures="pure(x) * pure(y) * pure(z) in alive") public static Vec sub(  Vec a,  Vec b){
    try {
      x_y_zLock.readLock().lock();
      return new Vec(a.x - b.x,a.y - b.y,a.z - b.z);
    }
  finally {
      x_y_zLock.readLock().unlock();
    }
  }
  @Perm(requires="share(x) * share(y) * share(z) in alive",ensures="share(x) * share(y) * share(z) in alive") public final void sub2(  Vec a,  Vec b){
    x_y_zLock.writeLock().lock();
    this.x=a.x - b.x;
    this.y=a.y - b.y;
    this.z=a.z - b.z;
    x_y_zLock.writeLock().unlock();
  }
  @Perm(requires="pure(x) * pure(y) * pure(z) in alive",ensures="pure(x) * pure(y) * pure(z) in alive") public static Vec mult(  Vec a,  Vec b){
    try {
      x_y_zLock.readLock().lock();
      return new Vec(a.x * b.x,a.y * b.y,a.z * b.z);
    }
  finally {
      x_y_zLock.readLock().unlock();
    }
  }
  @Perm(requires="pure(y) * pure(z) * pure(x) in alive",ensures="pure(y) * pure(z) * pure(x) in alive") public static Vec cross(  Vec a,  Vec b){
    try {
      x_y_zLock.readLock().lock();
      return new Vec(a.y * b.z - a.z * b.y,a.z * b.x - a.x * b.z,a.x * b.y - a.y * b.x);
    }
  finally {
      x_y_zLock.readLock().unlock();
    }
  }
  @Perm(requires="pure(x) * pure(y) * pure(z) in alive",ensures="pure(x) * pure(y) * pure(z) in alive") public static double dot(  Vec a,  Vec b){
    try {
      x_y_zLock.readLock().lock();
      return a.x * b.x + a.y * b.y + a.z * b.z;
    }
  finally {
      x_y_zLock.readLock().unlock();
    }
  }
  @Perm(requires="pure(x) * pure(y) * pure(z) in alive",ensures="pure(x) * pure(y) * pure(z) in alive") public static Vec comb(  double a,  Vec A,  double b,  Vec B){
    try {
      x_y_zLock.readLock().lock();
      return new Vec(a * A.x + b * B.x,a * A.y + b * B.y,a * A.z + b * B.z);
    }
  finally {
      x_y_zLock.readLock().unlock();
    }
  }
  @Perm(requires="share(x) * share(y) * share(z) in alive",ensures="share(x) * share(y) * share(z) in alive") public final void comb2(  double a,  Vec A,  double b,  Vec B){
    x_y_zLock.writeLock().lock();
    x=a * A.x + b * B.x;
    y=a * A.y + b * B.y;
    z=a * A.z + b * B.z;
    x_y_zLock.writeLock().unlock();
  }
  @Perm(requires="share(x) * share(y) * share(z) in alive",ensures="share(x) * share(y) * share(z) in alive") public final void scale(  double t){
    x_y_zLock.writeLock().lock();
    x*=t;
    y*=t;
    z*=t;
    x_y_zLock.writeLock().unlock();
  }
  @Perm(requires="share(x) * share(y) * share(z) in alive",ensures="share(x) * share(y) * share(z) in alive") public final void negate(){
    x_y_zLock.writeLock().lock();
    x=-x;
    y=-y;
    z=-z;
    x_y_zLock.writeLock().unlock();
  }
  @Perm(requires="share(x) * share(y) * share(z) in alive",ensures="share(x) * share(y) * share(z) in alive") public final double normalize(){
    double len;
    x_y_zLock.writeLock().lock();
    len=Math.sqrt(x * x + y * y + z * z);
    if (len > 0.0) {
      x/=len;
      y/=len;
      z/=len;
    }
    x_y_zLock.writeLock().unlock();
    return len;
  }
  @Perm(requires="pure(x) * pure(y) * pure(z) in alive",ensures="pure(x) * pure(y) * pure(z) in alive") public final String toString(){
    try {
      x_y_zLock.readLock().lock();
      return "<" + x + ","+ y+ ","+ z+ ">";
    }
  finally {
      x_y_zLock.readLock().unlock();
    }
  }
  public ReentrantReadWriteLock getX_y_zLock(){
    Cloner cloner=new Cloner();
    x_y_zLock=cloner.deepClone(x_y_zLock);
    Cloner cloner=new Cloner();
    x_y_zLock=cloner.deepClone(x_y_zLock);
    return x_y_zLock;
  }
}
