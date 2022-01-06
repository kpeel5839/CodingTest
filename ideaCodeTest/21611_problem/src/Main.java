import java.util.*;
import java.io.*;

// 21611 : 마법사 상어와 블리자드
/*
-- 전제조건
1. 토네이도랑 블리자드랑 섞여있다고 보면 된다.
2. 구슬은 무조건 3까지밖에 존재하지 않는다 그럴 수밖에 없는게 4개 이상으로 연속된 구슬들은 다 폭발되기 때문에 그렇다.
3. 입력으로 들어오는 방향은 4가지 방향이 존재하고 1 = 위 , 2 = 아래 , 3 = 왼쪽 , 4 = 아래이다.
4. 상어가 블리자드 마법을 쓰면 상어의 위치에서부터 해당 방향 , 해당 거리의 있는 구슬들은 사라진다.
5. 그 다음에 빈칸 없이 구슬들이 다 이동한다.
6. 그리고 4개 이상에 연속된 구슬들은 , (같은 구슬들이) 폭발된다.
7. 그러면서 빈칸 없이 구슬들이 다 이동 -> 폭발 이게 계속 반복된다 (4개 연속된 구슬이 없을 때까지)
8. 이제 폭발할 구슬이 없으면 구슬이 변화하는 단계가 된다 -> 이 단계는 연속된 그룹을 계속 짝짓고 , 이게 A + B 그룹으로 번식된다 , 그 방법은 먼저 이 그룹의 사이즈 , 그리고 이 그룹의 구슬 번호
9. 예를 들어서 1 , 1 이렇게 있으면 2 , 1 이렇게 변하는 것이다 , 그리고 2 이렇게 있으면 1, 2 이렇게 변한다.
10. 마법사 상어는 총 M번의 블리자드 마법을 사용한다 이때 1 * (폭발한 1번 구슬의 개수) + 2 * (폭발한 2번 구슬의 개수) + 3 * (폭발한 3번 구슬의 개수) 출력한다.
-- 틀 설계
1. 입력을 받는다. (n은 무조건 홀수이고 , 마법은 m개가 들어온다.)
2. 마법으로 파괴를 시키는 blizzard() 함수를 만든다. (blizzard 함수는 점수를 얻지 않는다. (파괴라서))
3. 항상 구슬들이 폭발하거나 파괴되면 구슬들을 원래 위치로 이동시킬 move() 함수를 만든다.
4. tornado 방향으로 움직일 수 있는 것은 여기서 다 그냥 기본이다.
5. 그리고 폭발 시키는 explosion() 함수를 만든다. (이것은 explore boolean 형의 것으로 폭발이 되었는지 안되었는지 확인해서 안되었으면 그때 끝낸다 근데 끝낼때에는 move안해도 됨)
6. 이제 구슬을 복사시키는 marbleCopy() 함수를 만든다.
7. 그래서 이제 빈칸은 그냥 0으로 관리한다.
8. for문으로 m 크기 만큼 전역으로 선언하고 explore = true ,blizzard() -> while(explore){ explosion() } -> marbleCopy() 이런식으로 간다. (move는 항상 함수들의 안에서 호출한다.)
-- 해맸던 점
1. marbleCopy할 때 오히려 구슬의 개수가 더 작아지는 경우를 생각하지 못했음
2. dir을 항상 2로 설정하고 들어갔어야 했는데 하나를 그거를 안해서 해맸음
3. 폭발시키고서 폭발한 구슬을 0으로 안 만듦
4. 그 외에 설계같은 부분은 막힘은 없었음
 */
public class Main {
    public static int n , m , score = 0;
    public static boolean explore = true;
    public static int[][] map , exploreMap , blizzardList;
    public static Point sharkPoint;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static class Point{
        int y;
        int x;
        public Point(int y, int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void move(){
        /*
        1. queue 를 이용해서 옮길 것이다.
        2. tornado 방향으로 돌면서 일단 처음에 빈칸을 발견했냐 안했냐를 boolean 타입으로 검사함
        3. 일단 빈칸을 발견하기 전까지는 그냥 계속 진행함
        4. 그러다가 빈칸을 딱 만나면 if(blank) 이면 그냥 boolean 조작안하고 만약 else이면 blank = true로 만들고 queue에다가 집어 넣음
        5. 이제부터 만나는 칸마다 비어있지 않은 칸을 만나면 queue.poll() 한 다음에 거기다가 집어넣고 해당 칸 0으로 만든다음에 queue 에다가 집어넣음
        6. 근데 이제 그러다 보면 해당 만약 비어있는 칸을 만나면 그냥 queue에다가 add만하면 됨
        7. 이제 tornado로 어떻게 돌거냐가 관건임 일단 처음에는 2번 첫칸 그다음에 2번 2칸 , 3칸 2번 이런식으로 되고 방향은 계속 3 , 2 , 1 , 0 , 3 이렇게 계속 돌아감
        8. 그러면 안에서 방향은 바꾸고 하면 됨 규칙은 2번 그리고 계속 y가 변하는데 계속 검사해야함 이게 나갔는지 안나갔는지
         */
        int y = sharkPoint.y;
        int x = sharkPoint.x;
        Queue<Point> queue = new LinkedList<>();
        boolean blank = false;
        int dir = 3;
        Loop:
        for(int i = 1; i <= n; i++){
            for(int j = 0; j < 2; j++){
                for(int c = 0; c < i; c++) {
                    y += dy[dir];
                    x += dx[dir];
                    if (y < 0 || y >= n || x < 0 || x >= n) {
                        break Loop;
                    }
//                    System.out.println("y : " + y + " x : " + x);
                    if(blank){
                        if(map[y][x] == 0){ // 또 빈칸을 만났을 때
                            queue.add(new Point(y , x));
//                            System.out.println("y : " + y + " x: " + x);
                        } else{
                            Point movePoint = queue.poll();
                            map[movePoint.y][movePoint.x] = map[y][x];
                            map[y][x] = 0;
                            queue.add(new Point(y , x));
                        }
                    }else if(map[y][x] == 0){ //처음 blank를 만난 순간
//                        System.out.println("y : " + y + " x: " + x);
                        blank = true;
                        queue.add(new Point(y , x));
                    }
                }
                dir = dir - 1 < 0 ? 3 : dir - 1;
            }
        }
    }
    public static void blizzard(int blizzardIndex){
        /*
        1. sharkPoint에서 시작해서
        2. 해당 방향으로 그냥 해당 blizzardIndex에 맞는 blizzard 마법으로 그 방향으로 그 개수만큼 구슬 없앤다음
        3. move를 사용하면 됨
         */
        int dir = blizzardList[blizzardIndex][0];
        int distance = blizzardList[blizzardIndex][1];
        int y = sharkPoint.y , x = sharkPoint.x;
        for(int i = 1; i <= distance; i++){
            map[y + i * dy[dir]][x + i * dx[dir]] = 0;
        }
        move();
    }
    public static void explosion(){
        /*
        1. 일단 tornado 방식으로 돌면서 연속된 것들을 체크한다
        2. 처음에 explore = false; 로 시작한다 그러면서 stack 이 4 이상이 한번이라도 나오면 explore = true로 바꾸는 것이다.
        3. queue에다가는 계속 기록하다가 다른 숫자가 stack 이 4 쌓이기 전에 되면 queue를 그냥 초기화한다.
        4. 만약에 그렇지 않으면 다른 숫자가 나왔을 때 queue.isEmpty 할 때까지 exploreMap에다가 1을 표시한다.
        5. 그러면서 이제 exploreMap에 있는 1 인것들을 다 폭파시킨다 , 그러면서 폭발되는 숫자에 따라서 score를 추가한다.
        6. 그런 다음에 move를 불러서 마무리시킨다.
         */
        int y = sharkPoint.y , x = sharkPoint.x;
        int dir = 3;
        explore = false;
        int stack = 0;
        int marbleValue = 0;
        Queue<Point> queue = new LinkedList<>();
        Loop:
        for(int i = 1; i <= n; i++){ // 폭발할 구슬 체크
            for(int j = 0; j < 2; j++){
                for(int c = 0; c < i; c++) {
                    y += dy[dir];
                    x += dx[dir];
                    if(i == 1 && j == 0 && c == 0){
                        marbleValue = map[y][x];
//                        System.out.println(marbleValue);
                    }
//                    System.out.println(marbleValue);
                    if(marbleValue == 0){
                        break Loop;
                    }
                    if (y < 0 || y >= n || x < 0 || x >= n) {
                        break Loop;
                    }
//                    System.out.println("y : " + y + " x : " + x );
//                    System.out.println(map[y][x]);
                    if(marbleValue == map[y][x]){
                        stack++;
                        queue.add(new Point(y , x));
                    }
                    else{
                        if(stack >= 4){
                            explore = true;
                            while(!queue.isEmpty()){
                                Point point = queue.poll();
                                exploreMap[point.y][point.x] = 1;
                            }
                        }
                        marbleValue = map[y][x];
                        stack = 1;
                        queue.clear();
                        queue.add(new Point(y , x));
                    }
                }
                dir = dir - 1 < 0 ? 3 : dir - 1;
            }
        }
//        exploreMapPrint();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(exploreMap[i][j] == 1){
                    exploreMap[i][j] = 0;
                    score += map[i][j];
                    map[i][j] = 0;
                }
            }
        }
        move();
//        mapPrint();
    }
    public static void marbleCopy(){
        /*
        1. 이것은 연속된 것들을 그룹으로 짝지어서 하면 된다.
        2. 그렇기 때문에 tornado를 두번 반복을 해야한다.
        3. 되게 explosion이랑 비슷한 형태이다.
        4. 일단 동일하게 존재하는 것들의 value와 size를 지속적으로 센다.
        5. 그러고서 그게 끝나면 List에다가 size, value를 추가한다.
        6. 그러고서 list에서 하나씩 get으로 꺼내면서 하면 된다 (list의 index 기능을 사용하면 될 듯 이번에는)
         */
        List<Integer> marbleCopyList = new ArrayList<>();
        int size = 0 , marbleValue = 0 , dir = 3 , y = sharkPoint.y , x = sharkPoint.x;
        Loop:
        for(int i = 1; i <= n; i++){ // 폭발할 구슬 체크
            for(int j = 0; j < 2; j++){
                for(int c = 0; c < i; c++) {
                    y += dy[dir];
                    x += dx[dir];
                    if(i == 1 && j == 0 && c == 0){
                        marbleValue = map[y][x];
                    }
                    if(marbleValue == 0){
                        break Loop;
                    }
                    if(y < 0 || y >= n || x < 0 || x >= n){
                        continue;
                    }
                    if(marbleValue == map[y][x]){
                        size++;
                    } else{
                        marbleCopyList.add(size);
                        marbleCopyList.add(marbleValue);
//                        System.out.println("y : " + y + " x : " + x);
                        marbleValue = map[y][x];
                        size = 1;
                    }
                }
                dir = dir - 1 < 0 ? 3 : dir - 1;
            }
        }
        int index = 0;
        y = sharkPoint.y;
        x = sharkPoint.x;
        dir = 3;
//        System.out.println(marbleCopyList);
        Loop:
        for(int i = 1; i <= n; i++){
            for(int j = 0; j < 2; j++){
                for(int c = 0; c < i; c++) {
                    y += dy[dir];
                    x += dx[dir];
                    if(y < 0 || y >= n || x < 0 || x >= n){
                        break Loop;
                    }
//                    System.out.println("y : " + y + " x : " + x);
                    if(index < marbleCopyList.size()){
                        map[y][x] = marbleCopyList.get(index++);
                    }else{
                        map[y][x] = 0;
                    }
                }
                dir = dir - 1 < 0 ? 3 : dir - 1;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        exploreMap = new int[n][n];
        blizzardList = new int[m][2];
        sharkPoint = new Point(n / 2 , n / 2);

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(input.readLine());
            int dir = Integer.parseInt(st.nextToken()); //1 -> 0 , 2 -> 2 , 3 -> 3 , 4 -> 1
            int distance = Integer.parseInt(st.nextToken());
            dir = dir != 1 ? (dir == 4 ? 1 : dir) : 0;
            blizzardList[i][0] = dir;
            blizzardList[i][1] = distance;
        }

        for(int i = 0; i < m; i++){
            explore = true;
            blizzard(i);
//            mapPrint();
            while(explore) explosion();
            marbleCopy();
//            mapPrint();
        }

        System.out.println(score);
    }
    public static void mapPrint(){
        System.out.println("--------------map-----------------");
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void exploreMapPrint(){
        System.out.println("---------------exploreMap------------------");
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.print(exploreMap[i][j] + " ");
            }
            System.out.println();
        }
    }
}

