package edu.cmu.cs.crystal.internal;
/** 
 * An interface for mutable objects that can be made immutable by freezing them.
 * @author Nels Beckman
 * @date Feb 20, 2008
 */
public class Freezable<T> {
  /** 
 * Create a copy of this object that can be modified.
 * @return A mutable copy of this object.
 */
  public void mutableCopy(){
  }
  /** 
 * Freeze the state of this object so that future modifying calls are disallowed.
 * @return This newly frozen object.
 */
  public T freeze(){
    return null;
  }
}
