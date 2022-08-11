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
 *
 * 해맸던 이유...
 * 아무리 봐도 로직이 맞았음 정확했음 정말
 * 근데 왜 자꾸 틀리나 했는데
 *
 * 알고보니까 처음에도 움직일 수 있음...........................
 */
public class Main {
    static int T;
    static int W;
    static int[] drop;
    static int[][][] dp;

    static int dfs(int depth, int cur, int move) {
        if (depth == (T - 1)) { // 끝에 도달한 경우
            return 0;
        }

        if (dp[cur][depth][move] != -1) {
            return dp[cur][depth][move];
        }

        if (W == move) {
            dp[cur][depth][move] = (cur == drop[depth + 1] ? 1 : 0) + dfs(depth + 1, cur, move);
        } else {
            dp[cur][depth][move] = Math.max((1 == drop[depth + 1] ? 1 : 0) + dfs(depth + 1, 1, 1 == cur ? move : move + 1),
                    (2 == drop[depth + 1] ? 1 : 0) + dfs(depth + 1, 2, 2 == cur ? move : move + 1));
        }

        return dp[cur][depth][move];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2240_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        T = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        dp = new int[3][T][W + 1];
        drop = new int[T];

        for (int i = 0; i < T; i++) {
            Arrays.fill(dp[1][i], -1);
            Arrays.fill(dp[2][i], -1);
            drop[i] = Integer.parseInt(br.readLine());
        }

        System.out.print(Math.max((1 == drop[0] ? 1 : 0) + dfs(0, 1, 0)
                , (2 == drop[0] ? 1 : 0) + dfs(0, 2, 1)));
    }
}
