package aeminium.health.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Health {
	public static ReentrantReadWriteLock sim_population_ratioLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock IQLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock sim_seedLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock sim_citiesLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock personnelLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock children_current_free_personnel_home_village_hosp_id_level_population_root_seed_sim_idLock = new ReentrantReadWriteLock();
	public static final int DEFAULT_THRESHOLD = 5;
	public static int sim_level = 5;
	public static int sim_cities = 12;
	public static int sim_population_ratio = 10;
	public static int sim_time = 365;
	public static int sim_assess_time = 2;
	public static int sim_convalescence_time = 12;
	public static int sim_seed = 23;
	public static double sim_get_sick_p = 0.002;
	public static double sim_convalescence_p = 0.100;
	public static double sim_realloc_p = 0.150;
	public static long res_population = 7238720;
	public static long res_hospitals = 346201;
	public static long res_personnel = 723872;
	public static long res_checkin = 5267413;
	public static long res_village = 7121156;
	public static long res_waiting = 73923;
	public static long res_assess = 28969;
	public static long res_inside = 14672;
	public static double res_avg_stay = 5.230891;
	public static int sim_id;
	public static int IQ = 127773;
	static Village current;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static Village allocateVillage(int level, int vid, Village back) {
		return Health.allocateVillage(level, vid, back, false, 0);
	}
	@Perm(requires = "none(sim_population_ratio) * unique(current) * immutable(id) * immutable(level) * unique(seed) * none(IQ) * none(sim_seed) * immutable(root) * full(id) * unique(sim_id) * immutable(seed) * immutable(home_village) * share(population) * immutable(population) * pure(hosp) * none(personnel) * pure(free_personnel) * none(sim_cities) * share(children) * immutable(children) in alive", ensures = "none(sim_population_ratio) * unique(current) * immutable(id) * immutable(level) * unique(seed) * none(IQ) * none(sim_seed) * immutable(root) * full(id) * unique(sim_id) * immutable(seed) * immutable(home_village) * share(population) * immutable(population) * pure(hosp) * none(personnel) * pure(free_personnel) * none(sim_cities) * share(children) * immutable(children) in alive")
	public static Village allocateVillage(int level, int vid, Village back, boolean isAe, int threshold) {
		int personnel = (int) Math.pow(2, level);
		int population = personnel * Health.sim_population_ratio;
		try {
			children_current_free_personnel_home_village_hosp_id_level_population_root_seed_sim_idLock.writeLock()
					.lock();
			current = new Village();
			current.id = vid;
			current.level = level;
			current.seed = vid * (IQ + Health.sim_seed);
			current.root = back;
			for (int i = 0; i < population; i++) {
				Patient p = new Patient();
				p.id = Health.sim_id++;
				p.seed = current.seed;
				p.home_village = current;
				current.population.add(p);
			}
			current.hosp.personnel = personnel;
			current.hosp.free_personnel = personnel;
			if (level > 1) {
				for (int i = sim_cities; i > 0; i--) {
					Village curr = Health.allocateVillage(level - 1, i, current, isAe, threshold);
					current.children.add(curr);
				}
			}
			return current;
		} finally {
			children_current_free_personnel_home_village_hosp_id_level_population_root_seed_sim_idLock.writeLock()
					.unlock();
		}
	}
}
