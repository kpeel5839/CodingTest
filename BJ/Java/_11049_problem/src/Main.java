import java.util.*;
import java.io.*;

// 11049 : 행렬 곱셈

/**
 * 3
 * 5 3
 * 3 2
 * 2 6
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11049_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[] p = new int[N + 1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            p[i] = Integer.parseInt(st.nextToken());
            p[i + 1] = Integer.parseInt(st.nextToken());
        }

        int[][] dp = new int[N][N];

        for (int dis = 1; dis < N; dis++) { // 길이는 i + 1
            for (int i = 0; i < N - dis; i++) {
                int j = i + dis;
                for (int k = i; k < j; k++) {
                    if (dp[i][j] == 0) {
                        dp[i][j] = dp[i][k] + dp[k + 1][j] + (p[i] * p[k + 1] * p[j + 1]);
                    } else {
                        dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + (p[i] * p[k + 1] * p[j + 1]));
                    }
                }
            }
        }

//        for (int i = 0; i < N; i++) {
//            System.out.println(Arrays.toString(dp[i]));
//        }

        System.out.println(dp[0][N - 1]);
    }
}
