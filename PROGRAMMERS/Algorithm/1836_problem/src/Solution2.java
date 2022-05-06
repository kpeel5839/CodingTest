import java.util.function.Function;
import java.io.*;
import java.util.*;

class Solution2 {
    public static class Point {
        int y;
        int x;
        char c; // 해당 문자
        public Point(int y, int x, char c) {
            this.y = y;
            this.x = x;
            this.c = c;
        }
    }
    public static List<Point> list = new ArrayList<>();
    public static char[][] map = new char[101][101];
    public static int H;
    public static int W;
    public static int[] dy = {-1, 0, 1, 0};
    public static int[] dx = {0, 1, 0, -1};
    public static boolean[][] visited;

    public static boolean dfs(int y, int x, char c, int dir, int count) {
        // count == 회전한 횟수
        if (dir != -1 && map[y][x] == c) {
            map[y][x] = '.';
            return true;
        }

        boolean result = false;
        visited[y][x] = true;

        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (outOfRange(ny, nx) || visited[ny][nx]) { // 범위를 나가거나, 이미 방문한 곳을 방문하면
                continue;
            }

            if (map[ny][nx] != c && map[ny][nx] != '.') { // map[ny][nx] 가 . 도 아닌데, target 도 아닐 때
                continue;
            }

            if (count >= 1) {
                if (i == dir) {
                    result |= dfs(ny, nx, c, i, count);
                }
            } else {
                result |= dfs(ny, nx, c, i, (dir == i) ? count : (count + 1));
            } // result 가 true 로 한번 정해지면, 다시 바뀌지 않도록
        }

        visited[y][x] = false;
        return result;
    }

    public static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    public static String solution(int m, int n, String[] board) {
        StringBuilder sb = new StringBuilder();

        H = m;
        W = n;
        int count = 0;

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                map[i][j] = board[i].charAt(j);

                if ('A' <= map[i][j] && map[i][j] <= 'Z') { // 문자인 경우
                    list.add(new Point(i, j, map[i][j]));
                    count++;
                }
            }
        }

        Collections.sort(list, (o1, o2) -> o1.c - o2.c); // 문자로 정렬
        visited = new boolean[H][W];

        while (true) {
            boolean pang = false;

            for (int a = 0; a < list.size(); a++) {
                Point point = list.get(a);
                int i = point.y;
                int j = point.x;
                char c = point.c;

                if (c < 'A' || c > 'Z') {
                    continue;
                }

                // 이제 여기서부터 이 지점을 가지고, dfs 를 실시해야 함
                if (!visited[i][j]) {
                    boolean remove = dfs(i, j, c, -1, -1);

                    if (remove) { // 삭제를 실질적으로 한 경우
                        pang = true;
                        count -= 2;
                        sb.append(c);
                        map[i][j] = '.';
                        list.remove(point);
                        break;
                    }
                }
            }
            if (!pang) { // pang 이 일어나지 않았으면.
                break;
            }
        }

        if (count == 0) { // 다 부셨으면
            return sb.toString();
        } else {
            return "IMPOSSIBLE";
        }
    }
}