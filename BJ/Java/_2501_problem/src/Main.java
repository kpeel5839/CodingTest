import java.util.*;
import java.io.*;

// 2501 : 약수 구하기

/**
 * Example
 * 2735 1
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2501_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int answer = 0;
        int find = 0;

        for (int i = 1; i <= N; i++) {
            if (N % i == 0) {
                if (K == ++find) {
                    answer = i;
                    break;
                }
            }
        }

        System.out.println(answer);
    }
}
