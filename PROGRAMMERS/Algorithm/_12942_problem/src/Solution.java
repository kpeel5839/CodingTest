import java.util.*;
import java.io.*;

class Solution {
    public int solution(int[][] matrix_sizes) {
        int n = matrix_sizes.length;
        int INF = 1_000_000_000;
        int[] p = new int[n + 1];
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            p[i] = matrix_sizes[i][0];
            if (i == n - 1) {
                p[i + 1] = matrix_sizes[i][1];
            }

            Arrays.fill(dp[i], INF);
        }

        for (int i = 0; i < n; i++) {
            dp[i][i] = 0;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j + i < n; j++) {
                for (int k = j; k < j + i; k++) {
                    int c = j + i;
                    dp[j][c] = Math.min(dp[j][c], dp[j][k] + dp[k + 1][c] + (p[j] * p[k + 1] * p[c + 1]));
                }
            }
        }

        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         System.out.print(dp[i][j] + " ");
        //     }
        //     System.out.println();
        // }

        return dp[0][n - 1];
    }
}