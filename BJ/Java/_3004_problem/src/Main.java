import java.util.*;
import java.io.*;

// 3004 : 체스판 조각

/**
 * Example
 * 3
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3004_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] n = new int[101];
        n[1] = 2;
        n[2] = 4;
        int addNumber = 2;

        for (int i = 3; i <= 100; i++) {
            n[i] = n[i - 1] + addNumber;

            if (i % 2 == 1) {
                addNumber++;
            }
        }

        System.out.println(n[Integer.parseInt(br.readLine())]);
    }
}