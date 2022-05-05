import java.util.*;
import java.io.*;
import java.util.function.Function;

// 17435 : 합성함수와 쿼리

/**
 * -- 전제 조건
 * f : {1, 2, 3, ....} -> {1, 2, 3 ....}
 * 이 있을 때, f(x) 가 주어지고, n 이 주어지는데, 이 경우는
 * fn(....f3(f2(f1(x)))) ... 등으로 이루어진다.
 *
 * 이럴때, m 이 20만 개, 쿼리가 50만개가 주어질 때, 1초만에 해결할 수 있도록 구현하라
 * -- 틀 설계
 * 일단 확실한 것은, 그냥 방법으로는 너무나도 쉽게 구현이 가능하다.
 * 하지만, 당연히 절대 그것이 아니라는 것을 알 수 있다.
 *
 * 나는 딱 봤을 때, 그냥 그래프가 그려진다.
 *
 * 조건 1. array에 저장된 값이 변하지 않는다.
 * 조건 2. f(a, b, c) = f(a, (b, c)) = f(f(a, b),c)로 결합 법칙이 성립한다.
 * 위 조건을 만족할 때 쿼리를 O(lgN) 만에 처리할 수 있는 자료 구조이다.
 * 전처리 과정을 통해 배열 내 구간의 쿼리를 빠르게 수행할 수 있도록 하는 자료구조라고 볼 수 있다.
 * 2의 거듭제곱인 범위의 구간값을 미리 계산해 저장해놓고 이용하는 방식이다.
 * 시간복잡도를 O(log n)까지 줄일 수 있다.
 *
 * 답을 봤는데 이게 LCA 구조에서 사용되던 원리였음
 * 즉, 희소 배열의 의미가 거듭 제곱으로 올라가는 것이였나봄...
 *
 * 난 당연히 트리에서만 가능한 줄 알았음.
 * 근데, 저런식의 결합법칙이 성립하는 경우, array 의 값이 변경되지 않는 경우 등등이 가능하다.
 * 여기에는 사이클이 형성될 수 있다. 하지만, 잘 생각해보면 LCA 기초지식인 희소배열 알고리즘이 안될 이유는 전혀 존재하지 않는다.
 *
 * 뭐 위의 조건 2를 굳이 이해를 하지 않더라도, 생각해보면 희소배열 알고리즘이 안될 이유는 없다, 그래서 이 알고리즘을 사용하여 해결이 가능하다.
 *
 * -- 해맸던 점
 * 이상하게 항상 그러는데, for 문에서 j++ 를 해줘야 하는데, i++ 를 해준다 자꾸
 * 그래서, 그것때문에 한 1분 해맸고, 바로 찾아서 해결하였음
 *
 * height 를 저런식으로 log 로 구하니까 이상하게 나옴
 * size 가 1일 때 문제가 있나?
 * 거의 맨 마지막에 터졌는데
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder res = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());
        String[] input = br.readLine().split(" ");
//        int height = (int) Math.floor(Math.log(N) / Math.log(2)) + 1; // 솔직히 size 로 19를 바로 사용해도 된다. (2 ^ 18) 이 20만이니까, 하지만 그렇게 하고 싶지않다, 사이즈는 동적으로 생성해보자.
        int height = 18; // height 를 동적으로 생성하면 안되는 이유는, 배열의 크기가 작아도, n == 50 만에 가깝게 나올 수 있기 때문이다, 즉 height 는 쿼리가 주어졌을 때, 그것을 빨리 구하기 위함이다.
        int[][] parent = new int[height + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            parent[0][i] = fun.apply(input[i - 1]);
        }

        for (int i = 1; i <= height; i++) {
            for (int j = 1; j <= N; j++) { // j 는 1 ~ N 까지 모든 노드
                parent[i][j] = parent[i - 1][parent[i - 1][j]]; // 현재 1은 2 ^ 1 위에 있는 부모, 즉 위의 위의 부모를 의미하고, 그것을 찾기 위해서는 본인의 부모의 부모를 찾아야 한다.
            }
        }

        int M = fun.apply(br.readLine());

        for (int i = 0; i < M; i++) { // M 개의 쿼리를 받음
            input = br.readLine().split(" ");
            int n = fun.apply(input[0]);
            int x = fun.apply(input[1]);

            // n 과 x 를 가지고 찾아야 한다.
            // 만일 n이 11이라면 부모노드로 11만큼의 부모노드로 이동하면 된다.
            // 그러면 n을 2진수로 표현하게 되면 1011 이고, 첫번째는 1, 2, 8 을 의미한다.
            // 그러면 쉽다, 바로 부모, 위의 위의 부모, 그리고 8 개 위의 부모를 찾아가면 된다.
            // 그러면 식을 n & (1 << j) != 0 로 구성할 수 있다
            for (int j = 0; j <= height; j++) { // 최대 height 까지 가능하지는 않은 것 같은데 확실하게 그렇게 하자
                if ((n & (1 << j)) > 0) { // 이렇게 했을 때, 0이 아니다? 그러면 그 자리가 1인 것이다 부모를 움직여준다.
                    x = parent[j][x];
                }
            }

            res.append(x).append("\n");
        }

        System.out.print(res);
    }
}
