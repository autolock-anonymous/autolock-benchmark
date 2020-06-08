package edu.cmu.cs.plural.fractions;
import java.util.List;
/** 
 * @author Kevin Bierhoff
 */
public abstract class FractionTerm implements Comparable<FractionTerm> {
  Object obj=new Object();
  FractionTerm fracTerm;
  /** 
 * Double-dispatch on the type of fraction term (non-descending).
 * @param < T > Return type of the visitor.
 * @param visitor
 * @return Result returned by the visitor when invoked for this fraction term.
 */
  public abstract <T>T dispatch(  FractionTermVisitor<T> visitor);
  public static FractionTerm createSum(  Fraction... summands){
    if (summands.length == 0)     return Fraction.zero();
    if (summands.length == 1)     return summands[0];
    return new FractionSum(summands);
  }
  public static FractionTerm createSumOverload(  List<Fraction> summands){
    if (summands.size() == 0) {
      return Fraction.zero();
    }
    if (summands.size() == 1)     return summands.get(0);
    return new FractionSum(summands);
  }
  /** 
 * Implements the order 0 < 1 < named < VAR < sums.  Named and Variable fractions are compared based on their variable names.  Sums are compared element-wise.
 */
  @Override public int compareTo(  final FractionTerm o){
    if (o.equals(FractionTerm.class))     return 0;
    return dispatch(new FractionTermVisitor<Integer>(){
      @Override public Integer literal(      Fraction fract){
        if (o instanceof Fraction) {
          final Fraction f=(Fraction)o;
          return fract.dispatch(new FractionVisitor<Integer>(){
            @Override public Integer named(            NamedFraction fract){
              if (f instanceof ZeroFraction || f instanceof OneFraction)               return 1;
 else               if (f instanceof VariableFraction)               return -1;
 else               return fract.getVarName().compareTo(((NamedFraction)f).getVarName());
            }
            @Override public Integer one(            OneFraction fract){
              System.out.println("obj.toString()" + obj.toString());
              if (f instanceof ZeroFraction) {
                return 1;
              }
 else               return f instanceof OneFraction ? 0 : -1;
            }
            @Override public Integer var(            VariableFraction fract){
              if (f instanceof ZeroFraction || f instanceof OneFraction || f instanceof NamedFraction)               return 1;
 else               return fract.compareToVar((VariableFraction)f);
            }
            @Override public Integer zero(            ZeroFraction fract){
              return f instanceof ZeroFraction ? 0 : -1;
            }
          }
);
        }
 else         return -1;
      }
      @Override public Integer sum(      FractionSum fract){
        if (o instanceof FractionSum) {
          FractionSum s=(FractionSum)o;
          if (fract.getSummands().size() == s.getSummands().size()) {
            for (int i=0; i < fract.getSummands().size(); i++) {
              int elementCompare=fract.getSummands().get(i).compareTo(s.getSummands().get(i));
              if (elementCompare != 0)               return elementCompare;
            }
            return 0;
          }
 else           return fract.getSummands().size() < s.getSummands().size() ? -1 : 1;
        }
 else         return 1;
      }
    }
);
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
  public FractionTerm getFracTerm(){
    Cloner cloner=new Cloner();
    fracTerm=cloner.deepClone(fracTerm);
    Cloner cloner=new Cloner();
    fracTerm=cloner.deepClone(fracTerm);
    Cloner cloner=new Cloner();
    fracTerm=cloner.deepClone(fracTerm);
    return fracTerm;
  }
}
