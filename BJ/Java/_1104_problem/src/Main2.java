import java.util.*;
import java.io.*;

// 1014 : 컨닝

/**
 * 예제
 * 4
 * 2 3
 * ...
 * ...
 * 2 3
 * x.x
 * xxx
 * 2 3
 * x.x
 * x.x
 * 10 10
 * ....x.....
 * ..........
 * ..........
 * ..x.......
 * ..........
 * x...x.x...
 * .........x
 * ...x......
 * ........x.
 * .x...x....
 */
public class Main2 {
    static int H;
    static int W;
    static int[] dx = {-1, -1, 1};
    static int[] dy = {0, -1, -1};
    static int[][][][] dp;
    static char[][] map;
    static boolean[][] visited;
    static StringBuilder sb = new StringBuilder();

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= H || x < 0 || x >= W) {
            return true;
        } else {
            return false;
        }
    }

    static boolean check(int y, int x) {
        if (outOfRange(y, x)) { // 여기에 있다면 true, 아니라면 false 를 반환하도록 하자.
            return false;
        }

        return visited[y][x];
    }

    static int[] increase(int y, int x) {
        if (x == W - 1) {
            return new int[] {y + 1, 0};
        } else {
            return new int[] {y, x + 1};
        }
    }

    static int dfs(int y, int x, int up, int bit) {
        if (y == H) {
            return 0;
        }

        boolean possible = true;

        if (dp[up][bit][y][x] != -1) {
            return dp[up][bit][y][x];
        }

        for (int i = 0; i < 3; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (check(ny, nx)) { // 주변에 이미 차있으면
                possible = false;
                break;
            }
        }

        if (map[y][x] == 'x') { // map 이 x 여도 possible false 임
            possible = false;
        }

        int[] nP = increase(y, x);

        if (possible) {
            visited[y][x] = true;
            dp[up][bit][y][x] = Math.max(dp[up][bit][y][x], 1 + dfs(nP[0], nP[1], y != nP[0] ? bit | 1 << x : up,bit | 1 << x));
            visited[y][x] = false;
            dp[up][bit][y][x] = Math.max(dp[up][bit][y][x], dfs(nP[0], nP[1], y != nP[0] ? bit : up, bit));
        } else {
            dp[up][bit][y][x] = Math.max(dp[up][bit][y][x], dfs(nP[0], nP[1], y != nP[0] ? bit : up, bit));
        }

        return dp[up][bit][y][x];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1104_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());

            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            dp = new int[1 << W][1 << W][H][W];
            visited = new boolean[H][W];
            map = new char[H][W];

            for (int i = 0; i < dp.length; i++) {
                for (int j = 0; j < dp[i].length; j++) {
                    for (int c = 0; c < H; c++) {
                        Arrays.fill(dp[i][j][c], -1);
                    }
                }
            }

            for (int i = 0; i < H; i++) {
                String string = br.readLine();
                for (int j = 0; j < W; j++) {
                    map[i][j] = string.charAt(j);
                }
            }

            bw.write(dfs(0, 0, 0, 0) + "\n");
        }

        bw.flush();
        bw.close();
    }
}
