// KMP (Knuth-Morris-Pratt) Algorithm for Pattern Searching
// Given a text and a pattern, find all occurrences of the pattern in the text.
// 
// The KMP algorithm is an efficient string matching algorithm that avoids redundant comparisons
// by utilizing the information from previous matches to skip characters in the text.
//
// Key advantage: Unlike naive approach O(m*n), KMP runs in O(m+n) time where m = text length, n = pattern length
//
// Examples:
// Text: "ABABDABACDABABCABCABCABCABC", Pattern: "ABCAB"
// Output: Pattern found at indices: [15, 20]
//
// Text: "AABAACAADAABAAABAA", Pattern: "AABA"  
// Output: Pattern found at indices: [0, 9, 13]

public class KMP_algorithm {
    
    /**
     * Main KMP search function that finds all occurrences of pattern in text.
     * 
     * Algorithm Overview:
     * 1. First, build the LPS (Longest Proper Prefix which is also Suffix) array for the pattern
     * 2. Use the LPS array to efficiently search for pattern in text by avoiding redundant comparisons
     * 
     * Key Insight:
     * When a mismatch occurs at position i in text and j in pattern, instead of starting over,
     * we can skip to position LPS[j-1] in the pattern because we know that the characters
     * from 0 to LPS[j-1] already match with the corresponding characters in text.
     * 
     * Time Complexity: O(m + n) where m = text length, n = pattern length
     * Space Complexity: O(n) for the LPS array
     * 
     * @param text the input text to search in
     * @param pattern the pattern to search for
     */
    public void KMPSearch(String text, String pattern) {
        int m = text.length();
        int n = pattern.length();
        
        // Build the LPS array for the pattern
        int[] lps = computeLPSArray(pattern);
        
        int i = 0; // index for text
        int j = 0; // index for pattern
        
        System.out.println("Searching for pattern '" + pattern + "' in text '" + text + "'");
        boolean found = false;
        
        while (i < m) {
            // If characters match, advance both pointers
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            
            // If we've matched the entire pattern
            if (j == n) {
                System.out.println("Pattern found at index: " + (i - j));
                found = true;
                
                // Get the next position to continue searching using LPS array
                j = lps[j - 1];
            }
            // If there's a mismatch after some matches
            else if (i < m && pattern.charAt(j) != text.charAt(i)) {
                // If we've made some progress in pattern matching
                if (j != 0) {
                    // Don't increment i, but use LPS to find next position of j
                    // This is the key optimization of KMP algorithm
                    j = lps[j - 1];
                } else {
                    // If no progress made, just move to next character in text
                    i++;
                }
            }
        }
        
        if (!found) {
            System.out.println("Pattern not found in the text.");
        }
    }
    
    /**
     * Computes the LPS (Longest Proper Prefix which is also Suffix) array for the given pattern.
     * 
     * LPS Array Explanation:
     * For each position i in the pattern, LPS[i] contains the length of the longest proper prefix
     * of pattern[0...i] which is also a suffix of pattern[0...i].
     * 
     * A proper prefix is a prefix that is not equal to the string itself.
     * 
     * Example: For pattern "ABCAB"
     * - LPS[0] = 0 (no proper prefix for single char)
     * - LPS[1] = 0 (no common prefix-suffix for "AB")
     * - LPS[2] = 0 (no common prefix-suffix for "ABC")  
     * - LPS[3] = 1 ("ABCA" has "A" as common prefix-suffix)
     * - LPS[4] = 2 ("ABCAB" has "AB" as common prefix-suffix)
     * 
     * Algorithm:
     * 1. Use two pointers: len (length of previous longest prefix suffix) and i
     * 2. If pattern[i] == pattern[len], increment both and set LPS[i] = len
     * 3. If they don't match:
     *    - If len != 0, set len = LPS[len-1] (don't increment i)
     *    - If len == 0, set LPS[i] = 0 and increment i
     * 
     * @param pattern the pattern for which to compute LPS array
     * @return LPS array
     */
    private int[] computeLPSArray(String pattern) {
        int n = pattern.length();
        int[] lps = new int[n];
        
        int len = 0; // length of the previous longest prefix suffix
        int i = 1;   // start from second character
        
        // LPS[0] is always 0 (single character has no proper prefix)
        lps[0] = 0;
        
        System.out.println("\nBuilding LPS array for pattern: " + pattern);
        
        while (i < n) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                // Characters match, so extend the length of prefix-suffix
                len++;
                lps[i] = len;
                System.out.println("Match at i=" + i + ", len=" + len + " -> LPS[" + i + "] = " + len);
                i++;
            } else {
                // Characters don't match
                if (len != 0) {
                    // Try with the previous longest prefix suffix
                    // This is the key insight: we don't reset len to 0, but use LPS[len-1]
                    len = lps[len - 1];
                    System.out.println("Mismatch at i=" + i + ", backtrack len to " + len);
                    // Note: we don't increment i here
                } else {
                    // No previous prefix suffix, set current LPS to 0
                    lps[i] = 0;
                    System.out.println("No prefix-suffix for i=" + i + " -> LPS[" + i + "] = 0");
                    i++;
                }
            }
        }
        
        System.out.println("Final LPS array: " + java.util.Arrays.toString(lps));
        return lps;
    }
    
    /**
     * Enhanced version that returns all found indices instead of just printing them.
     * 
     * @param text the input text
     * @param pattern the pattern to search for
     * @return list of all starting indices where pattern is found
     */
    public java.util.List<Integer> searchAllOccurrences(String text, String pattern) {
        java.util.List<Integer> result = new java.util.ArrayList<>();
        int m = text.length();
        int n = pattern.length();
        
        if (n == 0) return result; // empty pattern
        
        int[] lps = computeLPSArray(pattern);
        int i = 0; // index for text
        int j = 0; // index for pattern
        
        while (i < m) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            
            if (j == n) {
                result.add(i - j);
                j = lps[j - 1];
            } else if (i < m && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Utility method to demonstrate time complexity comparison with naive approach.
     * This is for educational purposes to show why KMP is better.
     */
    public void naiveSearch(String text, String pattern) {
        int m = text.length();
        int n = pattern.length();
        int comparisons = 0;
        
        System.out.println("\n=== Naive Search Comparison ===");
        System.out.println("Searching for pattern '" + pattern + "' in text '" + text + "'");
        
        for (int i = 0; i <= m - n; i++) {
            int j;
            for (j = 0; j < n; j++) {
                comparisons++;
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            if (j == n) {
                System.out.println("Naive: Pattern found at index " + i);
            }
        }
        
        System.out.println("Naive algorithm made " + comparisons + " character comparisons");
        System.out.println("KMP algorithm complexity: O(m + n) = O(" + m + " + " + n + ") = O(" + (m + n) + ")");
        System.out.println("Naive algorithm complexity: O(m * n) = O(" + m + " * " + n + ") = O(" + (m * n) + ")");
    }
    
    /**
     * Test method to demonstrate the KMP algorithm with various examples.
     */
    public static void main(String[] args) {
        KMP_algorithm kmp = new KMP_algorithm();
        
        System.out.println("=".repeat(60));
        System.out.println("KMP (Knuth-Morris-Pratt) Algorithm Demonstration");
        System.out.println("=".repeat(60));
        
        // Test Case 1: Basic example
        System.out.println("\n📍 Test Case 1: Basic Pattern Search");
        kmp.KMPSearch("ABABDABACDABABCABCABCABCABC", "ABCAB");
        
        // Test Case 2: Pattern with repetitions
        System.out.println("\n📍 Test Case 2: Pattern with Repetitions");
        kmp.KMPSearch("AABAACAADAABAAABAA", "AABA");
        
        // Test Case 3: No match
        System.out.println("\n📍 Test Case 3: Pattern Not Found");
        kmp.KMPSearch("ABCDEFGH", "XYZ");
        
        // Test Case 4: Pattern at the beginning
        System.out.println("\n📍 Test Case 4: Pattern at Beginning");
        kmp.KMPSearch("ABCABCABC", "ABC");
        
        // Test Case 5: Using searchAllOccurrences method
        System.out.println("\n📍 Test Case 5: All Occurrences Method");
        java.util.List<Integer> indices = kmp.searchAllOccurrences("ABABABAB", "ABA");
        System.out.println("All occurrences of 'ABA' in 'ABABABAB': " + indices);
        
        // Test Case 6: Compare with naive approach
        System.out.println("\n📍 Test Case 6: Complexity Comparison");
        kmp.naiveSearch("ABABCABABA", "ABABA");
        kmp.KMPSearch("ABABCABABA", "ABABA");
        
        // Test Case 7: Edge cases
        System.out.println("\n📍 Test Case 7: Edge Cases");
        kmp.KMPSearch("A", "A");           // Single character match
        kmp.KMPSearch("ABC", "ABCD");      // Pattern longer than text
        kmp.KMPSearch("", "ABC");          // Empty text
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Key Advantages of KMP Algorithm:");
        System.out.println("✅ Time Complexity: O(m + n) vs O(m * n) for naive approach");
        System.out.println("✅ No backtracking in text - each character examined at most twice");
        System.out.println("✅ Preprocessing creates LPS array in O(n) time");
        System.out.println("✅ Efficient for patterns with repetitive substructures");
        System.out.println("=".repeat(60));
    }
}
