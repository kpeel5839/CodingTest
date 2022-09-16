import java.util.*;
import java.io.*;

// 14863 : 서울에서 경산까지

/**
 * 예제
 * 3 1650
 * 500 200 200 100
 * 800 370 300 120
 * 700 250 300 90
 */
public class Main {
    static int N;
    static int K;
    static int INF = -200_000_000;
    static int[][] dp;
    static int[][] bike;
    static int[][] walk;

    static int dfs(int depth, int time) {
        if (K < time) {
            return INF;
        }

        if (depth == N) {
            return 0;
        }

        if (dp[depth][time] != -1) {
            return dp[depth][time];
        }

        // Walk or Bike
        dp[depth][time] = Math.max(dfs(depth + 1, time + walk[depth][0]) + walk[depth][1], dfs(depth + 1, time + bike[depth][0]) + bike[depth][1]);

        return dp[depth][time];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_14863_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dp = new int[N][K + 1];
        bike = new int[N][2];
        walk = new int[N][2];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            Arrays.fill(dp[i], -1);

            walk[i][0] = Integer.parseInt(st.nextToken());
            walk[i][1] = Integer.parseInt(st.nextToken());
            bike[i][0] = Integer.parseInt(st.nextToken());
            bike[i][1] = Integer.parseInt(st.nextToken());
        }

        System.out.println(dfs(0, 0));
    }
}
