package dsa.metrics;

public class Metrics {
    public long comparisons, swaps, allocations, calls, maxDepth;
    private long startNs;

    public void start() { startNs = System.nanoTime(); }
    public long stopMs() { return (System.nanoTime() - startNs) / 1_000_000; }

    public void cmp() { comparisons++; }
    public boolean cmpRet(boolean cond) { comparisons++; return cond; }
    public void swap() { swaps++; }
    public void alloc(long n) { allocations += n; }
    public void call() { calls++; }
    public void depth(long d) { if (d > maxDepth) maxDepth = d; }
}
