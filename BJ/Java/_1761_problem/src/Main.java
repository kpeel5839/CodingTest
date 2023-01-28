import java.util.*;
import java.io.*;

// 1761 : 정점들의 거리

/**
 * Example
 * 7
 * 1 6 13
 * 6 3 9
 * 3 5 7
 * 4 1 3
 * 2 4 20
 * 4 7 2
 * 3
 * 1 6
 * 1 4
 * 2 6
 */
public class Main {
    public static int[][] value;
    public static int[][] parent;
    public static int[] depth;
    public static List<ArrayList<int[]>> graph = new ArrayList<>();

    public static void makeParent() {
        for (int i = 1; i < parent.length; i++) {
            for (int j = 1; j < parent[i].length; j++) {
                parent[i][j] = parent[i - 1][parent[i - 1][j]];
                value[i][j] = value[i - 1][j] + value[i - 1][parent[i - 1][j]];
            }
        }
    }

    public static void makeDepth(int previous, int current, int nowDepth) {
        depth[current] = nowDepth;

        for (int[] next : graph.get(current)) {
            if (previous != next[0]) {
                value[0][next[0]] = next[1];
                parent[0][next[0]] = current;
                makeDepth(current, next[0], nowDepth + 1);
            }
        }
    }

    public static int lca(int firstNode, int secondNode) {
        if (depth[firstNode] < depth[secondNode]) {
            int temp = firstNode;
            firstNode = secondNode;
            secondNode = temp;
        }

        int distance = 0;
        for (int i = parent.length - 1; i != -1; i--) {
            if (depth[firstNode] - depth[secondNode] >= (1 << i)) {
                distance += value[i][firstNode];
                firstNode = parent[i][firstNode];
            }
        }

        if (firstNode == secondNode) {
            return distance;
        }

        for (int i = parent.length - 1; i != -1; i--) {
            if (parent[i][firstNode] != parent[i][secondNode]) {
                distance += value[i][firstNode];
                distance += value[i][secondNode];
                firstNode = parent[i][firstNode];
                secondNode = parent[i][secondNode];
            }
        }

        return distance + value[0][firstNode] + value[0][secondNode];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1761_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int height = (int) Math.floor(Math.log(N - 1) / Math.log(2)) + 1;
        parent = new int[height][N + 1];
        value = new int[height][N + 1];
        depth = new int[N + 1];

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int firstNode = Integer.parseInt(st.nextToken());
            int secondNode = Integer.parseInt(st.nextToken());
            int price = Integer.parseInt(st.nextToken());

            graph.get(firstNode).add(new int[] {secondNode, price});
            graph.get(secondNode).add(new int[] {firstNode, price});
        }

        makeDepth(-1, 1, 1);
        makeParent();

        int query = Integer.parseInt(br.readLine());
        for (int i = 0; i < query; i++) {
            st = new StringTokenizer(br.readLine());

            int firstNode = Integer.parseInt(st.nextToken());
            int secondNode = Integer.parseInt(st.nextToken());
            bw.write(lca(firstNode, secondNode) + "\n");
        }

        bw.flush();
        bw.close();
    }
}
