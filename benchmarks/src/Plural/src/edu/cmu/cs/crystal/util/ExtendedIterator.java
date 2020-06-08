package edu.cmu.cs.crystal.util;
import java.util.Iterator;
/** 
 * This interface extends regular iterators with the ability to replace  the last element seen, using   {@link #replace(Object)}.
 * @author Kevin Bierhoff
 * @param < T >
 */
public interface ExtendedIterator<T> extends Iterator<T> {
  /** 
 * Replaces in the underlying collection the last element returned by the iterator with the given value. This method can be called only once per call to   {@link java.util.Iterator#next()}, and only if   {@link java.util.Iterator#remove()}was not called since the last call to <code>next</code>.  Calling <code>remove</code> after calling this method is also not permitted.  The behavior of an iterator is unspecified if the underlying collection is modified  while the iteration is in progress in any way other than by calling this method or <code>remove</code>.
 * @param newValue
 * @throws IllegalStateException If the <code>next</code> method has not yet been called, or <code>remove</code> or <code>replace</code> has already been called  after the last call to the <code>next</code> method.
 * @see java.util.Iterator#remove()
 */
  public void replace(  T newValue);
}
