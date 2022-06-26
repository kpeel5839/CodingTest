import java.util.*;
import java.io.*;
import java.util.function.Function;

// 14466 : 소가 길을 건너간 이유 6

/**
 * -- 전제조건
 * 소가 길을 건너간 이유는 그냥 길이 많아서라고 한다.
 * 존의 농장에는 격자가 있고, 그냥 상하좌우 인접한 위치로 움직일 수 있다.
 *
 * 근데, 소가 있고, 길이 주어질 때,
 * 꼭 길을 건너야만 만나는 쌍, 즉 길을 건니조 못하면 만나지 못하는 쌍의 개수를 출력하시오.
 *
 * -- 틀 설계
 * 이 문제는 그냥 bfs 를 진행하고,
 * 얘가 갈 수 있냐 못 가냐를 체크해놓으면 된다.
 *
 * 그래서 길이 연결되어 있는지 확인할 수 있도록 하기 위해서
 * 3차원의 배열을 만들어서 map[y][x][0] ... map[y][x][3] 를 boolean 형으로 관리하여서 길이 존재하면
 * true 로 하여, 장애물로 인식하고 진행하면 된다.
 *
 * 그래서 내가 지금 목표로 하는 지점으로 갈 수 있냐 못 가냐를 판단하면 된다.
 *
 * 그렇게 하기 위해서 소의 위치도 각각 저장을 해놓아야 하고
 * 하나의 2 2 3 2 이런식으로 주어졌을 때, map[2][2][x] 도 true 로 처리를 해주어야 하고
 * 그리고 map[3][2][x] 도 true 로 처리해주어야 한다.
 *
 * 그러면 두개를 넘겨주어서 방향을 판단해서 각각 해당 지점을 true 로 만들어줄 메소드가 필요하고
 * 그리고 (2, 2), (3, 2) 로도 한번 넘기고 (3, 2), (2, 2) 로도 한번 넘겨서 진행하면 된다.
 *
 * 그러면 문제를 쉽게 해결할 수 있을 것 같다.
 */

public class Main {
    static int N;
    static int K; // cow
    static int R; // road
    static int ans = 0;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static Point[] cow;
    static boolean[][] visited; // 그냥 방문 배열
    static boolean[][][] road; // 길의 위치를 저장

    static class Point { // value 가 필요 없다 도달하는 지 안하는 지만 판단하면 되기 때문에
        int y;
        int x;
        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static void bfs(int start, int end) { // 시작지와 도착지를 체크한다.
        visited = new boolean[N][N]; // 초기화
        Queue<Point> queue = new LinkedList<>();
        Point s = cow[start];
        Point e = cow[end];

        queue.add(new Point(s.y, s.x));
        visited[s.y][s.x] = true;


        while (!queue.isEmpty()) {
            Point point = queue.poll();

            if (point.y == e.y && point.x == e.x) { // 목적지에 도달한 경우
                return; // 아얘 메소드를 끝내준다.
            }

            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx] || road[point.y][point.x][i]) { // 현재 위치에서 내가 가려는 위치가 길이 없는지를 보는 것이다.
                    continue;
                }

                visited[ny][nx] = true;
                queue.add(new Point(ny, nx));
            }
        }

        ans++;
    }

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= N || x < 0 || x >= N) {
            return true;
        } else {
            return false;
        }
    }

    static void calRoad(Point p1, Point p2) {
        // p1 을 기준으로 p2 가 어디있는지 판단해서 거기다가 길을 채워넣는다.
        for (int i = 0; i < 4; i++) {
            int ny = p1.y + dy[i];
            int nx = p1.x + dx[i];

            if (outOfRange(ny, nx)) {
                continue;
            }

            if (ny == p2.y && nx == p2.x) { // 무조건 인접한 것들이 주어지니 이렇게 하면 된다.
                road[p1.y][p1.x][i] = true; // 해당 위치에 길이 존재하는 것이니까 이렇게 true 를 넣어준다.
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");

        N = fun.apply(input[0]);
        K = fun.apply(input[1]);
        R = fun.apply(input[2]);

        cow = new Point[K];
        road = new boolean[N][N][4];

        for (int i = 0; i < R; i++) {
            input = br.readLine().split(" ");

            int a = fun.apply(input[0]) - 1;
            int b = fun.apply(input[1]) - 1;
            int c = fun.apply(input[2]) - 1;
            int d = fun.apply(input[3]) - 1;

            calRoad(new Point(a, b), new Point(c, d));
            calRoad(new Point(c, d), new Point(a, b));
        }

        for (int i = 0; i < K; i++) {
            input = br.readLine().split(" ");
            int a = fun.apply(input[0]) - 1;
            int b = fun.apply(input[1]) - 1;
            cow[i] = new Point(a, b);
        }

        for (int i = 0; i < K - 1; i++) {
            for (int j = i + 1; j < K; j++) {
                bfs(i, j);
            }
        }

        System.out.println(ans);
    }
}