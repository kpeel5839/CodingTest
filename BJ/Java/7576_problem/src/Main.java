import java.util.*;
import java.io.*;

// 7576 : 토마토

/*
-- 전제조건
토마토의 담겨있는 상자의 정보들이 주어지고,
거기서 익은 토마토 옆에 있는 토마토들은 익어간다.
상자 전체의 토마토가 익을려면 몇 분이 걸리는지 출력 (주변으로 옮겨 가는데 1분 걸린다.)
토마토가 모두 익지 못하는 상황이면 -1을 출력한다.
-- 틀설계
그냥 처음에 토마토 입력받으면서 1의 위치들 다 Queue 에다가 담아놓고
bfs 끝날 때까지 진행한다음에 , 만일 0인 위치가 있다라면 -1을 출력하고
아니라면 value 중 가장 높은 value 를 출력한다.
 */
public class Main {
    public static Queue<int[]> queue = new LinkedList<>();
    public static int res = 0 , H , W;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static boolean[][] visited;
    public static int[][] map;

    public static boolean check(){
        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                if(map[i][j] == 0) return true;
            }
        }

        return false;
    }

    public static void bfs(){
        while (!queue.isEmpty()) {
            int[] point = queue.poll();
//            System.out.println(point[0] + " " + point[1] + " " + point[2]);
            for(int i = 0; i < 4; i++){
                int ny = point[0] + dy[i];
                int nx = point[1] + dx[i];

                if(outOfRange(ny , nx) || visited[ny][nx] || map[ny][nx] == -1) continue;

                visited[ny][nx] = true;
                res = Math.max(res , point[2] + 1);
                map[ny][nx] = point[2] + 1;
                queue.add(new int[]{ny , nx , point[2] + 1});
            }
        }
    }

    public static boolean outOfRange(int y , int x){
        if(y < 0 || y >= H || x < 0 || x >= W) return true;
        return false;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        visited = new boolean[H][W];
        map = new int[H][W];

        for(int i = 0; i < H; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < W; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                // 처음에는 0으로 시작
                if(map[i][j] == 1){
                    queue.add(new int[]{i , j , 0});
                    visited[i][j] = true;
                }
            }
        }

        bfs();

        if(check()) System.out.println(-1);
        else System.out.println(res);
    }
}