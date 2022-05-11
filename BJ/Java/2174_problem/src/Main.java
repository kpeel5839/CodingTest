import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2174 : 로봇 시뮬레이션

/**
 * -- 전제조건
 * 맵의 크기와, 로봇들의 위치를 받고
 * 명령들을 받았을 때, 로봇이 벽에 박으면, Robot X crashes into the wall
 * 로봇끼리 박으면, Robot X crashes into robot Y
 * 이렇게 출력하면 된다.
 *
 * 가장 처음에 박은 결과를 출력하라고 하니, 그냥 박으면 끝내면 될 듯하다.
 * 그리고 L, R, F 가 있는데, L 은 현재 방향에서 왼쪽으로, R 은 현재 방향에서 오른쪽으로, F 는 그대로이고
 * 몇번로봇, 방향, 횟수 이렇게 주어지면, 그대로 움직이면 된다.
 * -- 틀 설계
 * 그냥, 방향 바꿀때는 계속 방향만 바꿔주고
 * 움직일 때는 움직여주고, map 에다가 로봇 번호 기록해놓고
 * 리스트를 이용해, set, get 연산으로 진행하였음
 *
 * 그리고 outOfRange, crashes 경우 따로 처리하였음
 *
 * 근데, 자꾸 nullPointer Exception 이 발생했음
 * 왜 그런가 했는데
 *
 * 다 맞게 한 것 같은데
 *
 * 근데 개 바보 같이 for (int i = 0; i < M; i++) 로 해야 하는데
 * for (int i = 0; i < W; i++) 이러고 있었음, 이래서 null Pointer 에러 났던 것임
 *
 * 역시 컴퓨터를 의심하면 안됨
 * 이상한 건 나임
 */
public class Main {
    static int H;
    static int W;
    static int N;
    static int M;
    static int[][] map;
    static List<Robot> robot = new ArrayList<>();
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0}; // 맵이 뒤집어져 있기 때문에 이렇게
    static boolean crashed = false;
    static StringBuilder sb;

    static class Robot {
        int y;
        int x;
        int dir;

        public Robot(int y, int x, int dir) {
            this.y = y;
            this.x = x;
            this.dir = dir;
        }
    }

    public static void execute(int number, int dir, int count) {
        Robot nowRobot = robot.get(number);

        int y = nowRobot.y;
        int x = nowRobot.x;
        int nowDir = nowRobot.dir;

        map[y][x] = 0; // map 에 있는 로봇 일단 없애줌

        if (dir == 0) { // 알고보니까, 개같이 L, R 은 회전만 하는 거였음
            for (int i = 0; i < count; i++) {
                nowDir = calDir(nowDir + dir); // 현재 방향 정해줌
                y = y + dy[nowDir];
                x = x + dx[nowDir];

                if (outOfRange(y, x)) {
                    sb = new StringBuilder("Robot " + number + " crashes into the wall");
                    crashed = true;
                    break;
                }

                if (map[y][x] != 0) { // 다른 로봇과 부딪힌 경우
                    sb = new StringBuilder("Robot " + number + " crashes into robot " + map[y][x]);
                    crashed = true;
                    break;
                }
            }
        } else {
            for (int i = 0; i < count; i++) {
                nowDir = calDir(nowDir + dir);
            }
        }

        if (!crashed) { // outOfRange 일 때, 따로 처리하기 귀찮음
            robot.set(number, new Robot(y, x, nowDir));
            map[y][x] = number;
        }
    }

    public static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    public static int calDir(int dir) {
        return (dir < 0) ? 3 : (dir % 4); // 계산된 dir 이 넘어오면 변환해서 넘겨준다.
    }

    public static int decideDir(String dir) { // F, L, R 까지 처리
        return (dir.equals("N")) ? 0
                : (dir.equals("E")) ? 1
                : (dir.equals("S")) ? 2
                : (dir.equals("W")) ? 3
                : (dir.equals("F")) ? 0
                : (dir.equals("L")) ? -1 : 1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder("OK");
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        W = fun.apply(input[0]);
        H = fun.apply(input[1]);

        map = new int[H][W];

        input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        M = fun.apply(input[1]);
        robot.add(null); // dummy data

        for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");

            int x = fun.apply(input[0]) - 1;
            int y = fun.apply(input[1]) - 1;
            int dir = decideDir(input[2]);

            map[y][x] = i + 1;
            robot.add(new Robot(y, x, dir));
        }

        for (int j = 0; j < M; j++) {
            input = br.readLine().split(" ");

            int number = fun.apply(input[0]);
            int dir = decideDir(input[1]);
            int count = fun.apply(input[2]);


            execute(number, dir, count);

            if (crashed) {
                break;
            }
        }

        System.out.println(sb);
    }

    public static void mapPrint() {
        System.out.println("next");
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
