import java.util.*;
import java.io.*;

// 4388 : 받아올림

/**
 * Example
 * 123 456
 * 555 555
 * 123 594
 * 0 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_4388_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String a = new StringBuilder(st.nextToken()).reverse().toString();
            String b = new StringBuilder(st.nextToken()).reverse().toString();

            if (a.length() < b.length()) {
                String t = a;
                a = b;
                b = t;
            }

            if (a.equals("0") && b.equals("0")) {
                break;
            }

            int answer = 0;
            int q = 0;

            for (int i = 0; i < b.length(); i++) {
                int f = ((a.charAt(i) - '0') + (b.charAt(i) - '0')) + q;
                q = 0;

                if (10 <= f) {
                    q++;
                    answer++;
                }
            }

            int index = b.length();
            while (index < a.length()) {
                int f = (a.charAt(index++) - '0') + q;
                q = 0;

                if (10 <= f) {
                    q++;
                    answer++;
                }
            }

            System.out.println(answer);
        }
    }
}