import java.util.*;
import java.io.*;

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
        StringTokenizer st = new StringTokenizer(input.readLine());

        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        int[][] dist = new int[v + 1][v + 1];
        List<ArrayList<Edge>> vertexList = new ArrayList<>();
        PriorityQueue<Edge> queue;

        for(int i = 0; i <= v; i++){
            vertexList.add(new ArrayList<>());
        }

        for(int i = 0; i <= v; i++){
            for(int j = 0; j <= v; j++){
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            vertexList.get(vertex1).add(new Edge(vertex2 , cost));
            vertexList.get(vertex2).add(new Edge(vertex1 , cost)); //양방향 간선으로 들어가는 것
        }

        for(int i = 1; i <= v; i++){
            queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
            dist[i][i] = 0;
            queue.offer(new Edge(i , 0));
            while(!queue.isEmpty()){
                Edge edge = queue.poll();

                if(dist[i][edge.idx] < edge.cost){ // 이거 그냥 queue에서 계속 비용에 밀려서 뒤로가다가 필요 없어진 Edge들 버리는 행위임
                    continue;
                }

                for(int j = 0; j < vertexList.get(edge.idx).size(); j++){
                    Edge innerEdge = vertexList.get(edge.idx).get(j);
                    if(dist[i][innerEdge.idx] > edge.cost + innerEdge.cost){
                        dist[i][innerEdge.idx] = edge.cost + innerEdge.cost;
                        queue.offer(new Edge(innerEdge.idx , dist[i][innerEdge.idx]));
                    }
                }
            }
        }
        for(int i = 1; i <= v; i++){
            System.out.print("정점 "+i+" 에서의 모든 노드와의 최소 거리 : ");
            for(int j = 1; j <= v; j++){
                System.out.print(dist[i][j] + " ");
            }
            System.out.println();
        }
    }

}
