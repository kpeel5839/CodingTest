import java.util.*;
import java.io.*;

// 2240 : 자두나무
/**
 * 예제
 * 7 2
 * 2
 * 1
 * 1
 * 2
 * 2
 * 1
 * 1
 */
public class Main {
    static int T;
    static int W;
    static int[] drop;
    static int[][] dp;

    static int dfs(int depth, int cur, int move) {
        if (depth == (T - 1)) { // 끝에 도달한 경우
            return 0;
        }

        if (dp[depth][move] != -1) {
            return dp[depth][move];
        }

        if (move == W) {
            dp[depth][move] = (cur == drop[depth + 1] ? 1 : 0) + dfs(depth + 1, cur, move);
        } else {
            dp[depth][move] = Math.max((1 == drop[depth + 1] ? 1 : 0) + dfs(depth + 1, 1, (1 == cur ? move : move + 1)),
                    (2 == drop[depth + 1] ? 1 : 0) + dfs(depth + 1, 2, (2 == cur ? move : move + 1)));
        }

        return dp[depth][move];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2240_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        T = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        dp = new int[T][W + 1];
        drop = new int[T];

        for (int i = 0; i < T; i++) {
            Arrays.fill(dp[i], -1);
            drop[i] = Integer.parseInt(br.readLine());
        }

        System.out.print(dfs(0, 1, 0));
    }
}
