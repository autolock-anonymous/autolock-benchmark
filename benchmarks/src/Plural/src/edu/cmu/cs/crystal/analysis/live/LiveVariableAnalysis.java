package edu.cmu.cs.crystal.analysis.live;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import edu.cmu.cs.crystal.AbstractCrystalMethodAnalysis;
import edu.cmu.cs.crystal.flow.TupleLatticeElement;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.TACFlowAnalysis;
import edu.cmu.cs.crystal.tac.Variable;
import edu.cmu.cs.crystal.util.Utilities;
public class LiveVariableAnalysis extends AbstractCrystalMethodAnalysis {
  public static LiveVariableAnalysis Instance;
  private TACFlowAnalysis<TupleLatticeElement<Variable,LiveVariableLE>> fa;
  public LiveVariableAnalysis(){
    Instance=this;
  }
  public boolean isLiveBefore(  Variable var,  ASTNode node){
    if (Utilities.getMethodDeclaration(node) == null)     return true;
 else     return fa.getResultsBefore(node).get(var) == LiveVariableLE.LIVE;
  }
  @Override public void analyzeMethod(  MethodDeclaration d){
    ITACTransferFunction<TupleLatticeElement<Variable,LiveVariableLE>> tf=new LiveVariableTransferFunction();
    fa=new TACFlowAnalysis<TupleLatticeElement<Variable,LiveVariableLE>>(tf,this.analysisInput.getComUnitTACs().unwrap());
    TupleLatticeElement<Variable,LiveVariableLE> finalLattice=fa.getResultsBefore(d);
  }
  public void printLattice(  TupleLatticeElement<Variable,LiveVariableLE> lattice){
    for (    Variable var : lattice.getKeySet()) {
      LiveVariableLE live=lattice.get(var);
      if (live == LiveVariableLE.LIVE)       reporter.debugOut().println(var.getSourceString() + ":" + " LIVE  ");
 else       reporter.debugOut().println(var.getSourceString() + ":" + " DEAD  ");
    }
    reporter.debugOut().println("\n\n");
  }
  public TACFlowAnalysis<TupleLatticeElement<Variable,LiveVariableLE>> getFa(){
    Cloner cloner=new Cloner();
    fa=cloner.deepClone(fa);
    Cloner cloner=new Cloner();
    fa=cloner.deepClone(fa);
    return fa;
  }
  public static LiveVariableAnalysis getInstance(){
    Cloner cloner=new Cloner();
    Instance=cloner.deepClone(Instance);
    Cloner cloner=new Cloner();
    Instance=cloner.deepClone(Instance);
    return Instance;
  }
}
