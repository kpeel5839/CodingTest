import java.util.*;
import java.io.*;

// 16933 : 벽 부수고 이동하기 3

/*
-- 전제조건
N = H , M = W 가 주어지고 , K = 최대로 부실 수 있는 벽의 개수가 주어진다.
주어진 맵에서 1 은 벽 , 0 은 갈 수 있는 곳이다.
벽을 가기 위해서는 , 벽을 부셔야하고 그것은 최대 K 개 부실 수 있다.
그리고 , 처음 위치부터 , value == 1 로 시작하고 , value % 2 == 0 이면
즉 짝수이면 벽을 부실 수 없다.
이렇게 주어졌을 때 , 불가능한 경우에는 -1 혹은 가능한 경우에는 벽을 부셨든 안부셨든
가장 빠른 경로로 가면 된다.
-- 틀설계
처음에 H , W 로 받고 , K 를 받는다.
그리고 , 처음 위치에 value 로 주어질 때에는 value == 1 로 지정하고
조건들을 정해야 한다.
벽을 부셔야 하는 경우
벽을 부시지 않는 경우

이 이전에는 해당 내가 가려는 위치가 벽이냐 , 아니면 벽이 아니냐가 결정이 된다.
그리고 , 이번에는 해당 위치에 그대로 서있는 것도 가능하다.
하지만 , 그대로 서있는 것을 무조건 하게 된다면 , 너무나도 오래 걸릴 것이다.
그래서 , 특정한 조건이 존재하여야 하는데 , 그 조건을 내가 생각했을 때에는 , 주변에 내가 부술만한
가치가 존재하는 경우에서만 진행해야 할 것 같다.
만일 무조건 가만히 있는 경우도 다 고려하게 되면 , 시간초과가 날 것 같은 느낌이 확든다.

그래서 이런식으로 경우를 나누자.
- 벽이 있는 경우
point.con 이 일단 K 보다 낮아야 한다. (그래야지 벽을 깰 수가 있다.)
그리고 visited[ny][nx] = INF 로 설정할 것인데 , 이 의미는 아직 방문하지 않았다라는 것이고
여기에는 지금까지 왔을 때 , 벽을 깬 갯수를 설정할 것이다.
당연하게도 , point.con 해당 벽을 깰 때 , 이 벽을 깨는 가치가 있기 위해서는 이전에 이 벽을 깬 놈이
본인보다 벽을 많이 깬 경우이다. 즉 , point.con + 1 < visited[ny][nx] 여야 한다라는 것이다.
여기에서 INF 로 설정해놓는 이유가 나온다 , 아직 방문하지 않았을 때에도 visited 가 더 클 수 있게 하기 위해서이다.

근데 여기서 고려해야 할 사항이 더 있다 , 현재 지금 벽을 깰 수 있는 상황이냐라는 것이다.
만일 , 벽을 깨지 않았다라면? , queue.add(new Point(....)) 이런식으로 원래 위치를 넣을 수 있다.
근데 여기서 문제는 중복되는 경우가 많을 것 같다라는 것이다.

나중에 온놈이 , 여기에 또 있을 확률이 존재할까?
일단 없다라고 가정하고 진행해보자 , 즉 정리하면 해당 위치에 더 늦게 온놈은 거리가 더 멀었기 때문에,
벽을 더 안깬 경우가 아니라면 갈 필요가 없는 것이고 , 여러개가 여기 위치로 들어올 수 있는 것이니,
가만히 있는 경우는 visited 처리를 해주어야 할 것 같다.(나중에 온놈이 여기에 가만히 있어야 될 확률은 없다고 가정하자)

그러면 일단 벽이 있는 경우는 처리하게 되었다.

- 벽이 없는 경우
이 경우는 그냥 point.con < visited[ny][nx] 여야 한다
이거는 무조건이다.
그리고서 , 그냥 진행해주면 된다.

정리하면 벽이 있는 경우만 특별하게 처리해주면 되는 것이고
가만히 있는 경우들을 체크해주기 위해서 , visited 배열을 유지해야 한다.

오마이갓 맞았음
 */
public class Main {
    public static int H , W , K , res = -1;
    public static int[][] visited , map;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static final int INF = Integer.MAX_VALUE;
    public static Queue<Point> queue = new LinkedList<>();

    public static class Point{
        int y;
        int x;
        int con; // 벽을 깬 횟수
        int value; // 현재 지점까지의 걸린 비용
        public Point(int y, int x , int value , int con ){
            this.y = y;
            this.x = x;
            this.value = value;
            this.con = con;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[H][W];
        visited = new int[H][W];

        for(int i = 0; i < H; i++){
            String string = input.readLine();
            Arrays.fill(visited[i] , INF);
            for(int j = 0; j < W; j++){
                // character , 숫자로 변환시켜줌
                map[i][j] = string.charAt(j) - '0';
            }
        }

        // 이제 queue 를 이용해서 시작을 해야함
        visited[0][0] = 0;
        queue.add(new Point(0 , 0 , 1 , 0));

        while(!queue.isEmpty()){
            Point point = queue.poll();
            if(point.y == H - 1 && point.x == W - 1){
                res = point.value;
                break;
            }

            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                // 범위를 나가는 부분은 빠져주고
                if(outOfRange(ny , nx)) continue;

                // 벽일 때
                if(map[ny][nx] == 1){
                    // 일단 벽을 깰 수 있는 횟수가 남아있어야 하고 , 현재 깨려는 게 이미 벽을 깬 애보다는 적어도 낮아야지 경쟁력이 있음
                    if(point.con < K && point.con + 1 < visited[ny][nx]){
                        // 이제 벽을 깨는 상황에서만 밤인지 낮인지가 중요해진다 , value % 2 == 0 이라면 벽을 깨지 못한다 , 여기서 한번 더 있어야 한다.
                        if(point.value % 2 == 0){ // 기다려야 하는 경우
                            queue.add(new Point(point.y , point.x , point.value + 1 , point.con)); // 거기에 가만히 멈춰있게끔 진행한다.
                        } // 아니면 stay 말고 , 벽에다가 더 작은 값을 넣어놓으면 똑같은 놈이 또 들어가지는 못한다.
                        else{ // 안 기다려도 되는 경우
                            queue.add(new Point(ny , nx , point.value + 1 , point.con + 1));
                            visited[ny][nx] = point.con + 1;
                        }
                    }
                }
                // 벽이 아닐 때
                else{
                    // 벽을 깨야 하는 상황이 아니기에 point.con + 1 을 해주지 않고 visited 와 비교한다.
                    // 늦게 왔지만 , visited[ny][nx] 보다 작으니까 가능하게 끔 한다.
                    if(point.con < visited[ny][nx]){
                        queue.add(new Point(ny , nx , point.value + 1 , point.con));
                        visited[ny][nx] = point.con;
                    }
                }
            }
        }

        System.out.println(res);
    }

    public static boolean outOfRange(int y, int x){
        if(y < 0 || y >= H || x < 0 || x >= W) return true;
        return false;
    }

}
