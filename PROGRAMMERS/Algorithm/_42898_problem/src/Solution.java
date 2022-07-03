import java.util.*;
import java.io.*;

class Solution {
    /*
    이 문제는 일단 최단 경로의 경우의 수이다.
    즉, 어떠한 지점을 가더라도 거기까지는 최소 경로로 가야한다라는 것이다.
    즉, 다른 경로로 간다라고 한들
    어디까지나 최단 경로는 항상 이득인 길로만 가는 것이기에
    모든 지점이 출발 지점에 있어서 최단 경로인 지점들이다.
    
    그렇기 때문에 bfs 로 모든 격자들의 지점들을 최단 경로로 이동하였을 때, 해당 지점까지의 이동 횟수로 초기화를 진항해놓고
    그 다음에 평범한 dfs 로 dp 를 진행하면 될 것 같다.
    */
    static int H;
    static int W;
    static final int INF = 1_000_000_000;
    static final int MOD = 1_000_000_007;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static boolean[][] map;
    static int[][] moveCnt;
    static int[][] dp;

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

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    static void bfs() {
        // 그냥 일반적인 bfs 로 map 을 이용해서, true 이면 갈 수 있는 곳, false 이면 갈 수 없는 곳으로 선택하면 된다.
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(0, 0, 0));
        moveCnt[0][0] = 0;

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx) || !map[ny][nx] || moveCnt[ny][nx] != INF) {
                    continue;
                }

                moveCnt[ny][nx] = point.value + 1;
                queue.add(new Point(ny, nx, point.value + 1));
            }
        }
    }

    static int dfs(int y, int x, int cnt) {
        // cnt == 이동 횟수 (현재 지점까지)
        if (y == H - 1 && x == W - 1) {
            return 1;
        }

        if (dp[y][x] != INF) {
            return dp[y][x];
        }

        dp[y][x] = 0;

        for (int i = 0; i < 4 ; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];
            int nCnt = cnt + 1;

            if (outOfRange(ny, nx) || moveCnt[ny][nx] < nCnt || !map[y][x]) {
                continue;
            }

            dp[y][x] = (dp[y][x] + dfs(ny, nx, cnt + 1)) % MOD;
        }

        return dp[y][x] % MOD;
    }

    public int solution(int m, int n, int[][] puddles) {
        H = n;
        W = m;

        map = new boolean[H][W];
        moveCnt = new int[H][W];
        dp = new int[H][W];

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                map[i][j] = true;
                moveCnt[i][j] = INF;
                dp[i][j] = INF;
            }
        }

        for (int i = 0; i < puddles.length; i++) {
            map[puddles[i][1] - 1][puddles[i][0] - 1] = false;
        }

        // for (int i = 0; i < H; i++) {
        //     for (int j = 0; j < W; j++) {
        //         if (map[i][j]) {
        //             System.out.print(1);
        //         } else {
        //             System.out.print(0);
        //         }
        //     }
        //     System.out.println();
        // }

        bfs();

        // System.out.println();
        // for (int i = 0; i < H; i++) {
        //     for (int j = 0; j < W; j++) {
        //         System.out.print(moveCnt[i][j] + " ");
        //     }
        //     System.out.println();
        // }

        return dfs(0, 0, 0);
    }
}