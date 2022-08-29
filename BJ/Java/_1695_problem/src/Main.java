import java.util.*;
import java.io.*;

// 1695 : 팰린드롬 만들기

/**
 * 예제
 * 5
 * 1 2 3 4 2
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1695_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[] number = new int[N];
        int[][] dp = new int[N][N];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            number[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < N - 1; i++) { // size == 2 인 것들만 처리
            int j = i + 1;

            if (number[i] != number[j]) {
                dp[i][j] = 1;
            }
        }

        for (int i = 2; i < N; i++) {
            for (int j = 0; j + i < N; j++) {
                int des = j + i;

                int min = Integer.MAX_VALUE;
                if (number[j] == number[des]) {
                    min = Math.min(min, dp[j + 1][des - 1]);
                }

                min = Math.min(min, 1 + dp[j + 1][des]);
                min = Math.min(min, 1 + dp[j][des - 1]); // 이것은 무조건 실행됨
                dp[j][des] = min;
            }
        }

        System.out.println(dp[0][N - 1]);
    }
}
