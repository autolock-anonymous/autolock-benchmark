package vectorjava117.entity;

import vectorjava117.entity.Vector;

public class Main{
    public static void main(String[] args) {
        String s = "s";
        Vector vector = new Vector(1);
//        String[] sa = new String[5];
        vector.addElement("123");
        vector.addElement("123");
        vector.addElement("123");
        vector.addElement("123");
        vector.addElement("123");
        vector.addElement("123");
        vector.addElement("123");
        vector.addElement("123");
//        vector.copyInto(sa);
        vector.trimToSize();
        vector.ensureCapacity(2);
        vector.setSize(5);
        vector.capacity();
        vector.size();
        vector.isEmpty();
        vector.contains(s);
        vector.indexOf1(s);
        vector.indexOf(s, 4);
        vector.lastIndexOf(s);
        vector.lastIndexOf(s, 4);
        vector.elementAt(4);
        vector.firstElement();
        vector.lastElement();
        vector.setElementAt(s, 1);
        vector.removeElementAt(1);
        vector.insertElementAt(s, 1);
        vector.addElement(s);
        vector.removeElement(s);
        vector.removeAllElements();
        vector.equals(vector);
        vector.hashCode();
        vector.toString();
    }
}