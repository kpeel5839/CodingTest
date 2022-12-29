import java.util.*;
import java.io.*;

// 1145 : 적어도 배수

/**
 * Example
 * 30 42 70 35 90
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1145_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] numbers = new int[5];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }

        int multipleAtLease = 1;
        while (true) {
            int divideCount = 0;

            for (int i = 0; i < 5; i++) {
                if (multipleAtLease % numbers[i] == 0) {
                    divideCount++;
                }
            }

            if (3 <= divideCount) {
                System.out.println(multipleAtLease);
                break;
            }

            multipleAtLease++;
        }
    }
}
