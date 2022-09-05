import java.util.*;
import java.io.*;

// 2718 : 타일 채우기

/**
 * 예제
 * 3
 * 2
 * 3
 * 7
 */
public class Main {
    static int N;
    static int[][] dp;

    static int dfs(int cur, int bit) {
        if (cur < 0) {
            return 0;
        }

        if (cur == 0) {
            return bit == 0 ? 1 : 0;
        }

        if (dp[cur][bit] != -1) {
            return dp[cur][bit];
        }

        if (bit == 0) {
            dp[cur][bit] = dfs(cur - 2, 0) + dfs(cur - 1, 0) + dfs(cur - 1, 3) + dfs(cur - 1, 9) + dfs(cur - 1, 12);
        } else if (bit == 3) {
            dp[cur][bit] = dfs(cur - 1, 12) + dfs(cur - 1, 0);
        } else if (bit == 6) {
            dp[cur][bit] = dfs(cur - 1, 9);
        } else if (bit == 9) {
            dp[cur][bit] = dfs(cur - 1, 6) + dfs(cur - 1, 0);
        } else if (bit == 12) {
            dp[cur][bit] = dfs(cur - 1, 3) + dfs(cur - 1, 0);
        }

        return dp[cur][bit];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2718_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            N = Integer.parseInt(br.readLine());
            dp = new int[N + 1][13];

            for (int i = 0; i <= N; i++) {
                Arrays.fill(dp[i], - 1);
            }

            bw.write(dfs(N, 0) + "\n");
        }

        bw.flush();
        bw.close();
    }
}
