package identityhashmap.entity;


public class Main{
    public static void main(String[] args) {
        IdentityHashMap<String, String> s = new IdentityHashMap<>();
        IdentityHashMap<String, String> s1 = new IdentityHashMap<>(1);
        IdentityHashMap<String, String> s2 = new IdentityHashMap<>(s1);
        s.size();
        s.isEmpty();
        s.get("s");
        s.containsKey("s");
        s.containsValue("S");
        s.put("s", "s");
        s.putAll(s1);
        s.remove("s");
        s.clear();
        s.keySet();
        s.values();
        s.entrySet();
    }
}