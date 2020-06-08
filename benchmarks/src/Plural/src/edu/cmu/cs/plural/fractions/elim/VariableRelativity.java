package edu.cmu.cs.plural.fractions.elim;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeoutException;
import edu.cmu.cs.plural.fractions.Fraction;
import edu.cmu.cs.plural.fractions.NamedFraction;
import edu.cmu.cs.plural.fractions.FractionRelation.Relop;
/** 
 * @author Kevin Bierhoff
 */
public class VariableRelativity {
  private final WeakHashMap<NormalizedFractionConstraint,Boolean> rejectedCache=new WeakHashMap<NormalizedFractionConstraint,Boolean>();
  private final Fraction var;
  private final Map<NamedFraction,NamedFraction> upperBounds;
  private final Set<NormalizedFractionConstraint> variable;
  private final Set<NormalizedFractionConstraint> ground;
  private final Set<NormalizedFractionSum> lessThan=new HashSet<NormalizedFractionSum>();
  private final Set<NormalizedFractionSum> lessOrEqual=new HashSet<NormalizedFractionSum>();
  private final Set<NormalizedFractionSum> equal=new HashSet<NormalizedFractionSum>();
  private final Set<NormalizedFractionSum> greaterOrEqual=new HashSet<NormalizedFractionSum>();
  private final Set<NormalizedFractionSum> greaterThan=new HashSet<NormalizedFractionSum>();
  private final long deadline;
  private LinkedList<NormalizedFractionConstraint> searchVariable;
  private int lastTimeoutCheck;
  public VariableRelativity(  Fraction var,  Map<NamedFraction,NamedFraction> upperBounds,  Set<NormalizedFractionConstraint> variable,  Set<NormalizedFractionConstraint> ground,  long deadline){
    this.var=var;
    this.upperBounds=upperBounds;
    this.variable=variable;
    this.ground=ground;
    this.deadline=deadline;
  }
  public boolean addRight(  Relop relop,  NormalizedFractionSum term){
    if (term.getCoefficient(var).isZero() == false)     throw new IllegalArgumentException("Eliminated variable " + var + " in term: "+ term);
switch (relop) {
case EQ:
      if (equal.add(term)) {
        greaterOrEqual.remove(term);
        lessOrEqual.remove(term);
        return true;
      }
    return false;
case LEQ:
  if (equal.contains(term) || greaterThan.contains(term))   return false;
if (lessOrEqual.remove(term)) {
  return equal.add(term);
}
return greaterOrEqual.add(term);
case LE:
if (greaterThan.add(term)) {
greaterOrEqual.remove(term);
return true;
}
return false;
}
throw new IllegalArgumentException("Unknown relop: " + relop);
}
public boolean addLeft(NormalizedFractionSum term,Relop relop){
if (term.getCoefficient(var).isZero() == false) throw new IllegalArgumentException("Eliminated variable " + var + " in term: "+ term);
switch (relop) {
case EQ:
if (equal.add(term)) {
greaterOrEqual.remove(term);
lessOrEqual.remove(term);
return true;
}
return false;
case LEQ:
if (equal.contains(term) || lessThan.contains(term)) return false;
if (greaterOrEqual.remove(term)) {
return equal.add(term);
}
return lessOrEqual.add(term);
case LE:
if (lessThan.add(term)) {
lessOrEqual.remove(term);
return true;
}
return false;
}
throw new IllegalArgumentException("Unknown relop: " + relop);
}
public Set<NormalizedFractionConstraint> dumpRelations() throws TimeoutException {
searchVariable=new LinkedList<NormalizedFractionConstraint>(variable);
NormalizedFractionSum last=null;
for (NormalizedFractionSum eq : equal) {
if (last != null && (eq.equals(last) == false)) dumpRelation(last,Relop.EQ,eq);
last=eq;
for (NormalizedFractionSum more : greaterOrEqual) {
if (eq.equals(more) == false) dumpRelation(eq,Relop.LEQ,more);
}
for (NormalizedFractionSum more : greaterThan) {
dumpRelation(eq,Relop.LE,more);
}
}
for (NormalizedFractionSum less : lessOrEqual) {
for (NormalizedFractionSum more : greaterOrEqual) {
if (less.equals(more) == false) dumpRelation(less,Relop.LEQ,more);
}
for (NormalizedFractionSum more : equal) {
if (less.equals(more) == false) dumpRelation(less,Relop.LEQ,more);
}
for (NormalizedFractionSum more : greaterThan) {
dumpRelation(less,Relop.LE,more);
}
}
for (NormalizedFractionSum less : lessThan) {
for (NormalizedFractionSum more : equal) {
dumpRelation(less,Relop.LE,more);
}
for (NormalizedFractionSum more : greaterOrEqual) {
dumpRelation(less,Relop.LE,more);
}
for (NormalizedFractionSum more : greaterThan) {
dumpRelation(less,Relop.LE,more);
}
}
return variable;
}
private void dumpRelation(NormalizedFractionSum less,Relop relop,NormalizedFractionSum more) throws TimeoutException {
if (++lastTimeoutCheck >= 100) {
lastTimeoutCheck=0;
if (System.currentTimeMillis() > deadline) throw new TimeoutException("Exceeded deadline: " + deadline);
}
NormalizedFractionConstraint c=NormalizedFractionConstraint.createConstraint(less,relop,more);
if (rejectedCache.containsKey(c)) {
return;
}
 else if (c.isTrueWithAssumptions(upperBounds)) {
rejectedCache.put(c,null);
}
 else {
if (c.isGround()) ground.add(c);
 else if (variable.contains(c)) {
return;
}
 else {
for (Iterator<NormalizedFractionConstraint> vcIter=searchVariable.iterator(); vcIter.hasNext(); ) {
NormalizedFractionConstraint vc=vcIter.next();
if (vc.dominates(c)) {
rejectedCache.put(c,null);
vcIter.remove();
searchVariable.addFirst(vc);
return;
}
if (c.dominates(vc)) {
vcIter.remove();
variable.remove(vc);
rejectedCache.put(vc,null);
}
}
searchVariable.addFirst(c);
variable.add(c);
}
}
}
public Set<NormalizedFractionConstraint> getGround(){
Cloner cloner=new Cloner();
ground=cloner.deepClone(ground);
Cloner cloner=new Cloner();
ground=cloner.deepClone(ground);
return ground;
}
public Set<NormalizedFractionSum> getLessThan(){
Cloner cloner=new Cloner();
lessThan=cloner.deepClone(lessThan);
Cloner cloner=new Cloner();
lessThan=cloner.deepClone(lessThan);
return lessThan;
}
public Set<NormalizedFractionSum> getGreaterOrEqual(){
Cloner cloner=new Cloner();
greaterOrEqual=cloner.deepClone(greaterOrEqual);
Cloner cloner=new Cloner();
greaterOrEqual=cloner.deepClone(greaterOrEqual);
return greaterOrEqual;
}
public WeakHashMap<NormalizedFractionConstraint,Boolean> getRejectedCache(){
Cloner cloner=new Cloner();
rejectedCache=cloner.deepClone(rejectedCache);
Cloner cloner=new Cloner();
rejectedCache=cloner.deepClone(rejectedCache);
return rejectedCache;
}
public Map<NamedFraction,NamedFraction> getUpperBounds(){
Cloner cloner=new Cloner();
upperBounds=cloner.deepClone(upperBounds);
Cloner cloner=new Cloner();
upperBounds=cloner.deepClone(upperBounds);
return upperBounds;
}
public Set<NormalizedFractionConstraint> getVariable(){
Cloner cloner=new Cloner();
variable=cloner.deepClone(variable);
Cloner cloner=new Cloner();
variable=cloner.deepClone(variable);
return variable;
}
public Fraction getVar(){
Cloner cloner=new Cloner();
var=cloner.deepClone(var);
Cloner cloner=new Cloner();
var=cloner.deepClone(var);
return var;
}
public Set<NormalizedFractionSum> getEqual(){
Cloner cloner=new Cloner();
equal=cloner.deepClone(equal);
Cloner cloner=new Cloner();
equal=cloner.deepClone(equal);
return equal;
}
public Set<NormalizedFractionSum> getLessOrEqual(){
Cloner cloner=new Cloner();
lessOrEqual=cloner.deepClone(lessOrEqual);
Cloner cloner=new Cloner();
lessOrEqual=cloner.deepClone(lessOrEqual);
return lessOrEqual;
}
public Set<NormalizedFractionSum> getGreaterThan(){
Cloner cloner=new Cloner();
greaterThan=cloner.deepClone(greaterThan);
Cloner cloner=new Cloner();
greaterThan=cloner.deepClone(greaterThan);
return greaterThan;
}
public LinkedList<NormalizedFractionConstraint> getSearchVariable(){
Cloner cloner=new Cloner();
searchVariable=cloner.deepClone(searchVariable);
Cloner cloner=new Cloner();
searchVariable=cloner.deepClone(searchVariable);
return searchVariable;
}
}
