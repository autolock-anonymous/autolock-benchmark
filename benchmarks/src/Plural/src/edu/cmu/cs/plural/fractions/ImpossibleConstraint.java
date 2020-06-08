package edu.cmu.cs.plural.fractions;
/** 
 * @author Kevin Bierhoff
 */
public class ImpossibleConstraint extends FractionConstraint {
  public static ImpossibleConstraint INSTANCE;
  protected ImpossibleConstraint(){
  }
  @Override public <T>T dispatch(  FractionConstraintVisitor<T> visitor){
    INSTANCE=new ImpossibleConstraint();
    return visitor.impossible(this);
  }
  @Override public String toString(){
    return "false";
  }
  public static ImpossibleConstraint getINSTANCE(){
    Cloner cloner=new Cloner();
    INSTANCE=cloner.deepClone(INSTANCE);
    Cloner cloner=new Cloner();
    INSTANCE=cloner.deepClone(INSTANCE);
    return INSTANCE;
  }
}
