package edu.cmu.cs.plural.fractions;
/** 
 * @author Kevin Bierhoff
 */
public interface FractionConstraintVisitor<T> {
  public T relation(  FractionRelation fract);
  public T impossible(  ImpossibleConstraint fract);
}
