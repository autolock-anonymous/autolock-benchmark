package aeminium.health.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SeqHealth {
	public static ReentrantReadWriteLock sim_timeLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock childrenLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock sim_levelLock = new ReentrantReadWriteLock();
	@Perm(requires = "none(sim_time) * immutable(sim_level) in alive", ensures = "none(sim_time) * immutable(sim_level) in alive")
	public static void main(String[] args) {
		int size = Health.sim_time;
		if (args.length > 0) {
			size = Integer.parseInt(args[0]);
		}
		Village village = null;
		village = Health.allocateVillage(Health.sim_level, 0, null);
		Village.displayVillageData(village);
		for (int i = 0; i < size; i++) {
			simVillage(village);
		}
	}
	@Perm(requires = "immutable(children) in alive", ensures = "immutable(children) in alive")
	public static void simVillage(Village village) {
		for (Village child : village.children) {
			simVillage(child);
		}
		village.tick();
	}
}
