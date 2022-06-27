import java.util.*;
import java.io.*;
import java.util.function.Function;

// 18500 : 미네랄 2

/**
 * -- 전제조건
 * 이 문제는 일단 클러스터들이 있다.
 * 그리고 두 친구가 왼쪽, 오른쪽을 번갈아가면서 창을 던진다
 *
 * 그러면 창을 던져서 제일 먼저 만난 미네랄은 부서지고 만일 그걸로 인해서 클러스터가 분리되었으면
 * 땅으로 떨어진다.
 *
 * 그래서 다른 클러스터를 만나거나 그러면 떨어지는 것을 멈춘다.
 * -- 틀 설계
 * 이 문제는 일단 창을 던지는 것을 잘해야 한다.
 * 가장 원래 N - 1 인 부분이 해당 문제에서는 반대이다.
 * 즉, 입력을 거꾸로 받고나 하면 된다.
 *
 * 근데 사실 입력 반대로 받는 것이 훨씬 나을 것 같기도 하다.
 *
 * 그러면 입력을 반대로 받고
 * 첫번째 행은 비워놓자.
 *
 * 적어도 좌우는 바뀌지 않으니까 그것은 신경쓰지 않아도 된다.
 *
 * 그러면 이제 신경써야 할 부분은 창을 던졌을 때, 클러스터가 분리되는지 어떻게 판단할 것이며
 * 클러스터를 같이 떨어질 수 있도록 어떻게 할 것인지가 관건이다.
 *
 * 이 문제는 땅과 붙어있는 클러스터를 확인하면 될 것 같다.
 *
 * 클러스터가 가만히 있으려면 무조건 적으로 땅이랑 붙어있는 미네랄 들이 있어야 한다.
 *
 * 그러니 가만히 있는 미네랄 들은 무조건 땅에 붙어있는 미네랄과 같은 클러스터여야 한다.
 * 그렇기 때문에 1번째 행렬에서 bfs 를 진행했을 때, 클러스터에 포함되지 않은 애들은 분리된 클러스터라는 것이다.
 *
 * 그리고 두 개 이상의 클러스터가 동시에 분리 되는 경우는? 없다라고 한다.
 *
 * 생각을 더하면 처리할 수 있을 것 같은데 없다고 하니까 더 문제가 쉬워졌음
 * 그래서 이제 낙하 처리만 하면 이 문제는 설계는 끝이다.
 *
 * 낙하처리는 어떻게 할 수 있을까?
 * 일단, 바닥에 닿거나 다른 클러스터에 닿는 경우가 있다.
 * 그래서 모든 클러스터를 자유낙하 시켜보고 그런 경우를 골라내서
 * 가장 작은 값을 취해 해당 클러스터를 그 정도만 내려주면 된다.
 *
 * 그러면 끝이다.
 *
 * -- 해맸던 점
 * 처음에 땅에 있던 놈들도 clusterNumber = 1 로 해주어야 하는데
 * queue 에다가 넣으면서 안해가지고 클러스터가 분리되도 낙하가 안되는 문제가 있었음
 *
 * 그래서 그것을 추가해주자 말자 맞았음
 */

public class Main {
    static int H;
    static int W;
    static int N;
    static Point noneCluster; // check method 에서 cluster 가 아닌 미네랄이 있으면 시작지점으로 주어진다.
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[][] clusterNumber; // 클러스터 번호를 기록
    static char[][] map; // 주어진 map
    static boolean[][] visited; // 방문처리를 진행할 배열
    static List<Point> list = new LinkedList<>(); // cluster 가 아닌애들을 담을 List

    static boolean outOfRange(int y, int x) {
        if (y < 1 || y >= (H + 1) || x < 0 || x >= W) { // 범위가 조금은 신기함
            return true;
        } else {
            return false;
        }
    }

    static void destroy(int h, boolean left) { // 창을 던진 높이가 주어지면 파괴할 수 있는 미네랄을 파괴한다.
        int start = left ? 0 : W - 1;
        int limit = left ? W : -1;
        int crease = left ? 1 : -1;

        for (int i = start; i != limit; i += crease) {
            if (map[h][i] == 'x') {
                map[h][i] = '.';
                break;
            }
        }
    }

    static void gameStart(int h, boolean left) { // 창을 던진 높이가 주어진다.
        clusterNumber = new int[H + 1][W];
        destroy(h, left);
        bfs(true);

        if (check()) {
            bfs(false);
            executeDrop();
        }
    }

    static void executeDrop() {
        // list 를 가지고 판단한다.
        int dropDistance = Integer.MAX_VALUE;
        for (Point point : list) {
            dropDistance = Math.min(dropDistance, drop(point.y, point.x));
            map[point.y][point.x] = '.'; // 짜피 움직여줄 거라서 . 으로 만들어줌
        }

//        for (int i = H; i != 0; i--) {
//            for (int j = 0; j < W; j++) {
//                System.out.print(clusterNumber[i][j]);
//            }
//            System.out.println();
//        }

        // dropDistance 를 구했으니까 실제로 그 만큼 드랍 시킨다.
        for (Point point : list) {
            map[point.y - dropDistance][point.x] = 'x'; // 그만큼 떨궈준다.
        }

        list = new ArrayList<>(); // 초기화
    }

    static int drop(int y, int x) {
        int res = 0;

        while (true) {
            // 계속 아래로 움직여주면서 outOfRange 되거나, 혹은 clusterNumber[y][x] 를 1을 만나는 일이 있으면 break;
            y--;
            if (outOfRange(y, x) || clusterNumber[y][x] == 1) { // cluster 로 한 이유는 본인이 본인 것과 부딪히지 않게 하기 위해서
                break;
            }

            res++;
        }

        return res;
    }

    static void bfs(boolean init) { // init = true 이면, 조사차원임 클러스터가 떠 있는 애들이 있는지 확인하는 용도, init = false 이면 noneCluster 를 가지고 시작
        Queue<Point> queue = new LinkedList<>();
        visited = new boolean[H + 1][W];

        if (init) { // 조사할 때에는 이렇게
            for (int i = 0; i < W; i++) {
                if (map[1][i] == 'x') {
                    queue.add(new Point(1, i));
                    visited[1][i] = true;
                    clusterNumber[1][i] = 1;
                }
            }
        } else {
            queue.add(new Point(noneCluster.y, noneCluster.x));
            list.add(new Point(noneCluster.y, noneCluster.x));
            visited[noneCluster.y][noneCluster.x] = true;
        }

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx] || map[ny][nx] == '.') { // 범위를 벗어나거나, 이미 방문한 곳이면
                    continue;
                }

                if (init) {
                    clusterNumber[ny][nx] = 1;
                } else {
                    list.add(new Point(ny, nx));
                }

                queue.add(new Point(ny, nx));
                visited[ny][nx] = true;
            }
        }
    }

    static class Point {
        int y;
        int x;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static boolean check() { // 분리된 클러스터가 있는지 확인한다.
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j] == 'x') { // 미네랄이면
                    if (clusterNumber[i][j] == 0) { // clusterNumber 가 1이 아니다? 그러면 true 를 반환하여 분리되었음을 나타낸다.
                        noneCluster = new Point(i, j);
                        return true;
                    }
                }
            }
        }

        noneCluster = null; // cluster 가 아닌 애들이 없으니까 null 로 해준다.
        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]);
        W = fun.apply(input[1]);

        map = new char[H + 1][W];

        for (int i = H; i != 0; i--) {
            String string = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j);
            }
        }

        N = fun.apply(br.readLine());
        input = br.readLine().split(" ");

        for(int i = 1; i <= N; i++) {
            gameStart(fun.apply(input[i - 1]), (i % 2) == 1); // left 는 홀 수 일 때이니까 홀수이면 true 를 넘김
        }

        for (int i = H; i != 0; i--) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}
