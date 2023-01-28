import java.util.*;
import java.io.*;

// 3653 : 영화수집 

/**
 * Example
 * 2
 * 3 3
 * 3 1 1
 * 5 3
 * 4 4 5
 */
public class Main {
    public static int[] tree;
    public static int[] index;
    public static int height;

    public static void update(int cur, int diff, int target, int left, int right) {
        if (!(left <= target && target <= right)) {
            return;
        }

        tree[cur] += diff;

        if (left != right) {
            update(cur * 2, diff, target, left, (left + right) / 2);
            update(cur * 2 + 1, diff, target, (left + right) / 2 + 1, right);
        }
    }

    public static int search(int cur, int left, int right, int searchLeft, int searchRight) {
        if (searchRight < left || right < searchLeft) {
            return 0;
        }

        if (searchLeft <= left && right <= searchRight) {
            return tree[cur];
        }

        return search(cur * 2, left, (left + right) / 2, searchLeft, searchRight)
                + search(cur * 2 + 1, (left + right) / 2 + 1, right, searchLeft, searchRight);
    }

    public static void init() {
        // init 은 2 ^ (height - 1) 부터 쭉쭉
        for (int i = height - 1; i != -1; i--) {
            for (int j = (int) Math.pow(2, i); j < (int) Math.pow(2, i + 1); j++) {
                tree[j] = tree[j * 2] + tree[j * 2 + 1];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3653_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            height = (int) Math.ceil(Math.log(N + M) / Math.log(2));
            tree = new int[(int) Math.pow(2, height) * 2];
            index = new int[N + 1];

            for (int i = 1; i < index.length; i++) {
                index[i] = M + i;
                tree[(int) Math.pow(2, height) + M + i - 1] = 1;
            }

            init();
            int fillDvdIndex = M;

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                int dvd = Integer.parseInt(st.nextToken());
                bw.write(search(1, 1, (int) Math.pow(2, height), 1, index[dvd] - 1) + " ");
                update(1, -1, index[dvd], 1, (int) Math.pow(2, height)); // index[dvd] 를 업데이트
                update(1, 1, fillDvdIndex, 1, (int) Math.pow(2, height));
                index[dvd] = fillDvdIndex;
                fillDvdIndex--;
            }

            bw.write("\n");
        }

        bw.flush();
        bw.close();
    }
}
