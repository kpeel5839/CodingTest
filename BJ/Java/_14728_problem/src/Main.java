import java.util.*;
import java.io.*;

// 14728 : 벼락치기

/**
 * 예제
 *
 * 3 310
 * 50 40
 * 100 70
 * 200 150
 *
 */
public class Main {
    static int T;
    static int N;
    static int[][] exam;
    static int[][] dp;

    static int dfs(int depth, int time) {
        if (depth == N) {
            return 0;
        }

        if (dp[depth][time] != -1) {
            return dp[depth][time];
        }

        if (T < exam[depth][0] + time) {
            dp[depth][time] = dfs(depth + 1, time);
        } else {
            dp[depth][time] = Math.max(exam[depth][1] + dfs(depth + 1, time + exam[depth][0]), dfs(depth + 1, time));
        }

        return dp[depth][time];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_14728_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        dp = new int[N][T + 1];
        exam = new int[N][2];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            Arrays.fill(dp[i], -1);
            exam[i][0] = Integer.parseInt(st.nextToken());
            exam[i][1] = Integer.parseInt(st.nextToken());
        }

        System.out.println(dfs(0, 0));
    }
}
