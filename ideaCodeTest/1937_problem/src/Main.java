import java.util.*;
import java.io.*;

// 1937 : 욕심쟁이 판다
/*
-- 전제 조건
N * N 크기의 대나무 숲이 주어져 있을 때 , 판다가 최대한 많은 칸을 이동하려면 어떤 경로를 통하여 움직여야 하는지
구하여라
-- 틀 설계
이것도 그냥 Top - Down 방식으로 풀면 될 것 같다.
각 배열에다가는 여기에 방문하면 추가적으로 몇칸을 갈 수 있다라는 최대 값들을 저장하고,
거기에 재 방문하게 되면 그 값을 돌려받는 식으로 진행하면 될 것 같다.


 */
public class Main {
    public static int N , ans = 0;
    public static int[][] dp;
    public static int[][] map;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static int dfs(int y, int x){
        /*
        이미 방문한 곳이면 해당 지점을 방문하였을 때 더 갈 수 있는 최대 개수를 반환하고,
        아니라면 4 방향을 다 본다음에 , 더 이상 갈 수가 없다면 1을 반환하고,
        아니라면 그냥 해당 방향을 호출해준다.
        그 다음에 , 또 본인이 받은 값을 반환해준다.
         */
        if(dp[y][x] != -1) return dp[y][x];

        boolean able = false;

        for(int i = 0; i < 4; i++){
            int ny = y + dy[i];
            int nx = x + dx[i];

            if(outOfRange(ny ,nx) || map[y][x] >= map[ny][nx]) continue;

            able = true; // 여기까지 왔다라는 것은 가능하다라는 것
            dp[y][x] = Math.max(dp[y][x] , dfs(ny, nx) + 1); // 재귀 호출 진행해주면서 dfs로 하는 경우는 본인에서 한칸 더 가는 거니까 1추가해주고
            // 만약 돌아왔는데 현재 본인이 가지고 있는 수보다 적다 , 이전에 진행해보았단 dfs 값이 더 크다 , 그러면 dp[y][x]를 그대로 선택
            // 아얘 개념 자체를 dp[y][x] 는 본인을 포함한 여기서 갈 수 있는 최대의 개수를 의미한다. (그래서 dfs(ny ,nx) + 1 하는 것임)
        }

        if(able) return dp[y][x];
        else return 1;
    }

    public static boolean outOfRange(int y, int x){
        if(y < 0 || y >= N || x < 0 || x >= N) return true;
        return false;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        map = new int[N][N];
        dp = new int[N][N];

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1;
            }
        }

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                ans = Math.max(dfs(i , j)  ,ans);
            }
        }

        System.out.println(ans);
    }
}
