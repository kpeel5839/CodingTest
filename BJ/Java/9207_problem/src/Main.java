import java.util.*;
import java.io.*;
import java.util.function.Function;

// 9207 : 페그 솔리테어

/**
 * -- 전제조건
 * 구멍이 뚫려 있는 이차원 게임판에서 하는 게임이다.
 * 각 구멍에는 핀을 하나 꽂을 수 있다.
 * # 은 꽂을 수 없다라는 것을 조심하자.
 *
 * 핀은 수평, 수직 방향으로 인접한 핀을 뛰어 넘어서 그 핀의 다음 칸으로 이동하는 것이 허용이된다.
 *
 * 그래서 핀이 어떠한 핀을 뛰어 넘게 되면,
 * 그 뛰어 넘어진 핀은 사라지게 된다.
 *
 * 이러한 조건이 주어졌을 때, 핀을 다 없애는 최소 횟수를 출력하시오
 *
 * -- 틀 설계
 * 일단 test case 도 많고,
 * 핀을 움직여서 남길 수 있는 핀의 최소 개수와
 * 그 개수를 만들기 위해서 필요한 최소 이동횟수를 구하기 위해서
 * 일단 핀의 개수와, 핀의 이동 횟수는 같이 관리가 되어야 하고
 * 만일 핀의 개수는 같지만, 이동 횟수는 다르다면, 이동 횟수가 더 작은 것을 선택하면 될 것 같다.
 *
 * 그리고 모든 경우를 시도해보면 될 것 같다.
 * 1번 핀이 뛰어 넘는 경우, 뭐 그런 식으로 해서 dfs 를 진행하면 될 것 같다.
 *
 * 계속 핀의 위치와, 핀의 개수, 맵을 갱신하면서 진행을 해야할 것 같다.
 *
 * 핀의 개수가 무조건 8개 이상을 가지지 않는다는 것과
 * 맵이 항상 5 * 9 라는 것도 있음으로
 * 시간이 그리 오래 걸릴 것 같지 않다
 */
public class Main {
    static int T;
    static int H = 5;
    static int W = 9;
    static int minRemain;
    static int minMove;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static boolean[] remove;
    static char[][] map;
    static List<Point> pinList;

    static class Point {
        int y;
        int x;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public boolean equals(int y, int x) {
            if (y == this.y && x == this.x) { // 같은 경우 true
                return true;
            } else { // 같지 않은 경우 false
                return false;
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

    static void dfs(int pin, int cnt, int remain) {
        // 여기서는 일단 이전의 위치를 기억하고 있어야 한다.
        // 현재 움직일 핀의 이전 위치를 기억하고 있어야지 map 을 돌려낼 수 있다.
        // 그리고, dead 시킨 놈도 기록해야 한다.
        // 그래야지 돌아왔을 때, 다시 살려낼 수 있다.
        int y = pinList.get(pin).y;
        int x = pinList.get(pin).x;

        if (map[y][x] == '.') { // 개 해매다가, 만일 map[y][x] 가 . 이라면 그냥 실행 x 를 추가하니까 해결되었음
            return;
        }

        // 4 방향으로 다 둘러보면서 인접한 핀이 있는지 확인해준다.
        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];
            int dead = -1;

            if (outOfRange(ny, nx) || map[ny][nx] == '#') { // 범위를 벗어나는 경우
                continue;
            }

            if (map[ny][nx] == 'o') { // 인접한 핀이 있는 경우
                // 그 방향으로 한칸 더 나아가, 갈 수 있는지 확인한다.
                ny += dy[i];
                nx += dx[i];

                if (outOfRange(ny, nx) || map[ny][nx] != '.') { // 범위를 벗어나거나, 다음 것이 비어있지 않은 경우
                    continue;
                }

                // 그러면 여기에 현재 pin 을 넘겨야 함
                for (int j = 0; j < pinList.size(); j++) {
                    if (j != pin) {
                        if (pinList.get(j).equals(ny - dy[i], nx - dx[i])) {
                            dead = j;
                            remove[dead] = true;
                            break;
                        }
                    }
                }

                map[y][x] = '.';
                map[ny][nx] = 'o';
                map[ny - dy[i]][nx - dx[i]] = '.';
                pinList.set(pin, new Point(ny, nx));

                for (int j = 0; j < pinList.size(); j++) {
                    if (!remove[j]) {
                        dfs(j, cnt + 1, remain - 1);
                    }
                }

                map[y][x] = 'o';
                map[ny][nx] = '.';
                map[ny - dy[i]][nx - dx[i]] = 'o';
                pinList.set(pin, new Point(y, x));
                remove[dead] = false; // 복구
            }
        }

        if (remain <= minRemain) { // 작거나 같은 경우
            if (remain == minRemain) { // 같은 경우
                if (cnt < minMove) {
                    minMove = cnt;
                }
            } else { // remain 이 더 작은 경우
                minRemain = remain;
                minMove = cnt; // 이 경우는 무조건 다 초기화
            }
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        T = fun.apply(br.readLine());

        while (T-- > 0) {
            map = new char[H][W];
            pinList = new ArrayList<>();

            for (int i = 0; i < H; i++) {
                String string = br.readLine();
                for (int j = 0; j < W; j++) {
                    map[i][j] = string.charAt(j);

                    if (map[i][j] == 'o') {
                        pinList.add(new Point(i, j));
                    }
                }
            }

            minRemain = pinList.size();
            minMove = Integer.MAX_VALUE;
            remove = new boolean[pinList.size()];

            for (int i = 0; i < pinList.size(); i++) {
                dfs(i,0, pinList.size());
            }

            br.readLine();
            sb.append(minRemain + " " + minMove).append("\n");
        }

        System.out.println(sb);
    }

    static void mapPrint() {
        System.out.println("next ");
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}