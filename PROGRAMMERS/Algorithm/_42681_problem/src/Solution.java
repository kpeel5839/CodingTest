import java.util.*;
import java.io.*;

class Solution {
    static int[] parent;

    static class Edge {
        int a;
        int b;
        int cost;

        Edge(int a, int b, int cost) {
            this.a = a;
            this.b = b;
            this.cost = cost;
        }
    }

    static int find(int a) {
        if (parent[a] == a) {
            return a;
        } else {
            return parent[a] = find(parent[a]);
        }
    }


    static void union(int a, int b) {
        parent[b] = a;
    }

    public int solution(int n, int[][] costs) {
        List<Edge> edges = new ArrayList<>();
        parent = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < costs.length; i++) {
            edges.add(new Edge(costs[i][0], costs[i][1], costs[i][2]));
        }

        Collections.sort(edges, (o1, o2) -> o1.cost - o2.cost);
        int res = 0;

        for (Edge edge : edges) {
            int a = find(edge.a);
            int b = find(edge.b);

            if (a != b) {
                union(a, b);
                res += edge.cost;
            }
        }

        return res;
    }
}