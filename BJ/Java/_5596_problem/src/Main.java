import java.util.*;
import java.io.*;

// 5596 : 시험 점수

/**
 * Example
 * 100 80 70 60
 * 80 70 80 90
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5596_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int a = 0;
        int b = 0;
        for (int i = 0; i < 4; i++) {
            a += Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 4; i++) {
            b += Integer.parseInt(st.nextToken());
        }

        System.out.println(Math.max(a, b));
    }
}