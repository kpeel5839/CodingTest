import java.util.*;
import java.io.*;

// 2842 : 집배원 한상덕

/**
 * 예제
 * 3
 * K.P
 * ...
 * K.K
 * 3 3 4
 * 9 5 9
 * 8 3 7
 */
public class Main {
    static int N;
    static int kCnt = 0;
    static boolean end;
    static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] start;
    static char[][] map;
    static int[][] height;
    static boolean[][] visited;

//    static class Point {
//        int y;
//        int x;
//        int max;
//        int min;
//        int cnt; // K 개를 몇개 도달했는지 표시
//
//        Point(int y, int x, int max, int min, int cnt) {
//            this.y = y;
//            this.x = x;
//            this.max = max;
//            this.min = min;
//            this.cnt = cnt;
//        }
//
//        public boolean cal(int value, int mid) {
//            // value 가 들어오면 min 과 max 중 하나로 대체하고, 고도의 차이를 보고, 현재 mid 이상이라면 false 를 반환하여서 가지 못하도록 한다.
//            // 만일 가게 되면 초기화 가능하도록 한다.
//            if (min <= value && value <= max) {
//                return true;
//            } else if (value < min) {
//                if ((max - value) <= mid) { // min 이 value 로 대체되어도 고도차이가 mid 이하인 경우
//                    return true;
//                }
//            } else {
//                if (value - min <= mid) { // max 가 value 로 교체되어도, 고도차이가 mid 이하인 경우
//                    return true;
//                }
//            }
//
//            return false;
//        }
//
//        public int[] getMaxAndMin(int value) {
//            return new int[] {Math.max(max, value), Math.min(min, value)};
//        }
//    }
//    static boolean bfs(int mid) {
//        // Point 로 지점을 넣어놓는다.
//        Queue<Point> queue = new LinkedList<>();
//        queue.add(new Point(start[0], start[1], height[start[0]][start[1]], height[start[0]][start[1]], 0));
//
//        while (!queue.isEmpty()) {
//            Point point = queue.poll();
//
//            if (point.cnt == kCnt) { // 성공한 경우
//                return true;
//            }
//
//            for (int i = 0; i < 8; i++) {
//                int ny = point.y + dy[i];
//                int nx = point.x + dx[i];
//
//                if (outOfRange(ny, nx)) {
//                    continue;
//                }
//
//                int nextHeight = height[ny][nx];
//
//                if (point.cal(nextHeight, mid)) { // 가는게 가능한 경우
//                    int[] maxAndMin = point.getMaxAndMin(nextHeight);
//
//                    if (map[ny][nx] == 'K') {
//                        queue.add(new Point(ny, nx, maxAndMin[0], maxAndMin[1], point.cnt + 1));
//                    } else {
//                        queue.add(new Point(ny, nx, maxAndMin[0], maxAndMin[1], point.cnt));
//                    }
//                }
//            }
//        }
//
//        return false;
//    }

    static void dfs(int y, int x, int mid, int cnt, int max, int min) {
        if (end) {
            return;
        }

        if (cnt == kCnt) {
            end = true;
            return;
        }

        visited[y][x] = true;

        for (int i = 0; i < 8; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (outOfRange(ny, nx) || visited[ny][nx]) {
                continue;
            }

            int value = height[ny][nx];
            int nMax = Math.max(max, value);
            int nMin = Math.min(min, value);

            if (nMax - nMin <= mid) {
                int nCnt = cnt;

                if (map[ny][nx] == 'K') {
                    nCnt++;
                }

                dfs(ny, nx, mid, nCnt, nMax, nMin);
            }
        }

        visited[y][x] = false;
    }

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= N) || (x < 0 || x >= N)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2842_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        height = new int[N][N];
        map = new char[N][N];
        int min = Integer.MAX_VALUE;
        int max = 0;
        int ans = 0;

        for (int i = 0; i < N; i++) {
            String string = br.readLine();

            for (int j = 0; j < N; j++) {
                map[i][j] = string.charAt(j);

                if (map[i][j] == 'P') {
                    start = new int[] {i, j};
                } else if (map[i][j] == 'K') {
                    kCnt++;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                height[i][j] = Integer.parseInt(st.nextToken());
                min = Math.min(min, height[i][j]);
                max = Math.max(max, height[i][j]);
            }
        }

        int left = 0;
        int right = max - min;

        while (left <= right) {
            int mid = (left + right) / 2;
            visited = new boolean[N][N];
            end = false;
            dfs(start[0], start[1], mid, 0, height[start[0]][start[1]], height[start[0]][start[1]]);

            if (end) { // 성공한 경우 right = mid - 1 (고도 허용 범위를 낮춰봐야함)
                System.out.println(mid);
                ans = mid;
                right = mid - 1;
            } else { // 실패한 경우 left = mid + 1 (고도 허용 범위를 높여야함)
                left = mid + 1;
            }
        }

        System.out.print(ans);
    }
}
