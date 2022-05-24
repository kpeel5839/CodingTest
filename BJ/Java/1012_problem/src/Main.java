import java.util.*;
import java.io.*;
import java.util.function.Function;

public class Main {
    static int T;
    static int H;
    static int W;
    static int K;
    static Queue<Point> queue;
    static boolean[][] visited;
    static int[][] map;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};

    static class Point {
        int y;
        int x;
        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static void bfs(int y, int x) {
        visited[y][x] = true;
        queue.add(new Point(y, x));

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx] || map[ny][nx] == 0) {
                    continue;
                }

                queue.add(new Point(ny, nx));
                visited[ny][nx] = true;
            }
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

        T = fun.apply(br.readLine());

        while (T-- > 0) {
            String[] input = br.readLine().split(" ");

            W = fun.apply(input[0]);
            H = fun.apply(input[1]);
            K = fun.apply(input[2]);

            map = new int[H][W];
            visited = new boolean[H][W];
            queue = new LinkedList<>();
            int count = 0;

            for (int i = 0; i < K; i++) {
                input = br.readLine().split(" ");
                map[fun.apply(input[1])][fun.apply(input[0])] = 1;
            }

            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (map[i][j] == 1 && !visited[i][j]) {
                        count++;
                        bfs(i, j);
                    }
                }
            }

            System.out.println(count);
        }
    }
}
