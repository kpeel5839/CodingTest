import java.util.*;
import java.io.*;

// 2573 : 빙산
/*
-- 전제 조건
2차원 배열로 빙산을 나타내고,
그 빙산의 높이가 나타낸다. (숫자는)

빙산의 높이가 0이 아닐때에는 주변에 바닷물이 있는 정도에 따라서 , 즉 바다가 몇개가 붙어있냐에 따라서 빙산이 녹는
속도가 다르다.

빙산이 두 덩어리 이상으로 나뉠 때까지 걸리는 최소년을 구하시오.
단 , 빙산이 완전히 사라질 때까지 덩어리가 나뉘지 않으면 0을 출력하면 된다.
-- 틀 설계
얼음을 순차적으로 녹이는 melt 함수 (queue 로 관리해야 할 듯)
그리고 덩어리를 확인하는 dfs ,
덩어리가 2개 이상으로 나뉘었는지 확인하는 lumpCheck();
빙하가 다 녹았는지 확인하는 check() 함수 이 4개로 쉽게 풀 수 있을 듯
 */
public class Main{
    public static int H , W , ans = 0;
    public static int[][] map;
    public static boolean[][] visited;
    public static boolean finish = false;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};

    public static void melt(){
        /*
        얼음을 녹이는 melt
        queue 에다가 넣으면서 주변에 있는 것 만큼 , - 해서 집어넣는데,
        뺐을 때 0보다 작으면 0으로 다시 집어넣는다.
         */

        Queue<Point> queue = new LinkedList<>();

        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                if(map[i][j] != 0) {
                    int count = 0;
                    for (int c = 0; c < 4; c++) { // 주변에 것을 0이면 더하면서 , queue 에다가 집어넣는다.
                        int ny = i + dy[c];
                        int nx = j + dx[c];

                        if(outOfRange(ny , nx) || map[ny][nx] != 0) continue; // 값이 0이 아니면 넘김

                        count++;
                    }

                    int value = map[i][j] - count;
                    if(value < 0) value = 0; // value 가 음수이면 0으로 바꿔준다.
                    queue.add(new Point(i , j , value)); // value 집어넣어준다.

                    // 무조건 queue 에다가 집어넣으면서 , 다 초기화 해준다.
                }
            }
        }

        while (!queue.isEmpty()) {
            Point point = queue.poll();
            map[point.y][point.x] = point.value;
        }
    }

    public static class Point{
        int y;
        int x;
        int value;

        public Point(int y, int x , int value){
            this.y = y;
            this.x = x;
            this.value = value;
        }

        public Point(int y, int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void bfs(int y, int x){
        /*
        덩어리가 나뉘었는지 순차적으로 실행시키는 dfs
        여기서 분명히 덩어리가 나뉘었는지 안나뉘었는지 확인이 가능할 것 같은데..

        빙산을 2번 이상 방문하면 , 끝내고,
        만일 빙산을 한번도 방문 못하면 ans = 0 으로 만들고 , 나감

        그리고서 그냥 주변다니면서 visited 처리만 해준다.
         */

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(y , x));
        visited[y][x] = true;

        while (!queue.isEmpty()) {
            Point point = queue.poll();
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if(outOfRange(ny , nx) || visited[ny][nx] || map[ny][nx] == 0) continue;

                visited[ny][nx] = true;
                queue.add(new Point(ny , nx));
            }
        }
    }

    public static boolean outOfRange(int y, int x){
        if(y < 0 || y >= H || x < 0 || x >= W) return true;
        return false;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        map = new int[H][W];
        visited = new boolean[H][W];

        for(int i = 0; i < H; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < W; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Loop:
        while(true){
            visited = new boolean[H][W];
            finish = false;
            ans++;

            melt();
//            mapPrint();

            for(int i = 0; i < H; i++){
                for(int j = 0; j < W; j++){
                    if(map[i][j] != 0 && !visited[i][j]) { // 방문안하고 , 0이 아닌 것
//                        System.out.println(" i : " + i  + " j : " + j);
                        if(finish) break Loop;
                        finish = true;
                        bfs(i, j);
                    }
                }
            }

            if(!finish) {
                ans = 0;
                break;
            }
        }

        System.out.println(ans);
    }
    public static void mapPrint(){
        System.out.println("next");
        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}