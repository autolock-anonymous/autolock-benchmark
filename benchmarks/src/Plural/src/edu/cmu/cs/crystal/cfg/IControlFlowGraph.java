package edu.cmu.cs.crystal.cfg;
import java.util.Map;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import att.grappa.Graph;
public interface IControlFlowGraph {
  public ICFGNode getStartNode();
  public ICFGNode getEndNode();
  public void createGraph(  MethodDeclaration startpoint);
  public ICFGNode getUberReturn();
  public ICFGNode getUndeclaredExit();
  public Map<ITypeBinding,? extends ICFGNode> getExceptionalExits();
  public Graph getDotGraph();
}
