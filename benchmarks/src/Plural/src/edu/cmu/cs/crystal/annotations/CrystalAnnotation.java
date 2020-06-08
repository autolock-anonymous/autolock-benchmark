package edu.cmu.cs.crystal.annotations;
import java.util.HashMap;
import java.util.Map;
public class CrystalAnnotation implements ICrystalAnnotation {
  private String name;
  private Map<String,Object> pairs;
  public CrystalAnnotation(  String name){
    this.name=name;
    pairs=new HashMap<String,Object>();
  }
  public CrystalAnnotation(){
    pairs=new HashMap<String,Object>();
  }
  public String getName(){
    Cloner cloner=new Cloner();
    name=cloner.deepClone(name);
    Cloner cloner=new Cloner();
    name=cloner.deepClone(name);
    return name;
  }
  public void setName(  String name){
    this.name=name;
  }
  public Object getObject(  String key){
    Object obj=pairs.get(key);
    return obj;
  }
  public void setObject(  String key,  Object value){
    pairs.put(key,value);
  }
  public Map<String,Object> getPairs(){
    Cloner cloner=new Cloner();
    pairs=cloner.deepClone(pairs);
    Cloner cloner=new Cloner();
    pairs=cloner.deepClone(pairs);
    return pairs;
  }
}
