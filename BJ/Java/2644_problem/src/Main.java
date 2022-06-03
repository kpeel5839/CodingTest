import java.util.*;
import java.io.*;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int V = fun.apply(br.readLine());
        String[] input = br.readLine().split(" ");
        List<ArrayList<Integer>> graph = new ArrayList<>();

        for (int i = 0; i <= V; i++) {
            graph.add(new ArrayList<>());
        }

        int sta = fun.apply(input[0]);
        int des = fun.apply(input[1]);
        int repeat = fun.apply(br.readLine());

        for (int i = 0; i < repeat; i++) {
            input = br.readLine().split(" ");
            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);

            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        boolean[] visited = new boolean[V + 1];
        Queue<int[]> queue = new LinkedList<>();
        visited[sta] = true;
        queue.add(new int[] {sta, 0});
        int res = -1;

        while (!queue.isEmpty()) {
            int[] point = queue.poll();

            if (point[0] == des) {
                res = point[1];
                break;
            }

            for (Integer next : graph.get(point[0])) {
                if (!visited[next]) {
                    queue.add(new int[] {next, point[1] + 1});
                    visited[next] =true;
                }
            }
        }

        System.out.println(res);
    }
}
