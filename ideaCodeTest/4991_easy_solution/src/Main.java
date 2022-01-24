import java.util.*;
import java.io.*;
// 4991 : 로봇 청소기
/*
-- 전제 조건
로봇청소기가 있고 격자들이 주어진다.
칸은 깨끗한 칸과 더러운 칸으로 나누어져 있으며 , 로봇 청소기는 더러운 칸을 방문해서 깨끗한 칸으로 바꿀 수 있다.
로봇 청소기는 가구가 놓여있는 칸으로는 움직일 수 있다 , 그리고 로봇은 한 번 움직일 때 인접한 칸으로 이동할 수 있고
또 로봇은 같은 칸을 여러 번 방문할 수 있다.
방의 정보가 주어졌을 때 , 더러운 칸을 모두 깨끗한 칸으로 만드는데 필요한 이동 횟수의 최솟값을 구하는 프로그램을 작성하시오

입력으로 여러개의 테스트 케이스가 주어지고
문자는 '.' = 깨끗한 칸 , '*' = 더러운 칸 , 'x' = 가구 , 'o' = 로봇 청소기의 시작 위치
더러운 칸의 개수는 10개를 넘지 않으며 로봇청소기는 항상 하나이다
입력의 마지막 줄에는 0 이 두개가 주어진다.
그러면 입력의 종료인 것 , 테스트의 개수가 초기에 주어지지 않는다.

-- 틀 설계
이번 설계는 내가 했던 방법과 굉장히 흡사하다.
그냥 일단 bfs로 다 구한다음에 순열을 만들어서 조합하면 되는 것이다.
일단 bfs로 모든 먼지와 먼지의 최소거리를 다 구하고 , 그 다음에
모든 순열을 만들어서 게산하여서 반환하면 된다.
그럴려면 calculate 와 dfs와 bfs로 이루어져야할 것 같음
이전 설계랑 엄청 비슷했지만
아마도 이거는 dist를 다 구해놓은 상태에서 시작하고
나는 구해야 할 상황에서만 구했었는데 그 과정에서도 오버헤드가 게속 발생했던 것 같음

-- 해맸던 점
depth == dirty.length 가 아니다 왜냐하면 dirty.length 에 robot도 들어가서
depth == dirty.length - 1 이 였고
visitedList[i] == 1 이 아니라 visitedList[i] != 1 이 였는데 이점도 조금 실수가 있어서
해맸음
 */

public class Main{
    public static int[][] dist;
    public static boolean fail = false;
    public static int[] dy = {-1 , 0 , 1 , 0} , dx = {0 , 1 , 0 , -1};
    public static int h , w , min = Integer.MAX_VALUE;
    public static char[][] map;
    public static int[] visitedList;
    public static Point[] dirty;
    public static class Point{
        int y;
        int x;
        int value;
        public Point(int y, int x, int value){
            this.y = y;
            this.x = x;
            this.value = value;
        }
        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void dfs(int depth , int pointNumber , int moveCount){
        /*
        여기서는 depth == dirty.length 가 되면 min을 골라내면 된다.
        그리고 visitedList 들도 게속 방문한 곳은 1 방문하지 않은 곳은 0으로 해준다.
         */
        if(depth == dirty.length - 1){
            min = Math.min(moveCount , min);
            return;
        }

        for(int i = 1; i < dirty.length; i++){
            if(visitedList[i] != 1 && pointNumber != i){
                visitedList[i] = 1;
                dfs(depth + 1 , i , moveCount + dist[pointNumber][i]);
                visitedList[i] = 0;
            }
        }
    }
    public static void bfs(int startPoint){
        /*
        여기서 startPoint를 제외한 dist에다가 값을 집어넣다고
        하나라도 방문하지 못하는 지점이 있으면 0이다.
        그러니 본인을 제외한 모든 지점을 돌 때 그 지점이 0이다?
        그러면 fail = true; 로 해주면 된다.
        그리고서 일단 visited 를 만들고 , distanceList도 만들어서 일단 숫자를 다 적어넣는다.
        거기서 이제 startPoint를 제외하고 0번도 제외하고 다 돌면서 dist를 dist[startPoint][i] 로 하면 된다.
         */
        int[][] visited = new int[h][w];
        int[][] distance = new int[h][w];
        Queue<Point> queue = new LinkedList<>();
        int y = dirty[startPoint].y , x = dirty[startPoint].x;
        queue.add(new Point(y , x , 0));
        distance[y][x] = 0;
        visited[y][x] = 0;

        while(!queue.isEmpty()){
            Point point = queue.poll();
            int value = point.value;
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= h || nx < 0 || nx >= w || visited[ny][nx] == 1 || map[ny][nx] == 'x') continue;
                visited[ny][nx] = 1;
                distance[ny][nx] = value + 1;
                queue.add(new Point(ny , nx , value + 1));
            }
        }

        for(int i = 1; i < dirty.length; i++){
            if(i != startPoint){
                int value = distance[dirty[i].y][dirty[i].x];
                if(value == 0){
                    fail = true;
                }
                dist[startPoint][i] = value;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while(true){
            st = new StringTokenizer(input.readLine());
            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());
            if(w == 0 && h == 0){
                break;
            }
            map = new char[h][w];
            Point robot = null;
            List<Point> dirtyList = new ArrayList<>();
            for(int i = 0; i < h; i++){
                String string = input.readLine();
                for(int j = 0; j < w; j++){
                    map[i][j] =  string.charAt(j);
                    if(map[i][j] == 'o'){
                        robot = new Point(i , j);
                        map[i][j] = '.';
                    }
                    if(map[i][j] == '*'){
                        dirtyList.add(new Point(i , j));
                        map[i][j] = '.';
                    }
                }
            }
            int size = dirtyList.size() + 1;
            dirty = new Point[size];
            dirty[0] = robot;
            for(int i = 1; i < size; i++){
                dirty[i] = dirtyList.get(i - 1);
            }
            visitedList = new int[size];
            dist = new int[size][size];
            min = Integer.MAX_VALUE;
            fail = false;
            for(int i = 0; i < size; i++){
                bfs(i);
            }
            if(fail){
                min = -1;
            }else {
                visitedList[0] = 1; // 로봇은 방문한 상태에서 시작
                dfs(0, 0, 0);
            }
            output.write(min + "\n");
        }
        output.flush();
        output.close();
    }
}