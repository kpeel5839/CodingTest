import java.util.*;
import java.io.*;

// 10101 : 삼각형 외우기

/**
 * Example
 * 60
 * 70
 * 50
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10101_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int anglesSum = 0;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            int angle = Integer.parseInt(br.readLine());
            anglesSum += angle;
            set.add(angle);
        }

        if (anglesSum != 180) {
            System.out.println("Error");
            return;
        }

        if (set.size() == 1) {
            System.out.println("Equilateral");
        }

        if (set.size() == 2) {
            System.out.println("Isosceles");
        }

        if (set.size() == 3) {
            System.out.println("Scalene");
        }
    }
}
