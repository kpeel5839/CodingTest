import java.util.*;
import java.io.*;

class Solution {
    static int maxAlgo = 0;
    static int maxCode = 0;
    static final int ALGO = 1;
    static final int CODE = 2;
    static final int INF = 1_000_000_000;
    static int[][] dp;

    static int cal(int value, int judge) {
        if (judge == ALGO) {
            return Math.min(maxAlgo, value);
        } else {
            return Math.min(maxCode, value);
        }
    }

    static int dfs(int algo, int code, int[][] problems) {
        if (maxAlgo <= algo && maxCode <= code) {
            return 0;
        }

        if (dp[algo][code] != INF) {
            return dp[algo][code];
        }

        dp[algo][code] = INF + 1;

        dp[algo][code] = Math.min(dp[algo][code], 1 + dfs(cal(algo + 1, ALGO), code, problems));
        dp[algo][code] = Math.min(dp[algo][code], 1 + dfs(algo, cal(code + 1, CODE), problems));

        for (int[] problem : problems) {
            if (problem[0] <= algo && problem[1] <= code) {
                dp[algo][code] = Math.min(dp[algo][code], problem[4] + dfs(cal(algo + problem[2], ALGO)
                        , cal(code + problem[3], CODE), problems));
            }
        }

        return dp[algo][code];
    }

    public int solution(int alp, int cop, int[][] problems) {
        for (int[] problem : problems) {
            maxAlgo = Math.max(maxAlgo, problem[0]);
            maxCode = Math.max(maxCode, problem[1]);
        }

        dp = new int[Math.max(alp, maxAlgo) + 1][Math.max(cop, maxCode) + 1];

        for (int i = 0; i <= Math.max(alp, maxAlgo); i++) {
            Arrays.fill(dp[i], INF);
        }

        return dfs(alp, cop, problems);
    }
}