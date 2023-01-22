import java.util.*;
import java.io.*;

// 2268 : 수들의 합 7
/**
 * Example
 * 3 5
 * 0 1 3
 * 1 1 2
 * 1 2 3
 * 0 2 3
 * 0 1 3
 */
public class Main {
    public static long[] tree;

    public static void update(int cur, int findIndex, long diff, int left, int right) {
        if (!(left <= findIndex && findIndex <= right)) {
            return;
        }

        tree[cur] += diff;

        if (left != right) {
            update(cur * 2, findIndex, diff, left, (left + right) / 2);
            update(cur * 2 + 1, findIndex, diff, (left + right) / 2 + 1, right);
        }
    }

    public static long search(int cur, int left, int right, int searchLeft, int searchRight) {
        if (right < searchLeft || searchRight < left) {
            return 0;
        }

//        System.out.println(left + " " + right);

        if (searchLeft <= left && right <= searchRight) {
            return tree[cur];
        }

        return search(cur * 2, left, (left + right) / 2, searchLeft, searchRight)
                + search(cur * 2 + 1, (left + right) / 2 + 1, right, searchLeft, searchRight);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2268_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int height = (int) Math.ceil(Math.log(N) / Math.log(2));

        tree = new long[(int) Math.pow(2, height) * 2];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int query = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());

            if (query == 0) {
                int b = Integer.parseInt(st.nextToken());
                bw.write(search(1, 1, (int) Math.pow(2, height), Math.min(a, b), Math.max(a, b)) + "\n");
            }

            if (query == 1) {
                long b = Integer.parseInt(st.nextToken());
                b -= tree[(int) Math.pow(2, height) + a - 1];

                update(1, a, b, 1, (int) Math.pow(2, height));
            }
        }

//        for (int i = 0; i <= 3; i++) {
//            System.out.println(tree[(int) Math.pow(2, height) + i]);
//        }

        bw.flush();
        bw.close();
    }
}
