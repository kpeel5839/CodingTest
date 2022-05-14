import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1938 : 통나무 옮기기

/**
 * -- 전제 조건
 * 통나무를 BBB 를 EEE 까지 옮기면 된다.
 * 1은 다른 나무를 의미하여서,
 * 만약에 회전, 혹은 이동하려고 할 때, 주변에 나무가 있다면 그 행동은 할 수 없다.
 *
 * 그렇게 하였을 때, 최소 이동으로 EEE 까지 옮겨라
 * 만약에 옮기지 못하는 경우는 0을 출력하면 된다.
 * -- 틀 설계
 * 통나무의 중심부만 알면 된다.
 * 현재의 방향과 그래서 현재의 방향이 어떤가에 따라서 그냥 상하좌우 움직임을 제어가 가능하다.
 * 그리고, 회전하는 경우도 그냥 인접한 공간에 1이 없으면 된다.
 *
 * 그래서, 결국 Map 에다가 BBB, EEE 는 받지 않는다.
 * Start, Destination 으로만 저장해놓으면 된다.
 *
 * -- 결과
 * 솔직히 되게 빨리 풀었는데
 * 모르고 int nx = point.x + dx[i] 해야 하는데
 * int nx = point.x = dx[i] 해서 계속 틀렸었음
 *
 * 문제점 찾는데 한 10분정도 소요했음
 *
 * 설계 이런 부분은 문제 없었으
 */
public class Main {
    static int N;
    static int res = Integer.MAX_VALUE;
    static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1}; // 8방향
    static int[][] map;
    static boolean[][][] visited; // 3차원의 방문배열, 맨 마지막은 상태를 의미
    static Point sta;
    static Point des;
    static List<Point> B = new ArrayList<>();
    static List<Point> E = new ArrayList<>();

    static class Point {
        int y;
        int x;
        int value;
        int status;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        Point(int y, int x, int value, int status) {
            this(y, x);
            this.value = value;
            this.status = status;
        }

        @Override
        public String toString() {
            return "(" + y + ", " + x + "), status : " + status + " value : " + value;
        }
    }


    static void bfs() {
        Queue<Point> queue = new LinkedList<>();
        queue.add(sta);
        visited[sta.y][sta.x][sta.status] = true;

//        System.out.println("sta : " + sta);
//        System.out.println("des : " + des);

        while (!queue.isEmpty()) { // 이제 반복을 시작
            Point point = queue.poll();

//            System.out.println(point);

            if (point.y == des.y && point.x == des.x && point.status == des.status) {
                res = point.value;
                return;
            }

            for (int i = 0; i < 8; i += 2) { // 일단, 상하 좌우로 움직이는 경우 처리
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx) || map[ny][nx] == 1 || possible(ny, nx, point.status)) { // 중심점이 범위를 벗어나거나, 나무에 부딪히는 경우와, 중심 부가 아닌 곳에 문제가 있을 때,
                    continue;
                }

                if (visited[ny][nx][point.status]) {
                    continue;
                }

                // 위에서 다 예외 처리 해주어서, 그냥 이제 진행하면 됨
                queue.add(new Point(ny, nx, point.value + 1, point.status));
                visited[ny][nx][point.status] = true;
            }

            boolean rotateAble = true; // 회전 가능 여부

            for (int i = 0; i < 8; i++) { // 회전이 가능한지 여부를 탐색, 주변 다 탐색
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];

                if (outOfRange(ny, nx) || map[ny][nx] == 1) {
                    rotateAble = false; // 회전 불가능 판단
                }
            }

            if (rotateAble) {
                int status = 0;

                if (point.status == 0) {
                    status = 1;
                }

                if (!visited[point.y][point.x][status]) { // 이전에 이 상태를 가진 적이 없을 때, 즉 방문한적이 없을 때
                    queue.add(new Point(point.y, point.x, point.value + 1, status));
                    visited[point.y][point.x][status] = true;
                }
            }
        }
    }

    static void decideStatus() {
        int bStatus = 0;
        int eStatus = 0;

        if (B.get(0).x == B.get(1).x) { // 일단, 첫번째와 2번째의 x 값이 같다면 세로, 아니라면 가로임
            bStatus = 1;
        } // 아니라면 바꿀 필요 x

        if (E.get(0).x == E.get(1).x) {
            eStatus = 1;
        }

        sta = new Point(B.get(1).y, B.get(1).x, 0, bStatus);
        des = new Point(E.get(1).y, E.get(1).x, 0, eStatus); // sta, des 의 상태를 설정해줌
    }

    static boolean possible(int y, int x, int status) { // 해당 method 가 있기에, 중심부만 움직여도 됨, 그대신 움직인 값을 넘김
        boolean impossible = false; // 가능 여부

//        System.out.println("y : " + y + " x : " + x + " status : " + status);
        // status == 0 이면 가로로 -1, 0, 1 x 값을 더해서 확인, status == 1 이면 세로로 -1, 0, 1 y 값을 더해서 확인
        for (int i = -1; i <= 1; i += 2) {
            int ny = y;
            int nx = x;

            if (status == 0) { // status == 0 일 때 (x 로 검사)
                nx += i;
            } else { // status == 1 일 때 (y 로 검사
                ny += i;
            }

            if (outOfRange(ny, nx)) { // outOfRange 인 경우는 끝
                impossible = true;
            } else if (map[ny][nx] == 1) { // 해당 지점이 나무인 경우
                impossible = true;
            }
        }

//        System.out.println(impossible);

        return impossible; // 결과를 반환
    }

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= N) || (x < 0 || x >= N)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        N = fun.apply(br.readLine());
        visited = new boolean[N][N][2];
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            String string = br.readLine();
            for (int j = 0; j < N; j++) {
                char input = string.charAt(j);

                if (input == 'B') {
                    B.add(new Point(i, j));
                    map[i][j] = 0;
                } else if (input == 'E') {
                    E.add(new Point(i, j));
                    map[i][j] = 0;
                } else {
                    map[i][j] = input - '0'; // 숫자로 변환
                }
            }
        }

        decideStatus();
        bfs();

        System.out.println((res == Integer.MAX_VALUE) ? 0 : res);
    }

    static void mapPrint() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
