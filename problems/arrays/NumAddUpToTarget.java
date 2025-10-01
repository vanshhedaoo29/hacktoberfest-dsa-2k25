// Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to the target.

// Example 1: INPUT: nums = [2,7,11,15] , target = 9 OUTPUT: [0,1]

// Example 2: INPUT: nums=[3,2,4] , target = 6 OUTPUT: [1,2]

// Example 3: INPUT: nums=[3,3] target = 6 OUTPUT: [0,1]

// Solution
package problems.arrays;

import java.util.*;

public class NumAddUpToTarget {
    public static ArrayList<Integer> findIdx(int nums[], int target) {
        if (nums.length < 2) {
            return new ArrayList<>(Arrays.asList(-1, -1));
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((nums[i] + nums[j]) == target)
                    return new ArrayList<>(Arrays.asList(i, j));
            }
        }
        return new ArrayList<>(Arrays.asList(-1, -1));
    }

    public static void main(String[] args) {
        System.out.println(findIdx(new int[] { 2, 7, 11, 15 }, 9));
        System.out.println(findIdx(new int[] { 3, 2, 4 }, 6));
        System.out.println(findIdx(new int[] { 3, 3 }, 6));
    }
}
