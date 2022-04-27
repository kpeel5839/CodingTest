import java.util.*;
import java.io.*;

// 2133 : 타일 채우기
/*
--전제조건
3 * N 크기의 벽을 2 * 1 , 1 * 2 의 타일로 채우는 경우의 수를 구하자
N 은 최대 30이다.
N이 2라면 3이 나온다. 이럴때는 3 * 2 에다가 2 * 1 , 1 * 2를 채워넣은 경우의 수를 구해야하는 것
(r , c) 즉 3 이 행 길이이고 n 이 열 길이이다.
--틀 설계
음 일단은 재귀로 짰다면 0, 0 에서 부터 시작해서 가로로 놓는 경우 , 세로로 놓는 경우 , 그 다음에 계속 해서
다음 지점을 오른쪽 아니면 아래로 할 것 같다.
그러면서 계속 분기하는 그런느낌으로 갈 것 같다.그러고서 n , n에 도달하는 그런 느낌으로
이미 차있으면 넘어가고 만약에 차있지 않은데 넣을 수가 없다, 그러면 이 방법은 취소하는 그런느낌으로
그럴려면 일단 [0][0] -> [0][1] -> [0][2] ... [1][0] ..... [2][0] ..... [2][n - 1]까지 계속 도는 느낌?
그런느낌으로 갈 것 같다 재귀호출로 하면
음 일단 3 * 1 은 안된다
그러면 3 * 2 부터 가능한 건데
이차원 배열로 만든다고 가정했을 때

3 , n - 1이 답이라고 했을 때

해당 코드는 재귀호출 버전임
 */
public class Main {
    public static int count = 0 , n;
    public static int[][] map;
    public static void dfs(int y , int x){
        /*
        0 은 가로 , 1은 세로이다.
        그러면 이제 두개의 포인트를 y1 , y2 , x1 , x2를 준비하고
        돌아오면 다시 새로 0으로 만들어줘야 한다 이전에 했던 것을
        그 다음에 ny ,nx 범위를 넘나 안넘나 확인한다.
        넘지 않는다면 해당 지점이 1이 아닌지도 확인해주어야 한다.
        그렇게 하면 쉽게 구할 수 있을 듯
         */
        if(y == 2 && x == n - 1 && map[y][x] == 1){
            count++;
            return;
        }

        if(map[y][x] == 1){
            if(x == n - 1){
                dfs(y + 1 , 0);
            }else{
                dfs(y , x + 1);
            }
        }else{
            /*
            여기로 왔다면 이 칸은 비어있다는 것인다 for문에서 아무것도 두지 못한다?
            그러면 나가리이다.
            그래서 exit 변수를 만들어서 continue 될 때마다 count++ 해준다
            이 값이 2라면 그냥 여기서 return 해준다.
             */
            int count = 0;
            for(int i = 0; i < 2; i++){
                int ny = y;
                int nx = x;
                if(i == 0){ // 가로
                    nx += 1;
                }
                else{ // 세로
                    ny += 1;
                }
                if(ny < 0 || ny >= 3 || nx < 0 || nx >= n || map[ny][nx] == 1){
                    count++;
                    continue;
                }
                map[y][x] = 1;
                map[ny][nx] = 1;
                if(x == n - 1){
                    dfs(y + 1 , 0);
                }else{
                    dfs(y , x + 1);
                }
                map[y][x] = 0;
                map[ny][nx] = 0;
            }
            if(count == 2){
                return;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(input.readLine());
        map = new int[3][n];

        dfs(0 , 0);
        System.out.println(count);
    }
}