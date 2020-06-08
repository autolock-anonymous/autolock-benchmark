package edu.cmu.cs.plural.fractions;
/** 
 * @author Kevin
 */
public class AbstractFractionTermVisitor<T> implements FractionVisitor<T>, FractionTermVisitor<T> {
  Object obj;
  @Override public T named(  NamedFraction fract){
    return null;
  }
  @Override public T one(  OneFraction fract){
    return null;
  }
  @Override public T var(  VariableFraction fract){
    return null;
  }
  @Override public T zero(  ZeroFraction fract){
    return null;
  }
  @Override public T literal(  Fraction fract){
    obj=new Object();
    return fract.dispatch((FractionVisitor<T>)this);
  }
  @Override public T sum(  FractionSum fract){
    return null;
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
