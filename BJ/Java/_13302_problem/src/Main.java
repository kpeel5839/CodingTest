import java.util.*;
import java.io.*;

// 13302 : 리조트

/**
 * 13 5
 * 4 6 7 11 12
 */
public class Main {
    static int N;
    static int M;
    static int INF = 10000000;
    static int[] time = {1, 3, 5};
    static int[] cost = {10000, 25000, 37000};
    static int[] giveCoupon = {0, 1, 2};
    static boolean[] not = new boolean[101];
    static int[][] dp = new int[101][40];

    static int dfs(int day, int coupon) {
        if (N < day) {
            return 0;
        }

        if (dp[day][coupon] != INF) {
            return dp[day][coupon];
        }

        if (not[day]) {
            dp[day][coupon] = dfs(day + 1, coupon);
        } else {
            if (coupon >= 3) {
                dp[day][coupon] = Math.min(dp[day][coupon], dfs(day + 1, coupon - 3));
            }

            for (int i = 0; i < 3; i++) {
                dp[day][coupon] = Math.min(dp[day][coupon], cost[i] + dfs(day + time[i], coupon + giveCoupon[i]));
            }
        }

        return dp[day][coupon];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_13302_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        if (M != 0) {
            st = new StringTokenizer(br.readLine());
        }

        for (int i = 0; i < M; i++) {
            not[Integer.parseInt(st.nextToken())] = true;
        }

        for (int i = 0; i <= 100; i++) {
            Arrays.fill(dp[i], INF);
        }

        System.out.println(dfs(1, 0));
    }
}
