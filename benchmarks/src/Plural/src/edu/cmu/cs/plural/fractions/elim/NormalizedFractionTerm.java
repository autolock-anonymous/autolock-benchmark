package edu.cmu.cs.plural.fractions.elim;
/** 
 * @author Kevin Bierhoff
 * @deprecated
 */
public interface NormalizedFractionTerm {
  public <T>T dispatch(  NormalizedFractionVisitor<T> visitor);
}
