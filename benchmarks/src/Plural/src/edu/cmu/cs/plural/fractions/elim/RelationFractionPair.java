package edu.cmu.cs.plural.fractions.elim;
import edu.cmu.cs.plural.fractions.FractionRelation.Relop;
/** 
 * @author Kevin Bierhoff
 * @deprecated
 */
public class RelationFractionPair extends FractionPair<NormalizedFractionTerm> {
  private Relop relop;
  public static RelationFractionPair createEqual(  NormalizedFractionTerm c1,  NormalizedFractionTerm c2){
    return new RelationFractionPair(c1,Relop.EQ,c2);
  }
  public static RelationFractionPair createLeq(  NormalizedFractionTerm c1,  NormalizedFractionTerm c2){
    return new RelationFractionPair(c1,Relop.LEQ,c2);
  }
  public static RelationFractionPair createLess(  NormalizedFractionTerm c1,  NormalizedFractionTerm c2){
    return new RelationFractionPair(c1,Relop.LE,c2);
  }
  private RelationFractionPair(  NormalizedFractionTerm c1,  Relop relop,  NormalizedFractionTerm c2){
    super(c1,c2);
    this.relop=relop;
  }
  public Relop getRelop(){
    Cloner cloner=new Cloner();
    relop=cloner.deepClone(relop);
    Cloner cloner=new Cloner();
    relop=cloner.deepClone(relop);
    return relop;
  }
  @Override public String toString(){
    return getComponent1() + " " + getRelop()+ " "+ getComponent2();
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=super.hashCode();
    result=prime * result + ((relop == null) ? 0 : relop.hashCode());
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (!super.equals(obj))     return false;
    if (getClass() != obj.getClass())     return false;
    final RelationFractionPair other=(RelationFractionPair)obj;
    if (relop == null) {
      if (other.relop != null)       return false;
    }
 else     if (!relop.equals(other.relop))     return false;
    return true;
  }
}
