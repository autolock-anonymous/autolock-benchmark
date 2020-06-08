package edu.cmu.cs.plural.fractions.elim;
import java.util.HashSet;
import java.util.Set;
import edu.cmu.cs.plural.fractions.Fraction;
import edu.cmu.cs.plural.fractions.FractionConstraint;
import edu.cmu.cs.plural.fractions.FractionConstraintVisitor;
import edu.cmu.cs.plural.fractions.FractionRelation;
import edu.cmu.cs.plural.fractions.FractionSum;
import edu.cmu.cs.plural.fractions.FractionTermVisitor;
import edu.cmu.cs.plural.fractions.ImpossibleConstraint;
import edu.cmu.cs.plural.fractions.NamedFraction;
import edu.cmu.cs.plural.fractions.OneFraction;
import edu.cmu.cs.plural.fractions.VariableFraction;
import edu.cmu.cs.plural.fractions.ZeroFraction;
import edu.cmu.cs.plural.fractions.FractionRelation.Relop;
import edu.cmu.cs.plural.fractions.elim.SimpleFractionSum.Sumop;
/** 
 * @author Kevin Bierhoff
 * @deprecated Use VariableElimination instead
 */
public class FractionElimination {
  private Set<RelationFractionPair> groundRelations;
  Set<RelationFractionPair> rels;
  NormalizedFractionTerm t;
  Set<RelationFractionPair> result;
  FractionConstraintVisitor<Boolean> cv;
  public Set<RelationFractionPair> eliminateVariables(  Set<FractionConstraint> constraints){
    rels=normalizeConstraints(constraints);
    Set<VariableFraction> vars=collectVariables(rels);
    for (    VariableFraction x : vars) {
      rels=eliminateVariableOverload(rels,x);
    }
    groundRelations=rels;
    return rels;
  }
  private Set<RelationFractionPair> eliminateVariableOverload(  Set<RelationFractionPair> rels,  VariableFraction x){
    SimpleVariableRelativity relativity=new SimpleVariableRelativity(x);
    HashSet<RelationFractionPair> result=new HashSet<RelationFractionPair>();
    Relations:     for (    RelationFractionPair r : rels) {
      int in1, in2;
      do {
        if (r.getComponent1().equals(r.getComponent2()) && !r.getRelop().equals(Relop.LE))         continue Relations;
        t=r.getComponent1();
        in1=containsVariable(t,x);
        in2=containsVariable(t,x);
        if (in1 == 0 && in2 == 0) {
          result.add(r);
          continue Relations;
        }
 else         if (in1 == in2) {
          r=subtractVariable(r,x,in1);
        }
 else         if (in1 == -1 * in2)         throw new UnsupportedOperationException("Variable on both sides: " + r);
 else         break;
      }
 while (true);
      if (in1 != 0) {
        if (r.getComponent1().equals(x)) {
          relativity.addRight(r.getRelop(),r.getComponent2());
          continue;
        }
        SimpleFractionSum s=(SimpleFractionSum)r.getComponent1();
switch (s.getSumop()) {
case PLUS:
          SimpleFractionSum diff;
        diff=SimpleFractionSum.createSub((Fraction)r.getComponent2(),s.getComponent1().equals(x) ? s.getComponent2() : s.getComponent1());
      relativity.addRight(r.getRelop(),diff);
    break;
case MINUS:
  if (s.getComponent1().equals(x)) {
    relativity.addRight(r.getRelop(),SimpleFractionSum.createAdd((Fraction)r.getComponent2(),s.getComponent2()));
  }
 else {
    relativity.addLeft(SimpleFractionSum.createSub(s.getComponent1(),(Fraction)r.getComponent2()),r.getRelop());
  }
break;
}
}
if (in2 != 0) {
if (r.getComponent2().equals(x)) {
relativity.addLeft(r.getComponent1(),r.getRelop());
continue;
}
SimpleFractionSum s=(SimpleFractionSum)r.getComponent2();
switch (s.getSumop()) {
case PLUS:
SimpleFractionSum diff;
diff=SimpleFractionSum.createSub((Fraction)r.getComponent1(),s.getComponent1().equals(x) ? s.getComponent2() : s.getComponent1());
relativity.addLeft(diff,r.getRelop());
break;
case MINUS:
if (s.getComponent1().equals(x)) {
relativity.addLeft(SimpleFractionSum.createAdd((Fraction)r.getComponent1(),s.getComponent2()),r.getRelop());
}
 else {
relativity.addRight(r.getRelop(),SimpleFractionSum.createSub(s.getComponent1(),(Fraction)r.getComponent1()));
}
break;
}
}
}
return relativity.dumpRelations(result);
}
private static RelationFractionPair subtractVariable(final RelationFractionPair rel,final VariableFraction x,final int sign){
final NormalizedFractionVisitor<NormalizedFractionTerm> v=new NormalizedFractionVisitor<NormalizedFractionTerm>(){
@Override public NormalizedFractionTerm named(NamedFraction f){
return f;
}
@Override public NormalizedFractionTerm one(OneFraction f){
return f;
}
@Override public NormalizedFractionTerm sum(SimpleFractionSum f){
if (f.getComponent1().equals(x) && f.getComponent2().equals(x)) {
return Fraction.zero();
}
 else if (f.getComponent1().equals(x)) {
switch (f.getSumop()) {
case PLUS:
return f.getComponent2();
case MINUS:
return SimpleFractionSum.createSub(Fraction.zero(),f.getComponent2());
default :
throw new IllegalArgumentException("Unknown sumop " + f.getSumop() + " in "+ rel);
}
}
 else if (f.getComponent2().equals(x)) {
return f.getComponent1();
}
 else return f;
}
@Override public NormalizedFractionTerm var(VariableFraction f){
if (f.equals(x)) return Fraction.zero();
return f;
}
@Override public NormalizedFractionTerm zero(ZeroFraction f){
return f;
}
}
;
NormalizedFractionTerm t1=rel.getComponent1().dispatch(v);
NormalizedFractionTerm t2=rel.getComponent2().dispatch(v);
switch (rel.getRelop()) {
case EQ:
return RelationFractionPair.createEqual(t1,t2);
case LEQ:
return RelationFractionPair.createLeq(t1,t2);
case LE:
return RelationFractionPair.createLess(t1,t2);
default :
throw new IllegalArgumentException("Unknown relop: " + rel);
}
}
private static int containsVariable(final NormalizedFractionTerm t,final VariableFraction x){
NormalizedFractionVisitor<Integer> v=new NormalizedFractionVisitor<Integer>(){
@Override public Integer named(NamedFraction f){
return 0;
}
@Override public Integer one(OneFraction f){
return 0;
}
@Override public Integer sum(SimpleFractionSum f){
int in1=f.getComponent1().dispatch(this);
int in2=f.getComponent2().dispatch(this);
if (in1 != 0 && in2 != 0) throw new UnsupportedOperationException("Variable " + x + " in both branches of sum: "+ t);
if (f.getSumop().equals(Sumop.MINUS)) in2=-1 * in2;
return in1 + in2;
}
@Override public Integer var(VariableFraction f){
return f.equals(x) ? 1 : 0;
}
@Override public Integer zero(ZeroFraction f){
return 0;
}
}
;
t.equals(v);
return t.dispatch(v);
}
private Set<VariableFraction> collectVariables(Set<RelationFractionPair> rels){
final Set<VariableFraction> result=new HashSet<VariableFraction>();
NormalizedFractionVisitor<Boolean> v=new NormalizedFractionVisitor<Boolean>(){
@Override public Boolean named(NamedFraction f){
return null;
}
@Override public Boolean one(OneFraction f){
return null;
}
@Override public Boolean sum(SimpleFractionSum f){
f.getComponent1().dispatch(this);
f.getComponent2().dispatch(this);
return null;
}
@Override public Boolean var(VariableFraction f){
result.add(f);
return null;
}
@Override public Boolean zero(ZeroFraction f){
return null;
}
}
;
for (RelationFractionPair r : rels) {
r.getComponent1().dispatch(v);
r.getComponent2().dispatch(v);
}
return result;
}
private Set<RelationFractionPair> normalizeConstraints(Set<FractionConstraint> constraints){
result=new HashSet<RelationFractionPair>();
final FractionTermVisitor<NormalizedFractionTerm> normalizer=new FractionTermVisitor<NormalizedFractionTerm>(){
@Override public NormalizedFractionTerm literal(Fraction fract){
return fract;
}
@Override public NormalizedFractionTerm sum(FractionSum fract){
if (fract.getSummands().size() == 1) return fract.getSummands().get(0);
if (fract.getSummands().size() == 2) return SimpleFractionSum.createAdd(fract.getSummands().get(0),fract.getSummands().get(1));
throw new UnsupportedOperationException("Sum with bad number of summands: " + fract);
}
}
;
cv=new FractionConstraintVisitor<Boolean>(){
@Override public Boolean impossible(ImpossibleConstraint fract){
return null;
}
@Override public Boolean relation(FractionRelation fract){
if (fract.getTerms().size() < 2) return null;
switch (fract.getRelop()) {
case EQ:
if (fract.getTerms().size() == 2) {
result.add(RelationFractionPair.createEqual(fract.getTerms().get(0).dispatch(normalizer),fract.getTerms().get(1).dispatch(normalizer)));
}
 else throw new UnsupportedOperationException("Equality between more than two terms not supported: " + fract);
break;
case LEQ:
for (int i=1; i < fract.getTerms().size(); i++) {
result.add(RelationFractionPair.createLeq(fract.getTerms().get(i - 1).dispatch(normalizer),fract.getTerms().get(i).dispatch(normalizer)));
}
break;
case LE:
if (fract.getTerms().size() == 2) {
result.add(RelationFractionPair.createLess(fract.getTerms().get(0).dispatch(normalizer),fract.getTerms().get(1).dispatch(normalizer)));
}
 else throw new UnsupportedOperationException("Disequality between more than two terms not supported: " + fract);
break;
default :
throw new IllegalArgumentException("Relation with unknown operator: " + fract.getRelop());
}
return null;
}
}
;
for (final FractionConstraint c : constraints) {
c.dispatch(cv);
}
return result;
}
public boolean isConsistent(){
return true;
}
public FractionConstraintVisitor<Boolean> getCv(){
Cloner cloner=new Cloner();
cv=cloner.deepClone(cv);
Cloner cloner=new Cloner();
cv=cloner.deepClone(cv);
return cv;
}
public NormalizedFractionTerm getT(){
Cloner cloner=new Cloner();
t=cloner.deepClone(t);
Cloner cloner=new Cloner();
t=cloner.deepClone(t);
return t;
}
public Set<RelationFractionPair> getRels(){
Cloner cloner=new Cloner();
rels=cloner.deepClone(rels);
Cloner cloner=new Cloner();
rels=cloner.deepClone(rels);
return rels;
}
public Set<RelationFractionPair> getResult(){
Cloner cloner=new Cloner();
result=cloner.deepClone(result);
Cloner cloner=new Cloner();
result=cloner.deepClone(result);
return result;
}
public Set<RelationFractionPair> getGroundRelations(){
Cloner cloner=new Cloner();
groundRelations=cloner.deepClone(groundRelations);
Cloner cloner=new Cloner();
groundRelations=cloner.deepClone(groundRelations);
return groundRelations;
}
}
