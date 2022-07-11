import java.util.*;
import java.io.*;
import java.util.function.Function;

// 11376 : 열혈강호 2

/**
 * -- 전제조건
 * 이전에 열혈 강호 1에서
 * 그냥 한 직원이 일을 2개 할 수 있다라는 것만 변경되었다.
 *
 * -- 틀 설계
 * 그냥 이전에 했던 이분매칭을 활용하면 될 것 같다.
 * 달라진 것은 2개를 선택한다라는 것이니까 그리 어렵진 않지 않을까?
 *
 * -- 해맸던 점
 * 처음에는 내가 임의로 코드를 바꾸어
 * 실행하려고 했음
 * 근데, 시뮬레이션을 돌려본 결과
 * 그냥 두번 실행하면 처음에 최대로 선택할 수 있는 것을 선택하고 그 상태에서
 * 또 돌리게 되면 거기서 또 최선으로 선택하려해서 결국 2개를 최적으로 선택하는 결과를 얻어낼 수 있었음
 *
 * 즉, 그냥 원래 코드에서 두번 실행한 것 밖에 없음
 */
public class Main {
    static int N;
    static int M;
    static List<ArrayList<Integer>> graph = new ArrayList<>();
    static int ans = 0;
    static int[] occupy;
    static boolean[] visited;
    static boolean matching(int a) {
        if (visited[a]) {
            return false;
        }

        visited[a] = true;

        for (Integer next : graph.get(a)) {
            if (occupy[next] == 0 || matching(occupy[next])) {
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
        M = fun.apply(input[1]);
        occupy = new int[M + 1];

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 1; i <= N; i++) {
            input = br.readLine().split(" ");

            for (int j = 1; j <= fun.apply(input[0]); j++) {
                graph.get(i).add(fun.apply(input[j]));
            }
        }

        for (int c = 0; c < 2; c++) {
            for (int i = 1; i <= N; i++) {
                visited = new boolean[N + 1];
                if (matching(i)) {
                    ans++;
                }
            }
        }

        System.out.println(ans);
    }
}