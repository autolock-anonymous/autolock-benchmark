package edu.cmu.cs.crystal.analysis.alias;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import edu.cmu.cs.crystal.AbstractCrystalMethodAnalysis;
import edu.cmu.cs.crystal.analysis.metrics.LoopCountingAnalysis;
import edu.cmu.cs.crystal.flow.ITACFlowAnalysis;
import edu.cmu.cs.crystal.flow.TupleLatticeElement;
import edu.cmu.cs.crystal.tac.SourceVariable;
import edu.cmu.cs.crystal.tac.TACFlowAnalysis;
import edu.cmu.cs.crystal.tac.TACInstruction;
import edu.cmu.cs.crystal.tac.Variable;
public class MayAliasAnalysis extends AbstractCrystalMethodAnalysis {
  private static final Logger log=Logger.getLogger(MayAliasAnalysis.class.getName());
  private ITACFlowAnalysis<TupleLatticeElement<Variable,AliasLE>> fa;
  private LoopCountingAnalysis loopCounter;
  /** 
 * This visitor will only be called if log level is   {@link Level#FINE} or lower.
 */
  private ASTVisitor checkResults;
  public MayAliasAnalysis(  LoopCountingAnalysis analysis){
    loopCounter=analysis;
  }
  /** 
 * Get the aliases of a variable at a particular node. Returns the aliases after the given node has been evaluated.
 * @param var The variable to look for
 * @param node The ASTNode that we just evaluated
 * @return A set of possible aliases, including temporary variables.
 */
  public Set<Variable> getAfterAliases(  Variable var,  ASTNode node){
    return getAliases(fa.getResultsAfter(node),var);
  }
  /** 
 * Get the aliases of a variable at a particular node. Returns the aliases after the given node has been evaluated.
 * @param var The variable to look for
 * @param node The ASTNode that we just evaluated
 * @return A set of possible aliases, including temporary variables.
 */
  public Set<Variable> getBeforeAliases(  Variable var,  ASTNode node){
    return getAliases(fa.getResultsBefore(node),var);
  }
  public Set<ObjectLabel> getBeforeAliasLabels(  Variable var,  ASTNode node){
    return fa.getResultsBefore(node).get(var).getLabels();
  }
  public Set<ObjectLabel> getAfterAliasLabels(  Variable var,  ASTNode node){
    TupleLatticeElement<Variable,AliasLE> le=fa.getResultsAfter(node);
    return le.get(var).getLabels();
  }
  /** 
 * Get all the object labels at this node which have the given type, regardless of who the alias is. This will also include anything which is a subtype of the given type. This is expensive.
 * @param typeBinding
 * @param node
 * @return A set of all object labels at node which are castable to typeBinding
 */
  public Set<ObjectLabel> getBeforeAliasLabels(  ITypeBinding typeBinding,  ASTNode node){
    return getAliasLabels(typeBinding,fa.getResultsBefore(node));
  }
  public Set<ObjectLabel> getBeforeAliasLabels(  String typeName,  ASTNode node){
    return getAliasLabels(typeName,fa.getResultsBefore(node));
  }
  public Set<ObjectLabel> getAfterAliasLabels(  String typeName,  ASTNode node){
    return getAliasLabels(typeName,fa.getResultsAfter(node));
  }
  public Set<ObjectLabel> getAfterAliasLabels(  ITypeBinding typeBinding,  ASTNode node){
    return getAliasLabels(typeBinding,fa.getResultsAfter(node));
  }
  private Set<ObjectLabel> getAliasLabels(  String typeName,  TupleLatticeElement<Variable,AliasLE> le){
    Set<ObjectLabel> labels=new HashSet<ObjectLabel>();
    for (    Variable var : le.getKeySet()) {
      Set<ObjectLabel> aliases=le.get(var).getLabels();
      for (      ObjectLabel label : aliases) {
        if (typeName.equals(label.getType().getQualifiedName()))         labels.add(label);
      }
    }
    return labels;
  }
  private Set<ObjectLabel> getAliasLabels(  ITypeBinding typeBinding,  TupleLatticeElement<Variable,AliasLE> le){
    Set<ObjectLabel> labels=new HashSet<ObjectLabel>();
    for (    Variable var : le.getKeySet()) {
      Set<ObjectLabel> aliases=le.get(var).getLabels();
      for (      ObjectLabel label : aliases) {
        if (typeBinding.isCastCompatible(label.getType()))         labels.add(label);
      }
    }
    return labels;
  }
  @Override public void analyzeMethod(  MethodDeclaration d){
    MayAliasTransferFunction tf=new MayAliasTransferFunction(this);
    fa=new TACFlowAnalysis<TupleLatticeElement<Variable,AliasLE>>(tf,this.analysisInput.getComUnitTACs().unwrap());
    TupleLatticeElement<Variable,AliasLE> finalLattice=fa.getResultsAfter(d);
    if (log.isLoggable(Level.FINE))     d.accept(checkResults);
  }
  private void printLattice(  TupleLatticeElement<Variable,AliasLE> lattice){
    for (    Variable var : lattice.getKeySet()) {
      if (!(var instanceof SourceVariable))       continue;
      AliasLE aliases=lattice.get(var);
      reporter.debugOut().println(var.getSourceString() + ":");
      for (      Variable alias : getAliases(lattice,var)) {
        reporter.debugOut().println("   " + alias.getSourceString() + "("+ alias.toString()+ ")");
      }
    }
  }
  private Set<Variable> getAliases(  TupleLatticeElement<Variable,AliasLE> tuple,  Variable varToFind){
    Set<ObjectLabel> labels=tuple.get(varToFind).getLabels();
    Set<Variable> aliases=new HashSet<Variable>();
    for (    Variable var : tuple.getKeySet()) {
      if (var != varToFind && tuple.get(var).hasAnyLabels(labels))       aliases.add(var);
    }
    return aliases;
  }
  public boolean isInLoop(  TACInstruction declaringInstr){
    return loopCounter.isInLoop(declaringInstr.getNode());
  }
  public TupleLatticeElement<Variable,AliasLE> getResultsAfter(  TACInstruction instr){
    return fa.getResultsAfter(instr.getNode());
  }
  public Set<ObjectLabel> getAllLabelsBefore(  TACInstruction instr){
    return getAllLabels(fa.getResultsBefore(instr.getNode()));
  }
  public Set<ObjectLabel> getAllLabelsAfter(  TACInstruction instr){
    return getAllLabels(fa.getResultsAfter(instr.getNode()));
  }
  private Set<ObjectLabel> getAllLabels(  TupleLatticeElement<Variable,AliasLE> lattice){
    Set allLabels=new HashSet<ObjectLabel>();
    for (    Variable var : lattice.getKeySet()) {
      AliasLE aliases=lattice.get(var);
      allLabels.addAll(aliases.getLabels());
    }
    return allLabels;
  }
  public Variable getThisVar(  MethodDeclaration methodDecl){
    return fa.getThisVariable(methodDecl);
  }
  public static Logger getLog(){
    Cloner cloner=new Cloner();
    log=cloner.deepClone(log);
    Cloner cloner=new Cloner();
    log=cloner.deepClone(log);
    return log;
  }
  public ITACFlowAnalysis<TupleLatticeElement<Variable,AliasLE>> getFa(){
    Cloner cloner=new Cloner();
    fa=cloner.deepClone(fa);
    Cloner cloner=new Cloner();
    fa=cloner.deepClone(fa);
    return fa;
  }
  public LoopCountingAnalysis getLoopCounter(){
    Cloner cloner=new Cloner();
    loopCounter=cloner.deepClone(loopCounter);
    Cloner cloner=new Cloner();
    loopCounter=cloner.deepClone(loopCounter);
    return loopCounter;
  }
  public ASTVisitor getCheckResults(){
    Cloner cloner=new Cloner();
    checkResults=cloner.deepClone(checkResults);
    Cloner cloner=new Cloner();
    checkResults=cloner.deepClone(checkResults);
    return checkResults;
  }
}
