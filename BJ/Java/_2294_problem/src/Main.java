import java.util.*;
import java.io.*;

// 2294 : 동전 2

/**
 *
 * 예제
 *
 * 3 15
 * 1
 * 5
 * 12
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2294_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int INF = 10_000_000;
        int[][] dp = new int[N][K + 1];
        int[] cost = new int[N];

        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], INF);
            cost[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(cost);

        for (int i = 0; i < N; i++) {
            for (int j = 1; j <= K; j++) {
                if (i == 0) {
                    if (j == cost[i]) {
                        dp[i][j] = 1;
                    } else if (cost[i] < j){
                        if (dp[i][j - cost[i]] != INF) {
                            dp[i][j] = dp[i][j - cost[i]] + 1;
                        }
                    }
                } else {
                    if (j < cost[i]) {
                        dp[i][j] = dp[i - 1][j];
                    } else if (j == cost[i]) {
                        dp[i][j] = 1;
                    } else {
                        if (dp[i - 1][j] == INF && dp[i][j - cost[i]] == INF) {
                            dp[i][j] = INF;
                        } else {
                            dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - cost[i]] + 1);
                        }
                    }
                }
            }
        }

        System.out.println(dp[N - 1][K] == INF ? -1 : dp[N - 1][K]);
    }
}
