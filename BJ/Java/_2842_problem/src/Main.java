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
    static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] start;
    static char[][] map;
    static int[][] height;
    static boolean[][] visited;
    static Set<Integer> list = new HashSet<>();

    static boolean bfs(int max, int min) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {start[0], start[1]});
        visited[start[0]][start[1]] = true;
        int cnt = 0;

        while (!queue.isEmpty()) {
            int[] point = queue.poll();

            if (map[point[0]][point[1]] == 'K') {
                cnt++;
            }

            for (int i = 0; i < 8; i++) {
                int ny = point[0] + dy[i];
                int nx = point[1] + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx] || !(min <= height[ny][nx] && height[ny][nx] <= max)) {
                    continue;
                }

                visited[ny][nx] = true;
                queue.add(new int[] {ny, nx});
            }
        }

        return cnt == kCnt;
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
        int ans = Integer.MAX_VALUE;

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
                list.add(height[i][j]);
            }
        }

        Integer[] h = list.toArray(Integer[]::new);
        Arrays.sort(h);

        int lp = 0;
        int rp = 0; // 하나의 고도로만 있는 맵을 고려하지 않았었다.

        while (lp <= rp) {
            int min = h[lp];
            int max = h[rp];
            visited = new boolean[N][N];

            if ((min <= height[start[0]][start[1]] && height[start[0]][start[1]] <= max) && bfs(max, min)) { // 성공한 경우 (min, max 의 범위를 시작 위치에는 적용하지 않았었음)
                ans = Math.min(ans, max - min);
                lp++;
            } else if (rp != (h.length - 1)) {
                rp++;
            } else {
                break;
            }
        }

        System.out.print(ans);
    }
}