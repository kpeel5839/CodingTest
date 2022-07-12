import java.util.*;
import java.io.*;
import java.util.function.Function;

// 20168 : 골목 대장 호석 - 기능성

/**
 * -- 전제조건
 * 이 문제는 딱 보자말자 다익스트라 느낌이 왔음
 * 골목 대장 호석짱은 바보 같이 맨날 골목을 지키고 있다.
 *
 * 그래서 골목을 지나는데에 조건이 있다.
 * 일단 우리는 호석이에게 돈을 최대한 적게 뜯기는게 관건이 아니라
 * 한번에 많은 돈을 뜯기지 않는 것이다.
 *
 * 근데, 내가 가지고 있는 돈이 적다면?
 * 돈을 한번에 많이 뜯기더라도, 돈에 맞게 뜯기게 가야한다.
 *
 * 그러면 그냥 다익스트라로 진행하면 될 것 같다.
 * -- 틀 설계
 * 다익스트라이지만, 중간에 끝내지 않는 다익스트라이다.
 * 하지만, 이제 find 연산을 통해서 경로를 거슬러 올라가며, 거기서 최대값을 계속 갱신하는 것은 분명히 필요해 보인다.
 *
 * 그래서 PriorityQueue 와 dist 를 사용하여
 * 최단거리부터 순서대로 거슬러 올라가는 것이 이 문제의 핵심인 것 같다.
 *
 * -- 해맸던 점
 * 처음에 그냥 parent 를 이용해서 접근하니까
 * 경로가 중간에 꼬여버려서 제대로 나오지 않았다
 * 어떻게 보면 당연한 결과였다.
 *
 * 왜냐하면, 계속 해서 목적지에 도달하게 되면 바로 종료를 해야 하는데
 * 그게 아니라 목적지에 도달할 떄마다, 경로를 추적하고 또 경로를 바꾸고 하니까
 * 경로가 제대로 나올 수가 없었다.
 *
 * 그래서 Point class 에다가 책임을 주어 지나온 경로의 max 값을 가지고 있을 수 있도록 하였다.
 * 그랬더니 문제의 정답을 구할 수 있었다.
 *
 * 하지만, 남은 문제는 시간 초과였다.
 * 당연한 결과 였다. 최대의 경우로는 간선의 cost 가 1, 그리고 C == 10000 일 때이다.
 * 그러면 10000번 움직일 수 있는 경로가 수없이 존재하는 것이니 당연히 초과가 날 수밖에 없다.
 * 그래서 class 에 visited 를 주어서
 * 독단적으로 visited 처리를 할 수 있도록 하였다.
 *
 * 더 좋은 방법이 있겠지만, 귀찮아서 그냥 이렇게 했다.
 * 그래서 이런식으로 문제들을 해결하여 문제를 결국 풀 수 있었다.
 */
public class Main {
    static int N;
    static int M;
    static int S;
    static int E;
    static int C;
    static int ans = Integer.MAX_VALUE;
    static int[] parent;
    static int[] dist;
    static int[][] map;

    static class Point {
        int v;
        int cost;
        int max;
        boolean[] visited;

        Point(int v, int cost, int max, boolean[] visited) {
            this.v = v;
            this.cost = cost;
            this.max = max;
            this.visited = visited;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        M = fun.apply(input[1]);
        S = fun.apply(input[2]);
        E = fun.apply(input[3]);
        C = fun.apply(input[4]);

        dist = new int[N + 1];
        parent = new int[N + 1];
        map = new int[N + 1][N + 1];

        for (int i = 0; i < M; i++) {
            input = br.readLine().split(" ");
            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);
            int c = fun.apply(input[2]);

            map[a][b] = c;
            map[b][a] = c;
        }

        // 이제 다익스트라 진행하면 된다.
        PriorityQueue<Point> queue = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
        queue.add(new Point(S, 0, 0, new boolean[N + 1])); // 시작 지점 넣어준다.

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            if (point.cost > C) {
                continue;
            }

            if (point.v == E) {
                ans = Math.min(ans, point.max);
                continue;
            }

            for (int i = 0; i < map[point.v].length; i++) {
                if (i != point.v && map[point.v][i] != 0 && !point.visited[i]) {
                    parent[i] = point.v;
                    boolean[] innerVisited = point.visited.clone();
                    innerVisited[i] = true;
                    queue.add(new Point(i, point.cost + map[point.v][i], Math.max(point.max, map[point.v][i]), innerVisited));
                }
            }
        }

        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }
}
