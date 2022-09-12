import java.util.*;
import java.io.*;

// 4781 : 사탕 가게

/**
 * 예제
 * 2 8.00
 * 700 7.00
 * 199 2.00
 * 3 8.00
 * 700 7.00
 * 299 3.00
 * 499 5.00
 * 0 0.00
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_4781_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int N;
        int COST;
        int[][] dp = new int[5000][10001]; // 그냥 미리 초기화해놓음
        int[][] candy = new int[5000][2]; // 이것도 최대로 선언해놓음

        while (true) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            COST = (int) (Double.parseDouble(st.nextToken()) * 100 + 0.5); // 부동 소수점의 오차 때문에 0.5 를 더해주어야지 100% 에서 정답처리를 받을 수 있음

            if (N == 0 && COST == 0) {
                break;
            }

//            System.out.println(N + " " + COST);

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                candy[i][0] = Integer.parseInt(st.nextToken());
                candy[i][1] = (int) (Double.parseDouble(st.nextToken()) * 100 + 0.5);
            }

            for (int i = 0; i < N; i++) {
                int calorie = candy[i][0];
                int cost = candy[i][1];

                for (int j = 1; j <= COST; j++) {
                    if (j < cost) {
                        if (i != 0) {
                            dp[i][j] = dp[i - 1][j]; // 그대로 가져오기
                        } else {
                            dp[i][j] = 0;
                        }
                    } else {
                        if (i == 0) {
                            dp[i][j] = dp[i][j - cost] + calorie;
                        } else {
                            dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - cost] + calorie);
                        }
                    }
                }
            }

            bw.write(dp[N - 1][COST] + "\n");
        }

        bw.flush();
        bw.close();
    }
}
