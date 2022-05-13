import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1726 : 로봇

/**
 * -- 전제 조건
 * 로봇이 방향을 돌리는 것,
 * 움직이는 것
 * 그래서 가능한 명령은 Go k 가 있고, 그리고 Turn Dir 이 존재한다.
 * 그 다음에, 이제 맵이 주어지고, 두 지점이 주어졌을 때,
 * 다른 지점으로 가고 방향까지 똑같이 하기 까지, 최소의 명령횟수를 출력하시오.
 *
 * -- 틀 설계
 * 일단, Go 1, 2, 3 까지만 가능하다.
 * 그리고, Turn Dir 은 왼쪽, 오른쪽으로만 가능하다.
 *
 * 그래서 로봇에게 가능한 경우의 수는
 * 현재 방향으로 1칸, 2칸, 3칸 가는 연산
 * 혹은 Dir 을 바꿔서 입력하는 것이다.
 *
 * 그래서, visited 배열을 3차원으로 만들어서
 * visited[y][x][Dir] 로 지정하여서 해당 Dir 을 가지고 (y, x) 좌표에 방문하였다면 제거하면 될 듯하다.
 *
 * 그렇게 해서 만들어야 할 메소드는
 * 일단, bfs 밖에 없을 것 같다.
 *
 * 1칸, 2칸, 3칸 이동하는 것은 for (int i = 0; i < 3; i++) 를 이용해서
 * 직접적으로 ny, nx 를 1, 2, 3칸 씩 이동하면서 장애물이 있다라면?
 * 거기서 바로 반복을 끝내면 된다.
 *
 * 그리고, 방향을 회전하는 것은 그냥 -1, +1 해주는데, 그냥 method 를 하나 더 만들어서
 * 해당 값을 넘겼을 때 정상적인 방향을 반환할 수 있도록 하자.
 *
 * 그리고 여기서 말하는 동쪽은 1, 서쪽이 2, 남쪽이 3, 북쪽이 4
 * 내가 하는 것은 동쪽은 1, 서쪽은 3, 남쪽은 2, 북쪽은 0 이다.
 *
 * 그리고 항상 이동이 가능한 경우만 주어지기 때문에, 딱히 도착하지 못하는 경우는 고려하지 않아도 된다.
 */
public class Main {
    static int H;
    static int W;
    static int res = 0;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[][] map;
    static boolean[][][] visited;
    static Point sta;
    static Point des;

    static class Point {
        int y;
        int x;
        int dir;
        int value;

        Point(int y, int x, int dir) {
            this.y = y;
            this.x = x;
            this.dir = dir;
        }

        Point(int y, int x, int dir, int value) {
            this(y, x, dir);
            this.value = value;
        }

        @Override
        public String toString() {
            return "y : " + y + " x : " + x + " dir : " + dir;
        }
    }

    public static int calDir(int dir) {
        return (dir < 0) ? 3 : dir % 4;
    }

    public static int translateDir(int dir) {
        return (dir == 2) ? 3
                : (dir == 3) ? 2
                : (dir == 4) ? 0 : 1;
    }

    public static void bfs() {
        Queue<Point> queue = new LinkedList<>();
        visited[sta.y][sta.x][sta.dir] = true;

        queue.add(new Point(sta.y, sta.x, sta.dir, 0)); // 처음 시작 집어넣기

        while (!queue.isEmpty()) {
            Point point = queue.poll();

//            System.out.println(point);

            if (point.y == des.y && point.x == des.x && point.dir == des.dir) { // 좌표, 방향까지 같으면 끝
                res = point.value;
                return;
            }

            for (int i = 1; i <= 3; i++) {
                int ny = point.y + (i * dy[point.dir]);
                int nx = point.x + (i * dx[point.dir]); // 현재 방향대로 i 칸 만큼 움직임, 중간에 map[ny][nx] 에 박거나, 혹은 outOfRange 에 걸리면 바로 나가리

                if (outOfRange(ny, nx) || map[ny][nx] == 1) {
                    break;
                }

                if (visited[ny][nx][point.dir]) { // 이것도 그냥 break 해도 될 것 같은데, 혹시 모르니까 visited 는 그냥 continue 하자
                    continue;
                }

                queue.add(new Point(ny, nx, point.dir, point.value + 1));
                visited[ny][nx][point.dir] = true;
            }

            // 이제 여기서는 방향 전환
            for (int i = -1; i <= 1; i += 2) { // 방향 두개만 진행
                int dir = calDir(point.dir + i); // 현재 dir 에서, 왼쪽이나 오른쪽으로 방향을 돌린 경우를 계산

                if (visited[point.y][point.x][dir]) {
                    continue;
                }

                queue.add(new Point(point.y, point.x, dir, point.value + 1));
                visited[point.y][point.x][dir] = true;
            }
        }
    }

    public static boolean outOfRange(int y, int x) {
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
        visited = new boolean[H][W][4];

        for (int i = 0; i < H; i++) {
            input = br.readLine().split(" ");
            for (int j = 0; j < W; j++) {
                map[i][j] = fun.apply(input[j]);
            }
        } // 입력 받기 완료

        input = br.readLine().split(" ");
        sta = new Point(fun.apply(input[0]) - 1,
                fun.apply(input[1]) - 1, translateDir(fun.apply(input[2])));

        input = br.readLine().split(" ");
        des = new Point(fun.apply(input[0]) - 1,
                fun.apply(input[1]) - 1, translateDir(fun.apply(input[2])));

//        System.out.println("sta : " + sta);
//        System.out.println("des : " + des);
//        mapPrint();

        bfs();

        System.out.println(res);
    }

    public static void mapPrint() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
