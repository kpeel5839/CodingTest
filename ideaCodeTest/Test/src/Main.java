import java.util.*;
import java.io.*;

public class Main{
    public static int v , e;
    public static int[] visited;
    public static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int[][] map;
    public static void dfs(int vertexNumber) throws IOException{
        writer.write(vertexNumber + " ");
        visited[vertexNumber] = 1;
        for(int i = 1; i <= v; i++){
            if(map[vertexNumber][i] == 1 && visited[i] == 0){
                dfs(i);
            }
        }
    }
    public static void bfs(int vertexNumber) throws IOException{
        Queue<Integer> queue = new LinkedList<>();
        queue.add(vertexNumber);
        visited[vertexNumber] = 1;
        while(!queue.isEmpty()){
            int number = queue.poll();
            writer.write(number + " ");
            for(int i = 1; i <= v; i++){
                if(map[number][i] == 1 && visited[i] == 0){
                    queue.add(i);
                    visited[i] = 1;
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

        visited = new int[v + 1];
        map = new int[v + 1][v + 1];

        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            map[vertex1][vertex2] = 1;
            map[vertex2][vertex1] = 1;
        }

        dfs(startNumber);
        visited = new int[v + 1];
        writer.write("\n");
        bfs(startNumber);
        writer.flush();
        writer.close();
    }
}








