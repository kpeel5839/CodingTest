import javax.swing.text.StyleContext;
import java.util.*;
import java.io.*;

// 3460 : 이진수

/**
 * Example
 * 1
 * 13
 */
public class Main {

    public static String qq(int v) {
        StringBuilder res = new StringBuilder();

        while (v != 0) {
            res.insert(0, (v % 2));
            v /= 2;
        }

        return res.toString();
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3460_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        List<Integer> l = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String res = qq(Integer.parseInt(br.readLine()));

            for (int j = res.length() - 1; j != -1; j--) {
                if (res.charAt(j) == '1') {
                    System.out.print((res.length() - j - 1) + " ");
                }
            }

            System.out.println();
        }
    }
}