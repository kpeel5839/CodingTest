import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1135 : 뉴스 전하기

/**
 * -- 전제조건
 * 민식이가 가장 상위에 있고,
 * 그 다음에, 하위 직원들이 있을 때,
 * 소식은 하위 직원들에게만 전달할 수 있다라고 가정하고
 * 소식을 한번 전달하는데 1초가 걸린다고 가정하였을 때,
 *
 * 모든 사원들이 소식을 알게되는 최소시간을 구하시오.
 *
 * -- 틀 설계
 * 확실히 머리로만 하는 것이 아닌 실제로 그려보면서 하니까 생각이 빠르게 정리되고 문제 해결 역량도 올라가는 느낌이 든다.
 * 역시 고집부려서 머리로만 생각하지말고 쓰면서 하는 게 나은 것 같다.
 *
 * 일단 아이디어는 이러하다.
 * 모든 서브트리를 보고
 * 해당 서브트리가 몇초가 걸리는지
 * 그 최소시간을 계속 구하면서 갱신해 나가는 것이다.
 *
 * 그냥 그게 끝이다.
 *
 * 그래서 일단 이진트리를 구성하고, 끝에 도달하게 되면 return 1 을 한다.
 * 그래서 그것을 List 에다가 모으고 res = Math.max(res, number + index) 를 계속 하면서
 * 정해지면 반환하면 된다.
 *
 * 그렇게 되면 각 서브트리가 최대한 빠르게 탐색을 진행했을 때, 시간이 얼마나 걸리는 지 알 수 있다.
 *
 * -- 해맸던 점
 * 사실 별로 해매지는 않았는데
 * 모든 것이 순조로웠지만
 * 마지막에 자꾸 + 1 된 값이 나오길래 곰곰히 생각을 해보니까
 * 처음에 시작지점을 넘겼을 때도, 그냥 method 의 입장에서는 누군가에게서 전해져온 값이고
 * 상위 부모에게 본인시간 + 본인에게로 보낸 시간을 전달하기 때문에 자꾸 +1 이 더해져서 넘어왔던 것임
 * 그래서 1분정도 해맸고, -1 을 해줌으로서 해결하였음
 *
 * 그래서 이 문제의 핵심은 이것임
 * 재귀적 순환을 통해서 서브트리를 가장 작게 쪼갠다
 * 그러면서 그 서브트리로 부터 해당 서브트리를 다 돌기 위해서는 몇초가 걸리는지 반환을 하는데
 * 본인에게로 보낸 시간까지 더해서 반환하게 된다
 * 그래서 leap 노드의 경우에는 0 + 1 을 반환하게 되는 것이다.
 *
 * 그래서 leap 노드의 경우 그렇게 되고, 그것을 통해서 또 상위의 서브트리의 최소시간을 구하기 위해서는
 * 본인과 연결된 각각의 서브트리의 걸리는 시간을 (최소 시간) 알아야 한다.
 *
 * 그러고서 다 알게 되면
 * 1초에 본인의 자식 노드에게 하나씩 소식을 전할 수 있기 때문에 자식들의 걸리는 시간들을 정렬을 진행해준다음
 * 가장 오래걸리는 놈부터 소식을 전한다고 가정했을 때,
 * for (Integer value : list) res = Math.max(res, value + index++) (index 는 0 부터 시작 (제일 처음에 보낸 애는 바로 보낸 거니까))
 *
 * 이런식으로 본인 서브트리의 최소 시간도 구하게 되면, 또 반환하고 재귀적으로 그것을 이어가면 된다.
 * 그러면 쉽게 정답을 구할 수 있다.
 *
 * 그래서 이 문제의 핵심은 결국, 1초에 하나씩 보낸다라는 것을 인지하고 위와 같이 sort 를 진행한다음 본인으로서 할 수 있는 최소시간을 결정한 뒤
 * 가장 작은 서브트리부터 역순으로 최소시간을 구해나간다라는 것이다.
 */
public class Main {
    static int N;
    static List<ArrayList<Integer>> graph = new ArrayList<>();

    static int dfs(int p, int c) { // p == parent, c == cur node 이다.
        int res = 0;
        int index = 0;
        List<Integer> list = new ArrayList<>();

        for (Integer next : graph.get(c)) {
            if (next != p) { // 부모가 아닌 경우에만
                list.add(dfs(c, next)); // 최소 값들을 받아준다 (서브트리를 다 도는데 걸리는 최소시간)
            }
        }

        Collections.sort(list, (o1, o2) -> o2 - o1);

        for (Integer value : list) {
            res = Math.max(res, value + index++);
        }

        return res + 1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;
        N = fun.apply(br.readLine());

        for (int i = 0; i < N; i++) { // 0 번째 인덱스부터 시작이니까 i < N
            graph.add(new ArrayList<>());
        }

        String[] input = br.readLine().split(" ");
        for (int i = 1; i < N; i++) {
            graph.get(Integer.parseInt(input[i])).add(i);
        }

        // res + 1 을 반환하기 때문에 -1 을 해준다 왜냐하면 0번째 지점이 시작 지점이니까
        System.out.println(dfs(-1, 0) - 1);
    }
}
