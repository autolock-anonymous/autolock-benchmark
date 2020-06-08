package sharedpooldatasource.entity.withlock;
import java.io.Serializable;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
class PoolKey implements Serializable {
	public ReentrantReadWriteLock datasourceName_usernameLock = new ReentrantReadWriteLock();
	private String datasourceName;
	private String username;
	@Perm(requires = "unique(datasourceName) * unique(username) in alive", ensures = "unique(datasourceName) * unique(username) in alive")
	PoolKey(String datasourceName, String username) {
		datasourceName_usernameLock.writeLock().lock();
		this.datasourceName = datasourceName;
		this.username = username;
		datasourceName_usernameLock.writeLock().unlock();
	}
	@Perm(requires = "share(datasourceName) * share(username) in alive", ensures = "share(datasourceName) * share(username) in alive")
	public boolean equals(Object obj) {
		if (obj instanceof PoolKey) {
			PoolKey pk = (PoolKey) obj;
			try {
				datasourceName_usernameLock.writeLock().lock();
				return (null == datasourceName ? null == pk.datasourceName : datasourceName.equals(pk.datasourceName))
						&& (null == username ? null == pk.username : username.equals(pk.username));
			} finally {
				datasourceName_usernameLock.writeLock().unlock();
			}
		} else {
			return false;
		}
	}
	@Perm(requires = "share(datasourceName) * share(username) in alive", ensures = "share(datasourceName) * share(username) in alive")
	public int hashCode() {
		int h = 0;
		datasourceName_usernameLock.writeLock().lock();
		if (datasourceName != null) {
			h += datasourceName.hashCode();
		}
		if (username != null) {
			h = 29 * h + username.hashCode();
		}
		datasourceName_usernameLock.writeLock().unlock();
		return h;
	}
	@Perm(requires = "pure(username) * pure(datasourceName) in alive", ensures = "pure(username) * pure(datasourceName) in alive")
	public String toString() {
		StringBuffer sb = new StringBuffer(50);
		sb.append("PoolKey(");
		datasourceName_usernameLock.readLock().lock();
		sb.append(username).append(", ").append(datasourceName);
		datasourceName_usernameLock.readLock().unlock();
		sb.append(')');
		return sb.toString();
	}
}
