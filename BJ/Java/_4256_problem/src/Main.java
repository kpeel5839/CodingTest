import java.io.*;
import java.util.*;
import java.util.function.Function;

// 4256 : 트리

/**
 * -- 전제 조건
 * 이전에 내가 풀었던 트리 문제와 굉장히 흡사하다.
 * 문제는 정말 별거 없다.
 *
 * 그냥 전위 순회, 중위 순회가 주어지고
 * 그러면 우리는 후위 순회만 구하면 된다.
 *
 * -- 틀 설계
 * 이전에 풀었던 양상의 문제이기에 이미 해답을 알고 있다.
 * 전위 순회는 제일 먼저 서브트리의 루트를 방문하여 출력한다.
 * 즉, 3, 6, 5, 4 ... 이렇게 진행되는 것들 모두다
 * 서브트리의 루트부터 순서대로 출력이 진행 된다.
 *
 * 이것을 이용하여서 중위 순회를 사용할 수 있다.
 * 중위 순회는 중간에 트리의 루트 지점이 있다.
 *
 * 그리고 그 루트를 기준으로 왼쪽은 왼쪽 서브트리, 오른쪽은 오른쪽 서브트리로 나뉜다.
 * 그러면 너무 쉽다.
 *
 * 이제 주어진 전위 순회를 가지고 순서대로 진행하면 된다.
 *
 * 일단 처음에는 모든 범위를 주고 첫번째 지점을 기준으로 중위 순회를 반으로 자른다.
 * 그리고, 전위 순회의 맨 앞을 자르고 왼쪽에 있던 것 만큼 + 해서 다음 걸로 넘기고
 * 그 다음에 그 다음 것부터 또 넘긴다 예를 들면 이러하다.
 *
 * 3 6 5 4 8 7 1 2
 * 5 6 8 4 3 1 2 7 이렇게 있으면
 * 3 을 기준으로 아래것을 자른다.
 * 5 6 8 4 / 3 / 1 2 7 이런식으로 된다.
 * 그러면 왼쪽은 4개 오른쪽은 3개이다.
 * 그러면 3 / 6 5 4 8 / 7 1 2 이런 식으로 나누면 된디.
 *
 * 이렇게 하게 되면 매우 쉽게 풀 수 있다.
 * 이런식으로 식이 세워진다. 전위 순회 시작 + 1, 전위 순회 시작 + 왼쪽 자식 개수, 전위 순회 시작 + 왼쪽 자식 개수 + 1, 전위 순회 맨 끝
 * 이런식으로 식을 세울 수 있다. 그러면
 * 이제 각각의 테스트 케이스 개수가 주어지고
 * 정점의 개수가 주어졌을 때, 각각 출력해보자.
 */

public class Main {
    static void divide(StringBuilder sb, int[] pre, int[] in,
            int preS, int preL, int inS, int inL) {
        if (preL < preS || inL < inS) { // 범위를 벗어나면 끝낸다.
            return;
        }

        int pivot = pre[preS];
        int count = 0;

        for (int i = inS; i <= inL; i++) { // pivot 을 차즌낟.
            if (in[i] == pivot) {
                break;
            }

            count++;
        }

        divide(sb, pre, in, preS + 1, preS + count, inS, inS + count - 1);
        divide(sb, pre, in, preS + count + 1,  preL, inS + count + 1, inL);

        sb.append(pre[preS]).append(" ");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        int T = fun.apply(br.readLine());

        while (T-- > 0) {
            int N = fun.apply(br.readLine());
            int[] pre = new int[N];
            int[] in = new int[N];

            String[] input = br.readLine().split(" ");
            for (int i = 0; i < N; i++) {
                pre[i] = fun.apply(input[i]);
            }

            input = br.readLine().split(" ");
            for (int i = 0; i < N; i++) {
                in[i] = fun.apply(input[i]);
            }

            divide(sb, pre, in, 0, pre.length - 1, 0, in.length);
            sb.append("\n");
        }

        System.out.print(sb);
    }
}
