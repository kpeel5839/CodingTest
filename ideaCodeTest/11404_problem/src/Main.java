import java.util.*;
import java.io.*;

// 11404 : 플로이드

/*
-- 전제조건
그냥 100개의 도시와 각각의 도로들이 주어지는데,
모든 도시들에서 다른 도시로 가는 최소비용들을 출력하라
-- 틀 설계
플로이드 와샬 알고리즘을 사용해도 되지만,
솔직히 다익스트라를 조금 더 확장해서 사용해도 된다.
두 가지 방법을 진행해볼것이다.
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
    public static int v , e;
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        v = Integer.parseInt(input.readLine());
        e = Integer.parseInt(input.readLine());

        PriorityQueue<Edge> queue;
        int[][] dist = new int[v + 1][v + 1];
        List<ArrayList<Edge>> graph = new ArrayList<>();

        for(int i = 0; i <= v; i++){
            Arrays.fill(dist[i] , Integer.MAX_VALUE);
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(vertex1).add(new Edge(vertex2 , cost));
        }

        for(int i = 1; i <= v; i++){
            queue = new PriorityQueue<>((o1 , o2) -> Integer.compare(o1.cost , o2.cost));
            dist[i][i] = 0;
            queue.add(new Edge(i , 0));
            while(!queue.isEmpty()){
                Edge edge = queue.poll();

                if(dist[i][edge.idx] < edge.cost) continue;

                for(Edge innerEdge : graph.get(edge.idx)){
                    if(dist[i][innerEdge.idx] > innerEdge.cost + edge.cost){
                        dist[i][innerEdge.idx] = innerEdge.cost + edge.cost;
                        queue.add(new Edge(innerEdge.idx , dist[i][innerEdge.idx]));
                    }
                }
            }
        }
        for(int i = 1; i <= v; i++){
            for(int j = 1; j <= v; j++){
                if(dist[i][j] == Integer.MAX_VALUE) output.write(0 + " ");
                else output.write(dist[i][j] + " ");
            }
            output.write("\n");
        }

        output.flush();
        output.close();
    }
}
