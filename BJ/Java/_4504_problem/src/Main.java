import javax.swing.plaf.synth.SynthMenuBarUI;
import java.util.*;
import java.io.*;

// 4504 : 배수 찾기

/**
 * Example
 * 3
 * 1
 * 7
 * 99
 * 321
 * 777
 * 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_4504_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int m = Integer.parseInt(br.readLine());

        while (true) {
            int q = Integer.parseInt(br.readLine());

            if (q == 0) {
                break;
            }

            if (q % m == 0) {
                System.out.print(q + " is a multiple of " + m);
            } else {
                System.out.print(q + " is NOT a multiple of " + m);
            }

            System.out.println(".");
        }
    }
}