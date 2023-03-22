import java.util.*;
import java.io.*;

// 2997 : 네 번째 수

/**
 * Example
 * 4 6 8
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2997_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] n = new int[3];

        for (int i = 0; i < 3; i++) {
            n[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(n);

        if (n[2] - n[1] == n[1] - n[0]) {
            System.out.println(n[2] + (n[2] - n[1]));
        } else {
            if (n[2] - n[1] > n[1] - n[0]) {
                System.out.println(n[1] + (n[1] - n[0]));
            } else {
                System.out.println(n[0] + (n[2] - n[1]));
            }
        }
    }
}