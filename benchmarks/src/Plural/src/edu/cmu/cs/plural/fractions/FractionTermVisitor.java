package edu.cmu.cs.plural.fractions;
/** 
 * @author Kevin Bierhoff
 */
public interface FractionTermVisitor<T> {
  public T sum(  FractionSum fract);
  public T literal(  Fraction fract);
}
