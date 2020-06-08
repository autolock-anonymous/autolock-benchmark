package edu.cmu.cs.crystal.analysis.constant;
import java.util.Set;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import edu.cmu.cs.crystal.AbstractCrystalMethodAnalysis;
import edu.cmu.cs.crystal.analysis.alias.AliasLE;
import edu.cmu.cs.crystal.analysis.alias.MayAliasTransferFunction;
import edu.cmu.cs.crystal.flow.TupleLatticeElement;
import edu.cmu.cs.crystal.internal.CrystalRuntimeException;
import edu.cmu.cs.crystal.tac.BranchSensitiveTACAnalysis;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.TACInstruction;
import edu.cmu.cs.crystal.tac.Variable;
public class ConstantAnalysis extends AbstractCrystalMethodAnalysis {
  BooleanConstantLE value;
  private BranchSensitiveTACAnalysis<TupleLatticeElement<Variable,BooleanConstantLE>> fa;
  public ConstantAnalysis(){
  }
  public boolean hasPreciseValueAfter(  Variable var,  ASTNode node,  boolean after){
    BooleanConstantLE value;
    if (after)     value=fa.getResultsAfter(node).get(var);
 else     value=fa.getResultsBefore(node).get(var);
    return value.equals(BooleanConstantLE.TRUE) || value.equals(BooleanConstantLE.FALSE);
  }
  public boolean getValue(  Variable var,  ASTNode node,  boolean after){
    Cloner cloner=new Cloner();
    value=cloner.deepClone(value);
    Cloner cloner=new Cloner();
    value=cloner.deepClone(value);
    if (after)     value=fa.getResultsAfter(node).get(var);
 else     value=fa.getResultsBefore(node).get(var);
    if (value.equals(BooleanConstantLE.TRUE))     return true;
 else     if (value.equals(BooleanConstantLE.FALSE))     return false;
 else     throw new CrystalRuntimeException("Can not get the constant value as it is not precise.");
  }
  @Override public void analyzeMethod(  MethodDeclaration d){
    ITACBranchSensitiveTransferFunction<TupleLatticeElement<Variable,BooleanConstantLE>> tf=new ConstantTransferFunction();
    fa=new BranchSensitiveTACAnalysis<TupleLatticeElement<Variable,BooleanConstantLE>>(tf,this.analysisInput.getComUnitTACs().unwrap());
    TupleLatticeElement<Variable,BooleanConstantLE> finalLattice=fa.getResultsAfter(d);
  }
  private void printLattice(  TupleLatticeElement<Variable,BooleanConstantLE> lattice){
    for (    Variable var : lattice.getKeySet()) {
      BooleanConstantLE bool=lattice.get(var);
      if (bool != BooleanConstantLE.BOTTOM)       reporter.debugOut().println(var.getSourceString() + ":" + bool.toString());
    }
  }
  public TupleLatticeElement<Variable,BooleanConstantLE> getResultsBefore(  TACInstruction instr){
    return fa.getResultsBefore(instr.getNode());
  }
  public BranchSensitiveTACAnalysis<TupleLatticeElement<Variable,BooleanConstantLE>> getFa(){
    Cloner cloner=new Cloner();
    fa=cloner.deepClone(fa);
    Cloner cloner=new Cloner();
    fa=cloner.deepClone(fa);
    return fa;
  }
}
