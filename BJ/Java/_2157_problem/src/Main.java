import java.util.*;
import java.io.*;

/**
 * 예제
 * 3 3 5
 * 1 3 10
 * 1 2 5
 * 2 3 3
 * 1 3 4
 * 3 1 100
 *
 * 반례
 * 3 3 2
 * 1 2 3
 * 1 3 1
 *
 * 시작하자말자, 도착지에 도달하는 경우가 있음
 *
 * 내가 잘못 처리했던 부분은, 시작하자말자 도달하는 경우가 문제가 아니라
 * 제대로된 목적지가 아니더라도, 0을 반환하는 경우가 있었다라는 것이다.
 *
 * 그래서 아예 dp 를 Integer.MIN_VALUE 로 초기화하여서, 제대로 된 경우가 존재할 때, 답으로 채택될 수 없도록 하였음
 * 만일 도착지점에 도달하지 못하는 경우가 있다면?
 *
 * 현재 내 코드는 통과할 수 없음
 */
public class Main {
    static int N;
    static int M;
    static int E;
    static int[][] dp;
    static List<ArrayList<int[]>> graph = new ArrayList<>();

    static int dfs(int depth, int v) {
        if (v == N) {
            return 0;
        }

        if (depth == M) { // 끝 지점에 도달하지 못했는데 이미 M 개를 거쳐온 경우
            return Integer.MIN_VALUE;
        }

        if (dp[v][depth] != Integer.MIN_VALUE) {
            if (dp[v][depth] == 0) { // 0 인 경우는 가지 못하는 경우니까 의미없는 숫자가 되어야함
                return Integer.MIN_VALUE;
            }

            return dp[v][depth];
        }

        for (int[] next : graph.get(v)) {
            dp[v][depth] = Math.max(dp[v][depth], next[1] + dfs(depth + 1, next[0]));
        }

        return dp[v][depth];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2157_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        dp = new int[N + 1][M];

        for (int i = 0; i <= N; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            if (a < b) {
                graph.get(a).add(new int[] {b, c});
            }
        }

//        int ans = 0;
//        for (int[] next : graph.get(1)) {
//            ans = Math.max(ans, next[1] + dfs(2, next[0]));
//        }

        System.out.print(dfs(1, 1));
    }
}
