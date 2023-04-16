import java.util.*;
import java.io.*;

// 1417 : 국회의원 선거

/**
 * Example
 * 3
 * 5
 * 7
 * 7
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1417_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        PriorityQueue<Integer> q = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int N = Integer.parseInt(br.readLine());
        int v = Integer.parseInt(br.readLine());

        for (int i = 1; i < N; i++) {
            q.add(Integer.parseInt(br.readLine()));
        }

        int ans = 0;

        while (!q.isEmpty() && v <= q.peek()) {
            Integer poll = q.poll();
            q.add(poll - 1);
            ans++;
            v++;
        }

        System.out.println(ans);
    }
}