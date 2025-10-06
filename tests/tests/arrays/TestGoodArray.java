package tests.arrays;

import problems.arrays.GoodArray;

public class TestGoodArray {
    public static void main(String[] args) {
        GoodArray ga = new GoodArray();

        // Test 1: array of multiples of 3 → should return false
        int[] arr1 = {3, 6, 9};
        System.out.println("Test 1 (expect false): " + ga.isGoodArray(arr1));

        // Test 2: array with coprime numbers → should return true
        int[] arr2 = {3, 6, 7};
        System.out.println("Test 2 (expect true): " + ga.isGoodArray(arr2));
    }
}
