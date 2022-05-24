import java.util.*;
import java.io.*;
import java.util.function.Function;

// 3055 : 탈출

/**
 * -- 전제 조건
 * 사랑스런 고슴도치짱이 비버의 굴로 피할려고 한다.
 * 하지만, 물이 차있다.
 * 고슴도치의 위치와 벽, 비버 굴, 물이 주어졌을 때,
 * 고슴도치가 비버의 굴로 피하는 최소 시간을 구하여라.
 *
 * 단 고슴도치는 물이 찰 공간에 갈 수 없다.
 * 즉, 죽게 되는 위치에 갈 수 없다라는 것
 * -- 틀 설계
 * 그냥 너무 평범한 bfs 이다.
 * 죽게 되는 위치에 고슴도치가 이동할 수 없음을 구현하기 위해서는
 * 그냥 물을 먼저 움직여주면 된다.
 *
 * 그리고, 물을 움직여준다음에 고슴도치를 움직이게 되고
 * 그렇기 때문에, 물이 고슴도치를 덮치는 일은 없다.
 *
 * 그 말은 무엇이냐, 고슴도치가 더 이상 움직일 공간이 없다??
 * 움직이지 못한다?
 * 그러면 끝내면 된다.
 *
 * 근데 여기서 의문점이 든다.
 * 그러면
 * D .
 * S * 이런 상황은 주어지지 않는 것일까?
 *
 * 사실 이런 상황에서도 물을 먼저 움직이고, 고슴도치가 있는 위치에는 물을 움직이지 않으면 깔끔하게 해결 가능하다.
 * 왜냐하면 원래는 동시에 움직이는 것이니까 하지만 로직을 편하게 하기 위해서 그런식으로 진행하지만 전혀 문제는 발생하지 않을 것을 보인다.
 */
public class Main {
    static int H;
    static int W;
    static int res = 0; // 고슴도치가 탈출하는 경우의 시간을 저장
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static char[][] map;
    static boolean[][] visited;
    static Point dochi; // 깜찍이 도치

    static class Point {
        int y;
        int x;
        int value;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
        Point(int y, int x, int value) {
            this(y, x);
            this.value = value;
        }
    }

    static void gameStart() {
        // 게임을 시작해서, 물을 움직이고 고슴도치를 움직이는 역할을 해준다.
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(dochi.y, dochi.x, 0));
        visited[dochi.y][dochi.x] = false;

        while (true) {
            spreadWater();
            int size = queue.size();

            for (int i = 0; i < size; i++) { // 이전에 갖고 있던 사이즈만큼만 진행해줌
                Point point = queue.poll();

                for (int j = 0; j < 4; j++) {
                    int ny = point.y + dy[j];
                    int nx = point.x + dx[j];

                    if (outOfRange(ny, nx) || visited[ny][nx]
                            || map[ny][nx] == '*' || map[ny][nx] == 'X') { // 우리 도치짱이 갈 수 없는 곳일 경우, continue
                       continue;
                    }

                    if (map[ny][nx] == 'D') {
                        res = point.value + 1;
                        return;
                    }

                    visited[ny][nx] = true;
                    queue.add(new Point(ny, nx, point.value + 1));
                }
            }

            if (queue.isEmpty()) { // 다 돌았는데, queue 가 Empty 이다? 그러면 성공 x
                return;
            }
        }
    }

    static void spreadWater() {
        // 물을 퍼트리는 역할을 한다.
        Queue<Point> queue = new LinkedList<>();

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j] == '*') {
                    queue.add(new Point(i, j));
                }
            }
        }

        for (Point point : queue) {
            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx) || map[ny][nx] != '.') { // . 에다가만 퍼트릴 수 있음
                    continue;
                }

                map[ny][nx] = '*'; // 실제로 퍼트림
            }
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
        map = new char[H][W];
        visited = new boolean[H][W];

        for (int i = 0; i < H; i++) {
            String string = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j);

                if (map[i][j] == 'S') {
                    dochi = new Point(i, j);
                }
            }
        }

        gameStart();

        System.out.println((res == 0) ? "KAKTUS" : res);
    }
}