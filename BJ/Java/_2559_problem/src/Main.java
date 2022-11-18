import java.util.*;
import java.io.*;

// 2559 : 수열

/**
 * 예제
 * 10 5
 * 3 -2 -4 -9 0 3 7 13 8 -3
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2559_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] accumulateSum = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int number = Integer.parseInt(st.nextToken());

            if (i == 0) {
                accumulateSum[i] = number;
            } else {
                accumulateSum[i] = accumulateSum[i - 1] + number;
            }
        }

        int answer = Integer.MIN_VALUE;
        for (int i = M - 1; i < N; i++) {
            if (i == M - 1) { // just compare
                answer = accumulateSum[i];
            } else {
                answer = Math.max(answer, accumulateSum[i] - accumulateSum[i - M]);
            }
        }

        System.out.println(answer);
    }
}
