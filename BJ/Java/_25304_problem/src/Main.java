import java.util.*;
import java.io.*;

// 25304 : 영수증

/**
 * 예제
 * 260000
 * 4
 * 20000 5
 * 30000 2
 * 10000 6
 * 5000 8
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_25304_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int receiptCost = Integer.parseInt(br.readLine());
        int N = Integer.parseInt(br.readLine());
        int realCost = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            realCost += a * b;
        }

        System.out.println(receiptCost == realCost ? "Yes" : "No");
    }
}
