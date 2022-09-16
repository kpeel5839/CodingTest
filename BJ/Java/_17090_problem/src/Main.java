import java.util.*;
import java.io.*;

// 17090 : 미로탈출

/**
 * 예제
 * 3 3
 * RRD
 * RDD
 * ULL
 */
public class Main {
    static int H;
    static int W;
    static int U = 0;
    static int R = 1;
    static int D = 2;
    static int L = 3;
    static int VISITED = 1;
    static int SUCCESS = 2;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static char[][] map;
    static int[][] dp;

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= H || x < 0 || x >= W) {
            return true;
        } else {
            return false;
        }
    }

    static int dfs(int y, int x) {
        if (outOfRange(y, x)) {
            return SUCCESS;
        }

        if (dp[y][x] != 0) { // 굳이 dp[y][x] = FAIL 까지 둘 필요는 없을 듯, 그냥 1 이면 안되도록
            return dp[y][x];
        }

        dp[y][x] = VISITED; // 방문 처리해주고

        switch (map[y][x]) {
            case 'U' :
                dp[y][x] = dfs(y + dy[U], x + dx[U]);
                break; // break 안써줘서 시간 버림
            case 'R' :
                dp[y][x] = dfs(y + dy[R], x + dx[R]);
                break;
            case 'D' :
                dp[y][x] = dfs(y + dy[D], x + dx[D]);
                break;
            case 'L' :
                dp[y][x] = dfs(y + dy[L], x + dx[L]);
                break;
        }

        return dp[y][x];
    }

    static int count() {
        int res = 0;

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (dp[i][j] == SUCCESS) {
                    res++;
                }
            }
        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_17090_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        map = new char[H][W];
        dp = new int[H][W];

        for (int i = 0; i < H; i++) {
            String string = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j);
            }
        }

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (dp[i][j] == 0) {
                    dfs(i, j);
//                    mapPrint();
                }
            }
        }

        System.out.println(count());
    }

    static void mapPrint() {
        System.out.println("next");

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
    }
}
