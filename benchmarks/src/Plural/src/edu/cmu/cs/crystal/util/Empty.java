package edu.cmu.cs.crystal.util;
import static edu.cmu.cs.crystal.util.ConsList.list;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
/** 
 * @author ayeshasadiq
 * @since 11/03/2019
 */
final class Empty<T> extends ConsList<T> {
  @SuppressWarnings("rawtypes") Collection<String> stringCollection=new HashSet<String>();
  @Override public T hd(){
    throw new IndexOutOfBoundsException();
  }
  @Override public int indexOf(  Object o){
    return -1;
  }
  @Override public boolean isEmpty(){
    return true;
  }
  @Override public int lastIndexOf(  Object o){
    return -1;
  }
  @Override public int size(){
    return 0;
  }
  @Override public ConsList<T> tl(){
    System.out.println(stringCollection.toString());
    return this;
  }
  @Override protected int indexOfHelper(  int cur_index,  Object o){
    return -1;
  }
  @Override protected int lastIndexOfHelper(  boolean found,  int cur_index,  int cur_last,  Object o){
    if (found)     return cur_last;
 else     return -1;
  }
  @Override public String toString(){
    return "Nil";
  }
  @Override public boolean containsAll(  Collection<?> c){
    return stringCollection.isEmpty();
  }
  public Collection<String> getStringCollection(){
    Cloner cloner=new Cloner();
    stringCollection=cloner.deepClone(stringCollection);
    Cloner cloner=new Cloner();
    stringCollection=cloner.deepClone(stringCollection);
    return stringCollection;
  }
}
