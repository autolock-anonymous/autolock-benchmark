package edu.cmu.cs.crystal.tac.eclipse;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
/** 
 * @author nbeckman
 * @since 3.3.1
 */
public class CompilationUnitTACs {
  private final Map<IMethodBinding,EclipseTAC> tacs;
  public CompilationUnitTACs(){
    this.tacs=new HashMap<IMethodBinding,EclipseTAC>();
  }
  public synchronized EclipseTAC getMethodTAC(  MethodDeclaration methodDecl){
    EclipseTAC tac;
    IMethodBinding methodBinding=methodDecl.resolveBinding();
    tac=tacs.get(methodBinding);
    if (tac == null) {
      tac=new EclipseTAC(methodBinding);
      tacs.put(methodBinding,tac);
    }
    return tac;
  }
  public Map<IMethodBinding,EclipseTAC> getTacs(){
    Cloner cloner=new Cloner();
    tacs=cloner.deepClone(tacs);
    Cloner cloner=new Cloner();
    tacs=cloner.deepClone(tacs);
    return tacs;
  }
}
