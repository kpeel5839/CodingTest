import java.util.*;
import java.io.*;

public class Main{
    public static int v , e;
    public static int[] visited;
    public static int[][] map;
    public static void dfs(int vertexNumber){
        System.out.print(vertexNumber + " "); // 함수가 호출 되었을 때 방문한 것이니 출력
        visited[vertexNumber] = 1; // 방문 처리
        for(int i = 1; i <= v; i++){
            if(map[vertexNumber][i] == 1 && visited[i] == 0){ // vertexNumber와 연결된 정점이 있는지와 , 그 정점을 방문하였는지 확인 , 방문하였으면 dfs로 호출
                dfs(i);
            }
        }
    }
    public static void bfs(int vertexNumber){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(vertexNumber); //queue 에다가 시작 노드 넣어줌
        visited[vertexNumber] = 1; // 그리고 방문 처리
        while(!queue.isEmpty()){ //queue가 비어있을 때까지 계속 처리
            int number = queue.poll(); // queue에서 넣은 정점을 뽑아서 출력 이후 해당 number와 연결된 정점이 있는지 확ㅇ니
            System.out.print(number + " ");
            for(int i = 1; i <= v; i++){
                if(map[number][i] == 1 && visited[i] == 0){ //이것도 dfs와 똑같은 맥락으로 방문했는지와 , 방문하지 않았으면 queue에다가 넣어서 추후에 방문 예정
                    queue.add(i);
                    visited[i] = 1; // 이것은 함수를 재호출 하는 것이 아니니 queue 에다가 추가를 함과 동시에 방문처리
                }
            }
        }
    }
    public static void clean(){
        for(int i = 1; i <= v; i++){
            visited[i] = 0;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());
        int startNumber = Integer.parseInt(st.nextToken());

        visited = new int[v + 1]; //정점 방문여부를 visited로 관리 1 == 방문 o, 0 == 방문 x
        map = new int[v + 1][v + 1]; //간선을 2차원 배열로 관리

        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            map[vertex1][vertex2] = 1;
            map[vertex2][vertex1] = 1; //vertex1 , vertex2사이에 간선이 주어지면 map에다가 map[vertex1][vertex2] , map[vertex2][vertex1]에 추가한다.
        }

        dfs(startNumber);
        clean(); // visited 배열 초기화
        System.out.println();
        bfs(startNumber);
    }
}