package uma.structure;
import java.util.LinkedList;
public class EMethod implements Cloneable {
  String name;
  String identifier;
  String returnType;
  String JMLPermission="Pure";
  int case_Number;
  boolean and;
  boolean requiresSatisfiability;
  LinkedList<ESpecification> requires;
  LinkedList<ESpecification> ensures;
  LinkedList<EParameter> parameters;
  LinkedList<String> concurrentMethods;
  private int methodIndex;
  private String methodSpecifications;
  public EMethod(){
    requires=new LinkedList<ESpecification>();
    ensures=new LinkedList<ESpecification>();
    parameters=new LinkedList<EParameter>();
    concurrentMethods=new LinkedList<String>();
    case_Number=0;
    methodIndex=0;
    requiresSatisfiability=false;
  }
  public void setJMLPermission(  String Permission){
    JMLPermission=Permission;
  }
  public String getJMLPermission(){
    Cloner cloner=new Cloner();
    JMLPermission=cloner.deepClone(JMLPermission);
    return JMLPermission.toString();
  }
  public int getCaseNumber(){
    return case_Number;
  }
  public void setCaseNumber(  int x){
    case_Number=case_Number + x;
  }
  public void setName(  String str){
    name=str;
  }
  public String getName(){
    Cloner cloner=new Cloner();
    name=cloner.deepClone(name);
    return name.toString();
  }
  public void setReturnType(  String str){
    returnType=str;
  }
  public String getReturnType(){
    Cloner cloner=new Cloner();
    returnType=cloner.deepClone(returnType);
    return returnType.toString();
  }
  public void setIdentifier(  String str){
    identifier=str;
  }
  public String getIdentifier(){
    Cloner cloner=new Cloner();
    identifier=cloner.deepClone(identifier);
    return identifier.toString();
  }
  public void addParameter(  EParameter parameter){
    parameters.add(parameter);
  }
  public LinkedList<ESpecification> getRequiresAPTS(){
    return requires;
  }
  public LinkedList<ESpecification> getEnsuresAPTS(){
    return ensures;
  }
  public LinkedList<EParameter> getParameters(){
    Cloner cloner=new Cloner();
    parameters=cloner.deepClone(parameters);
    return parameters;
  }
  public void setIndex(  int methodIndex){
    this.methodIndex=methodIndex;
  }
  public int getIndex(){
    return methodIndex;
  }
  public void setRequiresClauseSatisfiability(  Boolean flag){
    requiresSatisfiability=flag;
  }
  public void setConcurrentMethod(  String toMethod){
    concurrentMethods.add(toMethod);
  }
  public boolean getRequiresClauseSatisfiability(){
    return requiresSatisfiability;
  }
  public boolean isConcurrentMethod(  String str){
    for (    String name : concurrentMethods) {
      if (name.compareTo(str) == 0)       return true;
    }
    return false;
  }
  public Boolean isConcurrentMethod(){
    if (concurrentMethods.size() == 0)     return false;
 else     return true;
  }
  public void addSpecifications(  String annotation){
    methodSpecifications=annotation;
  }
  public LinkedList<ESpecification> getEnsures(){
    Cloner cloner=new Cloner();
    ensures=cloner.deepClone(ensures);
    return ensures;
  }
  public String getMethodSpecifications(){
    Cloner cloner=new Cloner();
    methodSpecifications=cloner.deepClone(methodSpecifications);
    return methodSpecifications;
  }
  public LinkedList<String> getConcurrentMethods(){
    Cloner cloner=new Cloner();
    concurrentMethods=cloner.deepClone(concurrentMethods);
    return concurrentMethods;
  }
  public LinkedList<ESpecification> getRequires(){
    Cloner cloner=new Cloner();
    requires=cloner.deepClone(requires);
    return requires;
  }
}
