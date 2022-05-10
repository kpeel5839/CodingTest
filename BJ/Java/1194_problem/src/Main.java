import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1194 : 달이 차오른다, 가자.

/**
 * -- 전제 조건
 * 미로가 주어지고, 그 미로를 탈출하면 된다.
 * 일단, 0은 한 개, 1은 적어도 한개 있음
 *
 * 이럴 때, a, b, c, d, e, f 열쇠가 있고
 * A, B, C, D, E, F 문이 있음
 *
 * 열쇠는 여러번 사용할 수 있음
 * -- 틀 설계
 * 그냥, 열쇠를 먹으면 그 위치까지 갔을 때의 이동한 횟수와
 * 그리고, 어떠한 열쇠를 먹었는지 넘기는데
 * 그것을 그냥 정수로 넘겨준다.
 *
 * (97 번째부터 소문자 a)
 * A -> 0 번째 자리
 * ....
 * 이렇게 가서
 * a == 1, b == 2, c == 4, d == 8, e == 16, f == 32 을 더해주면
 * a 만 있을 때에는
 * 1
 * b 까지 있을 때는
 * 3 = 11
 * c 까지 있을 때는
 * 7 == 111
 * d 까지 있을 때는
 * 15 == 1111
 * e 까지 있을 때는
 * 31 == 11111
 * f 까지 있을 때는
 * 63 == 111111 이 된다.
 *
 * 이렇게, 그냥 정수 + 1 << (알파벳 - 97) 을 해주면 된다.
 * 그러면 어떤 열쇠를 가지고 있는지가 나온다.
 *
 * 그래서 민식이 위치 하고
 * map 받고
 * 그 다음에, 열쇠들 저장하고
 * 해당 열쇠를 가지고 있으면, 열쇠 다시 만나도 다시 시작 안한다.
 * 그리고, 문은 그거에 따라서 딸 수 있음
 *
 * 진짜 한 코드 짜는데 20분 정도 걸리고
 * 1시간을 해맸는데
 * 막 alreadyStart 로 뭐 별 key 를 얻은 순서를 막 해보고 별 지랄을 다했는데
 *
 * 그냥 visited 3차원 배열로 만들고서
 * bfs 다시 재귀호출 할 필요도 없이, 맨 마지막 차원을 key 로 해놓고서
 * visited[y][x][key] 로 방문처리하면서 진행하면 그냥 쉽게 해결 되는 문제였음...
 *
 * 현타 오지네..
 */
public class Main {
    static int res = Integer.MAX_VALUE;
    static int H;
    static int W;
    static Point minsik; // 민식이냐
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static char[][] map;
    static boolean[][][] visited;
    static Queue<Point> queue = new LinkedList<>();

    static class Point {
        int y;
        int x;
        int key;
        int value;

        Point(int y, int x, int key, int value) {
            this.y = y;
            this.x = x;
            this.key = key;
            this.value = value;
        }
    }
    public static void bfs(int y, int x) {
        // count == 현재까지 이동횟수, key = 가지고 있는 키
        visited[y][x][0] = true;

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            if (map[point.y][point.x] == '1') { // bfs 이니까, 일찍 도착하는 것이 장땡임
                res = Math.min(res, point.value);
                break;
            }

            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                int key = point.key;

                if (outOfRange(ny, nx) || visited[ny][nx][key] || map[ny][nx] == '#') {
                    continue;
                }

                boolean upperCase = Character.isUpperCase(map[ny][nx]); // map[ny][nx] 가 대문자인지 확인

                if (upperCase) { // upperCase 이면 무조건 문임
                    if ((key & (1 << (map[ny][nx] - 65))) != 0) { // 해당 소문자가 있나 확인, 있다면 0이 아님
                        queue.add(new Point(ny ,nx, key, point.value + 1));
                        visited[ny][nx][key] = true; // 해당 지점을 갈 수 있음으로, 가고 방문처리
                    }
                } else if (97 <= map[ny][nx] && map[ny][nx] <= 122) { // upperCase 가 아니면 아무것도 아니거나, 소문자임, key 를 확인하기 위해서는 map[ny][nx] 여야 함
                    if ((key & (1 << (map[ny][nx] - 97))) != 0) { // 키를 이미 갖고 있는 경우
                        queue.add(new Point(ny, nx, key, point.value + 1));
                        visited[ny][nx][key] = true;
                    } else { // 해당 키를 갖고 있지 않은 경우
                        int newKey = (key | (1 << (map[ny][nx] - 97)));
                        queue.add(new Point(ny, nx, key, point.value + 1));
                        queue.add(new Point(ny, nx, newKey, point.value + 1));
                        visited[ny][nx][key] = true;
                        visited[ny][nx][newKey] = true;
                    }
                } else { // 그 외에는 그냥 담으면 됨
                    queue.add(new Point(ny, nx, key, point.value + 1));
                    visited[ny][nx][key] = true;
                }
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

    public static void keyPrint(int key) {
        for (int i = 0; i < 6; i++) {
            if ((key & (1 << i)) != 0) {
                System.out.print(1);
            } else {
                System.out.print(0);
            }
        }

        System.out.println();
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]);
        W = fun.apply(input[1]);
        map = new char[H][W];
        visited = new boolean[H][W][128];

        for (int i = 0; i < H; i++) {
            String string = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j);

                if (map[i][j] == '0') {
                    minsik = new Point(i, j, 0, 0); // 민식이의 위치를 얻어냄
                }
            }
        }

        queue.add(minsik);
        bfs(minsik.y, minsik.x);
        System.out.println(res == Integer.MAX_VALUE ? -1 : res);
    }
}
