package jomp.search.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Game {
	public ReentrantReadWriteLock columns_dias_height_moves_plycnt_rowsLock = new ReentrantReadWriteLock();
	static int colthr[];
	static boolean colwon[];
	static {
		int i;
		colthr = new int[128];
		for (i = 8; i < 128; i += 8) {
			colthr[i] = 1;
			colthr[i + 7] = 2;
		}
		colwon = new boolean[128];
		for (i = 16; i < 128; i += 16)
			colwon[i] = colwon[i + 15] = true;
	}
	int moves[], plycnt;
	int rows[], dias[];
	int columns[], height[];
	@Perm(requires = "unique(moves) * unique(rows) * unique(dias) * unique(columns) * unique(height) in alive", ensures = "unique(moves) * unique(rows) * unique(dias) * unique(columns) * unique(height) in alive")
	public Game() {
		super();
		columns_dias_height_moves_plycnt_rowsLock.writeLock().lock();
		moves = new int[44];
		rows = new int[8];
		dias = new int[19];
		columns = new int[8];
		height = new int[8];
		reset();
		columns_dias_height_moves_plycnt_rowsLock.writeLock().unlock();
	}
	@Perm(requires = "share(plycnt) * share(dias) * share(columns) * share(height) * share(rows) in alive", ensures = "share(plycnt) * share(dias) * share(columns) * share(height) * share(rows) in alive")
	void reset() {
		columns_dias_height_moves_plycnt_rowsLock.writeLock().lock();
		plycnt = 0;
		for (int i = 0; i < 19; i++)
			dias[i] = 0;
		for (int i = 0; i < 8; i++) {
			columns[i] = 1;
			height[i] = 1;
			rows[i] = 0;
		}
		columns_dias_height_moves_plycnt_rowsLock.writeLock().unlock();
	}
	@Perm(requires = "pure(plycnt) * pure(moves) in alive", ensures = "pure(plycnt) * pure(moves) in alive")
	public String toString() {
		StringBuffer buf = new StringBuffer();
		columns_dias_height_moves_plycnt_rowsLock.readLock().lock();
		for (int i = 1; i <= plycnt; i++)
			buf.append(moves[i]);
		columns_dias_height_moves_plycnt_rowsLock.readLock().unlock();
		return buf.toString();
	}
	@Perm(requires = "pure(rows) * pure(dias) in alive", ensures = "pure(rows) * pure(dias) in alive")
	final boolean wins(int n, int h, int sidemask) {
		int x, y;
		sidemask <<= (2 * n);
		columns_dias_height_moves_plycnt_rowsLock.readLock().lock();
		x = rows[h] | sidemask;
		columns_dias_height_moves_plycnt_rowsLock.readLock().unlock();
		y = x & (x << 2);
		if ((y & (y << 4)) != 0)
			return true;
		try {
			columns_dias_height_moves_plycnt_rowsLock.readLock().lock();
			x = dias[5 + n + h] | sidemask;
			y = x & (x << 2);
			if ((y & (y << 4)) != 0)
				return true;
			x = dias[5 + n - h] | sidemask;
		} finally {
			columns_dias_height_moves_plycnt_rowsLock.readLock().unlock();
		}
		y = x & (x << 2);
		return (y & (y << 4)) != 0;
	}
	@Perm(requires = "share(plycnt) * pure(moves) * share(height) * share(columns) * share(rows) * share(dias) in alive", ensures = "share(plycnt) * pure(moves) * share(height) * share(columns) * share(rows) * share(dias) in alive")
	void backmove() {
		int mask, d, h, n, side;
		columns_dias_height_moves_plycnt_rowsLock.writeLock().lock();
		side = plycnt & 1;
		n = moves[plycnt--];
		h = --height[n];
		columns[n] >>= 1;
		mask = ~(1 << (2 * n + side));
		rows[h] &= mask;
		dias[5 + n + h] &= mask;
		dias[5 + n - h] &= mask;
		columns_dias_height_moves_plycnt_rowsLock.writeLock().unlock();
	}
	@Perm(requires = "share(moves) * share(plycnt) * share(height) * share(columns) * share(rows) * share(dias) in alive", ensures = "share(moves) * share(plycnt) * share(height) * share(columns) * share(rows) * share(dias) in alive")
	void makemove(int n) {
		int mask, d, h, side;
		columns_dias_height_moves_plycnt_rowsLock.writeLock().lock();
		moves[++plycnt] = n;
		side = plycnt & 1;
		h = height[n]++;
		columns[n] = (columns[n] << 1) + side;
		mask = 1 << (2 * n + side);
		rows[h] |= mask;
		dias[5 + n + h] |= mask;
		dias[5 + n - h] |= mask;
		columns_dias_height_moves_plycnt_rowsLock.writeLock().unlock();
	}
}
