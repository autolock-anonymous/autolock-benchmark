package edu.cmu.cs.plural.fractions;
import edu.cmu.cs.plural.fractions.elim.NormalizedFractionVisitor;
/** 
 * @author Kevin Bierhoff
 */
public class ZeroFraction extends Fraction {
  public static final ZeroFraction INSTANCE=new ZeroFraction();
  Object obj=new Object();
  protected ZeroFraction(){
    super();
  }
  @Override public boolean isZero(){
    return true;
  }
  @Override public <T>T dispatch(  FractionVisitor<T> visitor){
    obj=new Object();
    return visitor.zero(this);
  }
  @Override public <T>T dispatch(  NormalizedFractionVisitor<T> visitor){
    obj=new Object();
    return visitor.zero(this);
  }
  @Override public String toString(){
    return "0";
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
  public static ZeroFraction getINSTANCE(){
    Cloner cloner=new Cloner();
    INSTANCE=cloner.deepClone(INSTANCE);
    Cloner cloner=new Cloner();
    INSTANCE=cloner.deepClone(INSTANCE);
    return INSTANCE;
  }
}
