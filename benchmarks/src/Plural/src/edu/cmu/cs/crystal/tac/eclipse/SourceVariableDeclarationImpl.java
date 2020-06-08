package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.SourceVariable;
import edu.cmu.cs.crystal.tac.SourceVariableDeclaration;
/** 
 * T x.  This node represents the declaration of a variable in the source, i.e. a method parameter or local variable. Notice that temporary and keyword variables do <i>not</i> have an explicit declaration.
 * @author Kevin Bierhoff
 */
class SourceVariableDeclarationImpl extends AbstractTACInstruction<VariableDeclaration> implements SourceVariableDeclaration {
  Object obj;
  /** 
 * @param node
 * @param tac
 * @see AbstractTACInstruction#AbstractTACInstruction(ASTNode,IEclipseVariableQuery)
 */
  public SourceVariableDeclarationImpl(  VariableDeclaration node,  IEclipseVariableQuery tac){
    super(node,tac);
    IVariableBinding b=node.resolveBinding();
    if (b.isField())     throw new IllegalArgumentException("Field declaration: " + node);
    if (b.isEnumConstant())     throw new IllegalArgumentException("Enum declaration: " + node);
  }
  public SourceVariable getDeclaredVariable(){
    return (SourceVariable)targetVariable(this.node);
  }
  public IVariableBinding resolveBinding(){
    return this.node.resolveBinding();
  }
  public boolean isCaughtVariable(){
    ASTNode parent=this.node.getParent();
    if (parent instanceof CatchClause) {
      CatchClause catch_clause=(CatchClause)parent;
      if (catch_clause.getException().equals(this.getNode())) {
        return true;
      }
    }
    return false;
  }
  public boolean isFormalParameter(){
    return this.node.getParent() instanceof MethodDeclaration;
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver17(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    obj=new Object();
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    return "declare " + getDeclaredVariable();
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
