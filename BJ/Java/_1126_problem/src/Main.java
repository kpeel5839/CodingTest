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
        int[][] dp = new int[N + 1][250000 + 1];
        int[] h = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            h[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i <= N; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= 21; j++) {
                // 본인을 추가하지 않는 경우
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);

                // 본인을 추가하는 경우 (h2에)
                if (j + h[i] <= 250000) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j + h[i]]);
                }

                // 본인을 추가하는 경우 (h1에)
                if (h[i] <= j) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - h[i]] + h[i]);
                } else {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][h[i] - j] + j);
                }

//                if (j == 0) {
//                    System.out.println("h[i] : " + h[i] + " dp[" + i + "][0] : " + dp[i][j]);
//                }
            }
        }

//        for (int i = 0; i <= 21; i++) {
//            for (int j = 1; j <= 3; j++) {
//                System.out.print(dp[j][i] + " ");
//            }
//            System.out.println();
//        }

        System.out.print(dp[N][0] == 0 ? -1 : dp[N][0]);
    }
}
