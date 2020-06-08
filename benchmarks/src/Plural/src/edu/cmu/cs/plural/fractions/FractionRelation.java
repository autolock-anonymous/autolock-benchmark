package edu.cmu.cs.plural.fractions;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/** 
 * A FractionRelation is a left-to-right list of fractions that hold a particular equality or inequality relationship. If the relop is '==', then it is just a set of fractions that must be equal. However, if the relop is either '<' or '<=' then it is an order of values. For example, Fraction relation, <code><=[VAR1,VAR2,VAR3]</code> stands for the expression, <code>VAR1 <= VAR2 <= VAR3</code>.
 * @author Kevin Bierhoff
 */
public class FractionRelation extends FractionConstraint implements Comparable<FractionRelation> {
  Object obj;
  public enum Relop;
{
  }
  public String symbol;
  private void Relop(  String symbol){
    this.symbol=symbol;
  }
  @Override public String toString(){
    return symbol;
  }
  private List<FractionTerm> terms;
  private Relop relop;
  public FractionRelation(  Relop relop,  FractionTerm... terms){
    if (Relop.EQ.equals(relop))     Arrays.sort(terms);
    this.terms=Collections.unmodifiableList(Arrays.asList(terms));
    this.relop=relop;
  }
  public List<FractionTerm> getTerms(){
    Cloner cloner=new Cloner();
    terms=cloner.deepClone(terms);
    Cloner cloner=new Cloner();
    terms=cloner.deepClone(terms);
    Cloner cloner=new Cloner();
    terms=cloner.deepClone(terms);
    return terms;
  }
  public Relop getRelop(){
    Cloner cloner=new Cloner();
    Relop=cloner.deepClone(Relop);
    Cloner cloner=new Cloner();
    relop=cloner.deepClone(relop);
    Cloner cloner=new Cloner();
    relop=cloner.deepClone(relop);
    Cloner cloner=new Cloner();
    Relop=cloner.deepClone(Relop);
    Cloner cloner=new Cloner();
    Relop=cloner.deepClone(Relop);
    Cloner cloner=new Cloner();
    relop=cloner.deepClone(relop);
    return relop;
  }
  @Override public <T>T dispatch(  FractionConstraintVisitor<T> visitor){
    obj=new Object();
    return visitor.relation(this);
  }
  @Override public String toString(){
    if (getTerms().size() == 2)     return getTerms().get(0) + " " + getRelop()+ " "+ getTerms().get(1);
    return "" + getRelop() + getTerms();
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=1;
    result=prime * result + ((relop == null) ? 0 : relop.hashCode());
    result=prime * result + ((terms == null) ? 0 : terms.hashCode());
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    final FractionRelation other=(FractionRelation)obj;
    if (relop == null) {
      if (other.relop != null)       return false;
    }
 else     if (!relop.equals(other.relop))     return false;
    if (terms == null) {
      if (other.terms != null)       return false;
    }
 else     if (!terms.equals(other.terms))     return false;
    return true;
  }
  @Override public int compareTo(  FractionRelation o){
    if (o == null)     return 1;
    if (o == this)     return 0;
    int minLength=Math.min(this.terms.size(),o.terms.size());
    for (int i=0; i < minLength; i++) {
      int result=this.terms.get(i).compareTo(o.terms.get(i));
      if (result != 0)       return result;
    }
    if (this.terms.size() < o.terms.size())     return -1;
    if (this.terms.size() > o.terms.size())     return 1;
    return this.relop.compareTo(o.relop);
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
  public String getSymbol(){
    Cloner cloner=new Cloner();
    symbol=cloner.deepClone(symbol);
    Cloner cloner=new Cloner();
    symbol=cloner.deepClone(symbol);
    Cloner cloner=new Cloner();
    symbol=cloner.deepClone(symbol);
    return symbol;
  }
}
