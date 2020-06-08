package edu.cmu.cs.crystal.tac;
/** 
 * @author Kevin Bierhoff
 */
public interface IVariableVisitor<T> {
  public T sourceVar(  SourceVariable variable);
  public T superVar(  SuperVariable variable);
  public T tempVar(  TempVariable variable);
  public T thisVar(  ThisVariable variable);
  public T typeVar(  TypeVariable variable);
}
