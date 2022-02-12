import java.util.*;
import java.io.*;

// 2638 : 치즈
/*
-- 전제조건
그냥 치즈가 있고 , 치즈의 면이 공기와 닿은 면이 2개 이상이다 , 그러면 치즈가 녹아 없어진다.
근데 이게 외부 공기와 닿아있어야 하기 때문에 , 만약 치즈 내부에 공간이 있다면 이 치즈들은 , 내부의 공기와 닿아있는 면은 그냥 안닿았다고 친다.
그 경우에 , 치즈가 다 녹아서 없어지는 시간을 구하시오.
-- 틀 설계
일단 시도할 때마다 0 , 0 에서 bfs를 시작해서 , 외부공기가 유입된 부분은 2로 채워넣는다,
그러면 이제 2와 두개이상 닿아있는 애들만 골라내서 queue에다가 담는다.
그리고 map을 다시 선언한 뒤에 queue에 있는 것들을 집어넣는다 , 그 행동을 반복하면서 melt를 할 때 1이 발견 되지 않을 때까지 하면 된다.
그러니까 , 시작할 때 ++ 해서 , 답은 -1 을 해서 출력하면 된다.

문제에 나와있는 예제
8 9
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 1 1 0 0 0 1 1 0
0 1 0 1 1 1 0 1 0
0 1 0 0 1 0 0 1 0
0 1 0 1 1 1 0 1 0
0 1 1 0 0 0 1 1 0
0 0 0 0 0 0 0 0 0
 */
public class Main {
    public static int[][] map , visited;
    public static Queue<Point> queue = new LinkedList<>();
    public static int w , h , ans = 0;
    public static boolean none;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static class Point{
        int y;
        int x;
        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void bfs(){
        /*
        방문하는 곳을 2로 채워넣으면 된다.
        0 , 0 에서 시작하면 된다. map에다가 바로 그려넣으면 되고
         */
        visited = new int[h][w];
        queue.add(new Point(0 , 0));
        visited[0][0] = 1; // 솔직히 visited 안해도 되는데 귀찮으니까 그냥하자
        map[0][0] = 2;

        while(!queue.isEmpty()){
            Point point = queue.poll();
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= h || nx < 0 || nx >= w || visited[ny][nx] == 1 || map[ny][nx] != 0) continue;
                visited[ny][nx] = 1;
                map[ny][nx] = 2;
                queue.add(new Point(ny , nx));
            }
        }
    }
    public static void melt(){
        /*
        2와 맞닿은 면이 2개 이상인 애들을 queue 에다가 집어넣으면서
        동시에 1의 개수가 하나도 나오지 않으면 끝나도록 1을 발견하면 none에 false를 집어넣는다.
         */

        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                if(map[i][j] == 1) {
                    none = false;
                    int count = 0;
                    for(int c = 0; c < 4; c++){
                        int ny = i + dy[c];
                        int nx = j + dx[c];
                        if(ny < 0 || ny >= h || nx < 0 || nx >= w) continue;
                        if(map[ny][nx] == 2) count++;
                    }
                    if(count < 2) queue.add(new Point(i , j));
                }
            }
        }

        map = new int[h][w];

        while(!queue.isEmpty()){
            Point point = queue.poll();
            map[point.y][point.x] = 1;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        map = new int[h][w];

        for(int i = 0; i < h; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < w; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while(true){
            none = true;
            ans++;
            bfs();
            melt();
            if(none) break;
        }

        System.out.println(ans - 1);
    }

    public static void mapPrint(){
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
