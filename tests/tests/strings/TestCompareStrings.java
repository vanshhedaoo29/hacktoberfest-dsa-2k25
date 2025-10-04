package tests.strings;

public class TestCompareStrings {
    public static void main(String[] args) {
        String first = "java programming";
        String second = "java programming";
        String third = "python programming";

        // Test 1: compare equal strings → should return true
        boolean result1 = first.equals(second);
        System.out.println("Strings first and second are equal: " + result1);

        // Test 2: compare different strings → should return false
        boolean result2 = first.equals(third);
        System.out.println("Strings first and third are equal: " + result2);
    }
}
