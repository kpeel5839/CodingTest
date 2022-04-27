import java.util.*;
import java.io.*;

// 17070 : 파이프 옮기기
/*
-- 전제조건
유현이가 새 집으로 이사했다.
새 집의 크기는 n * n 의 격자판으로 나타낼 수 있다
집 수리를 위해서 파이프 하나를 옮기려고 하는데 파이프는 회전 시킬 수 있으며 , 3가지의 방향이 존재한다.
파이프는 항상 빈칸만 차지해야한다. 그리고 파이프를 밀 수 있는 방향은 총 3가지 밖에 없다.
오른쪽 , 우하 방향 , 아래 방향이다 , 파이프는 밀면서 회전 시킬 수 있는데 회전은 45도만 시킬 수 있으며 , 미는 방향은 앞서 말한 방향이다.
파이프가 가로로 놓여진 경우에 가능한 이동방법은 오른쪽 , 우하 , 그리고 대각으로 가는 경우에는 무려 3방향이 비어있어야한다.
그리고 가로 , 세로로 놓여져 있는 경우에는 이동 방법이 2가지이지만 , 대각으로 놓여져있을 떄에는 무려 방법이 3개나 존재한다.
가장 처음에 파이프는 (1, 1)과 (1, 2)를 차지하고 있고 , 방향은 가로이다 , 파이프의 한쪽 끝을 (N, N)으로 이동시키는 방법의 개수를 구해보자.
빈칸은 0 , 벽은 1로 주어진다. (총 방법의 수를 구한다라는 것을 잊으면 x)
-- 틀 설계
입력을 받고 dfs로 풀면 풀릴 것 같다.
map을 일단 받아준 다음에 dx = {1 , 1 , 0} , dy = {0 , 1 , 1} 이렇게 선언해놓고서
해당 위치와 dir을 계속 가지고 가면서 가면 된다. 듯하다.
그래서 끝까지 즉 (n - 1) , (n - 1) 까지 간 경우인 방법의 수를 출력하면 된다.
그러면 (n - 1) , (n - 1) 까지 도달했으면 ans++ , return 을 하면 되는 것
그리고 파이프는 2칸을 차지하는데 짜피 지나온 칸은 신경안 써도 되니까 파이프의 끝이 위치하는 공간만 생각하면 된다.
 */
public class Main {
    public static int n , ans = 0;
    public static int[][] map;
    public static int[] dx = {1, 1, 0} , dy = {0 , 1 , 1}; //0 = 우 , 1 = 우하 , 2 = 하
    public static void dfs(int y ,int x, int dir){
        /*
        해당 받은 지점과 dir을 보고 갈 수 있는 위치를 판단할 것이다.
        일단 start , end 값을 정의하면
        0 = 0 , 1
        1 = 0 , 1 , 2
        2 = 1 , 2
        그리고서 그냥 해당 다음 칸을 갈 때 갈 수 있냐 없냐 판단하고 갈 수 있으면 간 위치와 dir 로 dfs 를 재호출 하면 된다.
         */
        if(y == n - 1 && x == n - 1){
            ans++;
            return;
        }
        int end;
        int start;
        if(dir == 0){
            start = 0;
            end = 1;
        }
        else if(dir == 1){
            start = 0;
            end = 2;
        }
        else{
            start = 1;
            end = 2;
        }
        for(int i = start; i <= end; i++){
            int ny = y + dy[i];
            int nx = x + dx[i];
            if(ny < 0 || ny >= n || nx < 0 || nx >= n || map[ny][nx] == 1){
                continue;
            }
            if(i != 1){ //이거는 그냥 이미 위에서 검사함 , 그래서 그냥 다음 위치로 넘기면 된다.
                dfs(ny , nx , i);
            }else{ //짜피 이거 안넘는 거 검증 되었음 그래서 위 , 왼쪽이 다 있을 수 밖에 없음
                if(map[ny - 1][nx] != 1 && map[ny][nx - 1] != 1){
                    dfs(ny , nx , i);
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());
        map = new int[n][n];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 1 , 0);

        System.out.println(ans);
    }
}
