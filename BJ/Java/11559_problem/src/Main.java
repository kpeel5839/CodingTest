import java.util.*;
import java.io.*;

// 11559 : Puyo Puyo

/*
-- 전제 조건
뿌요뿌요는 같은 색깔의 블록이 4개 이상 있으면,
터진다.
근데 , 동일한 타이밍에 블록이 4개이상 있는 그룹이 여러개가 있다면 동시에 터진다.
그리고 , 다 터진다음에 , 중력에 의해서 내려온다.

그 다음에 , 또 터지면 , 이제 연쇄가 시작되는 것이다.
누가 뿌요뿌요를 놓는 것이 아니기 때문에
여기서는 그냥 연쇄 폭팔이 몇번 일어나는지만 세면 된다.
-- 틀 설계
 */
public class Main {
    public static int H = 12 , W = 6 , ans = 0;
    public static char[][] map = new char[H][W];
    public static boolean[][] visited;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static boolean explosion;

    public static void gameStart(){
        /*
        gameStart 에서는
        그냥 계속 find , down 만 호출해주면 되고
        explosion 이 일어나는지 안일어나는지 확인한다음에 종료 조건으로 넣어준다.
         */

        while(true){
            explosion = false;
            visited = new boolean[H][W];
//            mapPrint();

            find();
            down();

            if(explosion) ans++; // 삭제 일어났으면 explosion true 임
            else break; // 삭제 안되어 있으면 explosion false 임
        }
    }

    public static void find(){
        /*
        이거는 지금 현재 map 에서 , 터질 뿌요뿌요가 있나 확인하기 위해서
        하나하나 visited 에 방문 처리 안 된 것들만 , . 이 아니고 문자인 애들만 해서
        넘겨가지고 , explosion 이 일어나는지 안일어나는지 확인해준다.
         */
        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                if(map[i][j] != '.')
                    bfs(map[i][j] , i , j);
            }
        }
    }
    public static void down(){
        /*
        중력에 의해서 알파벳들이 떨어지는 것을 구현해야 함
        일단 아래부터 한 지점을 잡고,
        맨 H - 2 부터 시작해서 , 0 까지 가면서 , 체크한다. 일파벳이면 move 함수에다가 넣어준다.
         */

        for(int i = H - 2; i != -1; i--){
            for(int j = 0; j < W; j++){
                if(map[i][j] != '.') move(i , j);
            }
        }
    }

    public static void move(int y , int x){
        /*
        실질적으로 알파벳을 움직이는 함수
        그냥 map[y][x] 를 없애주고 , 아래로 쭉 내려서 최종 목적지에다가 넣어주면 된다.
         */
        char character = map[y][x];
        map[y][x] = '.';


        while(y < H && map[y][x] == '.'){ // y가 범위를 넘지 않으면서 , 그런 경우
            y++;
        }

        map[y - 1][x] = character;
    }

    public static void bfs(char target , int y,  int x){
        /*
        문자와 좌표가 넘어오면 , 같은 그릅들을 찾아서 폭파시키면 된다.
        근데 , 그 그룹의 사이즈가 4개가 넘을 떄여야 한다.

        그래서 그냥 간단하다 , visited배열 가지고 , 해당 문자인 것들만 해서 ,
        bfs를 돌린다.
        그래서 , queue empty 할때까지 하고 count >= 4 explosion 한다.
         */

        int count = 0;
        List<Point> explosionList = new ArrayList<>();
        Queue<Point> queue = new LinkedList<>();

        queue.add(new Point(y , x));
        explosionList.add(new Point(y , x));

        visited[y][x] = true;

        while(!queue.isEmpty()){
            Point point = queue.poll();
            count++;
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= H || nx < 0 || nx >= W || visited[ny][nx] || map[ny][nx] != target) continue;

                queue.add(new Point(ny , nx));
                explosionList.add(new Point(ny , nx));
                visited[ny][nx] = true;
            }
        }

        if(count >= 4){
            explosion = true;
            for(Point point : explosionList){
                map[point.y][point.x] = '.';
            }
        }

    }

    public static class Point{
        int y;
        int x;
        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        for(int i = 0; i < H; i++){
            String string = input.readLine();
            for(int j = 0; j < W; j++){
                map[i][j] = string.charAt(j);
            }
        }

        gameStart();
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
