// Given an array nums of positive integers. Your task is to select some subset of nums, multiply each element by an integer and add all these numbers. The array is said to be good if you can obtain a sum of 1 from the array by any possible subset and multiplicand.
// Return True if the array is good otherwise return False.

public class GoodArray {
    /**
     * Determines if an array is "good" using Bézout's Identity theorem.
     * 
     * Approach:
     * According to Bézout's Identity, for any set of integers, we can obtain their GCD
     * as a linear combination (with integer coefficients) of those integers.
     * This means: if gcd(a1, a2, ..., an) = g, then there exist integers x1, x2, ..., xn
     * such that x1*a1 + x2*a2 + ... + xn*an = g
     * 
     * For our problem:
     * - We want to check if we can get sum = 1 using linear combinations
     * - This is possible if and only if gcd(nums) = 1
     * - If gcd > 1, then any linear combination will be divisible by gcd, so we can't get 1
     * - If gcd = 1, then by Bézout's Identity, we can always find coefficients to get 1
     * 
     * @param nums array of positive integers
     * @return true if the array is good (gcd = 1), false otherwise
     */
    public boolean isGoodArray(int[] nums) {
        // Start with the first element as initial GCD
        int gcd = nums[0];
        
        // Compute GCD of all elements in the array
        for (int num : nums) {
            gcd = gcd(gcd, num);
            
            // Early termination: if GCD becomes 1, we know the answer is true
            // No need to process remaining elements
            if (gcd == 1) {
                return true;
            }
        }
        
        // Return true if final GCD is 1, false otherwise
        return gcd == 1;
    }

    /**
     * Calculates the Greatest Common Divisor (GCD) of two integers using Euclidean algorithm.
     * 
     * Algorithm explanation:
     * The Euclidean algorithm is based on the principle that:
     * gcd(a, b) = gcd(b, a mod b)
     * 
     * We repeatedly apply this until b becomes 0, at which point a contains the GCD.
     * 
     * Example: gcd(48, 18)
     * Step 1: gcd(48, 18) = gcd(18, 48 % 18) = gcd(18, 12)
     * Step 2: gcd(18, 12) = gcd(12, 18 % 12) = gcd(12, 6)
     * Step 3: gcd(12, 6) = gcd(6, 12 % 6) = gcd(6, 0)
     * Step 4: Since b = 0, return a = 6
     * 
     * @param a first integer
     * @param b second integer
     * @return GCD of a and b
     */
    private int gcd(int a, int b) {
        // Continue until b becomes 0
        while (b != 0) {
            int temp = b;           // Store b temporarily
            b = a % b;              // New b = remainder of a/b
            a = temp;               // New a = old b
        }
        return a;                   // When b = 0, a contains the GCD
    }
}
