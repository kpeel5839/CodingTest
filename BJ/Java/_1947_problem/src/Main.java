import java.util.*;
import java.io.*;

// 1947 : 선물 전달

/**
 * 예제
 * 5
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1947_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int MOD = 1_000_000_000;
        long[] dp = new long[N + 1];

        dp[1] = 0;

        if (1 < N) {
            dp[2] = 1;
        }

        for (int i = 3; i <= N; i++) {
            dp[i] = ((i - 1) * (dp[i - 1] + dp[i - 2])) % MOD;
        }

        System.out.println(dp[N]);
    }
}
