package vectorjava117.entity.withlock;
import vectorjava117.entity.Vector;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Main {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String[] args) {
		String s = "s";
		Vector vector = new Vector(1);
		vector.addElement("123");
		vector.addElement("123");
		vector.addElement("123");
		vector.addElement("123");
		vector.addElement("123");
		vector.addElement("123");
		vector.addElement("123");
		vector.addElement("123");
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
