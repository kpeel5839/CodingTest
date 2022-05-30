import java.util.*;
import java.io.*;
import java.util.function.Function;

public class Main {
    static int N;
    static boolean[] visited; // 그냥 비효율적이지만 개 쉬운 방법으로 해결하자.
    static int[][] graph;

    static void bfs(int a) {
        visited = new boolean[N];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(a);

        while (!queue.isEmpty()) {
            int b = queue.poll();

            for (int i = 0; i < N; i++) {
                if (graph[b][i] == 1 && !visited[i]) {
                    graph[a][i] = 1;
                    queue.add(i);
                    visited[i] = true;
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        N = fun.apply(br.readLine());
        graph = new int[N][N];

        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                graph[i][j] = fun.apply(input[j]);
            }
        }

        for (int i = 0; i < N; i++) {
            bfs(i);
        }

        mapPrint();
    }

    static void mapPrint() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
    }
}
