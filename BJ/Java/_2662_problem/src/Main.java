import java.util.*;
import java.io.*;

// 2662 : 기업투자

/**
 * 예제
 * 4 2
 * 1 5 1
 * 2 6 5
 * 3 7 9
 * 4 10 15
 */
public class Main {
    static int N;
    static int M;
    static int[][] dp;
    static int[][] invest;
    static int[][] track;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2662_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        dp = new int[N + 1][M];
        invest = new int[N + 1][M];
        track = new int[N + 1][M];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            st.nextToken(); // 버려버리깃
            for (int j = 0; j < M; j++) {
                invest[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int j = 1; j <= N; j++) {
            dp[j][0] = invest[j][0];
            track[j][0] = j;
        }

        for (int i = 1; i < M; i++) {
            for (int j = 1; j <= N; j++) {
                int select = 0;

                for (int k = j; k != -1; k--) {
                    if (dp[j][i] < dp[j - k][i - 1] + invest[k][i]) {
                        select = k;
                        dp[j][i] = dp[j - k][i - 1] + invest[k][i];
                    }
                }

                track[j][i] = select;
            }
        }

        bw.write(dp[N][M - 1] + "\n");
        Stack<Integer> stack = new Stack<>();

        for (int i = M - 1; i != -1; i--) {
            stack.add(track[N][i]);
            N -= track[N][i];
        }

        while (!stack.isEmpty()) {
            bw.write(stack.pop() + " ");
        }

        bw.flush();
        bw.close();
    }
}
