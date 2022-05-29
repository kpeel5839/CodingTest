import java.util.*;
import java.io.*;
import java.util.function.Function;

// 5558 : チーズ (Cheese)

/**
 * -- 전제조건
 * 쥐는 지보다 더 높은 체력의 치즈를 먹을 수가 없다
 * 그러면 순서대로 쥐가 치즈를 먹는다고 했을 때,
 * 최소 이동거리를 구하여라.
 *
 * H, W, N 이 주어지고
 * 최소 이동거리를 출력하면 된다.
 * S 가 쥐의 초기 위치이다.
 * 치즈를 넘어갈 수 있다 하지만 장애물은 넘을 수 없다.
 * -- 틀 설계
 * 그냥 단순 bfs 이다.
 * 근데, 초기에 1, 2, 3 .. 까지의
 * 치즈들의 위치를 저장하고, 치즈를 먹을 때마다, 초기화 해야할 것 같긴하다. (visited 배열을)
 * 그러면 문제없을 것 같다.
 *
 * -- 결과
 * 굉장히 빠르게 맞음
 */
public class Main {
    static int H;
    static int W;
    static int N;
    static int res = 0;
    static Point[] cheese;
    static Point mouse;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static char[][] map;
    static boolean[][] visited;

    static class Point {
        int y;
        int x;
        int value;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        Point(int y, int x, int value) {
            this(y, x);
            this.value = value;
        }
    }

    static int bfs(int cheeseNumber) {
        visited = new boolean[H][W];
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(mouse.y, mouse.x, 0));
        visited[mouse.y][mouse.x] = true;
        int res = 0;

        Loop:
        while (!queue.isEmpty()) {
            Point point = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx] || map[ny][nx] == 'X') {
                    continue;
                }

                if ((map[ny][nx] - '0') == cheeseNumber) { // 원하는 치즈를 찾았을 때
                    res = point.value + 1;
                    mouse = new Point(ny, nx);
                    break Loop;
                }

                queue.add(new Point(ny, nx, point.value + 1));
                visited[ny][nx] = true;
            }
        }

        return res;
    }

    static boolean outOfRange(int y, int x) {
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
        N = fun.apply(input[2]);
        map = new char[H][W];
        cheese = new Point[N + 1];

        for (int i = 0; i < H; i++) {
            String string = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j);

                if (map[i][j] == 'S') {
                    mouse = new Point(i, j);
                } else if (map[i][j] != 'X' && map[i][j] != '.') { // 숫자일 때
                    cheese[map[i][j] - '0'] = new Point(i, j);
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            res += bfs(i);
        }

        System.out.println(res);
    }
}