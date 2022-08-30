import java.util.*;
import java.io.*;

// 14852 : 타일 채우기 3

/**
 * 예제
 * 1
 */
public class Main {
    static int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_14852_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        long[][] dp = new long[N + 1][2];

        dp[0][0] = 0;
        dp[1][0] = 2;

        if (N != 1) {
            dp[2][0] = 7;
            dp[2][1] = 1;
        }

        for (int i = 3; i <= N; i++) {
            dp[i][1] = (dp[i - 1][1] + dp[i - 3][0]) % MOD;
            dp[i][0] = (3 * dp[i - 2][0] + 2 * dp[i - 1][0] + 2 * dp[i][1]) % MOD;
        }

        System.out.println(dp[N][0] % MOD);
    }
}
