import java.util.*;
import java.io.*;
import java.util.function.Function;

// 11505 : 구간 곱 구하기

/**
 * -- 전제 조건
 * 그냥 N 개의 수가 주어지고,
 * 중간에 수의 변경이 빈번히 일어날 떄,
 * 어떤 부분의 곱을 구하려고 한다.
 *
 * 예를 들어서 3번째 수를 6 으로 바꾸고 2 ~ 5 번째의 값을 구하라고 한다면?
 * 그러면, 빠르게 구하기는 힘들것이다, 그것이 엄청나게 크다면
 *
 * 그럴 때, 쿼리가 주어지면, 그 값을 구하면 되는데,
 * N 개의 수가 주어지고, a, b, c 가 주어지고, a 가 1인 경우 b 번째 수를 c 로바꾸고
 * a 가 2인 경우에는 b, c 까지의 곱을 출력하면 된다.
 * -- 틀 설계
 * 당연히 이 문제는 세그먼트 트리를 구하는 문제이다.
 *
 * 일단, N 이 2 ^ n 일 때에는, 배열의 크기가 (2 * N) - 1 개가 필요 하다.
 *
 * 예를 들어서 N 이 4 개 일때에는
 * 1 ~ 4, 1 ~ 2, 3 ~ 4 가 있다.
 *
 * 그게 아닐때에는 N 이 3 일 때에도, 배열의 크기는 7개가 대략 필요하다.
 * 그렇다라는 것은, 그냥 본인보다 큰 2 ^ n 개 따라가면 된다.
 *
 * 그래서, 그냥 여기서도 size 는 귀찮으니까, 1 << 24 * 2 로 가자.
 *
 * -- 해맸던 점
 * 일단, 처음에는 사이즈를 과도하게 해서, 메모리 초과가 났었다.
 * 그 다음에는, 리프노드를 tree 에는 따로 저장하지 않아서 결과가 다르게 나왔었고
 * 그 다음에는, update 과정에서 논리 오류가 있었는데, 그 오류가 발생했던 이유는 이전에 세그먼트 트리를 구현했을 때의 기억으로 인해서 그랬었다.
 *
 * 이전에는 diff 를 따로 구해서, 모든 노드에다가 diff 를 처리해주면서 진행했다.
 * 하지만, 이 곱 같은 경우는 0이 있기 때문에 그렇게 하면 안된다.
 *
 * 그래서, 범위를 벗어나게 되면, 그냥 값만 반환하고, 그 값과 현재 노드 값을 곱할 수 있도록 진행하였음
 * 그래서, update를 그런식으로 수정하니까 맞았음
 */
public class Main {
    public static int SIZE;
    public static long[] tree;
    public static long[] value;
    public static final long MOD = 1_000_000_007;
    public static int N;
    public static int M;
    public static int K;

    public static long init(int now, int start, int end) {
        /**
         * 처음에 트리를 생성
         *
         * 트리를 생성하기 위해서는, 일단 원초적인 값들로부터 불러올 수 있어야 함
         * 그렇다라는 것은 start == end 인 상황에서부터 역순으로 끌어올릴 수 있어야한다.
         *
         * if (start == end) 로 종료조건을 지정한다음에 거시서부터 return value[start] 로 한다.
         *
         * 그 다음에가 중요하다.
         * 받아온 값들을 본인의 노드로 저장을 할 수 있어야 한다.
         * 그렇다라는 것은 start, end 를 지정하면서도
         * index 값을 알아야 한다라는 것이다.
         * 왼쪽으로 갈 때에는, index * 2, 오른쪽으로 갈때에는 index * 2 + 1 그리고, 루트노드를 1로 지정해야지 훨 편하다.
         * start, end 는 그렇게 지정하지만, 루트노드는 1로 지정하도록 해보자.
         *
         * 이거 어떻게 해야할까
         * start, end / 2 로 하면 반으로 나눠지고
         * (1, 2), (3, 4) 이렇게 나누어진다음에
         * 다 (1, 1), (2, 2), (3, 3), (4, 4) 이렇게 나누어져야 한다.
         *
         */
        if (start == end) {
            tree[now] = value[start];
            return tree[now];
        }

        tree[now] = (init(now * 2, start, (start + end) / 2)
                * init((now * 2) + 1, ((start + end) / 2) + 1, end)) % MOD;
        return tree[now];
    }

    public static long update(int now, int index, int start, int end, long c) {
        /**
         * 구간 곱을 업데이트
         *
         * update 는 비교적으로 쉬울 것 같다.
         * b를 나눠주고, c를 곱해주면 된다.
         * index 를 포함하고 있는 범위에
         *
         * update 도, 곱하기는 다르게 가야할 것 같다.
         */
        if (start > index || end < index) { // 범위를 벗어난 것은, 그저 곱하기 용으로 사용하면 된다.
            return tree[now]; // 끝내준다.
        }

        if (start == end) { // 범위를 벗어 나지 않으면서, start == end 한 애는, 바로 바꿔준다음에 다시 반환
            tree[now] = c;
            return tree[now];
        }

        // 이렇게 그냥 sum 처럼 diff 를 해주는 것이 아니라 그냥, 다시 새로 init 해준다라는 느낌으로
        return tree[now] = (update(now * 2, index, start, (start + end) / 2, c)
                * update((now * 2) + 1, index, ((start + end) / 2) + 1, end, c)) % MOD;
    }

    public static long mul(int now, int left, int right, int start, int end) {
        /**
         * 구간 곱을 구해줌
         *
         * 원래 구하려는 범위는 left, right
         * 그리고, 실질적으로 범위는 start, end 이다.
         *
         * 일단, 구간 곱을 구할 때, 필요없는 경우는?
         * 전혀 겹치지 않는 경우는 필요가 없다.
         * 즉, left, right 가 start, end 보다 큰 경우는 ? 포함하고 있는 경우니까, 그냥 return 해주면 되고
         * 그리고, left 보다 end 가 작으면, 혹은 right 보다 start 가 크다면 그냥 끝내면 된다.
         *
         * 근데 여기서는 return 값을 계속 곱해줘야 한다라는 것과
         * MOD 로 나누어줘야 하는 것이라는 것이 조금 차이점이다.
         */
        if (end < left || right < start) {
            return 1;
        }

        if (left <= start && end <= right) {
            return tree[now];
        }

        // 겹치는 구간이 있는 경우
        return (mul(now * 2, left, right, start, (start + end) / 2)
                * mul((now * 2) + 1, left, right, ((start + end) / 2) + 1, end)) % MOD;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        M = fun.apply(input[1]);
        K = fun.apply(input[2]);

        int height = (int) Math.ceil(Math.log(N) / Math.log(2));
        SIZE = (1 << (height + 1));
        value = new long[N + 1];
        tree = new long[SIZE + 1];

        for (int i = 1; i <= N; i++) {
            value[i] = fun.apply(br.readLine());
        }

        init(1, 1, N);

        for (int i = 0; i < M + K; i++) {
            input = br.readLine().split(" ");

            long a = fun.apply(input[0]);
            long b = fun.apply(input[1]);
            long c = fun.apply(input[2]);

            if (a == 1) { // 수를 바꾸는 경우
                /**
                 * 수를 바꾸는 경우는..
                 * 일단, 곱하는 수가 하나 바뀌는 것이니까
                 * 그 수로 나눠준다음에
                 * 새로운 수로 다시 곱해주어야 할 듯
                 */
                value[(int) b] = c;
                update(1, (int) b, 1, N, c);
            } else { // 수를 찾는 경우
                sb.append(mul(1, (int) b, (int) c, 1, N)).append("\n");
            }
        }

        System.out.print(sb);
    }

    public static void treePrint() {
        int index = 0;

        for (int i = 0; i < tree.length; i++) {
            if (i == (1 << index)) {
                System.out.println();
                index++;
            }

            System.out.print(tree[i] + " ");
        }
    }
}
