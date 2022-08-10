import java.util.*;
import java.io.*;

// 1328 : 고층 빌딩

/**
 * 예제
 * 3 2 2
 *
 * 답을 봐버렸다..
 * 막상 알면 쉬운데, 발상의 전환이 상당한 문제인 것 같다.
 */
public class Main {
    static int N;
    static int L;
    static int R;
    static long[][][] dp;
    static int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1328_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        dp = new long[N + 1][L + 1][R + 1];

        dp[1][1][1] = 1;
        for (int k = 2; k <= N; k++) {
            for (int i = 1; i <= L; i++) {
                for (int j = 1; j <= R; j++) {
                    dp[k][i][j] = (((dp[k - 1][i - 1][j] + dp[k - 1][i][j - 1]) % MOD) + dp[k - 1][i][j] * (k - 2)) % MOD;
                }
            }
        }

        System.out.println(dp[N][L][R]);
    }
}
