import java.util.*;
import java.io.*;
import java.util.function.Function;

// 프렌즈 사천성

/**
 * 결국 못 풀었음..
 * 다 맞는데 테스트케이스 도대체 뭐지?
 *
 * -- 해결
 * 진짜 개좆같음
 * 맞췄었어
 * 완전 맞췄었는데
 * 프로그래머스는 전역변수를
 * 초기화하면 안됨
 * 예를 들어서 public static List<Point> = new ArrayList<>()
 * 이렇게 하면 틀림
 *
 * 진짜 개좆같네
 * 이것때문에 3시간 쏟았는데
 * 진짜 개좆같다...
 */
class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;
        String[] input = br.readLine().split(" ");

        int m = fun.apply(input[0]);
        int n = fun.apply(input[1]);
        String[] board = new String[m];

        for (int i = 0; i < m; i++) {
            board[i] = br.readLine();
        }

        System.out.println(solution(m, n, board));
    }
    /*
    일단, A - Z 까지 한번만 나타난 다라는 것을 고려해야 함
    
    그래서 A - Z 까지의 위치들을 따로 class Point 배열을 만들어서 [i][0] , [i][1] 로 관리한다.
    그래서 bfs 를 호출해서 되는지 순서대로 확인하는데
    당연히 point = new Point[26][2] 로 선언을 하고 A - Z = 65 ~ 90 번인 것을 이용해서
    순서대로 진행한다.
    
    일단, 이게 visited 로 관리하고
    솔직히 그냥 하나 터트리면 다시 시작하면 된다. 0 ~ 25 까지 말이다.
    그러면 point[0 ~ 25] 를 뽑아서, bfs 로 서로에게 다을 수 있는 지 확인하면 되는데
    여기서 중요한 것이
    처음에 뻗어나갈 때를 제외하고, 방향 전환이 있다면? 그것을 카운트 해준다.
    그러니, Point 로 해결을 하는데, point 에는 3개의 속성이 존재하고
    y, x 그리고 count 가 들어가있으면 된다.
    
    쨌든 터트리면 다시 시작하고
    그렇게 해서 순서대로 answer 에다가 채워넣는다.
    
    그러다가, boolean pang 으로 pang 을 성공했는지 여부를 저장한 뒤에
    pang 을 하게 되면, 두 포인트들은 "." 으로 바꿔주고, 다시 시작하는데
    
    만약 끝까지 했는데 pang 이 false 이다? 그러면 알파벳이 있는지 확인하고
    Character.isUpperCase 를 확인해서 없다면? 그러면 answer 를 그냥 출력

    아니라면 impossible 을 출력하면 된다.
    */
    public static class Point {
        int y;
        int x;
        int value;
        int dir;

        public Point(int y, int x, int value, int dir) {
            this.y = y;
            this.x = x;
            this.value = value; // 꺾인 횟수
            this.dir = dir;
        }

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public String toString() {
            return "y : " + y + " x : " + x;
        }
    }

    public static Point[][] points = new Point[26][2];
    public static int[][] visited;
    public static boolean pang = false;
    public static int H;
    public static int W;
    public static int[] dx = {0, 1, 0, -1};
    public static int[] dy = {-1, 0, 1, 0};
    public static char[][] map;
    public static StringBuilder answer = new StringBuilder();

    public static void bfs(int index) {
        /*
        pang 하게 되면,
        넘겨 받은 index 포인터 0, 1 null 로 만들고

        있다라면? 0 번째에 visited 박아 놓고
        */

        if ((points[index][0] == null) && (points[index][1] == null)) { // 없는 알파벳이면, return
            return;
        }

        visited = new int[H][W];

        for (int i = 0; i < H; i++) {
            Arrays.fill(visited[i], 10001);
        }

        Point start = points[index][0];
        Point end = points[index][1];
        int y = start.y;
        int x = start.x;
        char target = (char) (index + 65);

        Queue<Point> queue = new LinkedList<>();
        visited[y][x] = 0;

        for (int i = 0; i < 4; i++) { // 일단 4방향으로 다 생성해줌
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (outOfRange(ny, nx) || map[ny][nx] == '*'
                    || (map[ny][nx] != '.' && map[ny][nx] != target)) { // 원하는 알파벳이 아닐때도
                continue;
            }

            visited[ny][nx] = 0;
            queue.add(new Point(ny, nx, 0, i)); // 아직까지 방향 전환 x, 그리고 한칸 가서 존재
        }

        // System.out.println(queue);
        // visitedPrint();

        while (!queue.isEmpty()) {
            Point point = queue.poll();

            if (point.value >= 2) {
                continue;
            }

            if ((point.y == end.y) && (point.x == end.x)) {
                pang = true;
                map[start.y][start.x] = '.';
                map[end.y][end.x] = '.'; // 터트려 준다.
                points[index][0] = null;
                points[index][1] = null;
                break;
            }

            for (int i = 0; i < 4; i++) { // 반대로 가는 것은 안된다.
                // 1, 3 과 0, 2
                if (((i + 2) % 4) == point.dir) { // 반대 방향이면, 넘겨준다.
                    continue;
                }

                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                int value = point.value;

                if (i != point.dir) {
                    value++;
                }

                if (value >= 2) {
                    continue;
                }

                if (outOfRange(ny, nx) || map[ny][nx] == '*'
                        || (map[ny][nx] != '.' && map[ny][nx] != target)) {
                    continue;
                }

                if (value > visited[ny][nx]) {
                    continue;
                }

                visited[ny][nx] = value;
                queue.add(new Point(ny, nx, value, i));
            }
        }

        if (pang) {
            answer.append(target);
        }
    }

    public static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean check() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j] != '.' && map[i][j] != '*') { // 남은 대문자가 있으면
                    return false; // 실패한 경우
                }
            }
        }

        return true; // 실패하지 않은 경우
    }

    public static String solution(int m, int n, String[] board) {
        long start = System.currentTimeMillis();
        H = m;
        W = n;
        map = new char[H][W];

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                map[i][j] = board[i].charAt(j);

                if (Character.isUpperCase(map[i][j])) { // 문자라면
                    int index = (int) map[i][j] - 65;
                    if (points[index][0] == null) { // 아직 이 알파벳에 대해서는 받은 적이 없을 때
                        points[index][0] = new Point(i, j);
                    } else { // 이 알파벳에 대해서, 이미 들어온 사례가 있을 때
                        points[index][1] = new Point(i, j);
                    }
                }
            }
        }

        while (true) {
            for (int i = 0; i < 26; i++) {
                // System.out.println("Now Target : " + (char) (i + 65));
                bfs(i);
                if (pang) { // 터트리면 나감
                    // mapPrint();
                    break;
                }
            }

            if (!pang) { // 다 돌았는데, 터트린 게 없는 경우
                break;
            }

            pang = false; // 다시 false 로 만들어줌
        }

        System.out.println(System.currentTimeMillis() - start);

        if (check()) { // 제대로 끝난 건지 확인
            return answer.toString();
        } else {
            return "IMPOSSIBLE";
        }
    }

    public static void mapPrint() {
        System.out.println("map");
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void visitedPrint() {
        System.out.println("visited");
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(visited[i][j] + " ");
            }
            System.out.println();
        }
    }
}