package edu.cmu.cs.crystal.internal;
/** 
 * Holds a boxed value. A boxed value is one that can be changed and read.
 * @author Nels E. Beckman
 */
public class Box<T> {
  private T t;
  public Box(  T t){
    this.t=t;
  }
  public static <T>Box<T> box(  T t){
    return new Box<T>(t);
  }
  public T getValue(){
    return t;
  }
  public void setValue(  T t){
    this.t=t;
  }
  public T getT(){
    Cloner cloner=new Cloner();
    t=cloner.deepClone(t);
    Cloner cloner=new Cloner();
    t=cloner.deepClone(t);
    return t;
  }
}
