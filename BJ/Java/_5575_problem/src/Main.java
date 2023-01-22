import java.util.*;
import java.io.*;

// 5575 : 타임카드

/**
 * Example
 * 9 0 0 18 0 0
 * 9 0 1 18 0 0
 * 12 14 52 12 15 30
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5575_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            int start = 0;
            int end = 0;
            int multiple = 3600;

            for (int j = 0; j < 3; j++) {
                start += Integer.parseInt(st.nextToken()) * multiple;
                multiple /= 60;
            }

            multiple = 3600;
            for (int j = 0; j < 3; j++) {
                end += Integer.parseInt(st.nextToken()) * multiple;
                multiple /= 60;
            }

            int diff = end - start;
            int hour = diff / 3600;
            diff %= 3600;
            int minute = diff / 60;
            diff %= 60;

            System.out.println(hour + " " + minute + " " + diff);
        }
    }
}
