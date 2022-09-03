import java.util.*;
import java.io.*;

class Solution {
    static List<ArrayList<Integer>> graph = new ArrayList<>();
    static long[] sum;
    static long[] a;
    static boolean[] visited;

    static long dfs(int c) { // a 를 계속 가지고 다니고, 트리를 순회하니 부모, 본인 값을 가지고 다닌다.
        visited[c] = true;

        for (Integer next : graph.get(c)) {
            if (!visited[next]) {
                long res = dfs(next);
                sum[c] = sum[c] + res;
                // sum[c] += dfs(next);
            }
        }

        sum[c] += a[c];
        return sum[c];
    }

    static long bfs() {
        Queue<Integer> queue = new LinkedList<>();
        visited = new boolean[sum.length];
        visited[0] = true;
        queue.add(0);
        long res = 0;

        while (!queue.isEmpty()) {
            int v = queue.poll();

            for (Integer next : graph.get(v)) {
                if (!visited[next]) { // 아직 방문하지 않은 애들
                    // 다음 애의 sum 값을 그대로 a[v] 에다가 더해주고, 또 a[next] 에는 뺴주고, res 에 계속 abs 로 더해줌
                    a[v] += sum[next];
                    a[next] -= sum[next];
                    res += Math.abs(sum[next]);
                    visited[next] = true;
                    queue.add(next);
                }
            }

            if (a[v] != 0) {
                return -1;
            }
        }

        return res;
    }

    public long solution(int[] d, int[][] edges) {
        // 트리 형성해야함
        sum = new long[d.length];
        a = new long[d.length];

        for (int i = 0; i < d.length; i++) {
            graph.add(new ArrayList<>());
            a[i] = (long) d[i];
        }

        for (int i = 0; i < edges.length; i++) {
            int b = edges[i][0];
            int c = edges[i][1];

            graph.get(b).add(c);
            graph.get(c).add(b);
        }

        visited = new boolean[a.length];

        if (dfs(0) != 0) {
            return -1;
        }

        return bfs();
    }
}