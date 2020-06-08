package uma.SMC;
public class specificationStruct {
class Clause {
    String accessPermission="NULL";
    String typeState="NULL";
    public String getAccessPermission(){
      Cloner cloner=new Cloner();
      accessPermission=cloner.deepClone(accessPermission);
      return accessPermission;
    }
    public String getTypeState(){
      Cloner cloner=new Cloner();
      typeState=cloner.deepClone(typeState);
      return typeState;
    }
  }
class Signature {
    public String methodName="Method_Name";
    public String className="Class_Name";
    public String getClassName(){
      Cloner cloner=new Cloner();
      className=cloner.deepClone(className);
      return className;
    }
    public String getMethodName(){
      Cloner cloner=new Cloner();
      methodName=cloner.deepClone(methodName);
      return methodName;
    }
  }
  public Clause requires, ensures;
  public Signature signature;
  specificationStruct(){
    requires=new Clause();
    ensures=new Clause();
    signature=new Signature();
  }
  public Clause getRequires(){
    Cloner cloner=new Cloner();
    requires=cloner.deepClone(requires);
    return requires;
  }
  public Signature getSignature(){
    Cloner cloner=new Cloner();
    signature=cloner.deepClone(signature);
    return signature;
  }
}
