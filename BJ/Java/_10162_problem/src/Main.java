import java.util.*;
import java.io.*;

// 10162 : 전자레인지

/**
 * Example
 * 100
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10162_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        if (N % 10 != 0) {
            System.out.println(-1);
        }

        if (N % 10 == 0) {
            int numberOfPushAButton = N / 300;
            N %= 300;
            int numberOfPushBButton = N / 60;
            N %= 60;
            int numberOfPushCButton = N / 10;

            System.out.println(numberOfPushAButton + " " + numberOfPushBButton + " " + numberOfPushCButton);
        }
    }
}
