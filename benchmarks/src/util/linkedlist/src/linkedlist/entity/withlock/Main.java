package linkedlist.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Main {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String[] args) {
		LinkedList<String> linkedList = new LinkedList<>();
		LinkedList<String> tmp = new LinkedList<>(linkedList);
		linkedList.getFirst();
		linkedList.getLast();
		linkedList.removeFirst();
		linkedList.removeLast();
		linkedList.addFirst("s");
		linkedList.addLast("s");
		linkedList.contains("s");
		linkedList.size();
		linkedList.add("s");
		linkedList.remove("s");
		linkedList.addAll(tmp);
		linkedList.addAll(1, tmp);
		linkedList.clear();
		linkedList.get(1);
		linkedList.set(0, "s");
		linkedList.set(0, "s");
		linkedList.remove(1);
		linkedList.indexOf("s");
		linkedList.lastIndexOf("s");
		linkedList.peek();
		linkedList.element();
		linkedList.poll();
		linkedList.remove();
		linkedList.offer("s");
		linkedList.offerFirst("s");
		linkedList.offerLast("s");
		linkedList.peekFirst();
		linkedList.peekLast();
		linkedList.pollFirst();
		linkedList.pollLast();
		linkedList.push("s");
		linkedList.pop();
		linkedList.removeFirstOccurrence("s");
		linkedList.removeLastOccurrence("s");
		linkedList.listIterator(1);
		linkedList.descendingIterator();
		linkedList.toArray();
	}
}
