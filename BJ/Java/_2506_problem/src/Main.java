import java.util.*;
import java.io.*;

// 2506 : 점수 계산

/**
 * Example
 * 10
 * 1 0 1 1 1 0 0 1 1 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2506_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int totalScore = 0;
        int correct = 0;

        while (st.hasMoreTokens()) {
            int judge = Integer.parseInt(st.nextToken());

            if (judge == 0) {
                correct = 0;
            }

            if (judge == 1) {
                correct++;
                totalScore += correct;
            }
        }

        System.out.println(totalScore);
    }
}
