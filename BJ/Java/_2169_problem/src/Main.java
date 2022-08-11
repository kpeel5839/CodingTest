import java.util.*;
import java.io.*;

/**
 * 예제
 *
 * 5 5
 * 10 25 7 8 13
 * 68 24 -78 63 32
 * 12 -69 100 -29 -25
 * -16 -22 -57 -33 99
 * 7 -76 -11 77 15
 *
 * 설계하고 바로 완벽하게 맞은 문제
 */
public class Main {
    static int H;
    static int W;
    static final int INF = -1000000000;
    static int[] dx = {1, 0, -1};
    static int[] dy = {0, 1, 0};

    static int[][] map;
    static int[][][] dp;

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    static boolean impossible(int preDir, int nowDir) {
        return nowDir == 0 ? preDir == 2
                : nowDir == 2 && preDir == 0; // 이 두가지 경우에만 impossible 함
    }

    static int dfs(int dir, int y, int x) {
        if (y == H - 1 && x == W - 1) {
            return 0;
        }

        if (dp[dir][y][x] != (INF * 2)) {
            return dp[dir][y][x];
        }

        dp[dir][y][x] = INF;

        for (int i = 0; i < 3; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (outOfRange(ny, nx) || impossible(dir, i)) {
                continue;
            }

            dp[dir][y][x] = Math.max(dp[dir][y][x], map[ny][nx] + dfs(i, ny, nx));
        }

        return dp[dir][y][x];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2169_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        map = new int[H][W];
        dp = new int[3][H][W];

        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            Arrays.fill(dp[0][i], INF * 2);
            Arrays.fill(dp[1][i], INF * 2);
            Arrays.fill(dp[2][i], INF * 2);

            for (int j = 0; j < W; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(map[0][0] + dfs(0, 0, 0));
    }
}
