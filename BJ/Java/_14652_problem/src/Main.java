import java.util.*;
import java.io.*;

// 14652 : 나는 행복합니다~

/**
 * 예제
 * 6 4 14
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_14652_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int number = Integer.parseInt(st.nextToken());

        System.out.println((number / M) + " " + (number % M));
    }
}
