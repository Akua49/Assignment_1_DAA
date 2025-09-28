package dsa.algo;

import dsa.metrics.*;
import java.util.Random;

public class QuickSort {
    private final Random rnd = new Random();

    public void sort(int[] a, Metrics m){
        DepthTracker dt = new DepthTracker(m);
        qs(a, 0, a.length, m, dt);
    }

    private void qs(int[] a, int l, int r, Metrics m, DepthTracker dt){
        while (r - l > 1){
            m.call();
            int p = l + rnd.nextInt(r - l);
            swap(a, l, p, m);
            int x = a[l];
            int i = l + 1, j = r - 1;
            while (i <= j){
                while (i <= j && m.cmpRet(a[i] < x)) i++;
                while (i <= j && m.cmpRet(a[j] > x)) j--;
                if (i <= j) swap(a, i++, j--, m);
            }
            swap(a, l, j, m);

            int leftN = j - l;
            int rightL = j + 1, rightN = r - rightL;
            if (leftN < rightN){
                long d = dt.enter();
                qs(a, l, j, m, dt);
                dt.exit();
                l = rightL;
            } else {
                long d = dt.enter();
                qs(a, rightL, r, m, dt);
                dt.exit();
                r = j;
            }
        }
    }

    private void swap(int[] a, int i, int j, Metrics m){
        int t = a[i]; a[i] = a[j]; a[j] = t; m.swaps++;
    }
}
