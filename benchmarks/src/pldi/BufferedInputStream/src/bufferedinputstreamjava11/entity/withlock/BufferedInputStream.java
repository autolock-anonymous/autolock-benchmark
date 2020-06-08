package bufferedinputstreamjava11.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class BufferedInputStream extends FilterInputStream {
	public static ReentrantReadWriteLock defaultBufferSizeLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock buf_count_in_marklimit_markpos_posLock = new ReentrantReadWriteLock();
	private static int defaultBufferSize = 2048;
	protected byte buf[];
	protected int count;
	protected int pos;
	protected int markpos = -1;
	protected int marklimit;
	@Perm(requires = "pure(in) in alive", ensures = "pure(in) in alive")
	private void ensureOpen() throws IOException {
		try {
			buf_count_in_marklimit_markpos_posLock.readLock().lock();
			if (in == null)
				throw new IOException("Stream closed");
		} finally {
			buf_count_in_marklimit_markpos_posLock.readLock().unlock();
		}
	}
	@Perm(requires = "none(defaultBufferSize) in alive", ensures = "none(defaultBufferSize) in alive")
	public BufferedInputStream(InputStream in) {
		this(in, defaultBufferSize);
	}
	@Perm(requires = "unique(buf) in alive", ensures = "unique(buf) in alive")
	public BufferedInputStream(InputStream in, int size) {
		super(in);
		if (size <= 0) {
			throw new IllegalArgumentException("Buffer size <= 0");
		}
		buf_count_in_marklimit_markpos_posLock.writeLock().lock();
		buf = new byte[size];
		buf_count_in_marklimit_markpos_posLock.writeLock().unlock();
	}
	@Perm(requires = "share(markpos) * share(pos) * share(buf) * pure(marklimit) * share(count) * pure(in) in alive", ensures = "share(markpos) * share(pos) * share(buf) * pure(marklimit) * share(count) * pure(in) in alive")
	private void fill() throws IOException {
		buf_count_in_marklimit_markpos_posLock.writeLock().lock();
		if (markpos < 0)
			pos = 0;
		else if (pos >= buf.length)
			if (markpos > 0) {
				int sz = pos - markpos;
				System.arraycopy(buf, markpos, buf, 0, sz);
				pos = sz;
				markpos = 0;
			} else if (buf.length >= marklimit) {
				markpos = -1;
				pos = 0;
			} else {
				int nsz = pos * 2;
				if (nsz > marklimit)
					nsz = marklimit;
				byte nbuf[] = new byte[nsz];
				System.arraycopy(buf, 0, nbuf, 0, pos);
				buf = nbuf;
			}
		count = pos;
		int n = in.read3(buf, pos, buf.length - pos);
		if (n > 0)
			count = n + pos;
		buf_count_in_marklimit_markpos_posLock.writeLock().unlock();
	}
	@Perm(requires = "share(pos) * share(count) * share(buf) in alive", ensures = "share(pos) * share(count) * share(buf) in alive")
	public synchronized int read() throws IOException {
		try {
			buf_count_in_marklimit_markpos_posLock.writeLock().lock();
			ensureOpen();
			if (pos >= count) {
				fill();
				if (pos >= count)
					return -1;
			}
			return buf[pos++] & 0xff;
		} finally {
			buf_count_in_marklimit_markpos_posLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(count) * share(pos) * share(buf) * share(markpos) * pure(in) in alive", ensures = "share(count) * share(pos) * share(buf) * share(markpos) * pure(in) in alive")
	private int read4(byte[] b, int off, int len) throws IOException {
		try {
			buf_count_in_marklimit_markpos_posLock.writeLock().lock();
			int avail = count - pos;
			if (avail <= 0) {
				if (len >= buf.length && markpos < 0) {
					return in.read3(b, off, len);
				}
				fill();
				avail = count - pos;
				if (avail <= 0)
					return -1;
			}
			int cnt = (avail < len) ? avail : len;
			System.arraycopy(buf, pos, b, off, cnt);
			pos += cnt;
		} finally {
			buf_count_in_marklimit_markpos_posLock.writeLock().unlock();
		}
		return cnt;
	}
	@Perm(requires = "pure(in) in alive", ensures = "pure(in) in alive")
	public synchronized int read3(byte b[], int off, int len) throws IOException {
		try {
			buf_count_in_marklimit_markpos_posLock.writeLock().lock();
			ensureOpen();
			if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
				throw new IndexOutOfBoundsException();
			} else if (len == 0) {
				return 0;
			}
			int n = read4(b, off, len);
			if (n <= 0)
				return n;
			while ((n < len) && (in.available() > 0)) {
				int n1 = read4(b, off + n, len - n);
				if (n1 <= 0)
					break;
				n += n1;
			}
		} finally {
			buf_count_in_marklimit_markpos_posLock.writeLock().unlock();
		}
		return n;
	}
	@Perm(requires = "share(count) * share(pos) * share(markpos) * pure(in) in alive", ensures = "share(count) * share(pos) * share(markpos) * pure(in) in alive")
	public synchronized long skip(long n) throws IOException {
		try {
			buf_count_in_marklimit_markpos_posLock.writeLock().lock();
			ensureOpen();
			if (n <= 0) {
				return 0;
			}
			long avail = count - pos;
			if (avail <= 0) {
				if (markpos < 0)
					return in.skip(n);
				fill();
				avail = count - pos;
				if (avail <= 0)
					return 0;
			}
			long skipped = (avail < n) ? avail : n;
			pos += skipped;
		} finally {
			buf_count_in_marklimit_markpos_posLock.writeLock().unlock();
		}
		return skipped;
	}
	@Perm(requires = "pure(count) * pure(pos) * pure(in) in alive", ensures = "pure(count) * pure(pos) * pure(in) in alive")
	public synchronized int available() throws IOException {
		try {
			buf_count_in_marklimit_markpos_posLock.readLock().lock();
			ensureOpen();
			return (count - pos) + in.available();
		} finally {
			buf_count_in_marklimit_markpos_posLock.readLock().unlock();
		}
	}
	@Perm(requires = "share(marklimit) * share(markpos) * pure(pos) in alive", ensures = "share(marklimit) * share(markpos) * pure(pos) in alive")
	public synchronized void mark(int readlimit) {
		buf_count_in_marklimit_markpos_posLock.writeLock().lock();
		marklimit = readlimit;
		markpos = pos;
		buf_count_in_marklimit_markpos_posLock.writeLock().unlock();
	}
	@Perm(requires = "pure(markpos) * share(pos) in alive", ensures = "pure(markpos) * share(pos) in alive")
	public synchronized void reset() throws IOException {
		try {
			buf_count_in_marklimit_markpos_posLock.writeLock().lock();
			ensureOpen();
			if (markpos < 0)
				throw new IOException("Resetting to invalid mark");
			pos = markpos;
		} finally {
			buf_count_in_marklimit_markpos_posLock.writeLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean markSupported() {
		return true;
	}
	@Perm(requires = "unique(in) * unique(buf) in alive", ensures = "unique(in) * unique(buf) in alive")
	public void close() throws IOException {
		try {
			buf_count_in_marklimit_markpos_posLock.writeLock().lock();
			if (in == null)
				return;
			in.close();
			in = null;
			buf = null;
		} finally {
			buf_count_in_marklimit_markpos_posLock.writeLock().unlock();
		}
	}
}
