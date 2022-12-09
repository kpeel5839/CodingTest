import java.util.*;
import java.io.*;

// 1917 : 정육면체 전개도

/**
 * Example
 * 0 0 0 0 0 0
 * 0 0 0 0 0 0
 * 0 0 1 0 0 0
 * 0 1 1 1 1 0
 * 0 0 1 0 0 0
 * 0 0 0 0 0 0
 * 0 1 1 0 0 0
 * 0 1 0 0 0 0
 * 0 1 0 0 0 0
 * 1 1 0 0 0 0
 * 0 0 0 0 0 0
 * 0 0 0 0 0 0
 * 0 0 0 0 0 0
 * 0 0 0 1 1 0
 * 0 0 1 1 0 0
 * 0 0 0 1 1 0
 * 0 0 0 0 0 0
 * 0 0 0 0 0 0
 */
public class Main {
    public static final int PAPER_SIZE = 6;
    public static final String POSSIBLE = "yes";
    public static final String IMPOSSIBLE = "no";
    public static int[] directionOfX = new int[] {0, 1, 0, -1};
    public static int[] directionOfY = new int[] {-1, 0, 1, 0};
    public static Boolean[] dice;
    public static boolean[][] visited;
    public static StringBuilder paper = new StringBuilder();
    public static StringTokenizer stringTokenizer;

    public static void roll(int dir) {
        if (dir == 0) {
            up();
        }

        if (dir == 1) {
            right();
        }

        if (dir == 2) {
            down();
        }

        if (dir == 3) {
            left();
        }
    }

    public static void up() {
        boolean temp = dice[0];
        dice[0] = dice[1];
        dice[1] = dice[2];
        dice[2] = dice[3];
        dice[3] = temp;
    }

    public static void right() {
        boolean temp = dice[0];
        dice[0] = dice[5];
        dice[5] = dice[2];
        dice[2] = dice[4];
        dice[4] = temp;
    }

    public static void down() {
        boolean temp = dice[3];
        dice[3] = dice[2];
        dice[2] = dice[1];
        dice[1] = dice[0];
        dice[0] = temp;
    }

    public static void left() {
        boolean temp = dice[5];
        dice[5] = dice[0];
        dice[0] = dice[4];
        dice[4] = dice[2];
        dice[2] = temp;
    }

    public static String cutPaper() { // 결과를 뱉음, 6 * 6 으로 잘라서 봄, 즉 StringTokenizer 에서 6 * 6 개의 토큰을 빼먹음
        int[][] cutPaper = new int[PAPER_SIZE][PAPER_SIZE];
        int row = -1;
        int col = -1;

        for (int i = 0; i < PAPER_SIZE; i++) {
            for (int j = 0; j < PAPER_SIZE; j++) {
                cutPaper[i][j] = Integer.parseInt(stringTokenizer.nextToken());

                if (row == -1 && col == -1 && cutPaper[i][j] == 1) {
                    row = i;
                    col = j;
                }
            }
        }

        visited = new boolean[PAPER_SIZE][PAPER_SIZE];
        dice = new Boolean[PAPER_SIZE];
        Arrays.fill(dice, false);
        rollDice(cutPaper, row, col);
        return validateCube();
    }

    public static void rollDice(int[][] cutPaper, int y, int x) {
        visited[y][x] = true;
        dice[0] = true;

        for (int i = 0; i < 4; i++) {
            int newY = y + directionOfY[i];
            int newX = x + directionOfX[i];

            if (outOfRange(newY, newX) || visited[newY][newX] || cutPaper[newY][newX] == 0) {
                continue;
            }

            roll(i);
            rollDice(cutPaper, newY, newX);
            roll(reverse(i));
        }
    }

    public static int reverse(int dir) {
        return (dir + 2) % 4;
    }

    public static String validateCube() {
        return Arrays.stream(dice).allMatch(face -> face) ? POSSIBLE : IMPOSSIBLE;
    } // 가능한 정육면체면 POSSIBLE, 아니면 IMPOSSIBLE 반환

    public static boolean outOfRange(int y, int x) {
        return (y < 0 || y >= PAPER_SIZE) || (x < 0 || x >= PAPER_SIZE);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1917_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        boolean remainPaper = true;

        while (remainPaper) {
            for (int i = 0; i < PAPER_SIZE; i++) {
                String line = br.readLine();

                if (line == null) {
                    remainPaper = false;
                    break;
                }

                paper.append(line).append("\n");
            }
        }

        stringTokenizer = new StringTokenizer(paper.toString());

        while (stringTokenizer.hasMoreTokens()) {
            bw.write(cutPaper() + "\n");
        }

        bw.flush();
        bw.close();
    }
}
