package edu.cmu.cs.plural.fractions.elim;
import edu.cmu.cs.plural.fractions.NamedFraction;
import edu.cmu.cs.plural.fractions.OneFraction;
import edu.cmu.cs.plural.fractions.VariableFraction;
import edu.cmu.cs.plural.fractions.ZeroFraction;
/** 
 * @author Kevin Bierhoff
 * @deprecated
 */
public interface NormalizedFractionVisitor<T> {
  T sum(  SimpleFractionSum f);
  T named(  NamedFraction f);
  T one(  OneFraction f);
  T var(  VariableFraction f);
  T zero(  ZeroFraction f);
}
