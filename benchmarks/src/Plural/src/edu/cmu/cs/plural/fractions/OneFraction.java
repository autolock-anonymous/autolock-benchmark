package edu.cmu.cs.plural.fractions;
import edu.cmu.cs.plural.fractions.elim.NormalizedFractionVisitor;
/** 
 * @author Kevin Bierhoff
 */
public class OneFraction extends Fraction {
  public static final OneFraction INSTANCE=new OneFraction();
  Object obj;
  protected OneFraction(){
    super();
  }
  @Override public boolean isOne(){
    return true;
  }
  @Override public <T>T dispatch(  FractionVisitor<T> visitor){
    obj=new Object();
    return visitor.one(this);
  }
  @Override public <T>T dispatch(  NormalizedFractionVisitor<T> visitor){
    obj=new Object();
    return visitor.one(this);
  }
  @Override public String toString(){
    return "1";
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
  public static OneFraction getINSTANCE(){
    Cloner cloner=new Cloner();
    INSTANCE=cloner.deepClone(INSTANCE);
    Cloner cloner=new Cloner();
    INSTANCE=cloner.deepClone(INSTANCE);
    Cloner cloner=new Cloner();
    INSTANCE=cloner.deepClone(INSTANCE);
    return INSTANCE;
  }
}
