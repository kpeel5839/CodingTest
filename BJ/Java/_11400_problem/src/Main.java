import java.util.*;
import java.io.*;
import java.util.function.Function;

// 11400 : 단절선

/**
 * -- 전제 조건
 * 단절선을 모두 구해내는 프로그램을 작성하시오
 *
 * -- 틀 설계
 * 단절선 알고리즘을 사용해서 문제를 해결한다.
 */
public class Main {
    static int V;
    static int E;
    static int[] id;
    static int idValue = 0;
    static HashSet<Edge> edges = new HashSet<>();
    static List<ArrayList<Integer>> graph = new ArrayList<>();

    static class Edge {
        int s;
        int d;

        Edge(int s, int d) {
            this.s = s;
            this.d = d;
        }
    }

    static int dfs(int a, int p) {
        id[a] = ++idValue;
        int res = id[a];

        for (Integer next : graph.get(a)) {
            if (p != next) {
                if (id[next] == 0) {
                    int min = dfs(next, a);

                    if (id[a] < min) {
                        edges.add(new Edge(Math.min(a, next), Math.max(a, next)));
                    }

                    res = Math.min(res, min);
                } else {
                    res = Math.min(res, id[next]);
                }
            }
        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11400_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        id = new int[V + 1];

        for (int i = 0; i <= V; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        for (int i = 1; i <= V; i++) {
            if (id[i] == 0) {
                dfs(i, -1);
            }
        }

        List<Edge> res = new ArrayList<>();

        for (Edge edge : edges) {
            res.add(edge);
        }

        Collections.sort(res, (o1, o2) -> {
            if (o1.s == o2.s) {
                return o1.d - o2.d;
            }

            return o1.s - o2.s;
        });

        StringBuilder sb = new StringBuilder();

        sb.append(edges.size()).append("\n");

        for (Edge edge : res) {
            sb.append(edge.s + " " + edge.d).append("\n");
        }

        System.out.print(sb);
    }
}
