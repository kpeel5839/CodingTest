import java.util.*;
import java.io.*;
import java.util.function.Function;

// 5014 : 스타트링크

/**
 * -- 전제 조건
 * 엘리베이터가 있고
 * 총 F 층이 있고, 강호는 S 층에 있다.
 * 그리고 G 층에 갈 예정이다.
 *
 * 그런데, 엘리베이터에 U, D 버튼 밖에 없고
 * U 는 위로 U 만큼 올라가는 것이고
 * D 는 아래로 D 만큼 내려가는 것이다.
 *
 * 이런 전제조건일 때, F, S, G, U, D 가 주어지고
 * 최소 몇번만에 도착할 수 있는지, 혹은 도착하지 못하면 use the stairs 를 출력하라.
 * -- 틀 설계
 * 무조건 bfs 인데,
 * 그냥 bfs 이다.
 *
 * 너무 쉬운데?
 * 그냥 visited 처리하면서 이동하면 끝날 것 같은데
 * 범위 나가는 것만 처리해주고
 *
 * -- 해맸던 점
 * 일단, 0층을 모르고 고려했었고
 * D 가 양수로 입력되는 것도 고려 안했었다.
 * 그리고, 무엇보다 res = 0 으로 설정하여서
 * 처음부터 G 층에 도달해있을 때를 고려하지 못했었음
 *
 * 그래서 한 5분정도 해맴
 */
public class Main {
    static int F;
    static int S;
    static int G;
    static int U;
    static int D;
    static int res = Integer.MAX_VALUE;
    static int[] move;
    static int[] path;
    static boolean[] visited;
    static Queue<Point> queue = new LinkedList<>();

    static class Point {
        int floor;
        int value;

        Point(int floor, int value) {
            this.floor = floor;
            this.value = value;
        }
    }

    static void bfs() {
        // U, D 를 이용해서 G 에 도달하는 것이 목표이다.
        while (!queue.isEmpty()) {
            Point point = queue.poll();

            if (point.floor == G) {
                res = point.value;
                return;
            }

            for (int i = 0; i < 2; i++) {
                int nFloor = point.floor + move[i]; // 0 up, 1 down

                if (outOfRange(nFloor) || visited[nFloor]) {
                    continue;
                }

                queue.add(new Point(nFloor, point.value + 1));
                visited[nFloor] = true;
                path[nFloor] = point.floor;
            }
        }
    }

    static void pathTrace(int a) {
        if (path[a] == a) {
            System.out.print("시작 : " + a + " -> ");
        } else {
            pathTrace(path[a]);
            if (a != G) {
                System.out.print(a + " -> ");
            } else {
                System.out.println(a);
            }
        }
    }

    static boolean outOfRange(int floor) { // 범위를 넘어갔을 때
        if (floor <= 0 || floor > F) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        F = fun.apply(input[0]);
        S = fun.apply(input[1]);
        G = fun.apply(input[2]);
        U = fun.apply(input[3]);
        D = fun.apply(input[4]);

        move = new int[] {U, -D};
        visited = new boolean[F + 1];
        path = new int[F + 1];
        queue.add(new Point(S, 0));
        visited[S] = true;

//        for (int i = 0; i < path.length; i++) {
//            path[i] = i;
//        }

        bfs();
//        pathTrace(G);

        System.out.println((res == Integer.MAX_VALUE) ? "use the stairs" : res);
    }
}