import java.util.*;
import java.io.*;

// 10026 : 적록색약

/*
-- 전제조건
적록색약은 빨간색과 초록색의 차이를 거의 느끼지 못한다.
따라서 , 적록색약인 사람이 보는 그림은 아닌 사람이 보는 그림과는 좀 다를 수 있다.

이럴 때에 적록색약이 아닌 사람이 본 구역수와 , 맞는 사람이 본 구역수의 개수를 각각 출력하여라.
-- 틀 설계
그냥 너무 쉽다.
bfs로 평범하게 구역의 수를 나누고
그 다음에 , visited 를 초기화 한다음에
이전에 R, G 였던 애들을 담아놨던 list를 다 그냥 W로 바꾼다음에
그냥 bfs로 돌리면 된다.

bfs 의 기준은 그냥 해당 point 의 글자를 넘기면 해당 글자만 탐색하면 된다.
 */
public class Main {
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static char[][] map;
    public static boolean[][] visited;
    public static int N , res1 , res2;
    public static List<Point> white = new ArrayList<>();

    public static boolean outOfRange(int y, int x){
        if(y < 0 || y >= N || x < 0 || x >= N) return true;
        return false;
    }

    public static class Point{
        int y;
        int x;
        public Point(int y, int x){
            this.y = y;
            this.x = x;
        }
    }

    public static void bfs(int y, int x ,char target){
        Queue<Point> queue = new LinkedList<>();

        visited[y][x] = true;
        queue.add(new Point(y , x));

        while(!queue.isEmpty()){
            Point point = queue.poll();
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(outOfRange(ny , nx) || visited[ny][nx] || map[ny][nx] != target) continue;

                visited[ny][nx] = true;
                queue.add(new Point(ny , nx));
            }
        }
    }

    public static void blind(){
        visited = new boolean[N][N];
        for(Point point : white){
            map[point.y][point.x] = 'W';
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(input.readLine());
        map = new char[N][N];
        visited = new boolean[N][N];

        for(int i = 0; i < N; i++){
            String string = input.readLine();
            for(int j = 0; j < N; j++){
                map[i][j] = string.charAt(j);
                // R 이거나 G 인 경우 , 넣어준다.
                if(map[i][j] == 'R' || map[i][j] == 'G') white.add(new Point(i , j));
            }
        }

        for(int i = 0; i < N ;i++){
            for(int j = 0; j < N; j++){
                if(!visited[i][j]) {
                    bfs(i , j , map[i][j]);
                    res1++;
                }
            }
        }

        blind();

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(!visited[i][j]){
                    bfs(i , j , map[i][j]);
                    res2++;
                }
            }
        }

        System.out.println(res1 + " " + res2);
    }
}
