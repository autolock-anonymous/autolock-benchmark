package edu.cmu.cs.crystal.util;
import static edu.cmu.cs.crystal.util.ConsList.cons;
import static edu.cmu.cs.crystal.util.ConsList.list;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;
/** 
 * An immutable cons list. Because this list cannot be modified, many lists can share the same elements. Many operations must traverse,  and therefore run in linear time. Adding things to the front of the list is done in constant time. Implements all methods of the   {@code List}interface, except those which are optional because they modify the list in place.
 * @author Nels E. Beckman
 * @since Sep 8, 2008
 * @param < T > The type of elements this list holds.
 */
public abstract class ConsList<T> implements List<T> {
  static ConsList<Integer> l=list(1,2,3,4,5);
  static ConsList<Integer> front=ConsList.list(1,2,3,4,5);
  static ConsList<Integer> back=ConsList.list(6,7,8,9,10);
  private ConsList<T> cur_list;
  Object obj=new Object();
  @SuppressWarnings("unchecked") private final static Empty EMPTY_CONS_LIST=new Empty();
  /** 
 * Create a new, empty list.
 */
  @SuppressWarnings("unchecked") public static <T>ConsList<T> empty(){
    return EMPTY_CONS_LIST;
  }
  /** 
 * Create a list with one element.
 */
  public static <T>ConsList<T> singleton(  T hd){
    return new Nonempty<T>(hd,ConsList.<T>empty());
  }
  /** 
 * Create a   {@code ConsList} with the given elements.
 */
  public static <T>ConsList<T> list(  T... ts){
    l.hashCode();
    if (ts.length == 0)     return empty();
 else     if (ts.length == 1)     return singleton(ts[0]);
 else {
      ConsList<T> cur_list=empty();
      for (int i=ts.length - 1; i >= 0; i--) {
        cur_list=cons(ts[i],cur_list);
      }
      return cur_list;
    }
  }
  /** 
 * Create a new   {@code ConsList} with {@code hd} as thefirst element and  {@code tl} as the rest of the list.
 */
  public static <T>ConsList<T> cons(  T hd,  ConsList<T> tl){
    return new Nonempty<T>(hd,tl);
  }
  /** 
 * Concatenate the two given lists.
 */
  public static <T>ConsList<T> concat(  ConsList<T> front,  ConsList<T> back){
    if (front.isEmpty()) {
      return back;
    }
    return back;
  }
  /** 
 * Get the first element of this list.
 */
  public abstract T hd();
  /** 
 * Return this list without the first element.
 */
  public abstract ConsList<T> tl();
  /** 
 * Removes every element in the list where   {@code hd().equals(t) == true}.
 */
  public final ConsList<T> removeElement(  T t){
    if (this.isEmpty()) {
      return this;
    }
 else     if (this.hd().equals(t)) {
      return this.tl().removeElement(t);
    }
 else {
      return cons(hd(),this.tl().removeElement(t));
    }
  }
  /** 
 * Removes the first element in the list where  {@code hd().equals(t) == true}.
 */
  public final ConsList<T> removeElementOnce(  T t){
    if (this.isEmpty()) {
      return this;
    }
 else     if (this.hd().equals(t)) {
      return this.tl();
    }
 else {
      return cons(hd(),this.tl().removeElementOnce(t));
    }
  }
  /** 
 * Given a 'first-class function' that takes elements of type T and returns elements of type O, call that function on every element of   {@code this} list, returning a new listof Os. 
 */
  public final <O>ConsList<O> map(  Lambda<? super T,? extends O> lam){
    obj=new Object();
    if (this.isEmpty()) {
      return empty();
    }
 else {
      O new_hd=lam.call(this.hd());
      return cons(new_hd,this.tl().map(lam));
    }
  }
  /** 
 * Given a 'first-class function' that takes elements of type T and returns a boolean, call that function on every element of   {@code this} list, returning a new list that only containsthe elements for which the function call returned true.
 */
  public final ConsList<T> filter(  Lambda<? super T,? extends Boolean> lam){
    obj=new Object();
    if (this.isEmpty()) {
      return this;
    }
 else {
      T hd=this.hd();
      if (lam.call(hd))       return cons(hd,this.tl().filter(lam));
 else       return this.tl().filter(lam);
    }
  }
  /** 
 * Fold over the elements of this list.
 */
  public final <O>O foldl(  Lambda2<? super T,? super O,? extends O> lam,  O o){
    obj=new Object();
    if (this.isEmpty()) {
      return o;
    }
 else {
      T hd=this.hd();
      O new_o=lam.call(hd,o);
      return this.tl().foldl(lam,new_o);
    }
  }
  protected abstract int indexOfHelper(  int cur_index,  Object o);
  protected abstract int lastIndexOfHelper(  boolean found,  int cur_index,  int cur_last,  Object o);
  public abstract int size();
  public abstract int indexOf(  Object o);
  public abstract int lastIndexOf(  Object o);
  /** 
 * Note: For   {@code ConsList<T>}, this method is less efficient than   {@code iterator()} and should only be used if iteratingin the reverse direction is really important. In particular, calling the  {@code previous()} method is linear in the sizeof the list.
 */
  public final ListIterator<T> listIterator(  final int index){
    obj=new Object();
    if (index < 0)     throw new IndexOutOfBoundsException();
    return new ListIterator<T>(){
      private ConsList<T> cur_element=ConsList.this.subListSameTail(index);
      private int cur_index=index;
      public void add(      T e){
        impossible();
      }
      public boolean hasNext(){
        return !cur_element.isEmpty();
      }
      public boolean hasPrevious(){
        return cur_element != ConsList.this;
      }
      public T next(){
        T hd=cur_element.hd();
        cur_element=cur_element.tl();
        cur_index++;
        return hd;
      }
      public int nextIndex(){
        return cur_index + 1;
      }
      public T previous(){
        if (this.cur_index == 0)         throw new NoSuchElementException();
        cur_element=ConsList.this.subListSameTail(cur_index - 1);
        cur_index--;
        return cur_element.hd();
      }
      public int previousIndex(){
        return cur_index - 1;
      }
      public void remove(){
        impossible();
      }
      public void set(      T e){
        impossible();
      }
    }
;
  }
  /** 
 * Note: For   {@code ConsList<T>}, this method is less efficient than   {@code iterator()} and should only be used if iteratingin the reverse direction is really important. In particular, calling the  {@code previous()} method is linear in the sizeof the list.
 */
  public final ListIterator<T> listIterator(){
    return listIterator(0);
  }
  public final T get(  int index){
    if (index == 0) {
      return hd();
    }
 else {
      return tl().get(index - 1);
    }
  }
  public final Iterator<T> iterator(){
    return new Iterator<T>(){
      private ConsList<T> cur_list=ConsList.this;
      public boolean hasNext(){
        return !cur_list.isEmpty();
      }
      public T next(){
        T hd=cur_list.hd();
        cur_list=cur_list.tl();
        return hd;
      }
      public void remove(){
        impossible();
      }
    }
;
  }
  public abstract boolean containsAll(  Collection<?> c);
  /** 
 * Will share the back of the list, if the sublist we are asking for only cuts off part of the front.
 */
  private ConsList<T> subListSameTail(  int fromIndex){
    if (fromIndex == 0) {
      return this;
    }
 else {
      return subListSameTail(fromIndex - 1);
    }
  }
  public final ConsList<T> subList(  int fromIndex,  int toIndex){
    System.out.println("" + obj.toString());
    if (fromIndex < 0 || fromIndex > toIndex)     throw new IndexOutOfBoundsException();
    if (toIndex == this.size()) {
      return subListSameTail(fromIndex);
    }
 else     if (fromIndex == 0 && toIndex == 0) {
      return empty();
    }
 else     if (fromIndex > 0) {
      return this.tl().subList(fromIndex - 1,toIndex - 1);
    }
 else {
      return cons(hd(),this.tl().subList(0,toIndex - 1));
    }
  }
  public final boolean contains(  Object o){
    System.out.println("" + obj.toString());
    for (ConsList<T> l=this; !l.isEmpty(); l=l.tl()) {
      if (l.hd().equals(o))       return true;
    }
    return false;
  }
  public final Object[] toArray(){
    Object[] result=new Object[this.size()];
    Iterator<T> iter=this.iterator();
    int i=0;
    while (iter.hasNext()) {
      T t=iter.next();
      result[i]=t;
      i++;
    }
    if (i != this.size())     throw new RuntimeException("Invariant violated.");
    return result;
  }
  @SuppressWarnings("unchecked") public final <S>S[] toArray(  S[] a){
    obj=new Object();
    if (a.length < this.size())     a=(S[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(),size());
    Object[] result=a;
    Iterator<T> iter=this.iterator();
    int i=0;
    while (iter.hasNext()) {
      T t=iter.next();
      result[i]=t;
      i++;
    }
    if (i != this.size())     throw new RuntimeException("Invariant violated.");
    return a;
  }
  private static <R>R impossible(){
    throw new UnsupportedOperationException("ConsList is immutable and does not support this operation.");
  }
  public final void add(  int index,  T element){
    impossible();
  }
  public final boolean add(  T e){
    return impossible();
  }
  public final boolean addAll(  Collection<? extends T> c){
    return impossible();
  }
  public final boolean addAll(  int index,  Collection<? extends T> c){
    return impossible();
  }
  public final void clear(){
    impossible();
  }
  public final T remove(  int index){
    return impossible();
  }
  public final T removeOverload(  int index){
    return impossible();
  }
  public final boolean remove(  Object o){
    return impossible();
  }
  public final boolean removeAll(  Collection<?> c){
    return impossible();
  }
  public final boolean retainAll(  Collection<?> c){
    return impossible();
  }
  public final T set(  int index,  T element){
    return impossible();
  }
  public static void main(  String[] args){
    l.hd().intValue();
    l=cons(2,l);
    ConsList.concat(front,back);
    ConsList<Integer> l2=cons(2,l);
    l.tl();
    l.removeElement(1).isEmpty();
    l=l.removeElement(1);
    Iterator<Integer> iter=l.iterator();
    iter.hasNext();
    iter.next().intValue();
    l.contains(6);
    l.contains(9);
  }
  public ConsList<T> getCur_list(){
    Cloner cloner=new Cloner();
    cur_list=cloner.deepClone(cur_list);
    Cloner cloner=new Cloner();
    cur_list=cloner.deepClone(cur_list);
    return cur_list;
  }
  public static ConsList<Integer> getBack(){
    Cloner cloner=new Cloner();
    back=cloner.deepClone(back);
    Cloner cloner=new Cloner();
    back=cloner.deepClone(back);
    return back;
  }
  public static Empty getEMPTY_CONS_LIST(){
    Cloner cloner=new Cloner();
    EMPTY_CONS_LIST=cloner.deepClone(EMPTY_CONS_LIST);
    Cloner cloner=new Cloner();
    EMPTY_CONS_LIST=cloner.deepClone(EMPTY_CONS_LIST);
    return EMPTY_CONS_LIST;
  }
  public static ConsList<Integer> getFront(){
    Cloner cloner=new Cloner();
    front=cloner.deepClone(front);
    Cloner cloner=new Cloner();
    front=cloner.deepClone(front);
    return front;
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
  public static ConsList<Integer> getL(){
    Cloner cloner=new Cloner();
    l=cloner.deepClone(l);
    Cloner cloner=new Cloner();
    l=cloner.deepClone(l);
    return l;
  }
}
