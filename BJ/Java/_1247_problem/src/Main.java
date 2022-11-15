import java.util.*;
import java.io.*;

// 1247 : 부호

/**
 * 예제
 * 3
 * 0
 * 0
 * 0
 * 10
 * 1
 * 2
 * 4
 * 8
 * 16
 * 32
 * 64
 * 128
 * 256
 * -512
 * 6
 * 9223372036854775807
 * 9223372036854775806
 * 9223372036854775805
 * -9223372036854775807
 * -9223372036854775806
 * -9223372036854775804
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1247_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int t = 0; t < 3; t++) {
            int N = Integer.parseInt(br.readLine());
            long sum = 0;

            for (int i = 0; i < N; i++) {
                sum += Long.parseLong(br.readLine());
            }

            if (sum < 0) {
                System.out.println("-");
            } else if (sum == 0) {
                System.out.println("0");
            } else {
                System.out.println("+");
            }
        }
    }
}
