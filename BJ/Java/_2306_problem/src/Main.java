import java.util.*;
import java.io.*;

// 2306 : 유전자

/**
 * 예제
 * acattgagtc
 *
 * 되게 무빙워크 근무하면서 생각했는데
 * 옛날 같았으면 절대 못풀었을 문제였음에도, 풀었다는 것이 굉장히 재밌었다.
 */
public class Main {
    static String s;
    static int N;
    static int[][] dp;

    static void cal(int i, int j, int dis) {
        // i == 시작점, j == 끝점, dis == 길이
        char a = s.charAt(i);
        char b = s.charAt(j);

        if (dis == 2) {
            if ((a == 'g' && b == 'c') || (a == 'a' && b == 't')) { // 두 글자인 애들은 맞는 경우에 2를 넣어준다.
                dp[i][j] = 2;
            }
        } else {
            int max = 0;

            if ((a == 'g' && b == 'c') || (a == 'a' && b == 't')) { // 두 글자인 애들은 맞는 경우에 2를 넣어준다.
                max = Math.max(max, dp[i + 1][j - 1] + 2);
            }

            for (int k = i; k < j; k++) {
                max = Math.max(max, dp[i][k] + dp[k + 1][j]);
            }

            dp[i][j] = max;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2306_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        s = br.readLine();
        N = s.length();
        dp = new int[N][N];

        for (int i = 2; i <= N; i++) {
            for (int j = 0; j < N - i + 1; j++) { // 길이가 2인 애들부터 실행하고, 처음에 길이가 2 인 애들은 N - 2 까지 방문해야 하니까 N - 2 + 1 까지만 진행하면 된다.
                cal(j, j + i - 1, i);
            }
        }

        System.out.print(dp[0][N - 1]);
    }
}
