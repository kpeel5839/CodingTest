import java.util.*;
import java.io.*;
import java.util.function.Function;

// 4179 : 불!

/**
 * -- 전제 조건
 * 지훈이가 불이 난 미로에 갇혔다.
 * 주변은 벽으로 막혀있다.
 * 지훈이는 그냥 맵 밖으로 나가면 탈출 성공이다.
 *
 * 지훈이가 불에 죽으면 IMPOSSIBLE 을 출력하고
 * 그렇지 않으면 지훈이가 탈출한 경우에서의 최소 시간을 출력한다.
 *
 * -- 틀 설계
 * 그냥, 지훈이를 먼저 움직이면 안된다.
 * 그러면 로직이 복잡해지기 때문에,
 * 지훈이를 먼저 움직이는 것이 아닌
 * 불을 먼저 움직이면 된다.
 * 그러고서 그 행위를 반복하면 이 문제는 해결할 수 있다.
 * outOfRange 를 만들어서, 맵을 탈출했을 때의 경우를 체크할 수 있도록 하자.
 *
 * 아니다 지훈이를 먼저 움직여야 할 것 같다.
 * 지훈이를 먼저 움직이고
 * 그냥 불이 지훈이를 죽여버리면 된다. (지운다.)
 */
public class Main {
    static int H;
    static int W;
    static int res = 0;
    static boolean finish = false;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static boolean[][] visited; // 지훈이가 이미 움직인 공간은 다시 움직이지 않는다.
    static char[][] map;
    static Queue<Point> jihun = new LinkedList<>();
    static Queue<Point> fire = new LinkedList<>();

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

    static boolean outOfRange(int y, int x) { // 맵을 탈출했음을 알 수 있는 method
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    static void spreadFire() {
        // J 를 지워버리면 된다.
        // fire 큐를 이용해서, 계속 움직여준다.
        // 현재의 사이즈만큼만 진행하면 된다.
        int initSize = fire.size();
        for (int i = 0; i < initSize; i++) {
            Point point = fire.poll();
            for (int j = 0; j < 4; j++) {
                int ny = point.y + dy[j];
                int nx = point.x + dx[j];

                if (outOfRange(ny, nx) || map[ny][nx] == '#' || map[ny][nx] == 'F') { // 최적화를 위해 조금 더 조건문을 추가하였음, visited 하면 지훈이 못잡아먹음
                    continue;
                }

                map[ny][nx] = 'F';
                fire.add(new Point(ny, nx)); // 다음에 진행할 불을 넣어둔다.
            }
        }
    }

    static void move() {
        // 지훈이를 움직인다, 지훈이가 없으면? 못 움직여버령
        // 지훈이도 큐에 담겨 있음
        // 지훈이도 큐에서 빼서 움직이는데, 뺐는데 그 위치에 지훈이 없으면 죽은 거니까 진행하지 않음
        // 나머지는 그대로 진행하는데, F와, # 쪽으로, 그리고 J 쪽으로는 못감, 근데 그럴 일은 없다 visited 될 것이니까, 그래도 혹시 모르니까 그냥 해버렷
        int initSize = jihun.size();

        for (int i = 0; i < initSize; i++) {
            Point point = jihun.poll();

            if (map[point.y][point.x] != 'J') { // 지훈이 죽은 경우
                continue;
            }

            for (int j = 0; j < 4; j++) {
                int ny = point.y + dy[j];
                int nx = point.x + dx[j];

                if (outOfRange(ny, nx)) {
                    finish = true;
                    res = point.value + 1;
                    return;
                }

                if (visited[ny][nx] || map[ny][nx] == '#' || map[ny][nx] == 'F') {
                    continue;
                }

                map[ny][nx] = 'J';
                visited[ny][nx] = true;
                jihun.add(new Point(ny, nx, point.value + 1));
            }
        }
    }

    static void gameStart() {
        // move, spreadFire 를 계속 하면 된다.
        while (true) {
            move();
            spreadFire();
//            mapPrint();
            if (jihun.isEmpty()) { // 지훈이가 안남은 경우도 return
                return;
            }

            if (finish) { // 끝난 경우도 return
                return;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]);
        W = fun.apply(input[1]);

        map = new char[H][W];
        visited = new boolean[H][W];

        for (int i = 0; i < H; i++) {
            String string = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j);

                if (map[i][j] == 'J') {
                    jihun.add(new Point(i, j, 0));
                    visited[i][j] = true;
                }

                if (map[i][j] == 'F') {
                    fire.add(new Point(i, j));
                    visited[i][j] = true;
                }
            }
        }

        gameStart();

        System.out.println((res == 0) ? "IMPOSSIBLE" : res);
    }

    static void mapPrint() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}