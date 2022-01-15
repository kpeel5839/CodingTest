import java.util.*;
import java.io.*;

// 2933 : 미네랄
/*
-- 전제조건
창영과 상근은 한 동굴을 놓고 소유권을 주장중에 있다.
싸움은 동굴에서 벌어지고 , 두 사람은 막대기를 서로에게 던지는 방법을 이용해서 누구의 소유인지를 결정하기로 하였다.
던진 막대기가 미네랄을 파괴할 수도 있다.
그리고 인접한 미네랄은 같은 클러스터이다 , 당연히 네방향이고 상하좌우겠지?
일단 그래서 왼쪽에서 오른쪽에서 각각 나무 막대기를 던지는데
나무막대기를 맞아서 사라진 미네랄때문에 분리된 클러스터가 생기게 되면 그 클러스터들은 중력에 의해서 떨어진다.
근데 그 인접한 클러스터들은 그대로의 형태로 떨어진다.
클러스터는 다른 클러스터나 땅을 만나기 전까지 계속 떨어진다.
-- 틀 설계
일단 미네랄의 형태를 받을 map을 만든다.
visited 배열을 만들 것인데 이것은 마냥 방문처리 뿐만 아니라 이게 땅에 붙어있는 지 아닌지도 판단할 것이다.
throwStick() 함수를 만드는데 이것은 0이면 왼쪽 1이면 오른쪽으로 하게 한다.
왼쪽이면 0 , 오른쪽이면 1은 그냥 for(int i = 0; i < N; i++) 으로 가면서 i % 2 로 하면된다.
그리고 두개 이상 클러스터가 한번에 떨어지는 일은 없으니 그냥 바닥에서 싹 훑은(n - 1) 다음에 그것들은 다 1로 하고
나머지 이제 바닥이 아닌 곳을 차례대로 할때에는 visited 에다가 2로 처리한다.
queue로 bfs를 돌면서 각각의 것들을 무조건 또 다른 list에다가 추가한다.
list에다가 추가한 다음에 하나하나씩 돌면서 calDis() 함수에다가 넣어서 최소 값을 구한다. 이때 calDis에서는 visited 1 을 만나거나 혹은 땅을 만날 경우에
값을 반환해서 해당 list에서 최소 값을 찾아낸 다음에 나머지는 다 돌면서 visited 1인 것들은 그냥 그려넣고 , 0인 것들은 그냥 . 그리고 2인것들은 해당 하는 down 만큼
down 시켜서 그리면 될 듯하다.
-- 해맸던 점
bfs를 들어갈 때 당연히 미네랄이 아닌 부분은 bfs를 들어가면 안됐었는데 들어갔음
그리고 calDir 에서 down++ 을 잘못된 위치에 썼었음
그리고 주변에 인접한 미네랄이 있어서 클러스터가 형성되었을 때도 떨어지다가 멈추는 줄 알았는데
그게 아니라 다른 미네랄이 떨어지는 공간에 있을 떄나 땅에 닿을 때 그때만 멈추는 거였음(오히려 더 어렵게 짰었음)
 */
public class Main {
    public static int[][] visited;
    public static char[][] map;
    public static int r, c , k;
    public static int downCount = Integer.MAX_VALUE;
    public static List<Point> moveList = new ArrayList<>();
    public static int[] dy = {-1 , 0 , 1 , 0}, dx ={0 , 1 , 0 , -1};
    public static class Point{
        int y;
        int x;
        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void throwStick(int judge , int height){
        /*
        막대기를 던져서 실제로 그 부분의 미네랄을 없애는 역할
        0이면 왼쪽부터 , 1은 오른쪽 height는 그대로 그 인덱스대로 탐색하면 됨
         */
        if(judge == 0){
            for(int i = 0; i < c; i++){ //왼쪽
                if(map[height][i] == 'x'){
                    map[height][i] = '.';
                    return;
                }
            }
        }else{ //오른쪽
            for(int i = c - 1; i != -1; i--){
                if(map[height][i] == 'x'){
                    map[height][i] = '.';
                    return;
                }
            }
        }
    }
    public static void bfs(int y , int x , int value) {
        /*
        이것은 이제 value는 visited 로 찍히는 것들을 value로 찍어내면된다.
        그러니까 땅에서 훑는 즉 n - 1에서 훑는 것들은 애초에 value = 1로 넘겨주고
        나머지는 2로 넘겨주면 된다.
         */
        Queue<Point> queue = new LinkedList<>();
        if(value == 2){
            moveList.add(new Point(y , x));
        }
        queue.add(new Point(y , x));
        visited[y][x] = value;
        while(!queue.isEmpty()){
            Point point = queue.poll();
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= r || nx < 0 || nx >= c || visited[ny][nx] != 0 || map[ny][nx] == '.'){
                    continue;
                }
                visited[ny][nx] = value;
                if(value == 2){
                    moveList.add(new Point(ny , nx));
                }
                queue.add(new Point(ny , nx));
            }
        }
    }
    public static void move() {
        /*
        moveList에 있는 것들을 calDis에 다 넣어서 downCount를 확보하고 min 값으로
        다 구하면 그 값대로 내려줘서 그리면 된다.
        그럼 일단 visited가 2인 거 빼고 다 그린다음에 2인 거 list돌면서 그리면 될 듯
        그러니까 visited[i][j] == 0 이다 그러면 . 으로 그리고 visited == 1이다 'x' 로 그리고
        그 다음에 visited가 2 인 것은 넘어간다
         */
        downCount = Integer.MAX_VALUE;
        for (Point point : moveList) {
            int value = calDis(point.y, point.x);
            if (downCount > value) {
                downCount = value;
            }
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if(visited[i][j] != 1){
                    map[i][j] = '.';
                }
                else if(visited[i][j] == 1){
                    map[i][j] = 'x';
                }
            }
        }

//        System.out.println(downCount);

        for(Point point : moveList){
            map[point.y + downCount][point.x] = 'x';
        }

        moveList.clear();
    }
    public static void clean(){
        /*
        visited를 clean 시키는 것
         */
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                visited[i][j] = 0;
            }
        }
    }
    public static int calDis(int y , int x){
        /*
        이제 bfs를 싹 돌았을 때 list에다가 value가 2인 것들을 줄텐데
        그러면 거기서 그 위치에서 미네랄을 떨어뜨리면서 4방향으로 탐색해서 몇칸을 갈 수 있나를 판단하는 것
         */
        int down = 0;
        Loop:
        while(true){
            y += 1;
            if(y == r || visited[y][x] == 1){
                break;
            }
            down++;
//            for(int i = 0; i < 4; i++){
//                int ny = y + dy[i];
//                int nx = x + dx[i];
//                if(ny < 0 || ny >= r || nx < 0 || nx >= c){
//                    continue;
//                }
//                if(visited[ny][nx] == 1){
//                    break Loop;
//                }
//            }
        }

        return down;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        visited = new int[r][c];
        map = new char[r][c];
        for(int i = 0; i < r; i++){
            String string = input.readLine();
            for(int j = 0; j < c; j++){
                map[i][j] = string.charAt(j);
            }
        }

        k = Integer.parseInt(input.readLine());
        st = new StringTokenizer(input.readLine());
        for(int i = 0; i < k; i++){
            int height = r - Integer.parseInt(st.nextToken());
            throwStick(i % 2 , height);
            for(int j = r - 1; j != -1; j--){
                for(int w = 0; w < c; w++){
                    if(map[j][w] != '.' && visited[j][w] == 0) {
                        if (j == r - 1) {
                            bfs(j, w, 1);
                        } else {
                            bfs(j, w, 2);
                        }
                    }
                }
            }
            move();
            clean();
//            mapPrint();
        }

        mapPrint();
    }
    public static void mapPrint(){
//        System.out.println("-------------------mapPrint--------------------");
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
    public static void visitedPrint(){
        System.out.println("------------------visitedPrint------------------");
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                System.out.print(visited[i][j]);
            }
            System.out.println();
        }
    }
}