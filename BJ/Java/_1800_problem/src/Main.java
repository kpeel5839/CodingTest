import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1800 : 인터넷 설치

/**
 * -- 전제조건
 * 1 번 컴퓨터와 N 번 컴퓨터를 연결하면 된다.
 * 근데, K 개 까지만 공제를 해주고 거기서 가장 비싼 값을 원장선생님한테 청구한다.
 *
 * 그럴 때, N 번 컴퓨터를 이을 경우, 낼 수 있는 가장 최소 비용을 구하시오.
 *
 * -- 틀 설계
 * 이 문제는 그냥 dfs 로 1 번 컴퓨터와 N 번 컴퓨터가 이어질 수 있도록 진행한다음에
 * 거기서 이어지게 된다라면 list 에다가 담아서 sort 하고, 정렬한 뒤 length - K - 1 이것을 ans 와 비교해서 최소인지 확인하면 될 것 같다.
 *
 * 만약에 이게 맞다면 어떻게 이게 골드 1 이지?
 */
public class Main {
    static int N;
    static int P;
    static int K;
    static int ans = Integer.MAX_VALUE;
    static boolean[] visited;
    static Stack<Integer> cost = new Stack<>(); // cost 를 순서대로 담아줄 queue
    static List<ArrayList<int[]>> graph = new ArrayList<>();

    static void dfs(int a) {
        if (a == N) {
            cal();
            return;
        }

        for (int[] next : graph.get(a)) {
            if (!visited[next[0]]) { // 방문하지 않은 정점일 경우
                visited[next[0]] = true;
                cost.push(next[1]);
                dfs(next[0]);
                visited[next[0]] = false;
                cost.pop();
            }
        }
    }

    static void cal() {
        Stack<Integer> stack = (Stack) cost.clone();

        Collections.sort(stack);
        ans = Math.min(ans, stack.get(cost.size() - K - 1)); // 이런식으로 진행해주면 된다.
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        P = fun.apply(input[1]);
        K = fun.apply(input[2]);
        visited = new boolean[N + 1];

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
        }

        dfs(1);
        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }
}
