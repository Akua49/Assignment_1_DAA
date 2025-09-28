package dsa.metrics;

public class DepthTracker {
    private long depth = 0;
    private final Metrics m;

    public DepthTracker(Metrics m) { this.m = m; }

    public long enter() {
        long d = ++depth;
        m.depth(d);
        return d;
    }

    public void exit() {
        depth--;
    }
}
