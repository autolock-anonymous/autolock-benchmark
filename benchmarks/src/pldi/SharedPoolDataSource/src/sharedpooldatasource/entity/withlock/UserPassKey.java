package sharedpooldatasource.entity.withlock;
import java.io.Serializable;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
class UserPassKey implements Serializable {
	public ReentrantReadWriteLock password_usernameLock = new ReentrantReadWriteLock();
	private String password;
	private String username;
	@Perm(requires = "unique(username) * unique(password) in alive", ensures = "unique(username) * unique(password) in alive")
	UserPassKey(String username, String password) {
		password_usernameLock.writeLock().lock();
		this.username = username;
		this.password = password;
		password_usernameLock.writeLock().unlock();
	}
	@Perm(requires = "pure(password) in alive", ensures = "pure(password) in alive")
	public String getPassword() {
		try {
			password_usernameLock.readLock().lock();
			return password;
		} finally {
			password_usernameLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(username) in alive", ensures = "pure(username) in alive")
	public String getUsername() {
		try {
			password_usernameLock.readLock().lock();
			return username;
		} finally {
			password_usernameLock.readLock().unlock();
		}
	}
	@Perm(requires = "share(username) * full(password) in alive", ensures = "share(username) * full(password) in alive")
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof UserPassKey)) {
			return false;
		}
		UserPassKey key = (UserPassKey) obj;
		password_usernameLock.writeLock().lock();
		boolean usersEqual = (this.username == null ? key.username == null : this.username.equals(key.username));
		boolean passwordsEqual = (this.password == null ? key.password == null : this.password.equals(key.password));
		password_usernameLock.writeLock().unlock();
		return (usersEqual && passwordsEqual);
	}
	@Perm(requires = "share(username) in alive", ensures = "share(username) in alive")
	public int hashCode() {
		try {
			password_usernameLock.writeLock().lock();
			return (this.username != null ? this.username.hashCode() : 0);
		} finally {
			password_usernameLock.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(username) * pure(password) in alive", ensures = "pure(username) * pure(password) in alive")
	public String toString() {
		StringBuffer sb = new StringBuffer(50);
		sb.append("UserPassKey(");
		password_usernameLock.readLock().lock();
		sb.append(username).append(", ").append(password).append(')');
		password_usernameLock.readLock().unlock();
		return sb.toString();
	}
}
