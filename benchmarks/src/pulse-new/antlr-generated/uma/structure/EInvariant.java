package uma.structure;
import java.util.LinkedList;
public class EInvariant {
  String ap;
  String variable;
  String stateName;
  String classType;
  public EInvariant(  String accessPermission,  String var,  String st){
    ap=accessPermission;
    variable=var;
    stateName=st;
  }
  public void setAP(  String str){
    ap=str;
  }
  public void setVariable(  String str){
    variable=str;
  }
  public void setState(  String str){
    stateName=str;
  }
  public String getAP(){
    Cloner cloner=new Cloner();
    ap=cloner.deepClone(ap);
    return ap;
  }
  public String getVariable(){
    Cloner cloner=new Cloner();
    variable=cloner.deepClone(variable);
    return variable;
  }
  public String getStateName(){
    Cloner cloner=new Cloner();
    stateName=cloner.deepClone(stateName);
    return stateName;
  }
  public LinkedList<EInvariant> getStateInvariants(  EPackage _pkg){
    for (    EClass _class : _pkg.getClasses()) {
      if (_class.getName().compareTo(classType) == 0) {
        for (        EState _state : _class.getStates()) {
          if (_state.getName().compareTo(stateName) == 0) {
            return _state.getInvariants();
          }
        }
      }
    }
    return null;
  }
  public void setStateIndex(  int stateIndex){
  }
  public void setVariableType(  String type){
    classType=type;
  }
  public String getVariableType(){
    return classType;
  }
  public String getClassType(){
    Cloner cloner=new Cloner();
    classType=cloner.deepClone(classType);
    return classType;
  }
}
