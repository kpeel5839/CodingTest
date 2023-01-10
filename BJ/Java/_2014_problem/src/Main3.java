import java.util.*;
import java.io.*;

// 2014 : 소수의 곱

/**
 * Example
 * 4 19
 * 2 3 5 7
 */
public class Main3 {
    static final int LIMIT = 400_000;
    static Set<Long> alreadyUseNumber = new HashSet<>();
    static PriorityQueue<Long> queue = new PriorityQueue<>(Long::compare);

    static void additionQueue(long number) {
        if (Integer.MAX_VALUE < number) {
            return;
        }

        if (!alreadyUseNumber.contains(number)) {
            queue.add(number);
            alreadyUseNumber.add(number);
        }
    }

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
            alreadyUseNumber.add(prime[i]);
        }

        int count = 0;
        long result = 0;
        while (!queue.isEmpty()) {
            long number = queue.poll();
            result = number;
            count++;

            if (count == K) {
                break;
            }

            for (int i = 0; i < N; i++) {
                additionQueue(number * prime[i]);
            }
        }

        System.out.println(queue.size());

        System.out.println(result);
    }
}
