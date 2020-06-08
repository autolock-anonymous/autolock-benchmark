package edu.cmu.cs.plural.fractions.elim;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import edu.cmu.cs.crystal.util.Pair;
import edu.cmu.cs.plural.fractions.Fraction;
import edu.cmu.cs.plural.fractions.NamedFraction;
import edu.cmu.cs.plural.fractions.OneFraction;
import edu.cmu.cs.plural.fractions.FractionRelation.Relop;
/** 
 * @author Kevin Bierhoff
 */
public class NormalizedFractionConstraint extends GeneralizedSum {
  public static NormalizedFractionConstraint createConstraint(  GeneralizedSum left,  Relop relop,  GeneralizedSum right){
    Set<Fraction> fs=new HashSet<Fraction>(left.getFractions());
    fs.addAll(right.getFractions());
    SortedMap<Fraction,Rational> newCoeffs=new TreeMap<Fraction,Rational>();
    for (    Fraction f : fs) {
      newCoeffs.put(f,left.getCoefficient(f).minus(right.getCoefficient(f)));
    }
    return new NormalizedFractionConstraint(newCoeffs,relop);
  }
  public static NormalizedFractionConstraint createConstraintOverload(  Fraction left,  Relop relop,  Fraction right){
    SortedMap<Fraction,Rational> newCoeffs=new TreeMap<Fraction,Rational>();
    newCoeffs.put(left,Rational.one());
    newCoeffs.put(right,Rational.minusOne());
    return new NormalizedFractionConstraint(newCoeffs,relop);
  }
  private final Relop relop;
  private boolean rangeConstraint;
  protected NormalizedFractionConstraint(  SortedMap<Fraction,Rational> coefficients){
    this(coefficients,Relop.EQ);
  }
  protected NormalizedFractionConstraint(  SortedMap<Fraction,Rational> coefficients,  Relop relop){
    super(coefficients,true);
    this.relop=relop;
    this.rangeConstraint=this.coefficients.size() < 2 || (this.coefficients.size() == 2 && this.coefficients.containsKey(Fraction.one()));
  }
  /** 
 * @param x
 * @return Boolean indicates whether the result and the eliminated variable swapped sides of the relational operator(by multiplying with -1).
 */
  public Pair<NormalizedFractionSum,Boolean> isolateFraction(  Fraction x){
    SortedMap<Fraction,Rational> newCoeffs=new TreeMap<Fraction,Rational>();
    Rational r=coefficients.get(x).negation();
    for (    Fraction f : coefficients.keySet()) {
      if (f.equals(x))       continue;
      newCoeffs.put(f,coefficients.get(f).div(r));
    }
    return Pair.create(new NormalizedFractionSum(newCoeffs),!r.isPositive());
  }
  public GeneralizedSum getLeft(){
    return this;
  }
  public Relop getRelop(){
    Cloner cloner=new Cloner();
    relop=cloner.deepClone(relop);
    Cloner cloner=new Cloner();
    relop=cloner.deepClone(relop);
    return relop;
  }
  public GeneralizedSum getRight(){
    return NormalizedFractionSum.zero();
  }
  /** 
 * This method assumes that all variables are positive (zero incl.). Consequently, range constraints (constraining, e.g., a variable to be greater than 0), are never dominated.
 * @param other
 * @return
 */
  public boolean dominates(  NormalizedFractionConstraint other){
    if (this == other)     return true;
    if (this.relop.equals(Relop.EQ) || other.relop.equals(Relop.EQ))     return this.equals(other);
    if (other.isRangeConstraint())     return false;
    for (    Map.Entry<Fraction,Rational> coeff : this.coefficients.entrySet()) {
      Rational thisV=coeff.getValue();
      Rational otherV=other.getCoefficient(coeff.getKey());
      if (thisV.isPositive()) {
        if (otherV.isPositive() && !thisV.isSmallerThan(otherV))         continue;
      }
 else {
        if (otherV.isNegative() || !thisV.isSmallerThan(otherV))         continue;
      }
      return false;
    }
    for (    Map.Entry<Fraction,Rational> otherCoeff : other.coefficients.entrySet()) {
      if (this.coefficients.containsKey(otherCoeff.getKey()) == false) {
        if (otherCoeff.getValue().isPositive())         return false;
      }
    }
    return this.relop.equals(other.relop) || this.relop.equals(Relop.LE);
  }
  private boolean isRangeConstraint(){
    return rangeConstraint;
  }
  /** 
 * Checks if the constraint compares a multiple of one.
 * @return <code>True</code> if constraint only compares a multiple of one, <code>false</code> otherwise.
 * @see OneFraction 
 */
  public boolean isPrimitive(){
    for (    Fraction f : coefficients.keySet())     if ((f instanceof OneFraction) == false)     return false;
    return true;
  }
  public boolean isTriviallyTrue(){
    return isTriviallyTrue(maxValue,minValue,relop);
  }
  private static boolean isTriviallyTrue(  Rational maxValue,  Rational minValue,  Relop relop){
switch (relop) {
case LEQ:
      return !maxValue.isPositive();
case LE:
    return maxValue.isNegative();
case EQ:
  return maxValue.isZero() && minValue.isZero();
default :
throw new UnsupportedOperationException("Unknown relop: " + relop);
}
}
public boolean isTrueWithAssumptions(Map<NamedFraction,NamedFraction> upperBounds){
if (isTriviallyTrue()) return true;
if (namedCoeffs.size() < 2) return false;
switch (relop) {
case EQ:
return false;
case LE:
if (getConstant().isPositive()) return false;
if (getConstant().isZero() && !ground) return false;
break;
case LEQ:
if (getConstant().isPositive()) return false;
break;
}
Rational myMax=maxValue;
Map<NamedFraction,Rational> adjusted=new HashMap<NamedFraction,Rational>(namedCoeffs);
for (Map.Entry<NamedFraction,Rational> named : namedCoeffs.entrySet()) {
if (named.getValue().isPositive()) myMax=myMax.minus(named.getValue());
NamedFraction smaller=named.getKey();
Rational smallerValue=adjusted.get(smaller);
if (smallerValue == null) continue;
search_upper: while (smallerValue.isPositive()) {
NamedFraction upper=upperBounds.get(smaller);
Rational biggerValue=adjusted.get(upper);
while (biggerValue == null) {
upper=upperBounds.get(upper);
if (upper == null) {
break search_upper;
}
biggerValue=adjusted.get(upper);
}
adjusted.remove(smaller);
smallerValue=biggerValue.plus(smallerValue);
smaller=upper;
}
if (named.getKey() != smaller) adjusted.put(smaller,smallerValue);
}
if (myMax.isPositive()) return false;
boolean hasNegativeConstant=false;
for (Map.Entry<NamedFraction,Rational> newCoeff : adjusted.entrySet()) {
if (newCoeff.getValue().isPositive()) {
myMax=myMax.plus(newCoeff.getValue());
}
 else if (newCoeff.getValue().isNegative()) {
hasNegativeConstant=true;
}
}
if (ground && hasNegativeConstant && !myMax.isPositive()) return true;
return isTriviallyTrue(myMax,minValue,relop);
}
@Override public String toString(){
return super.toString() + " " + relop+ " 0";
}
@Override public int hashCode(){
final int prime=31;
int result=super.hashCode();
result=prime * result + (rangeConstraint ? 1231 : 1237);
result=prime * result + ((relop == null) ? 0 : relop.hashCode());
return result;
}
@Override public boolean equals(Object obj){
if (this == obj) return true;
if (!super.equals(obj)) return false;
if (getClass() != obj.getClass()) return false;
NormalizedFractionConstraint other=(NormalizedFractionConstraint)obj;
if (rangeConstraint != other.rangeConstraint) return false;
if (relop == null) {
if (other.relop != null) return false;
}
 else if (!relop.equals(other.relop)) return false;
return true;
}
}
