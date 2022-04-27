import java.util.*;
import java.io.*;

// 17406 : 배열 돌리기 2

/*
-- 전제조건
회전 연산들이 주어지고 , (r , c , s)
어떻게 회전 연산들을 각각 한번씩 사용해서
배열 A의 값의 최솟값을 구하여 출력하면 된다.

배열 A의 값은 , 각 행의 합 중 가장 최솟값을 의미한다.
-- 틀설계
그냥 회전 시키는 method 정의하고
회전 연산 순서 맘대로 바꾸는 dfs 만 정의하고 진행하면 바로 끝날 듯하다.

양수들만 주어지고 , 범위를 넘어갈 만큼 큰 값들도 없음

-- 해맸던 점
쉬운 문제였는데 , 이상하게 자꾸해서 해맸는데
처음에는 일단 r - 1 , c - 1 값으로 받고 s 는 그대로 받았어야 했는데 다 - 로 받았고
반복문으로 s 돌리는데 , s -- 할때마다 y , x 값 다시 넣어줬어야 했는데 , 그것도 안했었고,
무엇보다 값들을 뒤로 미루는 과정에서 이전에 이상하게 했었음
 */
public class Main {
    public static int[][] initMap , map , rotateList;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0} , sequence;
    public static boolean[] visited;
    public static int H , W , K , res = Integer.MAX_VALUE;

    public static void rotate(int r , int c , int s){
        /*
        그냥 r , c 기준으로 r - s , c - s 에서 시작해서 위 , 오른쪽 , 아래 , 왼쪽으로 진행하면 됨
        내가 맨날 하던 dx , dy 방식이네
        개꿀딱

        여기서 아얘 s 0 될때까지 이거 반복해줌
         */
//        mapPrint(map);
        int y , x , now , pre;
        for(; s > 0; s--) {
            y = r + s;
            x = c - s;
            now = 0;
            pre = map[y][x];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < s * 2; j++) {
                    // 다음꺼에다가 넣으려면 , 다음 것을 기억하고 이전것을 넣어야한다.
                    now = map[y + dy[i]][x + dx[i]];
                    y += dy[i];
                    x += dx[i];
                    map[y][x] = pre;
                    pre = now;
                }
            }
//            mapPrint(map);
        }
    }

    public static void dfs(int index){
        /*
        여기에서는 sequence 에다가 visited 를 이용해서
        회전 연산의 순서만 지정해주면 된다.
        0번부터 시작해서 K - 1 까지 존재하면 된다.
         */
        if(index == K){
            gameStart();
            return;
        }

        for(int i = 0; i < K; i++){
            if(!visited[i]){
                visited[i] = true;
                sequence[index] = i;
                dfs(index + 1); // 정했으니까 다음 단계로
                visited[i] = false;
            }
        }
    }

    public static void gameStart(){
        /*
        여기안에서 rotate 를 dfs 에서 준 sequence 대로 순서대로 진행해준다음
        배열 A 값을 찾으면 된다.
         */

        map = new int[H][W];

        for(int i = 0; i < H; i++){
            System.arraycopy(initMap[i] , 0 , map[i] , 0 , initMap[i].length);
        }

        for(int i = 0; i < K; i++){
            rotate(rotateList[sequence[i]][0] , rotateList[sequence[i]][1] , rotateList[sequence[i]][2]);
        }

        for(int i = 0; i < H; i++){
            res = Math.min(res , calRow(i));
        }
    }

    public static int calRow(int y){
        int sum = 0;
        for(int i = 0; i < W; i++){
            sum += map[y][i];
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        initMap = new int[H][W];
        rotateList = new int[K][3];
        visited = new boolean[K];
        sequence = new int[K];

        for(int i = 0; i < H; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < W; j++){
                initMap[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < K; i++){
            st = new StringTokenizer(input.readLine());
            rotateList[i][0] = Integer.parseInt(st.nextToken()) - 1;
            rotateList[i][1] = Integer.parseInt(st.nextToken()) - 1;
            rotateList[i][2] = Integer.parseInt(st.nextToken());
        }
        dfs(0);

        System.out.println(res);
    }
    public static void mapPrint(int[][] map){
        System.out.println("next");
        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}