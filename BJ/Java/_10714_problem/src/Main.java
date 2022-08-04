import java.util.*;
import java.io.*;

/**
 * 예제
 *
 * 5
 * 2
 * 8
 * 1
 * 10
 * 9
 *
 * 와 진짜 설계부터 끝까지 완벽하게 맞았음
 */
public class Main {
    static int N;
    static long ans = 0;
    static long[] arr;
    static long[][] dp;

    static int convert(int v) {
        return v < 0 ? N - 1 : v % N;
    }

    static long dfs(int left, int right, int turn) {
        if (left == right) { // JOI 군의 턴이면 반환, 아니면 0을 반환
            return turn % 2 == 0 ? 0 : arr[left];
        }

        if (dp[left][right] != -1) {
            return dp[left][right];
        }

        if (turn % 2 == 0) { // JOI 양의 턴
            int nl = left;
            int nr = right;

            if (arr[left] < arr[right]) { // right 를 선택하는 경우
                nr = convert(right - 1);
            } else {
                nl = convert(left + 1);
            }

            return dp[left][right] = dfs(nl, nr, turn + 1); // Ai 는 모두 다른값이기에 같은 경우를 고려하지 않아도 된다.
        } else { // JOI 군의 턴
            return dp[left][right] = Math.max(arr[left] + dfs(convert(left + 1), right, turn + 1)
                    , arr[right] + dfs(left, convert(right - 1), turn + 1));
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10714_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new long[N];
        dp = new long[N][N];

        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
            arr[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 0; i < N; i++) {
            ans = Math.max(ans, dfs(convert(i + 1), convert(i - 1), 2) + arr[i]);
        }

        System.out.print(ans);
    }
}
