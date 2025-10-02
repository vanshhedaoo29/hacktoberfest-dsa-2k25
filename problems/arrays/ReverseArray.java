// Program to reverse an array in Java

import java.util.*;

public class ReverseArray {

    public static void main(String[] args) {

        // Sample input
        int[] arr = {1, 2, 3, 4, 5};

        System.out.println("Original array: " + Arrays.toString(arr));

        // Initialize two pointers: one at the start, one at the end
        int start = 0;
        int end = arr.length - 1;

        // Swap elements until start and end pointers meet
        while (start < end) {
            // Store the start element temporarily
            int temp = arr[start];

            // Swap start and end elements
            arr[start] = arr[end];
            arr[end] = temp;

            // Move pointers toward the center
            start++;
            end--;
        }

        System.out.println("Reversed array: " + Arrays.toString(arr));
    }
}
