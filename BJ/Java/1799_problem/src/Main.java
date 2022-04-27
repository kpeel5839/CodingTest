import java.util.*;
import java.io.*;

// 1799 : 비숍
/**
 * -- 전제 조건
 * 비숍을 놓을 수 없는 칸 놓을 수 있는 칸이 주어졌을 때 ,
 * 체스 규칙에 의거하여 , 최대의 말의 개수를 놓는 경우의 수를 구하여라
 * 최대 n은 10이고 일단 간단한 백트래킹을 이용해서 한번해보자.
 * -- 틀 설계
 * 색종이 문제 처럼 , 일단 놓을 수 있다면 대각으로 표시를 해준다.
 * 그리고서 표시를 표시가 되어 있는 곳은 그냥 넘어가준다.
 *
 * 그리고 , 유효하지 않다는 게 밝혀졌을 때에는 , 이전에 대각으로 뿌려놓은 표시를 거둬들이고
 * 그 다음에 본인 체스말을 여기다가 뒀다는 것을 지운다.
 *
 * 그러면 일단 able 로 map 을 받는다 , 1이 놓을 수 있는 곳이다.
 * 그 다음에 map 에다가 그려넣는다 , 체스 말은 2 , 놓지 못하는 칸은 1 , 안 놓아진 칸은 0으로 하자.
 * 그리고 dfs 를 돌리면서 계속 ans 를 갱신한다.
 *
 * 분명히 답이 나올 것인데 , 10초만에 가능할 것인지는 잘 모르겠다.
 *
 * dfs 에서 이전에 그렸던 것을 지우는 것은 queue 에다가 담아놨다가 지우면 될 것 같다.
 */
public class Main{
    public static int N , ans = 0;
    public static int[][] able , map;

    public static class Point{
        int y;
        int x;
        public Point(int y, int x){
            this.y = y;
            this.x = x;
        }
    }

    public static void dfs(int y, int x , int count){
        /**
         * 여기서는 그냥 y , x 를 증가시키면서 , y == N 이 되면 y++ x = 0 으로 진행해주고
         * 그게 아니라면 x 를 증가시켜준다.
         * 그리고 , if(y == N , x == 0) 이라면 return 을 해준다. (즉  [n - 1][n - 1] 이 끝나고 나서 끝낸다)
         * 그리고 무조건 ans 를 계속 count 와 갱신시켜주고
         * 그리고 필터링이 끝나면 queue 를 선언해서 , 왼쪽 대각과 , 오른쪽 대각을 처리를 해준다.
         * 대각을 처리하는 방법은 x++ , y++ 과 , y++ , x-- 가 존재한다.
         * outOfRange 함수를 만들어서 큐에다가 담다가도 범위가 끝나게 되면 넘어간다.
         *
         */
        ans = Math.max(count , ans);

        if(y == N && x == 0) {
//            mapPrint();
            return;
        }

        if(map[y][x] == 1 || able[y][x] != 1) {
            if(x == N - 1) dfs(y + 1 , 0 , count);
            else dfs(y , x + 1 , count);
        }

        Queue<Point> queue = new LinkedList<>();

        /**
         *
         * 이제 반복문으로 선택하거나 안하는 경우로 넘어간다
         * 그리고 queue 에다가 담은 다음에 dfs로 넘기는데
         * 그 때 중요한 점은 y, x 의 값을 정확히 넘기는 일이고
         * 이제 끝내고 돌아왔을 떄에는 해당 지점을 다시 0 으로 바꾸고
         * queue 에다가 담아놓은 것을 다시 0으로 바꾼다 (1로 바뀌어 있었음 , 이전 체스가 잡아먹는 공간이라서)
         *
         * 내가 애초에 한 설계 자체가 먼저 체스말을 놔야 한다.
         * 그런 다음에 , 거기서 놓냐 안놓냐가 결정되는 것이라서 , 먼저 선택해야 한다.
         *
         * 그리고 queue 에다가 넣을 때에도 , 이전에 못놓는 range 를 지워버리면 안되기 때문에 ,
         * 이미 1이 들어가있는 것은 queue 에다가 집어넣을 때 , 제외하고 집어넣어야 한다.
        */

        for(int i = 0; i < 2; i++){
            if(i == 1){ // 선택하지 않는 경우
                if(x == N - 1) dfs(y + 1, 0 , count);
                else dfs(y , x + 1 , count);
            }
            if(i == 0){ // 선택하는 경우
                if(map[y][x] == 1) continue;

                int ny = y;
                int nx = x;

                map[y][x] = 2;

//                System.out.println("y : " + y + " x : " + x);
//                mapPrint();

                while(true){ // 왼쪽
                    ny++;
                    nx--;

                    if(outOfRange(ny , nx)) break;
                    if(map[ny][nx] == 1) continue;

                    map[ny][nx] = 1;
                    queue.add(new Point(ny , nx));
                }

                ny = y;
                nx = x;

                while(true){
                    ny++;
                    nx++;
                    if(outOfRange(ny , nx)) break;
                    if(map[ny][nx] == 1) continue; // 1인 경우는 넘어감

                    map[ny][nx] = 1;
                    queue.add(new Point(ny , nx));
                }

                if(x == N - 1) dfs(y + 1 , 0 , count + 1);
                else dfs(y , x + 1 , count + 1);

                map[y][x] = 0; // 다시 돌려놓음

                while(!queue.isEmpty()){ // 다시 대각으로 처리해놓은 거 돌려놓기
                    Point point = queue.poll();
                    map[point.y][point.x] = 0;
                }
            }
        }
    }

    public static boolean outOfRange(int y, int x){
        if(y < 0 || y >= N || x < 0 || x >= N) return true;
        return false;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        able = new int[N][N];
        map = new int[N][N];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < N; j++){
                able[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0 , 0 , 0);

        System.out.println(ans);
    }

    public static void mapPrint(){
        System.out.println("next");
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}