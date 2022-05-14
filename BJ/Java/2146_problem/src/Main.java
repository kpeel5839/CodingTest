import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2146 : 다리 만들기

/**
 * -- 전제 조건
 * 여러개의 섬이 존재하고,
 * 어떤 사람이 공약으로 다리 만든다고 해서 당선 됐는데, 돈 아까워가지고 섬을 잇기로 한 공약을 최대한 싸게 막으려고함
 * 그래서, 섬들끼리 연결하는 다리를 가장 싼 값으로 할 수 있는, 즉 섬끼리의 최소 거리를 구하라
 *
 * -- 틀 설계
 * 이거는 뭐, 그냥 bfs 로 섬 번호 매겨주고
 * 그 다음에 bfs 진행할 떄에는, 다른 섬과의 닿는 가장 가까운 거리를 계속 갱신하면 된다.
 *
 * 그냥 격자 하나하나 마다 진행하는데, 현재 처음에 시작한 격자가 몇번째 섬이였는지만 체크하고, 그 다음에 OutOfRange 나 본인과 똑같은 섬을 만나게 되면
 * 끝내면 된다.
 *
 * -- 결과
 * 바로 쉽게 풀긴 했는데, 시간이 너무 많이 걸림 살짝 맘에 안듦
 */
public class Main {
    static int N;
    static int isLandNumber = 0;
    static int res = Integer.MAX_VALUE;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[][] map;
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

    static void decideIslandNumber(int y, int x) { // 섬들의 번호를 매기기 위해서
        isLandNumber++;
        Queue<Point> queue = new LinkedList<>();
        visited[y][x] = true;
        queue.add(new Point(y, x));

        while(!queue.isEmpty()) {
            Point point = queue.poll();
            map[point.y][point.x] = isLandNumber;

            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx] || map[ny][nx] == 0) {
                    continue;
                }

                queue.add(new  Point(ny, nx));
                visited[ny][nx] = true;
            }
        }
    }

    static void findMinPathOfIsland(int y, int x, int target) { // 섬들과 가까운 섬을 찾기 위해서, 해당 격자와 가까운 섬을 찾는 것임
        Queue<Point> queue = new LinkedList<>();
        visited[y][x] = true;
        queue.add(new Point(y, x, 0));

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx]) {
                    continue;
                }

                if (map[ny][nx] != target && map[ny][nx] != 0) { // 다른 섬을 만난 경우
                    res = Math.min(res, point.value);
                    continue;
                }

                queue.add(new Point(ny, nx, point.value + 1));
                visited[ny][nx] = true;
            }
        }
    }

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= N) || (x < 0 || x >= N)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        N = fun.apply(br.readLine());
        map = new int[N][N];
        visited = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                map[i][j] = fun.apply(input[j]);
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j] && map[i][j] != 0) { // 방문하지 않은 경우에 호출
                    decideIslandNumber(i, j);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                visited = new boolean[N][N];

                if (map[i][j] != 0) {
                    findMinPathOfIsland(i, j, map[i][j]);
                }
            }
        }

        System.out.println(res); // 답이 없는 경우는 존재하지 않음
    }
}
