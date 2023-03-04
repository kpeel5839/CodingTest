import java.util.*;
import java.io.*;

// 2010 : 플러그

/**
 * Example
 * 2
 * 5
 * 8
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2010_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int sum = 0;

        for (int i = 0; i < N; i++) {
            sum--;
            sum += Integer.parseInt(br.readLine());
        }

        System.out.println(++sum);
    }
}