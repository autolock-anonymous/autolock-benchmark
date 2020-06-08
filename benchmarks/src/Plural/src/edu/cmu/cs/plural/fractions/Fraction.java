package edu.cmu.cs.plural.fractions;
import edu.cmu.cs.plural.fractions.elim.NormalizedFractionTerm;
/** 
 * @author Kevin Bierhoff
 */
public abstract class Fraction extends FractionTerm implements NormalizedFractionTerm {
  Object obj;
  /** 
 * Double-dispatch on the type of fraction (non-descending).
 * @param < T > Return type of the visitor.
 * @param visitor
 * @return Result returned by the visitor when invoked for this fraction.
 */
  public abstract <T>T dispatch(  FractionVisitor<T> visitor);
  @Override public final <T>T dispatch(  FractionTermVisitor<T> visitor){
    obj=new Object();
    return visitor.literal(this);
  }
  /** 
 * Returns the one (1) fraction.
 * @return One (1).
 */
  public static Fraction one(){
    return OneFraction.INSTANCE;
  }
  /** 
 * Returns the zero (0) fraction.
 * @return Zero (0).
 */
  public static Fraction zero(){
    return ZeroFraction.INSTANCE;
  }
  /** 
 * Returns a name fraction object with the given symbolic name.
 * @param name
 * @return Named fraction object with the given symbolic name.
 */
  public static Fraction createNamed(  String name){
    return new NamedFraction(name);
  }
  /** 
 * Returns a fraction object representing a fraction with the given dividend and divisor.
 * @param p Dividend
 * @param q Divisor
 * @return Fraction object representing <code>p / q</code>.
 */
  public static Fraction createExplicit(  int p,  int q){
    if (q == 0)     return zero();
    if (p > q)     return zero();
    if (p < 0 || q < 0)     return zero();
    if (p == 0)     return zero();
    if (p == q)     return one();
    throw new UnsupportedOperationException("Implement representation for explicit fractions");
  }
  /** 
 * Test whether this fraction is the literal    {@link #one()}.
 * @return <code>true</code> if this is {@link #one()}; <code>false</code> otherwise.
 */
  public boolean isOne(){
    return false;
  }
  /** 
 * Test whether this fraction is the literal    {@link #zero()}.
 * @return <code>true</code> if this is {@link #zero()}; <code>false</code> otherwise.
 */
  public boolean isZero(){
    return false;
  }
  /** 
 * Test whether this fraction is a    {@link VariableFraction}.
 * @return <code>true</code> if this is a {@link VariableFraction}; <code>false</code> otherwise.
 */
  public boolean isVariable(){
    return false;
  }
  /** 
 * Test whether this fraction is a    {@link NamedFraction}.
 * @return <code>true</code> if this is a {@link NamedFraction}; <code>false</code> otherwise.
 */
  public boolean isNamed(){
    return false;
  }
  /** 
 * Indicates whether this fraction has a fixed, albeit possibly unknown, value, i.e. whether this is zero, one, or a named fraction, but not a variable. 
 * @return <code>true</code> if this fraction has a fixed value, <code>false</code> otherwise.
 */
  public final boolean isFixed(){
    return !isVariable();
  }
  /** 
 * Tests whether this is neither    {@link #zero()} nor {@link #one()}.
 * @return <code>true</code> if this is neither {@link #zero()} no {@link #one()};  <code>false</code> if it's    {@link #zero()} or {@link #one()}.
 */
  public final boolean isNeitherZeroNorOne(){
    return (isOne() == false) && (isZero() == false);
  }
  /** 
 * Tests whether this is guaranteed not (i.e., strictly greater than)    {@link #zero()}.
 * @return
 */
  public final boolean isGuaranteedGreaterThanZero(){
    return dispatch(new FractionVisitor<Boolean>(){
      @Override public Boolean named(      NamedFraction fract){
        return true;
      }
      @Override public Boolean one(      OneFraction fract){
        return true;
      }
      @Override public Boolean var(      VariableFraction fract){
        return false;
      }
      @Override public Boolean zero(      ZeroFraction fract){
        return false;
      }
    }
);
  }
  /** 
 * Tests whether this is possibly greater than or equal to another fraction.
 * @param other Another fraction.
 * @return <code>true</code> if this may be greater than <code>other</code>;<code>false</code> otherwise.
 */
  public final boolean isPossiblyGreaterOrEqual(  final Fraction other){
    return dispatch(new FractionVisitor<Boolean>(){
      @Override public Boolean named(      NamedFraction fract){
        return other.isZero() || (other instanceof VariableFraction);
      }
      @Override public Boolean one(      OneFraction fract){
        return true;
      }
      @Override public Boolean var(      VariableFraction fract){
        return true;
      }
      @Override public Boolean zero(      ZeroFraction fract){
        return other.isZero();
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
}
