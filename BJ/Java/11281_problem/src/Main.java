import java.util.*;
import java.io.*;
import java.util.function.Function;

// 11281 : 2-SAT - 4

/**
 * -- 전제조건
 * 문제는 2-SAT - 3 와 동일하다.
 * 하지만, 여기서 추가된 조건은 실제로 식에서 사용되는 x 값들의 boolean 값을 직접 지정해주어야 한다.
 * 그래서 조금 더 어려운 문제라고 할 수 있다.
 * -- 틀 설계
 * 이전 2-SAT - 3 에서는 모순인지 아닌지, 즉 이 식이 True 로 만드는 것이 가능한지 안한지에 대해서
 * 판단하는 문제였음, 그래서 모순이 발생하는지 안하는 지를 보기 위해서
 * 본인과 본인의 부정 즉, True -> False, False -> True 가 둘다 존재하는 경우를 막기 위해서
 * SCC 들 안에 부정과, 참이 존재하는지 확인했다.
 * 여기서는 추가적으로 더 나아간다.
 *
 * 해당 식이 모순이 되는지, 즉 true 의 결과값을 가질 수 없는지에 대해서 판단하는 것 뿐만 아니라.
 * x ... 값들을 정해주어야 한다.
 * 당연히 식을 True 로 만들어주는 값들이 여러개 존재할 수 있다.
 * 그래서, 해당 문제는 스페셜 저지로 이루어져 있다.
 *
 * 일단, 당연하게도 SCC 를 통해서 같은 그룹 내에 존재하는지 안하는지 확인해주면 된다.
 *
 * 그 다음에, 추가적으로 명제에 대해서 생각해볼 필요가 있다.
 * False -> True, False 로 이루어져 있다라고 가정했을 때, 함의의 특성상 무조건 적으로 False 에서 출발하게 되면
 * 해당 조건의 값은 True 가 된다.
 *
 * 그러면 SCC 간의 모순이 발생하지 않는 다라는 것을 확인하였으니까
 * SCC 와 연결되는 SCC 가 존재한다라면
 * 해당 SCC 간의 연결은 정점끼리 연결되었다고 봐도 무방하다고 할 수 있다.
 *
 * 그래서 결국 모든 간선들은 함의로 이루어져 있고,
 * 함의의 특성상 앞의 명제가 False 인 경우가 좋기 떄문에 우선적으로 False 로 지정하는 것이 좋다라는 것을 알 수 있다.
 *
 * 일단, 이렇게 정해도 되는 이유는 SCC 간의 연결을 보았을 때, 어떤 SC 가 어떤 SCC 를 가르킬 떄,
 * False 로 지정하더라도 그 지점은 True 가 되거나 False 가 도리 수 있는데 이런 경우 True -> False 가 되는 경우는 없다라고 보장할 수 있다고 한다.
 *
 * 사이트의 말을 인용하면
 *
 * q = ¬p라 할 때, p가 먼저 방문되었을 경우 p를 false로, q를 true로 설정하는 식으로 하면,
 *
 * p와 q는 서로 다른 SCC에 있고, p가 속한 SCC를 P, q가 속한 SCC를 Q라 하면,
 * P에서 Q로 가는 경로야 있을 수 있지만 Q에서 P로 가는 경로는 없으므로 이런 방식이 먹힙니다.
 * 즉, 이런 방식으로 값을 매기다가 참 -> 거짓 꼴의 이동경로가 생기지 않습니다.
 *
 * 즉 여기서 말하는 말은 먼저 만나는 정점을 우선적으로 False 로 설정하면 틀릴 일이 없다라는 것이다.
 * 그렇기 때문에 그렇게 지정하게 되면, 틀리지가 않는다.
 *
 * 1 = True, 0 = False
 *
 * if (cnf[j] == -1) { // 아직 j 가 결정되지 않았으면
 *     if (j % 2 == 0) { // 처음 만난애가 부정이면
 *         cnf[j] = 0;
 *         cnf[j + 1] = 1;
 *     } else {
 *         cnf[j] = 1;
 *         cnf[j - 1] = 0;
 *     }
 * }
 *
 * 원래 진짜 개 바보같이 이렇게 작성해놓고 왜 틀리지 이러고 앉아있었음
 * 당연히 내가 한 버전에서는 짝수이면 = 거짓, 홀수이면 = 참 인데
 * 그러면 먼저 만나게 되는 것이 짝수인 경우에는 cnt[j] = 0 이 되어야 한다.
 *
 * 즉, 먼저 만나게 되는 것이 not 인 경우는 항상 그렇듯 j = 0 이 되어야 하고
 * (현재 먼저 만난 것이니까, 무조건 해당 값은 false 가 되어야 함)
 *
 * 그러고서, 나머지 값은 반대로 해줬으면 됐는데
 * 개 바보같이 이상하게 짬
 *
 * SCC 번호가 높은 것,
 * 즉 나중에 담긴 애들은 SCC 번호가 높다.
 * 그러니까 신기하게도 SCC 의 특성으로 인해서, SCC 번호가 높을 수록 위상정렬 순에 앞서 있다라는 점을 이용하여
 *
 * 먼저 만나는 애들을 false 로 설정해줄 수 있다.
 * 그러면 어떠한 SCC -> SCC 로 가고, 이전 SCC 가 false 라면 일단 뒤에 SCC 가 참이든 거짓이든 상관이 없다.
 * 그리고 모순이 일어날일도 없다, SCC 간의 사이클은 존재하지 않으니까
 *
 * 그래서, 저기서 한 로직도 그냥 어떠한 정점에서 거짓, 참중 먼저 만나는 것을 false 로 하게 되면
 * 나머지 거짓, 혹 참은 boolean 값이 결정되게 된다.
 *
 * 그렇기 떄문에 그 점을 이용해서 sccValue - 1 부터, 즉 SCC 번호가 높은 순으로 부터
 * 순회를 시작하면서 먼저 만나는 정점은 무조건 적으로 false 처리를 해주고 (0), 그리고 그 반대의 정점은 즉 반대의 성질의 정점에는
 * 그 반대값인 1을 넣어주면 된다.
 *
 * 이 쉬운거를 난 뭐가 어려워서 그렇게 헷갈렸는지 모르곘다.
 * 왜 그렇게 바보같이 작성했는지
 */
public class Main {
    static int V;
    static int E;
    static int idValue = 0;
    static int sccValue = 0;
    static int[] id;
    static int[] scc;
    static int[] cnf;
    static boolean[] already;
    static List<List<Integer>> sccGroup = new ArrayList<>();
    static List<ArrayList<Integer>> graph = new ArrayList<>();
    static Stack<Integer> stack = new Stack<>();

    static int oppo(int a) {
        return (a % 2 == 0) ? (a + 1) : (a - 1); // 짝수 (거짓) -> 홀수 (참), ..
    }

    static int scc(int a) {
        id[a] = ++idValue;
        stack.push(a);
        int min = id[a];

        for (Integer next : graph.get(a)) {
            if (id[next] == 0) { // 아직 방문하지 않았다면
                min = Math.min(min, scc(next));
            } else if (!already[next]) { // 아직 scc 집합에 포함되지 못했다면
                min = Math.min(min, id[next]); // id 값을 반환
            }
        }

        if (id[a] == min) { // 본인이 사이클에서의 부모일 때
            List<Integer> addList = new ArrayList<>();
            sccValue++;
            while (true) {
                int b = stack.pop();
                already[b] = true;
                scc[b] = sccValue;
                addList.add(b);
                if (a == b) {
                    break;
                }
            }
            sccGroup.add(addList);
        }

        return min;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        V = fun.apply(input[0]);
        E = fun.apply(input[1]);

        id = new int[V * 2];
        scc = new int[V * 2];
        cnf = new int[V * 2];
        already = new boolean[V * 2];
        Arrays.fill(cnf, -1); // -1 로 채워넣음, 아직 실질적으로 방문해서 값을 집어넣었는지 판단을 해야하기 때문이다.

        for (int i = 0; i < (V * 2); i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            input = br.readLine().split(" ");

            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);

            // not 은 홀수
            // true 는 짝수로 지정
            a = a < 0 ? (-(a + 1) * 2) : ((a * 2) - 1);
            b = b < 0 ? (-(b + 1) * 2) : ((b * 2) - 1); // 음수인 경우 짝수, 양수인 경우 홀수로 지정해주면서 not 을 표현

            graph.get(oppo(a)).add(b);
            graph.get(oppo(b)).add(a); // 교집합을 함의로 바꿔줌
        }

        for (int i = 0; i < (V * 2); i++) {
            if (id[i] == 0) {
                scc(i);
            }
        }

        int cont = 1;

        for (int i = 0; i < V; i++) {
            if (scc[i * 2] == scc[(i * 2) + 1]) { // 모순이 발생한 경우 cont 에다가 0을 집어넣어줌
                cont = 0;
            }
        }

//        for (int i = 0; i < sccValue; i++) {
//            System.out.println(sccGroup.get(i));
//        }

        StringBuilder res = new StringBuilder();
        res.append(cont + "\n");

        if (cont == 1) { // 모순이 아닌 경우만
            for (int i = sccValue - 1; i != -1; i--) { // SCC 번호가 높으면, 위상정렬 상 앞에 있음, 즉 먼저 시작하는 애임
                for (Integer j : sccGroup.get(i)) { // 역으로 진행
                    // 처음 만난애가 false 가 되어야 한다.
                    if (cnf[j] == -1) {
                        if ((j % 2) == 0) { // 거짓을 먼저 만난 경우
                            cnf[j] = 0;
                            cnf[j + 1] = 1;
                        } else {
                            cnf[j] = 0;
                            cnf[j - 1] = 1;
                        }
                    }
                }
            }

            for (int i = 1; i < (V * 2); i += 2) {
                res.append(cnf[i]).append(" ");
            }
        }

        System.out.println(res);
    }
}

