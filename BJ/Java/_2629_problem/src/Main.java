import java.util.*;
import java.io.*;

// 2629 : 양팔 저울

/**
 * 예제
 * 2
 * 1 4
 * 2
 * 3 2
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2629_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int MAX = 15000;
        int[] weight = new int[N];
        boolean[][] dp = new boolean[N][MAX * 2 + 1];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            weight[i] = Integer.parseInt(st.nextToken());
        }

        dp[0][MAX] = true;
        dp[0][MAX + weight[0]] = true;
        dp[0][MAX - weight[0]] = true;

        for (int i = 1; i < N; i++) {
            for (int j = 0; j <= (MAX * 2); j++) {
                if (dp[i - 1][j]) {
                    for (int k = -1; k <= 1; k++) {
                        dp[i][j + (weight[i] * k)] = true;
                    }
                }
            }
        }

        int M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < M; i++) {
            int marble = Integer.parseInt(st.nextToken());

            if (MAX < marble) {
                bw.write("N ");
            } else {
                if (dp[N - 1][MAX + marble]) {
                    bw.write("Y ");
                } else {
                    bw.write("N ");
                }
            }
        }

        bw.flush();
        bw.close();
    }
}
