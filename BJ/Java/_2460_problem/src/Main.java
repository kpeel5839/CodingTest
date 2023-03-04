import java.util.*;
import java.io.*;

// 2460 : 지능형 기차 2

/**
 * Example
 * 0 32
 * 3 13
 * 28 25
 * 17 5
 * 21 20
 * 11 0
 * 12 12
 * 4 2
 * 0 8
 * 21 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2460_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int sum = 0;
        int answer = 0;
        for (int i = 0; i < 10; i++) {
            st = new StringTokenizer(br.readLine());
            sum -= Integer.parseInt(st.nextToken());
            sum += Integer.parseInt(st.nextToken());
            answer = Math.max(answer, sum);
        }

        System.out.println(answer);
    }
}