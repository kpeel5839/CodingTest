import java.util.*;
import java.io.*;

// 5626 : 제단

/**
 * 예제
 * 6
 * -1 -1 -1 2 -1 -1
 */
public class Main {
    static int N;
    static int MOD = 1_000_000_007;
    static int[] arr;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5626_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        boolean end = false;
        dp = new int[N][10000 / 2 + 1]; // 최대 5000 임, 사실 4999
        arr = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int v = Integer.parseInt(st.nextToken());
            if (Math.min(i, N - 1 - i) < v) {
                end = true;
            }
            arr[i] = v;
        }

        // 여기서부터 점화식 들어간다.
        // arr 값 -1 아니면 그 값 height 만 하고, 아니라면 다 하는데, Math.min(i, N - 1 - i) 를 이용해서 해야함, 거기까지만 가능하니까
        if (!end) {
            dp[0][0] = 1;
            for (int i = 1; i < N; i++) {
                int limit = Math.min(i, N - 1 - i); // 요 높이까지만 하면 됨

                if (arr[i] == -1) {
                    for (int j = 0; j <= limit; j++) {
                        for (int k = -1; k <= 1; k++) {
                            if (0 <= j + k) {
                                dp[i][j] = (dp[i][j] + dp[i - 1][j + k]) % MOD;
                            }
                        }
                    }
                } else {
                    for (int k = -1; k <= 1; k++) {
                        if (0 <= arr[i] + k) {
                            dp[i][arr[i]] = (dp[i][arr[i]] + dp[i - 1][arr[i] + k]) % MOD;
                        }
                    }
                }
            }
        }

        System.out.println(dp[N - 1][0] % MOD);
    }
}
