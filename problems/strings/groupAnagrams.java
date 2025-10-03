// Leetcode 49: Group Anagrams

/*
 * Problem Statement:
 * Given an array of strings, group the anagrams together. You can return the
 * answer in any order.
 *
 * My Logic:
 * 1. Create a result list to hold the groups of anagrams.
 * 2. Create a boolean array to keep track of visited strings.
 * 3. Iterate through each string in the input array.
 * 4. For each unvisited string, create a new group and mark it as visited.
 * 5. Compare it with all subsequent strings to check if they are anagrams using
 * a helper function.
 * 6. If they are anagrams, add them to the current group and mark them as
 * visited.
 * 7. Add the group to the result list.
 * 8. Return the result list.
 *
 * Dry Run:
 *
 * Example: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 *
 * 1. Initialize result = [], visited = [false, false, false, false, false,
 * false]
 * 2. i = 0 ("eat"): grp = ["eat"], visited = [true, false, false, false, false,
 * false]
 *   - j = 1 ("tea"): isAnagram("eat", "tea") -> true, grp = ["eat", "tea"],
 * visited = [true, true, false, false, false, false]
 *  - j = 2 ("tan"): isAnagram("eat", "tan") -> false
 * - j = 3 ("ate"): isAnagram("eat", "ate") -> true, grp = ["eat", "tea",
 * "ate"], visited = [true, true, false, true, false, false]
 *  - j = 4 ("nat"): isAnagram("eat", "nat") -> false
 * - j = 5 ("bat"): isAnagram("eat", "bat") -> false
 *  - Add grp to result: result = [["eat", "tea", "ate"]]
 * Similarly, process the remaining strings.
 * Final result = [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]]
 */
import java.util.*;
class Solution {
  public List<List<String>> groupAnagrams(String[] strs) {
    List<List<String>> result = new ArrayList<>();
    boolean[] visited = new boolean[strs.length];

    for (int i = 0; i < strs.length; i++) {
      if (visited[i])
        continue;

      List<String> grp = new ArrayList<>();
      grp.add(strs[i]);
      visited[i] = true;

      for (int j = i + 1; j < strs.length; j++) {
        if (!visited[j] && isAnagram(strs[i], strs[j])) {
          grp.add(strs[j]);
          visited[j] = true;
        }
      }
      result.add(grp);
    }
    return result;
  }

  public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) {
      return false;
    }
    HashMap<Character, Integer> map = new HashMap<>();

    for (char ch : s.toCharArray()) {
      map.put(ch, map.getOrDefault(ch, 0) + 1);
    }

    for (char ch : t.toCharArray()) {
      if (!map.containsKey(ch)) {
        return false;
      }
      map.put(ch, map.get(ch) - 1);

      if (map.get(ch) < 0) {
        return false;
      }
    }
    return true;
  }
}

public class groupAnagrams {
  public static void main(String[] args) {
    String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
    Solution sol = new Solution();
    List<List<String>> result = sol.groupAnagrams(strs);

    System.out.println("Grouped Anagrams: " + result);
  }
}