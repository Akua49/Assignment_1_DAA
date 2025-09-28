package dsa.cli;

import dsa.algo.*;
import dsa.metrics.*;
import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<String,String> a = parse(args);
        String algo = a.getOrDefault("--algo", "mergesort");
        int n = I(a.get("--n"), 100000);
        int trials = I(a.get("--trials"), 3);
        long seed = L(a.get("--seed"), 123);
        int cutoff = I(a.get("--cutoff"), 16);
        int k = I(a.get("--k"), n/2);
        File csv = new File(a.getOrDefault("--csv", "out.csv"));

        try (CsvSink sink = new CsvSink(csv)){
            for (int t=1; t<=trials; t++){
                Metrics m = new Metrics();
                Random rnd = new Random(seed + t);
                long ms;
                if ("mergesort".equals(algo)){
                    int[] arr = rand(n, rnd);
                    MergeSort s = new MergeSort(cutoff);
                    m.start(); s.sort(arr, m); ms = m.stopMs();
                    sink.row("mergesort", n, ms, m, seed, t);
                } else if ("quicksort".equals(algo)){
                    int[] arr = rand(n, rnd);
                    QuickSort s = new QuickSort();
                    m.start(); s.sort(arr, m); ms = m.stopMs();
                    sink.row("quicksort", n, ms, m, seed, t);
                } else if ("select".equals(algo)){
                    int[] arr = rand(n, rnd);
                    SelectMoM5 s = new SelectMoM5();
                    m.start(); int val = s.select(arr, Math.max(0, Math.min(k, n-1)), m); ms = m.stopMs();
                    if (val == Integer.MIN_VALUE) System.out.print("");
                    sink.row("select", n, ms, m, seed, t);
                } else if ("closest".equals(algo)){
                    List<ClosestPair2D.Pt> pts = randPts(n, rnd);
                    ClosestPair2D cp = new ClosestPair2D();
                    m.start(); double d = cp.solve(pts, m); ms = m.stopMs();
                    if (d < 0) System.out.print("");
                    sink.row("closest", n, ms, m, seed, t);
                } else {
                    System.err.println("Unknown algo. Use --algo mergesort|quicksort|select|closest");
                    return;
                }
            }
        }
    }

    static Map<String,String> parse(String[] as){
        Map<String,String> m = new HashMap<>();
        for (int i=0; i<as.length; i++) if (as[i].startsWith("--")){
            String k = as[i];
            String v = (i+1<as.length && !as[i+1].startsWith("--")) ? as[++i] : "true";
            m.put(k, v);
        }
        return m;
    }
    static int I(String s, int d){ return s==null?d:Integer.parseInt(s); }
    static long L(String s, long d){ return s==null?d:Long.parseLong(s); }
    static int[] rand(int n, Random r){ int[] a=new int[n]; for(int i=0;i<n;i++) a[i]=r.nextInt(); return a; }
    static List<ClosestPair2D.Pt> randPts(int n, Random r){
        ArrayList<ClosestPair2D.Pt> ps = new ArrayList<>(n);
        for(int i=0;i<n;i++) ps.add(new ClosestPair2D.Pt(r.nextDouble(), r.nextDouble()));
        return ps;
    }
}
