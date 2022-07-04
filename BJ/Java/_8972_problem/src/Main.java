import java.util.*;
import java.io.*;
import java.util.function.Function;

// 8972 : 미친 아두이노

/**
 * -- 전제조건
 * 종수는 아두이노를 이용해서 Robots 라는 게임을 만들었음
 *
 * 종수는 아두이노 한대로를 조정해서 피해다녀야 한다.
 *
 * 아래와 같은 5가지 과정이 반복된다.
 *
 * 1. 먼저, 종수가 아두이노를 8가지 방향(수직,수평,대각선)으로 이동시키거나, 그 위치에 그대로 놔둔다.
 *
 * 2. 종수의 아두이노가 미친 아두이노가 있는 칸으로 이동한 경우에는 게임이 끝나게 되며, 종수는 게임을 지게 된다.
 *
 * 3. 미친 아두이노는 8가지 방향 중에서 종수의 아두이노와 가장 가까워 지는 방향으로 한 칸 이동한다. 즉, 종수의 위치를 (r1,s1), 미친 아두이노의 위치를 (r2, s2)라고 했을 때, |r1-r2| + |s1-s2|가 가장 작아지는 방향으로 이동한다.
 *
 * 4. 미친 아두이노가 종수의 아두이노가 있는 칸으로 이동한 경우에는 게임이 끝나게 되고, 종수는 게임을 지게 된다.
 *
 * 5. 2개 또는 그 이상의 미친 아두이노가 같은 칸에 있는 경우에는 큰 폭발이 일어나고, 그 칸에 있는 아두이노는 모두 파괴된다
 *
 * 즉, 종수가 먼저 움직인다 (이미 명령된 방향이 있음)
 *
 * 그 다음에 만일 종수가 미친 아두이노가 있는 방향으로 움직이게 되면 죽는다.
 *
 * 그리고 미친 아두이노는 8가지 방향 중에서 종수의 아두이노와 가장 가까워 지는 방향으로 한칸을 이동한다. |r1 - r2| + |s1 - s2| 가 가장 작은 값으로
 *
 * 미친 아두이노가 만일 움직여서 종수의 아두이노가 있는 곳으로 움직이면 게임이 또 끝난다.
 *
 * 2개 이상의 미친 아두이노가 같은 칸에 있는 경우에는 해당 위치에 있는 아두이노는 모두 폭발하게 된다.
 *
 * -- 틀 설계
 * 일단 가만히 있는 방향이 있는 것이 살짝 까다로울 것 같다.
 * 5 번이 가만히 있는 경우이다.
 *
 * 아두이노는 무조건 상대방이 있는 경우로 움직이게 되니, 8개의 방향을 이용해서 계산을 하면 된다. (convertDir 메소드를 통해서 받은 dir 을 내 dir 식으로 변환시켜준다.)
 *
 * 종수가 먼저 움직여주고
 * 미친 아두이노가 움직이는 형식으로 진행해주고
 *
 * 하나라도 죽었다고 true 를 반환하면 바로 sb.append("kraj " + X) 형식으로 진행해주고
 * 만일 sb.length() == 0 이라는 것은 위와 같은 연산이 실행되지 않은 것이기에 map 을 출력해준다.
 */
public class Main {
    static int H;
    static int W;
    static Point soo; // 종수의 위치
    static int[] instruct;
    static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1, 0};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1, 0}; // 맨 마지막은 가만히 있는 경우
    static char[][] map;

    static class Point {
        int y;
        int x;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static int convertDir(int dir) {
        return (dir == 1) ? 5
                : (dir == 2) ? 4
                : (dir == 3) ? 3
                : (dir == 4) ? 6
                : (dir == 5) ? 8
                : (dir == 6) ? 2
                : (dir == 9) ? 1
                : (dir == 8) ? 0 : dir;
    }

    static int getDistance(Point p1, Point p2) {
        return Math.abs(p1.y - p2.y) + Math.abs(p1.x - p2.x); // 둘의 차이를 계산해서 반환한다.
    }

    static boolean moveNotCrazy(int moveDir) { // 종수의 아두이노가 움직일 때
        // 종수의 아두이노를 움직이고, 만일 map[ny][nx] 에 미친 아두이노가 있다면 return true; 를 한다.
        int ny = soo.y + dy[moveDir];
        int nx = soo.x + dx[moveDir];

        // 범위를 벗어나는 입력은 주어지지 않으니까 outOfRange 는 고려하지 않아도 된다.
        if (map[ny][nx] == 'R') {
            return true;
        }

        map[soo.y][soo.x] = '.';
        map[ny][nx] = 'I';
        soo = new Point(ny, nx);

        return false;
    }

    static boolean moveCrazy() { // 미친 아두이노가 움직일 떄
        /**
         * 아두이노를 움직일 때에는 무지성으로 하면 안된다.
         * 일단, 아두이노들의 위치들을 파악해놓는다.
         * 그리고, 새로운 맵을 판다.
         *
         * 그리고 아두이노들을 soo 변수를 이용해서 움직여본다.
         * 그리고 이제 무지성으로 Queue 에 다가 담아놓은 애들을 새로 임시로 파놓은 map 에다가 넣고
         * count 변수도 만들어서 아두이노가 움직인 위치에다가 ++ 해준다..
         *
         * 그런 다음에 다 움직인다음
         * count 변수 (배열) 을 확인하면서 2 이상인 애들은 다 지워준다.
         *
         * 그 다음에, soo 를 그려넣고
         * 그대로 map 에다가 복사한다.
         *
         * 근데, 이제 무지성으로 움직이면서 soo 의 위치와 같아지는 경우는 return true 를 해주어야 한다.
         */
        char[][] tempMap = new char[H][W];
        int[][] cntMap = new int[H][W];
        Queue<Point> crazy = new LinkedList<>();

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                tempMap[i][j] = '.';

                if (map[i][j] == 'R') {
                    crazy.add(new Point(i, j));
                }
            }
        }

        for (Point point : crazy) {
            int min = Integer.MAX_VALUE;
            int minDir = 0;

            for (int i = 0; i < 8; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i]; // 계산을 해주고

                if (!outOfRange(ny, nx)) { // 범위를 나가지 않는 경우에만 수와 떨어진 거리를 게산해준다.
                    int distance = getDistance(soo, new Point(ny, nx));

                    if (distance < min) { // 현재 방향의 distance 가 더 짧은 경우에는 이렇게
                        min = distance;
                        minDir = i;
                    }
                }
            }

            // 최종적으로 결정된 방향 즉, minDir 로 움직여준다.
            int ny = point.y + dy[minDir];
            int nx = point.x + dx[minDir];

            if (soo.y == ny && soo.x == nx) { // 박은 경우이다.
                return true;
            }

            tempMap[ny][nx] = 'R';
            cntMap[ny][nx]++; // 아두이노의 개수를 세준다.
        }

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (1 < cntMap[i][j]) { // 해당 위치의 아두이노가 2개를 넘어간다면 지워주낟.
                    tempMap[i][j] = '.';
                }
            }
        }

        tempMap[soo.y][soo.x] = 'I';

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                map[i][j] = tempMap[i][j];
            }
        }

        return false;
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

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]);
        W = fun.apply(input[1]);
        map = new char[H][W];

        for (int i = 0; i < H; i++) {
            String string = br.readLine();

            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j);

                if (map[i][j] == 'I') {
                    soo = new Point(i, j);
                }
            }
        }

        String string = br.readLine();
        instruct = new int[string.length()];

        for (int i = 0; i < string.length(); i++) {
            instruct[i] = convertDir(string.charAt(i) - '0');
        }

        for (int i = 1; i <= instruct.length; i++) {
            if (moveNotCrazy(instruct[i - 1]) || moveCrazy()) { // 박은 경우, 박지 않는다면 다 실행이 된다.
                sb.append("kraj ").append(i);
                break;
            }
        }

        if (sb.length() == 0) { // 중간에 안 끝난 것
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }
        }

        System.out.print(sb);
    }
}
