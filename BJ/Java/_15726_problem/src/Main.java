import java.util.*;
import java.io.*;

// 15726 : 이칙연산

/**
 * Example
 * 32 16 8
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_15726_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        double[] number = new double[3];
        for (int i = 0; i < 3; i++) {
            number[i] = Double.parseDouble(st.nextToken());
        }

        System.out.println(Math.max((long) (number[0] * number[1] / number[2]), (long) (number[0] / number[1] * number[2])));
    }
}
