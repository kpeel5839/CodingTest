import java.util.*;
import java.io.*;

// 1563 : 개근상

/**
 * 예제
 * 4
 */
public class Main {
    static int N;
    static int[][][] dp;
    static int MOD = 1_000_000;

    static int dfs(int depth, int late, int abs) {
        if (depth == N) {
            return 1;
        }

        if (dp[depth][late][abs] != -1) {
            return dp[depth][late][abs];
        }

        dp[depth][late][abs] = 0;

        if (late < 1) {
            dp[depth][late][abs] += dfs(depth + 1, late + 1, 0);
        }

        if (abs < 2) {
            dp[depth][late][abs] += dfs(depth + 1, late, abs + 1);
        }

        dp[depth][late][abs] += dfs(depth + 1, late, 0);

        return dp[depth][late][abs] % MOD;
    }
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1563_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        dp = new int[N][2][3];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 2; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        System.out.println(dfs(0, 0, 0));
    }
}
