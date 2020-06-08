package jomp.search.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public interface ConnectFourConstants {
	public final int TRANSIZE = 1050011;
	public final int PROBES = 8;
	public final int REPORTPLY = 8;
	public final int UNK = -4;
	public final int LOSE = -2;
	public final int DRAWLOSE = -1;
	public final int DRAW = 0;
	public final int DRAWWIN = 1;
	public final int WIN = 2;
	public final int EMPTY = 0;
	public final int BLACK = 1;
	public final int WHITE = 2;
	public final int EDGE = 3;
	public final String startingMoves[] = {"444333377", "44433337"};
}
