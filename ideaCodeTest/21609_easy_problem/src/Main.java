import java.util.*;
import java.io.*;

// 21609 : 상어 중학교
/*
-- 전제조건
1. 그냥 상하좌우 칸을 인접한 칸으로 표현한다.
2. -1은 검은색 블록 , 무지개 블록은 0 그리고 나머지 양수는 그냥 일반블록이고 각 숫자가 색깔을 의미한다.
3. 그리고 M이 주어지는데 M가지의 색상이 있다 , 즉 M이 주어지면 그 이하의 숫자들이 주어진다는 것이다.
4. 블록 그룹은 무조건 일반 블록이 적어도 하나는 존재하는 그룹이여야 한다. 그러니까 무조건 블록 그룹의 시작은 일반 블록인 것이다.
5. 블록 그룹에 무지개 블록은 얼마나 들어있든 상관이 없다 , 그룹에 속한 블록의 개수는 무조건 2개 이상이여야 한다. (검은색 블록은 그냥 벽이라고 생각하면 된다.)
6. 그리고 블록의 그룹은 모두 인접하게 연결이 되어있어야 한다.
7. 블록 그룹의 기준 블록은 그냥 블록을 순회했을 때의 가장 왼쪽 위에있는 블록이 기준 블록이다. collection sort하면 될 듯하다.
8. 이제 게임이 시작하면 크기가 가장 큰 블록 그룹을 찾고, 그게 여러개이면 무지개 블록이 가장 많은 블록을 찾는다.
9. 그것도 여러개이면 그 중 기준 블록이 가장 행의 첫번째에 있는 것을 해야한다 이건 무조건 Collections.sort를 사용해야 할 듯
10. 이제 그 블록 그룹을 찾으면 제거하고 해당 블록 그룹에 포함된 블록의 수의 제곱만큼을 점수를 획득한다.
11. 중력이 작용하고 rotate 되고 중력이 또 작동한다.
12. 그 다음에 계속 이 행동을 반복하는데 블록 그룹이 하나라도 있으면 계속 시도한다.
-- 틀 설계
1. 입력을 받는다.
2. for문으로 gameOver가 나올 때까지 루프를 돈다.
3. 모든 맵을 bfs로 돌고서 사라질 블록 그룹의 기준원소를 찾는다.
4. getScore를 하면 기준원소를 기준으로 점수도 얻고 블록들도 없앤다.
5. 이것을 끝날 때까지 반복한다.
-- 해맸던 점
1. bfs를 다시 돌 때 rainbow 칸들의 visited를 초기화 해줘야한다는 것을 생각못했었음
2. 그냥 clean해버리면 그냥 계속 bfs다 돌아서 짜피 시작 원소는 그냥 일반블록 밖에 안되니까 그냥 한번 돌때마다 rainbow만 clean하면 됨
3. 그리고 없앨 블록 그룹을 찾을 때 괜히 어렵게 생각했었음 bfs 돌면서 그때그때 찾았으면 됐었는데
4. 그리고 자꾸 기준원소를 getScore할 때 -2로 바꾸지 않아서도 다른 답이 나왔었음
 */

public class Main {
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static int n , m , score = 0 , groupSize = 0 , rainbowCount = 0;
    public static int[][] map , visited;
    public static boolean gameOver;
    public static Point pivot = new Point(0, 0);
    public static void bfs(int y, int x){
        Queue<Point> queue = new LinkedList<>();
        List<Point> innerList = new ArrayList<>();
        visited[y][x] = 1;
        int size = 1 , rainbow = 0 , value = map[y][x];
        queue.add(new Point(y , x));
        innerList.add(new Point(y , x));
        rainbowClean();
        while(!queue.isEmpty()){
            Point point = queue.poll();
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= n || nx <0 || nx >= n || visited[ny][nx] == 1 || (value != map[ny][nx] && map[ny][nx] != 0)){
                    continue;
                }
                queue.add(new Point(ny , nx));
                visited[ny][nx] = 1;
                size++;
                if(map[ny][nx] == 0){
                    rainbow++;
                } else{
                    innerList.add(new Point(ny , nx));
                }
            }
        }
        /*
        1. 처음에는 groupSize 비교하고
        2. groupSize가 같으면 rainbowCount 비교하고
        3. rainbowCount까지 같으면 pivot의 y , x 를 비교해서 한다.
         */
        if(size == 1){
            return;
        }
        gameOver = false;
        boolean change = false;
        Collections.sort(innerList);
        Point point = innerList.get(0);
        if(groupSize < size){
            change = true;
        } else if(groupSize == size){
            if(rainbowCount < rainbow){
                change = true;
            }else if(rainbowCount == rainbow){
                if(pivot.y < point.y){
                    change = true;
                } else if(pivot.y == point.y){
                    if(pivot.x < point.x){
                        change = true;
                    }
                }
            }
        }
        if(change){
            pivot = new Point(point.y , point.x);
            rainbowCount = rainbow;
            groupSize = size;
        }
    }
    public static void rotate(){
        int[][] copyMap = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(map[i], 0, copyMap[i], 0, map[i].length);
        }
        int y = 0;
        int x = 0;
        for (int i = n - 1; i != -1; i--) {
            for (int j = 0; j < n; j++) {
                map[y][x++] = copyMap[j][i];
            }
            y++;
            x %= n;
        }
    }
    public static void rainbowClean(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(map[i][j] == 0){
                    visited[i][j] = 0;
                }
            }
        }
    }
    public static void clean(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                visited[i][j] = 0;
            }
        }
    }
    public static void getScore(){
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(pivot.y , pivot.x));
        visited[pivot.y][pivot.x] = 1;
        score += (int)Math.pow(groupSize , 2);
        int value = map[pivot.y][pivot.x];
        map[pivot.y][pivot.x] = -2;
        while(!queue.isEmpty()){
            Point point = queue.poll();
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= n || nx < 0 || nx >= n || visited[ny][nx] == 1 || (value != map[ny][nx] && map[ny][nx] != 0)){
                    continue;
                }
                visited[ny][nx] = 1;
                map[ny][nx] = -2;
                queue.add(new Point(ny,nx));
            }
        }
        clean();
    }
    public static void gravity(){
        for (int i = n - 1; i != -1; i--) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] != -2 && map[i][j] != -1) {
                    down(i, j);
                }
            }
        }
    }
    public static void down(int y, int x){
        int nx = x;
        int ny = y;
        while (true) {
            nx = nx + dx[2];
            ny = ny + dy[2];
            if (nx < 0 || nx >= n || ny < 0 || ny >= n || map[ny][nx] != -2) {
                break;
            }
        }
        ny -= dy[2];
        nx -= dx[2];
        int temp = map[y][x];
        map[y][x] = -2;
        map[ny][nx] = temp;
    }
    public static class Point implements Comparable<Point>{
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public int compareTo(Point other) {
            if (this.y > other.y) {
                return 1;
            } else if (this.y == other.y) {
                return this.x - other.x;
            } else {
                return -1;
            }
        }
        @Override
        public String toString(){
            return "y : " + y + " x : " + x;
        }
    }
    public static void reset(){
        pivot = new Point(0 , 0);
        groupSize = 0;
        rainbowCount = 0;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        visited = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(input.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int count = 0;
        while (true) {
            gameOver = true;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (visited[i][j] != 1 && map[i][j] > 0) {
                        bfs(i, j);
                    }
                }
            }
            if(gameOver) {
                break;
            }
            clean();
            getScore();
//            System.out.println("pivot : " + pivot);
//            System.out.println("-------getScore after-------------");
//            for(int i = 0; i < n; i++){
//                for(int j = 0; j < n; j++){
//                    System.out.print(map[i][j] + " ");
//                }
//                System.out.println();
//            }
            gravity();
//            System.out.println("-------gravity after-------------");
//            for(int i = 0; i < n; i++){
//                for(int j = 0; j < n; j++){
//                    System.out.print(map[i][j] + " ");
//                }
//                System.out.println();
//            }
            rotate();
//            System.out.println("-------rotate after-------------");
//            for(int i = 0; i < n; i++){
//                for(int j = 0; j < n; j++){
//                    System.out.print(map[i][j] + " ");
//                }
//                System.out.println();
//            }
            gravity();
//            System.out.println("-------gravity again after-------------");
//            for(int i = 0; i < n; i++){
//                for(int j = 0; j < n; j++){
//                    System.out.print(map[i][j] + " ");
//                }
//                System.out.println();
//            }
            reset();
//            System.out.println("-------" + ++count + " after-------------");
//            for(int i = 0; i < n; i++){
//                for(int j = 0; j < n; j++){
//                    System.out.print(map[i][j] + " ");
//                }
//                System.out.println();
//            }
        }
        System.out.println(score);
    }
}
