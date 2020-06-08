package aeminium.health.withlock;
import java.util.ArrayList;
import java.util.List;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Hosp {
	public int personnel;
	public int free_personnel;
	List<Patient> waiting = new ArrayList<Patient>();
	List<Patient> assess = new ArrayList<Patient>();
	List<Patient> inside = new ArrayList<Patient>();
	List<Patient> realloc = new ArrayList<Patient>();
}
