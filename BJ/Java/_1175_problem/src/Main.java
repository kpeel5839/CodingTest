import java.util.*;
import java.io.*;

// 1175 : 배달

/**
 * 3 36
 * #.#.#.#.#.#.#.#.#.#.#.#.#.#.#.#.#.#C
 * .................S..................
 * C#.#.#.#.#.#.#.#.#.#.#.#.#.#.#.#.#.#
 */
public class Main {
    static int H;
    static int W;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[][] points = new int[3][2];
    static char[][] map;
    static boolean[][][][] visited;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1175_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        map = new char[H][W];
        visited = new boolean[3][4][H][W]; // Status, Dir, Height, Width
        int cIndex = 1;

        for (int i = 0; i < H; i++) {
            String line = br.readLine();
            for (int j = 0; j < W; j++) {
                char character = line.charAt(j);
                map[i][j] = character;

                if (character == 'S') {
                    points[0][0] = i;
                    points[0][1] = j;
                    map[i][j] = '.';
                }

                if (character == 'C') {
                    points[cIndex][0] = i;
                    points[cIndex++][1] = j;
                    map[i][j] = '.';
                }
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {points[0][0], points[0][1], -1, 0, 0}); // y, x, preDir, Status, moveCount

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int preDir = point[2];
            int moveCount = point[4];

            for (int i = 0; i < 4; i++) {
                if (preDir == i) {
                    continue;
                }

                int status = point[3];
                int y = point[0] + dy[i];
                int x = point[1] + dx[i];

                if (outOfRange(y, x) || visited[status][i][y][x] || map[y][x] == '#') {
                    continue;
                }

                if (y == points[1][0] && x == points[1][1]) {
                    status |= 1;
                }

                if (y == points[2][0] && x == points[2][1]) {
                    status |= 2; // 두개의 비트에다가 방문처리를 한다고 생각하면 됨
                }

                if (status == 3) {
                    System.out.println(moveCount + 1);
                    System.exit(0);
                }

                visited[status][i][y][x] = true;
                queue.add(new int[] {y, x, i, status, moveCount + 1});
            }
        }

        System.out.println(-1);
    }

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }
}
