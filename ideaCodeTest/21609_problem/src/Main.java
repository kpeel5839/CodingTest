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
2. rotate 함수를 만든다.
3. gravity 함수를 만든다. (쭉 떨어지는 아래로)
4. bfs를 함수를 만든다. (여기서는 그룹을 찾을 때 기준원소와 , 블록의 개수 , 무지개 블록의 개수를 알아내야함 , 그리고 bfs로 2개 이상의 그룹이 형성 되지 않으면 아무것도 반환하지 않는다.)
5. 이제 getScore 함수를 만든다. (점수를 얻는)
6. while(true) 로 하는데 gameOver 를 하고 모든 맵을 for문으로 돌면서 gameOver 가 아직도 true이면 break; 한다.
7. while 안에서는 먼저 for문으로 bfs를 하나하나 돌고 bfs가 끝나면 getScore를 한 뒤에 gravity() 이후 rotate() 이후 gravity() 적용하고를 반복한다.
8. 그리고서 if(gameOver) break; 이렇게 하면된다.
 */
public class Main {
    public static boolean gameOver = true;
    public static int n, m, score = 0;
    public static int[][] map, visited;
    public static int[] dx = {0, 1, 0, -1}, dy = {-1, 0, 1, 0};
    public static List<Point> pivot = new ArrayList<>();
    public static List<Integer> groupSize = new ArrayList<>(), rainbow = new ArrayList<>();

    public static class Point implements Comparable<Point> {
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

    public static void rotate() {
        /*
        1. 맵을 90도 반시계 방향으로 회전시켜주는 역할
        2. 끝에 열부터 순서대로 읽어서 위치시키면 됨
        3. 끝에 열 첫행 부터 순서대로 읽으면서 원래의 map에다가 투입
         */
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

    public static void gravity() {
        /*
        1. dir = 2 로 계속하면 된다.
        2. 빈칸은 유지되어야하기 때문에
        3. 제일 아래 행 부터 시작해서 떨어트리는 과정을 진행할 것인데 , -2는 빈칸이니까 오히려 갈 수 있는 것을 다음 칸이 -2이면 갈 수 있도록 설정하면 될 듯
        4. down() 함수를 만들어서 행과 열을 주면은 그냥 떨어뜨리는 함수를 만드는 것이 좋을 듯
        5. 그걸 끝행까지 진행하면 된다.
         */
        for (int i = n - 1; i != -1; i--) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] != -2 && map[i][j] != -1) {
                    down(i, j);
                }
            }
        }
    }

    public static void down(int y, int x) {
        /*
        1. 행과 열을 받으면 그냥 다음칸이 -2이면 갈 수 있도록 하고 아니면 스탑 그리고 격자 밖에 나가는지 계속 확인한다.
        2. 쨌든 그렇게 계속 내려가다가 최종 이 블록이 위치해야 할 지점을 찾으면 거기다가 이 블록의 값을 놓고 이전에 있던 공간은 -2로 바꾼다.
        3. 시작할 때 증가하고 시작하는데 바로 걸리면 그냥 변경사항 없고.
        4. 만약 while에서 찾아서 나왔으면 다시 ny - dy[0] 이런식으로 돌려서 적용하면됨
         */
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

    public static void bfs(int y, int x) {
        /*
        1. queue에다가 일단 집어넣는다.
        2. int size = 1로 설정한다 , rainbowCount = 0으로 설정한다.
        3. 마지막에 그리고 내 innerList에다가 계속 들어온 queue에다가 point들을 정리한다.
        4. 그 다음에 bfs로 돌면서 블록을 넣을 때마다 size += 1을 해주고 만일 값이 0 이면 rainbowCount += 1도 해준다.
        5. 계속 innerPoint에다가도 계속 넣어준다 그리고 맨 마지막에 size가 만약 1이면 그냥 바로 끝내고 아니면 innerPoint를 Collections.sort를 해준다음
        6. 제일 앞에있는 것을 pivot에다가 추가해준다.
        7. 그리고 순서대로 size 는 groupSize , rainbow 개수는 rainbow 리스트에다가 넣는다.
         */
        rainbowClean();
        Queue<Point> queue = new LinkedList<>();
        List<Point> innerList = new ArrayList<>();
        queue.add(new Point(y, x));
        innerList.add(new Point(y, x));
        visited[y][x] = 1;
        int value = map[y][x];
//        System.out.println("value : " +value);
        int size = 1, rainbowCount = 0;
        while (!queue.isEmpty()) {
            Point point = queue.poll();
            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if (ny < 0 || ny >= n || nx < 0 || nx >= n || visited[ny][nx] == 1 || (value != map[ny][nx] && map[ny][nx] != 0)) {
                    continue;
                }
                size++;
                if (map[ny][nx] == 0) {
                    rainbowCount++;
                } else{
                    innerList.add(new Point(ny, nx));
                }
                visited[ny][nx] = 1;
                queue.add(new Point(ny, nx));
            }
        }
//        System.out.println(size);
        if (size != 1) {
            gameOver = false;
            Collections.sort(innerList);
//            System.out.println(innerList.get(0).y + "," + innerList.get(0).x);
            groupSize.add(size);
            rainbow.add(rainbowCount);
            pivot.add(innerList.get(0));
        }
    }

    public static boolean check(int[] check) {
        int count = 0;
        for (int i = 0; i < check.length; i++) {
            if (check[i] == 1) {
                count++;
            }
        }
        if (count == 1) {
            return true;
        }
        return false;
    }

    public static void getScore() {
        /*
        1. 원소를 없애고 점수를 올려야함
        2. 없앤 원소는 -2로 정하자 , -2는 빈칸이라고 생각하면 될 듯
        3. 없앤 원소를 -2로 안바꾸면 애초에 성립이 안됨
        4. 여기서 비교를 잘해야함
        5. 일단 첫째로 groupSize에서 제일 큰 것을 찾는다. 여기서 같은 게 여러개이면 , 무지개 블록의 개수를 비교해야한다, 이것도 만약에 큰 것중 같은게 여러개이면 pivot에서 비교해서 얻어야한다.
        6. select 배열로 1인게 선택된 것들이고 만약에 이제 최종적으로 1이 하나만 남게 되면 그때 그 1이 있는 index로 pivot에서 뽑아내서 score += (int)Math.pow(groupSize[index] , 2) 로 해주고
        7. 다 지 -2로 만들어주면 된다 bfs로 만나는 것들은
        8. 그냥 내부에다가 작게 구현하자
         */
        System.out.println(groupSize);
        System.out.println(rainbow);
        System.out.println(pivot);
        int max = 0;
        int[] select = new int[pivot.size()];
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (Integer number : groupSize) {
                if (i == 0) {
                    max = Math.max(max, number);
                } else {;
                    if (max == number) {
                        select[index] = 1;
                    }
                    index++;
                }
            }
        }
//        System.out.println(max);
        System.out.println(Arrays.toString(select));
        if (!check(select)) {
            max = 0;
            for (int i = 0; i < 2; i++) {
                index = 0;
                for (Integer number : rainbow) {
//                    System.out.println(number);
                    if (i == 0) {
                        if (select[index++] == 1) {
                            max = Math.max(max , number);
                        }
                    } else {
                        if (select[index] == 1) {
                            if (max == number) {
                                select[index] = 1;
                            } else {
                                select[index] = 0;
                            }
                        }
                        index++;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(select));
        if (!check(select)) {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < 4; i++) {
                index = 0;
                if (i == 2) {
                    min = Integer.MAX_VALUE;
                }
                for (Point point : pivot) {
                    if (i == 0) {
                        if (select[index++] == 1) {
                            min = Math.min(min, point.y);
                        }
                    } else if (i == 1) {
                        if (select[index] == 1) {
                            if (min == point.y) {
                                select[index] = 1;
                            } else {
                                select[index] = 0;
                            }
                        }
                        index++;
                    } else if (i == 2) {
                        if (select[index++] == 1) {
                           min = Math.min(min, point.x);
                        }
                    } else {
                        if (select[index] == 1) {
                            if (min == point.x) {
                                select[index] = 1;
                            } else {
                                select[index] = 0;
                            }
                        }
                        index++;
                    }
                }
            }
        }
        index = 0;
        System.out.println(Arrays.toString(select));
        for(int i = 0; i < select.length; i++){
            if(select[i] == 1){
                index = i;
                break;
            }
        }
//        System.out.println(pivot.size());
        Queue<Point> queue = new LinkedList<>();
        int y = pivot.get(index).y;
        int x = pivot.get(index).x;
//        System.out.println("y: " + y + " x : " +x);
        score += (int)Math.pow(groupSize.get(index) , 2);
        queue.add(new Point(y , x));
        clean();
        visited[y][x] = 1;
        int value = map[y][x];
        map[y][x] = -2;
//        System.out.println(value);
        while(!queue.isEmpty()){
            Point point = queue.poll();
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= n || nx < 0 || nx >= n || visited[ny][nx] == 1 || (value != map[ny][nx] && map[ny][nx] != 0)){
//                    System.out.println("ny : " + ny + " nx : " + nx);
                    continue;
                }
                visited[ny][nx] = 1;
                queue.add(new Point(ny, nx));
                map[ny][nx] = -2;
            }
        }
        groupSize = new ArrayList<>();
        rainbow = new ArrayList<>();
        pivot = new ArrayList<>();
//        System.out.println("----------getScore after---------------");
//        for(int i = 0; i < n; i++){
//            for(int j = 0; j < n; j++){
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }
//        clean();
    }

    public static void clean() {
        /*
        1. visited 의 모든 원소를 0 으로 만들어주는 역할
         */
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                visited[i][j] = 0;
            }
        }
    }
    public static void rainbowClean(){
        /*
        1. rainbow 즉 0인 것들의 visited 만 0으로 바꿔주면 된다.
         */
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(map[i][j] == 0){
                    visited[i][j] = 0;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
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
//            clean();
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
            getScore();
            gravity();
            rotate();
            gravity();
            clean();
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
