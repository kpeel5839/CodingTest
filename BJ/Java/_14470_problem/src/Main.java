import java.util.*;
import java.io.*;

// 14470 : 전자레인지

/**
 * Example
 * -10
 * 20
 * 5
 * 10
 * 3
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_14470_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] info = new int[5];
        for (int i = 0; i < 5; i++) {
            info[i] = Integer.parseInt(br.readLine());
        }

        int time = 0;
        while (info[0] != info[1]) {
            if (info[0] < 0) {
                time += info[2];
            } else if (info[0] == 0) {
                time += (info[3] + info[4]);
            } else {
                time += info[4];
            }

            info[0]++;
        }

        System.out.println(time);
    }
}