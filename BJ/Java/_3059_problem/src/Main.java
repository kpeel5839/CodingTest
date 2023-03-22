import java.util.*;
import java.io.*;

// 3059 : 등장하지 않은 문자의 합

/**
 * Example
 * 2
 * ABCDEFGHIJKLMNOPQRSTUVW
 * A
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3059_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            boolean[] a = new boolean['Z' - 'A' + 1];
            String s = br.readLine();
            int sum = 0;

            for (int j = 0; j < s.length(); j++) {
                a[s.charAt(j) - 'A'] = true;
            }

            for (int j = 0; j < a.length; j++) {
                if (!a[j]) {
                    sum += (j + 'A');
                }
            }

            System.out.println(sum);
        }
    }
}