import java.util.*;
import java.io.*;
import java.util.function.Function;

// 가장 먼 노드

/**
 * -- 해맸던 점
 * 문제를 잘 못 읽었었던 것이
 * 가장 끝단, 즉 leap 노드들의 개수를 세는 것인 줄 알았는데 (사실 그래프라서 리프 노드라는 개념이 있기는 힘듦 양방향이기도 하고)
 * 다시 잘 읽어보니까, 가장 먼 노드(최단 경로로 갔을 때, 가장 멀리 있는 노드) 의 개수를 찾는 것이였음
 * 즉, 최단 경로로 갔을 때, 가장 먼 노드의 간선 수를 가진 노드들이 많이 존재하면 그만큼의 개수를 출력하면 된다.
 *
 * 그렇기 때문에, 가장 먼 노드를 찾고, 그 노드의 간선 개수를 이용해서 다 찾으면 된다.
 */
class Solution {
    public static int answer = 0;
    public static boolean[] visited;
    public static int[] depth; // 여기까지 도달하는데 걸린 비용
    public static int maxDepth = 0;
    public static List<ArrayList<Integer>> graph = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int n = fun.apply(br.readLine());
        int m = fun.apply(br.readLine());
        int[][] edges = new int[m][2];

        for (int i = 0; i < m; i++) { // 간선 받기
            String[] input = br.readLine().split(" ");

            edges[i][0] = fun.apply(input[0]);
            edges[i][1] = fun.apply(input[1]);
        }

        System.out.println(solution(n, edges));
    }

    public static void bfs(int vertex) {
        /*
        이제, 더 이상 이동할 수 없을 때,
        즉 이전에 지나온 곳을 제외하면, 더 이상 갈 곳이 없을 때, 그게 말단 노드라고 생각하면 되고,
        그럴 때마다 answer++ 를 해주면 된다.
        그리고 가장 멀리 떨어진지를 알기 위해서, bfs 로 진행해야 함
        dfs 는 깊이 상관없이 쭉쭉 먼저 뻗어 나가고, 그 다음 것을 진행하기 때문에,
        나중 가면 가장 먼 노드가 아님에도 불구하고 가장 먼 노드처럼 취급될 수 있음 (갈 곳이 없어서) (애초에, 최단 경로로 갔을 때, 가장 먼 노드가 주제임)
        */
        Queue<int[]> queue = new LinkedList<>();

        queue.add(new int[] {0, vertex}); // point[0] 은 depth 를 의미함
        visited[vertex] = true;

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            maxDepth = Math.max(maxDepth, point[0]); // depth 최대값 뽑아내기

            for (Integer next : graph.get(point[1])) {
                if (!visited[next]) { // 방문 처리가 되어 있지 않을 때                
                    visited[next] = true; // 방문처리를 해줌                
                    depth[next] = point[0] + 1;
                    queue.add(new int[] {depth[next], next}); // 방문한 노드로 부터 시작할 수 있도록 queue 에다가 삽입                
                }
            }
        }
    }

    public static int solution(int n, int[][] edge) {
        /*
        Level 3 이 이정도였나?
        이 문제는 너무 쉬운 듯 하다.
        */
        visited = new boolean[n + 1];
        depth = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edge.length; i++) { // edge 정보를 이용해서 graph 형성
            int[] detail = edge[i];

            int a = detail[0];
            int b = detail[1]; // 연결된 두 노드

            graph.get(a).add(b);
            graph.get(b).add(a); // 양방향으로 연결해줌
        }

        bfs(1); // 1번 노드부터 bfs 를 시작    

        for (int i = 1; i <= n; i++) {
            if (maxDepth == depth[i]) {
                answer++;
            }
        }

        return answer;
    }
}