import java.util.*;
import java.io.*;

// 3058 : 짝수를 찾아라

/**
 * Example
 * 2
 * 1 2 3 4 5 6 7
 * 13 78 39 42 54 93 86
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3058_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int min = Integer.MAX_VALUE;
            int sum = 0;

            for (int j = 0; j < 7; j++) {
                int n = Integer.parseInt(st.nextToken());

                if (n % 2 == 0) {
                    sum += n;
                    min = Math.min(min, n);
                }
            }

            System.out.println(sum + " " + min);
        }
    }
}
