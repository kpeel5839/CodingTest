import java.util.*;
import java.io.*;

// 2698 : 인접한 비트의 개수

/**
 * 10
 * 5 2
 * 20 8
 * 30 17
 * 40 24
 * 50 37
 * 60 52
 * 70 59
 * 80 73
 * 90 84
 * 100 90
 */
public class Main {
    static int N;
    static int K;
    static int[][][] dp;

    static int dfs(int depth, int sum, int cur) {
        if (depth == N - 1) {
            if (sum == K) {
                return 1;
            } else {
                return 0;
            }
        }

        if (K < sum) {
            return 0;
        }

        if (dp[depth][sum][cur] != -1) {
            return dp[depth][sum][cur];
        }

        dp[depth][sum][cur] = 0;
        dp[depth][sum][cur] += dfs(depth + 1, sum, 0);
        dp[depth][sum][cur] += dfs(depth + 1, cur == 1 ? sum + 1 : sum, 1);

        return dp[depth][sum][cur];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2698_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            dp = new int[N][K + 1][2];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j <= K; j++) {
                    dp[i][j][0] = -1;
                    dp[i][j][1] = -1;
                }
            }

            bw.write(dfs(0, 0, 0) + dfs(0, 0, 1) + "\n");
        }

        bw.flush();
        bw.close();
    }
}
