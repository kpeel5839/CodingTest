import java.util.*;
import java.io.*;

// 2229 : 조짜기

/**
 * 예제
 * 10
 * 2 5 7 1 3 4 8 6 9 3
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2229_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] dp = new int[N + 1];
        int[] num = new int[N + 1];

        for (int i = 0; i < N; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = N - 2; i != -1; i--) {
            int low = num[i];
            int high = num[i];

            for (int j = i; j <= N - 1; j++) {
                if (num[j] < low) {
                    low = num[j];
                }

                if (num[j] > high) {
                    high = num[j];
                }

                dp[i] = Math.max(dp[i], (high - low) + dp[j + 1]);
            }
        }

//        System.out.println(Arrays.toString(dp));
        System.out.println(dp[0]);
    }
}
