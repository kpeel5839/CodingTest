import java.util.*;
import java.io.*;

// 2857 : FBI

/**
 * Example
 * N321-CIA
 * F3-B12I
 * F-BI-12
 * OVO-JE-CIA
 * KRIJUMCAR1
 */
public class Main {
    public static void main(String[] args) throws IOException {
         System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2857_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String FAIL = "HE GOT AWAY!";
        int cnt = 0;

        for (int i = 1; i <= 5; i++) {
            String s = br.readLine();
            if (s.contains("FBI")) {
                cnt++;
                System.out.print(i + " ");
            }
        }

        if (cnt == 0) {
            System.out.println(FAIL);
        }
    }
}