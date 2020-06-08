package bitset.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Main {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String[] args) {
		BitSet bitSet = new BitSet(10);
		BitSet bitSet1 = new BitSet();
		bitSet.and(bitSet1);
		bitSet.flip(1, 1);
		bitSet.flip(1);
		bitSet.set(1);
		bitSet.set(1, false);
		bitSet.set(1, 1, false);
		bitSet.clear(1);
		bitSet.clear(1, 2);
		bitSet.clear();
		bitSet.get(1);
		bitSet.get(1, 3);
		bitSet.nextSetBit(1);
		bitSet.nextClearBit(1);
		bitSet.length();
		bitSet.isEmpty();
		bitSet.intersects(bitSet1);
		bitSet.cardinality();
		bitSet.and(bitSet1);
		bitSet.or(bitSet1);
		bitSet.xor(bitSet1);
		bitSet.andNot(bitSet1);
		bitSet.size();
	}
}
