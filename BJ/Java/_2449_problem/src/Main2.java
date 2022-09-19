import java.util.*;
import java.io.*;

// 2449 : 전구

/**
 * 예제
 * 10 3
 * 1 1 2 3 3 3 2 2 1 1
 */
public class Main2 {
    static int N;
    static int K;
    static int[][][] dp;
    static int[] light;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2449_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        light = new int[N];
        dp = new int[N][N][K + 1];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            light[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < N; i++) {
            for (int k = 1; k <= K; k++) {
                if (light[i] != k) { // 색이 다른 경우
                    dp[i][i][k] = 1;
                }
            }
        }

        // 이제 범위가 1 ~ (N - 1) 까지 할 것임
        for (int add = 1; add <= (N - 1); add++) {
            for (int i = 0; i + add < N; i++) {
                int j = i + add;

                // 범위 설정 완료
                // 이제 dp[i][j - 1] 와 j, 그리고 dp[i + 1][j] 와 i 를 계속해서 비교해가면서 가면됨
                for (int k = 1; k <= K; k++) {
                    int min1 = Math.min(dp[i][j - 1][k] + dp[j][j][k], dp[i + 1][j][k] + dp[i][i][k]); // 다이렉트로 가는 형태
                    int min2 = Math.min(dp[i][j - 1][light[j]] + 1, dp[i + 1][j][light[i]] + 1);

                    dp[i][j][k] = Math.min(min1, min2);
                }
            }
        }

        int res = Integer.MAX_VALUE;

        for (int i = 1; i <= K; i++) {
            res = Math.min(res, dp[0][N - 1][i]);
        }

        System.out.println(res);
    }
}
