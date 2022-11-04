import java.util.*;
import java.io.*;

// 2566 : 최댓값

/**
 * 예제
 * 3 23 85 34 17 74 25 52 65
 * 10 7 39 42 88 52 14 72 63
 * 87 42 18 78 53 45 18 84 53
 * 34 28 64 85 12 16 75 36 55
 * 21 77 45 35 28 75 90 76 1
 * 25 87 65 15 28 11 37 28 74
 * 65 27 75 41 7 89 78 64 39
 * 47 47 70 45 23 65 3 41 44
 * 87 13 82 38 31 12 29 29 80
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2566_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int[][] matrix = new int[10][10];

        for (int i = 1; i <= 9; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= 9; j++) {
                matrix[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int max = -1;
        int row = -1;
        int col = -1;

        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (max < matrix[i][j]) {
                    max = matrix[i][j];
                    row = i;
                    col = j;
                }
            }
        }

        System.out.println(max);
        System.out.println(row + " " + col);
    }
}
