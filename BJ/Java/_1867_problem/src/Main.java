import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1867 : 돌맹이 제거

/**
 * -- 전제조건
 * n 행 n 열인 운동장이 주어졌을 때,
 * 행 혹은 열로 쭉 달려서 k 개의 돌을 다 줍는 최소 횟수를 구하시오.
 *
 * -- 틀 설계
 * 이건 진짜 도저히 봐도 해결방안이 떠오르지 않아서 답을 봤는데도 불구하고 완벽히 이해가 불가능한 수준이다
 * 최대한 이해해보려고 노력한 결과, 어느정도 이해는 된 것 같다.
 *
 * 일단 이 문제의 가정 자체는 돌이 놓여있는 행, 열을 서로 간선을 잇는 것이다.
 *
 * 그렇다면? 간선의 양 끝점 중 하나의 정점이라도 선택하게 되면 해당 돌을 줍는 것이 되는 것이니까
 *
 * 그리고 그냥 최대유량을 구하면 답을 구할 수 있다.
 *
 * 근데 무엇인가 납득이 가지 않는다.
 *
 * 모든 간선의 양 끝점을 보았을 때, 적어도 하나의 정점은 선택되어야 한다라는 것은 이해가 가지만 이것을 왜? 최대 유량으로 구하는 것일까?
 *
 * 한참 생각해본 결과, 모든 돌을 다 줍기 위해서인 것 같다.
 *
 * 계속해서 여러가지의 예제를 생각해보고 실제로 이분 매칭을 진행해보니
 *
 * 얻어냈다라고 생각되는 결과가 있다.
 * 최대 유량은 모든 정점이 하나의 간선을 선택하려고 했을 때,
 * 최대한 많은 간선을 선택하는 경우이다.
 *
 * 즉, 매칭에 성공한 정점 중 선택되지 않은 건너편의 정점 중 본인의 간선이 남아있는 것이 있다면
 * 열을 선택한 경우이고, 그렇지 않다면 행을 선택한 경우이다.
 *
 * 하지만, 간선이 하나인 경우에는 열을 선택했을 수도 행을 선택했을 수도 있다.
 *
 * 쨌든 이런식으로 모든 돌을 주울 수 있도록 최대유량 알고리즘을 적용하고
 * 행을 선택할 수 없는 상황이면 선택하지 않아 열에 책임을 분배하는 모습을 볼 수 있었다.
 *
 * 이것을 보니까 내가 이해를 못한 이유가 있었다. 쾨니그의 정리에서 이러한 이론이 있다.
 * 쾨니그의 정리 (Kőnig's theorem)
 * "모든 이분 그래프에서 최대 매칭의 수는 최소 버텍스 커버 수와 같다." (https://en.wikipedia.org/wiki/K%C5%91nig%27s_theorem_(graph_theory))
 * 즉, 이 문제를 푼 당사자도 쾨니그의 정리를 보고 이렇게 풀 수 있겠구나 한 것이였음
 * 쾨니그의 정리를 이해해야 함
 *
 * https://www.notion.so/1867-fe1a8b27c70e41ec9fcc1b10508853df
 * 이해한 내용을 토대로 작성했다.
 */
public class Main {
    static int N;
    static int K;
    static int ans = 0;
    static boolean[] visited;
    static int[] occupy;
    static List<ArrayList<Integer>> graph = new ArrayList<>();

    static boolean dfs(int a) {
        if (visited[a]) { // 이미 왔다갔는 데 실패한 경우 혹은 진행하고 있는 경우임
            return false;
        }

        visited[a] = true;

        for (Integer next : graph.get(a)) {
            if (occupy[next] == 0 || dfs(occupy[next])) {
                occupy[next] = a;
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        K = fun.apply(input[1]);
        occupy = new int[N + 1];

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < K; i++) {
            input = br.readLine().split(" ");
            graph.get(fun.apply(input[0])).add(fun.apply(input[1]));
        }

        for (int i = 1; i <= N; i++) {
            visited = new boolean[N + 1];

            if (dfs(i)) {
                ans++;
            }
        }

        System.out.println(ans);
    }
}
