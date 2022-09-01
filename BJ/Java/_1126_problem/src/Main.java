import java.util.*;
import java.io.*;

// 1126 : 같은 탑

/**
 * 예제
 * 3
 * 2 3 5
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1126_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int MX = 500000;
        int[][] dp = new int[N + 1][MX + 1];
        int[] h = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            h[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i <= N; i++) {
            Arrays.fill(dp[i], -1);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= MX; j++) {
                // 본인을 추가하지 않는 경우
                dp[i][j] = dp[i - 1][j];

                // 본인을 추가하는 경우 (h2에)
                if (j + h[i] <= MX && dp[i - 1][j + h[i]] != -1) { // 해당 dp[i][j] 가 존재를 해야지 가져올 수 있는 것
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j + h[i]]);
                }

                // 본인을 추가하는 경우 (h1에)
                if (j - h[i] >= 0 && dp[i - 1][j - h[i]] != -1) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - h[i]] + h[i]);
                }

                if (h[i] - j >= 0 && dp[i - 1][h[i] - j] != -1) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][h[i] - j] + j);
                }
            }
        }

        System.out.print(dp[N][0] == 0 ? -1 : dp[N][0]);
    }
}
