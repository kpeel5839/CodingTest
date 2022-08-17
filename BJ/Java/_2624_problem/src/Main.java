import java.util.*;
import java.io.*;

// 2624 : 동전 바꿔주기

/**
 * 예제
 *
 * 20
 * 3
 * 5 3
 * 10 2
 * 1 5
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2624_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());
        int[][] coin = new int[K + 1][2];

        for (int i = 1; i <= K; i++) {
            st = new StringTokenizer(br.readLine());

            coin[i][0] = Integer.parseInt(st.nextToken());
            coin[i][1] = Integer.parseInt(st.nextToken());
        }

        int[][] dp = new int[T + 1][K + 1];
        dp[0][0] = 1;

        for (int i = 1; i <= K; i++) {
            int cost = coin[i][0];

            for (int j = 0; j <= coin[i][1]; j++) {
                for (int money = 0; money <= T; money++) {
                    int nMoney = money + (cost * j);

                    if (nMoney > T) {
                        break;
                    }

                    dp[nMoney][i] += dp[money][i - 1];
                }
            }
        }

        System.out.print(dp[T][K]);
    }
}
