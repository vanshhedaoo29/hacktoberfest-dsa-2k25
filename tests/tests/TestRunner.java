package tests;

import tests.arrays.TestGoodArray;
import tests.strings.TestCompareStrings;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Running Array Tests...");
        TestGoodArray.main(args);

        System.out.println("\nRunning String Tests...");
        TestCompareStrings.main(args);

        System.out.println("\nAll tests finished!");
    }
}
