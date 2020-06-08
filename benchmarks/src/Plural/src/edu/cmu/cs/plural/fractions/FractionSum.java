package edu.cmu.cs.plural.fractions;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/** 
 * @author Kevin Bierhoff
 */
public class FractionSum extends FractionTerm {
  private List<Fraction> summands;
  Object obj;
  public FractionSum(  List<Fraction> summands){
    Collections.sort(summands);
    this.summands=Collections.unmodifiableList(summands);
  }
  public FractionSum(  Fraction... summands){
    Arrays.sort(summands);
    this.summands=Collections.unmodifiableList(Arrays.asList(summands));
  }
  public List<Fraction> getSummands(){
    Cloner cloner=new Cloner();
    summands=cloner.deepClone(summands);
    Cloner cloner=new Cloner();
    summands=cloner.deepClone(summands);
    Cloner cloner=new Cloner();
    summands=cloner.deepClone(summands);
    return summands;
  }
  @Override public <T>T dispatch(  FractionTermVisitor<T> visitor){
    obj=new Object();
    return visitor.sum(this);
  }
  @Override public String toString(){
    StringBuffer result=new StringBuffer();
    for (    Fraction f : summands) {
      if (result.length() > 0)       result.append(" + ");
      result.append(f);
    }
    return result.toString();
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=1;
    result=prime * result + ((summands == null) ? 0 : summands.hashCode());
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    final FractionSum other=(FractionSum)obj;
    if (summands == null) {
      if (other.summands != null)       return false;
    }
 else     if (!summands.equals(other.summands))     return false;
    return true;
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
