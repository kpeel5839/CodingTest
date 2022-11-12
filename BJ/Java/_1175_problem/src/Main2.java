import java.util.*;
import java.io.*;

// 1175 : 배달

/**
 * 예제
 * 2 3
 * SCC
 * ...
 */
public class Main2 {
    static int H;
    static int W;
    static int answer = Integer.MAX_VALUE;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[] sPoint = new int[2];
    static char[][] map;
    static int[][] cToC = new int[2][4];
    static int[][] cPoints = new int[2][2];
    static boolean[][][] visited;

    static void getCtoC(int nowCIndex) {
        Arrays.fill(cToC[nowCIndex], Integer.MAX_VALUE);

        for (int i = 0; i < 4; i++) {
            int y = cPoints[nowCIndex][0] + dy[i];
            int x = cPoints[nowCIndex][1] + dx[i];

            if (outOfRange(y, x) || map[y][x] == '#') {
                continue;
            }

            // 여기서 bfs 를 목적지를 넣어서 할 수 있으면 좋을 듯, 시작 지점과, 그리고 시작 이동 번호
            cToC[nowCIndex][i] = bfsOfGetCtoC(cPoints[getOppositePointIndex(nowCIndex)], new int[] {y, x}, i, 1);
        }
    }

    static int getOppositePointIndex(int pointNumber) {
        return pointNumber == 0 ? 1 : 0;
    }

    static int bfsOfGetCtoC(int[] destination, int[] startingPoint, int preDir, int moveCount) {
        Queue<int[]> queue = new LinkedList<>();
        visited = new boolean[4][H][W];
        queue.add(new int[] {startingPoint[0], startingPoint[1], preDir, moveCount});
        visited[preDir][startingPoint[0]][startingPoint[1]] = true;

        while (!queue.isEmpty()) {
            int[] point = queue.poll();

            for (int i = 0; i < 4; i++) {
                if (point[2] == i) { // 같은 방향으로 진행하는 것이면 넘어감
                    continue;
                }

                int y = point[0] + dy[i];
                int x = point[1] + dx[i];

                if (outOfRange(y, x) || visited[i][y][x] || map[y][x] == '#') { // outOfRange 이거나, 혹은 이미 이 방향으로 방문했거나, 혹은 '.' 이거나
                    continue;
                }

                if (destination[0] == y && destination[1] == x) { // destination 과 같은 경우, 즉 도달한 경우
                    return point[3] + 1;
                }

                queue.add(new int[] {y, x, i, point[3] + 1});
                visited[i][y][x] = true;
            }
        }

        return Integer.MAX_VALUE;
    }

    static void getAnswer() { // 답을 구하기 위한
        Queue<int[]> queue = new LinkedList<>();
        visited = new boolean[4][H][W];
        queue.add(new int[] {sPoint[0], sPoint[1], -1, 0});

        while (!queue.isEmpty()) {
            int[] point = queue.poll();

            for (int i = 0; i < 4; i++) {
                if (point[2] == i) {
                    continue;
                }

                int y = point[0] + dy[i];
                int x = point[1] + dx[i];

                if (outOfRange(y, x) || visited[i][y][x] || map[y][x] == '#') {
                    continue;
                }

                if (getCIndex(y, x) != Integer.MAX_VALUE) {
                    answer = Math.min(answer, calculateTotalDistance(i, point[3] + 1, getCIndex(y, x)));
                    continue;
                }

                visited[i][y][x] = true;
                queue.add(new int[] {y, x, i, point[3] + 1});
            }
        }
    }

    static int getCIndex(int y, int x) {
        int result = Integer.MAX_VALUE;

        for (int i = 0; i < 2; i++) {
            if (y == cPoints[i][0] && x == cPoints[i][1]) {
                result = i;
            }
        }

        return result;
    }

    static int calculateTotalDistance(int preDir, int moveCount, int cIndex) {
        // 이 부분은 현재 S 가 어떤 상태로 도달했고 (어디 방향) 그리고 값을 몇을 가지고 있으며, 몇번째 인덱스의 C 에 도달했는지를 알면 됨
        int result = Integer.MAX_VALUE;

        for (int i = 0; i < 4; i++) {
            if (cToC[cIndex][i] == Integer.MAX_VALUE || preDir == i) {
                continue;
            }

            result = Math.min(result, cToC[cIndex][i]);
        }

        return result == Integer.MAX_VALUE ? result : result + moveCount;
    }

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1175_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        map = new char[H][W];
        int cIndex = 0;

        for (int i = 0; i < H; i++) {
            String line = br.readLine();
            for (int j = 0; j < W; j++) {
                char character = line.charAt(j);
                map[i][j] = character;

                if (character == 'S') {
                    sPoint[0] = i;
                    sPoint[1] = j;
                    map[i][j] = '.';
                }

                if (character == 'C') {
                    cPoints[cIndex][0] = i;
                    cPoints[cIndex++][1] = j;
                    map[i][j] = '.';
                }
            }
        }

        for (int i = 0; i < 2; i++) {
            getCtoC(i);
        }

        getAnswer();
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}