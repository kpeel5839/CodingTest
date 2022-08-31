import java.util.*;
import java.io.*;

// 1162 : 도로 포장

/**
 * 예제
 * 4 4 1
 * 1 2 10
 * 2 4 10
 * 1 3 1
 * 3 4 100
 */
public class Dynamic {
    static int V;
    static int E;
    static int K;
    static long INF = Integer.MAX_VALUE * 10000L;
    static long[][] dp;
    static List<ArrayList<int[]>> graph = new ArrayList<>();

    static long dfs(int v, int k) {
        if (v == V) {
            return 0;
        }

        if (dp[v][k] != INF) {
            return dp[v][k];
        }

        dp[v][k] = INF - 10;

        for (int[] next : graph.get(v)) {
            if (k < K) {
                dp[v][k] = Math.min(dp[v][k], dfs(next[0], k + 1));
                dp[v][k] = Math.min(dp[v][k], dfs(next[0], k) + (long) next[1]);
            } else { // k 를 더 이상 쓸 수 없는 경우
                dp[v][k] = Math.min(dp[v][k], dfs(next[0], k) + (long) next[1]);
            }
        }

        return dp[v][k];
    }

    public static void main(String[] args) throws IOException{
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1162_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        dp = new long[V + 1][K + 1];

        for (int i = 0; i <= V; i++) {
            graph.add(new ArrayList<>());
            Arrays.fill(dp[i], INF);
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(a).add(new int[] {b, cost});
            graph.get(b).add(new int[] {a, cost});
        }

//        System.out.println(INF);
        System.out.println(dfs(1, 0));

//        for (int i = 0; i <= K; i++) {
//            for (int j = 1; j <= V; j++) {
//                System.out.print(dp[j][i] + " ");
//            }
//            System.out.println();
//        }
    }
}
