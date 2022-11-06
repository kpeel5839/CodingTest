import java.util.*;
import java.io.*;

// 2587 : 대표값 2

/**
 * 예제
 * 10
 * 40
 * 30
 * 60
 * 30
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2587_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] number = new int[5];
        int sum = 0;

        for (int i = 0; i < 5; i++) {
            number[i] = Integer.parseInt(br.readLine());
            sum += number[i];
        }

        Arrays.sort(number);
        System.out.println(sum / 5);
        System.out.println(number[2]);
    }
}
