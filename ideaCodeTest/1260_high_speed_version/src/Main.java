import java.util.*;
import java.io.*;

/*
-- 틀 설계
Edge class 선언해주고
양방향 간선이기에 입력을 받을 때 두 방향의 간선 다 만들어준다.
이거 하기 전에 전처리로 new ArrayList로 다 만들어준다. List<ArrayList<Edge>> graph 이런식으로 선언해주면 된다.
dfs는 그 vertexNumber로 가고 그냥 솔직히 이전이랑 다른 것은 그냥 for문을 다 도는 것이 아니라 list에 존재하는 것만 도는 것이기 때문에 훨씬 빠를 수 밖에 없다.
 */
public class Main{
    public static int v , e;
    public static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    public static List<ArrayList<Edge>> graph = new ArrayList<>();
    public static int[] visited;
    public static class Edge implements Comparable<Edge>{
        int idx;
        public Edge(int idx){
            this.idx = idx;
        }
        @Override
        public int compareTo(Edge other){
            if(this.idx > other.idx){
                return 1;
            }
            else{
                return -1;
            }
        }
    }
    public static void dfs(int vertexNumber) throws IOException{
        writer.write(vertexNumber + " "); // 함수가 호출 되었을 때 방문한 것이니 출력
        visited[vertexNumber] = 1; // 방문 처리
        for(int i = 0; i < graph.get(vertexNumber).size(); i++){
            Edge edge = graph.get(vertexNumber).get(i);
            if(visited[edge.idx] == 0){
                dfs(edge.idx);
            }
        }
    }
    public static void bfs(int vertexNumber) throws IOException{
        Queue<Integer> queue = new LinkedList<>();
        queue.add(vertexNumber); //queue 에다가 시작 노드 넣어줌
        visited[vertexNumber] = 1; // 그리고 방문 처리
        while(!queue.isEmpty()){ //queue가 비어있을 때까지 계속 처리
            int number = queue.poll(); // queue에서 넣은 정점을 뽑아서 출력 이후 해당 number와 연결된 정점이 있는지 확ㅇ니
            writer.write(number + " ");
            for(int i = 0; i < graph.get(number).size(); i++){
                Edge edge = graph.get(number).get(i);
                if(visited[edge.idx] == 0){
                    queue.add(edge.idx);
                    visited[edge.idx] = 1;
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());
        int startNumber = Integer.parseInt(st.nextToken());

        visited = new int[v + 1]; //정점 방문여부를 visited로 관리 1 == 방문 o, 0 == 방문 x

        for(int i = 0; i <= v; i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            graph.get(vertex1).add(new Edge(vertex2));
            graph.get(vertex2).add(new Edge(vertex1));
        }

        for(int i = 1; i <= v; i++){
            Collections.sort(graph.get(i));
        }

        dfs(startNumber);
        visited = new int[v + 1];
        writer.write("\n");
        bfs(startNumber);
        writer.flush();
        writer.close();
    }
}
