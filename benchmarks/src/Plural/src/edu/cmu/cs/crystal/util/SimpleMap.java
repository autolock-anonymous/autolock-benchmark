package edu.cmu.cs.crystal.util;
/** 
 * That's right, a really simple map interface. The only thing you can do with it is get things. Check out getInvariantPermissions in PluralTupleLatticeElement to see how I use this to create a lazy map. Note that there is no real good reason to have this interface now that we have Lambda, except for the possibly more precise implication.
 * @author Nels Beckman
 * @date Mar 26, 2008
 * @param < K >
 * @param < V >
 */
public interface SimpleMap<K,V> {
  public V get(  K key);
}
