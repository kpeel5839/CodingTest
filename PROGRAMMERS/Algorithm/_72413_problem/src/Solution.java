import java.util.*;

/**
 * 내가 해맨 점은 두가지의 이유가 있음
 * 첫 째, 아이디어는 굉장히 좋았으나, 플로이드 워샬 알고리즘을 잘 못 사용했음
 * 둘 째, 테스트 차에 진행하던 출력을 안지워서 비교 연산 + System.out.println 시간으로 인해 시간초과가 났음
 *
 * 그래서 첫번째는, 진짜 개 바보같이
 * for (int i = 1; i <= n; i++) for (int j = 1; j <= n; j++) for (int k = 1; k <= n; k++) 이 지랄 하고 있었음
 *
 * 근데 계속 찍어보는데 자꾸 분명히 경로가 있는데 똑바로 나오지 않는 경우들이 생겼음
 * 그래서 다시 생각해보고 인터넷에 찾아본결과
 *
 * 플로이드 워샬의 기본 이념을 송두리째 이상하게 해놨었음
 * 플로이드 워샬이란 K, 즉 중간에 경유지로 설정할 정점을 계속 바꿔가면서 경유지를 포함해 이동하면서, 최단거리를 갱신하거나, 없는 길을 만들어내는 것을 의미함
 *
 * 그렇기 때문에 k == 1 이라고 했을 때, 1 을 경유지로 하게 [i][j] 를 다 돌아야 하는데 위에는 바보같이 말도 안되게 한 거임
 * 그래서 이것 때문에 살짝 해맸고,
 *
 * 그 다음에 테스트 차에 진행하던 출력과, 비교 연산이 발목을 잡았다.
 * 은근 비교 연산이 시간을 많이 잡아먹는 것 같다. 출력은 두말할 것도 없고
 *
 * 그래서 이렇게 해서 정답을 맞출 수 있었다.
 * 나의 예상 중 하나였던, 합승은 처음에만 가능하다라는 추측이 맞았기 때문에 굉장히 문제를 빨리 풀어낼 수 있었다.
 *
 * 만일, 중간에 다시 만나서 합승이 가능했다면 아마 풀지 못했을 것이다.
 */
class Solution {
    /**
     다시 합승이 되지 않는다라는 가정하에
     이러한 접근이 가능해진다.

     일단 플로이드 워샬로 각 정점 간의 최소거리로 간선들의 값을 갱신해준다.
     그 다음에, 처음에만 합승이 가능하다라는 가정하에
     한 지점까지 무조건 같이 간 다음에, 거기서 A, B 를 더해주는 것이다.
     그러면 너무 쉽게 풀릴 것 같은데?

     그리고 요금은 20000만 이하이다. 무조건
     */
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = Integer.MAX_VALUE;
        final int INF = 100_000_000;
        int[][] dp = new int[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }

        for (int i = 0; i < fares.length; i++) {
            int q = fares[i][0];
            int w = fares[i][1];
            int cost = fares[i][2];

            dp[q][w] = cost;
            dp[w][q] = cost;
        }

        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1 ; j <= n; j++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);

                    if (i == j) {
                        dp[i][j] = 0;
                    }
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            if (dp[s][i] == INF || dp[i][a] == INF || dp[i][b] == INF) {
                continue;
            }

            int sum = dp[s][i];
            sum += dp[i][a];
            sum += dp[i][b];

            answer = Math.min(answer, sum);
        }

        return answer;
    }
}