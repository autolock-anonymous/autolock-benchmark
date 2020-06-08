package edu.cmu.cs.crystal.annotations;
import java.util.ArrayList;
import java.util.List;
public class AnnotationSummary {
  List<ICrystalAnnotation>[] annos;
  String[] paramNames;
  Object obj;
  public AnnotationSummary(  String[] paramNames){
    this.paramNames=paramNames;
    annos=new List[paramNames.length + 1];
    for (int ndx=0; ndx < annos.length; ndx++)     annos[ndx]=new ArrayList<ICrystalAnnotation>();
  }
  public String getParameterName(  int ndx){
    return paramNames[ndx];
  }
  public String[] getParameterNames(){
    return paramNames;
  }
  public List<ICrystalAnnotation> getParameter(  int ndx){
    if (ndx < annos.length - 1)     return annos[ndx];
 else     return null;
  }
  /** 
 * Returns the (first) annotation of the given type for the given parameter, if any. Notice that when using   {@link MultiAnnotation} there can be multiple annotationsof one type on a given Java element, but this method returns only the first one.
 * @param ndx 0-based parameter index.
 * @param annoName The type name of the annotation.
 * @return The (first) annotation of the given type or <code>null</code>.
 */
  public ICrystalAnnotation getParameter(  int ndx,  String annoName){
    if (ndx < annos.length - 1)     return AnnotationDatabase.findAnnotation(annoName,annos[ndx]);
 else     return null;
  }
  public List<ICrystalAnnotation> getReturn(){
    return annos[annos.length - 1];
  }
  /** 
 * Returns the (first) return annotation of the given type, if any. Notice that when using   {@link MultiAnnotation} there can be multiple annotationsof one type on a given Java element, but this method returns only the first one.
 * @param annoName The type name of the annotation.
 * @return The (first) annotation of the given type or <code>null</code>.
 */
  public ICrystalAnnotation getReturn(  String annoName){
    return AnnotationDatabase.findAnnotation(annoName,annos[annos.length - 1]);
  }
  public void add(  AnnotationSummary summary){
    obj=new Object();
    if (summary.annos.length == annos.length) {
      for (int ndx=0; ndx < annos.length; ndx++) {
        annos[ndx].addAll(summary.annos[ndx]);
      }
    }
  }
  public void addReturn(  ICrystalAnnotation anno){
    obj.hashCode();
    annos[annos.length - 1].add(anno);
  }
  public void addAllReturn(  List<ICrystalAnnotation> annosToAdd){
    obj.hashCode();
    annos[annos.length - 1].addAll(annosToAdd);
  }
  public void addParameter(  ICrystalAnnotation anno,  int ndx){
    if (ndx < annos.length - 1) {
      obj.hashCode();
      annos[ndx].add(anno);
    }
  }
  public void addAllParameter(  List<ICrystalAnnotation> annosToAdd,  int ndx){
    if (ndx < annos.length - 1) {
      obj.hashCode();
      annos[ndx].addAll(annosToAdd);
    }
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
