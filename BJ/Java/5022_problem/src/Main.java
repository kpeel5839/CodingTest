import java.util.*;
import java.io.*;

// 5022 : 연결

/*
-- 전제 조건
어떠한 두 점이 주어졌을 때 , 해당 전선을 길이를 짧게 잇는다.
그랬을 때 , 서로 겹치지 않고 최대한 짧은 경우의 전선의 개수를 출력하시오.

단 , 서로 겹치지 않고 이울 수 없는 경우에는 IMPOSSIBLE 을 출력하면 된다.

-- 틀 설계
일단 내 생각은 이런 식으로 진행해본다.
먼저 a의 전선을 잇는 경우를 진행한다.
그러고서 parent 배열을 만들어서 , 해당 지점의 부모를 기록한다.

그래서 , 해당 지점의 부모를 기록하면서 , a1 -> a2 로 가는 경우를 찾아낸다.
그런 다음에 visited 를 초기화하고 , pathTrace 를 (parent로) 진행하면서,
visited 처리를 해준다(a의 경로를)

그 다음에 b1 -> b2 로 가는 경로를 찍는다.
그러면 이렇게 한다고 가정하고나서 , 또 해야 할 일은 이전에 a 를 먼저 진행하였으니까,
이번에는 b를 먼저 진행해서 또 나오는 값을 찾는다.

이제 해당 전제적인 설계는 끝났고 , detail 을 잡아야한다.
일단 해당 지점에 도달하지 못하는 경우를 잡아내야한다.

일단 첫번째로 진행하는 bfs 에서는 실패 할 일이 없다.
그렇다고 하더라도 , result가 초기에 설정해둔 값이라면?
실패라고 간주한다.

그렇다라는 것은 boolean 변수를 하나 두어서 , 실패하면은 해당 경우는 실패하는 걸로
그러니까 결과적으로 a 를 먼저 찾아보는 경우 , b 를 시작으로 찾아보는 경우 두가지의 경우이고,

일단 먼저 bfs 를 해서 pathTrace 를 통해서 방문처리를 하고 , b를 시작하는 경우
그리고 b를 먼저 한다음에 pathTrace 를 통해서 방문처리를 하고 , a를 시작하는 경우

이 두가지의 경우의 결과값을 알아낸 다음에 , 더 최소값을 출력하면 되고,
만일 ans 가 결국 Integer.MAX_VALUE 이면
IMPOSSIBLE 을 출력하면 된다.

-- 결론
난 내 설계가 완전 틀린줄 알았다.
3퍼센트에서 틀리길래,
왜냐하면 다양한 경우가 있고 그로 인해서 당연히 다른 결과가 나올 수도 있다고 생각했다.
근데 맞았고 , 내가 이전에 걸어가면서 생각했던 예외 상황을 피해서 생각했는데 맞게 되었다.

틀렸던 이유는 시작할 때 , a1 -> a2 의 경로를 구할 때 , b1, b2와 겹치는 경우를 생각하지 않았다.

예를 들어서

a 0 0 0 0
0 0 0 0 0
b 0 0 0 b
0 0 0 0 0
a 0 0 0 0

이 경우가 있을 때 , a 의 최단 경로는 쭉 그냥 이어서 일직선이다 , 즉 b를 지나간다.
하지만 이 경우는 impossible 이 되어야 하지만 , b가 시작할 때 , impossible 처리가 되어야 하지만
나는 이후에 갈 정점들을 정하면서 visited[ny][nx] 로 거르기 때문에 걸러지지 않았다.

그래서 이 점을 고쳤다.
initA() , initB() 로 , 그냥 한번에 통째로 init 해버리면
a1 -> a2 , b1 -> b2 로 갈 때 끝 지점이 이미 방문처리가 되어있어서, 가지 못하는 경우가 생긴다.
그러면서 결과 값을 구하지 못하게 된다 , 그래서 A 정점들 방문처리 , B 정점들 방문처리를 initA , initB method 로 따로만들어서
관리하였음

그래서 결국 맞았다.
 */

public class Main {
    public static boolean[][] visited;
    public static Point[][] parent;
    public static int W , H , ans = Integer.MAX_VALUE , res1 = 0 , res2 = 0;
    public static Point a1, a2 , b1 , b2;
    public static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    public static StringTokenizer st;
    public static int[] dy = {-1 , 0 , 1 , 0} , dx = {0 , 1 , 0 , -1};

    public static void bfs(Point p1, Point p2 , int select){
        /*
        해당 받은 지점을 잇는데,
        그냥 p2를 찾을 때까지 visited 와 point , queue 를 이용해서 평범하게 찾으면 된다.
        근데 , 중요한 점은 path를 등록하는 것이다.
         */

        Queue<Point> queue = new LinkedList<>();
        int y = p1.y , x = p1.x;

        visited[y][x] = true;
        parent[y][x] = new Point(y , x);

        queue.add(new Point(y , x , 0));

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            // 종료 처리
            if(point.equal(p2)){
                if(select == 1) res1 = point.value;
                else res2 = point.value;
                break;
            }

            // 여기서 parent 처리를 바로바로 해주고 , visited 처리도 바로 해줄 것임
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if(ny < 0 || ny >= H || nx < 0 || nx >= W || visited[ny][nx]) continue;

                parent[ny][nx] = new Point(point.y , point.x);
                visited[ny][nx] = true;
                queue.add(new Point(ny , nx , point.value + 1));
            }
        }
    }

    public static void pathTrace(Point p){
        /*
        Point p 가 넘어오면
        해당 지점부터 첫번째 지점까지의 path를 역추적한다
        equal 도 해놨으니까 쉽게 가능하다.

        parent[p.y][p.x] 는 부모를 의미하고 본인과 같다라면
        끝임

        그래서 같으면 종료하고 , 아니라면 부모의 지점을 인자로 넣어서 재귀적으로 진행한다.
         */
        if(p.equal(parent[p.y][p.x])){
            visited[p.y][p.x] = true;
            return;
        }
        visited[p.y][p.x] = true;
        pathTrace(parent[p.y][p.x]);
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
        @Override
        public String toString(){
            return "y : " + y + " x : " + x;
        }

        public boolean equal(Point o){
            if(this.y == o.y && this.x == o.x){
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(input.readLine());

        W = Integer.parseInt(st.nextToken()) + 1;
        H = Integer.parseInt(st.nextToken()) + 1;

        parent = new Point[H][W];
        visited = new boolean[H][W];

        a1 = inputPoint();
        a2 = inputPoint();
        b1 = inputPoint();
        b2 = inputPoint();

        /*
        a 를 먼저 진행해서 결과 값을 알아낸다. (res를 전역변수로 선언해서 결과값을 알아낼 것임
        그 다음에 pathTrace 해서 , 방문처리를 진행하고
        b1 , b2를 잇는 작업을 진행한다.
        이 것을 진행할 때 , res2 == 0 이라면 진행하지 않는다.
         */

        initB();

        bfs(a1 , a2 , 1);
        visited = new boolean[H][W];
        pathTrace(a2);
        bfs(b1 , b2 , 2);

        if(res2 != 0){
            ans = Math.min(ans , res1 + res2);
        }

        res1 = 0;
        res2 = 0;
        parent = new Point[H][W];
        visited = new boolean[H][W];
        initA();

        bfs(b1 , b2 , 1);
        visited = new boolean[H][W];
        pathTrace(b2);
        bfs(a1 , a2 , 2);

        if(res2 != 0){
            ans = Math.min(ans , res1 + res2);
        }

        if(ans == Integer.MAX_VALUE) System.out.println("IMPOSSIBLE");
        else System.out.println(ans);
    }
    public static Point inputPoint() throws IOException{
        st = new StringTokenizer(input.readLine());
        int c = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        return new Point(r , c);
    }

    public static void mapPrint(){
        System.out.println("next");
        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                System.out.print((visited[i][j] ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }

    public static void initA(){
        visited[a1.y][a1.x] = true;
        visited[a2.y][a2.x] = true;
    }
    public static void initB(){
        visited[b1.y][b1.x] = true;
        visited[b2.y][b2.x] = true;
    }
}
