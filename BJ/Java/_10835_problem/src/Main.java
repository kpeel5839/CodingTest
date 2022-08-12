import java.util.*;
import java.io.*;

// 10835 : 카드게임
/**
 * 예제
 * 3
 * 3 2 5
 * 2 4 1
 */
public class Main {
    static int N;
    static int[] a;
    static int[] b;
    static int[][] dp;

    static int dfs(int left, int right) {
        if (left == N || right == N) {
            return 0;
        }

        if (dp[left][right] != -1) {
            return dp[left][right];
        }

        if (a[left] > b[right]) {
            dp[left][right] = Math.max(Math.max(dfs(left + 1, right), dfs(left + 1, right + 1))
                    , dfs(left, right + 1) + b[right]);
        } else {
            dp[left][right] = Math.max(dfs(left + 1, right), dfs(left + 1, right + 1));
        }

        return dp[left][right];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10835_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        dp = new int[N][N];
        a = new int[N];
        b = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
            a[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            b[i] = Integer.parseInt(st.nextToken());
        }

        System.out.print(dfs(0, 0));
    }
}