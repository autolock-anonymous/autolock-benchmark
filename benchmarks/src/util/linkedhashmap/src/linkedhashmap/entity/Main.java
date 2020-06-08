package linkedhashmap.entity;
public class Main {
  public static void main(  String[] args){
    LinkedHashMap<String,String> map=new LinkedHashMap<>();
    LinkedHashMap<String,String> map1=new LinkedHashMap<>(5);
    LinkedHashMap<String,String> map2=new LinkedHashMap<>(5,0.75f);
    LinkedHashMap<String,String> map3=new LinkedHashMap<>(map);
    map3.containsValue("s");
    map.get("s");
    map.clear();
  }
}
