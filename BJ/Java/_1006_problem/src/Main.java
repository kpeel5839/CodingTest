import java.util.*;
import java.io.*;

// 1006 : 습격자 초라기

/**
 * 예제
 * 1
 * 8 100
 * 70 60 55 43 57 60 44 50
 * 58 40 47 90 45 52 80 40
 */
public class Main {
    static int N;
    static int W;
    static int[][] arr;
    static int[][] dp;

    static void solve() {
        dp[1][1] = 1;
        dp[1][2] = 1;

        for (int i = 2; i <= N; i++) {
            int up = arr[i - 1][0] + arr[i][0] <= W ? 1 : 2;
            int down = arr[i - 1][1] + arr[i][1] <= W ? 1 : 2;
            int ver = arr[i][0] + arr[i][1] <= W ? 1 : 2;

            dp[i][0] = Math.min(Math.min(Math.min(dp[i - 1][0] + ver, dp[i - 2][0] + up + down), dp[i - 1][1] + down + 1), dp[i - 1][2] + up + 1);
            dp[i][1] = Math.min(dp[i - 1][0] + 1, dp[i - 1][2] + up);
            dp[i][2] = Math.min(dp[i - 1][0] + 1, dp[i - 1][1] + down);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1006_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            dp = new int[N + 1][3];
            arr = new int[N + 1][2];

            for (int i = 0; i < 2; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= N; j++) {
                    arr[j][i] = Integer.parseInt(st.nextToken());
                }
            }

            int INF = 100000;
            int ans = Integer.MAX_VALUE;
            int up = arr[1][0];
            int down = arr[1][1];

            // 첫행이 연결이 안된 경우
            dp[1][0] = arr[1][0] + arr[1][1] <= W ? 1 : 2;
            solve();
            ans = Math.min(ans, dp[N][0]);

            // 첫행이 위만 연결된 경우
            if (arr[1][0] + arr[N][0] <= W) {
                dp[1][0] = 2;
                arr[1][0] = INF;
                solve();
                ans = Math.min(ans, dp[N][2]);
                arr[1][0] = up;
            }

            // 첫행이 아래만 연결된 경우
            if (arr[1][1] + arr[N][1] <= W) {
                dp[1][0] = 2;
                arr[1][1] = INF;
                solve();
                ans = Math.min(ans, dp[N][1]);
                arr[1][1] = down;
            }

            // 첫행이 둘다 연결된 경우
            if (arr[1][0] + arr[N][0] <= W && arr[1][1] + arr[N][1] <= W) {
                dp[1][0] = 2;
                arr[1][0] = INF;
                arr[1][1] = INF;
                solve();
                ans = Math.min(ans, dp[N - 1][0]);
                arr[1][0] = up;
                arr[1][1] = down;
            }

            if (N == 1) {
                ans = arr[1][0] + arr[1][1] <= W ? 1 : 2;
            }

            bw.write(ans + "\n");
        }

        bw.flush();
        bw.close();
    }
}
