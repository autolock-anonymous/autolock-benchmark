package aeminium.raytracer.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Interval implements java.io.Serializable {
  public final int number;
  public final int width;
  public final int height;
  public final int yfrom;
  public final int yto;
  public final int total;
  @Perm(requires="no permission in alive",ensures="no permission in alive") public Interval(  int number,  int width,  int height,  int yfrom,  int yto,  int total){
    this.number=number;
    this.width=width;
    this.height=height;
    this.yfrom=yfrom;
    this.yto=yto;
    this.total=total;
  }
}
