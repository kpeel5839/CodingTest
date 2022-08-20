import java.util.*;
import java.io.*;

class Solution {
    static List<ArrayList<Edge>> graph = new ArrayList<>();
    static HashSet<Integer> gate = new HashSet<>();
    static HashSet<Integer> summit = new HashSet<>();
    static Mount[] dp;
    static final int INF = 1_000_000_000;

    static class Edge {
        int a;
        int cost;

        Edge(int a, int cost) {
            this.a = a;
            this.cost = cost;
        }
    }

    static class Mount {
        int num;
        int value;

        Mount (int num, int value) {
            this.num = num;
            this.value = value;
        }
    }

    static Mount dfs(int c) {
        if (summit.contains(c)) {
            return new Mount(c, 0);
        }

        if (dp[c].value != INF) {
            return dp[c];
        }

        dp[c].value = INF + 1;

        for (Edge edge : graph.get(c)) {
            if (!gate.contains(edge.a)) {
                Mount tmp = dfs(edge.a);
                Mount res = new Mount(tmp.num, tmp.value);
                res.value = Math.max(res.value, edge.cost);

                if (dp[c].value >= res.value) {
                    if (dp[c].value == res.value) {
                        if (dp[c].num > res.num) {
                            dp[c] = new Mount(res.num, res.value);
                        }
                    } else {
                        dp[c] = new Mount(res.num, res.value);
                    }
                }
            }
        }

        return dp[c];
    }

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        dp = new Mount[n + 1];

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
            dp[i] = new Mount(0, INF);
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

        Mount res = new Mount(0, INF);

        for (Integer start : gate) {
            Mount tmp = dfs(start);

            if (res.value >= tmp.value) {
                if (res.value == tmp.value) {
                    if (res.num > tmp.num) {
                        res = tmp;
                    }
                } else {
                    res = tmp;
                }
            }
        }

        return new int[] {res.num, res.value};
    }
}