package edu.cmu.cs.plural.fractions.elim;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import edu.cmu.cs.plural.fractions.Fraction;
import edu.cmu.cs.plural.fractions.NamedFraction;
/** 
 * @author Kevin Bierhoff
 */
public abstract class GeneralizedSum {
  /** 
 * Should not mutate this map after construction finished.
 */
  protected final SortedMap<Fraction,Rational> coefficients=new TreeMap<Fraction,Rational>();
  protected final SortedMap<NamedFraction,Rational> namedCoeffs=new TreeMap<NamedFraction,Rational>();
  protected Rational minValue=Rational.zero(), maxValue=Rational.zero();
  protected boolean ground=true;
  protected GeneralizedSum(){
  }
  public GeneralizedSum(  Map<Fraction,Rational> coefficients){
    for (    final Fraction f : coefficients.keySet()) {
      if (f.isZero())       continue;
      Rational c=coefficients.get(f);
      if (c.isZero())       continue;
      this.coefficients.put(f,c);
      if (f.isOne()) {
        maxValue=maxValue.plus(c);
        minValue=minValue.plus(c);
      }
 else {
        if (c.isPositive()) {
          maxValue=maxValue.plus(c);
        }
 else {
          minValue=minValue.plus(c);
        }
      }
      if (f.isVariable()) {
        ground=false;
      }
 else       if (f.isNamed()) {
        this.namedCoeffs.put((NamedFraction)f,c);
      }
    }
  }
  public GeneralizedSum(  SortedMap<Fraction,Rational> coefficients,  boolean normalizeCoeffs){
    Rational scale=null;
    for (    final Fraction f : coefficients.keySet()) {
      if (f.isZero())       continue;
      Rational c=coefficients.get(f);
      if (c.isZero())       continue;
      if (scale == null) {
        scale=c.abs();
      }
      if (normalizeCoeffs)       c=c.div(scale);
      this.coefficients.put(f,c);
      if (f.isOne()) {
        maxValue=maxValue.plus(c);
        minValue=minValue.plus(c);
      }
 else {
        if (c.isPositive()) {
          maxValue=maxValue.plus(c);
        }
 else {
          minValue=minValue.plus(c);
        }
      }
      if (f.isVariable()) {
        ground=false;
      }
 else       if (f.isNamed()) {
        this.namedCoeffs.put((NamedFraction)f,c);
      }
    }
  }
  public GeneralizedSum(  Fraction... fractions){
    this(1,fractions);
  }
  public GeneralizedSum(  int multiplier,  Fraction... fractions){
    if (multiplier == 0)     return;
    Rational r=new Rational(multiplier);
    for (    Fraction f : fractions) {
      if (f.isZero())       continue;
      this.coefficients.put(f,r);
    }
  }
  /** 
 * @return
 */
  public Set<Fraction> getFractions(){
    return Collections.unmodifiableSet(coefficients.keySet());
  }
  /** 
 * @param f
 * @return
 */
  public Rational getCoefficient(  Fraction f){
    Rational result=coefficients.get(f);
    if (result == null)     return Rational.zero();
 else     return result;
  }
  public Rational getConstant(){
    return getCoefficient(Fraction.one());
  }
  public boolean isGround(){
    return ground;
  }
  @Override public String toString(){
    StringBuilder result=new StringBuilder();
    if (coefficients.isEmpty())     result.append("0");
    for (    Fraction f : coefficients.keySet()) {
      if (result.length() > 0)       result.append("+");
      result.append(coefficients.get(f) + "*" + f);
    }
    return result.toString();
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=1;
    result=prime * result + ((coefficients == null) ? 0 : coefficients.hashCode());
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    final GeneralizedSum other=(GeneralizedSum)obj;
    if (coefficients == null) {
      if (other.coefficients != null)       return false;
    }
 else     if (!coefficients.equals(other.coefficients))     return false;
    return true;
  }
  public Rational getMinValue(){
    Cloner cloner=new Cloner();
    minValue=cloner.deepClone(minValue);
    Cloner cloner=new Cloner();
    minValue=cloner.deepClone(minValue);
    return minValue;
  }
  public SortedMap<NamedFraction,Rational> getNamedCoeffs(){
    Cloner cloner=new Cloner();
    namedCoeffs=cloner.deepClone(namedCoeffs);
    Cloner cloner=new Cloner();
    namedCoeffs=cloner.deepClone(namedCoeffs);
    return namedCoeffs;
  }
  public SortedMap<Fraction,Rational> getCoefficients(){
    Cloner cloner=new Cloner();
    coefficients=cloner.deepClone(coefficients);
    Cloner cloner=new Cloner();
    coefficients=cloner.deepClone(coefficients);
    return coefficients;
  }
}
