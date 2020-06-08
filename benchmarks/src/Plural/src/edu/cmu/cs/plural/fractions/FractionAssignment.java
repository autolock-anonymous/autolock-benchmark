package edu.cmu.cs.plural.fractions;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
/** 
 * Manages equivalence classes of fraction terms and in particular attempts to assign "representatives" to every term. The assignment is built externally by calling <code>makeXxx</code> methods, and changes will make    {@link #isChanged()} <code>true</code>.This is useful to determine when the process of equating terms has reached a fixed point. The "changed" flag can be reset using  {@link #resetChangedFlag()}. Query methods are public, while modifying methods are package-private.
 * @author Kevin Bierhoff
 */
public class FractionAssignment {
  /** 
 * Flag for tracking changes as a result of    {@link #union(FractionTerm,FractionTerm)} calls. 
 */
  private boolean changed;
  /** 
 * Equivalence classes for each term. 
 */
  private Map<FractionTerm,SortedSet<FractionTerm>> equivalenceClasses;
  /** 
 * Terms known <b>not</b> to be zero. 
 */
  private Set<FractionTerm> nonZero;
  /** 
 * Creates an empty assignment object, ready to be populated using <code>makeXxx</code> methods.
 */
  FractionAssignment(){
    equivalenceClasses=new HashMap<FractionTerm,SortedSet<FractionTerm>>();
    equivalenceClasses.put(Fraction.zero(),mutableSet(Fraction.zero()));
    equivalenceClasses.put(Fraction.one(),mutableSet(Fraction.one()));
    nonZero=new HashSet<FractionTerm>();
    nonZero.add(Fraction.one());
  }
  /** 
 * Creates a fresh mutable set with the given initial elements.
 * @param initialElements
 * @return a fresh mutable set with the given initial elements.
 */
  private static SortedSet<FractionTerm> mutableSet(  FractionTerm... initialElements){
    TreeSet<FractionTerm> result=new TreeSet<FractionTerm>();
    for (    FractionTerm e : initialElements) {
      result.add(e);
    }
    return result;
  }
  /** 
 * Sets the changed flag back to <code>false</code>.
 */
  void resetChangedFlag(){
    changed=false;
  }
  /** 
 * Returns the changed flag.
 * @return the changed flag.
 */
  boolean isChanged(){
    return changed;
  }
  /** 
 * Equates the given collection of terms.
 * @param terms
 */
  void makeEquivalentOverload(  Iterable<FractionTerm> terms){
    for (    FractionTerm t1 : terms)     for (    FractionTerm t2 : terms) {
      union(t1,t2);
    }
  }
  /** 
 * Equates the given terms.
 * @param terms
 */
  void makeEquivalent(  FractionTerm... terms){
    for (    FractionTerm t1 : terms)     for (    FractionTerm t2 : terms) {
      union(t1,t2);
    }
  }
  /** 
 * Union the equivalence classes of the two given terms.
 * @param t1
 * @param t2
 */
  private void union(  FractionTerm t1,  FractionTerm t2){
    if (t1.equals(t2)) {
      if (equivalenceClasses.containsKey(t1) == false) {
        equivalenceClasses.put(t1,mutableSet(t1));
        changed=true;
      }
      return;
    }
    SortedSet<FractionTerm> eq1=equivalenceClasses.get(t1);
    SortedSet<FractionTerm> eq2=equivalenceClasses.get(t2);
    if (eq1 != null) {
      if (eq1 == eq2)       return;
      if (eq2 != null) {
        eq1.addAll(eq2);
        for (        FractionTerm t : eq2) {
          equivalenceClasses.put(t,eq1);
        }
      }
 else {
        eq1.add(t2);
        equivalenceClasses.put(t2,eq1);
      }
    }
 else {
      if (eq2 != null) {
        eq2.add(t1);
        equivalenceClasses.put(t1,eq2);
      }
 else {
        SortedSet<FractionTerm> newClass=mutableSet(t1,t2);
        equivalenceClasses.put(t1,newClass);
        equivalenceClasses.put(t2,newClass);
      }
    }
    changed=true;
  }
  /** 
 * Tests if the given term is known to be equivalent to 1.
 * @param f
 * @return <code>true</code> if the given term is known to be 1, <code>false</code> otherwise.
 */
  public boolean isOne(  FractionTerm f){
    return equivalenceClasses.get(Fraction.one()).contains(f);
  }
  /** 
 * Tests if the given term is known to be equivalent to 0.
 * @param f
 * @return <code>true</code> if the given term is known to be 0, <code>false</code> otherwise.
 */
  public boolean isZero(  FractionTerm f){
    return equivalenceClasses.get(Fraction.zero()).contains(f);
  }
  /** 
 * Tests if the given term is known <b>not</b> to be 0.
 * @param f
 * @return <code>true</code> if the given term is known to be anything but 0, <code>false</code> otherwise.
 */
  public boolean isNonZero(  FractionTerm f){
    return nonZero.contains(f);
  }
  /** 
 * Returns a constant (0, 1, or a named fraction) that the given term is equivalent to, if any.
 * @param f
 * @return a constant (0, 1, or a named fraction) that the given term is equivalent to, or <code>null</code> if the given term is not known to be equivalent to a constant.
 */
  public Fraction getConstant(  FractionTerm f){
    Set<FractionTerm> eq=equivalenceClasses.get(f);
    if (eq == null)     return null;
    final AbstractFractionTermVisitor<Fraction> v=new AbstractFractionTermVisitor<Fraction>(){
      @Override public Fraction one(      OneFraction fract){
        return fract;
      }
      @Override public Fraction zero(      ZeroFraction fract){
        return fract;
      }
      @Override public Fraction named(      NamedFraction fract){
        return fract;
      }
    }
;
    for (    FractionTerm t : eq) {
      Fraction result=t.dispatch(v);
      if (result != null)       return result;
    }
    return null;
  }
  /** 
 * Returns a fraction that the given term is known to be equivalent to, if any.
 * @param f
 * @return a fraction that the given term is known to be equivalent to, or <code>null</code> if there is no such fraction known.
 */
  public Fraction getLiteral(  FractionTerm f){
    Set<FractionTerm> eq=equivalenceClasses.get(f);
    if (eq == null)     return null;
    final AbstractFractionTermVisitor<Fraction> v=new AbstractFractionTermVisitor<Fraction>(){
      @Override public Fraction one(      OneFraction fract){
        return fract;
      }
      @Override public Fraction zero(      ZeroFraction fract){
        return fract;
      }
      @Override public Fraction named(      NamedFraction fract){
        return fract;
      }
      @Override public Fraction var(      VariableFraction fract){
        return fract;
      }
    }
;
    for (    FractionTerm t : eq) {
      Fraction result=t.dispatch(v);
      if (result != null)       return result;
    }
    return null;
  }
  /** 
 * Returns the unique representative for the given fraction.
 * @param f
 * @return A fraction representing the given fraction, possibly the given fraction itself, but never <code>null</code>.
 */
  public Fraction getRepresentative(  Fraction f){
    Fraction lit=getLiteral(f);
    if (lit == null)     return f;
    return lit;
  }
  /** 
 * Equates the given term with zero.
 * @param f
 */
  void makeZero(  FractionTerm f){
    union(f,Fraction.zero());
  }
  /** 
 * Equates the given collection of terms with zero.
 * @param terms
 */
  void makeZeroOverload(  List<FractionTerm> terms){
    for (    FractionTerm f : terms)     makeZero(f);
  }
  /** 
 * Equates the given term with one.
 * @param f
 */
  void makeOne(  FractionTerm f){
    union(f,Fraction.one());
  }
  /** 
 * Makes the given term definitely not zero.
 * @param t
 */
  void makeNonZero(  FractionTerm t){
    if (nonZero.add(t))     changed=true;
    Set<FractionTerm> eq=equivalenceClasses.get(t);
    if (eq != null)     if (nonZero.addAll(eq))     changed=true;
  }
  /** 
 * Tests whether the two given terms are known to be equal.
 * @param t1
 * @param t2
 * @return <code>true</code> if the two given terms are known to be equal,<code>false</code> otherwise.
 */
  public boolean areEquivalent(  FractionTerm t1,  FractionTerm t2){
    if (t1.equals(t2))     return true;
    SortedSet<FractionTerm> eq1=equivalenceClasses.get(t1);
    SortedSet<FractionTerm> eq2=equivalenceClasses.get(t2);
    return eq1 != null && (eq1 == eq2 || eq1.contains(t2) || (eq2 != null && eq2.contains(t1)));
  }
  /** 
 * Tests whether the two given terms are known to be or can be made  equal under the given mapping of named fractions.
 * @param t1
 * @param t2
 * @param mapping Named fraction mapping that may possibly be extended tomake the given terms equal.
 * @return <code>true</code> if the two given terms could be made equal,<code>false</code> otherwise.
 */
  public boolean areEquivalentOverload(  FractionTerm t1,  FractionTerm t2,  NamedFractionMapping mapping){
    if (areEquivalent(t1,t2))     return true;
    Fraction l1=getLiteral(t1);
    Fraction l2=getLiteral(t2);
    if (l1 != null && l2 != null && l1 instanceof NamedFraction && l2 instanceof NamedFraction)     return mapping.map((NamedFraction)l1,(NamedFraction)l2);
 else     return false;
  }
  /** 
 * Returns true if we can easily figure out that the sum of this FractionSum should intuitively be a constant. This includes all NamedFractions, all 1/0. 
 */
  private static boolean sumsToConstant(  FractionSum sum){
    boolean all_named_fractions=true;
    boolean all_literals=true;
    for (    Fraction fract : sum.getSummands()) {
      all_named_fractions&=fract instanceof NamedFraction;
      all_literals&=(fract instanceof OneFraction || fract instanceof ZeroFraction);
    }
    return all_named_fractions | all_literals;
  }
  /** 
 * Do the two given Fraction terms have the same literal values? This method is very simple. It just checks to see if both terms are 1 or 0 or sums of 1 and 0. If so, then we make sure they have the same value! If not, we return false.
 */
  private static boolean equivalentLiteralValues(  FractionTerm t1,  FractionTerm t2){
    if (t1 instanceof NamedFraction)     return false;
    if (t2 instanceof NamedFraction)     return false;
    int t1_value=(t1 instanceof OneFraction ? 1 : 0);
    int t2_value=(t2 instanceof OneFraction ? 1 : 0);
    if (t1 instanceof FractionSum) {
      for (      Fraction frac : ((FractionSum)t1).getSummands()) {
        if (frac instanceof OneFraction)         t1_value++;
      }
    }
    if (t2 instanceof FractionSum) {
      for (      Fraction frac : ((FractionSum)t2).getSummands()) {
        if (frac instanceof OneFraction)         t2_value++;
      }
    }
    return t1_value == t2_value;
  }
  /** 
 * Checks if there is no more than one constant in every equivalence class. Constants are    {@link ZeroFraction},    {@link OneFraction}, and    {@link NamedFraction}.
 * @return <code>true</code> if this fraction assignment is consistent; <code>false</code> otherwise.
 */
  public boolean isConsistent(){
    for (    Set<FractionTerm> eq : equivalenceClasses.values()) {
      FractionTerm literal=null;
      for (      FractionTerm t : eq) {
        if (t instanceof FractionSum) {
          if (sumsToConstant((FractionSum)t)) {
            if (literal != null && !equivalentLiteralValues(literal,t))             return false;
 else             literal=t;
          }
        }
 else         if ((t instanceof VariableFraction) == false) {
          if (literal != null && !equivalentLiteralValues(literal,t))           return false;
 else           literal=t;
        }
      }
    }
    for (    FractionTerm z : equivalenceClasses.get(Fraction.zero())) {
      if (nonZero.contains(z))       return false;
    }
    return true;
  }
  @Override public String toString(){
    StringBuffer result=new StringBuffer("[");
    for (    FractionTerm t : equivalenceClasses.keySet()) {
      if (result.length() > 1)       result.append(',');
      Fraction f=getLiteral(t);
      if (f == null)       result.append(t + " in " + equivalenceClasses.get(t));
 else       result.append(t + "=" + f);
    }
    result.append(']');
    return result.toString();
  }
  public Set<FractionTerm> getNonZero(){
    Cloner cloner=new Cloner();
    nonZero=cloner.deepClone(nonZero);
    Cloner cloner=new Cloner();
    nonZero=cloner.deepClone(nonZero);
    Cloner cloner=new Cloner();
    nonZero=cloner.deepClone(nonZero);
    return nonZero;
  }
  public Map<FractionTerm,SortedSet<FractionTerm>> getEquivalenceClasses(){
    Cloner cloner=new Cloner();
    equivalenceClasses=cloner.deepClone(equivalenceClasses);
    Cloner cloner=new Cloner();
    equivalenceClasses=cloner.deepClone(equivalenceClasses);
    Cloner cloner=new Cloner();
    equivalenceClasses=cloner.deepClone(equivalenceClasses);
    return equivalenceClasses;
  }
}
