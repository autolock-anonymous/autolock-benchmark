package aeminium.raytracer.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class View implements java.io.Serializable {
  public final Vec from;
  public final Vec at;
  public final Vec up;
  public final double dist;
  public final double angle;
  public final double aspect;
  @Perm(requires="no permission in alive",ensures="no permission in alive") public View(  Vec from,  Vec at,  Vec up,  double dist,  double angle,  double aspect){
    this.from=from;
    this.at=at;
    this.up=up;
    this.dist=dist;
    this.angle=angle;
    this.aspect=aspect;
  }
  public Vec getUp(){
    Cloner cloner=new Cloner();
    up=cloner.deepClone(up);
    Cloner cloner=new Cloner();
    up=cloner.deepClone(up);
    return up;
  }
  public Vec getFrom(){
    Cloner cloner=new Cloner();
    from=cloner.deepClone(from);
    Cloner cloner=new Cloner();
    from=cloner.deepClone(from);
    return from;
  }
  public Vec getAt(){
    Cloner cloner=new Cloner();
    at=cloner.deepClone(at);
    Cloner cloner=new Cloner();
    at=cloner.deepClone(at);
    return at;
  }
}
