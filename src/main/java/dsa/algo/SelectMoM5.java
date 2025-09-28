package dsa.algo;

import dsa.metrics.*;
import java.util.Arrays;

public class SelectMoM5 {
    public int select(int[] a, int k, Metrics m){
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        DepthTracker dt = new DepthTracker(m);
        return sel(a, 0, a.length, k, m, dt);
    }

    private int sel(int[] a, int l, int r, int k, Metrics m, DepthTracker dt){
        m.call(); long d = dt.enter();
        int n = r - l;
        if (n <= 5){
            Arrays.sort(a, l, r);
            dt.exit();
            return a[l + k];
        }
        int groups = (n + 4) / 5;
        int[] med = new int[groups];
        m.alloc(groups);
        for (int i = 0; i < groups; i++){
            int s = l + 5 * i;
            int e = Math.min(s + 5, r);
            Arrays.sort(a, s, e);
            med[i] = a[s + (e - s - 1)/2];
        }
        int pivot = select(med, groups/2, m);
        int lt = l, i = l, gt = r - 1;
        while (i <= gt){
            if (m.cmpRet(a[i] < pivot)) {
                swap(a, lt++, i++, m);
            } else if (m.cmpRet(a[i] > pivot)) {
                swap(a, i, gt--, m);
            } else {
                i++;
            }
        }
        int leftSize = lt - l;
        int midSize  = (gt + 1) - lt;
        if (k < leftSize){
            int res = sel(a, l, lt, k, m, dt); dt.exit(); return res;
        } else if (k < leftSize + midSize){
            dt.exit(); return pivot;
        } else {
            int res = sel(a, gt + 1, r, k - leftSize - midSize, m, dt); dt.exit(); return res;
        }
    }

    private void swap(int[] a, int i, int j, Metrics m){
        int t = a[i]; a[i] = a[j]; a[j] = t; m.swaps++;
    }
}
