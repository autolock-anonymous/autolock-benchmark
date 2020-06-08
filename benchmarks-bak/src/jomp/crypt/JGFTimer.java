package jomp.crypt.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFTimer {
	public ReentrantReadWriteLock calls_name_on_opcount_opname_size_start_time_timeLock = new ReentrantReadWriteLock();
	public String name;
	public String opname;
	public double time;
	public double opcount;
	public long calls;
	public int size = -1;
	private long start_time;
	private boolean on;
	@Perm(requires = "unique(name) * unique(opname) in alive", ensures = "unique(name) * unique(opname) in alive")
	public JGFTimer(String name, String opname) {
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().lock();
		this.name = name;
		this.opname = opname;
		reset();
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().unlock();
	}
	@Perm(requires = "unique(name) * unique(opname) * unique(size) in alive", ensures = "unique(name) * unique(opname) * unique(size) in alive")
	public JGFTimer(String name, String opname, int size) {
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().lock();
		this.name = name;
		this.opname = opname;
		this.size = size;
		reset();
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public JGFTimer(String name) {
		this(name, "");
	}
	@Perm(requires = "share(on) * immutable(name) * full(start_time) in alive", ensures = "share(on) * immutable(name) * full(start_time) in alive")
	public void start() {
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().lock();
		if (on)
			System.out.println("Warning timer " + name + " was already turned on");
		on = true;
		start_time = System.currentTimeMillis();
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().unlock();
	}
	@Perm(requires = "share(time) * pure(start_time) * share(on) * immutable(name) * share(calls) in alive", ensures = "share(time) * pure(start_time) * share(on) * immutable(name) * share(calls) in alive")
	public void stop() {
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().lock();
		time += (double) (System.currentTimeMillis() - start_time) / 1000.;
		if (!on)
			System.out.println("Warning timer " + name + " wasn't turned on");
		calls++;
		on = false;
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().unlock();
	}
	@Perm(requires = "share(opcount) in alive", ensures = "share(opcount) in alive")
	public void addops(double count) {
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().lock();
		opcount += count;
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().unlock();
	}
	@Perm(requires = "share(time) * share(calls) * share(opcount) * share(on) in alive", ensures = "share(time) * share(calls) * share(opcount) * share(on) in alive")
	public void reset() {
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().lock();
		time = 0.0;
		calls = 0;
		opcount = 0;
		on = false;
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().unlock();
	}
	@Perm(requires = "pure(opcount) * pure(time) in alive", ensures = "pure(opcount) * pure(time) in alive")
	public double perf() {
		try {
			calls_name_on_opcount_opname_size_start_time_timeLock.readLock().lock();
			return opcount / time;
		} finally {
			calls_name_on_opcount_opname_size_start_time_timeLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(opname) * immutable(name) * pure(calls) * pure(time) in alive", ensures = "pure(opname) * immutable(name) * pure(calls) * pure(time) in alive")
	public void longprint() {
		calls_name_on_opcount_opname_size_start_time_timeLock.readLock().lock();
		System.out.println("Timer            Calls         Time(s)       Performance(" + opname + "/s)");
		System.out.println(name + "           " + calls + "           " + time + "        " + this.perf());
		calls_name_on_opcount_opname_size_start_time_timeLock.readLock().unlock();
	}
	@Perm(requires = "full(opname) * immutable(name) * pure(time) * none(size) in alive", ensures = "full(opname) * immutable(name) * pure(time) * none(size) in alive")
	public void print() {
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().lock();
		if (opname.equals("")) {
			System.out.println(name + "   " + time + " (s)");
		} else {
			switch (size) {
				case 0 :
					System.out.println(name + ":SizeA" + "\t" + time + " (s) \t " + (float) this.perf() + "\t" + " ("
							+ opname + "/s)");
					break;
				case 1 :
					System.out.println(name + ":SizeB" + "\t" + time + " (s) \t " + (float) this.perf() + "\t" + " ("
							+ opname + "/s)");
					break;
				case 2 :
					System.out.println(name + ":SizeC" + "\t" + time + " (s) \t " + (float) this.perf() + "\t" + " ("
							+ opname + "/s)");
					break;
				default :
					System.out.println(
							name + "\t" + time + " (s) \t " + (float) this.perf() + "\t" + " (" + opname + "/s)");
					break;
			}
		}
		calls_name_on_opcount_opname_size_start_time_timeLock.writeLock().unlock();
	}
	@Perm(requires = "immutable(name) * pure(opname) in alive", ensures = "immutable(name) * pure(opname) in alive")
	public void printperf() {
		String name;
		name = this.name;
		while (name.length() < 40)
			name = name + " ";
		calls_name_on_opcount_opname_size_start_time_timeLock.readLock().lock();
		System.out.println(name + "\t" + (float) this.perf() + "\t" + " (" + opname + "/s)");
		calls_name_on_opcount_opname_size_start_time_timeLock.readLock().unlock();
	}
}
