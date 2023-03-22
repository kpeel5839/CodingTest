import java.util.*;
import java.io.*;

// 4435 : 중간계 전쟁

/**
 * Example
 * 3
 * 1 1 1 1 1 1
 * 1 1 1 1 1 1 1
 * 0 0 0 0 0 10
 * 0 1 1 1 1 0 0
 * 1 0 0 0 0 0
 * 1 0 0 0 0 0 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_4435_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int[][] a = new int[][] {{1, 2, 3, 3, 4, 10}, {1, 2, 2, 2, 3, 5, 10}};
        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            int[] e = new int[2];
            for (int j = 0; j < 2; j++) {
                st = new StringTokenizer(br.readLine());
                int index = 0;

                while (st.hasMoreTokens()) {
                    e[j] += a[j][index++] * Integer.parseInt(st.nextToken());
                }
            }

            System.out.print("Battle " + (i + 1) + ": ");

            if (e[0] > e[1]) {
                System.out.println("Good triumphs over Evil");
            } else if (e[0] < e[1]) {
                System.out.println("Evil eradicates all trace of Good");
            } else {
                System.out.println("No victor on this battle field");
            }
        }
    }
}