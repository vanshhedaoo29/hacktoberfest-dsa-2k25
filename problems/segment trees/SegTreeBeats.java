// SegTreeBeats.java
// Java 17 single-file implementation of Segment Tree Beats
// Supports: rangeChMin(l, r, x), rangeChMax(l, r, x), rangeAdd(l, r, v), rangeSum(l, r)
// Inclusive/exclusive convention: [l, r) - r is exclusive.
// Compile: javac SegTreeBeats.java
// Run (runs randomized tests): java SegTreeBeats

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SegTreeBeats {
    private final int n;              // original size (rounded up to power-of-two not required)
    private final long[] sum;
    private final long[] max1, max2;
    private final int[] cntMax;
    private final long[] min1, min2;
    private final int[] cntMin;
    private final long[] add;
    // tree size allocate 4*n to be safe
    public SegTreeBeats(long[] arr) {
        this.n = arr.length;
        int size = 1;
        while (size < n) size <<= 1;
        size <<= 1; // ensure space for internal nodes; 2*pow2
        sum = new long[size];
        max1 = new long[size];
        max2 = new long[size];
        cntMax = new int[size];
        min1 = new long[size];
        min2 = new long[size];
        cntMin = new int[size];
        add = new long[size];
        build(1, 0, n, arr);
    }

    private void build(int idx, int l, int r, long[] arr) {
        add[idx] = 0;
        if (r - l == 1) {
            if (l < arr.length) {
                long v = arr[l];
                sum[idx] = v;
                max1[idx] = v; max2[idx] = Long.MIN_VALUE; cntMax[idx] = 1;
                min1[idx] = v; min2[idx] = Long.MAX_VALUE; cntMin[idx] = 1;
            } else {
                // padding if needed
                sum[idx] = 0;
                max1[idx] = Long.MIN_VALUE; max2[idx] = Long.MIN_VALUE; cntMax[idx] = 0;
                min1[idx] = Long.MAX_VALUE; min2[idx] = Long.MAX_VALUE; cntMin[idx] = 0;
            }
            return;
        }
        int mid = (l + r) >>> 1;
        int left = idx << 1, right = left | 1;
        build(left, l, mid, arr);
        build(right, mid, r, arr);
        pull(idx);
    }

    // pull info from children to parent
    private void pull(int idx) {
        int left = idx << 1, right = left | 1;
        sum[idx] = sum[left] + sum[right];

        // max1, max2, cntMax
        if (max1[left] > max1[right]) {
            max1[idx] = max1[left];
            cntMax[idx] = cntMax[left];
            max2[idx] = Math.max(max2[left], max1[right]);
        } else if (max1[left] < max1[right]) {
            max1[idx] = max1[right];
            cntMax[idx] = cntMax[right];
            max2[idx] = Math.max(max1[left], max2[right]);
        } else {
            max1[idx] = max1[left];
            cntMax[idx] = cntMax[left] + cntMax[right];
            max2[idx] = Math.max(max2[left], max2[right]);
        }

        // min1, min2, cntMin
        if (min1[left] < min1[right]) {
            min1[idx] = min1[left];
            cntMin[idx] = cntMin[left];
            min2[idx] = Math.min(min2[left], min1[right]);
        } else if (min1[left] > min1[right]) {
            min1[idx] = min1[right];
            cntMin[idx] = cntMin[right];
            min2[idx] = Math.min(min1[left], min2[right]);
        } else {
            min1[idx] = min1[left];
            cntMin[idx] = cntMin[left] + cntMin[right];
            min2[idx] = Math.min(min2[left], min2[right]);
        }
    }

    // apply add to node covering length len
    private void applyAdd(int idx, long v, int len) {
        if (len <= 0) return;
        sum[idx] += v * len;
        max1[idx] = (max1[idx] == Long.MIN_VALUE ? Long.MIN_VALUE : max1[idx] + v);
        if (max2[idx] != Long.MIN_VALUE) max2[idx] += v;
        min1[idx] = (min1[idx] == Long.MAX_VALUE ? Long.MAX_VALUE : min1[idx] + v);
        if (min2[idx] != Long.MAX_VALUE) min2[idx] += v;
        add[idx] += v;
    }

    // apply chmin to node (only valid if node.max1 > x)
    private void applyChMin(int idx, long x) {
        if (max1[idx] <= x) return;
        sum[idx] += (x - max1[idx]) * (long)cntMax[idx];
        max1[idx] = x;
        if (min1[idx] > max1[idx]) min1[idx] = max1[idx]; // safety
    }

    // apply chmax to node (only valid if node.min1 < x)
    private void applyChMax(int idx, long x) {
        if (min1[idx] >= x) return;
        sum[idx] += (x - min1[idx]) * (long)cntMin[idx];
        min1[idx] = x;
        if (max1[idx] < min1[idx]) max1[idx] = min1[idx]; // safety
    }

    // push lazy info to children
    private void push(int idx, int l, int r) {
        if (r - l <= 1) return;
        int left = idx << 1, right = left | 1;
        int mid = (l + r) >>> 1;
        int lenL = mid - l, lenR = r - mid;

        // push add
        if (add[idx] != 0) {
            applyAdd(left, add[idx], lenL);
            applyAdd(right, add[idx], lenR);
            add[idx] = 0;
        }

        // push chmin using parent's max1
        if (max1[left] > max1[idx]) applyChMin(left, max1[idx]);
        if (max1[right] > max1[idx]) applyChMin(right, max1[idx]);

        // push chmax using parent's min1
        if (min1[left] < min1[idx]) applyChMax(left, min1[idx]);
        if (min1[right] < min1[idx]) applyChMax(right, min1[idx]);
    }

    // Public API: ranges are [ql, qr)
    public void rangeAdd(int ql, int qr, long v) { rangeAdd(1, 0, n, ql, qr, v); }
    public void rangeChMin(int ql, int qr, long x) { rangeChMin(1, 0, n, ql, qr, x); }
    public void rangeChMax(int ql, int qr, long x) { rangeChMax(1, 0, n, ql, qr, x); }
    public long rangeSum(int ql, int qr) { return rangeSum(1, 0, n, ql, qr); }

    private void rangeAdd(int idx, int l, int r, int ql, int qr, long v) {
        if (ql >= r || qr <= l) return;
        if (ql <= l && r <= qr) {
            applyAdd(idx, v, r - l);
            return;
        }
        push(idx, l, r);
        int mid = (l + r) >>> 1;
        rangeAdd(idx << 1, l, mid, ql, qr, v);
        rangeAdd(idx << 1 | 1, mid, r, ql, qr, v);
        pull(idx);
    }

    private void rangeChMin(int idx, int l, int r, int ql, int qr, long x) {
        if (ql >= r || qr <= l || max1[idx] <= x) return;
        if (ql <= l && r <= qr && max2[idx] < x) {
            applyChMin(idx, x);
            return;
        }
        push(idx, l, r);
        int mid = (l + r) >>> 1;
        rangeChMin(idx << 1, l, mid, ql, qr, x);
        rangeChMin(idx << 1 | 1, mid, r, ql, qr, x);
        pull(idx);
    }

    private void rangeChMax(int idx, int l, int r, int ql, int qr, long x) {
        if (ql >= r || qr <= l || min1[idx] >= x) return;
        if (ql <= l && r <= qr && min2[idx] > x) {
            applyChMax(idx, x);
            return;
        }
        push(idx, l, r);
        int mid = (l + r) >>> 1;
        rangeChMax(idx << 1, l, mid, ql, qr, x);
        rangeChMax(idx << 1 | 1, mid, r, ql, qr, x);
        pull(idx);
    }

    private long rangeSum(int idx, int l, int r, int ql, int qr) {
        if (ql >= r || qr <= l) return 0L;
        if (ql <= l && r <= qr) return sum[idx];
        push(idx, l, r);
        int mid = (l + r) >>> 1;
        return rangeSum(idx << 1, l, mid, ql, qr) + rangeSum(idx << 1 | 1, mid, r, ql, qr);
    }

    // ----------------- Test harness -----------------
    // Randomized brute-force comparator
    public static void main(String[] args) {
        final int N = 200;        // array size (small for brute-force)
        final int OPS = 2000;
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        for (int trial = 0; trial < 10; trial++) {
            int n = N;
            long[] arr = new long[n];
            for (int i = 0; i < n; i++) arr[i] = rnd.nextLong(-50, 51);

            SegTreeBeats st = new SegTreeBeats(arr.clone());
            long[] brute = arr.clone();

            for (int op = 0; op < OPS; op++) {
                int type = rnd.nextInt(4); // 0 add,1 chmin,2 chmax,3 sum
                int l = rnd.nextInt(n);
                int r = rnd.nextInt(l + 1, n + 1); // ensure r > l
                if (type == 0) {
                    long v = rnd.nextLong(-30, 31);
                    st.rangeAdd(l, r, v);
                    for (int i = l; i < r; i++) brute[i] += v;
                } else if (type == 1) {
                    long x = rnd.nextLong(-20, 71);
                    st.rangeChMin(l, r, x);
                    for (int i = l; i < r; i++) brute[i] = Math.min(brute[i], x);
                } else if (type == 2) {
                    long x = rnd.nextLong(-70, 21);
                    st.rangeChMax(l, r, x);
                    for (int i = l; i < r; i++) brute[i] = Math.max(brute[i], x);
                } else {
                    long s1 = st.rangeSum(l, r);
                    long s2 = 0;
                    for (int i = l; i < r; i++) s2 += brute[i];
                    if (s1 != s2) {
                        System.err.println("Mismatch on sum!");
                        System.err.println("Trial: " + trial + " op: " + op + " query [" + l + "," + r + ")");
                        System.err.println("SegTree sum = " + s1 + " brute = " + s2);
                        System.err.println("Array: " + Arrays.toString(brute));
                        System.exit(1);
                    }
                }
            }
            // final full-array compare via repeated queries
            for (int i = 0; i < n; i++) {
                long a = st.rangeSum(i, i + 1);
                if (a != brute[i]) {
                    System.err.println("Final mismatch at index " + i + ": seg=" + a + " brute=" + brute[i]);
                    System.exit(1);
                }
            }
            System.out.println("Trial " + trial + " passed.");
        }
        System.out.println("All randomized tests passed.");
        // Simple usage example
        long[] sample = {5,1,4,3,2,6,7,8};
        SegTreeBeats example = new SegTreeBeats(sample);
        System.out.println("Initial sum [0,8): " + example.rangeSum(0,8)); // 36
        example.rangeChMin(0,8,4); // cap <= 4
        System.out.println("After chmin(4), sum: " + example.rangeSum(0,8)); // expected 26
        example.rangeChMax(1,4,3); // ensure >=3 on [1,4)
        System.out.println("After chmax(1,4,3), sum: " + example.rangeSum(0,8));
        example.rangeAdd(2,6,5);
        System.out.println("After add(2,6,5), sum: " + example.rangeSum(0,8));
    }
}
