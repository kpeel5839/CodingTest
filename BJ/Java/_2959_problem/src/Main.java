import java.util.*;
import java.io.*;

// 2959 : 거북이

/**
 * Example
 * 1 2 3 4
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2959_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int max = 0;
        int[] arr = new int[4];

        for (int i = 0; i < 4; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                boolean[] b = new boolean[4];
                int m = Math.min(arr[i], arr[j]);
                b[i] = true;
                b[j] = true;
                int mm = Integer.MAX_VALUE;

                for (int c = 0; c < 4; c++) {
                    if (!b[c]) {
                        mm = Math.min(mm, arr[c]);
                    }
                }

                max = Math.max(max, m * mm);
            }
        }

        System.out.println(max);
    }
}