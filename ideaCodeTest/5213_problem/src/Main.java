import java.util.*;
import java.io.*;

// 5213 : 과외맨

/*
-- 전제 조건
타일은 크기가 == 2이다.
각 타일은 N 줄이 주어지고,
홀 수 줄은 N 개의 타일 , 짝수 줄은 N - 1 개의 타일이다.

항상 어떠한 타일로 이동하려면 , 그 타일은 변을 공유하는 타일과 , 같은 숫자가 있어야 한다.
즉 , 인접한 조각에 본인과 같은 숫자가 쓰여져 있는 타일이 있어야 한다.

그래서 그렇게 쭉 가서 , 가장 짧은 경로를 출력하면 된다.
여기서 문제의 목적은 가장 짧은 경로를 출력하면서 , 가장 마지막 타일에 도달하는 것이다.

여기서 주의해야 할 점은 , 가장 마지막 타일!에 도착하는 것이다.
그래서 마지막 타일에 도착하는 경우를 출력하면 되고 , 만일 마지막 타일에 도착하지 못하는 경우가 있다면?
가장 큰 타일 번호로 이동하는 경우의 경로를 출력하면 된다.

그리고 조각에 도착할 때 마다 , 그 경로를 출력하는 것이 아닌,
타일마다 경로를 출력해야 함에 주의하자
-- 틀 설계
일단 이 문제 , 맵을 구성하는 것이 중요할 듯 하다.
두 번째 해당 타일이 몇 번째 타일인지 알아야한다.
세 번째 , 타일 순서를 어떻게 갔느냐 그것이 중요하다.

그래서 , 출력은 간단하다 , 그냥 목적지에 먼저 도달한 애가 제일 짧은 경로이다.

그러면 일단 맵을 구성하고 , 내가 가는 곳이 몇번째 타일인지 , 그것만 알아내면 된다.

타일을 어떻게 기록할 수 있을까?
솔직히 움직이는 것 , 이게 몇번째 타일인지 , 그런 것은 해결 할 수 있다.
왜냐하면 타일은 무조건 2개의 조각으로 이루어져 있기 떄문이다.
그렇기 때문에 , 타일은 무조건 적으로 계산이 가능하다 , 이제 그러면 타일을 기록하는 것
그것이 관건인데..

맵을 기록할 때에는 조각은 무조건 1 ~ 6까지로 이루어져 있다라는 것을 기억하자.
이거를 진행할 때 dist 에다가 value 들을 저장해놓는 것이다.

그 다음에 이제 도착하는 경우에 재귀적으로 다시 탐색하면 된다.
시작은 map[N - 1][W - 1] 에서 시작해서 재귀적으로 탐색하면서 ,
도달하면 그 해당 stack 을 result stack 으로 선택한다.

tile(N == 5) 은
0 - 1 , 2 , 3 , 4 , 5
1 - 6 , 7 , 8 , 9
2 - 10 , 11 , 12 , 13 , 14
....

일단 그냥 막연하게 드는 생각은 , 일단은 N개는 이렇게 쫘르륵 넣고
그 다음에 N - 1 개는 쫘르륵 넣고
이렇게 넣은 다음에
타일을 아는 방법은
해당 row에서 몇번째 타일인지 일단 알아내고 , 그 다음에 y를 이용해서
얻는 방법이 있을 것 같다.

-- 결론
이 코드는 결국 틀렸다 , 답은 다 정확하게 나오는데 , 틀려서 슬프다..
 */
public class Main {

    public static int N , H , W , goalTile , maxTile = 0, maxTileValue = 0 , maxY , maxX;
    public static int[][] map , dist;
    public static boolean[][] visited;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static LinkedList<Point> queue = new LinkedList<>();
    public static Stack<Integer> result = new Stack<>();
    public static boolean find = false;

    public static class Point{
        int y;
        int x;
        int value;
        int tile;
        public Point(int y, int x, int value , int tile){
            this.y = y;
            this.x = x;
            this.value = value;
            this.tile = tile;
        }
    }
    public static boolean outOfRange(int y , int x){
        if(y < 0 || y >= H || x < 0 || x >= W) return true;
        return false;
    }
    public static int judgeTileNumber(int y, int x){
        /*
        말 그대로 y , x 를 주면 H , W 를 이용해서 이게 몇번째 타일인지 계산을 해볼 수 있다.
        그래서 이게 몇번째 타일인지를 넘기는 것이다.
         */
        int result;

        if(y % 2 == 0){ // 짝수이면 나중에 딱 맞아 떨어짐
            result = x / 2 + 1;
        }
        else{ // 홀 수이면 나중에 계산할 때 N칸 한개 남음
            result = (x - 1) / 2 + 1;
            result += N;
        }

        result += y / 2 * (N + N - 1);

        return result;
    }
    public static void bfs(){
        /*
        bfs 를 통해서 , 이제 해당 지점이 몇번째 타일인지 , 저장을하고,
        계속 진행을 하면 되는데 ,
        visited 처리도 하고 , 그렇기 때문에 , 일단 갈 수 있는지 확인하고 가는 것이 중요하다.

        그래서 , 계속 tile number 를 확인하고
        tile number 가 바뀌면 value 도 증가시킨다.
        그리고 , 0인 곳은 가지 않는 것도 중요하다. 쨋든 그것을 계속 확인하면서 , 가면서
        찍어내면 되고 , 항상 tileNumber 는 최고로 갱신하고 , 그러면서 tileNumber 가 갱신될 때
        value 값도 갱신한다.
         */

        queue.add(new Point(0 , 0 , 1 , 1));
        dist[0][0] = 1;

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            if(point.tile > maxTile){
                maxTile = point.tile;
                maxTileValue = point.value;
                maxY = point.y;
                maxX = point.x;
            }

            if(point.tile == goalTile){
                break;
            }

            if(visited[point.y][point.x]) continue;
            visited[point.y][point.x] = true;
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if(outOfRange(ny , nx) || map[ny][nx] == 0 || visited[ny][nx]) continue;

                if(!ableMove(ny , nx , point.tile , map[point.y][point.x])) continue;

                int tileNumber = judgeTileNumber(ny , nx);

                if(tileNumber != point.tile){
                    queue.add(new Point(ny , nx , point.value + 1 , tileNumber));
                    dist[ny][nx] = point.value + 1;
                }
                else{
                    queue.addFirst(new Point(ny , nx , point.value , tileNumber));
                    dist[ny][nx] = point.value;
                }
            }
        }
    }
    public static void pathTrace(int value , int y , int x , Stack<Integer> stack){
        /*
        경로를 역추적 해야한다.
        map[H - 1][W - 1] 에서 시작하면 된다.
         */

        if(find) return;
        if(visited[y][x]) return;

        visited[y][x] = true;
        int nowTile = judgeTileNumber(y , x);

        if(nowTile == 1){
            result = (Stack<Integer>)stack.clone(); // clone
            find = true;
            return;
        }

        for(int i = 0; i < 4; i++){ // 주변을 탐색하면서 , 본인과 타일이 같다면 value 가 같은 것을 선택
            // 아니라면 stack 에다가
            int ny = y + dy[i];
            int nx = x + dx[i];

            if(outOfRange(ny , nx) || visited[ny][nx] || dist[ny][nx] == 0 || map[ny][nx] == 0) continue;

            if(ableMove(ny , nx , nowTile , map[y][x])){ // 갈 수 있는데면
                int newTile = judgeTileNumber(ny , nx);

                if(nowTile == newTile){ // 현재의 타일과 같은 타일이면
                    if(value == dist[ny][nx]) pathTrace(value , ny , nx , (Stack<Integer>)stack.clone());
                }
                else{
                    if(value - 1 == dist[ny][nx]) { // 현재의 타일과 같지 않은 타일이면
                        stack.add(newTile);
                        pathTrace(value - 1 , ny , nx , (Stack<Integer>)stack.clone());
                    }
                }
            }
        }
    }
    public static boolean ableMove(int y, int x , int tile , int pre){
        // y , x 가 갈 수 있는 데인지 확인
        // 아 생각보다 단순한 거였음
        // 본인이 가려는 곳의 타일이 본인의 타일 넘버와 같다면 그냥 이동할 수 있다.
        // 다른 타일로 넘어가려면 본인과 그 타일의 넘버가 같아야한다.
        // 그래서 넘어온 tile 번호를 가지고 y , x 의 tile number가 같으면 그냥 true
        // 아니면 두개의 숫자가 같으면 true
        boolean able = false;

        int newTile = judgeTileNumber(y , x);

        if(newTile == tile) able = true;
        else{
            if(map[y][x] == pre) able = true;
        }

        return able;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        H = N;
        W = 2 * N;

        map = new int[H][W];
        dist = new int[H][W];
        visited = new boolean[H][W];
        goalTile = N * N - N / 2;

        int y = 0;
        int x = 0;
        int count = 0;
        // N줄이 반복되고 , 홀수줄은 1개의 타일이 덜 들어오니까 N * N - N / 2 이다.
        for(int i = 0; i < N * N - N / 2; i++){
            st = new StringTokenizer(input.readLine());
            // 어떻게 하면 map에다가 넣을 수 있을까 ?
            // i == 0 , 0 1 , i == 1 2 3 ... i == 5 0 1... 이런식으로 이어진다.
            if(y % 2 == 0){ // 홀수 줄
                map[y][x++] = Integer.parseInt(st.nextToken());
                map[y][x++] = Integer.parseInt(st.nextToken());
                count++;
                if(count == N){
                    y++;
                    x = 1;
                    count = 0;
                }
            }
            else{ // 짝수 줄
                map[y][x++] = Integer.parseInt(st.nextToken());
                map[y][x++] = Integer.parseInt(st.nextToken());
                count++;
                if(count == N - 1){
                    y++;
                    x = 0;
                    count = 0;
                }
            }
        }

        bfs();
//        for(int i = 0; i < H; i++) System.out.println(Arrays.toString(dist[i]));
        visited = new boolean[H][W];
        Stack<Integer> stack = new Stack<>();
        stack.add(maxTile);
        pathTrace(maxTileValue , maxY , maxX , (Stack<Integer>)stack.clone());

        output.write(maxTileValue + "\n");

        while(!result.isEmpty()){
            output.write(result.pop() + " ");
        }

        output.flush();
        output.close();
    }
}
