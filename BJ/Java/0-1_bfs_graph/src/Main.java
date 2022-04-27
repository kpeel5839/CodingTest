import java.util.*;
import java.io.*;

/*
입력 예제
9 14 1 5
1 2 0
1 8 1
2 8 1
2 3 1
8 9 1
8 7 1
7 9 1
9 3 1
3 6 0
7 6 1
6 4 1
3 4 0
4 5 1
6 5 1
 */
public class Main {
    /*
    1. 0 - 1 bfs로 일단 LinkedList를 이용해서 할 것임
    2. 일단 queue 간단하게 queue.addFirst 와 queue.add를 이용해서 구현할 것임
    3. 그러고서 목적지의 비용이 나왔을 때 바로 출력하는 것과 아닌 것의 차이를 구분해볼 것임
     */
    public static class Edge{
        int idx;
        int cost;
        public Edge(int idx , int cost){
            this.idx = idx;
            this.cost = cost;
        }
    }
    public static void find(int vertexNumber , int start, int end , int[] parent){
        if(vertexNumber == start){
            System.out.print(vertexNumber + " -> ");
            return;
        }

        else{
            find(parent[vertexNumber] , start , end , parent);
            if(vertexNumber == end){
                System.out.println(vertexNumber);
            }else{
                System.out.print(vertexNumber + " -> ");
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input =new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        List<ArrayList<Edge>> graph = new ArrayList<>();
        int[] dist = new int[v + 1];
        for(int i = 0; i <= v; i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(vertex1).add(new Edge(vertex2 , cost));
        }

        Arrays.fill(dist , Integer.MAX_VALUE);
        LinkedList<Edge> queue = new LinkedList<>();
        dist[start] = 0;
        queue.add(new Edge(start , 0));
        int[] parent = new int[v + 1];
        parent[start] = start;

        while(!queue.isEmpty()){
            Edge edge = queue.poll();

            if(edge.idx == end){
                System.out.println(edge.cost);
                break;
            }

            for(int i = 0; i < graph.get(edge.idx).size(); i++){
                Edge innerEdge = graph.get(edge.idx).get(i);
                if(innerEdge.cost == 0){
                    if(dist[innerEdge.idx] > edge.cost){
                        queue.addFirst(new Edge(innerEdge.idx , edge.cost));
                        dist[innerEdge.idx] = edge.cost;
                        parent[innerEdge.idx] = edge.idx;
                    }
                }

                if(innerEdge.cost == 1){
                    if(dist[innerEdge.idx] > edge.cost + 1){
                        queue.add(new Edge(innerEdge.idx , edge.cost + 1));
                        dist[innerEdge.idx] = edge.cost + 1;
                        parent[innerEdge.idx] = edge.idx;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(dist));
        find(end , start , end , parent);
    }
}
