import java.util.*;
import java.io.*;

// 2563 : 색종이

/**
 * 예제
 * 3
 * 3 7
 * 15 7
 * 5 2
 */
public class Main {
    static int N;
    static final int SIZE = 101;
    static boolean[][] drawingPaper = new boolean[SIZE][SIZE];

    static void stickPaper(int row, int col) {
        for (int i = row; i < row + 10; i++) {
            for (int j = col; j < col + 10; j++) {
                drawingPaper[i][j] = true;
            }
        }
    }

    static int countStickPaper() {
        int answer = 0;

        for (int i = 1; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                if (drawingPaper[i][j]) {
                    answer++;
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2563_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            stickPaper(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        System.out.println(countStickPaper());
    }
}
