import java.util.*;
import java.io.*;
import java.util.function.Function;

// 16562 : 친구비

/**
 * -- 전제 조건
 * 문제 전제조건이 슬프다.
 * 어떠한 친구를 사귈때에, 그 친구가 되려면 돈을 내야하고,
 * 그 돈을 지불하면 친구가 되는데,
 *
 * 친구의 친구도 본인의 친구라고 한다.
 * 개찐따 준석이가
 * 모두를 친구로 사귈 수 있도록 해보자.
 *
 * -- 틀 설계
 * 분리집합으로 (a, b) 로 주어지는 쌍을 기준으로
 * 집합을 만들고, 그 집합에 가장 cost 가 낮게, 계속 설정한다.
 *
 * 그 다음에 마지막으로, 본인이 집합의 부모인 애들의 cost들만 다 더하면 된다.
 */
public class Main {
    public static int N;
    public static int M;
    public static int K; // 주어진 돈
    public static int[] parent;
    public static int[] cost;
    public static int res = 0;

    public static int find(int a) {
        if (parent[a] == a) {
            return a;
        } else {
            return parent[a] = find(parent[a]);
        }
    }

    public static void union(int a, int b) {
        /**
         * union 에서는, 두 집합의 cost 를 비교해서,
         * 더 낮은 cost 로 parent[b] = a; 하면 된다.
         * 즉, a 가 최종 부모이기에, a 의 cost 값을 갱신해주어야 한다.
         */
        int minCost = Math.min(cost[a], cost[b]);
        cost[a] = minCost;
        parent[b] = a;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        M = fun.apply(input[1]);
        K = fun.apply(input[2]);

        parent = new int[N + 1];
        cost = new int[N + 1];

        input = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
            cost[i] = fun.apply(input[i - 1]);
        }

        for (int i = 0; i < M; i++) {
            input = br.readLine().split(" ");

            int a = find(fun.apply(input[0]));
            int b = find(fun.apply(input[1]));

            if (a == b) { // 이미 서로 같은 친구 망일때
                continue;
            } else { // 그렇지 않을 떄는 union 을 진행해주면 된다.
                union(a, b);
            }
        }

        for (int i = 1; i <= N; i++) {
            if (parent[i] == i) { // 본인이 부모인 애들만 다 더해줌, 그게 집합이니까
                res += cost[i];
            }
        }

        if (res > K) {
            System.out.println("Oh no");
        } else {
            System.out.println(res);
        }
    }
}
