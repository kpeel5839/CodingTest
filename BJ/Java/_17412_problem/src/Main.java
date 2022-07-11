import java.util.*;
import java.io.*;
import java.util.function.Function;

// 17412 : 도시 왕복하기 1

/**
 * -- 전제조건
 * 1 -> 2 번으로 가는 경로를 최대한 많이 찾아라
 * 근데, 경로 중, 같은 경로를 포함하면 안된다.
 * 그래서 최대 유량 알고리즘을 사용 가능한 거구나 capacity 를 1로 하면 되니까
 *
 * -- 틀 설계
 * 최대 유량 알고리즘을 공부하긴 했지만
 * 나머지는 다 이해간다.
 *
 * 하지만, 역으로 계산했을 때, 그걸로 인해서 어떻게 모든 경로를 찾을 수 있는지는 잘 모르겠다.
 *
 * 이 문제가 최대 유량을 사용하는 이유는
 * 1 -> 2 번으로 가는 경로에
 * 동일한 경로를 포함하지 않는 경로를 여러개 찾아야 하기 떄문이였음
 *
 * 그렇기 때문에, 이미 찾은 경로보다 더 좋은 경로를 찾게 되면 다른 경로를 찾기 위해서
 * 최대 유량을 1로 잡아놓고, 1 -> 2 로 하는 유량을 최대로 할 수 있도록 찾는 것임
 *
 * 즉, 1 -> 2 번 즉, 목적지로 향하게 하는 최대 유량을 찾는다.
 * 그것과는 다를 바가 없음
 *
 * 그냥, 각 capacity 를 1 로만 잡으면 유량을 그냥 해당 간선을 택하는 것으로 취급할 수 있기 때문이다.
 *
 * 근데 확실히 내가 이 유량 알고리즘에 대한 이해도가 전혀 없기 때문에
 * 분명하게 공부를 해야하는 것 같다.
 *
 * 수학적으로 풀어낼 수 있어야 할 것 같다.
 */
public class Main {
    static int N;
    static int P;
    static int ans = 0;
    static List<ArrayList<Integer>> graph = new ArrayList<>();
    static int[][] capacity;
    static int[][] flow;

    static void bfs() {
        int[] parent = new int[N + 1];

        while (true) {
            Queue<Integer> queue = new LinkedList<>();
            Arrays.fill(parent, -1);
            queue.add(1);

            while (!queue.isEmpty()) {
                int a = queue.poll();

                for (Integer next : graph.get(a)) {
                    if (capacity[a][next] - flow[a][next] > 0 && parent[next] == -1) {
                        queue.add(next);
                        parent[next] = a;

                        if (next == 2) {
                            break;
                        }
                    }
                }
            }

            if (parent[2] == -1) { // 도달하지 못한 경우 (더 이상 갈 경로가 없다.)
                break;
            }

            for (int i = 2; i != 1; i = parent[i]) {
                flow[parent[i]][i] += 1;
                flow[i][parent[i]] -= 1;
            }

            ans++; // 경로를 찾아서 flow 까지 조정하였음 (유량)
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        P = fun.apply(input[1]);

        capacity = new int[N + 1][N + 1];
        flow = new int[N + 1][N + 1];

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < P; i++) {
            input = br.readLine().split(" ");
            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);

            graph.get(a).add(b);
            graph.get(b).add(a);
            capacity[a][b] = 1; // 용량 추가
        }

        bfs();
        System.out.println(ans);
    }
}
