import java.util.*;
import java.io.*;

// 3015 : 오아시스 재결합

/**
 * Example
 * 7
 * 2
 * 4
 * 1
 * 2
 * 2
 * 5
 * 1
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3015_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        Stack<int[]> stack = new Stack<>(); // {value, count}
        long answer = 0;

        for (int i = 0; i < N; i++) {
            int value = Integer.parseInt(br.readLine());
            int sameValueCount = 1;

            while (!stack.isEmpty() && stack.peek()[0] <= value) {
                int[] popValue = stack.pop();

                if (popValue[0] == value) {
                    sameValueCount += popValue[1];
                }

                answer += popValue[1];
            }

            if (!stack.isEmpty()) {
                answer++;
            }

            stack.push(new int[] {value, sameValueCount});
        }

        System.out.println(answer);
    }
}
