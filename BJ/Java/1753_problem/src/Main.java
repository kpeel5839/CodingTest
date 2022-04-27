import java.util.*;
import java.io.*;

// 1753 : 최단경로
/*
-- 전제조건
그래프가 주어졌을 때 , (간선 및 정점)
그냥 시작 지점에서 부터 최단 지점을 출력하라
-- 틀 설계
다익스트라 알고리즘 써서 그냥 처음부터 끝까지 출력하면 됨
방문하지 않은 것을 넣는 것과 , 그냥 비교하면서 넣는 것 속도차이가 거의 없지만 , 비교연산이 약간의 우세를 보인다.
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
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int n = Integer.parseInt(st.nextToken()) , e = Integer.parseInt(st.nextToken());
        int start = Integer.parseInt(input.readLine());
        PriorityQueue<Edge> queue = new PriorityQueue<>((o1 , o2) -> Integer.compare(o1.cost , o2.cost));
        int[] dist = new int[n + 1];
        List<ArrayList<Edge>> graph = new ArrayList<>();

//        System.out.println("start : " + start);
        for(int i = 0; i <= n; i++){
            dist[i] = Integer.MAX_VALUE;
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(vertex1).add(new Edge(vertex2 , cost));
        }

        queue.add(new Edge(start , 0));
        dist[start] = 0;
        while(!queue.isEmpty()){
            Edge edge = queue.poll();
            if(dist[edge.idx] < edge.cost) continue;
            for(Edge innerEdge : graph.get(edge.idx)){
                if(dist[innerEdge.idx] > innerEdge.cost + edge.cost){
                    dist[innerEdge.idx] = innerEdge.cost + edge.cost;
                    queue.add(new Edge(innerEdge.idx , dist[innerEdge.idx]));
                }
            }
        }

        for(int i = 1; i <= n; i++){
            if(dist[i] == Integer.MAX_VALUE) output.write("INF" + "\n");
            else output.write(dist[i] +  "\n");
        }

        output.flush();
        output.close();
    }
}
