import java.util.*;
import java.io.*;

// 2637 : 장난감 조립

/**
 * 예제
 * 66
 * 100
 * 14 11 1
 * 27 13 1
 * 2 27 1
 * 46 38 1
 * 65 3 1
 * 51 33 1
 * 51 50 1
 * 6 62 1
 * 2 53 1
 * 29 2 1
 * 36 55 1
 * 27 25 1
 * 1 63 1
 * 11 50 1
 * 51 15 1
 * 61 26 1
 * 66 44 1
 * 54 61 1
 * 64 57 1
 * 36 22 1
 * 57 54 1
 * 7 45 1
 * 35 46 1
 * 1 35 1
 * 55 40 1
 * 62 1 1
 * 24 39 1
 * 11 51 1
 * 8 4 1
 * 65 31 1
 * 59 5 1
 * 51 58 1
 * 11 15 1
 * 23 59 1
 * 59 30 1
 * 16 6 1
 * 34 7 1
 * 45 42 1
 * 16 62 1
 * 13 25 1
 * 26 43 1
 * 47 23 1
 * 42 41 1
 * 3 47 1
 * 40 16 1
 * 48 36 1
 * 41 32 1
 * 5 36 1
 * 9 14 1
 * 53 27 1
 * 25 64 1
 * 63 35 1
 * 14 37 1
 * 57 61 1
 * 56 7 1
 * 8 56 1
 * 9 28 1
 * 12 8 1
 * 49 59 1
 * 31 3 1
 * 5 48 1
 * 23 49 1
 * 66 17 1
 * 56 34 1
 * 51 19 1
 * 22 55 1
 * 11 18 1
 * 4 56 1
 * 10 8 1
 * 35 38 1
 * 25 57 1
 * 61 21 1
 * 38 24 1
 * 41 65 1
 * 28 14 1
 * 21 26 1
 * 11 33 1
 * 51 18 1
 * 55 16 1
 * 11 19 1
 * 30 5 1
 * 11 58 1
 * 45 41 1
 * 43 9 1
 * 7 20 1
 * 38 39 1
 * 39 2 1
 * 39 29 1
 * 26 9 1
 * 52 1 1
 * 17 44 1
 * 62 52 1
 * 60 12 1
 * 20 45 1
 * 44 60 1
 * 44 12 1
 * 37 11 1
 * 32 65 1
 * 12 10 1
 * 3 23 1
 */
public class Main {
    static int N;
    static int M;
    static int[] need;
    static int[] entry;
    static boolean[] notLeaf;
    static List<ArrayList<Edge>> graph = new ArrayList<>();

    static class Edge {
        int a;
        int cost;

        Edge(int a, int cost) {
            this.a = a;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2637_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        need = new int[N + 1];
        entry = new int[N + 1];
        notLeaf = new boolean[N + 1];

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(x).add(new Edge(y, cost));
            entry[y]++;
            notLeaf[x] = true;
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(N);
        need[N] = 1;

        while (!queue.isEmpty()) {
            int v = queue.poll();

            for (Edge edge : graph.get(v)) {
                if (--entry[edge.a] == 0) {
                    queue.add(edge.a);
                }

                need[edge.a] += need[v] * edge.cost;
            }
        }

        for (int i = 1; i <= N; i++) {
            if (!notLeaf[i]) {
                bw.write(i + " " + need[i] + "\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
