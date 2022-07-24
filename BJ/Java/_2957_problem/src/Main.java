import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2957 : 이진 트리

/**
 * -- 전제 조건
 * 가장 적절한 위치를 찾는 것이다.
 * 이진 탐색트리에서의
 *
 * 근데 그 찾아가는 과정 중에 몇개의 정점을 거치냐
 * 즉, depth 를 기록 하는 문제이다.
 *
 * -- 틀 설계
 * 내가 했던 생각들이 무료할 만큼
 * treeSet 을 이용하면 lower, higher 라는 메소드를 통해서
 * 너무나도 쉽게 풀 수 있는 문제였다.
 *
 * 내가 한 가정 자체는 이랬다.
 * 트리를 그냥 배열로서 펴보면
 *
 * 트리가 들어갈 적절한 위치는
 * 당연히 해당 숫자보다 낮으면서 가장 가까운 숫자와, 높으면서 가장 가까운 숫자 사이에 들어간다.
 *
 * 그리고, 필연적으로 depth 가 더 높은 것을 선택해서 그 자식에 달리게 된다.
 * 왜냐하면, depth 가 더 높다라는 것은 이미 depth 가 더 낮은놈은 이미 자식을 나보다 낮거나 높은놈을 자식으로 가지고 있는 것이다. (서브트리로라도)
 *
 * 그래서 그런식으로 문제를 풀면 된다.
 * 엄청 쉬운 문제이다. 그 개념만 알면
 *
 * -- 해맸던 점
 * 실제로 set 에다가 숫자를 추가해주지 않아서 계속 값이 0이 나왔었다.
 * 이미 푸는 방법을 다 유도를 해놨는데
 * 어떻게 이걸 접근을 빨리해야 할 지 몰라서 답을 봤는데
 * 결국 TreeSet 을 푸는 문제였다.
 *
 * TreeSet 을 이용한다면?
 * 엄청나게 쉬운문제로 변한다.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2957_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        // set 에서 가장 낮은 놈, 가장 높은 놈을 골라낼 때, 쉽게 할 수 있도록 양쪽에 0, n + 1 을 넣어주도록 한다.
        // 그리고 depth 를 따로 기록해 둘 것이고, set 에 있는 숫자는 정점의 번호를 의미하고 depth[숫자] 는 정점의 depth 를 의미한다.
        // 그리고 내 왼쪽, 오른쪽에 있는 놈들 중 높은 depth 를 가진놈 + 1 이 본인 depth 이기에, 쉽게 할 수 있도록 depth[0], depth[depth.length - 1] 은 -1을 넣어줄 수 있도록 한다
        // 이것을 제외하고는 이 문제는 아무것도 고려할 사항이 없다.
        TreeSet<Integer> set = new TreeSet<>();
        int N = fun.apply(br.readLine());
        int[] depth = new int[N + 2];
        depth[0] = -1;
        depth[depth.length - 1] = -1;
        set.add(0);
        set.add(N + 1);
        long ans = 0;

        for (int i = 0; i < N; i++) {
            int a = fun.apply(br.readLine());
            depth[a] = Math.max(depth[set.lower(a)], depth[set.higher(a)]) + 1;
            set.add(a); // Set 에다가 추가를 안해줬음 이러면 낮은 숫자, 높은 숫자가 계속 0 과 n + 1 로 나옴
            ans += depth[a];
            sb.append(ans).append("\n");
        }

        System.out.print(sb);
    }
}
