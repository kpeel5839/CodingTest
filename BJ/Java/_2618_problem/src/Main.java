import java.util.*;
import java.io.*;

// 2618 : 경찰차

/**
 * 예제
 * 6
 * 3
 * 3 5
 * 5 5
 * 2 3
 */
public class Main {
    static int N;
    static int W;
    static Point[] left;
    static Point[] right;
    static int[][] dp;
    static int[][] select;

    static class Point {
        int y;
        int x;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static int dfs(int left, int right) {
        int depth = Math.max(left, right) + 1;

        if ((depth - 1) == W) {
            return 0;
        }

        if (dp[left][right] != 0) {
            return dp[left][right];
        }

        int leftValue = getDis(left, depth, true) + dfs(depth, right);
        int rightValue = getDis(right, depth, false) + dfs(left, depth);

        if (leftValue < rightValue) { // left 로 이동
            select[left][right] = 1;
        } else {
            select[left][right] = 2;
        }

        dp[left][right] = Math.min(leftValue, rightValue);
        return dp[left][right];
    }

    static void tracking(int left, int right, BufferedWriter bw) throws IOException {
        while (true) {
            int depth = Math.max(left, right) + 1;

            if (depth - 1 == W) { // 마지막 사건이면
                break;
            }

            if (select[left][right] == 1) { // 왼쪽선택
                bw.write(1 + "\n");
                left = depth;
            } else { // 오른쪽 선택
                bw.write(2 + "\n");
                right = depth;
            }
        }
    }

    static int getDis(int from, int to, boolean l) {
        Point p1;
        Point p2;

        if (l) {
            p1 = left[from];
            p2 = left[to];
        } else {
            p1 = right[from];
            p2 = right[to];
        }

        return Math.abs(p1.y - p2.y) + Math.abs(p1.x - p2.x);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2618_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        W = Integer.parseInt(br.readLine());

        dp = new int[W + 1][W + 1];
        select = new int[W + 1][W + 1];
        left = new Point[W + 1];
        right = new Point[W + 1];
        left[0] = new Point(1, 1);
        right[0] = new Point(N, N);

        for (int i = 1; i <= W; i++) {
            st = new StringTokenizer(br.readLine());

            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            left[i] = new Point(y, x);
            right[i] = new Point(y, x);
        }

        bw.write((dfs(0, 0) + "\n")); // 답까지는 출력했다. 이제 추적하면 된다.
        tracking(0, 0, bw);

        bw.flush();
        bw.close();
    }
}
