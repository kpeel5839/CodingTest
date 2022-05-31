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
 *
 * -- 해맸던 점
 * 일단, 다 맞았었는데
 * 처음에는 equals 를 해서 죽은 놈들을 처리하는 형식으로 했었다.
 * 하지만, 그 방법에서는 point.equals(ny - dy[i], nx - dx[i]) 로 했었어야 했다라는 것을 간과하고 있어서 이 부분을 고치니까 해결이 되었다.
 * 근데, 뒤지게 stack overflow 가 나오는 것이다.
 *
 * 왜 그러지 왜 그러지 하면서
 * 뭔가 굉장히 비효율적으로, map[y][x] == '.' 이 더라도 실행되는 것들이 있는 것 같아서
 * if (map[y][x] == '.') 구문을 추가하였더니
 * 시간도 획기적으로 단축되고 맞았다.
 *
 * 근데 너무 이상하다, 이전 방식에서 remove 된 것들은 방문하지 않고,
 * 그리고, 무엇보다 움직였다가 다 복구 시켜놓고
 * pin 도 순서대로 다 방문해보는 것인데
 * 왜 해당 pin 으로 방문했을 때, map[y][x] == '.'
 * 인 경우가 있었더 것일까?
 *
 * 그것도 dfs 인데 ???
 *
 * 되게 이상했지만
 * 그냥 현재로서 제거하는 애들은 .으로 바꿔서 제거해주고
 * 다시 돌아오면 다음 상황을 전개해야 하니, 다시 'o' 로 바꿔주는 방식을 선택했다
 * equals 도 쓰지 않았다.
 *
 * 그랬더니 코드가 훨씬 가독성도 좋아지고 간결해졌다.
 *
 * 근데, 아직도 그 문제점은 잘 모르겠는 찝찝함이 있다.
 * 왜그랬을까...
 */
public class Main {
    static int T;
    static int H = 5;
    static int W = 9;
    static int minRemain;
    static int minMove;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static char[][] map;
    static List<Point> pinList;

    static class Point {
        int y;
        int x;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
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

                map[y][x] = '.';
                map[ny][nx] = 'o';
                map[ny - dy[i]][nx - dx[i]] = '.';
                pinList.set(pin, new Point(ny, nx));

                for (int j = 0; j < pinList.size(); j++) {
                    dfs(j, cnt + 1, remain - 1);
                }

                map[y][x] = 'o';
                map[ny][nx] = '.';
                map[ny - dy[i]][nx - dx[i]] = 'o';
                pinList.set(pin, new Point(y, x));
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