import java.util.*;
import java.io.*;
import java.util.function.Function;

// 11375 : 열혈강호

/**
 * -- 전제조건
 * 각각 회사원들에게 할 수 있는 일의 리스트가 주어졌을 때,
 * 최대로 많은 일을 하는 경우에서의 일의 개수를 출력하시오.
 *
 * -- 틀 설계
 * 이 문제를 토대로 이분 매칭을 공부하게 되었다.
 * 생각보다 쉬운 알고리즘이지만, 혼자서는 생각못할 것 같다.
 *
 * 일단 이분 매칭이란 이분 그래프가 주어졌을 때, 최대 유량을 구할 수 있는 문제이다.
 *
 * 여기서 또 알고 지나가야 할 이분 그래프의 정의는, 모든 간선에 연결되어 있는 정점들이 서로 다른 그룹인 경우이다.
 * 즉, 단 하나의 간선도 같은 그룹을 포함하고 있는 경우는 없다.
 *
 * 이렇게 조건이 만족이 되었다면, 이분 매칭 알고리즘은 굉장히 쉬워진다.
 *
 * 알고리즘의 전체적인 흐름도는 이러하다.
 *
 * 일단, 가장 먼저 매칭을 시작한놈은 본인과 연결된 정점을 매칭한다.
 * 그래서, 매칭에 성공했다면 즉시 종료한다.
 *
 * 그리고 다음 정점이 매칭을 진행하게 되는데,
 * 이 때, 만일 이미 매칭된 정점과 매칭이 되었다면
 * 해당 정점과 매칭되어 있던 정점으로 타고 가서, 그 정점이 다른 정점을 매칭할 수 있도록하여
 * 본인이 그 정점을 차지하는 형태이다.
 *
 * 여기서 두 가지의 경우가 존재한다.
 * 첫째, 타고 가서 이미 매칭되었던 정점이 공간이 나서, 매칭에 성공하게 되는 경우
 * 둘째, 타고 가서 이미 매칭되었던 정점으로 갔는데, 얘도 더 이상 갈데가 없어서 매칭에 실패하는 경우
 *
 * 이러한 두 가지의 경우가 존재하고, 바꾸는 과정은 계속해서, 재귀적으로 타고 올라가면서 처리할 수 있다.
 *
 * 즉, 어떠한 경우냐면
 * 1 2 3 번이 있고 매칭 정점은 4 5 6 번이 있다라고 가정해보자.
 * 그 때, 3 -> 4 번 정점과 연결될 수 있고 1 -> 4, 5  2 -> 5, 6 이 가능하다고 해보자.
 * 그리고 일단 1 -> 4, 2 -> 5 이렇게 가르키고 있었다고 해보자 (실제로는 이러지 않을 것이다.)
 *
 * 그러면 3 -> 4 번을 매칭하려고 할 때, 이미 4 번을 점령했던 1 번을 타고 올라가서 매칭된 정점을 변경하려고 할 것이다.
 * 그러면 5 번을 가르키게 되는데 2번이 또 가르키고 있다. 그래서 2 번으로 가서 2 번이 6 번을 가르키려 할 때, 드디어 점령되지 않은 정점을 만나
 * 매칭 정점을 변경하게 되고, 1 이 5 번을 가르키고 또 성공하여, 3 번이 4번을 가르킬 수 있게 되는 것이다.
 *
 * 이런식으로 재귀적으로 플로우가 흘러가며, 최대유량을 결정할 수 있다.
 * 이 설명을 보면 알겠지만 굉장히 간단한 알고리즘이다 그래서, 코드도 굉장히 짧은 편이다.
 *
 * -- 결과
 * 따로 해맨 점은 없었고
 * 어제 공부했던 내용을 바탕으로 복기하여 코드를 작성하니 맞았음
 */
public class Main {
    static int N;
    static int M;
    static int res = 0;
    static int[] occupy; // 현재 해당 정점을 점령하고 있는 정점에 대한 정보를 입력할 것이다.
    static boolean[] visited;
    static List<ArrayList<Integer>> graph = new ArrayList<>();

    static boolean dfs(int a) {
        if (visited[a]) { // 이미 방문한 경우이다.
            return false;
        }

        visited[a] = true; // 방문하였으니까, 이제 처리하면 된다. (방문처리)

        for (Integer next : graph.get(a)) { // 본인과 연결된 일들을 보고 처리해야 한다.
            // occupy 가 0이다? 그러면 본인이 매칭하면 된다. 만일 아니다 그러면 그거로 가가지고 다른 매칭을 찾아야 한다, 그리고 이미 매칭된 정점이면 그냥 지나가는 역할도 한다.
            // (본인이 이미 매칭한 정점일 수도 있으니까 visited 배열을 이용해서 stack over flow 가 나지 않게 한다.)
            if (occupy[next] == 0 || dfs(occupy[next])) {  // dfs 에서 true 를 뱉으면, 내가 지금 매칭하려는 정점이 비었다라는 것이다 바로 뺏어버렷
                occupy[next] = a;
                return true;
            }
        }

        return false; // 현재 정점에서는 매칭 실패
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        M = fun.apply(input[1]);
        occupy = new int[M + 1]; // 일의 개수

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 1; i <= N; i++) {
            input = br.readLine().split(" ");

            for (int j = 1; j <= fun.apply(input[0]); j++) {
                graph.get(i).add(fun.apply(input[j]));
            }
        }

        for (int i = 1; i <= N; i++) {
            visited = new boolean[N + 1];

            if (dfs(i)) { // 결론적으로 새로운 매칭에 성공하였을 떄, true 를 반환하는 것이다, 그래서 이런식으로 처리해도 된다.
                res++;
            }
        }

        System.out.println(res);
    }
}
