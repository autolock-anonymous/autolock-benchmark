package edu.cmu.cs.crystal.annotations;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import edu.cmu.cs.crystal.AbstractCompilationUnitAnalysis;
import edu.cmu.cs.crystal.internal.CrystalRuntimeException;
public class AnnotationFinder extends AbstractCompilationUnitAnalysis {
  AnnotationDatabase db;
  public AnnotationFinder(  AnnotationDatabase database){
    db=database;
  }
  @Override public void analyzeCompilationUnit(  CompilationUnit d){
    d.accept(new AnnotationVisitor());
  }
  public List<ICrystalAnnotation> getAnnotation(  List<IExtendedModifier> modifiers){
    ICrystalAnnotation crystalAnno;
    List<ICrystalAnnotation> annoList=new ArrayList<ICrystalAnnotation>();
    IAnnotationBinding binding;
    for (    IExtendedModifier mod : modifiers) {
      if (!mod.isAnnotation())       continue;
      binding=((Annotation)mod).resolveAnnotationBinding();
      if (db.isMulti(binding)) {
        annoList.addAll(getMulti((Annotation)mod));
      }
 else {
        crystalAnno=db.createAnnotationOverload5(binding);
        annoList.add(crystalAnno);
      }
    }
    return annoList;
  }
  /** 
 * Get multiple annotations out of an annotation array. This presumes that baseAnno was marked as a multi. If so, it is expected to be either a singleMemberAnno, or a normal anno with an "annos" identifier.
 * @param baseAnno The multi annotation
 * @return The CrystalAnnotations found inside of it
 * @throws CrystalRuntimeException if anything went wrong during parsing. This meansthat the annotation was not actually a multi annotation and should not be marked as such.
 */
  private List<? extends ICrystalAnnotation> getMulti(  Annotation baseAnno){
    CrystalRuntimeException err=new CrystalRuntimeException("Hey! " + "If you have a multi annotation, it better have an array of Annotations!" + " Found " + baseAnno.toString() + " without an array.");
    ArrayInitializer realAnnos=null;
    List<ICrystalAnnotation> crystalAnnos=new ArrayList<ICrystalAnnotation>();
    if (baseAnno.isSingleMemberAnnotation()) {
      SingleMemberAnnotation anno=(SingleMemberAnnotation)baseAnno;
      if (!(anno.getValue() instanceof ArrayInitializer))       throw err;
      realAnnos=(ArrayInitializer)anno.getValue();
    }
 else     if (baseAnno.isNormalAnnotation()) {
      NormalAnnotation anno=(NormalAnnotation)baseAnno;
      for (      MemberValuePair pair : (List<MemberValuePair>)anno.values()) {
        if (pair.getName().getIdentifier().equals("annos") && pair.getValue() instanceof ArrayInitializer) {
          realAnnos=(ArrayInitializer)pair.getValue();
          break;
        }
      }
      if (realAnnos == null)       throw err;
    }
 else     throw err;
    for (    Expression exp : (List<Expression>)realAnnos.expressions()) {
      if (!(exp instanceof Annotation))       throw err;
      IAnnotationBinding binding=((Annotation)exp).resolveAnnotationBinding();
      crystalAnnos.add(db.createAnnotationOverload5(binding));
    }
    return crystalAnnos;
  }
private class AnnotationVisitor extends ASTVisitor {
    @Override public boolean visit(    FieldDeclaration node){
      List<ICrystalAnnotation> annoList=getAnnotation(node.modifiers());
      for (      ICrystalAnnotation anno : annoList)       db.addAnnotationToField(anno,node);
      return super.visit(node);
    }
    @Override public boolean visit(    MethodDeclaration node){
      AnnotationSummary sum;
      String[] paramNames=new String[node.parameters().size()];
      List<ICrystalAnnotation> annoList;
      int ndx=0;
      for (      SingleVariableDeclaration param : (List<SingleVariableDeclaration>)node.parameters()) {
        paramNames[ndx]=param.getName().getIdentifier();
        ndx++;
      }
      sum=new AnnotationSummary(paramNames);
      annoList=getAnnotation(node.modifiers());
      for (      ICrystalAnnotation anno : annoList)       sum.addReturn(anno);
      ndx=0;
      for (      SingleVariableDeclaration param : (List<SingleVariableDeclaration>)node.parameters()) {
        annoList=getAnnotation(param.modifiers());
        for (        ICrystalAnnotation anno : annoList)         sum.addParameter(anno,ndx);
        ndx++;
      }
      db.addAnnotationToMethod(sum,node);
      return super.visit(node);
    }
    @Override public boolean visit(    TypeDeclaration node){
      List<ICrystalAnnotation> annoList=getAnnotation(node.modifiers());
      for (      ICrystalAnnotation anno : annoList)       db.addAnnotationToType(anno,node);
      return super.visit(node);
    }
  }
  public AnnotationDatabase getDb(){
    Cloner cloner=new Cloner();
    db=cloner.deepClone(db);
    Cloner cloner=new Cloner();
    db=cloner.deepClone(db);
    return db;
  }
}
