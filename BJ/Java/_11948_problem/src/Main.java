import java.util.*;
import java.io.*;

// 11948 : 과목선택

/**
 * Example
 * 100
 * 34
 * 76
 * 42
 * 10
 * 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11948_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int sum = 0;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < 4; i++) {
            int number = Integer.parseInt(br.readLine());
            sum += number;
            min = Math.min(min, number);
        }

        sum -= min;
        min = Integer.MAX_VALUE;

        for (int i = 0; i < 2; i++) {
            int number = Integer.parseInt(br.readLine());
            sum += number;
            min = Math.min(min, number);
        }

        System.out.println(sum - min);
    }
}
