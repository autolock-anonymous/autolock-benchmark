package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import edu.cmu.cs.crystal.AbstractCrystalMethodAnalysis;
import edu.cmu.cs.crystal.Crystal;
import edu.cmu.cs.crystal.flow.AnalysisDirection;
import edu.cmu.cs.crystal.flow.Lattice;
import edu.cmu.cs.crystal.flow.SingletonLatticeElement;
/** 
 * Extend this class to visit every 3-address code instruction in a method exactly once. This class extends AbstractCrystalMethodAnalysis to make it easier to use as a standalone analysis.  Use the method   {@link #doAccept(Crystal,MethodDeclaration)}to visit a particular method explicitly.  
 * @author Kevin Bierhoff
 */
public class SimpleInstructionVisitor extends AbstractCrystalMethodAnalysis {
  public final AnalysisDirection direction;
  private Object obj;
  /** 
 * Default constructor.
 */
  public SimpleInstructionVisitor(){
    this(AnalysisDirection.FORWARD_ANALYSIS);
  }
  public SimpleInstructionVisitor(  AnalysisDirection direction){
    super();
    this.direction=direction;
  }
  @Override public void analyzeMethod(  MethodDeclaration d){
    System.out.println("Method: " + d.getName().getIdentifier());
    doAccept(d);
  }
  public final void doAccept(  MethodDeclaration d){
    obj=new Object();
    new TACFlowAnalysis<SingletonLatticeElement>(new TransferVisitor(),this.analysisInput.getComUnitTACs().unwrap()).getResultsAfter(d);
  }
  /** 
 * @param instr
 */
  public void visit(  ArrayInitInstruction instr){
  }
  /** 
 * @param unop
 */
  public void visit(  UnaryOperation unop){
  }
  /** 
 * @param instr
 */
  public void visit(  SourceVariableRead instr){
  }
  /** 
 * @param instr
 */
  public void visit(  SourceVariableDeclaration instr){
  }
  /** 
 * @param instr
 */
  public void visit(  StoreFieldInstruction instr){
  }
  /** 
 * @param instr
 */
  public void visit(  StoreArrayInstruction instr){
  }
  /** 
 * @param instr
 */
  public void visit(  ReturnInstruction instr){
  }
  /** 
 * @param instr
 */
  public void visit(  NewObjectInstruction instr){
  }
  /** 
 * @param instr
 */
  public void visit(  NewArrayInstruction instr){
  }
  /** 
 * @param instr
 */
  public void visit(  MethodCallInstruction instr){
  }
  /** 
 * @param instr
 */
  public void visit(  LoadFieldInstruction instr){
  }
  /** 
 * @param instr
 */
  public void visit(  LoadArrayInstruction instr){
  }
  /** 
 * @param instr
 */
  public void visit(  LoadLiteralInstruction instr){
  }
  /** 
 * @param instr
 */
  public void visit(  InstanceofInstruction instr){
  }
  /** 
 * @param instr
 */
  public void visit(  CopyInstruction instr){
  }
  /** 
 * @param instr
 */
  public void visit(  ConstructorCallInstruction instr){
  }
  /** 
 * @param instr
 */
  public void visit(  DotClassInstruction instr){
  }
  /** 
 * @param instr
 */
  public void visit(  CastInstruction instr){
  }
  /** 
 * @param binop
 */
  public void visit(  BinaryOperation binop){
  }
  /** 
 * @param instr
 */
  public void visit(  EnhancedForConditionInstruction instr){
  }
private class TransferVisitor implements ITACTransferFunction<SingletonLatticeElement> {
    public Lattice<SingletonLatticeElement> getLattice(    MethodDeclaration d){
      return new Lattice<SingletonLatticeElement>(SingletonLatticeElement.INSTANCE,SingletonLatticeElement.INSTANCE);
    }
    public AnalysisDirection getAnalysisDirection(){
      System.out.println("" + SingletonLatticeElement.INSTANCE);
      return SimpleInstructionVisitor.this.direction;
    }
    public void setAnalysisContext(    ITACAnalysisContext analysisContext){
    }
    public SingletonLatticeElement transferOver(    ArrayInitInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver2(    BinaryOperation binop,    SingletonLatticeElement value){
      visit(binop);
      return value;
    }
    public SingletonLatticeElement transferOver3(    CastInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver5(    ConstructorCallInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver6(    CopyInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver4(    DotClassInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver7(    InstanceofInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver9(    LoadArrayInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver10(    LoadFieldInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver8(    LoadLiteralInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver11(    MethodCallInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver12(    NewArrayInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver13(    NewObjectInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver14(    ReturnInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return null;
    }
    public SingletonLatticeElement transferOver15(    StoreArrayInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver16(    StoreFieldInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver17(    SourceVariableDeclaration instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transferOver18(    SourceVariableRead instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    public SingletonLatticeElement transfer(    UnaryOperation unop,    SingletonLatticeElement value){
      visit(unop);
      return value;
    }
    public SingletonLatticeElement transferOver6(    EnhancedForConditionInstruction instr,    SingletonLatticeElement value){
      visit(instr);
      return value;
    }
    @Override public SingletonLatticeElement transferOver19(    UnaryOperation unop,    SingletonLatticeElement value){
      return null;
    }
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
  public AnalysisDirection getDirection(){
    Cloner cloner=new Cloner();
    direction=cloner.deepClone(direction);
    Cloner cloner=new Cloner();
    direction=cloner.deepClone(direction);
    return direction;
  }
}
