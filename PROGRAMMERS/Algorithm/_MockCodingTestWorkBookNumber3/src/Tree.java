import java.util.*;
import java.io.*;

class Tree {
    static int[][] dp;
    static List<ArrayList<Integer>> graph = new ArrayList<>();

    static int dfs(int p, int c, int n) {
        if (dp[c][n] != -1) {
            return dp[c][n];
        }

        dp[c][n] = 0;

        for (Integer next : graph.get(c)) {
            if (p != next) {
                if (n == 0) {
                    dp[c][n] += 1 + dfs(c, next, 1);
                } else {
                    dp[c][n] += Math.min(dfs(c, next, 0), 1 + dfs(c, next, 1));
                }
            }
        }

        return dp[c][n];
    }

    public int solution(int n, int[][] lighthouse) {
        dp = new int[n + 1][2];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = -1;
            dp[i][1] = -1;
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < n - 1; i++) {
            int a = lighthouse[i][0];
            int b = lighthouse[i][1];

            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        return Math.min(dfs(-1, 1, 0), 1 + dfs(-1, 1, 1));
    }
}