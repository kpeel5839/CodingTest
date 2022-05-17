import java.util.*;
import java.io.*;
import java.util.function.Function;

// 17472 : 다리만들기 2

/**
 * -- 전제조건
 * 섬이 존재하고 섬은 개수는 2 <= 섬 <= 6 이런 식으로 존재할 수 있다.
 *
 * 섬과 섬을 이을 때는 방향이 꺾여서는 안되며
 * 무조건 일직선과 일직선으로 이어져야 한다.
 *
 * 그리고, 무조건 다리의 길이가 2 이상이여야 한다.
 * 그리고 다리가 겹치더라도 그냥 겹친 부분에 대한 cost 는 따로 생각하지 않는다.
 *
 * 그래서 0 과 1 로 이루어진 맵이 주어졌을 때
 * 섬과 섬으로 갈 수 있도록 다리를 건설할 때의, 최소비용을 출력하여라.
 *
 * -- 틀 설계
 * 이 문제는 최소비용트리를 이용하는 문제이다.
 * 그래서 일단 서순은 이렇게 해야할 것 같다.
 * 일단 먼저 섬을 찾는다 (그러면서 개수도 Count 해준다.)
 *
 * 섬과, 섬간의 다리를 연결해주기 위해서 각 섬에서의 연결할 수 있는 다리들을 전부 연결한다.
 *
 * 그리고 그 간선들의 정보를 가지고서
 * 최소비용트리를 만든다.
 *
 * 그 다음에, 모든 섬들이 부모를 다 똑같이 가지면 최소비용트리로 형성이 된 것이고
 * 그렇지 않다면 최소비용 트리가 아니다.
 *
 * 그렇기 떄문에 이 경우는 불가능하다고 판단 -1 로 출력을 진행하면 된다.
 *
 * 최소 비용의 간선을 선택하기 떄문에, 만일 A, B 섬을 잇는 간선이 있는데, 이것이 선택이 안되어서 트리를 형성하지 못하는 경우?
 * 그런 경우는 존재하지 않는다, 애초에 최소 비용으로 부터 간선을 탐색하는 것일 뿐, 결국 해당 섬과 섬의 간선이 하나 이상 존재하기만 한다면
 * 무조건적으로 선택되기 떄문이다.
 *
 * -- 해맸던 점
 * distance 를 이상한데서 증가시켜서
 * 실제 다리 길이가 1이여도, 하나 더 증가된 2로 처리되어서, 거리가 1이 떨어진 곳도 다리로 선택되게 됐었음
 *
 * 그리고 또 가장 큰 실수는 map 에다가 섬 번호를 기록할 때,
 * 시작하는 map[y][x] 를 모르고 섬 번호를 기록 안해서 1번으로 그대로 있는 문제가 있었음
 *
 * 해당 문제를 고치니까 바로 답이 잘 나왔음
 *
 * 또 해맸던 점은, 중간에 모르고 출력문 안지워가지고 선택되는 Edge 정보가 계속 나왔었음
 */
public class Main {
    static int H;
    static int W;
    static int res = 0;
    static int islandCount = 0;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[] parent;
    static int[][] map;
    static boolean[][] visited;
    static List<Edge> edges = new ArrayList<>();

    static class Edge {
        int sta;
        int des;
        int cost;

        Edge(int sta, int des, int cost) {
            this.sta = sta;
            this.des = des;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "sta : " + sta + " des : " + des + " cost : " + cost;
        }
    }

    static int find(int a) {
        if (parent[a] == a) { // 현재 부모가 본인이면
            return a;
        } else {
            parent[a] = find(parent[a]); // 일단 본인의 부모 값으로 재귀적으로 찾고
            return parent[a]; // 반환 받은 부모 값을 반환한다.
        }
    }

    static void union(int a, int b) {
        parent[b] = a; // b 를 a 집합에 포함시킨다.
    }

    static void bfs(int y, int x) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {y, x});
        visited[y][x] = true;

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            map[point[0]][point[1]] = islandCount;

            for (int i = 0; i < 4; i++) {
                int ny = point[0] + dy[i];
                int nx = point[1] + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx] || map[ny][nx] != 1) {
                    continue;
                }

                visited[ny][nx] = true;
                queue.add(new int[] {ny, nx});
            }
        }
    }

    static void makeEdge() {
        // Edge 를 만드는 부분
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j] != 0) { // 어떤 섬인 경우
                    for (int c = 0; c < 4; c++) {
                        findEdge(i, j, c);
                    }
                }
            }
        }
    }

    static void findEdge(int y, int x, int dir) {
        /**
         * 진짜 Edge 를 연결하는 부분
         * 처음에 y, x 좌표에서 본인이 몇번째 섬인지 얻어오고
         * 그 다음에, 해당 좌표에서 일직선으로 향했을 때, 다른 섬에 도달하는 경우 그 섬의 번호를 이용해
         * Edge 를 추가하면 된다.
         */

        int sta = map[y][x]; // 시작하는 섬 번호 지정
        int des = 0; // 목적지 변수 선언

        int distance = 0;
        while (true) {
            // y, x 를 계속 변경하면서 distance 를 변경해가면서, outOfRange 인지
            // 혹은 섬인지를 계속 확인해야 한다.
            y += dy[dir];
            x += dx[dir];

            if (outOfRange(y, x) || map[y][x] == sta) { // 해당 방향으로 뻗었는데, 본인 섬의 일부를 만나면 그건 안되는 것, 범위를 벗어나는 경우도 끝내줌
                break;
            }

            if (map[y][x] != 0 && map[y][x] != sta) { // 본인 섬과 다른 것을 만나면
                des = map[y][x];
                break;
            }

            distance++; // distance 증가, 해당 칸이 아무것도 아닌 0이라는 것을 증명해내야지 distance 가 증가되어야 하기 때문에, 맨 마지막에 위치시킨다.
        }

        if (des != 0 && distance >= 2) { // des 가 결정되어 있고, distance 가 2 이상일 때
            edges.add(new Edge(sta, des, distance));
        }
    }

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]);
        W = fun.apply(input[1]);

        map = new int[H][W];
        visited = new boolean[H][W];

        for (int i = 0; i < H; i++) {
            input = br.readLine().split(" ");
            for (int j = 0; j < W; j++) {
                map[i][j] = fun.apply(input[j]);
            }
        }

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (!visited[i][j] && map[i][j] != 0) { // 방문 아직 안했고, 1인 것
                    islandCount++;
                    bfs(i, j);
                }
            }
        }

        parent = new int[islandCount + 1];

        for (int i = 0; i <= islandCount; i++) { // 모두 본인을 부모로 초기화
            parent[i] = i;
        }

        makeEdge();
        Collections.sort(edges, (o1, o2) -> (o1.cost - o2.cost)); // 오름 차순으로 정렬

        for (Edge edge : edges) {
            int a = find(edge.sta);
            int b = find(edge.des); // edge sta, des 의 집합을 찾아줌

            if (a != b) { // 같은 집합이 아닌 경우만
                res += edge.cost;
                union(a, b); // 서로 같은 집합에 포함시켜주고
            }
        }

        int parentOfParent = find(1);

        for (int i = 2; i <= islandCount; i++) {
            if (parentOfParent != find(i)) {
                res = -1;
            }
        }

        System.out.println(res);
    }

    static void mapPrint() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
