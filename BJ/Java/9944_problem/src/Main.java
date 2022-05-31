import java.util.*;
import java.io.*;
import java.util.function.Function;

// 9944 : NxM 보드 완주하기

/**
 * -- 전제조건
 * N x M 보드가 주어지고
 * 공이 장애물에 부딪히거나, 혹은 본인이 이동한 경로에 부딪히면 멈추는 형식으로
 * 계속 상 하 좌 우를 움직여 보면서, 진행해보는 것이다.
 *
 * 그럴 때, 공이 모든 빈칸을 방문한적이 있는 경우의 최소 이동 단계를 거친 경우를 출력하라
 *
 * -- 틀 설계
 * 어떻게 풀 수 있을까...
 * 일단, 무엇보다 공이 다 지나가는 경우가 중요하다.
 * 그러면 가능한 모든 경우를 해야할 것 같다.
 * 하지만, 가능한 모든 경우를 하게 되면 시간이 너무 오래걸릴 것 같다.
 * 근데, 시간은 또 많이 주어진다.
 *
 * 어떻게 해야하는 것일까
 * 시간은 많이 주어지지만 메모리는 많이 주어지지 않는다.
 * 그러면 deep copy 를 이용한 dfs 는 안될 것 같다.
 *
 * 일단 확실한 것은 method 를 이용해서 본인이 선택한 이동경로로 방문처리를 하면서 진행하는 메소드 하나는 만들어야 할 것 같다.
 *
 *
 * -- 결과
 * 그냥 deep copy 로
 * bfs 에 충실하여 문제를 풀었는데
 * 생각외로 맞았다.
 *
 * 역시 시간초를 많이 준 이유가 있었음, 방법이 이것밖에 없었던 것인가??
 *
 * -- 해맸던 점
 * queue.add(new Point()); 에서 remain 을 전역변수 remain 을 사용했었음
 * 그리고, point.value + moveCount 가 아니라 point.value + 1 인데
 * 이 부분도 잘못했었음
 *
 * 그리고 point.visited 를 그냥 true 처리하게 되면 방문 처리가 이상하게 된다라는 것을 간과하였었고
 * 그래서 innerVisited 로 바꿨고
 * EOF 처리도 잘못했었어서
 * 그냥 처음에 br.readLine() 으로 받은 것이 null 이면 break 아니면, 그냥 parsing 을 진행하는 형식으로 진행했음
 * 그리고 remain 과, res 를 갱신을 안해준 점?
 * 이것도 틀렸었던 이유였음
 * 
 */
public class Main {
    static int H;
    static int W;
    static int res = Integer.MAX_VALUE;
    static int remain = 0;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static char[][] map;

    static class Point {
        int y;
        int x;
        int remain;
        int value;
        boolean[][] visited;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        Point(int y, int x, int remain, int value, boolean[][] visited) {
            this(y, x);
            this.remain = remain;
            this.value = value;
            this.visited = visited;
        }
    }

    static int move(Point point, int dir, boolean[][] visited) {
        int y = point.y;
        int x = point.x;
        int count = 0;

        while (true) {
            y += dy[dir];
            x += dx[dir];

            if (outOfRange(y, x) || visited[y][x] || map[y][x] == '*') { // 범위를 벗어나거나, 방문한 곳을 만나거나, 벽을 만난 경우
                break;
            }

            count++;
        }

        return count; // 해당 칸으로 몇칸 움직였는지 반환
    }

    static void bfs(int y, int x) {
        // 최소 단계를 구하기 위해서, 내 생각에는 무조건 bfs 를 사용해야 할 것 같다.
        // remain 을 가지고 있으면서, 빈칸을 다 방문했는지 체크해야 한다.
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[H][W];
        visited[y][x] = true;
        queue.add(new Point(y, x, remain - 1, 0, visited)); // visited 배열 까지 같이 넘겨줌

        while (!queue.isEmpty()) {
            Point point = queue.poll();

//            System.out.println("(" + point.y + ", " + point.x + ") : " + point.remain + " value : " + point.value);
            if (point.remain == 0) { // 모든칸을 방문한 경우는 가장 먼저 모든 칸을 방문한 것이니 break 은 물론이고, res 를 갱신해준다.
                res = Math.min(res, point.value);
                break;
            }

            for (int i = 0; i < 4; i++) {
                int moveCount = move(point, i, point.visited); // 여기로 얼마나 움직일 수 있는지 받는다.
                int ny = point.y;
                int nx = point.x;

                if (moveCount != 0) {
                    boolean[][] innerVisited = new boolean[H][W];

                    for (int c = 0; c < H; c++) {
                        for (int j = 0; j < W; j++) {
                            innerVisited[c][j] = point.visited[c][j];
                        }
                    }

                    for (int j = 0; j < moveCount; j++) { // 받은 무브 카운트만큼 움직이면서 visited 처리를 해준다.
                        ny += dy[i];
                        nx += dx[i];
                        innerVisited[ny][nx] = true;
                    }

                    queue.add(new Point(ny, nx, point.remain - moveCount, point.value + 1, innerVisited));
                }
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
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;
        int index = 0;

        while (true) {
            index++;
            String readLine = br.readLine();

            if (readLine == null) {
                break;
            }

            String[] input = readLine.split(" ");

            H = fun.apply(input[0]);
            W = fun.apply(input[1]);
            map = new char[H][W];
            res = Integer.MAX_VALUE;
            remain = 0;

            for (int i = 0; i < H; i++) {
                String string = br.readLine();
                for (int j = 0; j < W; j++) {
                    map[i][j] = string.charAt(j);
                    if (map[i][j] == '.') {
                        remain++;
                    }
                }
            }

            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (map[i][j] != '*') {
//                        System.out.println("start : (" + i + ", " + j + ")");
                        bfs(i, j);
                    }
                }
            }

            sb.append("Case " + index + ": ")
                    .append((res == Integer.MAX_VALUE) ? -1 : res).append("\n");
        }

        System.out.println(sb);
    }
}