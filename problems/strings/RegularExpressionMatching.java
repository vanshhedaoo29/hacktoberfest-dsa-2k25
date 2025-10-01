// Given an input string s and a pattern p, implement regular expression matching with support for '.' and '*' where:

// '.' Matches any single character.​​​​
// '*' Matches zero or more of the preceding element.
// The matching should cover the entire input string (not partial).

// Example 1:
// Input: s = "aa", p = "a"
// Output: false
// Explanation: "a" does not match the entire string "aa".


// Example 3:
// Input: s = "ab", p = ".*"
// Output: true
// Explanation: ".*" means "zero or more (*) of any character (.)".

public class RegularExpressionMatching {
    
    /**
     * Determines if a string matches a regular expression pattern with '.' and '*' support.
     * 
     * Approach - Dynamic Programming:
     * This problem can be solved using 2D DP where dp[i][j] represents whether
     * the first i characters of string s match the first j characters of pattern p.
     * 
     * Key insights:
     * 1. '.' matches any single character
     * 2. '*' matches zero or more of the preceding element
     * 3. We need to handle '*' specially as it can represent 0 or more occurrences
     * 
     * Time Complexity: O(m * n) where m = length of string, n = length of pattern
     * Space Complexity: O(m * n) for the DP table
     * 
     * @param s input string to match
     * @param p pattern with '.' and '*' support
     * @return true if s matches p completely, false otherwise
     */
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        
        // dp[i][j] = true if first i chars of s match first j chars of p
        boolean[][] dp = new boolean[m + 1][n + 1];
        
        // Base case: empty string matches empty pattern
        dp[0][0] = true;
        
        // Handle patterns that can match empty string (like "a*", "a*b*", ".*")
        // These patterns have '*' as every second character starting from index 1
        for (int j = 2; j <= n; j += 2) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }
        
        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char currentStringChar = s.charAt(i - 1);
                char currentPatternChar = p.charAt(j - 1);
                
                if (currentPatternChar == '*') {
                    // '*' matches zero or more of the preceding element
                    char precedingChar = p.charAt(j - 2);
                    
                    // Case 1: Use '*' as zero occurrences (ignore preceding char and '*')
                    dp[i][j] = dp[i][j - 2];
                    
                    // Case 2: Use '*' as one or more occurrences
                    // This is possible if:
                    // - preceding char matches current string char, OR
                    // - preceding char is '.' (matches any character)
                    if (precedingChar == currentStringChar || precedingChar == '.') {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else {
                    // Direct character matching (including '.' which matches any char)
                    if (currentPatternChar == currentStringChar || currentPatternChar == '.') {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                    // If characters don't match, dp[i][j] remains false (default)
                }
            }
        }
        
        return dp[m][n];
    }
    
    
    public boolean isMatchRecursive(String s, String p) {
        return isMatchHelper(s, p, 0, 0, new Boolean[s.length() + 1][p.length() + 1]);
    }
    
    /**
     * Recursive helper function with memoization.
     * 
     * Logic:
     * 1. If we've processed entire pattern, check if string is also fully processed
     * 2. If next character in pattern is '*', we have two choices:
     *    - Skip the "char*" combination (0 occurrences)
     *    - Use the "char*" if current chars match, then try matching rest
     * 3. If no '*', simply check if current characters match and recurse
     * 
     * @param s input string
     * @param p pattern
     * @param i current index in string
     * @param j current index in pattern
     * @param memo memoization table
     * @return true if remaining parts match
     */
    private boolean isMatchHelper(String s, String p, int i, int j, Boolean[][] memo) {
        // Base case: if pattern is exhausted
        if (j == p.length()) {
            return i == s.length();
        }
        
        // Check memoization
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        // Check if current characters match
        boolean currentMatch = (i < s.length()) && 
                              (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');
        
        // If next character is '*'
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            // Two options:
            // 1. Skip "char*" (zero occurrences)
            // 2. Use "char*" if current chars match
            memo[i][j] = isMatchHelper(s, p, i, j + 2, memo) || 
                        (currentMatch && isMatchHelper(s, p, i + 1, j, memo));
        } else {
            // No '*', so current chars must match and continue
            memo[i][j] = currentMatch && isMatchHelper(s, p, i + 1, j + 1, memo);
        }
        
        return memo[i][j];
    }
    
    /**
     * Test method to verify the implementation with various examples.
     */
    public static void main(String[] args) {
        RegularExpressionMatching solution = new RegularExpressionMatching();
        
        // Test cases
        System.out.println("Test 1: " + solution.isMatch("aa", "a"));        // false
        System.out.println("Test 2: " + solution.isMatch("aa", "a*"));       // true
        System.out.println("Test 3: " + solution.isMatch("ab", ".*"));       // true
        System.out.println("Test 4: " + solution.isMatch("aab", "c*a*b"));   // true
        System.out.println("Test 5: " + solution.isMatch("mississippi", "mis*is*p*."));  // false
        System.out.println("Test 6: " + solution.isMatch("", ""));           // true
        System.out.println("Test 7: " + solution.isMatch("", "a*"));         // true
        System.out.println("Test 8: " + solution.isMatch("ab", ".*c"));      // false
    }
}
