package uma.structure;
public class EBoolInvariant {
  String variable;
  String value;
  public EBoolInvariant(  String variable,  String value){
    this.variable=variable;
    this.value=value;
  }
  public String getVariable(){
    Cloner cloner=new Cloner();
    variable=cloner.deepClone(variable);
    return variable;
  }
  public String getValue(){
    Cloner cloner=new Cloner();
    value=cloner.deepClone(value);
    return value;
  }
}
