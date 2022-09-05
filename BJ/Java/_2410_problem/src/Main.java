import java.util.*;
import java.io.*;

// 2410 : 2의 멱수의 합

/**
 * 예제
 * 7
 */
public class Main {
    static int N;
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2410_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        int range = (int) Math.floor(Math.log(N) / Math.log(2));
        int MOD = 1_000_000_000;
        long[] dp = new long[N + 1];

        Arrays.fill(dp, 1);

        for (int i = 1; i <= range; i++) {
            int number = 1 << i;
            dp[number]++;
//            System.out.println(Arrays.toString(dp));

            for (int j = number + 1; j <= N; j++) {
                dp[j] = (dp[j] + dp[j - number]) % MOD;
            }
        }

        System.out.println(dp[N] % MOD);
    }
}
