package edu.cmu.cs.crystal.util;
/** 
 * A 'lambda,' or first-class function, that takes one argument and returns one argument.
 * @author Nels E. Beckman
 * @since Sep 9, 2008
 * @param < I > The type of the input to this lambda.
 * @param < O > The type of the output from this lambda.
 */
public interface Lambda<I,O> {
  public O call(  I i);
}
