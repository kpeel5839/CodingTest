import java.util.*;
import java.io.*;

// 5554 : 심부름 가는 길

/**
 * Example
 * 31
 * 34
 * 7
 * 151
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5554_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int totalSecond = 0;
        for (int i = 0; i < 4; i++) {
            totalSecond += Integer.parseInt(br.readLine());
        }

        System.out.println((totalSecond / 60) + "\n" + (totalSecond % 60));
    }
}
