import java.util.*;
import java.io.*;

// 2576 : 홀수

/**
 * Example
 * 12
 * 77
 * 38
 * 41
 * 53
 * 92
 * 85
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2576_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int min = Integer.MAX_VALUE;
        int sum = 0;
        for (int i = 0; i < 7; i++) {
            int number = Integer.parseInt(br.readLine());
            if (number % 2 != 0) {
                sum += number;
                min = Math.min(min, number);
            }
        }

        if (min == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(sum + "\n" + min);
        }

    }
}