import java.util.*;
import java.io.*;
import java.util.function.Function;

// 15591 : MooTube (Silver)

/**
 * -- 전제조건
 * 문제의 조건은 이러하다.
 * 각 유튜브 영상을 추천해주는데 그것은 연관된 동영상을 추천해주는 것이다.
 *
 * 연관되었는지 어떻게 판단하냐면, 그것은 바로 유사도로 측정을 한다.
 * 유사도는 각 유튜브 영상끼리의 쌍으로 이루어져 있고, 어떠한 영상과 영상간의 유사도는 두 정점간의 사이의 경로에서
 * 가장 작은 유사도를 가져오면 된다.
 *
 * 그래서 이렇게 하였을 때, 비디오가 지정이 되고, K 가 주어졌을 때, K 이상의 유사도를 가지는 비디오들이 몇개가 있는지 반환하면 된다.
 *
 * -- 틀 설계
 * 이것은 그냥 여기서 문제 전제조건에 모든 정점은 모든 정점과 연결되어 있다
 * 즉, 그 말은 한 정점에서 출발하면 모든 정점을 다 들릴 수 있는 것이고, 또 그것이 의미하는 것은
 *
 * 근데 약간 애매하다 말이 너무
 * 일단 예제로 주어진 것은 이러하다.
 * 1 <-> 2 (3)
 * 2 <-> 3 (2)
 * 2 <-> 4 (4) 이러한 유사도를 가진다.
 *
 * 그러면 내 생각이 맞다면 1, 2, 3, 4 모두 유사도를 2를 가져야 한다.
 * 왜냐하면? 다 연결되어 있으니까
 *
 * 그래서 내가 생각한 것과는 조금 다른 것 같다.
 *
 * 일단 힌트로서 주어진 말은
 * 위의 경우에서 1 번과 3번은 유사도 2를 가진다.
 * 그리고 1번과 4번은 유사도 3을 가진다라고 한다.
 * 그리고 3번과 4번은 유사도 2를 가진다라고 한다.
 *
 * 문제가 살짝 이해가 안간다 오랜만에 풀어서 그런가?
 * 겨우 골드 5인데 이상하다.
 *
 * 이거는 문제에 답이 있는 듯하다.
 *
 * N - 1 개의 간선이 존재한다.
 * 근데, 모든 정점은 다른 정점으로 갈 수 있는 경로가 하나는 존재한다 ??
 * 이거는 사이클이 없다라는 것이다.
 *
 * 그 말은 즉, N - 1 개의 간선이 존재하고, 사이클이 없는 그래프, 바로 트리이다.
 *
 * 트리라고 생각하고 문제를 풀게 되면 엄청나게 쉽다.
 * 단지 시간내에 해결이 되는지가 의문이긴 하지만 도전해볼만은 하다.
 *
 * 그러면 어떤식으로 해야 할까
 * 내 식대로 진행하게 되면 총 5000만번의 연산을 해야 한다.
 * 5000만번은 1초안에 들어오는가
 *
 * 그러면 일단 모든 정점을 루트 정점으로 잡고
 * 순회를 진행하면서, 해당 경로까지의 최솟 값을 계쏙 가져가면서 도착하는 족족 집어넣는다
 * 그러면 2차원 배열로서 관리하면 모든 정점의 정보를 담을 수 있다.
 *
 * 그 다음에 쿼리가 들어오게 되면 그냥 세면 된다.
 * 그러면 끝날 것 같다.
 *
 * visited 배열을 만들 필요는 없다 N - 1 개의 간선에, 모든 정점으로 가는 경로가 있다라고 한다면
 * 중복으로 방문은 불가능하다, 중복으로 방문이 가능하다라는 것은 애초에 사이클이 존재한다라는 것이니까
 *
 * -- 결과
 * 생각보다 뒤지게 오래걸리네
 * 근데 맞긴 했는데 모두 1초 초과이긴하지만 그래도 찝찝하다
 * 기분나쁘네..
 */
public class Main {
    static int N; // 전체 수
    static int Q; // 쿼리의 개수
    static int[][] cost;
    static List<ArrayList<int[]>> graph = new ArrayList<>();

    static void dfs(int start, int cur, int parent, int minCost) {
        /**
         * graph 를 탐색하면서 cost 에다가 적절하게 값을 집어넣자.
         *
         * 일단 cur 은 현재 정점이고
         * parent 는 본인이 바로 이전에 거쳐온 정점이다.
         * 트리의 특성상 이전에 거쳐온 곳만 가지 않으면 된다.
         * 그렇기 때문에, visited 배열을 두기 보다는 parent 만 가지 않으면 효율적으로 이동할 수 있다.
         *
         * 그리고 계속 Cost 를 최소로 갱신한다.
         * 그리고, 현재 방문한 곳에다가, 넣어준다.
         * 본인이 본인은 0이고 짜피 검사할 때 넘어갈 것이기 때문에 처음에 Integer.MAX_VALUE 로 넘기자.
         *
         * 그리고 start 는 시작 정점이다 그것과 연관해서 값을 넣어야 하기 떄문에 이렇게 진행한다.
         */
        cost[start][cur] = minCost;

        for (int[] next : graph.get(cur)) {
            if (next[0] != parent) { // parent 가 아닐 때만 진행한다.
                dfs(start, next[0], cur, Math.min(minCost, next[1])); // 현재의 minCost 와 현재 가려는 경로의 값중 작은 값을 취한다, 경로의 비용이 아니기에 간선에 대해서만 연산을 진행한다.
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        Q = fun.apply(input[1]);
        cost = new int[N + 1][N + 1];

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < N - 1; i++) {
            input = br.readLine().split(" ");
            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);
            int c = fun.apply(input[2]);

            graph.get(a).add(new int[] {b, c});
            graph.get(b).add(new int[] {a, c});
        }

        for (int i = 1; i <= N; i++) {
            dfs(i, i, -1, Integer.MAX_VALUE);
        }

        for (int i = 0; i < Q; i++) { // 쿼리를 받는다.
            input = br.readLine().split(" ");
            int k = fun.apply(input[0]);
            int v = fun.apply(input[1]);
            int add = 0;

            for (int j = 1; j <= N; j++) {
                if (j != v) { // 쿼리로 넘어온 영상이 아닐 때, 즉 현재 보고 있는 영상은 넘어간다.
                    if (k <= cost[v][j]) {
                        add++;
                    }
                }
            }

            sb.append(add).append("\n");
        }

        System.out.print(sb);
    }
}
