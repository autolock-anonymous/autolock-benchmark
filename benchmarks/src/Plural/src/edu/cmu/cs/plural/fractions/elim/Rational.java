package edu.cmu.cs.plural.fractions.elim;
/** 
 * @author Kevin Bierhoff
 */
public class Rational {
  private static final Rational ZERO=new Rational();
  private static final Rational ONE=new Rational(1);
  private static final Rational MINUS_ONE=new Rational(-1);
  public static Rational zero(){
    return ZERO;
  }
  public static Rational one(){
    return ONE;
  }
  public static Rational minusOne(){
    return MINUS_ONE;
  }
  /** 
 * Implements Euclid's algorithm to find the greatest common divisor or any two integers.
 * @param a
 * @param b
 * @return
 */
  public static int gcd(  int a,  int b){
    if (a < 0)     a=-1 * a;
    if (b < 0)     b=-1 * b;
    while (b != 0) {
      int t=b;
      b=a % b;
      a=t;
    }
    return a;
  }
  private final int p;
  private final int q;
  public Rational(){
    this(0);
  }
  public Rational(  int number){
    this.p=number;
    this.q=1;
  }
  public Rational(  int p,  int q){
    if (q == 0)     throw new IllegalArgumentException("q must be non-zero: " + q);
    if (q < 0) {
      p=-1 * p;
      q=-1 * q;
    }
    int gcd=gcd(p,q);
    this.p=p / gcd;
    this.q=q / gcd;
  }
  /** 
 * Returns the p.
 * @return the p.
 */
  public int getP(){
    return p;
  }
  /** 
 * Returns the q.
 * @return the q.
 */
  public int getQ(){
    return q;
  }
  /** 
 * @return
 */
  public boolean isZero(){
    return p == 0;
  }
  /** 
 * @return
 */
  public boolean isOne(){
    return p == q;
  }
  public boolean isPositive(){
    return (p > 0 && q > 0) || (p < 0 && q < 0);
  }
  public boolean isNegative(){
    return (p < 0 && q > 0) || (p > 0 && q < 0);
  }
  /** 
 * @param i
 * @return
 */
  public Rational times(  int i){
    return new Rational(p * i,q);
  }
  /** 
 * @return
 */
  public Rational inverse(){
    return new Rational(q,p);
  }
  /** 
 * @param r
 * @return
 */
  public Rational times(  Rational r){
    return new Rational(this.p * r.p,this.q * r.q);
  }
  /** 
 * @param r
 * @return
 */
  public Rational div(  Rational r){
    return new Rational(this.p * r.q,this.q * r.p);
  }
  /** 
 * @param coefficient
 * @return
 */
  public Rational minus(  Rational r){
    return plus(r.negation());
  }
  /** 
 * @param r
 * @return
 */
  public Rational plus(  Rational r){
    return new Rational(this.p * r.q + r.p * this.q,this.q * r.q);
  }
  /** 
 * @return
 */
  public Rational negation(){
    return new Rational(-1 * p,q);
  }
  public Rational abs(){
    if (isPositive())     return this;
    return negation();
  }
  @Override public String toString(){
    if (q == 1)     return "" + p;
    return p + "/" + q;
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=1;
    result=prime * result + p;
    result=prime * result + q;
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    final Rational other=(Rational)obj;
    if (p != other.p)     return false;
    if (q != other.q)     return false;
    return true;
  }
  public boolean isSmallerThan(  Rational other){
    return this.p * other.q < this.q * other.p;
  }
  public static Rational getONE(){
    Cloner cloner=new Cloner();
    ONE=cloner.deepClone(ONE);
    Cloner cloner=new Cloner();
    ONE=cloner.deepClone(ONE);
    return ONE;
  }
  public static Rational getZERO(){
    Cloner cloner=new Cloner();
    ZERO=cloner.deepClone(ZERO);
    Cloner cloner=new Cloner();
    ZERO=cloner.deepClone(ZERO);
    return ZERO;
  }
  public static Rational getMINUS_ONE(){
    Cloner cloner=new Cloner();
    MINUS_ONE=cloner.deepClone(MINUS_ONE);
    Cloner cloner=new Cloner();
    MINUS_ONE=cloner.deepClone(MINUS_ONE);
    return MINUS_ONE;
  }
}
