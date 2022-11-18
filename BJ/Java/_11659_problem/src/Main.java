import java.util.*;
import java.io.*;

// 11659 : 구간 합 구하기 4

/**
 * 예제
 * 5 3
 * 5 4 3 2 1
 * 1 3
 * 2 4
 * 5 5
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11659_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        long[] sectionSum = new long[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            long number = Integer.parseInt(st.nextToken());
            if (i == 0) {
                sectionSum[i] = number;
            } else {
                sectionSum[i] += sectionSum[i - 1] + number;
            }
        }

        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            start = start == 0 ? start : start - 1;
            int end = Integer.parseInt(st.nextToken());
            answer.append(sectionSum[end] - sectionSum[start]).append("\n");
        }

        System.out.println(answer);
    }
}
