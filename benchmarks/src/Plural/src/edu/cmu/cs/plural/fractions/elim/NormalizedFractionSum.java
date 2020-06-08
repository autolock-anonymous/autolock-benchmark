package edu.cmu.cs.plural.fractions.elim;
import java.util.List;
import java.util.Map;
import edu.cmu.cs.plural.fractions.Fraction;
/** 
 * @author Kevin Bierhoff
 */
public class NormalizedFractionSum extends GeneralizedSum {
  private static final NormalizedFractionSum SUM_ZERO=new NormalizedFractionSum();
  public static NormalizedFractionSum zero(){
    return SUM_ZERO;
  }
  /** 
 * @param coefficients
 */
  public NormalizedFractionSum(  Map<Fraction,Rational> coefficients){
    super(coefficients);
  }
  /** 
 * @param fractions
 */
  public NormalizedFractionSum(  Fraction... fractions){
    super(fractions);
  }
  /** 
 * @param multiplier
 * @param fractions
 */
  public NormalizedFractionSum(  int multiplier,  Fraction... fractions){
    super(multiplier,fractions);
  }
  public NormalizedFractionSum(  List<Fraction> fractions){
    super(fractions.toArray(new Fraction[fractions.size()]));
  }
  public static NormalizedFractionSum getSUM_ZERO(){
    Cloner cloner=new Cloner();
    SUM_ZERO=cloner.deepClone(SUM_ZERO);
    Cloner cloner=new Cloner();
    SUM_ZERO=cloner.deepClone(SUM_ZERO);
    return SUM_ZERO;
  }
}
