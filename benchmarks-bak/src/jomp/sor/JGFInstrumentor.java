package jomp.sor.withlock;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFInstrumentor {
	public ReentrantReadWriteLock data_time_timersLock = new ReentrantReadWriteLock();
	private static Hashtable timers;
	private static Hashtable data;
	static {
		timers = new Hashtable();
		data = new Hashtable();
	}
	@Perm(requires = "share(timers) in alive", ensures = "share(timers) in alive")
	public static synchronized void addTimer(String name) {
		data_time_timersLock.writeLock().lock();
		if (timers.containsKey(name)) {
			System.out.println("JGFInstrumentor.addTimer: warning -  timer " + name + " already exists");
		} else {
			timers.put(name, new JGFTimer(name));
		}
		data_time_timersLock.writeLock().unlock();
	}
	@Perm(requires = "share(timers) in alive", ensures = "share(timers) in alive")
	public static synchronized void addTimer(String name, String opname) {
		data_time_timersLock.writeLock().lock();
		if (timers.containsKey(name)) {
			System.out.println("JGFInstrumentor.addTimer: warning -  timer " + name + " already exists");
		} else {
			timers.put(name, new JGFTimer(name, opname));
		}
		data_time_timersLock.writeLock().unlock();
	}
	@Perm(requires = "share(timers) in alive", ensures = "share(timers) in alive")
	public static synchronized void addTimer(String name, String opname, int size) {
		data_time_timersLock.writeLock().lock();
		if (timers.containsKey(name)) {
			System.out.println("JGFInstrumentor.addTimer: warning -  timer " + name + " already exists");
		} else {
			timers.put(name, new JGFTimer(name, opname, size));
		}
		data_time_timersLock.writeLock().unlock();
	}
	@Perm(requires = "share(timers) in alive", ensures = "share(timers) in alive")
	public static synchronized void startTimer(String name) {
		data_time_timersLock.writeLock().lock();
		if (timers.containsKey(name)) {
			((JGFTimer) timers.get(name)).start();
		} else {
			System.out.println("JGFInstrumentor.startTimer: failed -  timer " + name + " does not exist");
		}
		data_time_timersLock.writeLock().unlock();
	}
	@Perm(requires = "share(timers) in alive", ensures = "share(timers) in alive")
	public static synchronized void stopTimer(String name) {
		data_time_timersLock.writeLock().lock();
		if (timers.containsKey(name)) {
			((JGFTimer) timers.get(name)).stop();
		} else {
			System.out.println("JGFInstrumentor.stopTimer: failed -  timer " + name + " does not exist");
		}
		data_time_timersLock.writeLock().unlock();
	}
	@Perm(requires = "share(timers) in alive", ensures = "share(timers) in alive")
	public static synchronized void addOpsToTimer(String name, double count) {
		data_time_timersLock.writeLock().lock();
		if (timers.containsKey(name)) {
			((JGFTimer) timers.get(name)).addops(count);
		} else {
			System.out.println("JGFInstrumentor.addOpsToTimer: failed -  timer " + name + " does not exist");
		}
		data_time_timersLock.writeLock().unlock();
	}
	@Perm(requires = "share(timers) * pure(time) in alive", ensures = "share(timers) * pure(time) in alive")
	public static synchronized double readTimer(String name) {
		double time;
		data_time_timersLock.writeLock().lock();
		if (timers.containsKey(name)) {
			time = ((JGFTimer) timers.get(name)).time;
		} else {
			System.out.println("JGFInstrumentor.readTimer: failed -  timer " + name + " does not exist");
			time = 0.0;
		}
		data_time_timersLock.writeLock().unlock();
		return time;
	}
	@Perm(requires = "share(timers) in alive", ensures = "share(timers) in alive")
	public static synchronized void resetTimer(String name) {
		data_time_timersLock.writeLock().lock();
		if (timers.containsKey(name)) {
			((JGFTimer) timers.get(name)).reset();
		} else {
			System.out.println("JGFInstrumentor.resetTimer: failed -  timer " + name + " does not exist");
		}
		data_time_timersLock.writeLock().unlock();
	}
	@Perm(requires = "share(timers) in alive", ensures = "share(timers) in alive")
	public static synchronized void printTimer(String name) {
		data_time_timersLock.writeLock().lock();
		if (timers.containsKey(name)) {
			((JGFTimer) timers.get(name)).print();
		} else {
			System.out.println("JGFInstrumentor.printTimer: failed -  timer " + name + " does not exist");
		}
		data_time_timersLock.writeLock().unlock();
	}
	@Perm(requires = "share(timers) in alive", ensures = "share(timers) in alive")
	public static synchronized void printperfTimer(String name) {
		data_time_timersLock.writeLock().lock();
		if (timers.containsKey(name)) {
			((JGFTimer) timers.get(name)).printperf();
		} else {
			System.out.println("JGFInstrumentor.printTimer: failed -  timer " + name + " does not exist");
		}
		data_time_timersLock.writeLock().unlock();
	}
	@Perm(requires = "share(data) in alive", ensures = "share(data) in alive")
	public static synchronized void storeData(String name, Object obj) {
		data_time_timersLock.writeLock().lock();
		data.put(name, obj);
		data_time_timersLock.writeLock().unlock();
	}
	@Perm(requires = "pure(data) in alive", ensures = "pure(data) in alive")
	public static synchronized void retrieveData(String name, Object obj) {
		data_time_timersLock.readLock().lock();
		obj = data.get(name);
		data_time_timersLock.readLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static synchronized void printHeader(int section, int size) {
		String header, base;
		header = "";
		base = "Java Grande Forum Benchmark Suite - Version 2.0 - Section ";
		switch (section) {
			case 1 :
				header = base + "1";
				break;
			case 2 :
				switch (size) {
					case 0 :
						header = base + "2 - Size A";
						break;
					case 1 :
						header = base + "2 - Size B";
						break;
					case 2 :
						header = base + "2 - Size C";
						break;
				}
				break;
			case 3 :
				switch (size) {
					case 0 :
						header = base + "3 - Size A";
						break;
					case 1 :
						header = base + "3 - Size B";
						break;
				}
				break;
		}
		System.out.println(header);
		System.out.println("");
	}
	@Perm(requires = "share(data) * share(timers) in alive", ensures = "share(data) * share(timers) in alive")
	public static void main(String argv[]) {
		Object obj = new Object();
		data_time_timersLock.writeLock().lock();
		JGFInstrumentor.storeData("hi", obj);
		JGFInstrumentor.startTimer("start");
		data_time_timersLock.writeLock().unlock();
	}
}
