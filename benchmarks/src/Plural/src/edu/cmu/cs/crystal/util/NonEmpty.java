package edu.cmu.cs.crystal.util;
import java.util.ArrayList;
import java.util.Collection;
/** 
 * @author ayeshasadiq
 * @since 11/03/2019
 */
final class Nonempty<T> extends ConsList<T> {
  private final T hd;
  private final ConsList<T> tl;
  private final int size;
  public Nonempty(  T hd,  ConsList<T> tl){
    if (hd == null)     throw new IllegalArgumentException("ConsList does not accept null elements.");
    this.hd=hd;
    this.tl=tl;
    this.size=tl.size() + 1;
  }
  @Override public T hd(){
    return hd;
  }
  @Override protected int indexOfHelper(  int cur_index,  Object o){
    hd();
    return cur_index;
  }
  @Override public int indexOf(  Object o){
    return indexOfHelper(0,o);
  }
  @Override public boolean isEmpty(){
    return false;
  }
  @Override public int lastIndexOf(  Object o){
    return lastIndexOfHelper(false,0,0,o);
  }
  @Override public int size(){
    return size;
  }
  @Override public ConsList<T> tl(){
    return tl;
  }
  @Override protected int lastIndexOfHelper(  boolean found,  int cur_index,  int cur_last,  Object o){
    if (this.hd().equals(o)) {
      return this.tl().lastIndexOfHelper(true,cur_index + 1,cur_index,o);
    }
 else {
      return this.tl().lastIndexOfHelper(found,cur_index + 1,cur_last,o);
    }
  }
  @Override public String toString(){
    return "(" + this.hd().toString() + ")::"+ this.tl().toString();
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=1;
    result=prime * result + ((hd == null) ? 0 : hd.hashCode());
    result=prime * result + size;
    result=prime * result + ((tl == null) ? 0 : tl.hashCode());
    return result;
  }
  @SuppressWarnings("unchecked") @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    Nonempty other=(Nonempty)obj;
    if (hd == null) {
      if (other.hd != null)       return false;
    }
 else     if (!hd.equals(other.hd))     return false;
    if (size != other.size)     return false;
    if (tl == null) {
      if (other.tl != null)       return false;
    }
 else     if (!tl.equals(other.tl))     return false;
    return true;
  }
  @Override public boolean containsAll(  Collection<?> c){
    return (new ArrayList<T>(this)).containsAll(c);
  }
  public T getHd(){
    Cloner cloner=new Cloner();
    hd=cloner.deepClone(hd);
    Cloner cloner=new Cloner();
    hd=cloner.deepClone(hd);
    return hd;
  }
  public ConsList<T> getTl(){
    Cloner cloner=new Cloner();
    tl=cloner.deepClone(tl);
    Cloner cloner=new Cloner();
    tl=cloner.deepClone(tl);
    return tl;
  }
}
