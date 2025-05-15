package com.campusmov.platform.profileservice.profile.application.internal.util;

import java.util.HashSet;
import java.util.Set;

public class CourseNameSimilarity {

    //Method to check if two course names match based on a threshold
    public static boolean isCourseNameMatch(String courseName, String targetCourseName, double threshold) {
        if (courseName.contains(targetCourseName)) {
            return true;
        }
        double fuzzyMatch = nGramMatch(courseName, targetCourseName);
        if (fuzzyMatch >= threshold) {
            return true;
        }
        double similarity = calculateSimilarity(targetCourseName, courseName);
        return similarity >= threshold;
    }

    //Method for calculating similarity using Levenshtein distance
    private static double calculateSimilarity(String s1, String s2) {
        int maxLength = Math.max(s1.length(), s2.length());
        if (maxLength == 0) return 1.0;
        int distance = levenshteinDistance(s1, s2);
        return 1.0 - ((double) distance / maxLength);
    }

    //Method for fuzzy match using n-grams
    private static double nGramMatch(String courseName, String targetCourseName) {
        Set<String> courseNameNGrams = generateNGrams(courseName, 3);
        Set<String> targetCourseNameNGrams = generateNGrams(targetCourseName, 3);

        Set<String> intersection = new HashSet<>(courseNameNGrams);
        intersection.retainAll(targetCourseNameNGrams);

        Set<String> union = new HashSet<>(courseNameNGrams);
        union.addAll(targetCourseNameNGrams);

        return (double) intersection.size() / union.size();
    }

    //Method to generate n-grams of a given string
    private static Set<String> generateNGrams(String str, int n) {
        Set<String> nGrams = new HashSet<>();
        for (int i = 0; i <= str.length() - n; i++) {
            nGrams.add(str.substring(i, i + n));
        }
        return nGrams;
    }

    //Levenshtein distance algorithm
    private static int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }
}