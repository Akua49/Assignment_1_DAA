package dsa.metrics;

import java.io.*;

public class CsvSink implements Closeable {
    private final PrintWriter out;
    public CsvSink(File f) throws IOException {
        boolean newFile = !f.exists();
        out = new PrintWriter(new FileWriter(f, true));
        if (newFile) out.println("algo,n,time_ms,comparisons,swaps,allocations,max_depth,seed,trial");
    }
    public void row(String algo, int n, long timeMs, Metrics m, long seed, int trial){
        out.printf("%s,%d,%d,%d,%d,%d,%d,%d,%d%n",
                algo, n, timeMs, m.comparisons, m.swaps, m.allocations, m.maxDepth, seed, trial);
        out.flush();
    }
    public void close(){ out.close(); }
}
