package edu.cmu.cs.plural.fractions;
import edu.cmu.cs.plural.fractions.FractionRelation.Relop;
/** 
 * @author Kevin Bierhoff
 */
public abstract class FractionConstraint {
  public abstract <T>T dispatch(  FractionConstraintVisitor<T> visitor);
  public static FractionConstraint createEquality(  FractionTerm... terms){
    return new FractionRelation(Relop.EQ,terms);
  }
  public static FractionConstraint createLessThan(  FractionTerm... terms){
    return new FractionRelation(Relop.LE,terms);
  }
  public static FractionConstraint createLessThanOrEqual(  FractionTerm... terms){
    return new FractionRelation(Relop.LEQ,terms);
  }
  public static FractionConstraint impossible(){
    return ImpossibleConstraint.INSTANCE;
  }
  /** 
 * @tag todo.general -id="3107857" : include problem in impossible constraints
 */
  public static FractionConstraint impossible(  String reason){
    return ImpossibleConstraint.INSTANCE;
  }
}
