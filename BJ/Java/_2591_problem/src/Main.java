import java.util.*;
import java.io.*;

// 2591 : 숫자 카드

/**
 * 예제
 * 27123
 */
public class Main {
    static int N;
    static char[] p;
    static int[] dp;

    static int dfs(int depth) {
        if (depth == N) {
            return 1;
        }

        if (dp[depth] != -1) {
            return dp[depth];
        }

        dp[depth] = 0;

        for (int i = 1; i <= 34; i++) {
            String s = i + ""; // int to String
            int remain = N - depth; // 남은 글자

            if (remain >= s.length()) { // 가능할 때
                boolean equals = true;
                for (int j = 0; j < s.length(); j++) {
                    if (p[depth + j] != s.charAt(j)) {
                        equals = false;
                    }
                }

                if (equals) { // 가능할 때에만 실행
                    dp[depth] += dfs(depth + s.length());
                }
            }
        }

        return dp[depth];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2591_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        p = br.readLine().toCharArray();
        N = p.length;
        dp = new int[N];
        Arrays.fill(dp, -1);

        System.out.println(dfs(0));
    }
}
