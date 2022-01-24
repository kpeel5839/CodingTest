import java.util.*;
import java.io.*;

// 4991 : 로봇 청소기
/*
-- 전제 조건
로봇청소기가 있고 격자들이 주어진다.
칸은 깨끗한 칸과 더러운 칸으로 나누어져 있으며 , 로봇 청소기는 더러운 칸을 방문해서 깨끗한 칸으로 바꿀 수 있다.
로봇 청소기는 가구가 놓여있는 칸으로는 움직일 수 있다 , 그리고 로봇은 한 번 움직일 때 인접한 칸으로 이동할 수 있고
또 로봇은 같은 칸을 여러 번 방문할 수 있다.
방의 정보가 주어졌을 때 , 더러운 칸을 모두 깨끗한 칸으로 만드는데 필요한 이동 횟수의 최솟값을 구하는 프로그램을 작성하시오

입력으로 여러개의 테스트 케이스가 주어지고
문자는 '.' = 깨끗한 칸 , '*' = 더러운 칸 , 'x' = 가구 , 'o' = 로봇 청소기의 시작 위치
더러운 칸의 개수는 10개를 넘지 않으며 로봇청소기는 항상 하나이다
입력의 마지막 줄에는 0 이 두개가 주어진다.
그러면 입력의 종료인 것 , 테스트의 개수가 초기에 주어지지 않는다.
-- 틀설계
더러운 공간은 10개를 넘지 않는다..
그러면 362만이다 경우의 수가 그러면 이거를 하나하나 다 시도한다? 그러면 무조건 시간초과일 것이다.
bfs까지 진행하니까 , 하지만 이것을 그냥 가까운 곳부터 처리하기에는 너무 그리디하지 않은 것 같다.
일단 * 부분들을 list에다가 다 넣어놓는다. 그 다음에 배열을 만들어서 point들을 즉 더러운 곳의 위치를 저장해놓는다.
그 다음에 그 list의 길이만큼의 경우의 수를 dfs로 만들어준다.
그러면서 bfs를 계속 하면서 계산해나간다.
dfs에다가는 일단 로봇의 위치 , 그리고 방문 순서를 저장할 수가 있을 것 같다.
이제 각각의 visited 포인트를 저장해놓고 해당 dfs에서 해당 지점을 이미 방문했다? 그러면 거기서는 제외를 시키고 방문을 하는 것이다
그러한 방법으로 dfs와 bfs를 동시에하면서 할 수 있을 것 같다.
일단 dfs로 모든 순서들을 다 만들면서 bfs로 탐색하는 경우를 한번 해보자.
*/
public class Main {
    public static Point[] dirty;
    public static int[] visitedPointList, dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    public static char[][] map;
    public static int[][] dist;
    public static int[][] visited;
    public static int w, h, min = Integer.MAX_VALUE;

    public static void dfs(int depth, int moveCount, Point robot, int pointNumber) {
        /*
        여기서 계속 depth가 dirty의 length 와 같아지면 return 하고
        지금까지 이동 거리를 min 과 비교해서 갱신한다
        그러고서 일단 방문을 하게 될 것인데 여기서 내가 deepMap으로 하나 map을 deepCopy를
        받아서 bfs에다가 넘긴다 , 그러면 지금 현재 map과 인자로 넘어온 robot의 위치
        그리고 받은 int deepMap에다가 순서대로 값을 채워넣는다 map을 이용해서
        그러면 bfs가 끝나면 해당 맵에 다 값들이 찍혀있을 것이다.
        그러면 이제 순서대로 갈 수 있는 위치와 visitedPointList에 1로 찍혀있는지만 확인하면 된다.
        그러면서 dfs를 돌리면 되는데
        그렇게 되면 10개인 경우 362만번을 bfs를 다 돌리게 된다 그러면 오버헤드가 너무 크다.
        그렇기 때문에 배열을 만들어서 해당 dist[n][n] 을 만들어서 특정 n의 지점으로부터 bfs를 구하면
        그것은 다시 bfs를 호출하지 않아도 되는 것이다.
        만일 robot의 현재 위치가 point의 i 번째에 있다 그러면 dist[i] 에 값이 하나라도 존재하면 bfs로 한번 더 탐색할 이유가 없는 것이다.
        그럴려면 근데 지금 현재 방문하는 point번호도 알아야함 그 숫자가 -1이면 로봇으로 인식하고 한번해보자
        근데 dist로 bfs를 한번 더 할지 말지 정하는 경우에서 문제는 먼지가 하나만 존재하는 경우임 그 경우는 예외로 처리해서
        하자 그러면 dirty.length == 1 인 경우에는 그냥 바로 robot에서
         */
        if (depth == dirty.length) {
            min = Math.min(moveCount, min);
            return;
        }
        int[][] deepMap = new int[h][w];
        if (pointNumber == -1) { //여기서는 pointNumber가 -1일 때 즉 처음에 위치일 때 bfs를 구하는 과정이다.
            bfs(robot, deepMap);
            for (int i = 0; i < dirty.length; i++) {
                if (deepMap[dirty[i].y][dirty[i].x] == -1) {
                    return;
                }
            }
            if (dirty.length == 1) {
                min = Math.min(deepMap[dirty[0].y][dirty[0].x], min);
                return;
            }
        } else { // 시작 위치가 아닌 경우
            boolean decide = false;
            for (int i = 0; i < dirty.length; i++) { // 여기서 만일 0이 아닌 게 나오게 되면 bfs를 한번 더 시도하지 않아도 됨
                if (dist[pointNumber][i] != 0) {
                    decide = true;
                    break;
                }
            }
            if (!decide) { // bfs를 시도해야 하는 경우
                bfs(robot, deepMap);
                for (int i = 0; i < dirty.length; i++) { //dist를 채워넣는 과정
                    if (pointNumber != i) {
                        dist[pointNumber][i] = deepMap[dirty[i].y][dirty[i].x]; // visited로 채운 것들을 dist에다가 집어넣음
                    }
                }
            }
        }

        // 이제 for문으로 본인과 방문한 곳을 제외하고 moveCount를 추가해주면서 가면 된다.
        for (int i = 0; i < dirty.length; i++) {
            if (i != pointNumber && visitedPointList[i] != 1) {
                visitedPointList[i] = 1;
                int addValue = deepMap[dirty[i].y][dirty[i].x];
                if (pointNumber != -1) {
                    addValue = dist[pointNumber][i];
                }
                dfs(depth + 1, moveCount + addValue, dirty[i], i);
                visitedPointList[i] = 0;
            }
        }
    }

    public static void bfs(Point robot, int[][] deepMap) {
        visited = new int[h][w];
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(robot.y, robot.x, 0));
        deepMap[robot.y][robot.x] = 0;
        while (!queue.isEmpty()) {
            Point point = queue.poll();
            int value = point.value;
            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if (ny < 0 || ny >= h || nx < 0 || nx >= w || visited[ny][nx] == 1 || map[ny][nx] == 'x') {
                    continue;
                }
                visited[ny][nx] = 1;
                deepMap[ny][nx] = value + 1;
                queue.add(new Point(ny, nx, value + 1));
            }
        }
    }

    public static class Point {
        /*
        이것은 queue 에도 쓰이고 (bfs)
        그리고 dirty point를 저장하는 것에도 쓰일 것이다.
         */
        int y;
        int x;
        int value;

        public Point(int y, int x, int value) {
            this.y = y;
            this.x = x;
            this.value = value;
        }

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public String toString() {
            return "y : " + y + " x: " + x;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(input.readLine());
            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());
            min = Integer.MAX_VALUE;
            Point robot = null;
            visited = new int[h][w];
            map = new char[h][w];
            List<Point> dirtyList = new ArrayList<>();
            if (h == 0 && w == 0) {
                break;
            }
            for (int i = 0; i < h; i++) {
                String string = input.readLine();
                for (int j = 0; j < w; j++) {
                    map[i][j] = string.charAt(j);
                    if (map[i][j] == 'o') {
                        robot = new Point(i, j);
                        map[i][j] = '.';
                    }
                    if (map[i][j] == '*') {
                        dirtyList.add(new Point(i, j));
                        map[i][j] = '.'; //그냥 로봇위치랑 더러운 부분 다 점으로 처리해서 귀찮지 않게
                    }
                }
            }
            dirty = new Point[dirtyList.size()];
            visitedPointList = new int[dirtyList.size()];
            dist = new int[dirtyList.size()][dirtyList.size()];
            for (int i = 0; i < dirtyList.size(); i++) {
                dirty[i] = dirtyList.get(i);
            }
            dfs(0, 0, robot, -1);
            output.write((min == 0 ? -1 : min) + "\n");
        }
        output.flush();
        output.close();
    }
}