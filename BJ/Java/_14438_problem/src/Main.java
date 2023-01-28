import java.util.*;
import java.io.*;

// 14438 : 수열과 쿼리 17

/**
 * Example
 * 5
 * 5 4 3 2 1
 * 6
 * 2 1 3
 * 2 1 4
 * 1 5 3
 * 2 3 5
 * 1 4 3
 * 2 3 5
 */
public class Main {
    public static int height;
    public static int[] tree;

    public static void init() {
        for (int i = height - 1; i != -1; i--) {
            for (int j = (int) Math.pow(2, i); j < (int) Math.pow(2, i + 1); j++) {
                tree[j] = Math.min(tree[j * 2], tree[j * 2 + 1]);
            }
        }
    }

    public static void update(int cur, int target, int left, int right) {
        if (!(left <= target && target <= right)) {
            return;
        }

        if (left != right) {
            update(cur * 2, target, left, (left + right) / 2);
            update(cur * 2 + 1, target, (left + right) / 2 + 1, right);
            tree[cur] = Math.min(tree[cur * 2], tree[cur * 2 + 1]);
        }
    }

    public static int search(int cur, int left, int right, int searchLeft, int searchRight) {
        if (right < searchLeft || searchRight < left) {
            return Integer.MAX_VALUE;
        }

        if (searchLeft <= left && right <= searchRight) {
            return tree[cur];
        }

        return Math.min(search(cur * 2, left, (left + right) / 2, searchLeft, searchRight),
                search(cur * 2 + 1, (left + right) / 2 + 1, right, searchLeft, searchRight));
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_14438_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        height = (int) Math.ceil(Math.log(N) / Math.log(2));
        tree = new int[(int) Math.pow(2, height) * 2];
        Arrays.fill(tree, Integer.MAX_VALUE);

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            tree[(int) Math.pow(2, height) + i] = Integer.parseInt(st.nextToken());
        }

        init();
        int query = Integer.parseInt(br.readLine());

        for (int i = 0; i < query; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            if (a == 1) {
                tree[(int) Math.pow(2, height) + b - 1] = c;
                update(1, b, 1, (int) Math.pow(2, height));
            } else {
                bw.write(search(1, 1, (int) Math.pow(2, height), b, c) + "\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
