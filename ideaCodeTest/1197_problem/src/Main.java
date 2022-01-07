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
1. 일단 v가 주어지면 그 만큼의 정점들을 만들어 놓는다 , edge는 null 인상태로 (list로 관리하자 , 어떤 노드들과 되어 있는 지)
2. 그걸 다 배열에 저장한다 해당 정점들을 그니까 size 자체를 v + 1로 선언해서 한다.
3. dfs로 하나하나의 정점을 다 들릴 것이다.
4. 그리고서 이제 visited는 다시 방문하지 않도록 하는 것이고 , 만약의 edge 값이 v - 1이 된다면
5. 그 떄 현재 가중치의 합과 min과 비교해서 한다.
-- 계속 내 코드를 보다가 내린 결론
1. 최소 가중치인 간선부터 순서대로 진행해야함
2. 그것들 부터 순서대로 연결해야지 가능함
 */
public class Main {
    public static int min = Integer.MAX_VALUE , v , m;
    public static Node[] vertexList;
    public static void dfs(int vertexNumber , int edgeCount , int score , List<Integer> visitedVertexList, int[] visited){
        /*
        1. 일단 edge가 v - 1이 되면 멈추면 됨
        2. 그리고 계속 dfs로 도는데 visited 방문한 칸의 vertexNumber에다가 visited를 처리하고 하면 된다.
        3. 그리고 for문은 본인이 가징 edgeList길이만큼 for 문 돌리면 될듯
         */
        int[] innerVisited = visited.clone();
        innerVisited[vertexNumber] = 1;
        Node curNode = vertexList[vertexNumber];
        List<Integer> innerVisitedVertexList = new ArrayList<>();
        for(Integer number : visitedVertexList){ //어디 정점을 지나치는 지 확인하기 위한 것
            innerVisitedVertexList.add(number);
        }
        innerVisitedVertexList.add(vertexNumber);
        if(v - 1 == edgeCount){
//            System.out.println("innerVisitedVertexList : " + innerVisitedVertexList);
//            System.out.println("innerVisited : " + Arrays.toString(innerVisited));
            min = Math.min(score , min);
            return;
        }
        for(Edge edge : curNode.edgeList) {
            if (innerVisited[edge.vertex] == 1) {
                continue;
            }
//            System.out.println(Arrays.toString(innerVisited));
            dfs(edge.vertex, edgeCount + 1, score + edge.weight, innerVisitedVertexList , innerVisited);
        }
    }
    public static class Node{
        int value;
        List<Edge> edgeList;
        public Node(int value){
            this.value = value;
            this.edgeList = new ArrayList<>();
        }
    }
    public static class Edge{
        int vertex;
        int weight;
        public Edge(int vertex , int weight){
            this.vertex = vertex;
            this.weight = weight;
        }
    }
//    public static void clean(){
//        for(int i = 1; i <= v; i++){
//            visited[i] = 0;
//        }
//    }
    public static void main(String[] args) throws IOException{
        /*
        1. 일단 v + 1 만큼 vertexList 사이즈 정하고 visited 사이즈도 정한다.
        2. 그리고 vertexValue 를 1부터 <= v까지 for 문으로 돌린다.
        3. 그 다음 min 출력
         */
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        v = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        vertexList = new Node[v + 1]; // 둘다 0은 그냥 비어있게 놔두면 됨 , 숫자 편하게 맞추려고 하나 추가함

        for(int i = 1; i <= v; i++){
            vertexList[i] = new Node(i);
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            vertexList[vertex1].edgeList.add(new Edge(vertex2, weight));
            vertexList[vertex2].edgeList.add(new Edge(vertex1, weight));
        }

        for(int i = 1; i <= 1; i++){
            int[] visited = new int[v + 1];
            dfs(i , 0, 0 , new ArrayList<>() , visited);
        }
        System.out.println(min);
    }
}

