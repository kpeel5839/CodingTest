import java.util.*;
import java.io.*;

// 23289 : 온풍기 안녕!
/*
-- 전제조건
1. 방향은 8가지의 방향이 존재함
2. 온풍기는 여러개 설치 될 수가 있음
3. 온풍기의 방향은 방향을 기준으로 순서대로 (x, y) -> (x - 1, y + 1) , (x , y + 1) , (x + 1, y + 1) 이런식으로 퍼져나감
4. 온도는 처음은 5 , 4 , 3 , 2 , 1 이렇게 퍼져나가고 해당 온도만큼 올라감
5. 그리고 온도가 조절됨 모든 인접한 칸에 대해서 , 온도가 높은 칸에서 낮은 칸으로 Math.floor((두 칸의 온도의 차이 / 4)) 로 차이난다.(모든 칸에서 동시에 일어나는 것에 주의해야 한다.)
6. 인접한 칸이 온도가 변할 때에는 무조건 그냥 동시에 일어나는 것이고 Math.floor((높은 칸 - 낮은 칸) / 4) 임을 잊으면 안됨
7. 그 다음에 온도가 1 이상인 가장 바깥쪽 칸의 온도가 1씩 감소한다. (테두리)
8. 초콜릿을 하나 먹고 , 모든 칸의 온도가 k 이상이 되었는지 검사한다. 모든 칸의 온도가 k 이상이면 테스트를 중단 한다.
9. 총 초콜릿을 얼마나 먹었는지 출력 (온풍기가 있는 곳에도 온도가 존재한다.)
-- 틀 설계
1. 입력을 받는다.
2. array는 visited , map , wallY(가로로 그어진 벽) , wallX(세로로 그어진 벽) , dy , dx , heater(처음에 받은 heater , 그냥 이거 방향대로 넣어주고 하면 되니까 따로 맵에 그리지 않을 예정) 8방향 을 만들어준다.
3. check() 이 움직임이 벽에 가로 막히는지 안막히는지 확인하는 함수
4. spread() 모든 격자를 돌면서 온도를 변화시키는 부분
5. edgeDown() 바깥쪽 온도를 떨어뜨리는 부분
6. clean() visited를 초기화 할 부분이다.
7. heating() 모든 온풍기를 통해서 온도를 높이는 부분
8. success() 를 해서 온도가 k 도 이상이 되었는지 5로 주어진 정점만 확인한다.
9. 처음에 heating()(check() , clean()) -> spread()(check()) -> edgeDown() -> chocolate++ -> if(success()) break; 이렇게
-- 해맸던 점
1. 갈 수 있는지 가지 못하는 지 결정하는 check 함수에서 heater의 방향에 따라서 어떠한 벽에 막히는지를 정확히 이해하지 못하고 하였었음 (재귀적으로 해결함)
2. spread에서 그냥 하나하나 반복문 돌면서 되게 간단하게 온도를 나눴으면 됐는데 괜히 bfs로 생각해서 복잡하게 생각했었음
3. 제일 외곽에 있는 온도들을 내리는 edgeDown 함수를 꼭짓점들이 2가 온도가 떨어지게 했었음
4. heater에서도 innerDir를 처음에 사용해서 continue 되었을 때 방향이 바뀌지 않았었음 그래서 완전히 다른 결과가 나왔었음
5. 역시나 항상 그렇듯 설계는 괜찮았으나 낮은 문제 이해력과 잦은 실수로 시간이 너무 오래걸렸었음 , 항상 더 생각하고 풀것
 */
public class Main {
    public static int r, c, k, chocolate = 0;
    public static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1} , dy = {-1, -1, 0, 1, 1, 1, 0, -1}; // 1 = 방향이 오른쪽 , 2 = 방향이 왼쪽 , 3 = 방향이 위 , 4 = 방향이 아래
    public static Point[] heaters;
    public static Point[] checkList;
    public static int[][] visited, map, wallY, wallX;
    public static boolean check(Point before, Point after, int heaterDir, int dir) {
        /*
        1. before , after pointer 를 받고 어느 지점의 벽을 확인해야 할지 결정하고 벽이 있는지 없는지 확인하고서 봐야함
        2. 일단 그냥 (y + 1 , x) 혹은 (y , x + 1)에 대해서만 처리를한다.
        3. 그리고 대각으로 오는 것들은 재귀적으로 해결한다.
        4. 만일 온풍기의 dir이 지금 0이라고 치자 근데 이제 지금 현재 7로 들어왔다 그러면 6 , 0 이렇게 가야함
        5. 만일 온풍기의 dir = 0 에다가 현재 dir = 1이다 그러면 2 -> 0 으로 가야한다.
        6. 만일 온풍기의 dir = 2 에다가 현재 dir = 1이다 그러면 0 -> 2
        7. 만일 온풍기의 dir = 2 에다가 현재 dir = 3이다 그러면 4 -> 2
         */
        if (dir % 2 == 0) {
            if ((before.x == after.x) && (wallY[Math.min(after.y , before.y)][before.x] == 1)) {
                return false;
            }
            if ((before.y == after.y) && (wallX[before.y][Math.min(before.x, after.x)] == 1)) {
                return false;
            }
        } else {
            int firstDir;
            int secondDir = heaterDir;

            if ((heaterDir - 1 < 0 ? 7 : heaterDir - 1) == dir) {
                firstDir = dir - 1 < 0 ? 7 : dir - 1;
            } else {
                firstDir = dir + 1 == 8 ? 0 : dir + 1;
            }

            after = new Point(before.y + dy[firstDir] , before.x + dx[firstDir]);
            boolean first = check(before , after , heaterDir , firstDir);
            before = after;
            after = new Point(before.y + dy[secondDir] , before.x + dx[secondDir]);
            boolean second = check(before , after , heaterDir , secondDir);

            if (!first || !second) {
                return false;
            }
        }
        return true;
    }
    public static void spread() {
        /*
        1. map을 일단 복사해놓고서 모든 정점을 돌면서 모든 방향으로 다 체크한다 , 0 , 2 , 4 , 6이다 4방향은
        2. 그리고서 계속 check를 해준다 이전 point와 다음 point를 계속 인자로 넣어주면서 체크해준다
        3. 일단 범위를 벗어나는 것은 제외해주면서 계속
         */
        int[][] deepMap = new int[r][c];
        for (int i = 0; i < map.length; i++) {
            System.arraycopy(map[i] , 0 , deepMap[i] , 0 , map[i].length);
        }
        for (int i = 0; i < r; i++) {
            for (int j = 1; j < c; j++) { //2
                if (check(new Point(i, j - 1) , new Point(i , j) , 2 , 2)) {
                    int beforeValue = deepMap[i][j - 1];
                    int afterValue = deepMap[i][j];
                    int high = Math.max(deepMap[i][j - 1] , deepMap[i][j]);
                    Point highPoint;
                    Point lowPoint;
                    if (high == beforeValue) {
                        highPoint = new Point(i , j - 1);
                        lowPoint = new Point(i , j);
                    } else {
                        highPoint = new Point(i , j);
                        lowPoint = new Point(i , j - 1);;
                    }
                    int gap = (int) Math.floor((Math.max(beforeValue , afterValue) - Math.min(beforeValue , afterValue)) / 4d);
                    map[highPoint.y][highPoint.x] -= gap;
                    map[lowPoint.y][lowPoint.x] += gap;
                }
            }
        }
        for (int i = 0; i < c; i++) {
            for (int j = 1; j < r; j++) { //4
                if (check(new Point(j - 1, i) , new Point(j , i) , 4 , 4)) {
                    int beforeValue = deepMap[j - 1][i];
                    int afterValue = deepMap[j][i];
                    int high = Math.max(deepMap[j - 1][i] , deepMap[j][i]);
                    Point highPoint;
                    Point lowPoint;
                    if (high == beforeValue) {
                        highPoint = new Point(j - 1 , i);
                        lowPoint = new Point(j , i);
                    } else {
                        highPoint = new Point(j , i);
                        lowPoint = new Point(j - 1 , i);;
                    }
                    int gap = (int) Math.floor((Math.max(beforeValue , afterValue) - Math.min(beforeValue , afterValue)) / 4d);
                    map[highPoint.y][highPoint.x] -= gap;
                    map[lowPoint.y][lowPoint.x] += gap;
                }
            }
        }
    }
    public static void edgeDown() {
        /*
        1. 끝쪽에 존재하는 것들 감소
        2. 온도 0도 인것은 그냥 그대로 두고 0 아니면 1감소시키면 됨
         */
        int y = 0 , x = 0 , dir = 2;
        if(map[y][x] != 0) map[y][x]--;
        for(int i = 0; i < 4; i++){
            int end = 0;
            if(i % 2 == 0){
                end = c;
            }else{
                end = r;
                if(i == 3){
                    end = r - 1;
                }
            }
            for(int j = 0; j < end - 1; j++){
                y += dy[dir];
                x += dx[dir];
                if(map[y][x] != 0){
                    map[y][x]--;
                }
            }
            dir = dir + 2 == 8 ? 0 : dir + 2;
        }
    }
    public static void clean() {
        /*
        1. 이건 그냥 visited를 clean 시켜주면 됨
         */
        for (int i = 0;i < r; i++) {
            for (int j = 0; j < c; j++) {
                visited[i][j] = 0;
            }
        }
    }
    public static void heating() {
        /*
        1. 모든 heaters를 돌면서 해당 방향으로 처리해주어야함
        2. 해당 온풍기가 가진 방향으로 하나 증가시켜줘서 그게 범위에 맞는지 확인한 다음에 check 까지 해서 queue에다가 집어넣는다.
        3. dir은 방향이 0이라면 7 , 0 , 1 를 가진다
        4. 현재 온풍기의 dir로 먼저 start로 5로 추가해놓고서(QueuePoint)로 그러고서 계속 dir - 1 에서 계속 해준다.
         */
        Queue<QueuePoint> queue = new LinkedList<>();
        for (int i = 0; i < heaters.length; i++) {
            queue.clear();
            int dir = heaters[i].dir;
            int startDir = dir - 1 < 0 ? 7 : dir - 1;
//            System.out.println(startDir);
            int y = heaters[i].y + dy[dir] , x = heaters[i].x + dx[dir];
            if ((y < 0 || y >= r) || (x < 0 || x >= c)) {
                continue;
            }
            clean();
            if (check(new Point(heaters[i].y , heaters[i].x) , new Point(y , x) , dir , dir)) {
               queue.add(new QueuePoint(y , x , 5));
            }
            visited[y][x] = 1;
            map[y][x] += 5;
            while (!queue.isEmpty()) {
                QueuePoint point = queue.poll();
                int value = point.value;
                if (value == 0) {
                    break;
                }
                for (int j = startDir; j != ((dir + 1 == 8 ? 0 : dir + 1) + 1 == 8 ? 0 : dir + 2); j = (j + 1 == 8 ? 0 : j + 1)) {
                    int ny = point.y + dy[j];
                    int nx = point.x + dx[j];
                    if ((ny < 0 || ny >= r) || (nx < 0 || nx >= c) ||
                            (visited[ny][nx] == 1 || !check(new Point(point.y , point.x) , new Point(ny , nx) , dir , j))) {
                        continue;
                    }
                    visited[ny][nx] = 1;
                    map[ny][nx] += value - 1;
                    queue.add(new QueuePoint(ny , nx , value - 1));
                }
            }
        }
    }
    public static boolean success() {
        /*
        1. checkList 를 순서대로 다 돌면서 온도가 k 이상인지 확인함
        2. 아니면 false 맞으면 true 반환
         */
        for (int i = 0; i < checkList.length; i++) {
            Point point = checkList[i];
            if (map[point.y][point.x] < k) {
                return false;
            }
        }
        return true;
    }
    public static class QueuePoint {
        int y;
        int x;
        int value;
        public QueuePoint(int y, int x, int value) {
            this.y = y;
            this.x = x;
            this.value = value;
        }
    }
    public static class Point {
        int y;
        int x;
        int dir;
        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
        public Point(int y ,int x , int dir) {
            this.y = y;
            this.x = x;
            this.dir = dir;
        }
    }
    public static void main(String[] args) throws IOException {
        /*
        1. wallY , wallX 어떻게 입력 받을 것인지 결정해보자
        2. 일단 뒤에 type = 1이면 wallX에 추가 , type = 0 이면 wallY에 추가가 됨
        3. wallX 만약 1 7 1 이라면 0, 6에다가 추가해주면 된다. 그니까 [y - 1][x - 1] 에다가 1 넣어주면 됨
        4. wallY 이다 만일 5 4 0 이다 이 경우에는 [y - 2][x - 1] 에다가 추가해주면 된다.
         */
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new int[r][c];
        visited = new int[r][c];
        wallX = new int[r][c - 1];
        wallY = new int[r - 1][c];

        Queue<Point> heaterList = new LinkedList<>();
        Queue<Point> tempCheckList = new LinkedList<>();

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(input.readLine());
            for (int j = 0; j < c; j++) {
                int value = Integer.parseInt(st.nextToken());
                if (value == 5) {
                    tempCheckList.add(new Point(i , j));
                } else if(value != 0) { //1 = 방향이 오른쪽 , 2 = 방향이 왼쪽 , 3 = 방향이 위 , 4 = 방향이 아래 현재는 0 = 위 , 2 = 오른쪽 , 4 = 아래 , 6 = 왼쪽
                    // 3 -> 0 , 1 -> 2 , 2 -> 6 , 4 -> 4
                    value = value == 3 ? 0 : value == 1 ? 2 : value == 2 ? 6 : value;
                    heaterList.add(new Point(i , j , value));
                }
            }
        }

        heaters = new Point[heaterList.size()];
        checkList = new Point[tempCheckList.size()];

        for (int i = 0; i < heaters.length; i++) {
            heaters[i] = heaterList.poll();
        }

        for (int i = 0; i < checkList.length; i++) {
            checkList[i] = tempCheckList.poll();
        } // 다 확인

        int wallCount = Integer.parseInt(input.readLine());
        for (int i = 0; i < wallCount; i++) {
            st = new StringTokenizer(input.readLine());
            int y = Integer.parseInt(st.nextToken()) , x = Integer.parseInt(st.nextToken()) , type = Integer.parseInt(st.nextToken());
            if (type == 1) { //wallX 에다가
                wallX[y - 1][x - 1] = 1;
            } else { //wallY 에다가
                wallY[y - 2][x - 1] = 1;
            }
        }

        for (int i = 0; i <= 100; i++) {
            heating();
            spread();
            edgeDown();
            chocolate++;
            if(success()){
                break;
            }
        }

        System.out.println(chocolate);
    }
    public static void mapPrint() {
        System.out.println("---------mapPrint------------");

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
