package bufferedinputstreamjava11.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class FilterInputStream extends InputStream {
	public ReentrantReadWriteLock inLock = new ReentrantReadWriteLock();
	protected InputStream in;
	@Perm(requires = "unique(in) in alive", ensures = "unique(in) in alive")
	protected FilterInputStream(InputStream in) {
		inLock.writeLock().lock();
		this.in = in;
		inLock.writeLock().unlock();
	}
	@Perm(requires = "pure(in) in alive", ensures = "pure(in) in alive")
	public int read() throws IOException {
		try {
			inLock.readLock().lock();
			return in.read();
		} finally {
			inLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public int read(byte b[]) throws IOException {
		try {
			inLock.readLock().lock();
			return read3(b, 0, b.length);
		} finally {
			inLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(in) in alive", ensures = "pure(in) in alive")
	public int read3(byte b[], int off, int len) throws IOException {
		try {
			inLock.readLock().lock();
			return in.read3(b, off, len);
		} finally {
			inLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(in) in alive", ensures = "pure(in) in alive")
	public long skip(long n) throws IOException {
		try {
			inLock.readLock().lock();
			return in.skip(n);
		} finally {
			inLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(in) in alive", ensures = "pure(in) in alive")
	public int available() throws IOException {
		try {
			inLock.readLock().lock();
			return in.available();
		} finally {
			inLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(in) in alive", ensures = "pure(in) in alive")
	public void close() throws IOException {
		inLock.readLock().lock();
		in.close();
		inLock.readLock().unlock();
	}
	@Perm(requires = "pure(in) in alive", ensures = "pure(in) in alive")
	public synchronized void mark(int readlimit) {
		inLock.readLock().lock();
		in.mark(readlimit);
		inLock.readLock().unlock();
	}
	@Perm(requires = "pure(in) in alive", ensures = "pure(in) in alive")
	public synchronized void reset() throws IOException {
		inLock.readLock().lock();
		in.reset();
		inLock.readLock().unlock();
	}
	@Perm(requires = "pure(in) in alive", ensures = "pure(in) in alive")
	public boolean markSupported() {
		try {
			inLock.readLock().lock();
			return in.markSupported();
		} finally {
			inLock.readLock().unlock();
		}
	}
}
