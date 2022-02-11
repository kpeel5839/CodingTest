import java.util.*;
import java.io.*;

// 1916 : 최소비용
/*
--전제 조건
첫째줄 두번째 줄에 vertex 수와 , 간선의 수가 주어진다.
그 다음에 간선들이 주어지고
맨 마지막에 출발지와 도착지가 주어진다.
--틀 설계
다익스트라 알고리즘을 사용해서 제한 시간 내에 성공하면된다.
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
        StringTokenizer st;

        int v = Integer.parseInt(input.readLine());
        int e = Integer.parseInt(input.readLine());

        int[] dist = new int[v + 1];
        List<ArrayList<Edge>> graph = new ArrayList<>();

        for(int i = 0; i <= v; i++){
            dist[i] = Integer.MAX_VALUE;
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());
            int sta = Integer.parseInt(st.nextToken());
            int des = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(sta).add(new Edge(des , cost));
        }

        st = new StringTokenizer(input.readLine());

        int sta = Integer.parseInt(st.nextToken());
        int des = Integer.parseInt(st.nextToken());

//        dist[sta] = 0;
        PriorityQueue<Edge> queue = new PriorityQueue<>((o1 , o2) -> Integer.compare(o1.cost , o2.cost));
        queue.add(new Edge(sta , 0));

        while(!queue.isEmpty()){
            Edge edge = queue.poll();

            if(dist[edge.idx] != Integer.MAX_VALUE) continue;

            dist[edge.idx] = edge.cost;
            if(dist[des] != Integer.MAX_VALUE) break;

            for(Edge innerEdge : graph.get(edge.idx)){
                if(dist[innerEdge.idx] > innerEdge.cost + edge.cost){
//                    dist[innerEdge.idx] = innerEdge.cost + edge.cost;
                    queue.add(new Edge(innerEdge.idx , innerEdge.cost + edge.cost));
                }
            }
        }

        System.out.println(dist[des]);
    }
}


