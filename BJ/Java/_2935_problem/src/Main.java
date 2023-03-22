import java.util.*;
import java.io.*;

// 2935 : 소음

/**
 * Example
 * 1000
 * *
 * 100
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2935_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String a = br.readLine();
        String b = br.readLine();
        String c = br.readLine();

        if (b.equals("+")) {
            if (a.length() < c.length()) {
                String t = a;
                a = c;
                c = t;
            }

            for (int i = 0; i < a.length(); i++) {
                char aa = a.charAt(i);

                if (a.length() - c.length() <= i) {
                    int dd = (aa - '0') + (c.charAt(i - (a.length() - c.length())) - '0');
                    System.out.print(dd);
                } else {
                    System.out.print(aa);
                }
            }
        } else {
            System.out.print(1);

            for (int i = 0; i < (a.length() - 1) + (c.length() - 1); i++) {
                System.out.print(0);
            }
        }
    }
}
