import java.util.*;
import java.io.*;

// 3507 : Automated Telephone Exchange

/**
 * Example
 * 196
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3507_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final int ATE = Integer.parseInt(br.readLine());
        int numberOfLuckyCount = 0;

        for (int firstSection = 0; firstSection < 100; firstSection++) {
            for (int secondSection = 0; secondSection < 100; secondSection++) {
                if (ATE - firstSection - secondSection == 0) {
                    numberOfLuckyCount++;
                }
            }
        }

        System.out.println(numberOfLuckyCount);
    }
}
