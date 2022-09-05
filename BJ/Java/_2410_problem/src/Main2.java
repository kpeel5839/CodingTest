import java.util.*;
import java.io.*;

// 2410 : 2의 멱수의 합

/**
 * 예제
 * 7
 */
public class Main2 {
    static int N;
    static int MOD = 1_000_000_000;
    static int[] dp;

    static int calRange(int v) {
        return (int) Math.floor(Math.log(v) / Math.log(2));
    }

    static int dfs(int a) {
        if (a == N) {
            return 1;
        }

        System.out.println(a);

        if (dp[a] != -1) {
            return dp[a];
        }

        dp[a] = 0;
        int range = calRange(N - a);
//        System.out.println("range : " + range);

        for (int i = 0; i <= range; i++) {
            System.out.println("plus : " + (1 << i));
            dp[a] = (dp[a] + dfs(a + (1 << i))) % MOD;
        }

        return dp[a] % MOD;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2410_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        dp = new int[N + 1];
        Arrays.fill(dp, -1);

        System.out.println(dfs(0) % MOD);
    }
}
