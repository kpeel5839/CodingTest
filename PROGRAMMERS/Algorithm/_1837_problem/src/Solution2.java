import java.util.*;

class Solution2 {

    /**
     * 이 문제는 정말이지 너어무 어려웠다.
     * 결국 답을 봤는데 역시나 내가 어려워하는 다이나믹 프로그래밍이였다.
     *
     * 이 문제의 해결 방안은 이러했다.
     *
     * 일단 당연히 시간 내에 해결하려면 배열로 접근해야 함은 당연했다.
     * dfs 로서 해결하는 것은 말이 안됐었음
     *
     * 그래서 답을 보니, dp[i][j] 가 있을 때, i 는 gps_log 순번을 의미하고
     * j 는 해당 순번에 j 위치가 온다면? 최소 수정 횟수는 몇번일까?
     *
     * 이게 근본이였음
     *
     * 그래서 dp[0] 은 dp[0][gps_log[0]] 만이 0일 수 있다 왜냐하면 나머지는 수정을 아얘 못하는 부분인 것이니까
     * 그래서 이런식으로 진행할 수 있고
     *
     * dp[1] 부터 하면 당연히 dp[1][j] 로 한다고 했을 때 이전 위치에서 j 로 올 수 있는 즉, j 와 연결되어있는
     * 다시 말하면 graph[gps_log[0]][j] 가 연결되어 있어야 한다라는 것이다.
     *
     * 그것을 찾는 방법은 그냥 반복문을 돌면서 graph[j][c] 가 있는지 확인하는 것이다.
     * 하지만 이전에 똑같은 정점에 있었을 수도 있으니 graph[j][j] 와 같이 본인이 본인에게 가는 간선도 1로 해서 열어놓는다.
     *
     * 그래서 그런식으로 진행을 하면 당연히 dp[k - 1][gps_log[k - 1]] 이 정답이 될 수 밖에 없다.
     *
     * 그러니까 이 문제를 정리하면 Array 를 다 INF 로 초기화하고 아얘 수정하지 못하는 부분들에 대해서는 INF 로 남겨놓는다.
     * 그리고 해당 위치를 수정한다라고 하면 이전에 본인 정점으로 올 수 있는 놈중에 최소 수정횟수를 가진놈을 가져오고
     * 그 다음에 만일 gps_log[i] 와 다르면 즉 gps_log[i] != j 원래와 다르다면 dp[i][j]++ 을 해주어서
     *
     * 수정횟수를 증가시킨다 (이전에서 가져왔다고 하더라도 본인이 증가되었으니 당연히 1 더해주어야 함)
     *
     * 그리고 마지막에 dp[k - 1][gps_log[k - 1]] 이 INF 이면 어떤 식으로 수정하더라도 도달할 수 없는 경우인 것이고
     * 만일 아니라면 그게 최소 수정횟수이다.
     */
    int INF = 99999999;

    public int solution(int n, int m, int[][] edge_list, int k, int[] gps_log) {

        int[][] graph = new int[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            graph[i][i] = 1;
        }

        for (int i = 0; i < edge_list.length; i++) {
            int a = edge_list[i][0];
            int b = edge_list[i][1];

            graph[a][b] = 1;
            graph[b][a] = 1;
        }

        int[][] dp = new int[k][n + 1];

        for (int i = 0; i < k; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][gps_log[0]] = 0; // 얘는 수정을 못하니께

        for (int i = 1; i < k; i++) { // 택시 위치 수정하는 것들
            for (int j = 1; j <= n; j++) { // 해당 위치에 들어갈 수 있는 위치들               
                for (int c = 1; c <= n; c++) {
                    if (graph[j][c] == 1) { // 길이 있는 경우에만
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][c]); // 현재와 이전 노드에서 오는 경우중 장은 것을 선택
                    }
                }

                if (gps_log[i] != j) {
                    dp[i][j]++;
                }
            }
        }

        if (dp[k - 1][gps_log[k - 1]] < INF) {
            return dp[k - 1][gps_log[k - 1]];
        } else {
            return -1;
        }
    }
}