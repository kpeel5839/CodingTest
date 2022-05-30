import java.util.*;
import java.io.*;
import java.util.function.Function;

// 14923 : 미로 탈출

/**
 * -- 전제조건
 * 그냥 벽 부수고 이동하기 모방문제이다.
 * -- 틀 설계
 * 처음에는 N, M 즉, H, W 가 주어지고
 * 그 다음에 홍익이가 시작하는 위치가 주어진다.
 * 그 다음에 미로의 탈출 위치가 주어진다.
 *
 * 탈출할 수 없다면 -1을 출력하면 되고
 * 탈출이 가능하다면 최소 값을 출력하면 된다.
 *
 * 일단, 벽 부수기처럼 본인이 부신 벽의 개수를 기록하고
 * 만일 부신 벽의 개수가 똑같은 곳에 도달했다면
 * 혹은 더 낮은 곳에 도달했다면 거기는 가지 않으면 된다.
 *
 * 즉 방문 처리를 부신 벽의 개수로 하게 되면 이 문제의 해를 구할 수 있다.
 *
 * -- 해맸던 점
 * 벽을 부시지 않았을 때에도 모르고 dp[ny][nx] = point.crash + 1; 을 해주었고
 * 그리고, 처음에 start, end 를 위치를 받을 때, 인덱스화 시켜줘야 하기 떄문에
 * -1 을 해주어야 하는데, 모르고 안해줬었음
 */
public class Main {
    static int H;
    static int W;
    static int res = -1;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[][] dp;
    static int[][] map;
    static Point start;
    static Point end;

    static class Point {
        int y;
        int x;
        int crash; // 부신 벽의 개수
        int value; // 이동 횟수

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        Point(int y, int x, int crash, int value) {
            this(y, x);
            this.crash = crash;
            this.value = value;
        }
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

        input = br.readLine().split(" ");
        start = new Point(fun.apply(input[0]) - 1, fun.apply(input[1]) - 1);

        input = br.readLine().split(" ");
        end = new Point(fun.apply(input[0]) - 1, fun.apply(input[1]) - 1); // start, end 위치 다 받음
        dp = new int[H][W];
        map = new int[H][W];

        for (int i = 0; i < H; i++) {
            input = br.readLine().split(" ");
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            for (int j = 0; j < W; j++) {
                map[i][j] = fun.apply(input[j]);
            }
        }

        Queue<Point> queue = new LinkedList<>();
        dp[start.y][start.x] = 0;
        queue.add(new Point(start.y, start.x, 0, 0)); // crash = 0, value = 0

        while (!queue.isEmpty()) {
            Point point = queue.poll();

//            System.out.println("(" + point.y + ", " + point.x + ")");
            if (point.y == end.y && point.x == end.x) {
                res = point.value;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx)) { // 범위를 벗어나는 경우
                    continue;
                }

                if (map[ny][nx] == 1) { // 벽인 경우
                    // 벽이 경우는 현재 거기에, 입력된 숫자가, 내 crash + 1 보다 같거나 작으면 안됨
                    if (point.crash != 1 && point.crash + 1 < dp[ny][nx]) { // 이미 벽을 부신적이 없고, 현재 해당 벽이 부서진 적이 없을 때
                        dp[ny][nx] = point.crash + 1;
                        queue.add(new Point(ny, nx, point.crash + 1, point.value + 1));
                    }
                } else { // 벽이 아닌 경우
                    // 벽이 아닌 경우는, 그냥 현재 point.crash 가 dp[ny][nx] 보다 크거나 같아서는 안됨
                    if (point.crash < dp[ny][nx]) {
                        dp[ny][nx] = point.crash;
                        queue.add(new Point(ny, nx, point.crash, point.value + 1));
                    }
                }
            }
        }


        System.out.println(res);
    }
}