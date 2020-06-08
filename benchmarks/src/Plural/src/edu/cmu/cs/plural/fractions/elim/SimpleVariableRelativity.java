package edu.cmu.cs.plural.fractions.elim;
import java.util.HashSet;
import java.util.Set;
import edu.cmu.cs.plural.fractions.NamedFraction;
import edu.cmu.cs.plural.fractions.OneFraction;
import edu.cmu.cs.plural.fractions.VariableFraction;
import edu.cmu.cs.plural.fractions.ZeroFraction;
import edu.cmu.cs.plural.fractions.FractionRelation.Relop;
/** 
 * @author Kevin Bierhoff
 * @deprecated Use VariableRelativity instead.
 */
public class SimpleVariableRelativity {
  private VariableFraction var;
  private NormalizedFractionVisitor<Boolean> varCheck;
  private Set<NormalizedFractionTerm> lessThan=new HashSet<NormalizedFractionTerm>();
  private Set<NormalizedFractionTerm> lessOrEqual=new HashSet<NormalizedFractionTerm>();
  private Set<NormalizedFractionTerm> equal=new HashSet<NormalizedFractionTerm>();
  private Set<NormalizedFractionTerm> greaterOrEqual=new HashSet<NormalizedFractionTerm>();
  private Set<NormalizedFractionTerm> greaterThan=new HashSet<NormalizedFractionTerm>();
  public SimpleVariableRelativity(  VariableFraction var){
    this.var=var;
  }
  public boolean addRight(  Relop relop,  NormalizedFractionTerm term){
    if (term.dispatch(varCheck))     throw new IllegalArgumentException("Eliminated variable " + var + " in term: "+ term);
switch (relop) {
case EQ:
      return equal.add(term);
case LEQ:
    return greaterOrEqual.add(term);
case LE:
  return greaterThan.add(term);
}
throw new IllegalArgumentException("Unknown relop: " + relop);
}
public boolean addLeft(NormalizedFractionTerm term,Relop relop){
if (term.dispatch(varCheck)) throw new IllegalArgumentException("Eliminated variable " + var + " in term: "+ term);
switch (relop) {
case EQ:
return equal.add(term);
case LEQ:
return lessOrEqual.add(term);
case LE:
return lessThan.add(term);
}
throw new IllegalArgumentException("Unknown relop: " + relop);
}
public Set<RelationFractionPair> dumpRelations(Set<RelationFractionPair> result){
NormalizedFractionTerm last=null;
for (NormalizedFractionTerm eq : equal) {
if (last != null && (eq.equals(last) == false)) result.add(RelationFractionPair.createEqual(last,eq));
last=eq;
for (NormalizedFractionTerm more : greaterOrEqual) {
if (eq.equals(more) == false) result.add(RelationFractionPair.createLeq(eq,more));
}
for (NormalizedFractionTerm more : greaterThan) {
result.add(RelationFractionPair.createLess(eq,more));
}
}
for (NormalizedFractionTerm less : lessOrEqual) {
for (NormalizedFractionTerm more : greaterOrEqual) {
if (less.equals(more) == false) result.add(RelationFractionPair.createLeq(less,more));
}
for (NormalizedFractionTerm more : equal) {
if (less.equals(more) == false) result.add(RelationFractionPair.createLeq(less,more));
}
for (NormalizedFractionTerm more : greaterThan) {
result.add(RelationFractionPair.createLess(less,more));
}
}
for (NormalizedFractionTerm less : lessThan) {
for (NormalizedFractionTerm more : equal) {
result.add(RelationFractionPair.createLess(less,more));
}
for (NormalizedFractionTerm more : greaterOrEqual) {
result.add(RelationFractionPair.createLess(less,more));
}
for (NormalizedFractionTerm more : greaterThan) {
result.add(RelationFractionPair.createLess(less,more));
}
}
return result;
}
public Set<NormalizedFractionTerm> getGreaterThan(){
Cloner cloner=new Cloner();
greaterThan=cloner.deepClone(greaterThan);
Cloner cloner=new Cloner();
greaterThan=cloner.deepClone(greaterThan);
return greaterThan;
}
public Set<NormalizedFractionTerm> getGreaterOrEqual(){
Cloner cloner=new Cloner();
greaterOrEqual=cloner.deepClone(greaterOrEqual);
Cloner cloner=new Cloner();
greaterOrEqual=cloner.deepClone(greaterOrEqual);
return greaterOrEqual;
}
public Set<NormalizedFractionTerm> getEqual(){
Cloner cloner=new Cloner();
equal=cloner.deepClone(equal);
Cloner cloner=new Cloner();
equal=cloner.deepClone(equal);
return equal;
}
public Set<NormalizedFractionTerm> getLessThan(){
Cloner cloner=new Cloner();
lessThan=cloner.deepClone(lessThan);
Cloner cloner=new Cloner();
lessThan=cloner.deepClone(lessThan);
return lessThan;
}
public VariableFraction getVar(){
Cloner cloner=new Cloner();
var=cloner.deepClone(var);
Cloner cloner=new Cloner();
var=cloner.deepClone(var);
return var;
}
public Set<NormalizedFractionTerm> getLessOrEqual(){
Cloner cloner=new Cloner();
lessOrEqual=cloner.deepClone(lessOrEqual);
Cloner cloner=new Cloner();
lessOrEqual=cloner.deepClone(lessOrEqual);
return lessOrEqual;
}
public NormalizedFractionVisitor<Boolean> getVarCheck(){
Cloner cloner=new Cloner();
varCheck=cloner.deepClone(varCheck);
Cloner cloner=new Cloner();
varCheck=cloner.deepClone(varCheck);
return varCheck;
}
}
