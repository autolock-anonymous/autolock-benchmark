package bufferedinputstreamjava11.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class StringBufferInputStream extends InputStream {
	public ReentrantReadWriteLock buffer_count_posLock = new ReentrantReadWriteLock();
	protected String buffer;
	protected int pos;
	protected int count;
	@Perm(requires = "unique(buffer) * unique(count) in alive", ensures = "unique(buffer) * unique(count) in alive")
	public StringBufferInputStream(String s) {
		buffer_count_posLock.writeLock().lock();
		this.buffer = s;
		count = s.length();
		buffer_count_posLock.writeLock().unlock();
	}
	@Perm(requires = "share(pos) * immutable(count) * unique(buffer) in alive", ensures = "share(pos) * immutable(count) * unique(buffer) in alive")
	public synchronized int read() {
		try {
			buffer_count_posLock.writeLock().lock();
			return (pos < count) ? (buffer.charAt(pos++) & 0xFF) : -1;
		} finally {
			buffer_count_posLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(pos) * immutable(count) * none(buffer) in alive", ensures = "share(pos) * immutable(count) * none(buffer) in alive")
	public synchronized int read(byte b[], int off, int len) {
		if (b == null) {
			throw new NullPointerException();
		} else if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		}
		try {
			buffer_count_posLock.writeLock().lock();
			if (pos >= count) {
				return -1;
			}
			if (pos + len > count) {
				len = count - pos;
			}
			if (len <= 0) {
				return 0;
			}
			String s = buffer;
			int cnt = len;
			while (--cnt >= 0) {
				b[off++] = (byte) s.charAt(pos++);
			}
		} finally {
			buffer_count_posLock.writeLock().unlock();
		}
		return len;
	}
	@Perm(requires = "immutable(count) * share(pos) in alive", ensures = "immutable(count) * share(pos) in alive")
	public synchronized long skip(long n) {
		if (n < 0) {
			return 0;
		}
		buffer_count_posLock.writeLock().lock();
		if (n > count - pos) {
			n = count - pos;
		}
		pos += n;
		buffer_count_posLock.writeLock().unlock();
		return n;
	}
	@Perm(requires = "immutable(count) * pure(pos) in alive", ensures = "immutable(count) * pure(pos) in alive")
	public synchronized int available() {
		try {
			buffer_count_posLock.readLock().lock();
			return count - pos;
		} finally {
			buffer_count_posLock.readLock().unlock();
		}
	}
	@Perm(requires = "share(pos) in alive", ensures = "share(pos) in alive")
	public synchronized void reset() {
		buffer_count_posLock.writeLock().lock();
		pos = 0;
		buffer_count_posLock.writeLock().unlock();
	}
}
