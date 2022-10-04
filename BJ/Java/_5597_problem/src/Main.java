import java.util.*;
import java.io.*;

// 5597 : 과제 안내신 분 .. ?

/**
 * 예제
 * 3
 * 1
 * 4
 * 5
 * 7
 * 9
 * 6
 * 10
 * 11
 * 12
 * 13
 * 14
 * 15
 * 16
 * 17
 * 18
 * 19
 * 20
 * 21
 * 22
 * 23
 * 24
 * 25
 * 26
 * 27
 * 28
 * 29
 * 30
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5597_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean[] check = new boolean[31];

        for (int i = 0; i < 28; i++) {
            check[Integer.parseInt(br.readLine())] = true;
        }

        for (int i = 1; i < check.length; i++) {
            if (!check[i]) {
                System.out.println(i);
            }
        }
    }
}
