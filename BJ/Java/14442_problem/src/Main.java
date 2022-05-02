import java.util.*;
import java.io.*;
import java.util.function.Function;

// 14442 : 벽 부수고 이동하기 2

/**
 * -- 전제 조건
 * 그냥 벽을 부시고 이동하면 더 빠르면 벽을 부시고 이동하고,
 * (N, M) 에 도착하면 된다.
 *
 * 그대신 K 가 주어지고, K 개의 벽까지만 부실 수 있다.
 * -- 틀 설계
 * 이 문제는 벽 부시고 이동하기 3 ? 그것보다 더 쉬운 문제이고,
 * 똑같은 부류의 문제이다.
 *
 * 이 문제의 핵심은 bfs 의 특징을 이용하는 것이다.
 * bfs 의 특징은, 먼저 해당 지점에 도착하면, 그것은 무조건 적으로 최단 경로라는 것이다.
 * 그러면 다음에 오는 애는, 최단 경로가 아니다.
 *
 * 하지만, 이 경우에 K가 더 적다면 올 수 있다.
 *
 * 즉, map 에다가 부신 벽의 수를 기록하고,
 * 거기에 도달하였을 때, 거기가 벽이 아니라 빈 공간이라면,
 * 벽을 덜 부셨다면, 나중에 도착하더라도 괜찮다.
 *
 * 하지만, 이제 벽을 부시는 상황을 생각해보자.
 * 벽이 있는 공간에, 가려면 두가지의 조건을 고려해야 한다.
 *
 * 첫번째, 현재까지 부신 벽이 K 미만인가?
 * 두번째, 이 벽을 부셨을 때, 이전에 이미 여기에 도달했던 애보다 벽을 덜 부셨냐?
 * 즉, 현재 이 벽을 부시고 들어왔을 때, 이전에 이 벽을 부신애와 같아도 안된다.
 *
 * 무조건 적게 부셔야 한다.
 * 솔직히, 이러한 조건들은 다 수행시간을 줄이기 위함이다.
 * 만일, 수행시간이 상관이없다면 이런거 다 알빠 아니다.
 * 그냥 다 부셔보고 가면 된다.
 *
 * 하지만, 시간 내에 탐사를 성공해야 하기 떄문에, 이런식으로 처리를 해줘야 한다.
 *
 * 쉽게 맞았음
 */
public class Main {
    public static int H;
    public static int W;
    public static int K;
    public static int res = -1;
    public static final int INF = (1000 * 1000) + 1; // 적어도 벽의 개수가 이것보다는 적음
    public static Queue<int[]> queue = new LinkedList<>();
    public static int[][] visited; // 여기에 도달한놈이 있나 없나, 있다면 부신 벽의 개수를 기록
    public static int[][] map; // 벽인지 아닌지 기록
    public static int[] dx = {0, 1, 0, -1};
    public static int[] dy = {-1, 0, 1, 0};

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
        K = fun.apply(input[2]);

        map = new int[H][W];
        visited = new int[H][W];

        for (int i = 0; i < H; i++) {
            Arrays.fill(visited[i], INF);
        }

        for (int i = 0; i < H; i++) {
            String string = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j) - '0';
            }
        }

        // queue 에다가 위치와, 부신 벽의 개수, 그리고 이동한 개수를 집어넣고, 처음에 1로 집어넣는다.
        queue.add(new int[] {0, 0, 0, 1});
        visited[0][0] = 0; // 부신 벽의 개수는 처음에는 무조건 0

        while (!queue.isEmpty()) {
            int[] point = queue.poll();

            if (point[0] == (H - 1) && point[1] == (W - 1)) {
                res = point[3];
                break;
            }

            for (int i = 0; i < 4; i++) {
                int ny = point[0] + dy[i];
                int nx = point[1] + dx[i];

                if (outOfRange(ny, nx)) {
                    continue;
                } // 일단 범위 나가면 볼 것도 없음

                // 두가지의 경우, 벽인 경우와 아닌 경우
                if (map[ny][nx] == 1) { // 벽인 경우
                    // 벽인 경우는, K 보다 지금 point[2] 가 더 낮고, visited - 1 에 기록 되어 있는 것보다 낮아야함
                    if (point[2] < K && (point[2] + 1) < visited[ny][nx]) { // 벽을 부시면 point[2] + 1 이 되고, 적어도 늦게 도착했는데 이것보다 이득이려면 visited[ny][nx] 보다 낮아야함
                        visited[ny][nx] = point[2] + 1; // 더 늦게 도착하는 애는 이것보다 낮아야함
                        queue.add(new int[] {ny, nx, visited[ny][nx], point[3] + 1});
                    }
                } else if (point[2] < visited[ny][nx]) { // 부신 벽의 개수가, 현재 가려는 칸에 먼저 도착한 애가 벽을 부신 개수보다 적어야함
                    visited[ny][nx] = point[2];
                    queue.add(new int[] {ny, nx, visited[ny][nx], point[3] + 1});
                }
            }
        }

        System.out.println(res);
    }
}
