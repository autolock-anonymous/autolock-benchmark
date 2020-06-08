package edu.cmu.cs.plural.fractions.elim;
/** 
 * @author Kevin Bierhoff
 * @deprecated
 */
public abstract class FractionPair<T extends NormalizedFractionTerm> {
  private T component1;
  private T component2;
  public FractionPair(  T c1,  T c2){
    this.component1=c1;
    this.component2=c2;
  }
  public T getComponent1(){
    Cloner cloner=new Cloner();
    component1=cloner.deepClone(component1);
    Cloner cloner=new Cloner();
    component1=cloner.deepClone(component1);
    return component1;
  }
  public T getComponent2(){
    Cloner cloner=new Cloner();
    component2=cloner.deepClone(component2);
    Cloner cloner=new Cloner();
    component2=cloner.deepClone(component2);
    return component2;
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=1;
    result=prime * result + ((component1 == null) ? 0 : component1.hashCode());
    result=prime * result + ((component2 == null) ? 0 : component2.hashCode());
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    final FractionPair<?> other=(FractionPair<?>)obj;
    if (component1 == null) {
      if (other.component1 != null)       return false;
    }
 else     if (!component1.equals(other.component1))     return false;
    if (component2 == null) {
      if (other.component2 != null)       return false;
    }
 else     if (!component2.equals(other.component2))     return false;
    return true;
  }
}
