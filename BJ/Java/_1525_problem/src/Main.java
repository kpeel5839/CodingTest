import java.util.*;
import java.io.*;

// 1525 : 퍼즐

/**
 * Example
 * 1 0 3
 * 4 2 5
 * 7 8 6
 */
public class Main {
    public static final int PUZZLE_SIZE = 3;
    public static int[] directionOfX = new int[] {0, 1, 0, -1};
    public static int[] directionOfY = new int[] {-1, 0, 1, 0};
    public static Set<String> visited = new HashSet<>();

    public static int[][] stringToPuzzle(String stringPuzzle) {
        int[][] puzzle = new int[PUZZLE_SIZE][PUZZLE_SIZE];

        for (int i = 0; i < PUZZLE_SIZE; i++) {
            for (int j = 0; j < PUZZLE_SIZE; j++) {
                puzzle[i][j] = stringPuzzle.charAt(i * 3 + j) - '0';
            }
        }

        return puzzle;
    }

    public static String puzzleToString(int[][] puzzle) {
        StringBuilder stringPuzzle = new StringBuilder();

        for (int i = 0; i < PUZZLE_SIZE; i++) {
            for (int j = 0; j < PUZZLE_SIZE; j++) {
                stringPuzzle.append(puzzle[i][j]);
            }
        }

        return stringPuzzle.toString();
    }

    public static boolean isCorrectPuzzle(int[][] puzzle) {
        int[][] correctPuzzle = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        for (int i = 0; i < PUZZLE_SIZE; i++) {
            for (int j = 0; j < PUZZLE_SIZE; j++) {
                if (correctPuzzle[i][j] != puzzle[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isOutOfRange(int y, int x) {
        return (y < 0 || y >= PUZZLE_SIZE) || (x < 0 || x >= PUZZLE_SIZE);
    }

    public static void printPuzzle(int[][] puzzle) {
        for (int i = 0; i < PUZZLE_SIZE; i++) {
            for (int j = 0; j < PUZZLE_SIZE; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }

    public static void printQueue(Queue<Object[]> queue) {
        System.out.print("[");
        for (Object[] objects : queue) {
            System.out.print("{" + (int) objects[0] + ", " + (String) objects[1] + "} ");
        }

        System.out.println("]");
    }

    public static int bfs(int[][] initialPuzzle) {
        String stringPuzzle = puzzleToString(initialPuzzle);
        visited.add(stringPuzzle);
        Queue<Object[]> queue = new LinkedList<>();
        queue.add(new Object[] {0, stringPuzzle}); // {움직인 거리, 현재 맵 상태}

        while (!queue.isEmpty()) {
            Object[] information = queue.poll();
            int[][] nowPuzzle = stringToPuzzle((String) information[1]);

            if (isCorrectPuzzle(nowPuzzle)) {
                return (int) information[0];
            }

            for (int i = 0; i < PUZZLE_SIZE; i++) {
                for (int j = 0; j < PUZZLE_SIZE; j++) {
                    if (nowPuzzle[i][j] != 0) {
                        for (int dir = 0; dir < 4; dir++) {
                            int newY = i + directionOfY[dir];
                            int newX = j + directionOfX[dir];

                            if (isOutOfRange(newY, newX) || nowPuzzle[newY][newX] != 0) {
                                continue;
                            }

                            int temp = nowPuzzle[i][j];
                            nowPuzzle[i][j] = 0;
                            nowPuzzle[newY][newX] = temp; // 변경
                            String newPuzzle = puzzleToString(nowPuzzle);

                            if (visited.contains(newPuzzle)) { // 방문처리
                                nowPuzzle[i][j] = temp;
                                nowPuzzle[newY][newX] = 0;
                                continue;
                            }

                            visited.add(newPuzzle);
                            queue.add(new Object[] {(int) information[0] + 1, newPuzzle});
                            nowPuzzle[i][j] = temp;
                            nowPuzzle[newY][newX] = 0; // 다시 돌려놓기
                        }
                    }
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1525_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int[][] initialPuzzle = new int[PUZZLE_SIZE][PUZZLE_SIZE];
        for (int i = 0; i < PUZZLE_SIZE; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < PUZZLE_SIZE; j++) {
                initialPuzzle[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(bfs(initialPuzzle));
    }
}
