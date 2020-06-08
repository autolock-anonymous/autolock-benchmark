package uma.structure;
import java.util.LinkedList;
public class EState {
  String name;
  int reachable=0;
  LinkedList<EInvariant> invariants;
  LinkedList<EBoolInvariant> boolInvariants;
  private int stateIndex;
  LinkedList<String> reachableStates;
  public EState(  String name,  int flag){
    this.name=name;
    reachable=0;
    invariants=new LinkedList<EInvariant>();
    boolInvariants=new LinkedList<EBoolInvariant>();
    reachableStates=new LinkedList<String>();
  }
  public void addInvariant(  EInvariant inv){
    invariants.add(inv);
  }
  public void addBoolInvariant(  EBoolInvariant inv){
    boolInvariants.add(inv);
  }
  public LinkedList<EBoolInvariant> getBoolInvariants(){
    Cloner cloner=new Cloner();
    boolInvariants=cloner.deepClone(boolInvariants);
    return boolInvariants;
  }
  public String getName(){
    Cloner cloner=new Cloner();
    name=cloner.deepClone(name);
    return this.name;
  }
  public int isReachable(){
    return reachable;
  }
  public void setReachability(  int value){
    reachable=value;
  }
  public LinkedList<EInvariant> getInvariants(){
    Cloner cloner=new Cloner();
    invariants=cloner.deepClone(invariants);
    return invariants;
  }
  public void setIndex(  int stateIndex){
    this.stateIndex=stateIndex;
  }
  public int getStateIndex(){
    return stateIndex;
  }
  public void setReachability(  String toState){
    reachableStates.add(toState);
  }
  public Boolean isReachableState(  String str){
    for (    String state : reachableStates) {
      if (state.compareTo(str) == 0)       return true;
    }
    return false;
  }
  public Boolean isReachableState(){
    if (name.compareTo("alive") == 0)     return true;
    if (reachableStates.size() == 0)     return false;
 else     return true;
  }
  public LinkedList<String> getReachableStates(){
    Cloner cloner=new Cloner();
    reachableStates=cloner.deepClone(reachableStates);
    return reachableStates;
  }
}
