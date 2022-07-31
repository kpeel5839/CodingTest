import java.util.*;
import java.io.*;

public class Main {
    static int V;
    static int E;
    static int vMax = 100000;
    static int eMax = 300000;
    static long[] dist = new long[vMax + 1];
    static long[] paper = new long[vMax + 1];
    static boolean[] visited = new boolean[vMax + 1];
    static List<ArrayList<Edge>> graph;
    static Queue<Integer> max;

    static class Edge {
        int b;
        int kind;

        Edge(int b, int kind) {
            this.b = b;
            this.kind = kind;
        }
    }

    static long dijkstra() {
        LinkedList<long[]> queue = new LinkedList<>();
        queue.add(new long[] {1, 0});
        dist[1] = 0;

        while (!queue.isEmpty()) {
            long[] v = queue.poll();

            if (dist[(int) v[0]] > v[1]) {
                continue;
            }

            for (Edge edge : graph.get((int) v[0])) {
                if (edge.kind == 1) { // 단방향 간선일 경우
                    if (dist[edge.b] < v[1] + paper[(int) v[0]]) { // 현재의 비용에 paper 비용을 더하면서 넘어가는 것보다 dist[edge.b] 가 작다면 가능한 경우이니 가준다.
                        dist[edge.b] = v[1] + paper[(int) v[0]];
                        queue.add(new long[] {edge.b, dist[edge.b]});
//                        queue.addFirst(new long[] {edge.b, dist[edge.b]}); // 나와서 생각해보니까 이렇게 하면 걍 dfs 임, 시간 개차반 나는 경우도 존재함, 근데 시험장의 예제들은 시간들이 다 단축되서 현혹됐었ㅇ므
                    }
                } else { // 양방향 간선일 경우
                    if (dist[edge.b] < v[1]) { // 현재 v[1] 이 더 크지 않은이상 넘어갈 이유가 전혀없음
                        dist[edge.b] = v[1];
                        queue.add(new long[] {edge.b, dist[edge.b]});
//                        queue.addFirst(new long[] {edge.b, dist[edge.b]}); // 나와서 생각해보니까 이렇게 하면 걍 dfs 임, 시간 개차반 나는 경우도 존재함
                    }
                }
            }
        }

        return dist[V];
    }

    static void bfs(int a) {
        Queue<Integer> queue = new LinkedList<>();
        max = new LinkedList<>();
        queue.add(a);
        max.add(a);
        visited[a] = true;
        long maxValue = paper[a];

        while (!queue.isEmpty()) {
            int v = queue.poll();

            for (Edge edge : graph.get(v)) {
                if (!visited[edge.b] && edge.kind == 2) {
                    maxValue = Math.max(maxValue, paper[edge.b]);
                    max.add(edge.b);
                    queue.add(edge.b);
                    visited[edge.b] = true;
                }
            }
        }

        while (!max.isEmpty()) {
            paper[max.poll()] = maxValue;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_22_sds_summer_pro_exam/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        int index = 0;

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());

            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            Arrays.fill(dist, -1);
            Arrays.fill(visited, false);
            graph = new ArrayList<>();

            st = new StringTokenizer(br.readLine());

            graph.add(new ArrayList<>());
            for (int i = 1; i <= V; i++) { // 여기서도 실수했었네, V 까지 받았네 i <= V 까지 받았어야 했는데, 근데 사실 V 번째의 종이의 내용은 쓰일일이 절대 없기 떄문에, 중요한 실수는 아닌 것 같다, 단지 내가 실수를 했다는 사실을 발견했을 뿐
                graph.add(new ArrayList<>());
                paper[i] = Long.parseLong(st.nextToken());
            }

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                if (c == 1) {
                    graph.get(a).add(new Edge(b, c));
                } else {
                    graph.get(a).add(new Edge(b, c));
                    graph.get(b).add(new Edge(a, c));
                }
            }

            for (int i = 1; i <= V; i++) {
                if (!visited[i]) {
                    bfs(i);
                }
            }

            bw.write("#" + ++index + " " + dijkstra() + "\n");
        }

        bw.flush();
        bw.close();
    }
}
