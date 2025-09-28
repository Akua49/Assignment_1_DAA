package dsa;

import dsa.algo.MergeSort;
import dsa.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {
    @Test void randomArrays(){
        Random r = new Random(1);
        for(int n: new int[]{0,1,2,5,10,100,1000}){
            int[] a = r.ints(n).toArray();
            int[] b = Arrays.copyOf(a, a.length);
            Arrays.sort(b);
            Metrics m = new Metrics();
            new MergeSort(16).sort(a, m);
            assertArrayEquals(b, a);
        }
    }
}

