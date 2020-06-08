package edu.cmu.cs.plural.fractions;
/** 
 * @author Kevin Bierhoff
 */
public interface FractionVisitor<T> {
  public T one(  OneFraction fract);
  public T zero(  ZeroFraction fract);
  public T named(  NamedFraction fract);
  public T var(  VariableFraction fract);
}
