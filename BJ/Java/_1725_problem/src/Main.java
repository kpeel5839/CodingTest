import java.util.*;
import java.io.*;
import java.util.function.Function;

import static java.lang.Math.log;

// 1725 : 히스토그램

/**
 * -- 전제 조건
 * 히스토그램 막대 그래프 내에서 가장 큰 직사각형을 찾아라.
 *
 * -- 틀 설계
 * 이 문제 같은 경우는 사실 원래
 * 스택으로 푼 적이 있다.
 * 하지만, 그거는 아무리 풀어도 내 머릿속에 남지 않는 풀이 방법이였고,
 * 그렇기 때문에 나는 이 풀이 방법은 나에게 맞지 않다라는 생각을 한 문제였다
 *
 * 그래서 이 문제를 분할정복으로 풀어야 겠다라는 생각을 했다.
 * 근데, 그러기에는 너무 양쪽에서 가장 작은 값ㅇ르 찾는 과정이 말이 안되게 길었다.
 *
 * 그래서 이 문제를 쉽게 해결해줄 수 있는
 * 누적된 값에 대해서, 연속된 구간에 대해서 아주 빠르게 해답을 구해줄 수 있는 세그먼트 트리를 이용하기로 했다.
 *
 * 사실 중간에 값이 변경되거나 그런일은 전혀 없다.
 * 하지만, 세그먼트 트리를 사용하게 되면, 시간적으로, 공간적으로 많은 이득을 볼 수 있다.
 *
 * 이 문제의 핵심은 현재 범위에서 가장 낮은 값의 인덱스를 찾고
 * 반으로 분할하는 것이다.
 *
 * 즉, recursion Method 에서 필요한 인자는 범위이다.
 * 그거 말고는 전혀 필요없다.
 *
 * 이렇게 연산을 진행해서 가장 max 직사각형의 넓이를 구해내면 된다.
 * 직사각형의 넓이를 구하는 공식은 현재 범위 (right - left + 1) * min height 이다.
 * 즉, min height 도 같이 받아야 한다.
 */
public class Main {
    static int N;
    static int S;
    static final int MAX = 1_000_000_000;
    static long ans = -1;
    static Tree[] tree;

    static class Tree {
        int index;
        int height;

        Tree(int index, int height) {
            this.index = index;
            this.height = height;
        }

        @Override
        public String toString() {
            return "{info(" + " index : " + index + " height : " + height + ")}";
        }
    }

    static void init() {
        for (int i = S - 1; i != 0; i--) {
            int minIndex = i * 2;

            if (tree[i * 2 + 1].height < tree[i * 2].height) {
                minIndex++;
            }

            tree[i] = new Tree(tree[minIndex].index, tree[minIndex].height);
        }
    }

    static Tree query(int left, int right, int cur, int findLeft, int findRight) {
        if (findRight < left || right < findLeft) {
            return new Tree(MAX, MAX); // 의미 없는 것을 반환
        }

        if (findLeft <= left && right <= findRight) { // 와 이거 바보같이 하고 있었네... 개멍청이 새끼
            return tree[cur];
        }

        Tree t1 = query(left, (left + right) / 2, cur * 2, findLeft, findRight);
        Tree t2 = query((left + right) / 2 + 1, right, cur * 2 + 1, findLeft, findRight);

        if (t1.height < t2.height) {
            return t1;
        } else {
            return t2;
        }
    }

    static void recursion(int left, int right) {
        if (left == right) {
            ans = Math.max(ans, query(1, S, 1, left, right).height);
            return;
        }

        Tree info = query(1, S, 1, left, right); // 현재 범위에서 가장 작은 값을 알아낸다.

        if (info.index != left) {
            recursion(left, info.index - 1);
        }

        if (info.index != right) {
            recursion(info.index + 1, right);
        }

        ans = Math.max(ans, (long) (right - left + 1) * info.height);
    }
    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1725_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        N = fun.apply(br.readLine());
        S = 1 << ((int) Math.ceil(log(N) / log(2)));
        tree = new Tree[S * 2];

        for (int i = 1; i <= N; i++) {
            tree[S + i - 1] = new Tree(i, fun.apply(br.readLine()));
        }

        for (int i = N + 1; i <= S; i++) {
            tree[S + i - 1] = new Tree(MAX, MAX);
        }

        init();

        recursion(1, N);
        System.out.println(ans);
    }
}
