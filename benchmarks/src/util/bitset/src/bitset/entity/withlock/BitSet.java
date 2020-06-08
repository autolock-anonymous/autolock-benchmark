package bitset.withlock;
import java.io.*;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class BitSet implements Cloneable, java.io.Serializable {
	public ReentrantReadWriteLock sizeIsSticky_words_wordsInUseLock = new ReentrantReadWriteLock();
	private final static int ADDRESS_BITS_PER_WORD = 6;
	private final static int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;
	private final static int BIT_INDEX_MASK = BITS_PER_WORD - 1;
	private static final long WORD_MASK = 0xffffffffffffffffL;
	private static final ObjectStreamField[] serialPersistentFields = {new ObjectStreamField("bits", long[].class)};
	private long[] words;
	private transient int wordsInUse = 0;
	private transient boolean sizeIsSticky = false;
	private static final long serialVersionUID = 7997698588986878753L;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int wordIndex(int bitIndex) {
		return bitIndex >> ADDRESS_BITS_PER_WORD;
	}
	@Perm(requires = "pure(wordsInUse) * pure(words) in alive", ensures = "pure(wordsInUse) * pure(words) in alive")
	private void checkInvariants() {
		sizeIsSticky_words_wordsInUseLock.readLock().lock();
		assert (wordsInUse == 0 || words[wordsInUse - 1] != 0);
		assert (wordsInUse >= 0 && wordsInUse <= words.length);
		assert (wordsInUse == words.length || words[wordsInUse] == 0);
		sizeIsSticky_words_wordsInUseLock.readLock().unlock();
	}
	@Perm(requires = "share(wordsInUse) * pure(words) in alive", ensures = "share(wordsInUse) * pure(words) in alive")
	private void recalculateWordsInUse() {
		int i;
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		for (i = wordsInUse - 1; i >= 0; i--)
			if (words[i] != 0)
				break;
		wordsInUse = i + 1;
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "unique(sizeIsSticky) in alive", ensures = "unique(sizeIsSticky) in alive")
	public BitSet() {
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		initWords(BITS_PER_WORD);
		sizeIsSticky = false;
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "unique(sizeIsSticky) in alive", ensures = "unique(sizeIsSticky) in alive")
	public BitSet(int nbits) {
		if (nbits < 0)
			throw new NegativeArraySizeException("nbits < 0: " + nbits);
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		initWords(nbits);
		sizeIsSticky = true;
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "unique(words) in alive", ensures = "unique(words) in alive")
	private void initWords(int nbits) {
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		words = new long[wordIndex(nbits - 1) + 1];
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "share(words) * share(sizeIsSticky) in alive", ensures = "share(words) * share(sizeIsSticky) in alive")
	private void ensureCapacity(int wordsRequired) {
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		if (words.length < wordsRequired) {
			int request = Math.max(2 * words.length, wordsRequired);
			words = Arrays.copyOf(words, request);
			sizeIsSticky = false;
		}
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "share(wordsInUse) in alive", ensures = "share(wordsInUse) in alive")
	private void expandTo(int wordIndex) {
		int wordsRequired = wordIndex + 1;
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		if (wordsInUse < wordsRequired) {
			ensureCapacity(wordsRequired);
			wordsInUse = wordsRequired;
		}
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void checkRange(int fromIndex, int toIndex) {
		if (fromIndex < 0)
			throw new IndexOutOfBoundsException("fromIndex < 0: " + fromIndex);
		if (toIndex < 0)
			throw new IndexOutOfBoundsException("toIndex < 0: " + toIndex);
		if (fromIndex > toIndex)
			throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + " > toIndex: " + toIndex);
	}
	@Perm(requires = "share(words) in alive", ensures = "share(words) in alive")
	public void flip(int bitIndex) {
		if (bitIndex < 0)
			throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
		int wordIndex = wordIndex(bitIndex);
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		expandTo(wordIndex);
		words[wordIndex] ^= (1L << bitIndex);
		recalculateWordsInUse();
		checkInvariants();
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "share(words) in alive", ensures = "share(words) in alive")
	public void flip(int fromIndex, int toIndex) {
		checkRange(fromIndex, toIndex);
		if (fromIndex == toIndex)
			return;
		int startWordIndex = wordIndex(fromIndex);
		int endWordIndex = wordIndex(toIndex - 1);
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		expandTo(endWordIndex);
		long firstWordMask = WORD_MASK << fromIndex;
		long lastWordMask = WORD_MASK >>> -toIndex;
		if (startWordIndex == endWordIndex) {
			words[startWordIndex] ^= (firstWordMask & lastWordMask);
		} else {
			words[startWordIndex] ^= firstWordMask;
			for (int i = startWordIndex + 1; i < endWordIndex; i++)
				words[i] ^= WORD_MASK;
			words[endWordIndex] ^= lastWordMask;
		}
		recalculateWordsInUse();
		checkInvariants();
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "share(words) in alive", ensures = "share(words) in alive")
	public void set(int bitIndex) {
		if (bitIndex < 0)
			throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
		int wordIndex = wordIndex(bitIndex);
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		expandTo(wordIndex);
		words[wordIndex] |= (1L << bitIndex);
		checkInvariants();
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void set(int bitIndex, boolean value) {
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		if (value)
			set(bitIndex);
		else
			clear(bitIndex);
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "share(words) in alive", ensures = "share(words) in alive")
	public void set(int fromIndex, int toIndex) {
		checkRange(fromIndex, toIndex);
		if (fromIndex == toIndex)
			return;
		int startWordIndex = wordIndex(fromIndex);
		int endWordIndex = wordIndex(toIndex - 1);
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		expandTo(endWordIndex);
		long firstWordMask = WORD_MASK << fromIndex;
		long lastWordMask = WORD_MASK >>> -toIndex;
		if (startWordIndex == endWordIndex) {
			words[startWordIndex] |= (firstWordMask & lastWordMask);
		} else {
			words[startWordIndex] |= firstWordMask;
			for (int i = startWordIndex + 1; i < endWordIndex; i++)
				words[i] = WORD_MASK;
			words[endWordIndex] |= lastWordMask;
		}
		checkInvariants();
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void set(int fromIndex, int toIndex, boolean value) {
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		if (value)
			set(fromIndex, toIndex);
		else
			clear(fromIndex, toIndex);
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "share(wordsInUse) * share(words) in alive", ensures = "share(wordsInUse) * share(words) in alive")
	public void clear(int bitIndex) {
		if (bitIndex < 0)
			throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
		int wordIndex = wordIndex(bitIndex);
		try {
			sizeIsSticky_words_wordsInUseLock.writeLock().lock();
			if (wordIndex >= wordsInUse)
				return;
			words[wordIndex] &= ~(1L << bitIndex);
			recalculateWordsInUse();
			checkInvariants();
		} finally {
			sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(wordsInUse) * share(words) in alive", ensures = "share(wordsInUse) * share(words) in alive")
	public void clear(int fromIndex, int toIndex) {
		checkRange(fromIndex, toIndex);
		if (fromIndex == toIndex)
			return;
		int startWordIndex = wordIndex(fromIndex);
		try {
			sizeIsSticky_words_wordsInUseLock.writeLock().lock();
			if (startWordIndex >= wordsInUse)
				return;
			int endWordIndex = wordIndex(toIndex - 1);
			if (endWordIndex >= wordsInUse) {
				toIndex = length();
				endWordIndex = wordsInUse - 1;
			}
			long firstWordMask = WORD_MASK << fromIndex;
			long lastWordMask = WORD_MASK >>> -toIndex;
			if (startWordIndex == endWordIndex) {
				words[startWordIndex] &= ~(firstWordMask & lastWordMask);
			} else {
				words[startWordIndex] &= ~firstWordMask;
				for (int i = startWordIndex + 1; i < endWordIndex; i++)
					words[i] = 0;
				words[endWordIndex] &= ~lastWordMask;
			}
			recalculateWordsInUse();
			checkInvariants();
		} finally {
			sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(wordsInUse) * share(words) in alive", ensures = "share(wordsInUse) * share(words) in alive")
	public void clear() {
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		while (wordsInUse > 0)
			words[--wordsInUse] = 0;
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "pure(wordsInUse) * pure(words) in alive", ensures = "pure(wordsInUse) * pure(words) in alive")
	public boolean get(int bitIndex) {
		if (bitIndex < 0)
			throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
		try {
			sizeIsSticky_words_wordsInUseLock.readLock().lock();
			checkInvariants();
			int wordIndex = wordIndex(bitIndex);
			return (wordIndex < wordsInUse) && ((words[wordIndex] & (1L << bitIndex)) != 0);
		} finally {
			sizeIsSticky_words_wordsInUseLock.readLock().unlock();
		}
	}
	@Perm(requires = "share(words) * share(wordsInUse) in alive", ensures = "share(words) * share(wordsInUse) in alive")
	public BitSet get(int fromIndex, int toIndex) {
		checkRange(fromIndex, toIndex);
		try {
			sizeIsSticky_words_wordsInUseLock.writeLock().lock();
			checkInvariants();
			int len = length();
			if (len <= fromIndex || fromIndex == toIndex)
				return new BitSet(0);
			if (toIndex > len)
				toIndex = len;
			BitSet result = new BitSet(toIndex - fromIndex);
			int targetWords = wordIndex(toIndex - fromIndex - 1) + 1;
			int sourceIndex = wordIndex(fromIndex);
			boolean wordAligned = ((fromIndex & BIT_INDEX_MASK) == 0);
			for (int i = 0; i < targetWords - 1; i++, sourceIndex++)
				result.words[i] = wordAligned
						? words[sourceIndex]
						: (words[sourceIndex] >>> fromIndex) | (words[sourceIndex + 1] << -fromIndex);
			long lastWordMask = WORD_MASK >>> -toIndex;
			result.words[targetWords - 1] = ((toIndex - 1) & BIT_INDEX_MASK) < (fromIndex & BIT_INDEX_MASK)
					? ((words[sourceIndex] >>> fromIndex) | (words[sourceIndex + 1] & lastWordMask) << -fromIndex)
					: ((words[sourceIndex] & lastWordMask) >>> fromIndex);
			result.wordsInUse = targetWords;
			result.recalculateWordsInUse();
			result.checkInvariants();
		} finally {
			sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
		}
		return result;
	}
	@Perm(requires = "pure(wordsInUse) * pure(words) in alive", ensures = "pure(wordsInUse) * pure(words) in alive")
	public int nextSetBit(int fromIndex) {
		if (fromIndex < 0)
			throw new IndexOutOfBoundsException("fromIndex < 0: " + fromIndex);
		try {
			sizeIsSticky_words_wordsInUseLock.readLock().lock();
			checkInvariants();
			int u = wordIndex(fromIndex);
			if (u >= wordsInUse)
				return -1;
			long word = words[u] & (WORD_MASK << fromIndex);
			while (true) {
				if (word != 0)
					return (u * BITS_PER_WORD) + Long.numberOfTrailingZeros(word);
				if (++u == wordsInUse)
					return -1;
				word = words[u];
			}
		} finally {
			sizeIsSticky_words_wordsInUseLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(wordsInUse) * share(words) in alive", ensures = "pure(wordsInUse) * share(words) in alive")
	public int nextClearBit(int fromIndex) {
		if (fromIndex < 0)
			throw new IndexOutOfBoundsException("fromIndex < 0: " + fromIndex);
		try {
			sizeIsSticky_words_wordsInUseLock.writeLock().lock();
			checkInvariants();
			int u = wordIndex(fromIndex);
			if (u >= wordsInUse)
				return fromIndex;
			long word = ~words[u] & (WORD_MASK << fromIndex);
			while (true) {
				if (word != 0)
					return (u * BITS_PER_WORD) + Long.numberOfTrailingZeros(word);
				if (++u == wordsInUse)
					return wordsInUse * BITS_PER_WORD;
				word = ~words[u];
			}
		} finally {
			sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(wordsInUse) * pure(words) in alive", ensures = "pure(wordsInUse) * pure(words) in alive")
	public int length() {
		try {
			sizeIsSticky_words_wordsInUseLock.readLock().lock();
			if (wordsInUse == 0)
				return 0;
			return BITS_PER_WORD * (wordsInUse - 1)
					+ (BITS_PER_WORD - Long.numberOfLeadingZeros(words[wordsInUse - 1]));
		} finally {
			sizeIsSticky_words_wordsInUseLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(wordsInUse) in alive", ensures = "pure(wordsInUse) in alive")
	public boolean isEmpty() {
		try {
			sizeIsSticky_words_wordsInUseLock.readLock().lock();
			return wordsInUse == 0;
		} finally {
			sizeIsSticky_words_wordsInUseLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(wordsInUse) * pure(words) in alive", ensures = "pure(wordsInUse) * pure(words) in alive")
	public boolean intersects(BitSet set) {
		try {
			sizeIsSticky_words_wordsInUseLock.readLock().lock();
			for (int i = Math.min(wordsInUse, set.wordsInUse) - 1; i >= 0; i--)
				if ((words[i] & set.words[i]) != 0)
					return true;
		} finally {
			sizeIsSticky_words_wordsInUseLock.readLock().unlock();
		}
		return false;
	}
	@Perm(requires = "pure(wordsInUse) * pure(words) in alive", ensures = "pure(wordsInUse) * pure(words) in alive")
	public int cardinality() {
		int sum = 0;
		sizeIsSticky_words_wordsInUseLock.readLock().lock();
		for (int i = 0; i < wordsInUse; i++)
			sum += Long.bitCount(words[i]);
		sizeIsSticky_words_wordsInUseLock.readLock().unlock();
		return sum;
	}
	@Perm(requires = "share(wordsInUse) * share(words) in alive", ensures = "share(wordsInUse) * share(words) in alive")
	public void and(BitSet set) {
		if (this == set)
			return;
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		while (wordsInUse > set.wordsInUse)
			words[--wordsInUse] = 0;
		for (int i = 0; i < wordsInUse; i++)
			words[i] &= set.words[i];
		recalculateWordsInUse();
		checkInvariants();
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "share(wordsInUse) * share(words) in alive", ensures = "share(wordsInUse) * share(words) in alive")
	public void or(BitSet set) {
		if (this == set)
			return;
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		int wordsInCommon = Math.min(wordsInUse, set.wordsInUse);
		if (wordsInUse < set.wordsInUse) {
			ensureCapacity(set.wordsInUse);
			wordsInUse = set.wordsInUse;
		}
		for (int i = 0; i < wordsInCommon; i++)
			words[i] |= set.words[i];
		if (wordsInCommon < set.wordsInUse)
			System.arraycopy(set.words, wordsInCommon, words, wordsInCommon, wordsInUse - wordsInCommon);
		checkInvariants();
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "share(wordsInUse) * share(words) in alive", ensures = "share(wordsInUse) * share(words) in alive")
	public void xor(BitSet set) {
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		int wordsInCommon = Math.min(wordsInUse, set.wordsInUse);
		if (wordsInUse < set.wordsInUse) {
			ensureCapacity(set.wordsInUse);
			wordsInUse = set.wordsInUse;
		}
		for (int i = 0; i < wordsInCommon; i++)
			words[i] ^= set.words[i];
		if (wordsInCommon < set.wordsInUse)
			System.arraycopy(set.words, wordsInCommon, words, wordsInCommon, set.wordsInUse - wordsInCommon);
		recalculateWordsInUse();
		checkInvariants();
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "share(wordsInUse) * share(words) in alive", ensures = "share(wordsInUse) * share(words) in alive")
	public void andNot(BitSet set) {
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		for (int i = Math.min(wordsInUse, set.wordsInUse) - 1; i >= 0; i--)
			words[i] &= ~set.words[i];
		recalculateWordsInUse();
		checkInvariants();
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "pure(wordsInUse) * pure(words) in alive", ensures = "pure(wordsInUse) * pure(words) in alive")
	public int hashCode() {
		long h = 1234;
		sizeIsSticky_words_wordsInUseLock.readLock().lock();
		for (int i = wordsInUse; --i >= 0;)
			h ^= words[i] * (i + 1);
		sizeIsSticky_words_wordsInUseLock.readLock().unlock();
		return (int) ((h >> 32) ^ h);
	}
	@Perm(requires = "pure(words) in alive", ensures = "pure(words) in alive")
	public int size() {
		try {
			sizeIsSticky_words_wordsInUseLock.readLock().lock();
			return words.length * BITS_PER_WORD;
		} finally {
			sizeIsSticky_words_wordsInUseLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(wordsInUse) * pure(words) in alive", ensures = "pure(wordsInUse) * pure(words) in alive")
	public boolean equals(Object obj) {
		if (!(obj instanceof BitSet))
			return false;
		if (this == obj)
			return true;
		BitSet set = (BitSet) obj;
		try {
			sizeIsSticky_words_wordsInUseLock.readLock().lock();
			checkInvariants();
			set.checkInvariants();
			if (wordsInUse != set.wordsInUse)
				return false;
			for (int i = 0; i < wordsInUse; i++)
				if (words[i] != set.words[i])
					return false;
		} finally {
			sizeIsSticky_words_wordsInUseLock.readLock().unlock();
		}
		return true;
	}
	@Perm(requires = "share(sizeIsSticky) * share(words) in alive", ensures = "share(sizeIsSticky) * share(words) in alive")
	public Object clone() {
		try {
			sizeIsSticky_words_wordsInUseLock.writeLock().lock();
			if (!sizeIsSticky)
				trimToSize();
			try {
				BitSet result = (BitSet) super.clone();
				result.words = words.clone();
				result.checkInvariants();
				return result;
			} catch (CloneNotSupportedException e) {
				throw new InternalError();
			}
		} finally {
			sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(wordsInUse) * share(words) in alive", ensures = "pure(wordsInUse) * share(words) in alive")
	private void trimToSize() {
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		if (wordsInUse != words.length) {
			words = Arrays.copyOf(words, wordsInUse);
			checkInvariants();
		}
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
	}
	@Perm(requires = "share(sizeIsSticky) * share(words) in alive", ensures = "share(sizeIsSticky) * share(words) in alive")
	private void writeObject(ObjectOutputStream s) throws IOException {
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		checkInvariants();
		if (!sizeIsSticky)
			trimToSize();
		ObjectOutputStream.PutField fields = s.putFields();
		fields.put("bits", words);
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
		s.writeFields();
	}
	@Perm(requires = "pure(words) * share(wordsInUse) * share(sizeIsSticky) in alive", ensures = "pure(words) * share(wordsInUse) * share(sizeIsSticky) in alive")
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		ObjectInputStream.GetField fields = s.readFields();
		sizeIsSticky_words_wordsInUseLock.readLock().lock();
		sizeIsSticky_words_wordsInUseLock.writeLock().lock();
		words = (long[]) fields.get("bits", null);
		wordsInUse = words.length;
		recalculateWordsInUse();
		sizeIsSticky = (words.length > 0 && words[words.length - 1] == 0L);
		checkInvariants();
		sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
		sizeIsSticky_words_wordsInUseLock.readLock().unlock();
	}
	@Perm(requires = "pure(wordsInUse) in alive", ensures = "pure(wordsInUse) in alive")
	public String toString() {
		try {
			sizeIsSticky_words_wordsInUseLock.writeLock().lock();
			checkInvariants();
			int numBits = (wordsInUse > 128) ? cardinality() : wordsInUse * BITS_PER_WORD;
			StringBuilder b = new StringBuilder(6 * numBits + 2);
			b.append('{');
			int i = nextSetBit(0);
			if (i != -1) {
				b.append(i);
				for (i = nextSetBit(i + 1); i >= 0; i = nextSetBit(i + 1)) {
					int endOfRun = nextClearBit(i);
					do {
						b.append(", ").append(i);
					} while (++i < endOfRun);
				}
			}
			b.append('}');
			return b.toString();
		} finally {
			sizeIsSticky_words_wordsInUseLock.writeLock().unlock();
		}
	}
}
