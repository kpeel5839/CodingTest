import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1103 : 게임

/**
 * -- 전제 조건
 * 동전이 주어지고,
 * 각 칸에는 숫자가 적혀있다.
 * 그 숫자만큼 위, 아래, 오른쪽, 왼쪽으로 움직일 수 있다.
 * 그랬을 때, 무한대로 움직일 수 있다면 -1, 아니라면 이동할 수 있는 횟수를 출력하라.
 * 나가는 움직임까지 포함이다.
 *
 * -- 틀 설계
 * Dynamic programming 과, dfs 를 쓰는 문제이다.
 * 일단, 무한대를 판별하는 것은 dfs 상으로 움직일 때, 모든 격자들을 다 돌고서도 움직임이 남아있다면?
 * 그거는, 이미 이동하였던데를 또 이동했다라는 뜻으로 판별이 된다.
 *
 * 그래서, dp 배열로, 그 칸에서 최대로 움직일 수 있는 칸의 개수를 저장하면서,
 * 그리고, dfs 상에서 현재 움직임이 움직인 칸이 전체 칸수를 넘어가게 된다라면?
 * 그러면 바로 INF 로 처리하고 끝내면 될 것 같다.
 *
 * -- 해결한 방법
 * 되게 신기한 방법으로 진행했음
 * 그냥, 한 지점에 5번 이상 방문하면 INF 로 했음 사실 4번으로 해도 될 것 같음
 * 2번도 가능할 것 같기도하고
 * 왜냐하면, 다시 방문하는 경우는 다른 지점으로 돌아서 오는 경우
 * 그리고, 우리가 원하는 사이클이 형성된 경우가 있음
 * 다른 지점에서 오는 경우는 3가지의 방향
 * 그리고, 사이클이 형성된 경우는 1가지 방향? 이라고 할 수 있다.
 * 그래서 4번 정도 방문하면, 사이클 형성된 거 아닐까 했는데 맞음
 *
 * 다시 깔끔하게 코드를 정리해보았는데,
 * 그냥 이거였음
 * dp[y][x] 값이 결정되지 않았다라는 것은?
 * 이 정점을 방문하였을 때의, 그 경로의 경우가 끝나지 않았다라는 것,
 * 즉 아직 이 지점은 4방향을 다 탐색하지 않은 상태라는 것이다.
 *
 * 근데, 이 상태에서 2번 이상을 방문하게 되면?
 * 당연히, 사이클이 생겨서 다시 돌아온 것으로밖에 설명이 되지 않는다.
 *
 * 만일, 그냥 사이클이 아니라 다른 경로를 통해서 해당 지점에 온다라면?
 * 이미 해당 dp[y][x] 는 결정이 되었을 것이다.
 *
 * 그래서 return dp[y][x] 를 하게 된다.
 *
 * 하지만, 사이클이 형성된 경우에는 당연하게도 dp[y][x] 값이 결정되지 않은 상태에서,
 * 해당 정점을 2번 이상 방문하게 된다.
 *
 * 그래서, 그냥 해당 정점에 2번 방문을 하게 되면 (아직 dp[y][x] 가 결정 되지 않은 상태에서)
 * 사이클이 형성되었다고 간주하고 끝내게 된다.
 */
public class Main {
    public static int H;
    public static int W;
    public static int[][] dp;
    public static char[][] map;
    public static int[][] visited;
    public static int[] dx = {0, 1, 0, -1};
    public static int[] dy = {-1, 0, 1, 0};
    public static boolean INF = false;

    public static int dfs(int y, int x, int count) {
        if (INF) { // INF 판정이 나면,
            return 0;
        }

        if (dp[y][x] != 0) { // 이미 최대 개수가 정해진 경우에는 이렇게 하면 된다.
            return dp[y][x];
        }

        visited[y][x]++;

        if (visited[y][x] >= 2) { // 한 지점을 5번 이상 방문하게 되면 INF?
            INF = true;
            return 0;
        }

        int result = 0;

        for (int i = 0; i < 4; i++) {
            int ny = y + ((map[y][x] - '0') * dy[i]);
            int nx = x + ((map[y][x] - '0') * dx[i]);

            if (outOfRange(ny, nx) || map[ny][nx] == 'H') { // 범위를 나가는 경우
                continue;
            }

            result = Math.max(result, dfs(ny, nx, count + 1)); // 그것이 아니라면, dfs 를 이용한다.
        }

        dp[y][x] = result + 1;
        return dp[y][x];
    }

    public static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]);
        W = fun.apply(input[1]);

        map = new char[H][W];
        dp = new int[H][W];
        visited = new int[H][W];

        for (int i = 0; i < H; i++) {
            String string = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j);
            }
        }

        int res = dfs(0, 0, 0);

        if (INF) { // 무한인 경우는 -1
            System.out.println(-1);
        } else { // 아닌 경우는 횟수 출력
            System.out.println(res);
        }
    }
}
