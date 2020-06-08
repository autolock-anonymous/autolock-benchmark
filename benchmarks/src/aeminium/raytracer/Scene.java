package aeminium.raytracer.withlock;
import jomp.jgfutil.*;
import java.util.Vector;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Scene implements java.io.Serializable {
  public ReentrantReadWriteLock viewLock=new ReentrantReadWriteLock();
  public final Vector lights;
  public final Vector objects;
  private View view;
  @Perm(requires="no permission in alive",ensures="no permission in alive") public Scene(){
    this.lights=new Vector();
    this.objects=new Vector();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void addLight(  Light l){
    this.lights.addElement(l);
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void addObject(  Primitive object){
    this.objects.addElement(object);
  }
  @Perm(requires="full(view) in alive",ensures="full(view) in alive") public void setView(  View view){
    viewLock.writeLock().lock();
    this.view=view;
    viewLock.writeLock().unlock();
  }
  @Perm(requires="pure(view) in alive",ensures="pure(view) in alive") public View getView(){
    Cloner cloner=new Cloner();
    view=cloner.deepClone(view);
    Cloner cloner=new Cloner();
    view=cloner.deepClone(view);
    try {
      viewLock.readLock().lock();
      return this.view;
    }
  finally {
      viewLock.readLock().unlock();
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public Light getLight(  int number){
    return (Light)this.lights.elementAt(number);
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public Primitive getObject(  int number){
    return (Primitive)objects.elementAt(number);
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public int getLights(){
    Cloner cloner=new Cloner();
    lights=cloner.deepClone(lights);
    Cloner cloner=new Cloner();
    lights=cloner.deepClone(lights);
    return this.lights.size();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public int getObjects(){
    Cloner cloner=new Cloner();
    objects=cloner.deepClone(objects);
    Cloner cloner=new Cloner();
    objects=cloner.deepClone(objects);
    return this.objects.size();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void setObject(  Primitive object,  int pos){
    this.objects.setElementAt(object,pos);
  }
  public ReentrantReadWriteLock getViewLock(){
    Cloner cloner=new Cloner();
    viewLock=cloner.deepClone(viewLock);
    Cloner cloner=new Cloner();
    viewLock=cloner.deepClone(viewLock);
    return viewLock;
  }
}
