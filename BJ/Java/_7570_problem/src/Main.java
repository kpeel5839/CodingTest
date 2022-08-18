import java.util.*;
import java.io.*;

// 7570 : 줄세우기

/**
 * 예제
 *
 * 5
 * 5 2 4 1 3
 */

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_7570_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[] dp = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        int max = 0;

        for (int i = 0; i < N; i++) {
            int input = Integer.parseInt(st.nextToken());

            dp[input] = dp[input - 1] + 1;
            max = Math.max(max, dp[input]);
        }

        System.out.println(N - max);
    }
}
