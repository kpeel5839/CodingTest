import java.util.*;
import java.io.*;

// 11003 : 최솟값 찾기

/**
 * Example
 * 12 3
 * 1 5 2 3 6 2 3 7 3 5 2 6
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11003_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int size = Integer.parseInt(st.nextToken());
        int length = Integer.parseInt(st.nextToken());
        Deque<int[]> deque = new ArrayDeque<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < size; i++) {
            int number = Integer.parseInt(st.nextToken());
            while (!deque.isEmpty() && number < deque.peekLast()[1]) {
                deque.pollLast();
            }

            deque.addLast(new int[] {i, number});

            if (deque.peekFirst()[0] + length == i) {
                deque.pollFirst();
            }

            bw.write(deque.peekFirst()[1] + " ");
        }


        bw.flush();
        bw.close();
    }
}
