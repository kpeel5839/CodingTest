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

        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]); // {index, value}

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < size; i++) {
            queue.add(new int[] {i, Integer.parseInt(st.nextToken())});

            while (queue.peek()[0] < (i - length + 1)) {
                queue.poll();
            }

            bw.write(queue.peek()[1] + " ");
        }

        bw.flush();
        bw.close();
    }
}
