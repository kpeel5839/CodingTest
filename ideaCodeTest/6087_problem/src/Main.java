import java.util.*;
import java.io.*;

// 6087 : 레이저 통신
/*
-- 전제 조건
크기가 w * h인 크기의 지도가 있음 , 각 칸은 빈 칸이거나 , 벽이다 , 'C'로 표시되어 있는 두 칸을 레이저로 통신하기 위해서
설치해야 하는 거울 개수의 최솟값을 구하는 프로그램을 작성하시오.
레이저로 통신한다는 것을 두 칸을 레이저로 연결할 수 있음을 의미한다.
빈칸에 '/' , '\' 을 설치해서 방향을 90도 회전시킬 수 있다. (이 경우에 당연히 레이저의 방향에 따라서 모든 방향이 가능하다.)
맵이 주어지고 , 두 레이저를 이어지게 하기 위한 최소 거울 개수만 구해서 출력하면 된다.
-- 틀설계
0-1 bfs를 하면 될 것 같다
현재 queue에다가 현재 방향을 저장해놓고 일단은 그냥 현재 방향을 유지하는 것을 먼저 선택한다.
그리고 현재 방향에 있어서 90도로 꺾이는 부분은 숫자를 하나 더 올려서 넣는다.
그러면 될 것 같음
그러니까 입력을 받고
레이저 하나의 지점을 뽑아낸다 , 그리고서 0 - 1 bfs를 하는 함수를 만들어서
해당 지점으로 부터 4방향으로 다 시도를 한 다음에
거기서 min 을 뽑는다. 다른 c지점까지의 min 값을
그리고 본인과 방향이 90도로 차이나는 것들은
0 - 1 3
1 - 0 2
2 - 1 3
3 - 0 2
이런식으로 있다 이 지점들을 나중에 처리하면 된다.
벽에 막히는 것은 가지 않고 , 일단 이 것은 어떠한 지점을 move를 최소로 해서 하는 것이 아니고 거울을 설치하는 개수이기 때문에
방향이 90도 회전하는 것을 나중에 처리하면 되는 것이다.

일단 그러면 입력을 받고 char[][] map으로
입력을 받으면서 Point class 로 C지점을 받는다 , list로
두개를 다 받는다.
그 다음에 이제 벽을 피해서 4방향으로 할 것인데
하나의 C지점 , 즉 나는 list.get(0)으로 할 것이고 여기서
4가지 방향을 다 시도해본다.
class 의 속성들은 y , x , value , dir 로 이루어 질 것이고
4방향으로 시도할 것이고 bfs로 주어질 것이다.
그리고 진행된 방향으로 줄 것이고 , 만일 C지점에 4방향 중 막혀있는 부분이 있다면 시작조차 못하도록 할 것이다.
 */
public class Main {
    public static int min = Integer.MAX_VALUE , h , w;
    public static List<Point> laser = new ArrayList<>();
    public static char[][] map;
    public static void bfs(int y , int x , int dir){
        /*
        일단 처음 지점을 받고
        현재 맵 크기만큼의 deepMap을 만든다.
        그러고서 -1로 채워넣는다.(이것은 방문처리용으로 쓰일 것임)
        그것을 LinkedList에다가 집어넣는다. (new Point(y, x , 0 , dir)로
        그 다음에 진행을 하는데 일단 같은 방향 addFirst해주고
        아니면 그냥 add해준다 그 다음에 다 진행되면 deepMap[laser.get(1).y][laser.get(1).x]로 값을 구해서 min과 비교해서 집어넣어준다.
         */
        int[][] deepMap = new int[h][w];
        for(int i = 0; i < h; i++){
            Arrays.fill(deepMap[i] , -1);
        }
        LinkedList<Point> queue = new LinkedList<>();
        queue.add(new Point(y , x , 0 , dir));
        deepMap[y - dy[dir]][x - dx[dir]] = 0;
        while(!queue.isEmpty()){
            Point point = queue.poll();
            if(deepMap[point.y][point.x] != -1) continue;
            int value = point.value;
            int direction = point.dir;
            int avoid = (direction + 2) % 4;
            deepMap[point.y][point.x] = value;
            for(int i = 0; i < 4; i++){
                if(i == avoid) continue;
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= h || nx < 0 || nx >= w || map[ny][nx] == '*' || deepMap[ny][nx] != -1){
                    continue;
                }
                /*
                본인과 다른 방향이면 그냥 add 만일 같은 방향이면 addFirst
                 */
                if(direction == i){
                    queue.addFirst(new Point(ny , nx , value , i));
                }else{
                    queue.addLast(new Point(ny , nx , value + 1 , i));
                }
            }
        }

//        System.out.println("next");
//        for(int i = 0; i < h; i++){
//           System.out.println(Arrays.toString(deepMap[i]));
//        }
        int result = deepMap[laser.get(1).y][laser.get(1).x];
        min = Math.min(result , min);
    }
    public static class Point{
        int y;
        int x;
        int value;
        int dir;
        public Point(int y, int x ,int value , int dir){
            this.y = y;
            this.x = x;
            this.value = value;
            this.dir = dir;
        }
        public Point(int y, int x){
            this.y = y;
            this.x = x;
        }
        @Override
        public String toString(){
            return "y : " + y + " x : " + x;
        }
    }
    public static int[] dy = {-1 , 0 , 1 , 0} , dx = {0 , 1 , 0 , -1};
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        w = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        map = new char[h][w];

        for(int i = 0; i < h; i++){
            String string = input.readLine();
            for(int j = 0; j < w; j++){
                map[i][j] = string.charAt(j);
                if(map[i][j] == 'C'){
                    laser.add(new Point(i, j));
                }
            }
        }

        int y = laser.get(0).y , x = laser.get(0).x;
        for(int i = 0; i < 4; i++){
            int ny = y + dy[i];
            int nx = x + dx[i];
            if(ny < 0 || ny >= h || nx < 0 || nx >= w || map[ny][nx] == '*'){
                continue;
            }
            bfs(ny , nx , i);
        }

        System.out.println(min);
    }
}