package edu.cmu.cs.crystal.analysis.metrics;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;
import edu.cmu.cs.crystal.AbstractCrystalMethodAnalysis;
import edu.cmu.cs.crystal.util.Utilities;
public class LoopCountingAnalysis extends AbstractCrystalMethodAnalysis {
  private Map<ASTNode,Integer> loopDepth=Collections.emptyMap();
  private MethodDeclaration decl;
  public int getLoopDepth(  ASTNode node){
    Cloner cloner=new Cloner();
    loopDepth=cloner.deepClone(loopDepth);
    Cloner cloner=new Cloner();
    loopDepth=cloner.deepClone(loopDepth);
    if (!loopDepth.containsKey(node)) {
      final MethodDeclaration d=Utilities.getMethodDeclaration(node);
      if (d == null) {
        return 0;
      }
 else {
        if (decl == null || !decl.equals(d))         reset(d);
      }
    }
    return loopDepth.get(node).intValue();
  }
  public boolean isInLoop(  ASTNode node){
    return getLoopDepth(node) == 0;
  }
  @Override public void beforeAllMethods(  ICompilationUnit compUnit,  CompilationUnit rootNode){
    super.beforeAllMethods(compUnit,rootNode);
  }
  @Override public void analyzeMethod(  MethodDeclaration d){
    if (decl == null || !decl.equals(d))     reset(d);
  }
  private void reset(  MethodDeclaration method){
    if (method == null)     throw new IllegalArgumentException("Need a method declaration for counting loops");
    decl=method;
    loopDepth=new HashMap<ASTNode,Integer>();
    decl.accept(new LoopCounter());
  }
private class LoopCounter extends ASTVisitor {
    private int currentLoopDepth;
    public LoopCounter(){
      currentLoopDepth=0;
    }
    @Override public void preVisit(    ASTNode node){
      loopDepth.put(node,currentLoopDepth);
    }
    @Override public boolean visit(    DoStatement node){
      currentLoopDepth++;
      return super.visit(node);
    }
    @Override public boolean visit(    EnhancedForStatement node){
      currentLoopDepth++;
      return super.visit(node);
    }
    @Override public boolean visit(    ForStatement node){
      currentLoopDepth++;
      return super.visit(node);
    }
    @Override public boolean visit(    WhileStatement node){
      currentLoopDepth++;
      return super.visit(node);
    }
    @Override public void endVisit(    DoStatement node){
      currentLoopDepth--;
      super.endVisit(node);
    }
    @Override public void endVisit(    EnhancedForStatement node){
      currentLoopDepth--;
      super.endVisit(node);
    }
    @Override public void endVisit(    ForStatement node){
      currentLoopDepth--;
      super.endVisit(node);
    }
    @Override public void endVisit(    WhileStatement node){
      currentLoopDepth--;
      super.endVisit(node);
    }
  }
  public MethodDeclaration getDecl(){
    Cloner cloner=new Cloner();
    decl=cloner.deepClone(decl);
    Cloner cloner=new Cloner();
    decl=cloner.deepClone(decl);
    return decl;
  }
}
