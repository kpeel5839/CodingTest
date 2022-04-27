import java.util.*;
import java.io.*;

// 2178 : 미로 탐색
/*
-- 전제조건
미로에서 1은 이동할 수 있는 칸을 나타낸다.
이 경우에서 (1, 1) 에서 시작해서 (n , m)까지의 최소 경로를 구하라
-- 틀 설계
point class 를 선언한다.
위치는 0 , 0 으 value = 1로 queue 에다가 집어넣는다.
그러고서 해당 queue에다가 집어넣을 때 이전에 뽑은 point에서 value + 1 을 해서 queue 에다가 추가한다.
map[y][x]가 0이면 queue에다가 추가하지 않는다 , 가지 못하는 경로 취급
그러면은 해당 경로까지 몇칸을 지나서 온지 value 가 나타내게 된다.
그러면 map 에다가 찍어 놓으면 map[n-1][m-1]은 0, 0 에서 n-1,m-1까지의 최소 경로를 나타낸다.
 */
public class Main {
    public static class Point{
        int y;
        int x;
        int value;
        public Point(int y, int x, int value){
            this.y = y;
            this.x = x;
            this.value = value;
        }
    }
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 ,1 , 0};
    public static void main(String[] args) throws IOException{
        /*
        입력 받고
        queue 를 이용해서 bfs를 진행한다.
         */
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        int[][] map = new int[r][c];
        int[][] visited = new int[r][c];

        for(int i = 0; i < r; i++){
            String string = input.readLine();
            for(int j = 0; j < c; j++){
                map[i][j] = string.charAt(j) - '0';
            }
        }

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(0 , 0 , 1));
        visited[0][0] = 1;

        while(!queue.isEmpty()){
            Point point = queue.poll();
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= r || nx < 0 || nx >= c || visited[ny][nx] == 1 || map[ny][nx] == 0){
                    continue;
                }
                visited[ny][nx] = 1;
                map[ny][nx] = point.value + 1;
                queue.add(new Point(ny , nx , point.value + 1));
            }
        }

        System.out.println(map[r - 1][c - 1]);
    }
}
