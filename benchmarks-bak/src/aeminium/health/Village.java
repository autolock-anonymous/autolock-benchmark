package aeminium.health.withlock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import edu.cmu.cs.plural.annot.Perm;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Village {
	public ReentrantReadWriteLock home_villageLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock sim_get_sick_pLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock sim_convalescence_timeLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock sim_convalescence_pLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock assess_children_free_personnel_hosp_hosps_visited_id_inside_level_population_realloc_root_seed_sim_assess_time_sim_level_time_time_left_waitingLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock sim_realloc_pLock = new ReentrantReadWriteLock();
	public int id;
	public int level;
	public int seed;
	public List<Village> children = new ArrayList<Village>();
	public Village root;
	public List<Patient> population = new ArrayList<Patient>();
	public Hosp hosp = new Hosp();
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void tick() {
		this.checkPatientsInside();
		this.checkPatientsAssess();
		this.checkPatientsWaiting();
		this.checkPatientsRealloc();
		this.checkPatientsPopulation();
	}
	@Perm(requires = "share(population) * immutable(population) * immutable(seed) * none(sim_get_sick_p) in alive", ensures = "share(population) * immutable(population) * immutable(seed) * none(sim_get_sick_p) in alive")
	public void checkPatientsPopulation() {
		List<Patient> rem = new ArrayList<Patient>();
		for (Patient p : this.population) {
			Random r = new Random(p.seed);
			if (r.nextDouble() < Health.sim_get_sick_p) {
				rem.add(p);
				putInHosp(p);
			}
		}
		this.population.remove(rem);
	}
	@Perm(requires = "share(inside) * share(hosp) * immutable(inside) * share(time_left) * share(free_personnel) * share(population) * immutable(population) in alive", ensures = "share(inside) * share(hosp) * immutable(inside) * share(time_left) * share(free_personnel) * share(population) * immutable(population) in alive")
	public void checkPatientsInside() {
		List<Patient> rem = new ArrayList<Patient>();
		assess_children_free_personnel_hosp_hosps_visited_id_inside_level_population_realloc_root_seed_sim_assess_time_sim_level_time_time_left_waitingLock
				.writeLock().lock();
		for (Patient p : this.hosp.inside) {
			p.time_left--;
			if (p.time_left == 0) {
				this.hosp.free_personnel++;
				rem.add(p);
				this.population.add(p);
			}
		}
		this.hosp.inside.remove(rem);
		assess_children_free_personnel_hosp_hosps_visited_id_inside_level_population_realloc_root_seed_sim_assess_time_sim_level_time_time_left_waitingLock
				.writeLock().unlock();
	}
	@Perm(requires = "share(assess) * share(hosp) * immutable(assess) * share(time_left) * immutable(seed) * none(sim_convalescence_p) * none(sim_realloc_p) * immutable(level) * immutable(sim_level) * share(inside) * immutable(inside) * none(sim_convalescence_time) * pure(time) * share(free_personnel) * immutable(root) * share(population) * immutable(population) in alive", ensures = "share(assess) * share(hosp) * immutable(assess) * share(time_left) * immutable(seed) * none(sim_convalescence_p) * none(sim_realloc_p) * immutable(level) * immutable(sim_level) * share(inside) * immutable(inside) * none(sim_convalescence_time) * pure(time) * share(free_personnel) * immutable(root) * share(population) * immutable(population) in alive")
	public void checkPatientsAssess() {
		List<Patient> rem = new ArrayList<Patient>();
		assess_children_free_personnel_hosp_hosps_visited_id_inside_level_population_realloc_root_seed_sim_assess_time_sim_level_time_time_left_waitingLock
				.writeLock().lock();
		for (Patient p : this.hosp.assess) {
			p.time_left--;
			if (p.time_left == 0) {
				Random random = new Random(p.seed);
				if (random.nextDouble() < Health.sim_convalescence_p) {
					if (random.nextDouble() > Health.sim_realloc_p || this.level == Health.sim_level) {
						rem.add(p);
						this.hosp.inside.add(p);
						p.time_left = Health.sim_convalescence_time;
						p.time += p.time_left;
					} else {
						this.hosp.free_personnel++;
						rem.add(p);
						this.root.hosp.assess.add(p);
					}
				} else {
					this.hosp.free_personnel++;
					rem.add(p);
					this.population.add(p);
				}
			}
		}
		this.hosp.assess.remove(rem);
		assess_children_free_personnel_hosp_hosps_visited_id_inside_level_population_realloc_root_seed_sim_assess_time_sim_level_time_time_left_waitingLock
				.writeLock().unlock();
	}
	@Perm(requires = "share(waiting) * share(hosp) * immutable(waiting) * share(free_personnel) * pure(time_left) * immutable(sim_assess_time) * full(time) * share(assess) * immutable(assess) in alive", ensures = "share(waiting) * share(hosp) * immutable(waiting) * share(free_personnel) * pure(time_left) * immutable(sim_assess_time) * full(time) * share(assess) * immutable(assess) in alive")
	public void checkPatientsWaiting() {
		List<Patient> rem = new ArrayList<Patient>();
		assess_children_free_personnel_hosp_hosps_visited_id_inside_level_population_realloc_root_seed_sim_assess_time_sim_level_time_time_left_waitingLock
				.writeLock().lock();
		for (Patient p : this.hosp.waiting) {
			if (this.hosp.free_personnel > 0) {
				this.hosp.free_personnel--;
				p.time_left = Health.sim_assess_time;
				p.time += p.time_left;
				rem.add(p);
				this.hosp.assess.add(p);
			} else {
				p.time++;
			}
		}
		this.hosp.waiting.remove(rem);
		assess_children_free_personnel_hosp_hosps_visited_id_inside_level_population_realloc_root_seed_sim_assess_time_sim_level_time_time_left_waitingLock
				.writeLock().unlock();
	}
	@Perm(requires = "unique(realloc) * pure(hosp) * none(realloc) * pure(id) in alive", ensures = "unique(realloc) * pure(hosp) * none(realloc) * pure(id) in alive")
	public void checkPatientsRealloc() {
		Patient s = null;
		assess_children_free_personnel_hosp_hosps_visited_id_inside_level_population_realloc_root_seed_sim_assess_time_sim_level_time_time_left_waitingLock
				.readLock().lock();
		for (Patient p : this.hosp.realloc) {
			if (s == null || p.id < s.id)
				s = p;
		}
		if (s != null) {
			this.hosp.realloc.remove(s);
			putInHosp(s);
		}
		assess_children_free_personnel_hosp_hosps_visited_id_inside_level_population_realloc_root_seed_sim_assess_time_sim_level_time_time_left_waitingLock
				.readLock().unlock();
	}
	@Perm(requires = "pure(hosp) * full(hosps_visited) * share(free_personnel) * share(assess) * immutable(assess) * pure(time_left) * immutable(sim_assess_time) * pure(time) * share(waiting) * immutable(waiting) in alive", ensures = "pure(hosp) * full(hosps_visited) * share(free_personnel) * share(assess) * immutable(assess) * pure(time_left) * immutable(sim_assess_time) * pure(time) * share(waiting) * immutable(waiting) in alive")
	public void putInHosp(Patient p) {
		assess_children_free_personnel_hosp_hosps_visited_id_inside_level_population_realloc_root_seed_sim_assess_time_sim_level_time_time_left_waitingLock
				.writeLock().lock();
		Hosp hosp = this.hosp;
		p.hosps_visited++;
		if (hosp.free_personnel > 0) {
			hosp.free_personnel--;
			hosp.assess.add(p);
			p.time_left = Health.sim_assess_time;
			p.time += p.time_left;
		} else {
			hosp.waiting.add(p);
		}
		assess_children_free_personnel_hosp_hosps_visited_id_inside_level_population_realloc_root_seed_sim_assess_time_sim_level_time_time_left_waitingLock
				.writeLock().unlock();
	}
	@Perm(requires = "immutable(root) * immutable(id) * share(children) * immutable(children) * immutable(population) in alive", ensures = "immutable(root) * immutable(id) * share(children) * immutable(children) * immutable(population) in alive")
	public static void displayVillageData(Village v) {
		if (v.root != null)
			System.out.println(", Root Village = " + v.root.id);
		Iterator<Village> it = v.children.iterator();
		if (it.hasNext()) {
			Village.displayVillageData(it.next());
		}
		if (v.population != null) {
			DisplayVillagePatients(v);
		}
	}
	@Perm(requires = "immutable(population) * pure(id) * pure(hosps_visited) * pure(time_left) * pure(time) * immutable(home_village) * immutable(id) in alive", ensures = "immutable(population) * pure(id) * pure(hosps_visited) * pure(time_left) * pure(time) * immutable(home_village) * immutable(id) in alive")
	static void DisplayVillagePatients(Village v) {
		for (Patient p : v.population) {
			assess_children_free_personnel_hosp_hosps_visited_id_inside_level_population_realloc_root_seed_sim_assess_time_sim_level_time_time_left_waitingLock
					.readLock().lock();
			System.out.print("patient_id = " + p.id + ", hosps_visited = " + p.hosps_visited + ", time_left = "
					+ p.time_left + ", time = " + p.time + ", home_village =" + p.home_village.id);
			assess_children_free_personnel_hosp_hosps_visited_id_inside_level_population_realloc_root_seed_sim_assess_time_sim_level_time_time_left_waitingLock
					.readLock().unlock();
			System.out.println();
		}
	}
}
