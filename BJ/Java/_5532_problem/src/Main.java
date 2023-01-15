import java.util.*;
import java.io.*;

// 5532 : 방학 숙제

/**
 * Example
 * 20
 * 25
 * 30
 * 6
 * 8
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5532_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        float[] homework = new float[5];
        for (int i = 0; i < 5; i++) {
            homework[i] = Integer.parseInt(br.readLine());
        }

        System.out.println((int) homework[0] - Math.max((int) Math.ceil(homework[1] / homework[3]), (int) Math.ceil(homework[2] / homework[4])));
    }
}
