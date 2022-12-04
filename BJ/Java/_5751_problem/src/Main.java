import java.util.*;
import java.io.*;

// 5751 : Head Or Tail

/**
 * Example
 * 5
 * 0 0 1 0 1
 * 6
 * 0 0 0 0 0 1
 * 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5751_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {
            int N = Integer.parseInt(br.readLine());

            if (N == 0) {
                break;
            }

            st = new StringTokenizer(br.readLine());
            int numberOfMarryWon = 0;

            for (int i = 0; i < N; i++) {
                if (Integer.parseInt(st.nextToken()) == 0) {
                    numberOfMarryWon++;
                }
            }

            bw.write("Mary won " + numberOfMarryWon + " times and John won " + (N - numberOfMarryWon) + " times\n");
        }

        bw.flush();
        bw.close();
    }
}
