import java.util.*;
import java.io.*;

// 21610 : 마법사 상어와 비바라기
/*
-- 전제조건
1. 각 격자에는 바구니가 하나가 있다.
2. 바구니의 물의 양은 제한이 없다.
3. (r, c) 는 r 행 c 열에 있는 바구니를 의마한다 , A[r][c]는 바구니에 저장되어 있는 물의 양을 의미
4. 격자의 가장 왼쪽 윗칸은 (1, 1) 가장 오른쪽 아래는 (N, N)이다. 즉 1부터 시작
5. 그리고 이전에 파이어스톰처럼 map이 동그랗게 연결이 되어있는 형태이다.
6. 비바라기를 처음에 딱 시전하면 맨 좌측 아래 사각형으로 4칸의 격자에 비구름이 생긴다.
7. 구름에 이동을 명령하고 M번 명령한다 , 이동 명령은 방향 d와 거리 s로 이루어져 있다.
8. 방향은 총 8개의 방향이 존재하고 , 1부터 순서대로 왼쪽부터 시계방향으로 이동한다.
9. 이동을 명령하면 이러한 과정이 진행된다.
10. 1) 모든 구름이 d 방향으로 s 칸 이동 -> 2) 이동한 뒤 각 구름에서 비가 내려 구름이 있는 칸의 바구니에 저장된 물의양이 1 증가
-> 3) 구름이 모두 사라진다. -> 4) 2번에서 물이 증가한 칸(r , c)에 물복사 버그 마법을 시전한다. 물복사 버그 마법을 시전하면
대각선 방향으로 거리가 1인 칸에 물이 있는 바구니의 수만큼 (r , c)에 있는 바구니의 물이 양이 증가한다. 이때 경계를 넘어가면 대각선 방향으로 거리가 1인 칸이 x ,
그러니까 대각선의 바구니에서 물이 존재하는 바구니를 탐색할 때에는 이때만큼은 map이 구의 모양을 띄지 않는다는 것이다.
-> 5) 바구니에 저장된 물의 양이 2 이상인 모든 칸에 구름이 생기고 물의 양이 2 줄어든다. 이때 구름이 생기는 칸은 3에서 구름이 사라진 칸이 아니여야한다.
-- 틀 설계
1. 입력을 받는다. (map의 사이즈와 격자안의 물 그리고 이동 명령)
2. 구름을 이동 시키는 move 함수를 만든다. (이때 map이 구모양이라는 것을 적용시켜야함)(cloud map으로 하나 만들어 놓자.)
3. 구름에 비를 내리게하고 구름을 없애는 rain 함수를 만든다.
4. 물복사 버그 마법을 구현한 waterCopy함수를 만든다. (이때는 구의 모양이 아니라는 것을 주의해야함)
5. 다시 구름을 생기게 하는 makeCloud 를 실행한다.
6. list에다가 cloudList를 저장할 것임 , 현재의 구름을 근데 그거는 rain에서 할 것임
7. 그러면 for문에다가 m만큼 반복하고 move() -> rain() -> waterCopy() -> makeCloud() 순으로 계속 실행한 뒤
8. 격자의 남은 물의 양을 전부 더해서 출력한다.
-- 해맸던 점
1. cloud move 과정에서 본인이 움직이고 자신이 있던 자리의 구름을 없애는 방법을 사용하였는데
2. 그 과정에서 본인이 있었던 자리로 이동한 구름이 있다는 것을 생각하지 못했었음
3. 그래서 afterCloudList , beforeCloudList 로 나누면서 보완하였음
4. 그리고 movePoint 함수에서 afterY , afterX 를 잘못 계산하였었음
5. 그리고 makeCloud 함수에서 구름을 형성하는 과정에서 map[i][j] -= 2를 안해서도 답이 이상하게 나왔었음
 */
public class Main {
    public static int n , m , ans = 0;
    public static List<Point> cloudList;
    public static int[][] map , cloudMap;
    public static int[] dx = {-1, -1 , 0 , 1 , 1 , 1, 0 , -1} , dy ={0 , -1 , -1 , -1 , 0 , 1 , 1 , 1};
    public static void move(int distance , int dir){
        /*
        1. 일단 현재 있는 cloud들을 queue에다가 다 담고
        2. 다시 list를 돌면서 movePoint를 통해서 도착 위치를 받고
        3. cloud를 거기다가 옮겨 놓으면서 원래 있던 위치를 0으로 만든다.
        4. list는 지역변수로 선언해서 한다.
         */
        List<Point> beforeCloudList = new ArrayList<>() , afterCloudList = new ArrayList<>();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(cloudMap[i][j] == 1) {
                    beforeCloudList.add(new Point(i, j));
                }
            }
        }
        for(Point point : beforeCloudList){
            Point afterPoint = movePoint(point.y, point.x ,distance , dir);
            cloudMap[point.y][point.x] = 0;
            afterCloudList.add(new Point(afterPoint.y , afterPoint.x));
//            cloudMap[afterPoint.y][afterPoint.x] = 1;
        }
        for(Point point : afterCloudList){
            cloudMap[point.y][point.x] = 1;
        }
    }
    public static Point movePoint(int y, int x, int distance , int dir){
        /*
        1. 위치를 받고 , 이동 거리를 받으면 dir을 통해서 계산해서 돌려준다. 해당 point를
        2. 찾은 위치를 point로 반환하면 된다.
        3. 더해서 그냥 +로 가는 것은 % n 해주면 된다.
        4. 먼저 distance 를 %= n해준다.
        5. 그럼에도 음수가 된다면 해당 distance 에서 현재 좌표만큼 빼면 남은 거리가 되고 거기다가 이제 방향을 곱해주면된다.
        6. y = y + dy[dir] * distance < 0 ? n + (distance - y) * dy[dir] : (y + dy[dir] * distance) % n
        */
        distance %= n;
//        System.out.println("before y : " + y + " x : " + x + " distance : " + distance + " dir : " + dir);
        y = y + dy[dir] * distance < 0 ? n + (distance - y) * dy[dir] : (y + dy[dir] * distance) % n;
        x = x + dx[dir] * distance < 0 ? n + (distance - x) * dx[dir] : (x + dx[dir] * distance) % n;
//        System.out.println("after y : " + y + " x : " + x + " distance : " + distance + " dir : " + dir);
        return new Point(y , x);
    }
    public static void rain(){
        /*
        1. 그냥 구름이 있는 칸의 물의 양을 1증가 시키고 cloudList에다가 현재 구름이 있는 칸을 point로 담는다.
        2. 그러면 makeCloud에서 쓸 것임
         */
        cloudList = new ArrayList<>();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(cloudMap[i][j] == 1){
                    map[i][j]++;
                    cloudList.add(new Point(i, j));
                }
            }
        }
    }
    public static void waterCopy(){
        /*
        1. 물복사 버그를 구현할 것임
        2. 이거는 그냥 평범하게 방향은 0 , 2 , 4 , 6으로 탐색해서
        3. 물의 양이 1 이상인 칸을 새고 거기서 물을 증가시켜준다.
        4. cloudList에 있는 포인터들을 사용할 것이다.
         */
        for(Point point : cloudList){
            int count = 0;
            for(int i = 1; i <= 7; i+=2){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= n || nx < 0 || nx >= n){
                    continue;
                }
                if(map[ny][nx] != 0){
                    count++;
                }
            }
            map[point.y][point.x] += count;
        }
    }
    public static void makeCloud(){
        /*
        1. 일단 물의 양이 2이상인 칸을 전부 1로 만든다 cloud map을
        2. cloudList에 담겨 있는것들은 다시 0으로 만든다.
         */
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(map[i][j] >= 2 && cloudMap[i][j] != 1){
                    cloudMap[i][j] = 1;
                    map[i][j] -= 2;
                }
            }
        }
        for(Point point : cloudList){
            cloudMap[point.y][point.x] = 0;
        }
    }
    public static void calAns(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                ans += map[i][j];
            }
        }
    }
    public static class Point{
        int y;
        int x;
        public Point(int y, int x){
            this.y =y;
            this.x =x;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        cloudMap = new int[n][n];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = n - 1; i >= n - 2; i--){
            for(int j = 0; j <= 1; j++){
                cloudMap[i][j] = 1;
            }
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(input.readLine());
            int dir = Integer.parseInt(st.nextToken()) - 1;
            int distance = Integer.parseInt(st.nextToken());
            move(distance , dir);
//            mapPrint();
//            cloudPrint();
            rain();
//            System.out.println("rain after");
//            mapPrint();
//            cloudPrint();
            waterCopy();
//            System.out.println("waterCopy after");
//            mapPrint();
//            cloudPrint();
            makeCloud();
//            System.out.println("makeCloud after");
//            mapPrint();
//            cloudPrint();
        }

        calAns();
        System.out.println(ans);
    }
//    public static void mapPrint(){
//        System.out.println("---------mapPrint----------------");
//        for(int i = 0; i < n; i++){
//            for(int j = 0; j < n; j++){
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
//    public static void cloudPrint(){
//        System.out.println("---------cloudPrint--------------");
//        for(int i = 0; i < n; i++){
//            for(int j = 0; j < n; j++){
//                System.out.print(cloudMap[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
}
