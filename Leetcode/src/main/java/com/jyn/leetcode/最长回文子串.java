package com.jyn.leetcode;

/*
 * 如何寻找最长回文子串
 * https://labuladong.gitee.io/algo/5/39/
 */
class 最长回文子串 {
    public static void main(String[] args) {
        System.out.println(longestPalindrome("babbab"));
    }

    public static String longestPalindrome(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            String s1 = palindrome(s, i, i);
            String s2 = palindrome(s, i, i + 1);
            result = result.length() > s1.length() ? result : s1;
            result = result.length() > s2.length() ? result : s2;=====
        }
        return result;
    }

    /*
     * 寻找回文字串
     */
    public static String palindrome(String s, int left, int right) {
        while (left >= 0 && right < s.length() &&
                s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return s.substring(left + 1, right);
    }
}
