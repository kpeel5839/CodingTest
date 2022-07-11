import java.util.*;
import java.io.*;
import java.util.function.Function;

public class Main2 {
    static int H;
    static int W;
    static int G;
    static int R;
    static final int VISIT = 9876;
    static int twoCount = 0;
    static int ans = 0;
    static Point[] twoGround;
    static int[] red;
    static int[] green;
    static boolean[] combVisited;
    static int[] assign;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[][] map;
    static int[][] visited;

    static class Point {
        int y;
        int x;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public String toString() {
            return "y : " + y + " x : " + x;
        }
    }


    static void permGreen(int start, int r) {
        if (r == G) {
            permRed(0, 0);
            return;
        }

        for (int i = start; i < twoGround.length; i++) {
            if (!combVisited[i]) {
                combVisited[i] = true;
                green[r] = i;
                permGreen(i + 1, r + 1);
                combVisited[i] = false;
            }
        }
    }

    static void permRed(int start, int r) {
        if (r == R) {
            bfs();
            return;
        }

        for (int i = start; i < twoGround.length; i++) {
            if (!combVisited[i]) {
                combVisited[i] = true;
                red[r] = i;
                permRed(i + 1, r + 1);
                combVisited[i] = false;
            }
        }
    }

    static void bfs() {
        Queue<Point> queue = new LinkedList<>();
        int flower = 0; // flower count 할 변수

        for (int i = 0; i < red.length; i++) {
            int y = twoGround[red[i]].y;
            int x = twoGround[red[i]].x;

            map[y][x] = 3;
            queue.add(new Point(y, x));
        }

        for (int i = 0; i < green.length; i++) {
            int y = twoGround[green[i]].y;
            int x = twoGround[green[i]].x;

            map[y][x] = 4;
            queue.add(new Point(y, x));
        }

        int time = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Point point = queue.poll();

                if (visited[point.y][point.x] == -1) { // 꽃인 경우
                    continue;
                }

                for (int j = 0; j < 4; j++) {
                    int ny = point.y + dy[j];
                    int nx = point.x + dx[j];

                    if (outOfRange(ny, nx) || map[ny][nx] == 0) {
                        continue;
                    }

                    if (map[ny][nx] == 1) { // 그냥 가는 경우
                        queue.add(new Point(ny, nx));
                        visited[ny][nx] = time;
                        map[ny][nx] = map[point.y][point.x]; // 꽃 복사
                    } else if (map[ny][nx] != map[point.y][point.x] && visited[ny][nx] == time) {
                        map[ny][nx] = 10;
                        visited[ny][nx] = -1;
                        flower++;
                    }
                }
            }

            time++;
        }

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j] > 1) {
                    map[i][j] = 1;
                    visited[i][j] = VISIT;
                }
            }
        }

        ans = Math.max(ans, flower);
    }

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= H || x < 0 || x >= W) {
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
        G = fun.apply(input[2]);
        R = fun.apply(input[3]);
        map = new int[H][W];
        visited = new int[H][W];
        red = new int[R];
        green = new int[G];
        List<Point> twoList = new ArrayList<>();

        for (int i = 0; i < H; i++) {
            input = br.readLine().split(" ");
            for (int j = 0; j < W; j++) {
                map[i][j] = fun.apply(input[j]);

                if (map[i][j] == 2) { // 배양액을 뿌릴 수 있는 땅을 받았을 때
                    twoCount++;
                    map[i][j] = 1;
                    twoList.add(new Point(i, j));
                    visited[i][j] = VISIT;
                }
            }
        }

        assign = new int[twoCount];
        twoGround = twoList.toArray(Point[]::new); // Array 에다가 메소드 참조할 수 있었구나 (toArray 에 어떤 타입의 배열로 만들 것인지 명시하면 됨
        combVisited = new boolean[twoGround.length];

        permGreen(0, R);
        System.out.println(ans);
    }

    static void mapPrint(int[][] map) {
        System.out.println("next !");
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
