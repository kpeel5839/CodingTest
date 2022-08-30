import java.util.*;
import java.io.*;

// 1106 : 호텔

/**
 * 예제
 * 12 2
 * 3 5
 * 1 1
 */
public class Main {
    static int C;
    static int N;
    static int[][] dp;
    static int[][] advertise;
    static int INF = 1_000_000_000;

    static int cal(int v) {
        return Math.min(C, v);
    }

    static int dfs(int depth, int cus) {
        if (depth == N) {
            if (cus == C) {
                return 0;
            } else {
                return INF;
            }
        }

        if (dp[depth][cus] != Integer.MAX_VALUE) {
            return dp[depth][cus];
        }

        for (int i = 0; i <= 1000; i++) { // 최소 1이라고 할 때
            dp[depth][cus] = Math.min(dp[depth][cus], (i * advertise[depth][0]) + dfs(depth + 1, cal(cus + (i * advertise[depth][1]))));

            if (C <= cus + i * advertise[depth][1]) { // 넘어가는 경우 이제 그마안
                break;
            }
        }

        return dp[depth][cus];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1106_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        dp = new int[N][C + 1];
        advertise = new int[N][2];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            Arrays.fill(dp[i], Integer.MAX_VALUE);

            advertise[i][0] = Integer.parseInt(st.nextToken());
            advertise[i][1] = Integer.parseInt(st.nextToken());
        }

        System.out.println(dfs(0, 0));
    }
}
