import java.util.*;
import java.io.*;

// 2098 : 외판원 순회

/**
 * - 예제
 * 4
 * 0 10 15 20
 * 5 0 9 10
 * 6 13 0 12
 * 8 8 9 0
 */
public class Main {
    static int N;
    static int[][] map;
    static int[][] dp;
    static int INF = 100_000_000;

    static int dfs(int now, int bit) {
        if (bit == (1 << N) - 1) {
            if (map[now][0] == 0) {
                return INF;
            } else {
                return map[now][0];
            }
        }

        if (dp[now][bit] != -1) {
            return dp[now][bit];
        }

        dp[now][bit] = INF;

        for (int i = 1; i < N; i++) {
            if ((bit & (1 << i)) == 0 && map[now][i] != 0) {
                dp[now][bit] = Math.min(dp[now][bit], dfs(i, bit | (1 << i)) + map[now][i]);
            }
        }

        return dp[now][bit];
    }
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2098_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp = new int[N][1 << N];

        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
        }

        System.out.print(dfs(0, 1));
    }
}
