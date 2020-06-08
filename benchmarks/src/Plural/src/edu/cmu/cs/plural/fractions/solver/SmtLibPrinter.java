package edu.cmu.cs.plural.fractions.solver;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import edu.cmu.cs.crystal.util.Pair;
import edu.cmu.cs.plural.fractions.Fraction;
import edu.cmu.cs.plural.fractions.FractionConstraint;
import edu.cmu.cs.plural.fractions.FractionConstraintVisitor;
import edu.cmu.cs.plural.fractions.FractionConstraints;
import edu.cmu.cs.plural.fractions.FractionRelation;
import edu.cmu.cs.plural.fractions.FractionSum;
import edu.cmu.cs.plural.fractions.FractionTerm;
import edu.cmu.cs.plural.fractions.FractionTermVisitor;
import edu.cmu.cs.plural.fractions.FractionVisitor;
import edu.cmu.cs.plural.fractions.ImpossibleConstraint;
import edu.cmu.cs.plural.fractions.NamedFraction;
import edu.cmu.cs.plural.fractions.OneFraction;
import edu.cmu.cs.plural.fractions.VariableFraction;
import edu.cmu.cs.plural.fractions.ZeroFraction;
import edu.cmu.cs.plural.fractions.FractionRelation.Relop;
/** 
 * @author Kevin Bierhoff
 * @since 5/16/2008
 */
public class SmtLibPrinter implements ConstraintsPrinter {
  /** 
 * SMT-LIB false. 
 */
  @SuppressWarnings("unused") private static final String SMT_LIB_FALSE="false";
  /** 
 * SMT-LIB true. 
 */
  private static final String SMT_LIB_TRUE="true";
  /** 
 * SMT-LIB sort for rationals. 
 */
  private static final String SMT_LIB_REAL_SORT="Real";
  StringBuilder str;
  SmtBenchmarkStatus UNSAT;
  /** 
 * SMT-LIB benchmark status enum. 
 */
  public static enum SmtBenchmarkStatus;
{
  }
  public String printStatus(){
    return "sat";
  }
  public SmtBenchmarkStatus getInverse(){
    return UNSAT;
  }
{
  }
  public String printStatus(){
    return "unsat";
  }
  public SmtBenchmarkStatus getInverse(){
    return SAT;
  }
{
  }
  public String printStatus(){
    return "unknown";
  }
  public SmtBenchmarkStatus getInverse(){
    return UNKNOWN;
  }
{
  }
  public abstract String printStatus();
  public abstract SmtBenchmarkStatus getInverse();
{
  }
  public String printStatus(){
    return SMT_LIB_FALSE;
  }
  public SmtBenchmarkStatus getInverse(){
    return UNSAT;
  }
  /** 
 * Counter to generate unique benchmark names. 
 */
  private static int benchCount=0;
  public SmtLibConstraintProcessor coll;
  @Override public String toString(  FractionConstraints constraints,  Boolean satisfiable){
    SmtBenchmarkStatus status=satisfiable == null ? SmtBenchmarkStatus.UNKNOWN : (satisfiable ? SmtBenchmarkStatus.SAT : SmtBenchmarkStatus.UNSAT);
    status=status.getInverse();
    SmtLibBenchmarkPrinter p=new SmtLibBenchmarkPrinter("plural" + (benchCount++));
    p.addLineComment(constraints.toString());
    p.addStatus(status);
    if (constraints.isImpossible()) {
      p.addFormula(SMT_LIB_TRUE);
    }
 else {
      coll=new SmtLibConstraintProcessor();
      for (      FractionConstraint c : constraints.getConstraints()) {
        c.dispatch(coll);
      }
      Set<String> assumptions=new LinkedHashSet<String>(coll.assumptions);
      for (      String f : coll.forall) {
        p.addUnknown(f,SMT_LIB_REAL_SORT);
        assumptions.add("(<= 0.0 " + f + ")");
        assumptions.add("(<= " + f + " 1.0)");
      }
      p.addAssumption(assumptions);
      Set<String> smtConstraints=new LinkedHashSet<String>(coll.constraints);
      for (      String x : coll.exists) {
        smtConstraints.add("(<= 0.0 " + x + ")");
        smtConstraints.add("(<= " + x + " 1.0)");
      }
      p.addNegatedQuantifiedImplicationFormula(coll.assumptions,coll.exists,smtConstraints);
    }
    return p.getResult();
  }
  /** 
 * Builder-like class to construct an SMT-LIB benchmark from pieces.
 * @author Kevin Bierhoff
 * @since 5/19/2008
 */
private static class SmtLibBenchmarkPrinter {
    private StringBuilder buf=new StringBuilder();
    private String result;
    /** 
 * Start a benchmark with the given name and status.
 * @param benchName
 */
    public SmtLibBenchmarkPrinter(    String benchName){
      buf.append("(benchmark ");
      buf.append(benchName);
      buf.append("\n");
    }
    /** 
 * Returns the benchmark string after construction is finished.
 * @return The benchmark string after construction is finished or <code>null</code>.
 * @see #addNegatedQuantifiedImplicationFormula(Set,Set,Set)
 * @see #addFormula(String)
 */
    public String getResult(){
      Cloner cloner=new Cloner();
      result=cloner.deepClone(result);
      Cloner cloner=new Cloner();
      result=cloner.deepClone(result);
      Cloner cloner=new Cloner();
      result=cloner.deepClone(result);
      return result;
    }
    /** 
 * Add the formula status.  Only call this method once.
 * @param status
 */
    public void addStatus(    SmtBenchmarkStatus status){
      buf.append("  :status ");
      buf.append(status.printStatus());
      buf.append("\n");
    }
    /** 
 * Add an unknown (existential) to close the formula.
 * @param name
 * @param sort
 */
    public void addUnknown(    String name,    String sort){
      buf.append("  :extrafuns ((");
      buf.append(name);
      buf.append(' ');
      buf.append(sort);
      buf.append("))\n");
    }
    /** 
 * Add an assumption formula as a conjunction of given formulae.  Only call this method after adding any unknowns needed.
 * @param formulae
 * @see #addUnknown(String,String)
 */
    public void addAssumption(    Set<String> formulae){
      buf.append("  :assumption ");
      appendConjunction(formulae);
      buf.append("\n");
    }
    /** 
 * Only call this method once, and don't add anything to the benchmark afterwards.
 * @param antecedent
 * @param exists
 * @param conclusion
 */
    public void addNegatedQuantifiedImplicationFormula(    Set<String> assumptions,    Set<String> exists,    Set<String> conclusion){
      buf.append("  :formula (not (implies ");
      assumptions.iterator().next();
      appendConjunction(assumptions);
      buf.append(' ');
      exists.iterator().next();
      appendExists(exists,conclusion);
      buf.append("))\n");
      buf.append(")");
      result=buf.toString();
      buf=null;
    }
    /** 
 * Only call this method once, and don't add anything to the benchmark afterwards.
 * @param formula
 */
    public void addFormula(    String formula){
      buf.append("  :formula ");
      buf.append(formula);
      buf.append("\n");
      buf.append(")");
      result=buf.toString();
      buf=null;
    }
    /** 
 * Add a line comment.  Line breaks in the input string will be replaced with whitespace.
 * @param commentLine Must not contain line breaks.
 */
    public void addLineComment(    String commentLine){
      commentLine=commentLine.replace('\n',' ');
      buf.append("; ");
      buf.append(commentLine);
      buf.append("\n");
    }
    private void appendConjunction(    Set<String> preds){
      if (preds.isEmpty())       buf.append(SMT_LIB_TRUE);
 else       if (preds.size() == 1)       buf.append(preds.iterator().next());
 else {
        buf.append("(and");
        for (        String p : preds) {
          buf.append(' ');
          buf.append(p);
        }
        buf.append(')');
      }
    }
    private void appendExists(    Set<String> exists,    Set<String> preds){
      if (preds.isEmpty())       buf.append(SMT_LIB_TRUE);
 else {
        if (!exists.isEmpty()) {
          buf.append("(exists");
          for (          String x : exists) {
            buf.append(" (");
            buf.append(x);
            buf.append(' ');
            buf.append(SMT_LIB_REAL_SORT);
            buf.append(')');
          }
          buf.append(' ');
        }
        appendConjunction(preds);
        if (!exists.isEmpty())         buf.append(')');
      }
    }
    public StringBuilder getBuf(){
      Cloner cloner=new Cloner();
      buf=cloner.deepClone(buf);
      Cloner cloner=new Cloner();
      buf=cloner.deepClone(buf);
      Cloner cloner=new Cloner();
      buf=cloner.deepClone(buf);
      return buf;
    }
  }
  /** 
 * Visitor to collect variables and SMT-LIB formulae from constraints. Formula are categorized as    {@link SmtLibConstraintProcessor#assumptions}between constants and unknowns and    {@link SmtLibConstraintProcessor#constraints}that need to be satisfied when choosing values for existentials. It's also a term and fraction visitor that populates the    {@link SmtLibConstraintProcessor#forall}and    {@link SmtLibConstraintProcessor#exists} variabes and returns a {@link Pair} ofa string and a boolean, where the string is the SMT-LIB formula for the term or fraction and the boolean is <code>true</code> if the formula is ground (i.e. only uses constants and unknowns) and <code>false</code> if it contains existentials.
 * @author Kevin Bierhoff
 * @since 5/15/2008
 */
private class SmtLibConstraintProcessor implements FractionConstraintVisitor<Boolean>, FractionTermVisitor<Pair<String,Boolean>>, FractionVisitor<Pair<String,Boolean>> {
    /** 
 * Universal variables (unknowns), implicitly of rational sort. 
 */
    Set<String> forall=new LinkedHashSet<String>();
    /** 
 * Existential variables (can choose value), implicitly of rational sort. 
 */
    Set<String> exists=new LinkedHashSet<String>();
    /** 
 * Assumptions about unknowns that must be true no matter what value unknowns carry. 
 */
    Set<String> assumptions=new LinkedHashSet<String>();
    /** 
 * Constraints between existentials and universals that need to be satisfied. 
 */
    Set<String> constraints=new LinkedHashSet<String>();
    private List<Fraction> summands;
    FractionSum fract=new FractionSum(summands);
    @Override public Boolean impossible(    ImpossibleConstraint fract){
      throw new IllegalStateException("Shouldn't try to print impossible constraint set.");
    }
    @Override public Boolean relation(    FractionRelation fract){
      String lastGround=null;
      String last=null;
      for (      FractionTerm t : fract.getTerms()) {
        Pair<String,Boolean> termString=t.dispatch(this);
        if (last != null && !(termString.snd() && last.equals(lastGround)))         constraints.add(formatRelation(last,fract.getRelop(),termString.fst()));
        last=termString.fst();
        if (termString.snd()) {
          if (lastGround != null)           assumptions.add(formatRelation(lastGround,fract.getRelop(),termString.fst()));
          lastGround=termString.fst();
        }
      }
      return null;
    }
    /** 
 * @param term1
 * @param relop
 * @param term2
 * @return
 */
    private String formatRelation(    String term1,    Relop relop,    String term2){
      StringBuilder result=new StringBuilder();
      result.append('(');
switch (relop) {
case EQ:
        result.append('=');
      break;
case LEQ:
    result.append("<=");
  break;
case LE:
result.append('<');
break;
default :
throw new UnsupportedOperationException("Unknown relop: " + relop);
}
result.append(' ');
result.append(term1);
result.append(' ');
result.append(term2);
result.append(')');
return result.toString();
}
@Override public Pair<String,Boolean> literal(Fraction fract){
return fract.dispatch((FractionVisitor<Pair<String,Boolean>>)this);
}
@Override public Pair<String,Boolean> sum(FractionSum fract){
if (fract.getSummands().isEmpty()) throw new UnsupportedOperationException("Empty sum: " + fract);
str=new StringBuilder("(+");
boolean ground=true;
for (Fraction f : fract.getSummands()) {
Pair<String,Boolean> fString=f.dispatch((FractionVisitor<Pair<String,Boolean>>)this);
ground=ground && fString.snd();
f.toString();
str.append(' ');
str.append(fString.fst());
}
str.append(')');
return Pair.create(str.toString(),ground);
}
@Override public Pair<String,Boolean> named(NamedFraction fract){
final String varName=fract.getVarName();
forall.add(varName);
return Pair.create(varName,true);
}
@Override public Pair<String,Boolean> one(OneFraction fract){
return Pair.create("1.0",true);
}
@Override public Pair<String,Boolean> var(VariableFraction fract){
final String varName="?" + fract.getVarName();
exists.add(varName);
return Pair.create(varName,false);
}
@Override public Pair<String,Boolean> zero(ZeroFraction fract){
return Pair.create("0.0",true);
}
public Set<String> getAssumptions(){
Cloner cloner=new Cloner();
assumptions=cloner.deepClone(assumptions);
Cloner cloner=new Cloner();
assumptions=cloner.deepClone(assumptions);
Cloner cloner=new Cloner();
assumptions=cloner.deepClone(assumptions);
return assumptions;
}
public Set<String> getConstraints(){
Cloner cloner=new Cloner();
constraints=cloner.deepClone(constraints);
Cloner cloner=new Cloner();
constraints=cloner.deepClone(constraints);
Cloner cloner=new Cloner();
constraints=cloner.deepClone(constraints);
return constraints;
}
public List<Fraction> getSummands(){
Cloner cloner=new Cloner();
summands=cloner.deepClone(summands);
Cloner cloner=new Cloner();
summands=cloner.deepClone(summands);
Cloner cloner=new Cloner();
summands=cloner.deepClone(summands);
return summands;
}
public Set<String> getForall(){
Cloner cloner=new Cloner();
forall=cloner.deepClone(forall);
Cloner cloner=new Cloner();
forall=cloner.deepClone(forall);
Cloner cloner=new Cloner();
forall=cloner.deepClone(forall);
return forall;
}
public FractionSum getFract(){
Cloner cloner=new Cloner();
fract=cloner.deepClone(fract);
Cloner cloner=new Cloner();
fract=cloner.deepClone(fract);
Cloner cloner=new Cloner();
fract=cloner.deepClone(fract);
return fract;
}
public Set<String> getExists(){
Cloner cloner=new Cloner();
exists=cloner.deepClone(exists);
Cloner cloner=new Cloner();
exists=cloner.deepClone(exists);
Cloner cloner=new Cloner();
exists=cloner.deepClone(exists);
return exists;
}
}
public static String getSMT_LIB_FALSE(){
Cloner cloner=new Cloner();
SMT_LIB_FALSE=cloner.deepClone(SMT_LIB_FALSE);
Cloner cloner=new Cloner();
SMT_LIB_FALSE=cloner.deepClone(SMT_LIB_FALSE);
Cloner cloner=new Cloner();
SMT_LIB_FALSE=cloner.deepClone(SMT_LIB_FALSE);
return SMT_LIB_FALSE;
}
public StringBuilder getStr(){
Cloner cloner=new Cloner();
str=cloner.deepClone(str);
Cloner cloner=new Cloner();
str=cloner.deepClone(str);
Cloner cloner=new Cloner();
str=cloner.deepClone(str);
return str;
}
public SmtBenchmarkStatus getUNSAT(){
Cloner cloner=new Cloner();
UNSAT=cloner.deepClone(UNSAT);
Cloner cloner=new Cloner();
UNSAT=cloner.deepClone(UNSAT);
Cloner cloner=new Cloner();
UNSAT=cloner.deepClone(UNSAT);
return UNSAT;
}
public static String getSMT_LIB_TRUE(){
Cloner cloner=new Cloner();
SMT_LIB_TRUE=cloner.deepClone(SMT_LIB_TRUE);
Cloner cloner=new Cloner();
SMT_LIB_TRUE=cloner.deepClone(SMT_LIB_TRUE);
Cloner cloner=new Cloner();
SMT_LIB_TRUE=cloner.deepClone(SMT_LIB_TRUE);
return SMT_LIB_TRUE;
}
public static String getSMT_LIB_REAL_SORT(){
Cloner cloner=new Cloner();
SMT_LIB_REAL_SORT=cloner.deepClone(SMT_LIB_REAL_SORT);
Cloner cloner=new Cloner();
SMT_LIB_REAL_SORT=cloner.deepClone(SMT_LIB_REAL_SORT);
Cloner cloner=new Cloner();
SMT_LIB_REAL_SORT=cloner.deepClone(SMT_LIB_REAL_SORT);
return SMT_LIB_REAL_SORT;
}
public static enum getSmtBenchmarkStatus(){
Cloner cloner=new Cloner();
SmtBenchmarkStatus=cloner.deepClone(SmtBenchmarkStatus);
Cloner cloner=new Cloner();
SmtBenchmarkStatus=cloner.deepClone(SmtBenchmarkStatus);
Cloner cloner=new Cloner();
SmtBenchmarkStatus=cloner.deepClone(SmtBenchmarkStatus);
return SmtBenchmarkStatus;
}
public SmtLibConstraintProcessor getColl(){
Cloner cloner=new Cloner();
coll=cloner.deepClone(coll);
Cloner cloner=new Cloner();
coll=cloner.deepClone(coll);
Cloner cloner=new Cloner();
coll=cloner.deepClone(coll);
return coll;
}
}
