import java.util.*;
import java.io.*;
import java.util.function.Function;

import static java.lang.Math.log;

// 1275 : 커피숍2

/**
 * -- 전제 조건
 * 그냥 평범한 세그먼트 트리 문제이다.
 *
 * 처음에 N 과 턴의 개수 Q 가 주어진다.
 * 그리고 다음줄에 N 개의 정수가 주어지고 (연달아서)
 * x y a b 가 주어진다.
 *
 * 이것이 의미하는 것은 x ~ y 까지의 합을 구하고 a 번째 수를 b 로 바꾸세요이다.
 * 그리고 이 문제는 굉장히 치사하게 x, y 의 값이 꼭 x 가 작게 주어지지는 않는다.
 *
 * -- 틀 설계
 * 그냥 세그먼트 트리 문제이다.
 */
public class Main {
    static int N;
    static int Q;
    static int S;
    static long[] tree;

    static void init() {
        for (int i = S - 1; i != 0; i--) {
            tree[i] = tree[i * 2] + tree[i * 2 + 1];
        }
    }

    static long sum(int left, int right, int cur, int sumLeft, int sumRight) {
        if (right < sumLeft || sumRight < left) {
            return 0;
        }

        if (sumLeft <= left && right <= sumRight) {
            return tree[cur];
        }

        return sum(left, (left + right) / 2, cur * 2, sumLeft, sumRight)
                + sum((left + right) / 2 + 1, right, cur * 2 + 1, sumLeft, sumRight);
    }

    static void update(int left, int right, int cur, int target, long diff) {
        if (!(left <= target && target <= right)) {
            return;
        }

        tree[cur] += diff;

        if (left != right) {
            update(left, (left + right) / 2, cur * 2, target, diff);
            update((left + right) / 2 + 1, right, cur * 2 + 1, target, diff);
        }
    }
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1275_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        Q = fun.apply(input[1]);
        S = 1 << (int) (Math.ceil(log(N) / log(2)));
        tree = new long[2 * S];

        input = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) {
            tree[S + i - 1] = Long.parseLong(input[i - 1]);
        }

        init();

//        for (int i = 1; i < tree.length; i++) {
//            System.out.println(tree[i]);
//        }

        for (int i = 0; i < Q; i++) {
            input = br.readLine().split(" ");

            int x = fun.apply(input[0]);
            int y = fun.apply(input[1]);
            int a = fun.apply(input[2]);
            long b = Long.parseLong(input[3]);

            if (x > y) { // x 가 더 큰 경우 바꿔준다.
                int temp = x;
                x = y;
                y = temp;
            }

            sb.append(sum(1, S, 1, x, y)).append("\n");
            update(1, S, 1, a, b - tree[S + a - 1]);
        }

//        for (int i = 0; i < S; i++) {
//            System.out.print(tree[S + i] + " ");
//        }

        System.out.print(sb);
    }
}
