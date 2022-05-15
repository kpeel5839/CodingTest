import java.util.*;
import java.io.*;
import java.util.function.Function;

// 17471 : 게리맨더링

/**
 * -- 전제조건
 * 각 구역이 존재하고 인접한 구역들은, 선으로 연결되어 있음
 * 그것들은 각 구역의 개수인 N 이 주어지고
 * 구역의 사람들은 People(i) 로 순서대로 주어진다.
 *
 * 그리고, i 번째 구역과 연결된 구역의 개수와, 연결된 애들이 주어진다.
 *
 * 이럴 때, 구역을 2 선거구로 나누는데, 두 선거구의 인원 차가 최소가 되게끔 하라.
 * 그리고, 그 값을 출력하라
 *
 * 만일 두 선거구로 나눌 수 없는 경우에는 -1을 출력하면 된다.
 * -- 틀 설계
 * 일단 브루트 포스 느낌으로다가 dfs 로 각 구역구마다 포함된 선거구를 정해준다.
 * 그 다음에, bfs 를 통해서, 해당 선거구로 나눴을 때, 서로 인접한 상황인지를 판단한다.
 */
public class Main {
    static int N;
    static int[] count;
    static int[] area;
    static int[] res = new int[2];
    static int ans = Integer.MAX_VALUE;
    static boolean[] visited;
    static List<ArrayList<Integer>> graph = new ArrayList<>();

    static boolean check() {
        int[] start = returnStartArea(); // 선거구를 얻어줌
        visited = new boolean[N + 1];

        Queue<Integer> a = new LinkedList<>();
        Queue<Integer> b = new LinkedList<>();

        a.add(start[0]); // 0 번 선거구
        b.add(start[1]); // 1 번 선거구

        visited[start[0]] = true;
        visited[start[1]] = true; // 방문처리
        int totalA = 0; // 0번 선거구 총 인구수
        int totalB = 0; // 1번 선거구 총 인구수

        while (!a.isEmpty()) {
            int sta = a.poll();
            totalA += count[sta];

            for (Integer next : graph.get(sta)) {
                if (!visited[next] && area[next] == 0) { // 아직 방문하지 않았을 때, 같은 선거구일 때
                    a.add(next);
                    visited[next] = true;
                }
            }
        }

        while (!b.isEmpty()) {
            int sta = b.poll();
            totalA += count[sta];

            for (Integer next : graph.get(sta)) {
                if (!visited[next] && area[next] == 0) { // 아직 방문하지 않았을 때, 같은 선거구일 때
                    a.add(next);
                    visited[next] = true;
                }
            }
        }

        if (checkVisited()) { // 유효한 선거구일 때
            res[0] = totalA;
            res[1] = totalB;
            return true;
        } else {
            return false;
        }
    }

    static boolean checkVisited() {
        for (int i = 1; i <= N; i++) {
            if (!visited[i]) { // 방문하지 않은 정점이 있으면, 유효한 선거구가 아님
                return false;
            }
        }

        return true;
    }

    static int[] returnStartArea() {
        int[] startArea = new int[2];

        for (int i = 1; i <= N; i++) {
            if (startArea[area[i]] == 0) { // 아직 startArea 가 정해지지 않은 선거구면
                startArea[area[i]] = i; // start 할 선거구를 집어넣어줌
            }
        }

        return startArea;
    }

    static void dfs(int index) {
        if (index == area.length) { // 끝까지 할당 하면
            if (check() && seperateCheck()) { // 유효한 선거구이다
                ans = Math.min(ans, Math.abs(res[0] - res[1]));
            }

            System.out.println(Arrays.toString(area));
            return;
        }

        for (int i = 0; i < 2; i++) {
            area[index] = i;
            dfs(index + 1);
        } // 0 혹은 1을 넣고 (선거구의 번호) 진행
    }

    static boolean seperateCheck() {
        int value = area[1];

        for (int i = 2; i <= N; i++) {
            if (value != area[i]) { // 서로 다른 선거구가 있다? 그러면 옳은 것
                return true;
            }
        }

        return false; // 아니면 안됨
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        N = fun.apply(br.readLine());
        area = new int[N + 1];
        count = new int[N + 1];

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        String[] input = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) {
            count[i] = fun.apply(input[i - 1]);
        }

        for (int i = 1; i <= N; i++) {
            input = br.readLine().split(" ");
            int connect = fun.apply(input[0]); // connect 개수

            for (int j = 0; j < connect; j++) {
                int next = fun.apply(input[j + 1]);

                graph.get(i).add(next); // 간선 추가
            }
        }

        dfs(1); // 1번째 부터 시작임

        System.out.println((ans == Integer.MAX_VALUE) ? -1 : ans);
    }
}

