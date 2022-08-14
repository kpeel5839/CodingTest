import java.util.*;
import java.io.*;

// 11058 : 크리보드

/**
 * 예제
 * 11
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11058_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        long[] dp = new long[N];

        for (int i = 0; i < N; i++) {
            dp[i] = i + 1;
        }

        for (int i = 1; i <= N - 4; i++) {
            for (int j = i + 3; j <= N - 1; j++) {
                dp[j] = Math.max(dp[j], dp[i] * (long) (j - i - 1));
            }
        }

        System.out.print(dp[N - 1]);
    }
}
