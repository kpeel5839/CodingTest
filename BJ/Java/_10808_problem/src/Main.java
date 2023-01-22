import java.util.*;
import java.io.*;

// 10808 : 알파벳 개수

/**
 * Example
 * baekjoon
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10808_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] count = new int['z' - 'a' + 1];
        String line = br.readLine();
        for (int i = 0; i < line.length(); i++) {
            count[line.charAt(i) - 'a']++;
        }

        for (int i = 0; i < count.length; i++) {
            System.out.print(count[i] + " ");
        }
    }
}
