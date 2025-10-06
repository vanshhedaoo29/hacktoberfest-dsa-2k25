"""
Valid Palindrome

Problem: Given a string s, return true if it is a palindrome, or false otherwise.
A phrase is a palindrome if, after converting all uppercase letters into lowercase letters 
and removing all non-alphanumeric characters, it reads the same forward and backward.

Example 1:
Input: s = "A man, a plan, a canal: Panama"
Output: true
Explanation: "amanaplanacanalpanama" is a palindrome.

Example 2:
Input: s = "race a car"
Output: false
Explanation: "raceacar" is not a palindrome.
"""

def is_palindrome(s):
    """
    Two-pointer approach to check if string is palindrome
    Time Complexity: O(n)
    Space Complexity: O(1)
    """
    if not s or len(s) <= 1:
        return True
    
    left, right = 0, len(s) - 1
    
    while left < right:
        # Skip non-alphanumeric characters from left
        while left < right and not s[left].isalnum():
            left += 1
        
        # Skip non-alphanumeric characters from right
        while left < right and not s[right].isalnum():
            right -= 1
        
        # Compare characters (case-insensitive)
        if s[left].lower() != s[right].lower():
            return False
        
        left += 1
        right -= 1
    
    return True

if __name__ == "__main__":
    # Test cases
    test_cases = [
        "A man, a plan, a canal: Panama",
        "race a car",
        "",
        "a",
        "Madam"
    ]
    
    for test in test_cases:
        result = is_palindrome(test)
        print(f'Test: "{test}" -> {result}')