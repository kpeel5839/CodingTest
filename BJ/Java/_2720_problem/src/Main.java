import java.util.*;
import java.io.*;

// 2720 : 세탁소 사장 동혁

/**
 * Example
 * 3
 * 124
 * 25
 * 194
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2720_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] a = new int[] {25, 10, 5, 1};

        for (int i = 0; i < N; i++) {
            int[] ans = new int[4];
            int num = Integer.parseInt(br.readLine());

            for (int j = 0; j < 4; j++) {
                ans[j] = num / a[j];
                num %= a[j];
            }

            for (int j = 0; j < 4; j++) {
                System.out.print(ans[j] + " ");
            }

            System.out.println();
        }
    }
}
