import java.util.*;
import java.io.*;
import java.util.function.Function;

// 11280 : 2-SAT - 3

/**
 * -- 전제 조건
 * 논리식이 주어지고, 해당 논리식을 참으로 만들 수 있는지 판단하여서, 결과를 반환한다.
 * N 개의 정수가 주어지고,
 *
 * 각 N 은 음수이면, not 양수이면 그냥이고
 * 두 수씩 주어지는데, 두 수는 같은 집합으로 이루어져 있고, 논리 합으로 이루어져 있음
 *
 * 그리고, 두 수들의 집합들은 논리 곱으로 이루어져 있음
 * -- 틀 설계
 * 명제를 이용해서 푸는 문제이다.
 * 일단, (x v y) 는 ㄱx -> y 로 바꾸거나 ㄱy -> x 로 바꿀 수 있다.
 *
 * 이것을 통해서, 그래프를 형성할 수 있음
 * 왜냐하면 함의가 가진 뜻은 x -> y 라고 하면 x 는 y 이다. 라는 뜻임
 *
 * 그렇기 때문에 x -> y 가 참이기 위해서는 x, y 가 true, true 이거나
 * 혹은 (false, false), (false, true) 여야지 가능하다
 *
 * 일단, 전제가 false 이면, 무조건 해당 명제는 참이 될 수 밖에 없다.
 * 그래서, 고려해야 하는 것은 true -> false 이다.
 * 하지만, 만일 사이클이 형성되어 있는 경우에는?
 *
 * 서로 모든 정점에 도달할 수 있다. 그렇다라는 것은
 * 만일 ㄱx, x 가 같은 SCC 집합에 속한다라면?
 *
 * 그것은 무조건 false 가 될 수 밖에 없다.
 * 즉, 주어진 논리식으로 그래프를 구성하였더니, 같은 SCC 에 같은 정점이 부정, 참이 섞여있으면 그 식은 true 로 만들 수가 없는 것이다.
 *
 * 왜냐하면 여기서 주어진 문제 자체가 논리합으로 이루어진 절들을 논리곱으로 연결하는 식이기 때문에,
 * 절이 하나라도 false 이면, 안된다. 근데, 만일 위와 같은 상황이 벌어진다라면?
 *
 * 현재 절들의 함의식으로 그래프를 구성하였기 때문에, 이것의 결과는 그 절의 결과가 되는 것이다.
 * 그러면 false 가 포함이 되는 것이니까, 결과는 부정이 될 수밖에 없는 것이다.
 */
public class Main {
    static int N;
    static int M;
    static int res = 1;
    static int idValue = 0;
    static int sccValue = 0;
    static int[] scc;
    static int[] id;
    static boolean[] already;
    static List<ArrayList<Integer>> graph = new ArrayList<>();
    static Stack<Integer> stack = new Stack<>();

    public static int oppo(int a) {
        // 1 번째 정점은 (0(부정), 1(참)), 2 번째 정점은 (2(부정), 3(참)) 이런식으로 구성할 것임, 즉 노드가 2배 필요함 N * 2
        return a % 2 == 0 ? a + 1 : a - 1; // 짝수이면 ㄱa, return a, 홀수이면 a, return ㄱa
    }

    public static int scc(int a) {
        id[a] = ++idValue;
        stack.push(a);

        int min = id[a];

        for (Integer next : graph.get(a)) {
            if (id[next] == 0) { // 아직 방문 안했으면
                min = Math.min(min, scc(next));
            } else if (!already[next]) { // 방문을 하였지만, 아직 SCC 에 포함이 안된 정점이면
                min = Math.min(min, id[next]);
            }
        }

        if (id[a] == min) { // 본인이 최종 부모이면
            sccValue++;
            while (true) {
                int b = stack.pop();
                already[b] = true;
                scc[b] = sccValue;
                if (a == b) { // 본인까지 빠지면 끝
                    break;
                }
            }
        }

        return min; // 최종 부모 반환
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        M = fun.apply(input[1]);

        scc = new int[N * 2];
        id = new int[N * 2];
        already = new boolean[N * 2];

        for (int i = 0; i < (N * 2); i++) {
            graph.add(new ArrayList<>());
        } // N * 2 만큼 공간을 만들어줘서, 그 수와 not 을 포함

        for (int i = 0; i < M; i++) {
            input = br.readLine().split(" ");

            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);

            a = (a < 0) ? (-(a + 1) * 2) : ((a * 2) - 1);
            b = (b < 0) ? (-(b + 1) * 2) : ((b * 2) - 1); // a, b 가 not 이면 짝수, not 이 아니면 홀수로 지정해준다.

            graph.get(oppo(a)).add(b); // ㄱa -> b 를 추가
            graph.get(oppo(b)).add(a); // ㄱb -> a 를 추가, 나중에 2 - sat - 4 는 이 간선을 이용해서 위상정렬도 진행할 것임
        }

        for (int i = 0; i < (N * 2); i++) {
            if (id[i] == 0) {
                scc(i);
            }
        }

        for (int i = 0; i < N; i++) {
            if (scc[(i * 2)] == scc[(i * 2) + 1]) { // i, ㄱi 가 같은 집합이면
                res = 0;
            }
        }

        System.out.println(res);
    }
}
