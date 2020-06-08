package bufferedinputstreamjava11.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public abstract class InputStream {
	public static ReentrantReadWriteLock skipBufferLock = new ReentrantReadWriteLock();
	private static final int SKIP_BUFFER_SIZE = 2048;
	private static byte[] skipBuffer;
	public abstract int read() throws IOException;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public int read1(byte b[]) throws IOException {
		return read3(b, 0, b.length);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public int read3(byte b[], int off, int len) throws IOException {
		if (b == null) {
			throw new NullPointerException();
		} else if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return 0;
		}
		int c = read();
		if (c == -1) {
			return -1;
		}
		b[off] = (byte) c;
		int i = 1;
		try {
			for (; i < len; i++) {
				c = read();
				if (c == -1) {
					break;
				}
				if (b != null) {
					b[off + i] = (byte) c;
				}
			}
		} catch (IOException ee) {
		}
		return i;
	}
	@Perm(requires = "unique(skipBuffer) in alive", ensures = "unique(skipBuffer) in alive")
	public long skip(long n) throws IOException {
		long remaining = n;
		int nr;
		skipBufferLock.writeLock().lock();
		if (skipBuffer == null)
			skipBuffer = new byte[SKIP_BUFFER_SIZE];
		byte[] localSkipBuffer = skipBuffer;
		skipBufferLock.writeLock().unlock();
		if (n <= 0) {
			return 0;
		}
		while (remaining > 0) {
			nr = read3(localSkipBuffer, 0, (int) Math.min(SKIP_BUFFER_SIZE, remaining));
			if (nr < 0) {
				break;
			}
			remaining -= nr;
		}
		return n - remaining;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public int available() throws IOException {
		return 0;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void close() throws IOException {
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public synchronized void mark(int readlimit) {
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public synchronized void reset() throws IOException {
		throw new IOException("mark/reset not supported");
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean markSupported() {
		return false;
	}
}
