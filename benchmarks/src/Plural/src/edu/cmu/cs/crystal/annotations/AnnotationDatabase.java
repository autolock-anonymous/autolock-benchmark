package edu.cmu.cs.crystal.annotations;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IInitializer;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IMemberValuePairBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import edu.cmu.cs.crystal.internal.CrystalRuntimeException;
import edu.cmu.cs.crystal.util.Pair;
/** 
 * This class is a database for annotations. It can store annotations of methods we have analyzed or of ones we have not. Either way, it does not store any AST information, so it returns Crystal objects that represent an annotation. Those who want to use this database must register the type of annotations to scan for. A ICrystalAnnotation provides all the information that one needs about the annotations. An AnnotationSummary is particularly useful for finding all information relating to the method, including parameters. And just in case you were wondering...we can not use regular reflection objects here (Class, Annotation, etc.) as the files we are analyzing aren't on the classpath of the system we are running.
 * @author cchristo
 * @author Nels Beckman
 */
public class AnnotationDatabase {
  private static final Logger log=Logger.getLogger(AnnotationDatabase.class.getName());
  private Map<String,Class<? extends ICrystalAnnotation>> qualNames;
  private Map<String,Class<? extends ICrystalAnnotation>> metaQualNames;
  static Object obj;
  private Map<String,AnnotationSummary> methods;
  private Map<String,List<ICrystalAnnotation>> classes;
  private Map<String,List<ICrystalAnnotation>> fields;
  public AnnotationDatabase(){
    qualNames=new HashMap<String,Class<? extends ICrystalAnnotation>>();
    metaQualNames=new HashMap<String,Class<? extends ICrystalAnnotation>>();
    methods=new HashMap<String,AnnotationSummary>();
    classes=new HashMap<String,List<ICrystalAnnotation>>();
    fields=new HashMap<String,List<ICrystalAnnotation>>();
  }
  public void register(  String fullyQualifiedName,  Class<? extends ICrystalAnnotation> crystalAnnotationClass,  boolean isMeta){
    obj=new Object();
    Class<? extends ICrystalAnnotation> annoClass=isMeta ? metaQualNames.get(fullyQualifiedName) : qualNames.get(fullyQualifiedName);
    if (crystalAnnotationClass != null && annoClass != null && !(crystalAnnotationClass.isAssignableFrom(annoClass))) {
      throw new CrystalRuntimeException("Can not register " + fullyQualifiedName + " for "+ crystalAnnotationClass.getCanonicalName()+ ", the class "+ annoClass.getCanonicalName()+ " is already registered.");
    }
    if (isMeta)     metaQualNames.put(fullyQualifiedName,crystalAnnotationClass);
 else     qualNames.put(fullyQualifiedName,crystalAnnotationClass);
  }
  protected ICrystalAnnotation createAnnotationOverload5(  IAnnotationBinding binding){
    String qualName;
    ICrystalAnnotation crystalAnno;
    qualName=binding.getAnnotationType().getQualifiedName();
    crystalAnno=createCrystalAnnotation(binding.getAnnotationType());
    crystalAnno.setName(qualName);
    for (    IMemberValuePairBinding pair : binding.getAllMemberValuePairs()) {
      crystalAnno.setObject(pair.getName(),getAnnotationValue(pair.getValue(),pair.getMethodBinding().getReturnType().isArray()));
    }
    return crystalAnno;
  }
  protected IType getTypeOfAnnotation(  IAnnotation anno,  IType relative_type) throws JavaModelException {
    IJavaProject project=anno.getJavaProject();
    Pair<String,String> name=getQualifiedAnnoType(anno,relative_type);
    IType anno_type=project.findType(name.fst(),name.snd());
    IField[] anno_fields=anno_type.getFields();
    IInitializer[] inits=anno_type.getInitializers();
    IType lm=(IType)anno_type.getPrimaryElement();
    IField[] fields=lm.getFields();
    IMethod[] methods=lm.getMethods();
    IMemberValuePair val=methods[0].getDefaultValue();
    return anno_type;
  }
  protected Pair<String,String> getQualifiedAnnoType(  IAnnotation anno,  IType relative_type) throws JavaModelException {
    String[][] names=relative_type.resolveType(anno.getElementName());
    if (names.length > 1)     throw new RuntimeException("Not yet implemented.");
    String pack=names[0][0];
    String clazz=names[0][1];
    return Pair.create(pack,clazz);
  }
  protected ICrystalAnnotation createAnnotation(  IAnnotation anno,  IType relative_type) throws JavaModelException {
    String qualName;
    ICrystalAnnotation crystalAnno;
    Pair<String,String> qual_name_=getQualifiedAnnoType(anno,relative_type);
    qualName="".equals(qual_name_.fst()) ? qual_name_.snd() : qual_name_.fst() + "." + qual_name_.snd();
    IType anno_type=getTypeOfAnnotation(anno,relative_type);
    crystalAnno=createCrystalAnnotation(anno_type);
    crystalAnno.setName(qualName);
    Set<String> has_non_default_value=new HashSet<String>();
    for (    IMemberValuePair pair : anno.getMemberValuePairs()) {
      boolean val_is_array=pair.getValue() instanceof Object[];
      has_non_default_value.add(pair.getMemberName());
      crystalAnno.setObject(pair.getMemberName(),getAnnotationValue(pair.getValue(),val_is_array));
    }
    for (    IMemberValuePair pair : findAnnotationDefaults(anno_type)) {
      boolean val_is_array=pair.getValue() instanceof Object[];
      if (!has_non_default_value.contains(pair.getMemberName())) {
        crystalAnno.setObject(pair.getMemberName(),getAnnotationValue(pair.getValue(),val_is_array));
      }
    }
    return crystalAnno;
  }
  private List<IMemberValuePair> findAnnotationDefaults(  IType anno_type) throws JavaModelException {
    IMethod[] properties=anno_type.getMethods();
    List<IMemberValuePair> result=new ArrayList<IMemberValuePair>();
    for (    IMethod property : properties) {
      IMemberValuePair default_val_=property.getDefaultValue();
      if (default_val_ != null)       result.add(default_val_);
    }
    return result;
  }
  public boolean isMulti(  IAnnotationBinding annoBinding){
    ITypeBinding binding=annoBinding.getAnnotationType();
    for (    IAnnotationBinding meta : binding.getAnnotations()) {
      if (meta.getAnnotationType().getQualifiedName().equals(MultiAnnotation.class.getName()))       return true;
    }
    return false;
  }
  public boolean isMulti(  IAnnotation anno,  IType relative_type) throws JavaModelException {
    IType anno_type=getTypeOfAnnotation(anno,relative_type);
    for (    IAnnotation meta : anno_type.getAnnotations()) {
      Pair<String,String> qual_name_=getQualifiedAnnoType(meta,anno_type);
      String qual_name="".equals(qual_name_.fst()) ? qual_name_.snd() : qual_name_.fst() + "." + qual_name_.snd();
      if (qual_name.equals(MultiAnnotation.class.getName()))       return true;
    }
    return false;
  }
  private Object getAnnotationValue(  Object rawValue,  boolean forceArray){
    if (rawValue instanceof Object[]) {
      Object[] array=(Object[])rawValue;
      Object[] result=new Object[array.length];
      for (int i=0; i < array.length; i++)       result[i]=getAnnotationValue(array[i],false);
      return result;
    }
    if (rawValue instanceof IAnnotationBinding) {
      rawValue=createAnnotationOverload5((IAnnotationBinding)rawValue);
    }
    if (forceArray) {
      return new Object[]{rawValue};
    }
    return rawValue;
  }
  /** 
 * See   {@link #createCrystalAnnotation(ITypeBinding)}.
 * @throws JavaModelException 
 */
  public ICrystalAnnotation createCrystalAnnotation(  IType typeOfAnnotation) throws JavaModelException {
    Class<? extends ICrystalAnnotation> annoClass=qualNames.get(typeOfAnnotation.getFullyQualifiedName('.'));
    if (annoClass == null) {
      IAnnotation[] metas=typeOfAnnotation.getAnnotations();
      for (int ndx=0; ndx < metas.length && annoClass == null; ndx++) {
        IAnnotation meta=metas[ndx];
        Pair<String,String> meta_name=null;
        String meta_name_=meta_name.fst() + "." + meta_name.snd();
        annoClass=metaQualNames.get(meta_name_);
      }
    }
    if (annoClass == null)     return new CrystalAnnotation();
    try {
      return annoClass.newInstance();
    }
 catch (    InstantiationException e) {
      log.log(Level.WARNING,"Error instantiating custom annotation parser.  Using default representation.",e);
      return new CrystalAnnotation();
    }
catch (    IllegalAccessException e) {
      log.log(Level.WARNING,"Error accessing custom annotation parser.  Using default representation.",e);
      return new CrystalAnnotation();
    }
  }
  public ICrystalAnnotation createCrystalAnnotation(  ITypeBinding typeBinding){
    Class<? extends ICrystalAnnotation> annoClass=qualNames.get(typeBinding.getQualifiedName());
    if (annoClass == null) {
      IAnnotationBinding[] metas=typeBinding.getAnnotations();
      for (int ndx=0; ndx < metas.length && annoClass == null; ndx++) {
        IAnnotationBinding meta=metas[ndx];
        String metaName=meta.getAnnotationType().getQualifiedName();
        annoClass=metaQualNames.get(metaName);
      }
    }
    if (annoClass == null)     return new CrystalAnnotation();
    try {
      return annoClass.newInstance();
    }
 catch (    InstantiationException e) {
      log.log(Level.WARNING,"Error instantiating custom annotation parser.  Using default representation.",e);
      return new CrystalAnnotation();
    }
catch (    IllegalAccessException e) {
      log.log(Level.WARNING,"Error accessing custom annotation parser.  Using default representation.",e);
      return new CrystalAnnotation();
    }
  }
  public void addAnnotationToField(  ICrystalAnnotation anno,  FieldDeclaration field){
    IVariableBinding binding;
    String name;
    List<ICrystalAnnotation> annoList;
    if (field.fragments().isEmpty())     return;
    binding=((VariableDeclarationFragment)field.fragments().get(0)).resolveBinding();
    name=binding.getKey();
    annoList=fields.get(name);
    if (annoList == null) {
      annoList=new ArrayList<ICrystalAnnotation>();
      fields.put(name,annoList);
    }
    annoList.add(anno);
  }
  public void addAnnotationToMethod(  AnnotationSummary anno,  MethodDeclaration method){
    IMethodBinding binding=method.resolveBinding();
    String name=binding.getKey();
    AnnotationSummary existing=methods.get(name);
    if (existing == null)     methods.put(name,anno);
 else     existing.add(anno);
  }
  public void addAnnotationToType(  ICrystalAnnotation anno,  TypeDeclaration type){
    ITypeBinding binding=type.resolveBinding();
    String name=binding.getKey();
    List<ICrystalAnnotation> annoList;
    annoList=classes.get(name);
    if (annoList == null) {
      annoList=new ArrayList<ICrystalAnnotation>();
      classes.put(name,annoList);
    }
    annoList.add(anno);
  }
  public static <A extends ICrystalAnnotation>List<A> filter(  List<ICrystalAnnotation> list,  Class<A> type){
    obj=new Object();
    List<A> result=new LinkedList<A>();
    for (    ICrystalAnnotation anno : list) {
      if (type.isAssignableFrom(anno.getClass()))       result.add((A)anno);
    }
    return result;
  }
  static protected ICrystalAnnotation findAnnotation(  String name,  List<ICrystalAnnotation> list){
  }
  public Map<String,AnnotationSummary> getMethods(){
    Cloner cloner=new Cloner();
    methods=cloner.deepClone(methods);
    Cloner cloner=new Cloner();
    methods=cloner.deepClone(methods);
    return methods;
  }
  public Map<String,Class<? extends ICrystalAnnotation>> getMetaQualNames(){
    Cloner cloner=new Cloner();
    metaQualNames=cloner.deepClone(metaQualNames);
    Cloner cloner=new Cloner();
    metaQualNames=cloner.deepClone(metaQualNames);
    return metaQualNames;
  }
  public Map<String,List<ICrystalAnnotation>> getClasses(){
    Cloner cloner=new Cloner();
    classes=cloner.deepClone(classes);
    Cloner cloner=new Cloner();
    classes=cloner.deepClone(classes);
    return classes;
  }
  public Map<String,Class<? extends ICrystalAnnotation>> getQualNames(){
    Cloner cloner=new Cloner();
    qualNames=cloner.deepClone(qualNames);
    Cloner cloner=new Cloner();
    qualNames=cloner.deepClone(qualNames);
    return qualNames;
  }
  public Map<String,List<ICrystalAnnotation>> getFields(){
    Cloner cloner=new Cloner();
    fields=cloner.deepClone(fields);
    Cloner cloner=new Cloner();
    fields=cloner.deepClone(fields);
    return fields;
  }
  public static Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
  public static Logger getLog(){
    Cloner cloner=new Cloner();
    log=cloner.deepClone(log);
    Cloner cloner=new Cloner();
    log=cloner.deepClone(log);
    return log;
  }
}
