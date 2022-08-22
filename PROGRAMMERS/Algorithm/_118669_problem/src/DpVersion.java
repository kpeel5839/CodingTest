import java.util.*;

class DpVersion {
    static List<ArrayList<Edge>> graph = new ArrayList<>();
    static HashSet<Integer> gate = new HashSet<>();
    static HashSet<Integer> summit = new HashSet<>();
    static int[][] dp;
    static final int INF = 1_000_000_000;

    static class Edge {
        int a;
        int cost;

        Edge(int a, int cost) {
            this.a = a;
            this.cost = cost;
        }
    }

    static int[] dfs(int c) {
        if (summit.contains(c)) {
            return new int[] {c, 0};
        }

        if (dp[c][1] != INF) {
            return dp[c];
        }

        dp[c][1] = INF + 1;

        for (Edge edge : graph.get(c)) {
            if (!gate.contains(edge.a)) {
                int[] tmp = dfs(edge.a);
                int[] res = new int[] {tmp[0], tmp[1]};
                res[1] = Math.max(res[1], edge.cost);

                if (dp[c][1] >= res[1]) {
                    if (dp[c][1] == res[1]) {
                        if (dp[c][0] > res[0]) {
                            dp[c] = res;
                        }
                    } else {
                        dp[c] = res;
                    }
                }
            }
        }

        return dp[c];
    }

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        dp = new int[n + 1][2];

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
            dp[i][1] = INF;
        }

        for (int i = 0; i < gates.length; i++) {
            gate.add(gates[i]); // gate 추가
        }

        for (int i = 0; i < summits.length; i++) {
            summit.add(summits[i]);
        }

        for (int i = 0; i < paths.length; i++) {
            graph.get(paths[i][0]).add(new Edge(paths[i][1], paths[i][2]));
            graph.get(paths[i][1]).add(new Edge(paths[i][0], paths[i][2]));
        }

        int[] res = new int[] {0, INF};

        for (Integer start : gate) {
            int[] tmp = dfs(start);

            if (res[1] >= tmp[1]) {
                if (res[1] == tmp[1]) {
                    if (res[0] > tmp[0]) {
                        res = tmp;
                    }
                } else {
                    res = tmp;
                }
            }
        }

        return res;
    }
}