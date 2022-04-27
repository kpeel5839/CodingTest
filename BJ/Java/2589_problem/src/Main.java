import java.util.*;
import java.io.*;

// 2589 : 보물섬
/*
-- 전제조건
육지와 땅이 존재하는데 , 최단 거리로 이동하였을 때 가장 먼 지점인 두곳에
묻혀있다 , 그 때 두 보물의 거리를 구하시오
-- 틀 설계
그냥 한 지점씩 돌면서 , bfs 돌려서 가장 최대 값 찾으면 됨
그냥 visited 만 찍고 point 해서 value 만 최 댓값 찍어내면 됨
 */
public class Main {
    public static int[][] visited;
    public static char[][] map;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static int w , h , ans = 0;
    public static class Point{
        int y;
        int x;
        int value;
        public Point(int y , int x , int value) {
            this.y = y;
            this.x = x;
            this.value = value;
        }
    }
    public static void bfs(int y , int x){
        /*
        받으면 그냥 bfs로 돌리는데 value 값을 게속 갱신하면서 , 더 이상 이동 못할 때까지 이동한다.
        그런 다음에 그냥 Math.max(ans , result); 하면 됨

        그리고 함수 시작할 때 h , w 만큼 visited 선언해줌
         */
        visited = new int[h][w];
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(y , x , 0));
        int result = 0;
        visited[y][x] = 1;

        while(!queue.isEmpty()){
            Point point = queue.poll();
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= h || nx < 0 || nx >= w || visited[ny][nx] == 1 || map[ny][nx] == 'W') continue;
                queue.add(new Point(ny , nx , point.value + 1));
                visited[ny][nx] = 1;
                result = point.value + 1;
            }
        }

        ans = Math.max(result , ans);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        map = new char[h][w];

        for(int i = 0; i < h; i++){
            String string = input.readLine();
            for(int j = 0; j < w; j++){
                map[i][j] = string.charAt(j);
            }
        }
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                if(map[i][j] == 'L') bfs(i , j);
            }
        }

        System.out.println(ans);
    }
}