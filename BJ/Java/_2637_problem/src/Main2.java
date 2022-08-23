import java.util.*;
import java.io.*;

// 2637 : 장난감 조립

/**
 * 예제
 * 7
 * 8
 * 5 1 2
 * 5 2 2
 * 7 5 2
 * 6 5 2
 * 6 3 3
 * 6 4 4
 * 7 6 3
 * 7 4 5
 */
public class Main2 {
    static int N;
    static int M;
    static int[] need;
    static boolean[] notLeaf;
    static List<ArrayList<Edge>> graph = new ArrayList<>();

    static class Edge {
        int a;
        int cost;

        Edge(int a, int cost) {
            this.a = a;
            this.cost = cost;
        }
    }

    static void dfs(int cur, int cnt) {
        if (!notLeaf[cur]) {
            need[cur] += cnt;
            return;
        }

        for (Edge edge : graph.get(cur)) {
            dfs(edge.a, cnt * edge.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2637_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        need = new int[N + 1];
        notLeaf = new boolean[N + 1];

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            notLeaf[x] = true;
            graph.get(x).add(new Edge(y, cost));
        }

        dfs(N, 1);

        for (int i = 1; i <= N; i++) {
            if (!notLeaf[i]) {
                bw.write(i + " " + need[i] + "\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
