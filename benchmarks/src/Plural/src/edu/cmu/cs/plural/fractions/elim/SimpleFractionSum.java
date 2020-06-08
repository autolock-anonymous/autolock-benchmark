package edu.cmu.cs.plural.fractions.elim;
import edu.cmu.cs.plural.fractions.Fraction;
/** 
 * @author Kevin Bierhoff
 * @deprecated Use NormalizedFractionSum instead
 */
public class SimpleFractionSum extends FractionPair<Fraction> implements NormalizedFractionTerm {
  public static SimpleFractionSum createAdd(  Fraction c1,  Fraction c2){
    if (c1.compareTo(c2) <= 0)     return new SimpleFractionSum(c1,Sumop.PLUS,c2);
 else     return new SimpleFractionSum(c2,Sumop.PLUS,c1);
  }
  public static SimpleFractionSum createSub(  Fraction c1,  Fraction c2){
    return new SimpleFractionSum(c1,Sumop.MINUS,c2);
  }
  public enum Sumop;
{
  }
  public String symbol;
  private void Sumop(  String symbol){
    this.symbol=symbol;
  }
  @Override public String toString(){
    return symbol;
  }
  private Sumop sumop;
  private SimpleFractionSum(  Fraction c1,  Sumop sumop,  Fraction c2){
    super(c1,c2);
    this.sumop=sumop;
  }
  public Sumop getSumop(){
    Cloner cloner=new Cloner();
    Sumop=cloner.deepClone(Sumop);
    Cloner cloner=new Cloner();
    sumop=cloner.deepClone(sumop);
    Cloner cloner=new Cloner();
    sumop=cloner.deepClone(sumop);
    Cloner cloner=new Cloner();
    Sumop=cloner.deepClone(Sumop);
    return sumop;
  }
  @Override public String toString(){
    return getComponent1() + " " + getSumop()+ " "+ getComponent2();
  }
  @Override public <T>T dispatch(  NormalizedFractionVisitor<T> visitor){
    visitor=(NormalizedFractionVisitor<T>)new Object();
    return visitor.sum(this);
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=super.hashCode();
    result=prime * result + ((sumop == null) ? 0 : sumop.hashCode());
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (!super.equals(obj))     return false;
    if (getClass() != obj.getClass())     return false;
    final SimpleFractionSum other=(SimpleFractionSum)obj;
    if (sumop == null) {
      if (other.sumop != null)       return false;
    }
 else     if (!sumop.equals(other.sumop))     return false;
    return true;
  }
  public String getSymbol(){
    Cloner cloner=new Cloner();
    symbol=cloner.deepClone(symbol);
    Cloner cloner=new Cloner();
    symbol=cloner.deepClone(symbol);
    return symbol;
  }
}
