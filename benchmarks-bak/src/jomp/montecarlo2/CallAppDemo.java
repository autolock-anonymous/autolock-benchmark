package jomp.montecarlo2.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class CallAppDemo {
	public ReentrantReadWriteLock ap_datasizes_input_sizeLock = new ReentrantReadWriteLock();
	public int size;
	int datasizes[] = {10000, 60000};
	int input[] = new int[2];
	AppDemo ap = null;
	@Perm(requires = "pure(ap) in alive", ensures = "pure(ap) in alive")
	public void runiters() {
		ap_datasizes_input_sizeLock.readLock().lock();
		ap.runSerial();
		ap_datasizes_input_sizeLock.readLock().unlock();
	}
	@Perm(requires = "share(input) * pure(datasizes) * pure(size) * unique(ap) in alive", ensures = "share(input) * pure(datasizes) * pure(size) * unique(ap) in alive")
	public void initialise() {
		ap_datasizes_input_sizeLock.writeLock().lock();
		input[0] = 1000;
		input[1] = datasizes[size];
		String dirName = "Data";
		String filename = "hitData";
		ap = new AppDemo(dirName, filename, (input[0]), (input[1]));
		ap.initSerial();
		ap_datasizes_input_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "pure(ap) in alive", ensures = "pure(ap) in alive")
	public void presults() {
		ap_datasizes_input_sizeLock.readLock().lock();
		ap.processSerial();
		ap_datasizes_input_sizeLock.readLock().unlock();
	}
}
