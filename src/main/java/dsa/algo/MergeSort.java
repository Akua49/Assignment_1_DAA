package dsa.algo;

import dsa.metrics.*;

public class MergeSort {
    private final int cutoff;
    public MergeSort(int cutoff){ this.cutoff = cutoff; }

    public void sort(int[] a, Metrics m){
        int[] tmp = new int[a.length];
        m.alloc(a.length);
        DepthTracker dt = new DepthTracker(m);
        sortRec(a, 0, a.length, tmp, m, dt);
    }

    private void sortRec(int[] a, int l, int r, int[] tmp, Metrics m, DepthTracker dt){
        m.call();
        long d = dt.enter();
        int n = r - l;
        if (n <= 1) { dt.exit(); return; }
        if (n <= cutoff) { insertion(a, l, r, m); dt.exit(); return; }
        int mid = l + n/2;
        sortRec(a, l, mid, tmp, m, dt);
        sortRec(a, mid, r, tmp, m, dt);
        merge(a, l, mid, r, tmp, m);
        dt.exit();
    }

    private void insertion(int[] a, int l, int r, Metrics m){
        for(int i=l+1; i<r; i++){
            int x = a[i];
            int j = i-1;
            while (j>=l && m.cmpRet(a[j] > x)) { a[j+1] = a[j]; m.swaps++; j--; }
            a[j+1] = x; m.swaps++;
        }
    }

    private void merge(int[] a, int l, int m1, int r, int[] tmp, Metrics M){
        int i=l, j=m1, k=0;
        while(i<m1 && j<r){
            M.cmp();
            if(a[i] <= a[j]) tmp[k++] = a[i++]; else tmp[k++] = a[j++];
        }
        while(i<m1) tmp[k++] = a[i++];
        while(j<r)  tmp[k++] = a[j++];
        System.arraycopy(tmp, 0, a, l, k);
        M.swaps += k;
    }
}
