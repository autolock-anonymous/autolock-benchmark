package vectorjava142.entity;
public class Main {
  public static void main(  String[] args){
    String s="s";
    Vector vector=new Vector(1);
    vector.addAll(vector);
    vector.addElement("123");
    vector.addElement("123");
    vector.addElement("123");
    vector.addElement("123");
    vector.addElement("123");
    vector.addElement("123");
    vector.addElement("123");
    vector.addElement("123");
    vector.add("123");
    vector.add(1,"123");
    vector.trimToSize();
    vector.ensureCapacity(2);
    vector.setSize(5);
    vector.capacity();
    vector.size();
    vector.isEmpty();
    vector.contains(s);
    vector.indexOf(s);
    vector.indexOf(s,4);
    vector.lastIndexOf(s);
    vector.lastIndexOf(s,4);
    vector.elementAt(4);
    vector.firstElement();
    vector.lastElement();
    vector.setElementAt(s,1);
    vector.removeElementAt(1);
    vector.insertElementAt(s,1);
    vector.addElement(s);
    vector.removeElement(s);
    vector.removeAllElements();
    vector.equals(vector);
    vector.hashCode();
    vector.toString();
    vector.elements();
    vector.toArray();
  }
}
