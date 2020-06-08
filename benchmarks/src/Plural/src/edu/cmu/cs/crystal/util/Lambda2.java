package edu.cmu.cs.crystal.util;
/** 
 * A 'lambda,' or first-class function, that takes two arguments and returns one argument.
 * @author Nels E. Beckman
 * @since Sep 9, 2008
 * @param < I1 > The type of the first argument.
 * @param < I2 > The type of the second argument.
 * @param < O > The type of the output.
 */
public interface Lambda2<I1,I2,O> {
  public O call(  I1 i1,  I2 i2);
}
