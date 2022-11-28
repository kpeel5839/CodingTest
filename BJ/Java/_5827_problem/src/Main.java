import java.util.*;
import java.io.*;

// 5827 : What's Up With Gravity

/**
 * example
 * 5 5
 * #####
 * #...#
 * #...D
 * #C...
 * ##.##
 */
public class Main {
    public static int height;
    public static int width;
    public static int[] directionY = {-1, 0, 1, 0};
    public static int[] directionX = {0, 1, 0, -1};
    public static int[] directionGravity = {1, -1}; // {flip gravity, normal gravity}
    public static int[] captainPosition = new int[2];
    public static int[] doctorPosition = new int[2];
    public static char[][] map;
    public static boolean[][][] visited;

    public static boolean outOfRange(int y, int x) {
        return (y < 0 || y >= height) || (x < 0 || x >= width);
    }

    public static boolean meetDoctor(int y, int x) {
        return y == doctorPosition[0] && x == doctorPosition[1];
    }

    public static int finalRowIndexWithGravity(int nowRowIndex, int nowColumnIndex, int gravity) { // 인덱스를 주는 것이 좋을 듯, 근데 gravity 에 따라서 위로 통과, 아래로 통과 일텐데, 그것은 넘어가면 -1로 하는 것으로 하자.
        // -1 means of out of range, gravity value is zero or one
        // and Integer.MAX_VALUE means we finally met Doctor beefalo
        int finalRowIndex = nowRowIndex;
        boolean hitWallNotYet = true;

        while (hitWallNotYet) {
            finalRowIndex += directionGravity[gravity];

            if (outOfRange(finalRowIndex, nowColumnIndex)) { // 범위를 빠져나간 경우
                finalRowIndex = -1;
                hitWallNotYet = false; // 닿아버림
            } else if (map[finalRowIndex][nowColumnIndex] == '#') { // hitWall
                finalRowIndex -= directionGravity[gravity];
                hitWallNotYet = false;
            } else if (meetDoctor(finalRowIndex, nowColumnIndex)) {
                finalRowIndex = Integer.MAX_VALUE;
                hitWallNotYet = false;
            }
        }

        return finalRowIndex;
    }

    public static int flipGravity(int gravity) {
        if (gravity == 0) {
            return 1;
        }

        return 0;
    }

    public static int bfs() {
        LinkedList<int[]> queue = new LinkedList<>(); // {y, x, number of flap, now gravity}
        queue.add(new int[] {captainPosition[0], captainPosition[1], 0, 0});
        int distanceUntilReachingDoctor = -1; // 값이 안들어가면 -1 반환
        boolean reachDoctorYet = true;

        while (!queue.isEmpty() && reachDoctorYet) {
            int[] nowCaptainPosition = queue.poll();
            int finalRowIndex = finalRowIndexWithGravity(nowCaptainPosition[0], nowCaptainPosition[1], nowCaptainPosition[3]);

            if (finalRowIndex == -1) {
                continue;
            }

            if (finalRowIndex == Integer.MAX_VALUE) {
                distanceUntilReachingDoctor = nowCaptainPosition[2];
                break;
            }

            if (visited[nowCaptainPosition[3]][finalRowIndex][nowCaptainPosition[1]]) {
                continue;
            }

            visited[nowCaptainPosition[3]][finalRowIndex][nowCaptainPosition[1]] = true;

            // 그리고 왼쪽, 오른쪽으로 그 다음에 플립한 걸 집어넣는다.
            for (int i = 1; i < 4; i += 2) { // 왼쪽, 오른쪽
                int newRow = finalRowIndex + directionY[i];
                int newColumn = nowCaptainPosition[1] + directionX[i];

                if (outOfRange(newRow, newColumn) || visited[nowCaptainPosition[3]][newRow][newColumn] || map[newRow][newColumn] == '#') {
                    continue;
                }

                if (meetDoctor(newRow, newColumn)) {
                    distanceUntilReachingDoctor = nowCaptainPosition[2];
                    reachDoctorYet = false;
                    break;
                }

                queue.addFirst(new int[] {newRow, newColumn, nowCaptainPosition[2], nowCaptainPosition[3]});
            }

            queue.add(new int[] {nowCaptainPosition[0], nowCaptainPosition[1], nowCaptainPosition[2] + 1, flipGravity(nowCaptainPosition[3])});
        }

        return distanceUntilReachingDoctor;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5827_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());
        map = new char[height][width];
        visited = new boolean[2][height][width];

        for (int i = 0; i < height; i++) {
            String lineOfMap = br.readLine();
            for (int j = 0; j < width; j++) { // 여기서 가장 먼저 닿는놈이 승리하도록 해야하는데, C 의 first position 도 필요하고, D 의 position 도 중요함
                map[i][j] = lineOfMap.charAt(j);

                if (map[i][j] == 'D') {
                    doctorPosition[0] = i;
                    doctorPosition[1] = j;
                }

                if (map[i][j] == 'C') {
                    captainPosition[0] = i;
                    captainPosition[1] = j;
                }
            }
        }

        System.out.println(bfs());
    }
}
