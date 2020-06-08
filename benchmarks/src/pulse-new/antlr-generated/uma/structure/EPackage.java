package uma.structure;
import java.util.LinkedList;
public class EPackage {
  String pkgName;
  LinkedList<EClass> classes;
  String sinkStates;
  public EPackage(){
    classes=new LinkedList<EClass>();
    sinkStates="";
  }
  public int getTotalReachableStates(){
    int count=0;
    for (    EClass _class : classes)     count=count + _class.getTotalReachableStates();
    return count;
  }
  public void setName(  String str){
    pkgName=str;
  }
  public String getName(){
    return pkgName.toString();
  }
  public LinkedList<EClass> getClasses(){
    Cloner cloner=new Cloner();
    classes=cloner.deepClone(classes);
    return classes;
  }
  public int getTotalStates(){
    int count=0;
    for (    EClass _class : classes)     count=count + _class.getTotalStates();
    return count;
  }
  public void setSinkStates(  String sinkStates){
    this.sinkStates=sinkStates;
  }
  public String getSinkStates(){
    Cloner cloner=new Cloner();
    sinkStates=cloner.deepClone(sinkStates);
    return sinkStates;
  }
  public String getPkgName(){
    Cloner cloner=new Cloner();
    pkgName=cloner.deepClone(pkgName);
    return pkgName;
  }
}
