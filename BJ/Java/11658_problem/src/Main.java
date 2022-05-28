import java.util.*;
import java.io.*;
import java.util.function.Function;

// 11658 : 구간 합 구하기 3

/**
 * -- 전제 조건
 * 2차원 배열이 주어질 때,
 * 구간합을 구하라고 주어지면
 * 구간합을 구해서 출력하라
 * w, x1, y1, x2, y2 가 주어지는데
 * w == 0 이면 update (이 경우는 w, x, y)
 * w == 1 이면 sum 이라고 생각하면 된다.
 *
 * -- 틀 설계
 * 세그먼트 트리로 풀려고 했다가
 * 팬윅 트리라는 것을 알게 되었음
 *
 * 근데 생각보다 너무 쉬움
 * 그래서 이것을 2차원으로도 한번 적용해보려고 한다.
 *
 * 일단 팬윅 트리의 포인트는 2가지이다.
 * 인덱스를 2진수로 변환해서 생각한다.
 * 그리고 인덱스를 1부터 시작한다.
 *
 * 그래서 팬윅 트리는 일단 맨 처음에 나오는 1의 수만큼 앞의수를 더한 값을 가지고 있다.
 * 예를 들어서 100 이라고 하면 (4) 1, 2, 3, 4 의 합을 가지고 있다 (누적합)
 * 이러는 이유는 나중에 sum 을 구하게 될 때 알 수 있다.
 *
 * sum 을 구할 때 예를 들어서
 * 111 이라고 한다면 누적합을 구하려면
 * sum(4) + sum(6) + sum(7) 이다
 * 그러면 이것을 어떻게 구할 수 있냐
 * 그냥, tree[i] 를 계속 더 해주면서
 * 1 & -1 을 해주면 된다.
 * 왜 그러냐면 0100 을 예로 들어보자.
 * 0100, ~0100 == 1011 음수는 여기서 1을 더한 수이다.
 * 즉, 그렇다라는 것은 1100 이 되게 된다.
 * 그러면 and 연산을 하게 되면 당연히 0100 이 된다.
 * 왜냐하면? 다 반대로 취해준다음에, 1을 더해서 처음으로 올림수가 없는 위치가
 * 이전에 처음으로 1이 있던 위치인 것은 당연한 것이니까
 * sum 은 이렇게 한다라느 것을 알 수 있다.
 *
 * 그러면 update 는 어떻게 할까?
 * 이것도 사실 쉽다.
 * 예를 들어서 1번 인덱스를 바꿨다라고 가정해보자.
 * 그러면 1, 10, 100, 1000 을 바꿔야 한다.
 * 여기서 확실한 것은 처음에 1이 나온 것을 더한다라는 것이다.
 * 그러면 이전에 update, sum 의 차이점은 딱 하나이다.
 * i += i & -i 와
 * i -= i & -i 로 나뉜다.
 *
 * 이런 것으로 미루어 보았을 때, 사이즈도 그렇고
 * 팬윅트리가 훨씬 단순한 것 같다라고 생각이 들어서 팬윅 트리로 풀어보려고 한다.
 *
 * 2차원 배열에서 팬윅 트리는 그냥 원래는 x 축만 존재했다면 y 축을 추가해줬다라고 생각하면 되고
 * y 축에 대한 연산도 그냥 완전히 똑같이 한다.
 *
 * 그러니 그냥 이전에 1 중 while 문이였다면, 지금은 그냥 2 중 while 문으로 바뀐 것 그 이상 그 이하도 아니다.
 * 그리고, 사실 이 부분은 잘 이해하지 못했는데, x, y 축으로 둘다 팬윅 트리를 초기화 할 때, 갱신된다라는 것을 인지할 수 있었다.
 * 그렇기 때문에, y 축에 대한 값도, x 축에 대한 값도 그냥 똑같이 위와 같이 해주면 된다.
 *
 * -- 해맸던 점
 * 일단, 범위가 1 ~ N 이라는 점을 간과하고
 * while (y < N) 으로 조건문을 걸어놨었음
 *
 * 그리고 sum 은 nx -= (nx & -nx) ... 이런식으로 해야하는데
 * nx += (nx & -nx) ... 이런식으로 했었음
 * 그리고 이차원 배열에서 누적합을 이용한 연산을 진행할 때
 * 원하는 구간의 합을 구하기 위하여,
 * sum(y2, x2) - sum(y1 - 1, x2) - sum(y2, x1 - 1) + sum(y1 - 1, x1 - 1)
 * 이런식의 연산을 해야 하는데, 여기서 -1 을 안해줘서 겹치게 빼버렸음
 *
 * 그리고 맨 마지막으로 가장 어이없는 실수는
 * int y = fun.apply(input[1]);
 * int x = fun.apply(input[2]);
 * int c = fun.apply(input[3]);
 *
 * int diff = c - map[y][x];
 * update(y, x, diff); // update
 * map[y][x] = c;
 *
 * 저기 y, x, c 입력 받을 때 input[1] 만 계속 받았음..
 * 그래서 틀렸었음
 *
 * -- 결론
 * 이번에 이 문제를 풀면서 팬윅 트리를 공부해봤는데
 * 세그먼트 트리보다 생각할 부분도 적고
 * 코드도 간결해서 너무 좋은 알고리즘이라는 걸 깨달았음
 * 심지어 속도도 느리지 않다
 */
public class Main {
    static int N;
    static int M; // 연산 수
    static int[][] map;
    static int[][] tree;

    static int sum(int y, int x) {
        int res = 0;

        while (y > 0) {
            int nx = x;

            while (nx > 0) {
                res += tree[y][nx];
                nx -= (nx & -nx);
            }

            y -= (y & -y);
        }

        return res;
    }

    static void update(int y, int x, int diff) {
        while (y <= N) {
            int nx = x;

            while (nx <= N) {
                tree[y][nx] += diff;
                nx += (nx & -nx);
            }

            y += (y & -y);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        M = fun.apply(input[1]);
        map = new int[N + 1][N + 1];
        tree = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            input = br.readLine().split(" ");
            for (int j = 1; j <= N; j++) {
                map[i][j] = fun.apply(input[j - 1]);
                update(i, j, map[i][j]);
            }
        }

        while (M-- > 0) { // M 번 반복
            input = br.readLine().split(" ");
            int w = fun.apply(input[0]);

            if (w == 0) { // 변경
                int y = fun.apply(input[1]);
                int x = fun.apply(input[2]);
                int c = fun.apply(input[3]);

                int diff = c - map[y][x];
                update(y, x, diff); // update
                map[y][x] = c;
            } else { // 합 구하기
                int y1 = fun.apply(input[1]);
                int x1 = fun.apply(input[2]);
                int y2 = fun.apply(input[3]);
                int x2 = fun.apply(input[4]);

                sb.append(sum(y2, x2) - sum(y1 - 1, x2)
                        - sum(y2, x1 - 1) + sum(y1 - 1, x1 - 1)).append("\n");
            }
        }

        System.out.println(sb);
    }
}