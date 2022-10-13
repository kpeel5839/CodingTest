import java.util.*;
import java.io.*;

// 1175 : 배달

/**
 * 예제
 * 2 3
 * SCC
 * ...
 */
public class Main {
    static int H;
    static int W;
    static int cCnt = 0;
    static int[] start;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[][] destination = new int[2][];
    static char[][] map;
    static boolean[][][] visited;

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    static int bfs() {
        int res = -1;
        Queue<int[]> queue = new LinkedList<>(); // {y, x, preDir}
        queue.add(new int[] {start[0], start[1], -1}); // 처음에는 방향을 가지고 있지 않음

        while (!queue.isEmpty()) {
            int[] point = queue.poll();


        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1175_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        map = new char[H][W];
        visited = new boolean[H][W][4];

        for (int i = 0; i < H; i++) {
            String s = br.readLine();
            for (int j = 0; j < W; j++) {
                char c = s.charAt(j);
                map[i][j] = c;

                if (c == 'S') {
                    start = new int[] {i, j};
                } else if (c == 'C') {
                    destination[cCnt++] = new int[] {i, j};
                }
            }
        }

        System.out.println(bfs());
    }
}
