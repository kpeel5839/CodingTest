import java.util.*;
import java.io.*;

// 2151 : 거울 설치
/*
--전제 조건
출발하는 곳은 # , 거울을 설치할 수 있는 곳은 ! , 통과할 수 있는 곳은 . , 통과하지 못하는 곳은 *이라고 했을 때
출발해서 , 다른 문까지 도달하려면 최소 몇개의 거울을 설치해야 하는지 출력하시오.
--틀 설계
01 bfs의 원리를 사용하면 될 듯하다.
queue에다가 , 일단 직선방향으로 이동은 가장 우선으로 둔다.
그 다음에 !가 있을 때 우선순위가 떨어지게 queue.add(90도 꺾인 방향) 을 추가한다.
그러면 이게 가다가 막히게 되면 여기서 분기할 것이다.

그렇게 해서 , !가 있는 곳에서만 90도로 꺾인 방향 양쪽으로 분기하게 되면 답을 구할 수 있다.

이 원리는 일단 bfs 의 특성상 , 비용이 오름차순으로 진행되게 된다 , 그 말은 즉 , 비용이 낮은 순으로 진행이 된다라는 거싱다.
그래서 이미 방문한 곳을 만나게 된다면 ? 거기는 더 낮은 비용으로 이동이 가능한 지점이라는 것을 의미한다.

그래서 이러한 원리로 이렇게 풀 수 있을 것 같다.

-- 해맸던 점
처음에는 일단, dir 계산을 잘 못했음 , 양쪽으로 가는
그리고 visited 처리를 처음에 해서 목적지까지 도달할 수가 없는 점도 있었음 , 특수한 상황에서
물음표에서만 방향전환을 할 수 있다는 특성 때문에
그래서 visited 를 없앴는데 , 메모리 초과가 나왔음
그래서 visited 를 만들고 , 해당 지점에 도달했을 때 visited[y][x][dir] 까지 true이면
그 때 끝내는 걸로 했음
왜냐하면 나중에 dir까지 똑같이 도달했다? 그러면 무조건 비용이 더높음 탐색할 필요가 없는 것이다.

근데도 안됐음 , 알고보니까 여러 방향으로 출발지점에서 뻗어나갈 때 , 거울에 도달할 수 없는 경우의 수가 존재함
경로는 무조건 존재하는데 , 그게 어디로 가도 성공하는 것이 아님 , (처음에 어디로 뻗어나가도)
그래서 result = 0 으로 해놔서 ans 가 0 으로 초기화되버려서 , 정확한 답이 나와도
값이 담기지 않았음.
그래서 result = Integer.MAX_VALUE 로 바꿔서 ac받음
 */
public class Main {
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static char[][] map;
    public static int n , ans = Integer.MAX_VALUE;
    public static boolean[][][] visited;
    public static Point[] door = new Point[2];
    public static class Point{
        int y;
        int x;
        int dir;
        int value;
        public Point(int y , int x, int dir , int value){
            this.y = y;
            this.x = x;
            this.dir = dir;
            this.value = value;
        }
        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
    }

    public static void bfs(int y, int x , int dir){
        /*
        방향으로 받아서 한번 해당 방향으로부터 탐색을 시작할 것이다.
        비용을 입력할 필요는 없고 , 맨 마지막 while 문이 끝날 때
        즉 queue.isEmpty 할때의 값을 얻어올 변수가 필요하다
        그리고 진행하던 방향으로 !를 만나면 90도 꺾인 방향으로 양방향으로다가
        추가해놓는다 , (우선순위가 낮게)

        그리고 도달하면 result = value 를 집어넣고
        ans = Math.min(ans , result) 하면 된다.
         */
        LinkedList<Point> queue = new LinkedList<>();
        visited = new boolean[n][n][4];
        int result = Integer.MAX_VALUE;

        queue.add(new Point(y , x , dir , 0)); // 첫번째 지점을 넣어줌

        while(!queue.isEmpty()){
            Point point = queue.poll();

            if(point.y == door[1].y && point.x == door[1].x){
                result = point.value;
                break;
            }

//            if(y == 3 && x == 0) System.out.println("point y : " + point.y + " point x : " + point.x);
            if(visited[point.y][point.x][point.dir]) continue;
            visited[point.y][point.x][point.dir] = true;

            if(map[point.y][point.x] == '#') continue;
//            System.out.println("point dir : " + point.dir);
//            System.out.println("point y : " + point.y + " point x : " + point.x);


            // 검사해야하는 것이 , 내가 지금 어디 위치에 있는지 검사해야함 , 느낌표인지 , 아닌지
            // 느낌표에 있다면 양방향으로 분기할 수 있음 근데 그것은 그냥 queue.add임
            // 그리고 항상 가던 방향 그대로 가는 것은 queue.addFirst 임 , 비용이 적으니까
            if(map[point.y][point.x] == '!'){
                for(int i = -1; i < 2; i++){
                    dir = point.dir + i < 0 ? 3 : (point.dir + i) % 4;
                    int ny = point.y + dy[dir];
                    int nx = point.x + dx[dir];
                    if(outRange(ny , nx)) continue;
                    if(i == 0){
                        queue.addFirst(new Point(ny , nx , point.dir , point.value));
                    }
                    else{
                        queue.add(new Point(ny , nx , dir , point.value + 1));
                    }
                }
            }
            else{
                int ny = point.y + dy[point.dir];
                int nx = point.x + dx[point.dir];
                if(outRange(ny , nx)) continue;
                queue.addFirst(new Point(ny , nx , point.dir , point.value));
            }
        }

        ans = Math.min(ans , result);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(input.readLine());

        map = new char[n][n];

        for(int i = 0; i < n; i++){
            String string = input.readLine();
            for(int j = 0; j < n; j++){
                map[i][j] = string.charAt(j);
                if(map[i][j] == '#'){
                    if(door[0] == null) door[0] = new Point(i , j);
                    else door[1] = new Point(i, j); // 문 두개의 위치 초기화
                }
            }
        }

        int y = door[0].y;
        int x = door[0].x;
        // 첫번째 문으로 4방향으로 이동을 시도해볼 것이다.
        for(int i = 0; i < 4; i++){
            int ny = y + dy[i];
            int nx = x + dx[i];
            if(outRange(ny , nx)) continue;
            bfs(ny , nx , i);
        }

        System.out.println(ans);
    }
    public static boolean outRange(int y, int x){
        if(y < 0 || y >= n || x < 0 || x >= n || map[y][x] == '*') return true;
        return false;
    }
}
