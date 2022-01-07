import java.util.*;
import java.io.*;

// 1197 : 최소 스패닝 트리
/*
-- 전제조건
1. 그래프가 주어졌을 때 최소신장 트리르 구하는 프로그램을 작성하시오
2. 최소 스패닝 트리 - 주어진 그래프의 모든 정점들을 연결하는 부분 그래프 중에서 가중치의 합이 최소인 트리를 찾는다.(무조건 어디 정점에서 시작하더라도 모든 정점을 방문 가능해야함)
3. 1번부터 v번 까지 정점 번호가 주어지고
4. 간선들도 주어진다.
-- 틀 설계
1.
 */
public class Main {
    public static boolean find = false;
    public static void dfs(int vertexNumber , int edgeCount , int score ,List<Integer> visitedVertexList, int[] visited){
        if(find){
            return;
        }
        int[] innerVisited = visited.clone();
        innerVisited[vertexNumber] = 1;
        List<Integer> innerVisitedVertexList = new ArrayList<>();
        for(Integer number : visitedVertexList){
            innerVisitedVertexList.add(number);
        }
        innerVisitedVertexList.add(vertexNumber);
        System.out.println(innerVisitedVertexList);
        if(edgeCount == v - 1){
            find = true;
            min = Math.min(score , min);
            return;
        }
        for(Edge edge : vertexList[vertexNumber].edgeList){
            if(innerVisited[edge.vertex] == 1){
                continue;
            }
            dfs(edge.vertex , edgeCount + 1 , score + edge.weight , innerVisitedVertexList, innerVisited);
        }
    }
    public static int v, m , min = Integer.MAX_VALUE;
    public static Node[] vertexList;
    public static class Node{
        int value;
        List<Edge> edgeList;
        public Node(int value){
            this.value = value;
            edgeList = new ArrayList<>();
        }
    }
    public static class Edge implements Comparable<Edge>{
        int vertex;
        int weight;
        public Edge(int vertex, int weight){
            this.vertex = vertex;
            this.weight = weight;
        }
        @Override
        public int compareTo(Edge other){
            if(this.weight > other.weight){
                return 1;
            } else if(this.weight == other.weight){
                return this.vertex - other.vertex;
            } else{
                return -1;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        v = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        vertexList = new Node[v + 1];

        for(int i = 1; i <= v; i++){
            vertexList[i] = new Node(i);
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            vertexList[vertex1].edgeList.add(new Edge(vertex2 , weight));
            vertexList[vertex2].edgeList.add(new Edge(vertex1 , weight));
        }

        for(int i = 1; i <= v; i++){
            Collections.sort(vertexList[i].edgeList);
        }

        for(int i = 1; i <= v; i++){
            int[] visited = new int[v + 1];
            dfs(i , 0 , 0 , new ArrayList<>() , visited);
        }

        System.out.println(min);
    }
}
