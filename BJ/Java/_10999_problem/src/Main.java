import java.util.*;
import java.io.*;

// 10999 : 구간 합 구하기 2

/**
 * Example
 * 5 2 2
 * 1
 * 2
 * 3
 * 4
 * 5
 * 1 3 4 6
 * 2 2 5
 * 1 1 3 -2
 * 2 2 5
 */
public class Main {
    static long[] tree;
    static long[] lazy;
    static int height;

    static void init() {
        for (int i = height - 1; i != -1; i--) {
            for (int j = 1 << i; j < (1 << (i + 1)); j++) {
                tree[j] = tree[j * 2] + tree[j * 2 + 1];
            }
        }
    }

    static void updateLazy(int cur, int left, int right) {
        if (lazy[cur] != 0) {
            tree[cur] += (lazy[cur] * (right - left + 1));

            if (left != right) { // 인덱스 에러 방지
                lazy[cur * 2] += lazy[cur];
                lazy[cur * 2 + 1] += lazy[cur];
            }

            lazy[cur] = 0;
        }
    }

    static long search(int cur, int left, int right, int searchLeft, int searchRight) {
        updateLazy(cur, left, right);

        if (right < searchLeft || searchRight < left) {
            return 0;
        }

        if (searchLeft <= left && right <= searchRight) {
            return tree[cur];
        }

        return search(cur * 2, left, (left + right) / 2, searchLeft, searchRight)
                + search(cur * 2 + 1, (left + right) / 2 + 1, right, searchLeft, searchRight);
    }

    static void updateRange(long diff, int cur, int left, int right, int rangeLeft, int rangeRight) {
        updateLazy(cur, left, right);

        if (right < rangeLeft || rangeRight < left) {
            return;
        }

        if (rangeLeft <= left && right <= rangeRight) { // 요기서는 완전히니까 lazy 저장해줘야함
            tree[cur] += diff * (right - left + 1);

            if (left != right) {
                lazy[cur * 2] += diff;
                lazy[cur * 2 + 1] += diff;
            }

            return;
        }

        updateRange(diff, cur * 2, left, (left + right) / 2, rangeLeft, rangeRight);
        updateRange(diff, cur * 2 + 1, (left + right) / 2 + 1, right, rangeLeft, rangeRight);
        tree[cur] = tree[cur * 2] + tree[cur * 2 + 1];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10999_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        height = (int) Math.ceil(Math.log(N) / Math.log(2));
        tree = new long[(1 << height) * 2];
        lazy = new long[(1 << height) * 2];

        for (int i = 1 << height; i < (1 << height) + N; i++) {
            tree[i] = Long.parseLong(br.readLine());
        }

        init();
        int loopCount = M + K;

        while (loopCount != 0) {
            loopCount--;

            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());

            if (a == 1) {
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                long diff = Long.parseLong(st.nextToken());

                updateRange(diff, 1, 1, 1 << height, b, c);
            }

            if (a == 2) {
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                bw.write(search(1, 1, 1 << height, b, c) + "\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
