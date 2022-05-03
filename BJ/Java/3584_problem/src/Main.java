import java.util.*;
import java.io.*;
import java.util.function.Function;

// 3584 : 가장 가까운 공통 조상

/**
 * -- 전제 조건
 * 그래프가 주어지고(루트가 있는)
 * 마지막에 두 정점이 주어졌을 때, 두 노드의 공통조상을 알아내면 된다.
 * -- 틀 설계
 * 일단 첫번째, 루트 노드를 알아야 한다.
 * 루트 노드를 알기 위해서는, 부모 노드가 없는 노드를 찾아야 한다.
 * depth 를 파악하기 위해서는, 부모 -> 자식으로 graph 를 형성해야 한다.
 * 그리고, 이제 이것을 이용해서, root 노드로부터 dfs 를 시작한다음에, depth, 부모 노드를 기록해야 한다.
 *
 * 입력 받으면서도 그냥 할 수 있지만, 굳이 그럴 필요가 없다.
 * 왜냐하면, depth 를 기록할 때, dfs를 진행해야 하기 떄문이다.
 * 근데, 부모 노드를 기록하면, 루트노드를 알 수 있으니까, 그냥 입력 받으면서 부모 노드 기록해야할 듯
 *
 * 그래서, 서순은 입력 받으면서 부모를 기록하고,
 * 루트노드에서 시작해서, depth 를 기록한다.
 *
 * 그리고서, 두 노드를 가지고, depth 를 더 낮은 쪽에서 시작한다음에
 * 높이를 맞추고, 같은지 확인한다음에, 계속 겹칠때까지 진행한다.
 */
public class Main {
    public static int T;
    public static int N;
    public static List<ArrayList<Integer>> graph = new ArrayList<>();
    public static int[] parent;
    public static int[] depth;

    public static void dfs(int node, int dep) {
        /**
         * dfs로 돌면서, depth 를 기록한다.
         * 그냥 그러면 된다.
         */
        depth[node] = dep;

        for (Integer next : graph.get(node)) {
            dfs(next, dep + 1);
        }
    }

    public static int findParent(int a, int b) {
        /**
         * depth 가 더 높은 애로 시작해서
         * 본인 부모노드로 계속 진행한다음에, depth 같게 맞춰놓은 다음에
         * 현재 지금 노드 같은지 확인하고
         * 아니면, 같아질 때까지 진행한다 (LCA 그냥 느린 버전)
         */
        if (depth[a] < depth[b]) { // a 가 depth 가 더 높게 유지하자, 즉 a 를 올리는 겨
            int tmp = a;
            a = b;
            b = tmp;
        }

        while (true) {
            if (depth[a] == depth[b]) {
                break;
            }
            a = parent[a]; // 본인의 부모 노드로 올라간다.
        }

        if (a == b) {
            return a; // 부모노드 반환
        }

        while (true) {
            if (a == b) {
                break;
            }
            a = parent[a];
            b = parent[b];
        }

        return a;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        Function<String, Integer> fun = Integer::parseInt;

        T = fun.apply(br.readLine());

        while (T-- > 0) {
            N = fun.apply(br.readLine());
            parent = new int[N + 1];
            depth = new int[N + 1];
            graph = new ArrayList<>();

            for (int i = 0; i <= N; i++) {
                graph.add(new ArrayList<>());
            }

            for (int i = 0; i < N - 1; i++) {
                String[] input = br.readLine().split(" ");

                int a = fun.apply(input[0]); // 부모 노드
                int b = fun.apply(input[1]); // 자식 노드

                parent[b] = a;
                graph.get(a).add(b); // 간선 추가
            }

            int root = 0;

            for (int i = 1; i <= N; i++) { // 루트노드 찾기
                if (parent[i] == 0) { // 부모가 없으면 값이 0이기 때문에, 0을 찾으면 루트노드임
                    root = i;
                    break;
                }
            }

            dfs(root, 0);
            String[] input = br.readLine().split(" ");
            bw.write(findParent(fun.apply(input[0]), fun.apply(input[1])) + "\n");
        }

        bw.flush();
        bw.close();
    }
}
