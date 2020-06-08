package sharedpooldatasource.entity.withlock;
import javax.sql.PooledConnection;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
final class PooledConnectionAndInfo {
	private final PooledConnection pooledConnection;
	private final String password;
	private final String username;
	private final UserPassKey upkey;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	PooledConnectionAndInfo(PooledConnection pc, String username, String password) {
		this.pooledConnection = pc;
		this.username = username;
		this.password = password;
		upkey = new UserPassKey(username, password);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	final PooledConnection getPooledConnection() {
		return pooledConnection;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	final UserPassKey getUserPassKey() {
		return upkey;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	final String getPassword() {
		return password;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	final String getUsername() {
		return username;
	}
}
