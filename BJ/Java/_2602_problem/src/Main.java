import java.util.*;
import java.io.*;

/**
 * 예제
 * RGS
 * RINGSR
 * GRGGNS
 */
public class Main {
    static int N; // 문자열의 길이
    static int M; // 다리의 길이
    static char[] arr;
    static char[] a;
    static char[] b;
    static int[][][] dp;

    static int dfs(int depth, int cur, boolean isUp) { // isUp = true, 윗 다리에 있는 것, isUp = false, 아랫 다리에 있는 것
        if (depth == N) {
            return 1;
        }

        if (cur == M) { // 실패한 경우, depth 에 도달하지도 못했는데, 이미 다리 끝까지 간 경우
            return 0;
        }

        int now = isUp ? 0 : 1;

        if (dp[now][depth][cur] != -1) {
            return dp[now][depth][cur];
        }

        dp[now][depth][cur] = 0;

        for (int i = cur; i < M; i++) {
            if (isUp) {
                if (arr[depth] == b[i]) {
                    dp[now][depth][cur] += dfs(depth + 1, i + 1, !isUp);
                }
            } else {
                if (arr[depth] == a[i]) {
                    dp[now][depth][cur] += dfs(depth + 1, i + 1, !isUp);
                }
            }
        }

        return dp[now][depth][cur];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2602_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        arr = br.readLine().toCharArray();
        a = br.readLine().toCharArray();
        b = br.readLine().toCharArray();

        N = arr.length;
        M = a.length;
        dp = new int[2][N][M];

        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[0][i], - 1);
            Arrays.fill(dp[1][i], -1);
        }

        System.out.println(dfs(0, 0, true) + dfs(0, 0, false));
    }
}
