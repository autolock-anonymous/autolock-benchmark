package edu.cmu.cs.plural.fractions.elim;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;
import edu.cmu.cs.crystal.util.Pair;
import edu.cmu.cs.plural.fractions.Fraction;
import edu.cmu.cs.plural.fractions.FractionAssignment;
import edu.cmu.cs.plural.fractions.FractionConstraint;
import edu.cmu.cs.plural.fractions.FractionConstraintVisitor;
import edu.cmu.cs.plural.fractions.FractionRelation;
import edu.cmu.cs.plural.fractions.FractionSum;
import edu.cmu.cs.plural.fractions.FractionTerm;
import edu.cmu.cs.plural.fractions.FractionTermVisitor;
import edu.cmu.cs.plural.fractions.ImpossibleConstraint;
import edu.cmu.cs.plural.fractions.NamedFraction;
import edu.cmu.cs.plural.fractions.VariableFraction;
import edu.cmu.cs.plural.fractions.FractionRelation.Relop;
/** 
 * @author Kevin Bierhoff
 */
public class VariableElimination {
  private Set<NamedFraction> constants;
  private Set<NormalizedFractionConstraint> assumptions;
  private Set<NormalizedFractionConstraint> groundRelations;
  private FractionAssignment assignment;
  private Map<NamedFraction,NamedFraction> upperBounds;
  private Map<VariableFraction,Integer> variableTiers;
  private long deadline;
  private long timeout=10000;
  VariableRelativity relativity;
  List<VariableFraction> result;
  Set<NormalizedFractionConstraint> result2;
  public Set<NormalizedFractionConstraint> eliminateVariables(  Collection<FractionConstraint> constraints,  FractionAssignment a) throws TimeoutException {
  }
  /** 
 * Returns the timeout.
 * @return the timeout.
 */
  public long getTimeout(){
    return timeout;
  }
  /** 
 * @param timeout the timeout to set
 */
  public void setTimeout(  long timeout){
    this.timeout=timeout;
  }
  private Set<NormalizedFractionConstraint> addVariableConstraints(  Set<NormalizedFractionConstraint> rels,  Iterable<VariableFraction> vars){
    for (    VariableFraction x : vars) {
      rels.add(NormalizedFractionConstraint.createConstraintOverload(Fraction.zero(),Relop.LEQ,x));
      rels.add(NormalizedFractionConstraint.createConstraintOverload(x,Relop.LEQ,Fraction.one()));
    }
    return rels;
  }
  private Set<NormalizedFractionConstraint> addConstConstraints(  Set<NormalizedFractionConstraint> rels,  Iterable<NamedFraction> vars){
    for (    NamedFraction x : vars) {
      rels.add(NormalizedFractionConstraint.createConstraintOverload(Fraction.zero(),Relop.LE,x));
      rels.add(NormalizedFractionConstraint.createConstraintOverload(x,Relop.LE,Fraction.one()));
    }
    return rels;
  }
  private Set<NormalizedFractionConstraint> eliminateFraction(  Set<NormalizedFractionConstraint> rels,  Fraction x,  boolean populateGroundRels) throws TimeoutException {
    LinkedHashSet<NormalizedFractionConstraint> result=new LinkedHashSet<NormalizedFractionConstraint>();
    if (populateGroundRels)     relativity=new VariableRelativity(x,upperBounds,result,groundRelations,deadline);
 else     relativity=new VariableRelativity(x,Collections.<NamedFraction,NamedFraction>emptyMap(),result,result,deadline);
    for (    NormalizedFractionConstraint rel : rels) {
      if (rel.getFractions().contains(x)) {
        Pair<NormalizedFractionSum,Boolean> elim=rel.isolateFraction(x);
        if (elim.snd())         relativity.addRight(rel.getRelop(),elim.fst());
 else         relativity.addLeft(elim.fst(),rel.getRelop());
      }
 else       result.add(rel);
    }
    return relativity.dumpRelations();
  }
  private <T extends Fraction>SortedSet<T> collectVariables(  Set<NormalizedFractionConstraint> rels,  Class<T> variableType){
    final SortedSet<T> result=new TreeSet<T>();
    for (    final NormalizedFractionConstraint rel : rels) {
      for (      Fraction f : rel.getFractions()) {
        if (variableType.isAssignableFrom(f.getClass()))         result.add((T)f);
      }
    }
    return result;
  }
  private List<VariableFraction> eliminationOrder(  Set<VariableFraction> vars){
    result=new ArrayList<VariableFraction>(vars);
    Collections.sort(result,new Comparator<VariableFraction>(){
      @Override public int compare(      VariableFraction o1,      VariableFraction o2){
        Integer tier1=variableTiers.get(o1);
        Integer tier2=variableTiers.get(o2);
        if (tier1 == null) {
          if (tier2 == null)           return o2.compareTo(o1);
          return 1;
        }
        if (tier2 == null)         return -1;
        if (tier1.equals(tier2))         return o2.compareTo(o1);
        return tier2.compareTo(tier1);
      }
    }
);
    return result;
  }
  private Set<NormalizedFractionConstraint> normalizeConstraints(  Collection<FractionConstraint> constraints){
    result2=new HashSet<NormalizedFractionConstraint>();
    final FractionConstraintVisitor<Boolean> cv=new FractionConstraintVisitor<Boolean>(){
      @Override public Boolean impossible(      ImpossibleConstraint fract){
        return null;
      }
      @Override public Boolean relation(      FractionRelation fract){
        if (fract.getTerms().size() < 2)         return null;
        int tier=0;
        FractionTerm last=null;
        NamedFraction lastConst=null;
        for (        FractionTerm t : fract.getTerms()) {
          if (t instanceof NamedFraction) {
            NamedFraction c=(NamedFraction)t;
            if (lastConst != null) {
              assumptions.add(createRelation(lastConst,fract.getRelop(),c));
              upperBounds.put(lastConst,c);
            }
            lastConst=c;
          }
 else           if (fract.getRelop().equals(Relop.LEQ) && t instanceof VariableFraction) {
            variableTiers.put((VariableFraction)t,tier++);
          }
          if (last != null) {
            NormalizedFractionConstraint c=createRelation(normalizeTerm(last),fract.getRelop(),normalizeTerm(t));
            if (c.isTriviallyTrue() == false) {
              if (c.isPrimitive())               groundRelations.add(c);
 else               if (c.isGround()) {
                if (!Relop.EQ.equals(fract.getRelop()))                 assumptions.add(c);
 else                 groundRelations.add(c);
              }
 else               result2.add(c);
            }
          }
          last=t;
        }
        return null;
      }
      private NormalizedFractionConstraint createRelation(      NormalizedFractionSum left,      Relop relop,      NormalizedFractionSum right){
        return NormalizedFractionConstraint.createConstraint(left,relop,right);
      }
      private NormalizedFractionConstraint createRelation(      Fraction left,      Relop relop,      Fraction right){
        return NormalizedFractionConstraint.createConstraintOverload(left,relop,right);
      }
    }
;
    for (    final FractionConstraint c : constraints) {
      c.dispatch(cv);
    }
    return result2;
  }
  private NormalizedFractionSum normalizeTerm(  final FractionTerm term){
    final FractionTermVisitor<NormalizedFractionSum> normalizer=new FractionTermVisitor<NormalizedFractionSum>(){
      @Override public NormalizedFractionSum literal(      Fraction fract){
        return new NormalizedFractionSum(getRepresentative(fract));
      }
      @Override public NormalizedFractionSum sum(      FractionSum fract){
        return new NormalizedFractionSum(getRepresentatives(fract.getSummands()));
      }
      private Fraction getRepresentative(      Fraction fract){
        return assignment.getRepresentative(fract);
      }
      private Fraction[] getRepresentatives(      List<Fraction> summands){
        Fraction[] result=new Fraction[summands.size()];
        int i=0;
        for (        Fraction s : summands) {
          result[i++]=getRepresentative(s);
        }
        return result;
      }
    }
;
    return term.dispatch(normalizer);
  }
  public boolean isSatisfiable(  Set<NormalizedFractionConstraint> rels,  Set<? extends Fraction> vars) throws TimeoutException {
    for (    Fraction x : vars) {
      rels=eliminateFraction(rels,x,false);
    }
    for (    NormalizedFractionConstraint c : rels) {
      if (isPrimitiveConstraintSatisfiable(c) == false)       return false;
    }
    return true;
  }
  /** 
 * @param c
 */
  private static boolean isPrimitiveConstraintSatisfiable(  NormalizedFractionConstraint c){
    if (c.isPrimitive() == false)     throw new IllegalStateException("Constraint not primitive: " + c);
    Rational value=c.getCoefficient(Fraction.one());
switch (c.getRelop()) {
case LE:
      if (value.isZero())       return false;
case LEQ:
    if (value.isPositive())     return false;
  break;
case EQ:
if (value.isZero() == false) return false;
break;
default :
throw new UnsupportedOperationException();
}
return true;
}
public boolean isConsistent() throws TimeoutException {
}
public Set<NormalizedFractionConstraint> getGroundRelations(){
Cloner cloner=new Cloner();
groundRelations=cloner.deepClone(groundRelations);
Cloner cloner=new Cloner();
groundRelations=cloner.deepClone(groundRelations);
return groundRelations;
}
public VariableRelativity getRelativity(){
Cloner cloner=new Cloner();
relativity=cloner.deepClone(relativity);
Cloner cloner=new Cloner();
relativity=cloner.deepClone(relativity);
return relativity;
}
public Set<NormalizedFractionConstraint> getAssumptions(){
Cloner cloner=new Cloner();
assumptions=cloner.deepClone(assumptions);
Cloner cloner=new Cloner();
assumptions=cloner.deepClone(assumptions);
return assumptions;
}
public Set<NamedFraction> getConstants(){
Cloner cloner=new Cloner();
constants=cloner.deepClone(constants);
Cloner cloner=new Cloner();
constants=cloner.deepClone(constants);
return constants;
}
public Map<NamedFraction,NamedFraction> getUpperBounds(){
Cloner cloner=new Cloner();
upperBounds=cloner.deepClone(upperBounds);
Cloner cloner=new Cloner();
upperBounds=cloner.deepClone(upperBounds);
return upperBounds;
}
public FractionAssignment getAssignment(){
Cloner cloner=new Cloner();
assignment=cloner.deepClone(assignment);
Cloner cloner=new Cloner();
assignment=cloner.deepClone(assignment);
return assignment;
}
public Map<VariableFraction,Integer> getVariableTiers(){
Cloner cloner=new Cloner();
variableTiers=cloner.deepClone(variableTiers);
Cloner cloner=new Cloner();
variableTiers=cloner.deepClone(variableTiers);
return variableTiers;
}
public List<VariableFraction> getResult(){
Cloner cloner=new Cloner();
result=cloner.deepClone(result);
Cloner cloner=new Cloner();
result=cloner.deepClone(result);
return result;
}
public Set<NormalizedFractionConstraint> getResult2(){
Cloner cloner=new Cloner();
result2=cloner.deepClone(result2);
Cloner cloner=new Cloner();
result2=cloner.deepClone(result2);
return result2;
}
}
