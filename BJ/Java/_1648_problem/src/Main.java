import java.util.*;
import java.io.*;

// 1648 : 격자판 채우기

/**
 * 예제
 * 3 6
 */
public class Main {
    static int N;
    static int M;
    static int size;
    static final int MOD = 9901;
    static int[][] dp;

    static void printRadix(int v) {
        String s = "";

        while (v != 0) {
            s = (v % 2) + s;
            v = v / 2;
        }

        if (s.length() != M) {
            while (s.length() != M) {
                s = "0" + s;
            }
        }

        System.out.println(s);
    }

    static int dfs(int idx, int status) {
        if (idx == size) {
            if (status == 0) { // 정확히 모두 채운 경우
                return 1;
            } else {
                return 0;
            }
        }

//        printRadix(status);

        if (dp[idx][status] != -1) {
            return dp[idx][status];
        }

        dp[idx][status] = 0;

        // 현재 위치에 놓여져 있는지 확인해야함
        if ((status & 1) == 0) { // 0 이 아니면 있는 것
            // 1 * 2 를 놓는 경우
            if ((status & 1 << 1) == 0 && (idx % M != (M - 1))) { // 두번째 칸에 있으면 안되니까 없는 것을 확인하고 넘어간다.
                dp[idx][status] = (dp[idx][status] + dfs(idx + 2, status >> 2)) % MOD;
            }

            // 2 * 1 을 놓는 경우
            dp[idx][status] = (dp[idx][status] + dfs(idx + 1, (status | (1 << M)) >> 1)) % MOD;
        } else {
            dp[idx][status] = (dp[idx][status] + dfs(idx + 1, status >> 1)) % MOD;
        }

        return dp[idx][status] % MOD;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1648_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        size = N * M;

        dp = new int[size][1 << M];
        for (int i = 0; i < size; i++) {
            Arrays.fill(dp[i], -1);
        }

        System.out.println(dfs(0, 0));
    }
}
