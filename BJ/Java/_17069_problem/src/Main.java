import java.util.*;
import java.io.*;

/**
 * 예제
 * 22
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *
 * 문제 조금 안 읽어서, 대각선으로 갈 때, 비어있어야 하는 곳에 대한 조건을 안읽었음
 */
public class Main {
    static int N;
    static int[] dx = {1, 1, 0};
    static int[] dy = {0, 1, 1};
    static int[][] map;
    static long[][][] dp;

    static long dfs(int y, int x, int dir) {
        if (y == N - 1 && x == N - 1) {
            return 1;
        }

        if (dp[dir][y][x] != -1) {
            return dp[dir][y][x];
        }

        dp[dir][y][x] = 0;

        for (int i = 0; i < 3; i++) {
            if (dir != 1 && dir == ((i + 2) % 4)) {
                continue;
            }

            int ny = y + dy[i];
            int nx = x + dx[i];

            if (outOfRange(ny, nx) || map[ny][nx] == 1) {
                continue;
            }

            if (i == 1) { // 대각선인 경우
                if (map[ny - 1][nx] == 1 || map[ny][nx - 1] == 1) {
                    continue;
                }
            }

            dp[dir][y][x] += dfs(ny, nx, i);
        }

        return dp[dir][y][x];
    }

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= N || x < 0 || x >= N) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_17069_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        dp = new long[3][N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            Arrays.fill(dp[0][i], -1);
            Arrays.fill(dp[1][i], -1);
            Arrays.fill(dp[2][i], -1);
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(dfs(0, 1, 0));
    }
}
