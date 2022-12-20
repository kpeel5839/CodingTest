import java.util.*;
import java.io.*;

// 2490 : 윷놀이

/**
 * Example
 * 0 1 0 1
 * 1 1 1 0
 * 0 0 1 1
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2490_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        String[] name = {"A", "B", "C", "D", "E"};
        int[][] rule = {{1, 3}, {2, 2}, {3, 1}, {4, 0}, {0, 4}};

        while (true) {
            String information = br.readLine();

            if (information == null) {
                break;
            }

            int numberOfFirst = 0; // 배 (0)
            int numberOfSecond = 0; // 등 (1)
            st = new StringTokenizer(information);

            while (st.hasMoreTokens()) {
                int number = Integer.parseInt(st.nextToken());

                if (number == 0) {
                    numberOfFirst++;
                }

                if (number == 1) {
                    numberOfSecond++;
                }
            }

            for (int i = 0; i < rule.length; i++) {
                if (rule[i][0] == numberOfFirst && rule[i][1] == numberOfSecond) {
                    bw.write(name[i] + "\n");
                    break;
                }
            }
        }

        bw.flush();
        bw.close();
    }
}
