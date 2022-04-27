import java.util.*;
import java.io.*;

// 1967 : 트리의 지름
/*
--전제 조건
트리가 주어졌을 때 트리의 지름을 구하시오.

--틀 설계
트리라는 것은 특성상 , 한 노드까지 도달하는데에 경로가 단 한개만 존재한다.
그러한 특성으로 인해서 , 트리의 가중치가 양수로만 이루어져 있을 때에는 어떠한 노드에서 가장 먼 거리의 정점은
바로 본인이 갈 수 있는 경로의 끝에 위치한다 , 근데 이제 분기되어서 , 경로의 끝이 여러부분으로 나뉠 수 있는 데
그것은 cost를 계산해서 , 가장 높은 노드를 고르면 된다.
dfs로 가장 cost 가 높은 것들을 관리하면서 , cost 가 높은 곳에 도달하였을 떄의 idx들을 저장하면 ,
한 지점에서의 가장 먼 경로를 저장할 수 있다.
그리고 그 가장 먼 지점 , 끝단에 있는 지점에서 , 가장 멀리 있는 정점 , 그것이 바로 트리의 지름이라고 할 수 있다.
여기서도 트리의 특성상 , 맨 마지막 , 맨 끝단에 존재한다면 , 양수로만 가중치가 이루어져 있을 때에는 당연하게도 , 얘에서 가장 먼 지점,
그것이 가장 멀은 거리 , 즉 트리의 지름이 되는 것이다.

-- 결론
어떤 한 정점에서 시작하였을 때 가장 먼 정점은 , 트리의 특성으로 인해서 , 모든 정점들이 공유하게 된다
그 이유는 트리의 특성상 경로가 단 하나만 존재하기 때문이다 , 항상 가장 먼 정점은 , 양 끝단에 있다라는 것을 알면
한 정점에서 가장 먼 정점을 구하게 되면 , 다른 정점들도 그 정점을 가장 먼 정점으로 택하게 된다는 것이다.
그래서 그 가장 먼 정점에서 또 가장 먼 정점을 구하게 되면 , 트리에서 가장 길이가 긴 , 즉 지름이되게 되는 것이다.
 */
public class Main {
    public static List<ArrayList<Edge>> graph = new ArrayList<>();
    public static boolean[] visited;
    public static int n , max = 0,  maxIdx = 1;
    public static class Edge{
        int idx;
        int cost;
        public Edge(int idx , int cost){
            this.idx = idx;
            this.cost = cost;
        }
    }
    public static void dfs(int vertex , int cost){
        visited[vertex] = true;

        if(max < cost){
            max = cost;
            maxIdx = vertex;
        }
        for(Edge edge : graph.get(vertex)){
            if(!visited[edge.idx]){
                dfs(edge.idx , cost + edge.cost);
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());

        for(int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < n - 1; i++){ // 트리는 각 정점에 연결된 갓너이 단 하나만 존재한다. 루트 노드는 들어오는 간선이 없기 때문에 실질적인 간선은 n - 1 개가 들어온다.
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(vertex1).add(new Edge(vertex2 , cost));
            graph.get(vertex2).add(new Edge(vertex1 , cost));
        }

        visited = new boolean[n + 1];
        dfs(1 , 0);
        visited = new boolean[n + 1];
        dfs(maxIdx, 0);

        System.out.println(max);
    }
}
