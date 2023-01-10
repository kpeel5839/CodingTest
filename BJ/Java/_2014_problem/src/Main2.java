import java.util.*;
import java.io.*;

// 2014 : 소수의 곱

/**
 * Example
 * 4 19
 * 2 3 5 7
 */
public class Main2 {
    static PriorityQueue<Long> queue = new PriorityQueue<>(Long::compare);

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2014_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 일단 먼저 소수를 받음
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        long[] prime = new long[N];
        for (int i = 0; i < N; i++) {
            prime[i] = Integer.parseInt(st.nextToken());
            queue.add(prime[i]);
        }

        long result = 0;
        for (int i = 0; i < K; i++) {
            result = queue.poll();

            for (int j = 0; j < N; j++) {
                queue.add(result * prime[j]);

                if (result % prime[j] == 0) {
                    break;
                }
            }
        }

        System.out.println(result);
    }
}
