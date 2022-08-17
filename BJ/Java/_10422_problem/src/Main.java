import java.util.*;
import java.io.*;

// 10422 : 괄호

/**
 * 예제
 *
 * 3
 * 1
 * 2
 * 4
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10422_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        int MOD = 1_000_000_007;
        long[] dp = new long[5001];
        dp[0] = 1;
        dp[2] = 1;

        for (int i = 4; i <= 5000; i += 2) {
            for (int j = 0; j <= i - 2; j++) {
                dp[i] = (dp[i] + dp[j] * dp[i - j - 2]) % MOD;
            }
        }

        for (int i = 0; i < T; i++) {
            bw.write(dp[Integer.parseInt(br.readLine())] + "\n");
        }

        bw.flush();
        bw.close();
    }
}
