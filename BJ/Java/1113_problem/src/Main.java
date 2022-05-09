import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1113 : 수영장 만들기

/**
 * -- 전제 조건
 * 격자가 주어지고,
 * 각 격자에는 숫자들이 쓰여져있다.
 * 이 숫자의 의미는 직육각형의 높이를 의미한다.
 *
 * 직육각형들이 해당 숫자만큼의 높이로 있고,
 * 높이는 1 ~ 9 까지 주어지고,
 * 땅은 높이가 0이라고 할 때, 수영장에 채울 수 있는 물의 양은 얼마나 되는지 구하시오.
 *
 * -- 틀 설계
 * 일단 본인 주변의 4방향에 본인보다 다 높아야함
 * 그래야지 물이 들어갈 수 있음
 *
 * 근데, 인접한 4방향의 격자만 봐서는 안됨
 * 그 주변에 있는 것들도 봐야함
 *
 * 주변을 0으로 채워넣는다.
 * 그러고서 본인보다 낮은 숫자를 만나면 계속 진행하다가
 * 0을 만나면 끝낸다. (0을 만나면, 무조건 적으로 지금까지 담은 Queue 에 있던 포인트들에다가 다 0을 집어넣는다.
 * (왜냐하면 다 흐르니까)
 *
 * 이렇게 하면 되지 않을까?
 * 일단 낮은 수부터 시작하는 것이다
 * bfs를
 * 그래서, bfs 를 실시를 하는데 1 ~ 9 까지 bfs 를 실시하고
 * 본인보다 낮은 숫자를 만나게 되면, 흘러내린다.
 * 그래서, 그냥 낮은 숫자나 같은 숫자를 만나게 되면, 그 지점들은 아무 처리하지 않는다. (흘러내리니까, 물을 채워넣지 않고 그대로 내버려둔다. 짜피 채울 수도 없음)
 *
 * 그리고, 본인보다 높은 지점을 만나면, 그 지점들 중 가장 낮은 것을 기록해놓고
 * Queue 에다가 담아놓은 포인트들을 다 그 숫자로 바꾼다음 바꾸면서
 * res 에다가 더해준다.
 *
 * 그러면서 1 ~ 9 까지 진행해준다.
 *
 * 약간 진짜 수영장을 이미지화 해서, 높이가 다른 수영장을 채울 때 어떤 느낌일까 하는 생각으로 해결했음
 * -- 해맸던 점
 * queue, fill 초기화를 진행안해줘서 자꾸해맸었고
 * outOfRange 에서도, 흘러내린 경우인데, 여기서 minHeight = 0 으로 처리 안해주어서도 해맸었고,
 * 그리고, map[i][j] == number && !visited[i][j] 로 했었어야 했는데, map[i][j] == number || !visited[i][j] 로 해가지고 해맸었음
 *
 * 근데, 결국은 다 찾아서 맞았음
 * 아이디어는 낮은 높이의 땅부터 시작해서, 물을 채워준다는 느낌으로 했음
 *
 * 왜냐하면 물을 채우면 그거는 땅이나 다름 없으니까,
 * 낮은 애들 부터 물을 차근차근 채워가면서 하면, 그러면, 완전 흘러내리는 경우도 찾을 수 있고,
 * 가장 적합한 물을 채울 수 있는 높이를 찾을 수 있음
 */
public class Main {
    public static int H;
    public static int W;
    public static int res = 0;
    public static int[] dx = {0, 1, 0, -1};
    public static int[] dy = {-1, 0, 1, 0};
    public static int[][] map;
    public static boolean[][] visited;
    public static Queue<Point> queue;
    public static Queue<Point> fill;

    public static class Point {
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public String toString() {
            return "y : " + y + " x : " + x;
        }
    }

    public static void bfs(int y, int x, int number) {
        queue = new LinkedList<>();
        fill = new LinkedList<>();

        fill.add(new Point(y, x));
        queue.add(new Point(y, x));
        visited[y][x] = true;

        int minHeight = 10;
        while (!queue.isEmpty()) {
            Point point = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx)) { // 밖으로 나가는 경우는 무조건 0임
                    minHeight = 0;
                } else if (visited[ny][nx]) {
                    continue;
                } else if (map[ny][nx] < number) { // 해당 number 보다 작은 map[ny][nx] 를 만나면
                    minHeight = 0;
                } else if (map[ny][nx] > number) { // 더 높은 것을 만나는 경우
                    minHeight = Math.min(minHeight, map[ny][nx]); // 높은 것중에 제일 작은 것을 얻어낸다, 일단 제일 높은 것 중 제일 작은 것으로 높이를 맞춰놔야함
                } else { // 같은 것을 만나는 경우
                    queue.add(new Point(ny, nx));
                    fill.add(new Point(ny, nx));
                    visited[ny][nx] = true;
                }
            }
        }

        if (minHeight != 0) { // 다 흘러내리지 않는 경우
            for (Point point : fill) { // 채워넣을 것들을 채워넣음
                res += minHeight - map[point.y][point.x]; // 변경할려고 하는 minHeight 와 map[point.y][point.x] 의 차이값을 더해줌
                map[point.y][point.x] = minHeight;
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

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]);
        W = fun.apply(input[1]);
        map = new int[H][W];

        for (int i = 0; i < H; i++) {
            String string = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j) - '0';
            }
        }

        for (int number = 1; number < 10; number++) {
            visited = new boolean[H][W]; // number 마다 visited 배열을 선언하여서, 높이가 변경되는 경우를 대비
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (map[i][j] == number && !visited[i][j]) { // ma[[i][j] 가 number 와 같을 때만 진행한다, 그리고 방문을 아직 하지 않은 경우
                        bfs(i, j, number);
                    }
                }
            }
        }

        System.out.println(res);
    }

    public static void mapPrint() {
        System.out.println("next");
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
