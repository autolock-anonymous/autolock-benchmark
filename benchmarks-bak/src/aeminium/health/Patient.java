package aeminium.health.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Patient {
	public int id;
	public int time = 0;
	public int time_left = 0;
	public int hosps_visited = 0;
	public int seed;
	public Village home_village;
}
