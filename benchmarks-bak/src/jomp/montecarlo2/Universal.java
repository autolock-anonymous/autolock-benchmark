package jomp.montecarlo2.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Universal {
	public ReentrantReadWriteLock promptLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock DEBUG_UNIVERSAL_DEBUGLock = new ReentrantReadWriteLock();
	private static boolean UNIVERSAL_DEBUG;
	private boolean DEBUG;
	private String prompt;
	@Perm(requires = "unique(DEBUG) * unique(UNIVERSAL_DEBUG) * none(prompt) in alive", ensures = "unique(DEBUG) * unique(UNIVERSAL_DEBUG) * none(prompt) in alive")
	public Universal() {
		super();
		DEBUG_UNIVERSAL_DEBUGLock.writeLock().lock();
		this.DEBUG = true;
		this.UNIVERSAL_DEBUG = true;
		DEBUG_UNIVERSAL_DEBUGLock.writeLock().unlock();
		this.prompt = "Universal> ";
	}
	@Perm(requires = "pure(DEBUG) in alive", ensures = "pure(DEBUG) in alive")
	public boolean getDEBUG() {
		try {
			DEBUG_UNIVERSAL_DEBUGLock.readLock().lock();
			return (this.DEBUG);
		} finally {
			DEBUG_UNIVERSAL_DEBUGLock.readLock().unlock();
		}
	}
	@Perm(requires = "none(DEBUG) * full(DEBUG) in alive", ensures = "none(DEBUG) * full(DEBUG) in alive")
	public void setDEBUG(boolean DEBUG) {
		DEBUG_UNIVERSAL_DEBUGLock.writeLock().lock();
		this.DEBUG = DEBUG;
		DEBUG_UNIVERSAL_DEBUGLock.writeLock().unlock();
	}
	@Perm(requires = "pure(UNIVERSAL_DEBUG) in alive", ensures = "pure(UNIVERSAL_DEBUG) in alive")
	public boolean getUNIVERSALDEBUG() {
		try {
			DEBUG_UNIVERSAL_DEBUGLock.readLock().lock();
			return (this.UNIVERSAL_DEBUG);
		} finally {
			DEBUG_UNIVERSAL_DEBUGLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(UNIVERSAL_DEBUG) in alive", ensures = "full(UNIVERSAL_DEBUG) in alive")
	public void setUNIVERSALDEBUG(boolean UNIVERSAL_DEBUG) {
		DEBUG_UNIVERSAL_DEBUGLock.writeLock().lock();
		this.UNIVERSAL_DEBUG = UNIVERSAL_DEBUG;
		DEBUG_UNIVERSAL_DEBUGLock.writeLock().unlock();
	}
	@Perm(requires = "pure(prompt) in alive", ensures = "pure(prompt) in alive")
	public String getprompt() {
		try {
			promptLock.readLock().lock();
			return (this.prompt);
		} finally {
			promptLock.readLock().unlock();
		}
	}
	@Perm(requires = "none(prompt) * full(prompt) in alive", ensures = "none(prompt) * full(prompt) in alive")
	public void setprompt(String prompt) {
		promptLock.writeLock().lock();
		this.prompt = prompt;
		promptLock.writeLock().unlock();
	}
	@Perm(requires = "pure(DEBUG) * pure(UNIVERSAL_DEBUG) * pure(prompt) in alive", ensures = "pure(DEBUG) * pure(UNIVERSAL_DEBUG) * pure(prompt) in alive")
	public void dbgPrintln(String s) {
		DEBUG_UNIVERSAL_DEBUGLock.readLock().lock();
		if (DEBUG || UNIVERSAL_DEBUG) {
			promptLock.readLock().lock();
			System.out.println("DBG " + prompt + s);
			promptLock.readLock().unlock();
		}
		DEBUG_UNIVERSAL_DEBUGLock.readLock().unlock();
	}
	@Perm(requires = "pure(DEBUG) * pure(UNIVERSAL_DEBUG) * pure(prompt) in alive", ensures = "pure(DEBUG) * pure(UNIVERSAL_DEBUG) * pure(prompt) in alive")
	public void dbgPrint(String s) {
		DEBUG_UNIVERSAL_DEBUGLock.readLock().lock();
		if (DEBUG || UNIVERSAL_DEBUG) {
			promptLock.readLock().lock();
			System.out.print("DBG " + prompt + s);
			promptLock.readLock().unlock();
		}
		DEBUG_UNIVERSAL_DEBUGLock.readLock().unlock();
	}
	@Perm(requires = "pure(prompt) in alive", ensures = "pure(prompt) in alive")
	public void errPrintln(String s) {
		promptLock.readLock().lock();
		System.err.println(prompt + s);
		promptLock.readLock().unlock();
	}
	@Perm(requires = "pure(prompt) in alive", ensures = "pure(prompt) in alive")
	public void errPrint(String s) {
		promptLock.readLock().lock();
		System.err.print(prompt + s);
		promptLock.readLock().unlock();
	}
}
