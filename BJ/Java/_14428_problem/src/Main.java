import java.util.*;
import java.io.*;

// 14428 : 수열과 쿼리 16

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
    public static int[] tree;
    public static int[] value;
    public static int height;

    public static void init() {
        for (int i = height - 1; i != -1; i--) {
            for (int j = (int) Math.pow(2, i); j < Math.pow(2, i + 1); j++) {
                if (value[tree[j * 2]] <= value[tree[j * 2 + 1]]) {
                    tree[j] = tree[j * 2];
                } else {
                    tree[j] = tree[j * 2 + 1];
                }
            }
        }
    }

    public static int search(int cur, int left, int right, int searchLeft, int searchRight) {
        if (right < searchLeft || searchRight < left) {
            return (int) Math.pow(2, height) * 2;
        }

        if (searchLeft <= left && right <= searchRight) {
            return tree[cur];
        }

        int leftIndex = search(cur * 2, left, (left + right) / 2, searchLeft, searchRight);
        int rightIndex = search(cur * 2 + 1, (left + right) / 2 + 1, right, searchLeft, searchRight);

        if (value[leftIndex] <= value[rightIndex]) {
            return leftIndex;
        } else {
            return rightIndex;
        }
    }

    public static void update(int cur, int target, int left, int right) {
        if (!(left <= target && target <= right)) {
            return;
        }

        if (left != right) {
            update(cur * 2, target, left, (left + right) / 2);
            update(cur * 2 + 1, target, (left + right) / 2 + 1, right);

            if (value[tree[cur * 2]] <= value[tree[cur * 2 + 1]]) {
                tree[cur] = tree[cur * 2];
            } else {
                tree[cur] = tree[cur * 2 + 1];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_14428_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        height = (int) Math.ceil(Math.log(N) / Math.log(2));
        tree = new int[(int) Math.pow(2, height) * 2 + 1];
        value = new int[(int) Math.pow(2, height) * 2 + 1];

        Arrays.fill(value, Integer.MAX_VALUE);
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            value[(int) Math.pow(2, height) + i] = Integer.parseInt(st.nextToken());
            tree[(int) Math.pow(2, height) + i] = (int) Math.pow(2, height) + i;
        }
        
        init();

        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            if (a == 1) { // update
                value[(int) Math.pow(2, height) + b - 1] = c;
                update(1, b, 1, (int) Math.pow(2, height));
            } else { // search
                bw.write(search(1, 1, (int) Math.pow(2, height), b, c) - ((int) Math.pow(2, height) - 1) + "\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
