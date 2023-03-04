import java.util.*;
import java.io.*;

// 2903 : 중앙 이동 알고리즘

/**
 * Example
 * 5
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2903_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        long n = Integer.parseInt(br.readLine());

        System.out.println((long) Math.pow(Math.pow(2, n) + 1, 2));
    }
}
