package dsa;

import dsa.algo.QuickSort;
import dsa.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {
    @Test void randomArrays(){
        Random r = new Random(2);
        for(int n: new int[]{1,2,3,10,100,1000}){
            int[] a = r.ints(n).toArray();
            int[] b = Arrays.copyOf(a, a.length);
            Arrays.sort(b);
            Metrics m = new Metrics();
            new QuickSort().sort(a, m);
            assertArrayEquals(b, a);
            assertTrue(m.maxDepth <= 2 * (int)(Math.log(Math.max(1,n))/Math.log(2)) + 40);
        }
    }
}
