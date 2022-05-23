import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2636 : 치즈

/**
 * -- 전제 조건
 * 치즈인 곳은 1, 치즈가 아닌 곳은 0으로 표시하고
 * 치즈는 외부 공기와 닿으면 사라진다.
 * 그래서, 모든 치즈가 사라지기 까지의 시간과 모든 치즈가 모두 사라지기 1시간 전의 치즈 개수를 구하라.
 * -- 틀 설계
 * 일단, 그냥 0을 기준으로 모두 체크를 진행하고
 * bfs 를 통해서 다 2로 만들고
 * 치즈를 지워주면서 0으로 표시해준다.
 *
 * 그 다음에 계속 visited 배열을 초기화 해주고, 1을 만나면 끝나게 해서, 계속 2로 바꿔준다.
 * 그런 다음에 치즈가 남아있나 안남아있나 확인하면서
 * 0이면 이전에 남았던 치즈개수를 출력하고
 * 아니라면 치즈개수를 갱신해준다.
 */
public class Main {
    static int H;
    static int W;
    static int time;
    static int remain;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[][] map;
    static boolean[][] visited;
    static Queue<Point> queue;

    static class Point {
        int y;
        int x;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static boolean check() {
        // check 를 해주고 remain 에다가 넣어준다, 1이 없으면 true 를 반환한다.
        int count = 0;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j] == 1) {
                    count++;
                }
            }
        }

        if (count == 0) {
            return true;
        } else {
            remain = count;
            return false;
        }
    }

    static void bfs() {
        visited = new boolean[H][W];
        queue = new LinkedList<>();
        visited[0][0] = true;
        queue.add(new Point(0, 0));
        map[0][0] = 2;

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx] || map[ny][nx] == 1) { // visited 나, 1을 만나면 continue
                    continue;
                }

                map[ny][nx] = 2;
                visited[ny][nx] = true;
                queue.add(new Point(ny, nx));
            }
        }
    }

    static void melt() {
        // 주변에 2가 하나라도 있으면 melt 해준다.
        Queue<Point> queue = new LinkedList<>();

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                for (int c = 0; c < 4; c++) {
                    int y = i + dy[c];
                    int x = j + dx[c];

                    if (outOfRange(y, x)) {
                        continue;
                    }

                    if (map[y][x] == 2) {
                        queue.add(new Point(i, j));
                        break;
                    }
                }
            }
        }

        while (!queue.isEmpty()) {
            Point point = queue.poll();
            map[point.y][point.x] = 0;
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
        map = new int[H][W];

        for (int i = 0; i < H; i++) {
            input = br.readLine().split(" ");
            for (int j = 0; j < W; j++) {
                map[i][j] = fun.apply(input[j]);

                if (map[i][j] == 1) {
                    remain++; // 1 을 세줌
                }
            }
        }

        mapPrint();

        while (true) {
            // 계속 bfs 를 실행하고
            bfs();
            melt();
            time++;
            mapPrint();
            if (check()) {
                break;
            }
        }

        System.out.println(time);
        System.out.println(remain);
    }

    static void mapPrint() {
        System.out.println("next ");
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
