package top.liebes.test;

public class ResInfo {
    private int threads;

    private int loops;

    private long time;

    private long timeWithLock;

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getLoops() {
        return loops;
    }

    public void setLoops(int loops) {
        this.loops = loops;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTimeWithLock() {
        return timeWithLock;
    }

    public void setTimeWithLock(long timeWithLock) {
        this.timeWithLock = timeWithLock;
    }
}
