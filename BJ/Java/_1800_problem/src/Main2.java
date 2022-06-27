import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1800 : 인터넷 설치

/**
 * -- 틀 설계
 * 답을 봤는데 이거는 답을 봐도 잘 모르겠을 정도로 전혀 생각 못한 방법이다.
 * 이런식의 이분탐색으로 문제를 해결할 수 있는지 몰랐다.
 *
 * 즉, 여기서 사용한 방법은 이러하다.
 * K 개까지는 케이블을 무료로 제공한다.
 * 그렇기 때문에, 최대한 K 개는 최대한 비싼 것을 골라야 한다.
 *
 * 그래서 해당 방법은 이런식으로 구했다.
 * mid 값을 dijkstra 방법을 이용하여서,
 * 목적지까지 도달하려고 할 때, K 를 넘지 않는 것들을 최대한 골라서 갈 수 있냐라는 것이다.
 * 그렇기 때문에 여기서 발생하는 조건은 dist[vertex] 는 최대한 작은 것을 골라야 한다, 즉 현재 지나온 x 이상의 정점 개수보다 크면 진행한다라는 것이다.
 *
 * 그래서 이런식으로 이분탐색을 통해서 x 이상의 정점들을 K 개 내에서 걸러낼 수 있냐
 * 그것을 통해서, x를 최적의 값을 찾을 수 있는 것이다.
 *
 * 왜냐하면 K 개를 제거한 나머지 중에서 가장 큰 값이기 때문이다.
 * 만일 예를 들어서 낼 수 있는 최소 비용이 2라고 가정하자.
 * 그러면 2 이상의 것들은 K 개 안으로 선택하면서 N 에 도달 가능하다라는 것이다.
 *
 * 그렇기 떄문에, x 가 4일 때에도 K 개 안
 * 3 일 때에도 K 개 안
 * 2 일 때에도 K 개 안이고
 * 1 일 때에는 K 개 안에 들어오지 않을 것이다 (x 이상의 정점들을 거치지 않고서 N 으로 도달하는 경우)
 *
 * 그렇기 때문에 dijkstra 의 결과값이 false 인 경우에는 답으로 채택하면 안되는 것이다.
 *
 * 그러니까 이 문제는 x 값을 계속 주어서
 * x 값을 초과하는 값들은 K 개 이하로만 선택하면서 (즉, K 개를 선택하게 되면 x 가 내야하는 비용임)
 * 갈 수 있는지 확인하면서, 만약 가능하다면 x 값을 낮춰서 계속 가능한지 체크한다.
 * 그러면서 최종적으로 가능한 것을 캐치해낼 수 있도록
 * x 값을 낮추는 과정인
 *
 * if (dijkstra(mid)) {
 *     ans = mid;
 *     right = mid - 1;
 * }
 *
 * 에서 ans 를 조정할 수 있는 코드를 넣어놓는다.
 *
 * 그리고 정말 결정적인 것은 다익스트라 알고리즘을 최소비용이 아닌, x 초과인 간선을 최소로 선택하기 위해 사용했다는 점이다.
 *
 * 그래서
 * 1. mid 값을 조정하면서, 최소 mid 값은 혹은 그 이하를 비용으로 낼 수 잇도록 조정하고 (이런 식으로 보장이 되면 x 값을 낮춰서 최적의 값을 찾아나감)
 * 2. 다익스트라 알고리즘을 최소비용이 아닌, x 초과인 간선을 최소로 선택하기 위해 사용한다.
 */
public class Main2 {
    static int N;
    static int P;
    static int K;
    static int ans = -1;
    static List<ArrayList<int[]>> graph = new ArrayList<>();

    static boolean dijkstra(int mid) {
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);

        int[] dist = new int[N + 1];
        for (int i = 1; i < N + 1; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        queue.add(new int[] {1, 0});
        dist[1] = 0;

        while (!queue.isEmpty()) {
            int[] a = queue.poll();

            if (dist[a[0]] < a[1]) {
                continue;
            }

            if (a[0] == N) {
                break;
            }

            for (int[] next : graph.get(a[0])) {
                // next 의 cost 가 mid 보다 큰 경우 nextCost + 1 을 해주고 현재 dist[next[0]] 이 값이 더 큰 가봐서 아니면 진행한다.
                int nextCost = a[1];

                if (mid < next[1]) {
                    nextCost++;
                }

                if (nextCost < dist[next[0]]) { // 현재 cost 보다 dist[next[0]] 이 더 커야지 여기로 가는게 의미가 있음
                    queue.add(new int[] {next[0], nextCost});
                    dist[next[0]] = nextCost;
                }
            }
        }

        return dist[N] <= K; // 내가 지금 검사한 x 값이 원장선생님이 낼 수 있는 최종 비용이 될 수 있는지 검사하는 것임
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        P = fun.apply(input[1]);
        K = fun.apply(input[2]);
        int max = 0;

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < P; i++) {
            input = br.readLine().split(" ");
            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);
            int c = fun.apply(input[2]);

            graph.get(a).add(new int[] {b, c});
            graph.get(b).add(new int[] {a, c});
            max = Math.max(max, c);
        }

        int left = 0;
        int right = max;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (dijkstra(mid)) {
                ans = mid;
                right = mid - 1; // 성공했으니 mid 의 값을 낮춘다 (그래야지 탐색하는 의미가 있음)
            } else {
                left = mid + 1;
            }
        }

        System.out.println(ans);
    }
}
