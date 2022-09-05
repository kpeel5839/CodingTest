import java.util.*;
import java.io.*;

// 1720 : 타일 코드

/**
 * 예제
 * 4
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1720_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int MAXSIZE = 31;
        long[] dp = new long[MAXSIZE];
        dp[1] = 1;
        dp[2] = 3;

        for (int i = 3; i <= N; i++) {
            dp[i] = dp[i - 1] + dp[i - 2] * 2;
        }

        long[][] dp2 = new long[MAXSIZE][MAXSIZE];

        // 이제 홀수, 짝수
        if (N % 2 == 0) { // 짝 수
            dp2[N / 2][N / 2 + 1] = 3;
            dp2[N / 2 - 1][N / 2 + 2] = 5;

            int i = N / 2 - 2;
            int j = N / 2 + 3;

            while (0 < i && j <= N) {
                dp2[i][j] = dp2[i + 1][j - 1] + dp2[i + 2][j - 2] * 2;
                i--;
                j++;
            }
        } else { // 홀 수
            dp2[N / 2 + 1][N / 2 + 1] = 1;
            dp2[N / 2][N / 2 + 2] = 1;

            int i = N / 2 - 1;
            int j = N / 2 + 3;

            while (0 < i && j <= N) {
                dp2[i][j] = dp2[i + 1][j - 1] + dp2[i + 2][j - 2] * 2;
                i--;
                j++;
            }
        }

        System.out.println((dp[N] - dp2[1][N]) / 2 + dp2[1][N]);
    }
}
