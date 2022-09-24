import java.util.*;
import java.io.*;

// 2449 : 전구

/**
 * 예제
 * 10 3
 * 1 1 2 3 3 3 2 2 1 1
 */
public class Main {
    static int N;
    static int K;
    static int[] arr;
    static int[][] dp;

    static int dfs(int left, int right) {
        if (left == right) {
            return 0;
        }

        if (dp[left][right] != -1) {
            return dp[left][right];
        }

        dp[left][right] = Integer.MAX_VALUE;

        for (int i = left; i < right; i++) {
            dp[left][right] = Math.min(dp[left][right], dfs(left, i) + dfs(i + 1, right) + ((arr[left] == arr[i + 1]) ? 0 : 1));
        }

        return dp[left][right];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2449_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        dp = new int[N][N];
        arr = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
            arr[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println();
    }
}
