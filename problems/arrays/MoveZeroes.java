// Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.

// Time Complexity: O(n)
// Space Complexity: O(1)

import java.util.Arrays;

public class MoveZeroes {

    /*
     * Function to move all zeroes in the array to the end
     * while keeping the relative order of non-zero elements.
     * 
     * @param nums the input array of integers
     */
    public static void moveZeroes(int[] nums) {
        int insertPos = 0; // Pointer to place the next non-zero element

        // Step 1: Shift all non-zero elements forward
        for (int num : nums) {
            if (num != 0) {
                nums[insertPos++] = num;
            }
        }

        // Step 2: Fill the rest with zeroes
        while (insertPos < nums.length) {
            nums[insertPos++] = 0;
        }
    }

    public static void main(String[] args) {
        // Example 1
        int[] nums1 = {0, 1, 0, 3, 12};
        moveZeroes(nums1);
        System.out.println("Input: [0, 1, 0, 3, 12]");
        System.out.println("Output: " + Arrays.toString(nums1));

        // Example 2
        int[] nums2 = {0};
        moveZeroes(nums2);
        System.out.println("\nInput: [0]");
        System.out.println("Output: " + Arrays.toString(nums2));

        // Example 3
        int[] nums3 = {4, 2, 4, 0, 0, 3, 0, 5, 1, 0};
        moveZeroes(nums3);
        System.out.println("\nInput: [4, 2, 4, 0, 0, 3, 0, 5, 1, 0]");
        System.out.println("Output: " + Arrays.toString(nums3));

        // Example 4
        int[] nums4 = {1, 2, 3, 4, 5};
        moveZeroes(nums4);
        System.out.println("\nInput: [1, 2, 3, 4, 5]");
        System.out.println("Output: " + Arrays.toString(nums4));
    }
}

/*
==========================
 Expected Input / Output
==========================

Input: [0, 1, 0, 3, 12]
Output: [1, 3, 12, 0, 0]

Input: [0]
Output: [0]

Input: [4, 2, 4, 0, 0, 3, 0, 5, 1, 0]
Output: [4, 2, 4, 3, 5, 1, 0, 0, 0, 0]

Input: [1, 2, 3, 4, 5]
Output: [1, 2, 3, 4, 5]
*/
