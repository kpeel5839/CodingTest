import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1600 : 말이 되고픈 원숭이

/**
 * -- 전제 조건
 * 그냥 나이트 왼쪽 끝에서,
 * 오른쪽 끝으로 진행하면 되는데,
 * 나이트와 같은 움직임이 추가되고
 * 그것은 K 번만 가능하다.
 * -- 틀 설계
 * 그냥 dx, dy 에 나이트의 움직임만 더해주면 된다.
 *
 * -- 해맸던 점
 * 엄청 쉬운 문제인 줄 알았는데, 벽 부수고 이동하기 처럼, 생각보다 생각을 해야하는 문제였음
 * 표면적으로 보았을 때에는, 테케도 그렇고, 굉장히 쉽게 맞고 넘어갔음
 *
 * 하지만,
 *
 * 1
 * 5 5
 * 0 0 0 0 0
 * 0 0 0 0 0
 * 0 0 0 0 0
 * 0 0 0 1 1
 * 0 0 0 1 0
 *
 * 이런 반례가 존재했음
 * 문제점은 이러했음, 나중에 방문하는 것이 이미 K 번 move 하지 않았어도
 * 막히는 것이였음
 *
 * 하지만 아님, visited[ny][nx] 에 기록되어 있는 move 횟수보다 적으면, 갈 수 있어야 함
 * 왜냐하면 현재 저 반례와 같이, 나중에 못들어갈 수도 있고, 나중에 더 효율적인 움직임을 못하게 될 수도 있기 때문이다.
 *
 * 그래서 visited[ny][nx] 를 int 형으로 선언해주고
 * 거기다가 point.move 를 넣어주니까 해결되었음
 */
public class Main {
    static int K;
    static int H;
    static int W;
    static int res = Integer.MAX_VALUE;
    static int[] dx = {0, 1, 0, -1, -1, 1, 2, 2, 1, -1, -2, -2};
    static int[] dy = {-1, 0, 1, 0, -2, -2, -1, 1, 2, 2, 1, -1}; // 0 ~ 3 = 상하좌우, 4 ~ 11 = 나이트의 움직임
    static int[][] map;
    static int[][] visited;
    static Queue<Point> queue = new LinkedList<>();

    static class Point {
        int y;
        int x;
        int move;
        int value;
        Point(int y, int x, int move, int value) {
            this.y = y;
            this.x = x;
            this.move = move;
            this.value = value;
        }
    }
    public static void bfs() {
        visited[0][0] = 0;
        queue.add(new Point(0, 0, 0, 0)); // 처음 시작 지점 넣어주고

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            if ((point.y == (H - 1)) && (point.x == (W - 1))) {
                res = point.value;
                return;
            }

            for (int i = 0; i < 4; i++) { // 정상적으로, 상하좌우 움직임 먼저
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx) || map[ny][nx] == 1 || (visited[ny][nx] <= point.move)) {
                    continue;
                }

                visited[ny][nx] = point.move;
                queue.add(new Point(ny, nx, point.move, point.value + 1));
            }

            if (point.move < K) { // 더 나이트의 움직임을 할 수 있을 때에만
                for (int i = 4; i < 12; i++) {
                    int ny = point.y + dy[i];
                    int nx = point.x + dx[i];

                    if (outOfRange(ny, nx) || map[ny][nx] == 1 || visited[ny][nx] <= (point.move + 1)) {
                        continue;
                    }

                    visited[ny][nx] = point.move + 1;
                    queue.add(new Point(ny, nx, point.move + 1, point.value + 1));
                }
            }
        }
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

        K = fun.apply(br.readLine());
        String[] input = br.readLine().split(" ");
        W = fun.apply(input[0]);
        H = fun.apply(input[1]);
        map = new int[H][W];
        visited = new int[H][W];

        for (int i = 0; i < H; i++) {
            Arrays.fill(visited[i], Integer.MAX_VALUE);
        }

        for (int i = 0; i < H; i++) {
            input = br.readLine().split(" ");
            for (int j = 0; j < W; j++) {
                map[i][j] = fun.apply(input[j]);
            }
        }

        bfs();

        System.out.println((res == Integer.MAX_VALUE) ? -1 : res);
    }
}
