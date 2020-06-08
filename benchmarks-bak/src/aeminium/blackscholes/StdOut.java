package aeminium.blackscholes.withlock;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public final class StdOut {
	public static ReentrantReadWriteLock outLock = new ReentrantReadWriteLock();
	private static final String UTF8 = "UTF-8";
	private static final Locale USLOCALE = new Locale("en", "US");
	private static PrintWriter out;
	static {
		try {
			out = new PrintWriter(new OutputStreamWriter(System.out, UTF8), true);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private StdOut() {
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void close() {
		outLock.writeLock().lock();
		out.close();
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void println() {
		outLock.writeLock().lock();
		out.println();
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void println(Object x) {
		outLock.writeLock().lock();
		out.println(x);
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void println(boolean x) {
		outLock.writeLock().lock();
		out.println(x);
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void println(char x) {
		outLock.writeLock().lock();
		out.println(x);
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void println(double x) {
		outLock.writeLock().lock();
		out.println(x);
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void println(float x) {
		outLock.writeLock().lock();
		out.println(x);
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void println(int x) {
		outLock.writeLock().lock();
		out.println(x);
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void println(long x) {
		outLock.writeLock().lock();
		out.println(x);
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void println(short x) {
		outLock.writeLock().lock();
		out.println(x);
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void println(byte x) {
		outLock.writeLock().lock();
		out.println(x);
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void print() {
		outLock.writeLock().lock();
		out.flush();
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void print(Object x) {
		outLock.writeLock().lock();
		out.print(x);
		out.flush();
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void print(boolean x) {
		outLock.writeLock().lock();
		out.print(x);
		out.flush();
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void print(char x) {
		outLock.writeLock().lock();
		out.print(x);
		out.flush();
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void print(double x) {
		outLock.writeLock().lock();
		out.print(x);
		out.flush();
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void print(float x) {
		outLock.writeLock().lock();
		out.print(x);
		out.flush();
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void print(int x) {
		outLock.writeLock().lock();
		out.print(x);
		out.flush();
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void print(long x) {
		outLock.writeLock().lock();
		out.print(x);
		out.flush();
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void print(short x) {
		outLock.writeLock().lock();
		out.print(x);
		out.flush();
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void print(byte x) {
		outLock.writeLock().lock();
		out.print(x);
		out.flush();
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void printf(String format, Object... args) {
		outLock.writeLock().lock();
		out.printf(USLOCALE, format, args);
		out.flush();
		outLock.writeLock().unlock();
	}
	@Perm(requires = "share(out) in alive", ensures = "share(out) in alive")
	public static void printf(Locale locale, String format, Object... args) {
		outLock.writeLock().lock();
		out.printf(locale, format, args);
		out.flush();
		outLock.writeLock().unlock();
	}
}
