import java.util.*;
import java.io.*;

// 20056 : 마법사 상어와 파이어볼
/*
-- 전제 조건
1. 일단 map이 끝꽈 끝이 다 연결 되어있는 구의 형태라고 생각하면 됨
2. 얘를 들어서 파이어볼이 대각으로 갔음 n이 5인 상태에서 (4,4)의 볼이 그러면 (0 , 0) 이 되는 것
3. 그러니까 경계를 넘는 것이 아니라 % n 을 게속 해주면 될 듯
4. 파이어볼은 질량 , 방향 , 속력을 가지고 있다.
5. 이동이 시작되면 모든 파이어볼은 자신의 방향으로 속력만큼 이동한다.
6. 이동이 모두 끝났는데 2개 이상의 파이어볼이 있는 칸에서는 모두 합쳐진다.
7. 그 다음 파이어볼은 4개의 파이어볼로 나누어진다.
8. 나누어진 파이어볼의 질량 = (합쳐진 파이어볼 질량의 합 / 5) (내림)
9. 나누어진 파이어볼의 속력 = ((합쳐진 파이어볼 속력의 합) / (합쳐진 파이어볼의 개수)) (내림) - floor
10. 나누어진 파이어볼의 방향 = (합쳐진 것이 모두 짝수나 홀수이면 0 , 2 , 4 , 6) , (아니면 1 , 3 , 5 , 7)
11. 이동을 k번 하고나서 남아있는 파이어볼의 질량의 합
-- 틀 설계
1. 입력을 받는다.
2. for문으로 k번 만큼 반복한다.
3. 반복할 떄 현재 파이어볼을 모두 움직이는 instruct함수를 구현 하고
4. 특이한 점이 있는 fireball들을 다 check 할 수 있도록 check 함수를 구현하여서 fireball을 컨트롤 해야함
5. instruct에서 fireball을 발견하면 move를 시킬 수 있도록 딱 넣으면 이동할 지점을 구해주는 move함수를 구현 (Point를 반환)
6. 그러고서 계속 반복한다. (모든 fireball들을 움직일 때에는 null이 아닌 이상 해당 인덱스의 list를 다 돌면서 움직이게 끔 하면 될 듯)
7. move하면 point를 반환하는 것보다 그냥 옮겨 주는 것도 나쁘지 않을 듯 하나씩 돌면서 remove해주는 것 list에서 move로 넘기면서
8. 그러면 move에서는 지점을 구해서 null이면 새로 list를 파서 거기다가 add하면 됨 그런 식으로 계속 진행하면 될 듯
9. check에서도 그냥 합칠 때 아얘 new ArrayList로 새로 판다음에 거기에서 add하면 됨 새로 나온 것을, 정보를 list.size로 해서 정보를 남겨 놓던 가
-- 해맸던 점
1. 설계는 거의 바로 맞았음
2. check에서 break;이 아니라 continue;를 했었어야 했는데 break을 해서 이것때문에 시간 엄청 걸림
 */
public class Main {
    public static int n , m , k;
    public static int[] dx = {0 , 1 , 1 , 1 , 0 , -1 , -1 , -1} , dy = {-1 , -1 , 0 , 1 , 1 , 1 , 0 , -1}; //위부터 시계 방향으로 됨
    public static List[][] map , tempMap;
    public static void move(int y, int x , Ball fire){
        /*
        1. fire를 실제로 이동해야 하는 위치로 옮겨주는 역할을 할 함수
        2. origin + speed < 0 ? n + speed : (origin + speed) % n , speed %= n
        3. 쨋든 나와준 것들을 다 이렇게 해주면 지점을 구할 수 있다.
         */
        int speed = fire.speed % n;
        int dir = fire.dir;
        y = y + (speed * dy[dir]) < 0 ? n + ((speed - y) * dy[dir]) : (y + speed * dy[dir]) % n;
        x = x + (speed * dx[dir]) < 0 ? n + ((speed - x) * dx[dir]) : (x + speed * dx[dir]) % n;
//        System.out.println("y : " + y + " x : " + x);
        if(tempMap[y][x] == null){
            tempMap[y][x] = new ArrayList<Ball>();
        }
        tempMap[y][x].add(new Ball(fire.weight , fire.speed , fire.dir));
    }
    public static void check(){
        /*
        1. 해당 인덱스의 list.size > 1인 경우에 해당 것들을 처리해줄 함수
        2. list.size >= 2인 것들만 해당 되는 이야기
        3. weight 랑 speed 다 더한다 , count는 size로 , same = true이면 0 , 2 , 4 , 6 same = false이면 1, 3, 5 ,7로 할 것임
        4. weight 랑 speed는 그냥 다 더한거 speed는 size로 나누고 weight / 5 로 나눈 거 math.floor해서 저장해놓으면 된다.
        5. 이제 관건은 짝수냐 홀수이냐를 보는 것인데 처음 것을 % 2 로 하고 그 다음에 게속 이게 같은 가 확인하는 것 같지 않으면 바로 same = false 초기에는 true이렇게 해놓고서 same이 false 냐 true이냐에 따라서 두 갈래로 나뉘면 됨
         */
//        System.out.println("-----------------");
//        for(int j = 0; j < n; j++){
//            for(int c = 0; c < n; c++){
//                if(map[j][c] == null){
//                    System.out.print("n" + " ");
//                }
//                else {
//                    System.out.print(map[j][c].size() + " ");
//                }
////                    if(map[j][c] != null && map[j][c].size() == 2){
////                        for(Ball ball : new ArrayList<Ball>(map[j][c])){
////                            System.out.println(ball.weight + " ");
////                        }
////                    }
//            }
//            System.out.println();
//        }
        for(int i = 0;i < n; i++){
            for(int j = 0; j < n; j++){
//                if(map[i][j] != null){
//                    System.out.println("size is : " + map[i][j].size());
//                }
                if(map[i][j] != null && map[i][j].size() >= 2){
//                    System.out.println("size over");
//                    System.out.println("y : " + i + " x : " + j);
                    int weight = 0;
                    int speed = 0;
                    boolean same = true;
                    int remain = ((Ball)map[i][j].get(0)).dir % 2;
                    for(Ball ball : new ArrayList<Ball>(map[i][j])){
                        weight += ball.weight;
                        speed += ball.speed;
                        if(remain != (ball.dir % 2)){
                            same = false;
                        }
                    }
                    weight = (int)Math.floor(weight / 5d);
                    speed = (int)Math.floor((double)speed / map[i][j].size());
                    if(weight == 0){
                        map[i][j] = null;
                        continue;
                    }
                    map[i][j] = new ArrayList<Ball>();
                    if(same){
                        for(int c = 0; c <= 6; c += 2){
                            map[i][j].add(new Ball(weight , speed , c));
                        }
                    }
                    else{
                        for(int c = 1; c <= 7; c += 2){
                            map[i][j].add(new Ball(weight , speed , c));
                        }
                    }
                }
            }
        }
    }
    public static void instruct(){
        /*
        1. 실제로 move 를 지시하는 메인 부분
        2. 이런식으로 null이 아닌 것들은 전부 다 돌면서 move를 지시함
        3. tempMap에다가 move를 호출해서 그린다음에 실제 map에다가 copy함 그리고 tempMap은 clean시킴
         */
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(map[i][j] != null){
//                    int limit = map[i][j].size();
//                    int index = 0; //동시성 이슈때문에 itertor를 사용해서 제거 해야함
//                    for(Iterator<Ball> iterator = map[i][j].iterator(); iterator.hasNext()){
//                        if(limit == index) break;
//                        Ball ball = iterator.next();
//                        move(i, j , ball);
//                        iterator.remove();
//                        index++;
//                    } //동시성 이슈를 피하기 위한 iterator사용
                    for(Ball ball : new ArrayList<Ball>(map[i][j])){
                        move(i , j , ball);
                    }
                }
            }
        }
        copy();
        check();
    }
    public static void copy(){
        /*
        1. tempMap의 내용을 map에다가 옮김과 동시에
        2. tempMap 내용 지움
         */
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                map[i][j] = tempMap[i][j];
                tempMap[i][j] = null;
            }
        }
    }
    public static class Ball{
        int weight;
        int speed;
        int dir;
        public Ball(int weight , int speed , int dir){
            this.weight = weight;
            this.speed = speed;
            this.dir = dir;
        }
    }
    public static class Point{
        int y;
        int x;
        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken()); //map의 크기
        m = Integer.parseInt(st.nextToken()); //초기 파이어볼 개수
        k = Integer.parseInt(st.nextToken()); //이동을 k번 명령

        map = new ArrayList[n][n];
        tempMap = new ArrayList[n][n];

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(input.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            int weight = Integer.parseInt(st.nextToken());
            int speed = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            map[y][x] = new ArrayList<Ball>();
            map[y][x].add(new Ball(weight , speed , dir));
        }
//        for(int j = 0; j < n; j++){
//            for(int c = 0; c < n; c++){
//                if(map[j][c] == null){
//                    System.out.print("n" + " ");
//                }
//                else {
//                    System.out.print(map[j][c].size() + " ");
//                }
//            }
//            System.out.println();
//        }

        for(int i = 0; i < k; i++){
            instruct();
//            System.out.println("-----------------");
//            for(int j = 0; j < n; j++){
//                for(int c = 0; c < n; c++){
//                    if(map[j][c] == null){
//                        System.out.print("n" + " ");
//                    }
//                    else {
//                        System.out.print(map[j][c].size() + " ");
//                    }
////                    if(map[j][c] != null && map[j][c].size() == 2){
////                        for(Ball ball : new ArrayList<Ball>(map[j][c])){
////                            System.out.println(ball.weight + " ");
////                        }
////                    }
//                }
//                System.out.println();
//            }
        }
        int result = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(map[i][j] != null){
                    for(Ball ball : new ArrayList<Ball>(map[i][j])){
                        result += ball.weight;
                    }
                }
            }
        }
        System.out.println(result);
    }
}