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
public class Main {
    static int V;
    static int E;
    static int K;
    static long ans;
    static long INF = Integer.MAX_VALUE * 10000L;
    static long[][] dp;
    static List<ArrayList<int[]>> graph = new ArrayList<>();

    static void dijkstra() {
        K = Math.min(E, K);
        PriorityQueue<long[]> queue = new PriorityQueue<>((o1, o2) -> Long.compare(o1[1], o2[1]));
        queue.add(new long[] {1, 0, 0}); // {정점, cost, k}
        dp[1][0] = 0;

        while (!queue.isEmpty()) {
            long[] v = queue.poll();

            if (dp[(int) v[0]][(int) v[2]] < v[1]) {
                continue;
            }

            if (v[0] == V) {
                if (v[2] == K) {
                    ans = v[1];
                    return;
                }

                continue;
            }

            for (int[] next : graph.get((int) v[0])) {
                if (dp[next[0]][(int) v[2]] > v[1] + next[1]) { // 지우지 않는 경우
                    dp[next[0]][(int) v[2]] = v[1] + next[1];
                    queue.add(new long[] {next[0], v[1] + next[1], v[2]});
                }

                if (v[2] < K) { // 간선을 지울 수 있는 경우
                    if (dp[next[0]][(int) v[2] + 1] > v[1]) { // 지우는 경우
                        dp[next[0]][(int) v[2] + 1] = v[1];
                        queue.add(new long[] {next[0], v[1], v[2] + 1});
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
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

            graph.get(a).add(new int[]{b, cost});
            graph.get(b).add(new int[]{a, cost});
        }

        dijkstra();
        System.out.println(ans);
    }
}
