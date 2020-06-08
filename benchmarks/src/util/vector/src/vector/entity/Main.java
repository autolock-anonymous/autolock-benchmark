package vector.entity;

public class Main{

    public static void main(String[] args) {
        String s = "s", s1 = "s1", s2 = "s2";
        Vector<String> vector = new Vector<>();
        Vector<String> vector1 = new Vector<>(1);
        Vector<String> vector2 = new Vector<>(1, 2);
        Vector<String> vector3 = new Vector<>(vector);
        String[] sa = new String[5];
        vector.copyInto(sa);
        vector.trimToSize();
        vector.ensureCapacity(2);
        vector.setSize(5);
        vector.capacity();
        vector.size();
        vector.isEmpty();
        vector.elements();
        vector.contains(s);
        vector.indexOf(s);
        vector.indexOf(s, 4);
        vector.lastIndexOf(s);
        vector.lastIndexOf(s, 4);
        vector.elements();
        vector.elementAt(4);
        vector.firstElement();
        vector.lastElement();
        vector.setElementAt(s, 1);
        vector.removeElementAt(1);
        vector.insertElementAt(s, 1);
        vector.addElement(s);
        vector.removeElement(s);
        vector.removeAllElements();
        vector.clone();
        vector.toArray();
        vector.get(1);
        vector.set(1, s);
        vector.add(s);
        vector.remove(s);
        vector.add(1, s);
        vector.remove(1);
        vector.clear();
        vector.containsAll(vector1);
        vector.addAll(vector1);
        vector.removeAll(vector1);
        vector.retainAll(vector1);
        vector.addAll(1, vector1);
        vector.equals(vector1);
        vector.hashCode();
        vector.toString();
        vector.subList(1, 2);

    }
}