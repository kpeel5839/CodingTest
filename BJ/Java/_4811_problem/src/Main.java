import java.util.*;
import java.io.*;

// 4811 : 알약

/**
 * 예제
 * 6
 * 1
 * 4
 * 2
 * 3
 * 30
 * 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_4811_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {
            int N = Integer.parseInt(br.readLine());

            if (N == 0) {
                break;
            }

            long[][] dp = new long[N + 1][N + 1];
            dp[N][N - 1] = 1;

            for (int k = (2 * N - 2); k != -1; k--) {
                for (int i = 0; i <= N; i++) {
                    if (k < i) {
                        break;
                    }
                    for (int j = 0; j <= N; j++) {
                        if (i < j) {
                            break;
                        }

                        if (i + j == k) {
                            if (i == N) {
                                dp[i][j] = dp[i][j + 1];
                            } else {
                                dp[i][j] = dp[i + 1][j] + dp[i][j + 1];
                            }
                        }
                    }
                }
            }

            bw.write(dp[0][0] + "\n");
        }

        bw.flush();
        bw.close();
    }
}