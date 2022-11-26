import java.util.*;
import java.io.*;

// 3276 : ICONS

/**
 * Example
 * 2
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3276_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int pebbles = Integer.parseInt(br.readLine());

        Loop:
        for (int sum = 2; sum <= 20; sum++) {
            for (int i = 1; i < sum; i++) {
                if (pebbles <= i * (sum - i)) {
                    System.out.println(i + " " + (sum - i));
                    break Loop;
                }
            }
        }
    }
}
