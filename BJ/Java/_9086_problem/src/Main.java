import java.util.*;
import java.io.*;

// 9086 : 문자열

/**
 * 예제
 * 3
 * ACDKJFOWIEGHE
 * O
 * AB
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_9086_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            String s = br.readLine();

            System.out.println(s.charAt(0) + "" + s.charAt(s.length() - 1));
        }
    }
}
