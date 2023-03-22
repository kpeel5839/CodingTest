import java.util.*;
import java.io.*;

// 2953 : 나는 요리사다.

/**
 * Example
 * 4 4 3 3
 * 5 4 3 5
 * 5 5 2 4
 * 5 5 5 1
 * 4 4 4 4
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2953_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int maxIndex = 0;
        int max = -1;
        for (int i = 1; i <= 5; i++) {
            int sum = 0;
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < 4; j++) {
                sum += Integer.parseInt(st.nextToken());
            }

            if (max < sum) {
                maxIndex = i;
                max = sum;
            }
        }

        System.out.println(maxIndex + " " + max);
    }
}