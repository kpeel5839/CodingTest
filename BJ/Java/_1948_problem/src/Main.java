import java.util.*;
import java.io.*;

// 1948 : 임계 경로

/**
 * 예제
 *
 * 7
 * 9
 * 1 2 4
 * 1 3 2
 * 1 4 3
 * 2 6 3
 * 2 7 5
 * 3 5 1
 * 4 6 4
 * 5 6 2
 * 6 7 5
 * 1 7
 *
 * 역시 이번에도
 * 역추적 하는 과정에 있어서, visited 처리와, 이미 방문한 지점이라도 간선은 세어주었어야 했었는데
 * 그 부분을 또 실수했었음
 */
public class Main {
    static int V;
    static int E;
    static int S;
    static int D;
    static int maxCost = 0;
    static int cnt = 0;
    static int[] dist;
    static boolean[] visited;
    static List<ArrayList<Edge>> graph = new ArrayList<>();
    static List<ArrayList<Edge>> tracking = new ArrayList<>();

    static class Edge {
        int a;
        int cost;

        Edge(int a, int cost) {
            this.a = a;
            this.cost = cost;
        }
    }

    static void bfs() {
        // dist 를 초기화해준다.
        // 처음에는 S 로 시작해준다.
        Queue<int[]> queue = new LinkedList<>();
        dist[S] = 0;
        queue.add(new int[] {S, 0});

        while (!queue.isEmpty()) {
            int[] v = queue.poll();

            if (dist[v[0]] > v[1]) { // 현재 dist 가 내 경로값보다 크면? 실행할 이유가 전혀없다.
                continue;
            }

            for (Edge edge : graph.get(v[0])) {
                if (dist[edge.a] < v[1] + edge.cost) { // 목적지의 값이 내가 거기로 가는 것보다 작으면, 실행해준다.
                    dist[edge.a] = v[1] + edge.cost;
                    queue.add(new int[] {edge.a, dist[edge.a]});
                }
            }
        }

        maxCost = dist[D];
    }

    static void track() {
        // 얻은 maxCost 를 가지고 역으로 추적해가야함
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {D, maxCost});
        visited[D] = true;

        while (!queue.isEmpty()) {
            int[] v = queue.poll();

            for (Edge edge : tracking.get(v[0])) {
                if ((v[1] - edge.cost == dist[edge.a])) { // 아직 방문하지 않았으며, 여기로 온게 맞는 경우
                    cnt++;

                    if (!visited[edge.a]) {
                        queue.add(new int[] {edge.a, v[1] - edge.cost});
                    }

                    visited[edge.a] = true; // vistied 처리 안해줬음
                }
            }
        }
    }

    public static void main(String[] args) throws IOException{
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1948_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        V = Integer.parseInt(br.readLine());
        E = Integer.parseInt(br.readLine());
        dist = new int[V + 1];
        visited = new boolean[V + 1];

        for (int i = 0; i <= V; i++) {
            dist[i] = -1;
            graph.add(new ArrayList<>());
            tracking.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            graph.get(a).add(new Edge(b, c));
            tracking.get(b).add(new Edge(a, c)); // 역으로 추적할 수 있는 graph 생성
        }

        st = new StringTokenizer(br.readLine());
        S = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        bfs();
        track();

        System.out.print(maxCost + "\n" + cnt);
    }
}
