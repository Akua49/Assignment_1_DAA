package dsa.algo;

import dsa.metrics.*;
import java.util.*;

public class ClosestPair2D {
    public static class Pt { public final double x, y; public Pt(double x, double y){ this.x=x; this.y=y; } }

    public double solve(List<Pt> pts, Metrics m){
        List<Pt> px = new ArrayList<>(pts);
        px.sort(Comparator.comparingDouble(p -> p.x));
        m.alloc(px.size());
        DepthTracker dt = new DepthTracker(m);
        return rec(px, m, dt);
    }

    private double rec(List<Pt> px, Metrics m, DepthTracker dt){
        m.call(); dt.enter();
        int n = px.size();
        if (n <= 3){
            double best = Double.POSITIVE_INFINITY;
            for (int i=0;i<n;i++) for (int j=i+1;j<n;j++){ m.cmp(); best = Math.min(best, dist(px.get(i), px.get(j))); }
            dt.exit(); return best;
        }
        int mid = n/2; double midx = px.get(mid).x;
        double dl = rec(px.subList(0, mid), m, dt);
        double dr = rec(px.subList(mid, n), m, dt);
        double delta = Math.min(dl, dr);

        List<Pt> strip = new ArrayList<>();
        for (Pt p: px) if (Math.abs(p.x - midx) < delta) strip.add(p);
        strip.sort(Comparator.comparingDouble(p -> p.y));

        double best = delta;
        for (int i=0; i<strip.size(); i++){
            for (int j=i+1; j<strip.size() && (strip.get(j).y - strip.get(i).y) < best && j <= i+7; j++){
                m.cmp();
                best = Math.min(best, dist(strip.get(i), strip.get(j)));
            }
        }
        dt.exit();
        return best;
    }

    private double dist(Pt a, Pt b){ double dx=a.x-b.x, dy=a.y-b.y; return Math.hypot(dx, dy); }
}
