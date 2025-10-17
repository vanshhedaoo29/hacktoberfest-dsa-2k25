/*
Given two strings string1 and string2, print the longest common substring. If there is no common substring, then print No Common Substring.

Input Format
First line contains string1. Second line contains string2.

Output Format
First line contains longest common substring.

Example 1
Input

ABABC
BABCA
Output

ABC
Explanation

The longest common substring of the strings ABABC, BABCA and ABCBA is string ABC of length 3. Other common substrings are A, AB, B, BA, BC and C.

ABABC
  |||
 BABCA
  |||
  ABCBA
Constraints
1 <= string1.length, string2.length <= 1000
The variables string1 and string2 are capable of holding any character from the Unicode character set.
*/

function longestCommonSubstring(string1, string2) {
  const n = string1.length;
  const m = string2.length;

  const dp = Array.from({ length: n + 1 }, () => Array(m + 1).fill(0));

  let maxLength = 0;
  let endIndex = 0;

  for (let i = 1; i <= n; i++) {
    for (let j = 1; j <= m; j++) {
      if (string1[i - 1] === string2[j - 1]) {
        dp[i][j] = dp[i - 1][j - 1] + 1;

        // Update only when strictly greater
        if (dp[i][j] > maxLength) {
          maxLength = dp[i][j];
          endIndex = i;
        }
      } else {
        dp[i][j] = 0;
      }
    }
  }

  if (maxLength === 0) return "No Common Substring";

  return string1.substring(endIndex - maxLength, endIndex);
}

/* Do not change the code below  */
const rl = require("readline");
const reader = rl.createInterface({
  input: process.stdin,
  output: process.stdout,
});
reader.on("line", function (line1) {
  reader.on("line", function (line2) {
    console.log(longestCommonSubstring(line1, line2));
    reader.close();
  });
});
