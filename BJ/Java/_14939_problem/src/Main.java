import java.util.*;
import java.io.*;
import java.util.function.Function;

// 14939 : 불 끄기

/**
 * Example
 * #O########
 * OOO#######
 * #O########
 * ####OO####
 * ###O##O###
 * ####OO####
 * ##########
 * ########O#
 * #######OOO
 * ########O#
 */
public class Main {
    public static int SIZE = 10;
    public static int pushCount;
    public static int minimumPush = Integer.MAX_VALUE;
    public static int[] dy = {-1, 0, 1, 0};
    public static int[] dx = {0, 1, 0, -1};
    public static boolean[][] isTurnOn = new boolean[SIZE][SIZE];

    public static void turnOnRight(int firstLineAct) {
        // 첫번째 라인에 대해서만 act 를 진행하고
        // 나머지 라인들은 모두 다 켜져있는 윗 라인을 위주로 진행하게 된다.
        // 일단 isTurnOn 을 전부 copy 하고 firstLineAct 를 기준으로 시작을 진행한다.
        boolean[][] innerTurnOn = new boolean[SIZE][SIZE];
        pushCount = 0;

        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(isTurnOn[i], 0, innerTurnOn[i], 0, SIZE);
        }

        for (int i = 0; i < SIZE; i++) {
            if ((firstLineAct & (1 << i)) != 0) {
                push(innerTurnOn, 0, i);
            }
        }

        for (int i = 1; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (innerTurnOn[i - 1][j]) {
                    push(innerTurnOn, i, j);
                }
            }
        }

        if (isTurnOnAll(innerTurnOn)) {
            minimumPush = Math.min(minimumPush, pushCount);
        }
    }

    public static void push(boolean[][] isTurnOn, int y, int x) {
        pushCount++;

        isTurnOn[y][x] = !isTurnOn[y][x];
        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (!isOutOfRange(ny, nx)) {
                isTurnOn[ny][nx] = !isTurnOn[ny][nx];
            }
        }
    }

    public static boolean isOutOfRange(int y, int x) {
        return (y < 0 || y >= SIZE) || (x < 0 || x >= SIZE);
    }

    public static boolean isTurnOnAll(boolean[][] innerTurnOn) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (innerTurnOn[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_14939_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < SIZE; i++) {
            String line = br.readLine();
            for (int j = 0; j < SIZE; j++) {
                if (line.charAt(j) == 'O') {
                    isTurnOn[i][j] = true;
                }

                if (line.charAt(j) == '#') {
                    isTurnOn[i][j] = false;
                }
            }
        }

        // 전구 기록 했으니까, 첫줄에 대해서 브루트포스를 진행하면 된다.
        // 어떻게? 키고 끄는 형식으로 말이다.
        for (int i = 0; i < 1024; i++) {
            turnOnRight(i);
        }

        System.out.println(minimumPush == Integer.MAX_VALUE ? -1 : minimumPush);
    }
}
