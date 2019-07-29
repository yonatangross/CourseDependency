package business.algorithms.stringMatchers;

import java.util.Arrays;

public class WordLevenshteinDistance implements StringMatcher {
    private int costOfSubstitution(String a, String b) {
        return a.equals(b) ? 0 : 1;
    }

    private int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }

    public int calculate(String xString, String yString) {
        String[] xArr = xString.split(" ");
        String[] yArr = yString.split(" ");

        int[][] dp = new int[xArr.length + 1][yArr.length + 1];

        for (int i = 0; i <= xArr.length; i++) {
            for (int j = 0; j <= yArr.length; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + costOfSubstitution(xArr[i - 1], yArr[j - 1]),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[xArr.length][yArr.length];
    }
}
