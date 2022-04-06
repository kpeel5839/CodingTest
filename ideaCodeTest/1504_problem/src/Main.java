import java.util.*;
import java.io.*;

// 1504 : 특정한 최단 경로
/*
-- 전제조건
방향성 없는 그래프가 주어지고 , 1번 정점에서 N번 정점으로 가는 최단거리를 구한다.
근데 두 정점을 무조건 지나쳐야한다.
-- 틀 설계
평범한 다익스트라 알고리즘에서
Math.max(dist[1][v1] + dist[v1][v2] + dist[v2][n] , dist[1][v2] + dist[v2][v1] + dist[v1][n]) 으로 하면 된다.

-- 해맸던 점
방문하지 않은 곳을 처리하는 것은 처음해보는 것이라서 해맸고
그 다음에 가지 못하는 경우를 고려하지 않아서 해맸음
그 다음에 시간을 짧게 줄일 수 있었던 방법은 , 그냥 내가 가려는 목적지에 방문이 성공했냐 여부를 보고
다 성공했으면 , break; 하니까 훨씬 시간이 줄어듦

-- 재채점 이후 틀림
입력
4 2
1 2 1
1 3 1
2 3

출력(오답)
2
출력(정답)
-1

여기서 보면 , 내가 한 방식이 지금 이상하다.
뭐하러 이렇게 복잡하게 구성을 하였지?

-- 해맸던 점
복잡하게 구성했던 것은 교수님이 추천했던 방법을 사용했었어서 그랬던 것 같고
해맸던 점은 if(dist[start][innerEdge.idx] > edge.cost + innerEdge.cost) 로 구성을 했어야 했는데 , 현재 가려는 경로가 더 작아야 되니까,
근데 , 이렇게 안하고 if(dist[start][innerEdge.idx] < edge.cost + innerEdge.cost) 이렇게 해서 틀렸었다.
 */
public class Main {
    public static class Edge{
        int idx;
        int cost;
        public Edge(int idx , int cost){
            this.idx = idx;
            this.cost = cost;
        }
    }
    public static final int INF = 100_000_000; // 1억
    public static void main(String[] args) throws IOException{
        /*
        그냥 graph 전처리로 만들어주고
        dist[1] = 0 하고 시작해서
        v1 , v2 만 추가적으로 비용들 구해주면 된다.

        여기서 교수님께서 말씀해주신 방법을 한번 써보자.
         */
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int n = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        int[][] dist = new int[n + 1][n + 1];
        List<ArrayList<Edge>> graph = new ArrayList<>();

        for(int i = 0; i <= n; i++){
            graph.add(new ArrayList<>());
            Arrays.fill(dist[i] , INF); // 모든 정점 지나서 오더라도 800 * 1000 이라서 최대 그래봤자 800000 임 , 1억도 얘내한텐 개큼
        }

        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(vertex1).add(new Edge(vertex2 , cost));
            graph.get(vertex2).add(new Edge(vertex1 , cost));
        }

        st = new StringTokenizer(input.readLine());
        int v1 = Integer.parseInt(st.nextToken()), v2 = Integer.parseInt(st.nextToken());

        for(int i = 0; i < 3; i++){
            int start;
            if(i == 0) start = 1;
            else if(i == 1) start = v1;
            else start = v2;
            PriorityQueue<Edge> queue = new PriorityQueue<>((o1 , o2) -> Integer.compare(o1.cost , o2.cost));
            queue.add(new Edge(start , 0)); // 해당 지점에서의 시작
            dist[start][start] = 0;
            while(!queue.isEmpty()){
                // if 로 N에 도착하면 넘길려고 해보았는데 , 그러면 자칫하면 , 다른 경로에 대해서 파악을 못할 수도 있을 것 같아서 , 보류
                Edge edge = queue.poll();

                // dist[start][edge.idx] == edge.cost 인 경우는 그냥 넘어감 , 왜냐하면 본인 값일 수도 있으니까 , 근데 이미 넘어간다 ? 그러면 고려할 이유자체가 없음
                if(dist[start][edge.idx] < edge.cost) continue;

                for(Edge innerEdge : graph.get(edge.idx)){
                    if(dist[start][innerEdge.idx] > edge.cost + innerEdge.cost){
                        dist[start][innerEdge.idx] = edge.cost + innerEdge.cost;
                        queue.add(new Edge(innerEdge.idx , dist[start][innerEdge.idx]));
                    }
                }
            }
        }

        int result = Math.min(dist[1][v1] + dist[v1][v2] + dist[v2][n] , dist[1][v2] + dist[v2][v1] + dist[v1][n]);
        System.out.println(result >= INF ? -1 : result); // INF 보다 크다면 , INF 가 하나라도 껴있는 것임 , 짜피 다 양수이니까 INF 껴있으면 , 무조건 INF 보다 크거나 같음
    }
}

