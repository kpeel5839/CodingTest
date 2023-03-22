import java.util.*;
import java.io.*;

// 2921 : 도미노

/**
 * Example
 * 15
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2921_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int answer = 0;

        for (int i = 1; i <= N; i++) {
            answer += (i * (i + 1)) / 2 + (i * (i + 1));
        }

        System.out.println(answer);
    }
}