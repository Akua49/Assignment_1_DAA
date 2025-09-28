package dsa;

import dsa.algo.SelectMoM5;
import dsa.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class SelectMoM5Test {
    @Test void kthAgainstSort(){
        Random r = new Random(3);
        for(int n: new int[]{1,5,10,100,333}){
            int[] a = r.ints(n).toArray();
            for(int k=0;k<n;k++){
                int[] b = Arrays.copyOf(a, a.length);
                Arrays.sort(b);
                Metrics m = new Metrics();
                int got = new SelectMoM5().select(Arrays.copyOf(a, a.length), k, m);
                assertEquals(b[k], got);
            }
        }
    }
}
