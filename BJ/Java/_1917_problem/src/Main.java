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
    public static int[][] directionOf3d = new int[][] {{1, 5, 4, 3}, {2, 5, 0, 3}, {4, 5, 1, 3}, {1, 0, 4, 2}, {0, 5, 2, 3}, {1, 2, 4, 0}}; // 가장 중요한 배열, 이걸로 끝임
    public static StringBuilder paper = new StringBuilder();
    public static StringTokenizer stringTokenizer;

    public static String cutPaper() { // 결과를 뱉음, 6 * 6 으로 잘라서 봄, 즉 StringTokenizer 에서 6 * 6 개의 토큰을 빼먹음
        int[][] cutPaper = new int[PAPER_SIZE][PAPER_SIZE];

        for (int i = 0; i < PAPER_SIZE; i++) {
            for (int j = 0; j < PAPER_SIZE; j++) {
                cutPaper[i][j] = Integer.parseInt(stringTokenizer.nextToken());
            }
        }

        boolean foldingIsPossible = false;

        for (int i = 0; i < PAPER_SIZE; i++) {
            for (int j = 0; j < PAPER_SIZE; j++) {
                if (cutPaper[i][j] == 1 && !foldingIsPossible) {
                    foldingIsPossible = foldingPaper(cutPaper, i, j);
                }
            }
        }

        return foldingIsPossible ? POSSIBLE : IMPOSSIBLE;
    }

    public static boolean foldingPaper(int[][] cutPaper, int y, int x) { // 여기서 bfs 로 해주면 됨 directionOf3d 을 이용해서 해주면 된다. [6][4] 의 현재 paper 의 방향대로 접으면 어디 면으로 가는지
        Boolean[] cube = new Boolean[PAPER_SIZE];
        Arrays.fill(cube, false);
        Queue<int[]> pointsInPaper = new LinkedList<>();
        boolean[][] visited = new boolean[PAPER_SIZE][PAPER_SIZE];
        pointsInPaper.add(new int[] {y, x, 0}); // {row, column, face}
        visited[y][x] = true;

        while (!pointsInPaper.isEmpty()) {
            int[] pointInPaper = pointsInPaper.poll();
            cube[pointInPaper[2]] = true;

            for (int i = 0; i < directionOfX.length; i++) {
                int newY = pointInPaper[0] + directionOfY[i];
                int newX = pointInPaper[1] + directionOfX[i];

                if (outOfRange(newY, newX) || visited[newY][newX] || cutPaper[newY][newX] == 0) {
                    continue;
                }

                visited[newY][newX] = true;
                pointsInPaper.add(new int[] {newY, newX, directionOf3d[pointInPaper[2]][i]});
            }
        }

        return validateCube(cube);
    }

    public static boolean validateCube(Boolean[] cube) {
        return Arrays.stream(cube).allMatch(face -> face);
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
