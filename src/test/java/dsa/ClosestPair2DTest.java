package dsa;

import dsa.algo.ClosestPair2D;
import dsa.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPair2DTest {
    @Test void smallNAgainstBruteforce(){
        Random r = new Random(4);
        for(int n: new int[]{2,3,5,20,200}){
            List<ClosestPair2D.Pt> pts = new ArrayList<>();
            for(int i=0;i<n;i++) pts.add(new ClosestPair2D.Pt(r.nextDouble(), r.nextDouble()));
            Metrics m = new Metrics();
            double fast = new ClosestPair2D().solve(pts, m);
            double slow = bruteForce(pts);
            assertTrue(Math.abs(fast - slow) < 1e-9);
        }
    }
    private static double bruteForce(List<ClosestPair2D.Pt> ps){
        double best = Double.POSITIVE_INFINITY;
        for(int i=0;i<ps.size();i++) for(int j=i+1;j<ps.size();j++){
            double dx=ps.get(i).x-ps.get(j).x, dy=ps.get(i).y-ps.get(j).y;
            best = Math.min(best, Math.hypot(dx,dy));
        }
        return best;
    }
}
