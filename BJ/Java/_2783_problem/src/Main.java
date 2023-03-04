import java.util.*;
import java.io.*;

// 2783 : 삼각 김밥

/**
 * Example
 * 100 5
 * 3
 * 99 8
 * 65 14
 * 78 10
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2783_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        final double BUY = 1000;
        double ans = Double.parseDouble(st.nextToken()) * (BUY / Double.parseDouble(st.nextToken()));
        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            ans = Math.min(ans, Double.parseDouble(st.nextToken()) * (BUY / Double.parseDouble(st.nextToken())));
        }

        System.out.printf("%.2f%n", Math.round(ans * 100) / 100d);
    }
}