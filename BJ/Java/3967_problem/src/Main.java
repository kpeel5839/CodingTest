import java.util.*;
import java.io.*;

// 3967 : 매직 스타

/*
-- 전제 조건
매직스타의 조건은 하나이다.
4개의 숫자의 합이 26이 되면 된다.

그리고 답은 사전순으로 가장 앞서는 것을 출력하면 된다.

ascii 코드는 A == 65 , Z == 90 이다.
즉 ascii 코드가 A == 1 부터 시작한다.

같은 문자가 여러번 쓰여도 된다.
사전순으로 가장 앞서기만 하면 될 듯
-- 틀 설계
그냥 스도쿠 문제 처럼 진행하면 될 것 같다.
근데 해당 범위를 찾는 과정이 까다로울 것 같다.

일단 해당 알파벳들을 모두 숫자로 바꾸면서 받는다.
그러면서 나중에 출력할 때 , 숫자에다가 64를 더한다음에 char로 변환시키면 된다.

dfs를 돌리면 될 것 같다.
일단 비어있는 부분들을 담는다.

X로 표기되어 있는 곳을 담으면 된다.
방향은 4방향이다
처음은 왼쪽 , 오른쪽

그리고 , 왼쪽 윗 대각 , 아래 오른쪽 대각
그 다음에 오른쪽 윗 대각 , 아래 왼쪽 대각

이렇게 3가지의 경우가 존재하고 어떤 지점이든 꼭
무조건 2가지의 경우가 존재한다.

일단 map을 받을때 모든 . 은 -1 로 받는다.
그리고 모든 X는 0 (합에 영향을 주지 않도록)
서로 합이 존재할 것이다.
그 중에 작은 수에 맞춰서 1 부터 그 수까지 순서대로 넣으면 된다.

그것을 계속반복한다.

-- 해맸던 점
일단 처음에는 합이 26이 되는 경우는 고려하지 않았다.
그러다가 구문을 발견하게 되었다.
A ~ L 까지만 들어온다고
이거를 ascii code 로 변환하게 되면 65 ~ 76 즉 숫자로 변경하게 되면
1 ~ 12 만 들어오는 것이였다.

1 ~ 12 까지 순서대로 들어오게 된다면 , 아니 필수적으로 모든 자리 수가 26이 되려면 1 ~ 12라는 숫자가 전부 다 존재해야지 그 경우가 가능하다.
그래서 중복된 알파벳도 그냥 들어오게 했던 것을 안되는 것으로 바꿔서 적어도 합들이 26이 될 확률을 맞춰줬고,

해당 limit 을 구해서 (해당 위치에 들어갈 수 있는 가장 큰 수)

그리고서 , 1 ~ 12 까지 i로 지정해서 순서대로 집어넣는데 , 이 경우에 if(limit < i) 즉 i 가 limit 보다 크면
즉 들어올 수 없는 수가 들어오면 , 다시 바꿔말하면 이 수 가 들어오면 합이 26이 넘어버리는 수를 배제했다.

그랬더니 마법같이 풀렸음

완전 진짜 스도쿠 하위호환판이였음 , 아마 스도쿠를 풀지 못했다면 해맸지 않았을까.
그리고 dx , dy 도 원래는 1차원 배열 여러개로 표현하다가,
그냥 2차원 배열로 표현하니까 2중 포문으로 깔끔하게 해결 할 수 있었음
 */
public class Main {
    public static BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
    public static List<Point> zeroPoint = new ArrayList<>();
    public static final int H = 5 , W = 9;
    public static int[][] map = new int[H][W];

    public static int[][] dx = {{-1 , 1} , {1 , -1} , {1 , -1}} , dy ={{0 , 0} ,{-1 , 1} , {1 , -1}};
    public static boolean finish = false;
    public static boolean[] visited = new boolean[13]; // false 이면 방문 x , true 이면 방문 o

    public static int findMax(Point point){
        /*
        해당 지점이 주어지면 1 , 2 , 3 방향을 순서대로 실행하면서
        두 개 중 더 낮은 숫자를 반환한다.

        1 , 2 , 3 모든 방향으로 시도한 다음에 만일 innerRes 가 그대로이면
        res 에 넣지 않고,
        innerRes 가 그대로가 아니라면 res 에 Math.max(innerRes , res) 해서 넣어준다.
        해당 범위가 outOfRange 가 될 때까지 진행해주고,
        그리고 -1 , 0은 무시한다.
         */

        int res = 0;

        for(int i = 0; i < 3; i++){ // 모든 방향으로 다 시도
            int innerRes = 0;
            for(int j = 0; j < 2; j++){
                int ny = point.y;
                int nx = point.x; // ny , nx 초기화 , 양 방향으로 뻗어 나가야 하니까
                while(!outOfRange(ny = ny + dy[i][j] , nx = nx + dx[i][j])){
                    if(map[ny][nx] == 0 || map[ny][nx] == -1) continue; // 0 이거나 -1 이면 넘어감

                    innerRes += map[ny][nx]; // 아니며 더함
                }
            }
            res = Math.max(innerRes , res);
        }

        return 26 - res; // 남은 수를 return 하는 것은 그대로 가고
    }

    public static void dfs(int index) throws IOException{
        /*
        zeroPoint 의 index 를 넘겨 받고
        해당 point를 인도하면 해당 값의 min 값을 넘겨 받고
        해당 것을 지금 하려는 숫자로 바꾸고 다음 index 로 넘어간다.

        그리고 다시 갔다가 돌아오면 끝
         */

        if(finish) return;

        if(index == zeroPoint.size()) {
            finish = true;
            for(int i = 0; i < H; i++){
                for(int j = 0; j < W; j++){
                    if(map[i][j] == -1) output.write('.' + "");
                    else output.write((char)(map[i][j] + 64) + "");
                }
                output.write("\n");
            }
            return;
        }

        Point point = zeroPoint.get(index);

        int limit = findMax(point);

        // 동일한 것만 못가게 막는다.
        for(int i = 1; i < 13; i++){
            if(visited[i]) continue; // 방문 처리 하고
            if(limit < i) continue; // limit 보다 i 가 큰 경우에서는 그냥 넘기기로 한다.
            map[point.y][point.x] = i;
            visited[i] = true;
            dfs(index + 1);
            map[point.y][point.x] = 0;
            visited[i] = false;
        }
    }

    public static boolean outOfRange(int y, int x){
        if(y < 0 || y >= H || x < 0 || x >= W) return true;
        return false;
    }
    public static class Point{
        int y;
        int x;
        public Point(int y, int x){
            this.y = y;
            this.x = x;
        }
        @Override
        public String toString(){
            return "y : " + y + " x : " + x;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        for(int i = 0; i < H; i++){
            String string = input.readLine();
            for(int j = 0; j < W; j++){
                char character = string.charAt(j);

                // . == -1
                // x == 0
                // 알파벳 == int(알파벳) - 64
                // 나중에 이렇게 복호화 할 것임
                if(character == 'x') zeroPoint.add(new Point(i , j));

                if(character == '.') map[i][j] = -1;
                else if(character == 'x') map[i][j] = 0;
                else {
                    map[i][j] = (int)character - 64;
                    visited[map[i][j]] = true; // 이미 들어온 알파벳들
                }
            }
        }

        dfs(0);

        output.flush();
        output.close();
    }
}
