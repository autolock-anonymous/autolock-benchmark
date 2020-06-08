package edu.cmu.cs.plural.fractions;
import edu.cmu.cs.plural.fractions.elim.NormalizedFractionVisitor;
/** 
 * @author Kevin Bierhoff
 */
public class VariableFraction extends Fraction {
  private static long nextID=0;
  private long id;
  private Object obj;
  public VariableFraction(){
    this.id=nextID++;
  }
  public String getVarName(){
    return "VAR" + id;
  }
  @Override public boolean isVariable(){
    return true;
  }
  @Override public <T>T dispatch(  FractionVisitor<T> visitor){
    obj=new Object();
    return visitor.var(this);
  }
  @Override public <T>T dispatch(  NormalizedFractionVisitor<T> visitor){
    obj=new Object();
    return visitor.var(this);
  }
  /** 
 * Implementation of   {@link Comparable#compareTo(Object)} withdifferent name due to Java restriction to one polymorphic type instantiation.
 * @param other the object to be compared.
 * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
 * @throws NullPointerException if <code>other</code> is <code>null</code>.
 */
  public int compareToVar(  VariableFraction other){
    long otherID=other.id;
    long thisID=this.id;
    return Long.signum(thisID - otherID);
  }
  @Override public String toString(){
    return getVarName();
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=1;
    result=prime * result + (int)(id ^ (id >>> 32));
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    VariableFraction other=(VariableFraction)obj;
    if (id != other.id)     return false;
    return true;
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
