import java.util.*;
import java.io.*;

// 1520 : 내려가기
/*
--전제 조건
항상 지점이 높은 곳에서 , 낮은 곳으로만 이동하였을 때
이동 가능한 방법의 수를 모두 구하시오
--틀 설계
50 60
60 50
일단 처음에는 주변에 인접한 것들 중 , 본인보다 더 큰 것들이 있으면 거기서 그 개수만큼 + 해준다.
이제 두번째는 마지막의 행열부터 , 다시 올라가면서 , 주변의 본인보다 , 더 작아야한다.
이렇게 했을 때 dp 로 값이 구해지나 한번 보자

결국 처음에 생각했었던 메모제이션 방법으로 풀었다.

일단 처음에 시작 지점에서 , dfs 를 시작한다.
일단은 처음에는 무작정 , 목적지 까지 갈 수 있는 경로로 간다.
이제 목적지에 도착하면 거기서 부터 메모이제이션을 적용하는 것이다.

이전에 방문하는 곳마다 , 방문처리를 하려고 dp[y][x] = 0 을 해주었다 , 이것은 방문처리이기도 하면서 , 해당 지점에서의 목적지까지 가는 경로의 수를 의미한다. 그렇기에 첫 방문일 때에는 dp[y][x] = 0인 것이다.
이제 그러면 방문처리를 한다면 , 이미 방문한 곳을 도착하였을 때 , 그 경우가 중요하다 , dfs로 처리가 되면서 어떠한 지점에서 방문을 이미 한 곳으로 이동하게 된다면 , 그 지점에서 목적지까지 가는 경로의 수가 본인의 경우의 수에 추가가 되는 것이다.
왜냐하면 , 해당 지점까지 가기만 하면 해당 경우의 수만큼 목적지에 도달하는 경로가 있으니까 , 그래서 본인의 dp[y][x] 에다가 더해준다. (이것은 물론 이동이 가능한 , 상하좌우 인접한 지점끼리 이동이 가능한 경우에만 해당 연산을 적용하는 것이다.)
해당 경로로 처음 목적지에 도달하였을 때에는 1을 return 해주면서 , 이전에 내가 지나온 경로들에 다 1을 추가해준다고 생각하면 된다. 그 과정이 dp[y][x] += dfs(ny , nx) 이다.
그래서 이렇게 지속적으로 반복하게 되면 , dp[0][0]에 시작점에서 , 목적지까지 가는 경우의 수가 기록되게 된다.
그리고 여기서 이렇게 가능한 이유는 , 항상 높은지점에서 , 낮은지점으로 가는 것이 가능하므로 , 본인이 지나왔던 길을 다시 갈 수가 없다는 특성을 이용해서 가능하다.

그래서 결론은 처음 목적지에 도달하면 , 재귀를 끝내주면서 return 1을 반환하고 , 경우가 1개가 추가 된 것이니까 , 이전의 dp[y][x] 에 추가 되게 된다, 그리고
그 다음에 이미 방문했던 곳에 도달하게 되면 , 이미 그 지점은 4 방향 재귀 탐문을 끝낸 지점이다 , 그렇기 때문에 그 지점에서 목적지까지의 경우의 수는 모두 구한 것이니 , 본인의 경우의 수에 추가한다.
그래서 dfs의 깊이 우선 탐색의 , 특성과 , 시간을 줄일 수 있는 메모이제이션의 특성으로 푼 문제이다.

솔직히 느낌은 오는데 이렇다 하게 확실하게 이해가 되지 않는다.
value return 하는 재귀문이 가장 어려운 느낌이든다.
 */
public class Main {
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static int[][] map , dp;
    public static int w , h ;
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        map = new int[h][w];
        dp = new int[h][w];

        for(int i = 0; i < h; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < w; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1; // memoization
            }
        }

        System.out.println(dfs(0 , 0));
    }
    public static int dfs(int y , int x){
        if(y == h - 1 && x == w - 1){
            return 1;
        }

        if(dp[y][x] != -1){
            return dp[y][x]; // 이미 방문한 곳이다.
        }
        dp[y][x] = 0;
        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];
            if (check(ny, nx)) continue;
            if (map[y][x] > map[ny][nx]) dp[y][x] += dfs(ny, nx);
        }
        return dp[y][x];
    }
    public static boolean check(int y , int x){
        if(y < 0 || y >= h || x < 0 || x >= w) return true;
        return false;
    }
}