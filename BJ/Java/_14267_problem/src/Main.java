import java.util.*;
import java.io.*;

// 14267 : 회사 문화 1

/**
 * 예제
 * 5 3
 * -1 1 2 3 4
 * 2 2
 * 3 4
 * 5 6
 */
public class Main {
    static int V;
    static int E;
    static int[] add;
    static int[] res;
    static boolean[] visited;
    static List<ArrayList<Integer>> graph = new ArrayList<>();

    static void dfs(int c, int value) {
        value += add[c];
        res[c] = value;
        visited[c] = true;

        for (Integer next : graph.get(c)) {
            dfs(next, value);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_14267_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        add = new int[V + 1];
        res = new int[V + 1];
        visited = new boolean[V + 1];

        for (int i = 0; i <= V; i++) {
            graph.add(new ArrayList<>());
        }

        st = new StringTokenizer(br.readLine());

        for (int i = 1; i <= V; i++) {
            int parent = Integer.parseInt(st.nextToken());

            if (i != 1) {
                graph.get(parent).add(i);
            }
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            add[a] += cost;
        }

        for (int i = 1; i <= V; i++) {
            if (!visited[i]) {
                dfs(i, 0);
            }
        }

        for (int i = 1; i <= V; i++) {
            bw.write(res[i] + " ");
        }

        bw.flush();
        bw.close();
    }
}
