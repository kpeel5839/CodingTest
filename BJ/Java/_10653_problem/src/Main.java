import java.util.*;
import java.io.*;
import java.util.function.Function;

// 10653 : 마라톤 2

/**
 * -- 전제조건
 * 소가 마라톤을 달린다.
 * 각 체크포인트들이 주어지고
 * N 과 K 가 주어진다.
 *
 * 최대 K 개의 체크포인트를 건너뛸 수 있고
 * 꼭 첫번째 체크포인트와 마지막 체크포인트는 지나야 한다.
 *
 * 이렇게 주어졌을 때, 소가 완주를 하기 까지, 최소거리를 구하여라.
 *
 * -- 틀 설계
 * 일단 처음에는 그래프 이론적으로 생각했다.
 * 근데, 아무리 봐도, 브루트 포스하게 가야하는 것 같았다.
 *
 * 그래서 dfs 를 생각해보았는데, 너무 크다 N의 사이즈가
 * 그래서 DP 적으로 생각해보았다.
 *
 * 약간 감이 올랑 말랑 하다.
 *
 * 일단 내 설계는 이러하다.
 * dp[i][j] 가 의미하는 것은 i 까지 도달하였을 때,
 * j 개를 건너띈 경우 최소 비용을 저장하는 것이다.
 *
 * 그러면 당연하게 dp[i][j] 일 때, i 가 0 이라면?
 * j 는 0 밖에 될 수 없다.
 * 왜냐? 첫 지점은 건너 뛸 수 없으니까
 * 그래서 i 는 1 부터 시작해서
 * 진행한다.
 * 일단 i 가 1이다?
 *
 * 그러면 마지막 지점이 아닌 이상
 * j = 0 일 때에는 첫번 쨰 포인터부터 여기까지 오는 경우
 * j = 1 일 때에는 계산할 수 없다. 왜냐하면 현재 포인트를 점프해야 하니까
 * 즉, i == j 일 때에는 아얘 거쳐가는 체크포인트가 없는 것이기에
 * 0으로 계산하면 된다.
 *
 * 그래서 이런식으로 계산할 수 있을 것 같다.
 *
 * for (int i = 1; i < N; i++) {
 *      for (int j = 0; j < i; j++) {
 *      }
 * }
 *
 * 이런식의 연산을 진행할 수 있을 것 같다.
 * 그리고 저기 안에서 실행되는 연산을 정해야 한다.
 *
 * 일단, j = 0 이다.
 * 건너뛰는 것이 없다.
 * 그러면 dp[i - 1][j] 과 graph[i - 1][i] 를 더해준다.
 * 즉, dp[i - j - 1][j - 1] + graph[i - j - 1][i] 와 dp[i - 1][j] 중 작은 것을 가져오면 된다.
 *
 * 이런식으로 점프를 하는 경우와 안하는 경우 두가지의 경우를 고려하고
 * graph 에다가 0 -> 0 ... 0 -> N - 1 ... N - 1 -> N - 1 까지 다 초기화 해주면서
 * (주어진 그래프를 가지고)
 * 위와 같은 연산이 가능케끔 한다.
 *
 * 그리고 dp[N - 1][K] 를 출력하면 된다.
 *
 * -- 해맸던 점
 * 처음에는 위와 같이 굉장히 단순하게 생각했었다.
 * 근데 생각보다 굉장히 어려운 문제였다.
 *
 * 일단 처음에는 dp 를 INF 로 가득 채워준다
 * 왜 그렇냐면
 * Math.min 을 통해서 이 경로로 가는 게 이득인지
 * 아니면 다른 경로로 가는것이 이득인지 판단을 해줘야 하기 때문이다.
 *
 * 그리고 받은 Points 를 이용해서 graph 를 갱신해준다.
 * 그냥 여느 방법과 다르지 않은 방법을 사용하였다.
 *
 * 그리고 이제 위와 같은 방법으로 진행을 하였다.
 * 당연히 건너뛰지 않는 경우는 위 생각이 정확히 맞았다.
 *
 * 하지만 건너뛰는 경우는 위 생각이 맞지 않았다.
 * 첫째, j < i 조건만 있으면 안된다.
 * 왜냐하면 당연하게 K < N 이니, N - K > 2 라면 index error 가 나게 되어있다. 그래서 j < i && j <= K 로 바꿔주었다. (다중 조건)
 *
 * 그리고 둘 째, 건너 뛰는 경우에 대해서 잘 못 생각했다.
 * 일단 현재 i 번째 지점으로서의 입장으로 봤을 때,
 * 이전 -> i 로 오는 경우에서 i - 1 -> i 부터 i - j - 1 -> i (j <= K)
 *
 * 이 즉 쉽게 말하면 본인으로 오기까지 건너뛰는 경우와
 * 건너뛰지 않는 경우를 계산하면 된다.
 *
 * 그래서 dp[i][j] 를 계산할 때 (j != 0)
 * for (int c = 0; c <= j; c++) 를 이용하여서 (c 는 본인 앞에 몇개를 건너뛰냐)
 * dp[i][j] = Math.min(dp[i][j], dp[i - c - 1][j - c] + graph[i - c - 1][i]) 라는 점화식이 나올 수 있게 된다.
 *
 * 이것을 쉽게 보면 c 는 0 부터 j 즉, 내 앞에 있는 모든 것을 건너 뛰냐 (K 범위 내에서) 혹은, 그냥 아얘 건너뛰지 않냐 (앞에 놈들이 건너뛴거를 그대로 이용하냐)
 * 그래서 c 개를 건너 뛰게 되면 i - c - 1 번째 정점에 도달할 것이고
 * 이미 c 개를 건너 뛰었으니까 dp[i - c - 1][j - c] (dp 값은 모두 최소비용으로 갱신하였기 때문에 가능함)
 * 에다가 graph[i - c - 1][i] 즉, i - c - 1 정점에서 i 번째 정점까지의 거리를 더해주면 되는 것이다.
 *
 * 그러면 c 개를 건너뛴 최소 비용을 구할 수 있다.
 * 하지만, 0 .. j 개까지 건너뛸 수 있기 때문에 당장 이것이 최소 비용인지 알 수 없기에
 * c 의 범위를 0 <= c <= j 까지 진행해주면서, 최소 0개, 최대 j개 본인 정점으로 까지 건너뛰면서 최소 비용을 갱신할 수 있는 것이다.
 *
 * 듬성 듬성 건너뛴 경우는 고려하지 않아도 된다.
 * 왜냐하면 이미 dp[i - c - 1][j - c] + graph[i - c - 1][i] 연산을 진행할 때, 본인이 c 개를 건너 뛰고
 * 이전에 건너뛴 놈들도 그대로 가져가기 때문에 그러한 경우는 다 포함이 되어있다.
 *
 * 즉 점화식은
 * (1 <= i < N)
 * (j == 0) -> dp[i - 1][j] + graph[i - 1][i];
 * (j != 0) -> Math.min(dp[i][j], dp[i - c - 1][j - c] + graph[i - c - 1][i]) (0 <= c <= j)
 * 로 정리된다.
 */
public class Main {
    static final int INF = 1_000_000_000;
    static class Point {
        int y;
        int x;
        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static int getDistance(Point p1, Point p2) {
        return Math.abs(p1.y - p2.y) + Math.abs(p1.x - p2.x);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        int N = fun.apply(input[0]);
        int K = fun.apply(input[1]);

        int[][] dp = new int[N][K + 1]; // 몇 개를 뛰어넘었는지를 알아야 하기 떄문에
        int[][] graph = new int[N][N];
        Point[] points = new Point[N];

        for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");
            Arrays.fill(dp[i], INF);
            points[i] = new Point(fun.apply(input[0]), fun.apply(input[1]));
        }

        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                graph[i][j] = getDistance(points[i], points[j]); // 두 점의 거리를 간선화함
            }
        }

        dp[0][0] = 0; // 첫번째 지점까지 가는데의 거리
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i && j <= K; j++) {
                if (j == 0) { // j == 0 일 때에는 그냥, 앞에것에다가 더해서 넣어야함
                    dp[i][j] = dp[i - 1][j] + graph[i - 1][i];
                } else { // 이제 dp 를 적용할 수 있음
                    for (int c = 0; c <= j; c++) { // 직접 0 개를 건너 뛰는 경우부터 j 개를 건너 뛰는 경우까지
                        dp[i][j] = Math.min(dp[i][j], dp[i - c - 1][j - c] + graph[i - c - 1][i]);
                    }
                }
            }
        }

        System.out.println(dp[N - 1][K]);
    }

    static void mapPrint(int N, int K, int[][] dp) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= K; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
    }
}
