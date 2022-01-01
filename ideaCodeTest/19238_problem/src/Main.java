import java.util.*;
import java.io.*;

/*
19238 : 스타트 택시
-- 전제 조건
1. 항상 최단 경로로 이동한다.
2. 1칸씩 이동할 때마다 연료가 단다
3. 손님을 태우고 나서 해당 손님의 목적지까지 데려다 줄 경우 태운 위치에서 목적지까지의 연료의 2배만큼 회복한다
4. 근데 이제 이동중에 연료가 떨어지면 끝난다.
5. 이제 이게 손님의 목적지까지 못 가는 경우도 고려해야 할 듯
6. 0은 빈칸 , 1은 벽을 나타낸다.
7. 손님을 다 데려다주고 끝에 연료를 충전한 뒤에 남은 연료의 양을 출력하는 문제

-- 틀 설계
1. 일단 bfs로 최단 거리를 계속 구해야한다.
2. 손님의 위치 그리고 목적지의 위치는 따로 주어진다.
3. 이것을 2차원 배열로 저장해놔야 할 듯 point[i][0] = 손님의 위치 point[i][1] = 손님의 목적지 위치
4. 벽은 -1 빈칸은 -2로 하는게 좋을 듯 그리고 시작하는 부분부터 최소거리는 숫자로 매기면 될 듯
5. 일단 bfs로 쫙 돌린다 해당 택시의 위치부터 택시는 리얼 -3으로 표시하자
6. 택시가 이동하는 거리 즉 moveMap과 그냥 map을 나누어 놓자 moveMap은 벽과 빈칸만 표시하면 될 듯
7. 손님을 그릴 때 손님의 정보가 들어있는 index의 -10을 해서 저장하자
8. 그러면 0번째 손님은 -10 , -11 이런식으로 저장되고
9. 최소 경로를 찾을 때에도 map 과 moveMap을 보면서 map에 -10 보다 작은 숫자들의 최소값을 구한 뒤에
10. 최소 값을 구한 인덱스는 예를 들어서 -11이 최소다 그러면 (-11 - 10) * -1 이렇게 해서 원래 인덱스 값을 찾고 이렇게 할 필요도 없이 그냥 -1 , -2로 표시해놓고 손님 인덱스에다가 박아놓으면 될 듯
11. bfs를 한번 더 돌려서 이 지점부터 목적지까지와의 거리를 구한다.
12. 근데 이 과정에서 계속해서 연료를 체크해야 할 듯 이 과정을 계속 반복한다 목적지 데려다 주면 연료 충전하고 그런 식으로

-- 해맸던 점
1. 정말 사소한 실수로부터 많이 해맸음
2. 일단 한번 이미 태워다 준 손님을 고려 못했음
3. 그리고 min 과 같은 것을 찾을 때 break; 문을 잘 못써서 자꾸 이상한 답나왔었음
4. dfs 돌리고 visited clean 안해줘서 답 안나왔었음
5. 평소 하던 대로 그냥 min = Integer.MAX_VALUE 안하고 첫번째 요소집어넣었다가도 엄청 해맴
6. int guestNumber = map[taxi.y][taxi.x] 할 때 이렇게 하면 value가 아닌 reference가 넘어가서 guestNumber가 map의 정보가 바뀜에 따라서 달라진다는 것을 고려하지 못했었음
7. 그거 말고 fail이 guest에서 떴을 때를 생각하지 못했었고 , 찾은 min 값과 똑같은 값을 찾을 때 map[i][j] != 2 라는 것도 고려했었어야 했는데 이것을 고려하지 못함
 */
public class Main {
    public static int n , m , fuel;
    public static boolean fail = false;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1, 0};
    public static int[][] map , moveMap , tempMoveMap, visited;
    public static Point taxi;
    public static Point[][] guestInfo;
    public static void bfs(){
        /*
        1. 그냥 택시에 맞춰서 경로를 다 찍으면 됨 (taxi Point 이용)
        2. QueuePoint를 이용해서 bfs를 돌면 됨
        3. 그냥 moveMap에다가 bfs로 순서대로 돌면서 -1은 벽이니까 못가고 -2만 갈 수 있게끔 해서 경로 다 찍어놓으면 됨
         */
        Queue<QueuePoint> queue = new LinkedList<>();
        queue.add(new QueuePoint(taxi.y , taxi.x , 0));
        visited[taxi.y][taxi.x] = 1;
        moveMap[taxi.y][taxi.x] = 0;
        while(!queue.isEmpty()){
            QueuePoint queuePoint = queue.poll();
            int value = queuePoint.value;
            for(int i = 0; i < 4; i++){
                int ny = queuePoint.y + dy[i];
                int nx = queuePoint.x + dx[i];
                if(ny < 0 || ny >= n || nx < 0 || nx >= n || visited[ny][nx] == 1 || moveMap[ny][nx] == -1){
                    continue;
                }
                moveMap[ny][nx] = value + 1;
                queue.add(new QueuePoint(ny , nx , value + 1));
                visited[ny][nx] = 1;
            }
        }
    }
    public static void guest(){
        /*
        1. 손님 있는 목적지를 고르는 함수
        2. bfs를 이용해야함
        3. bfs를 이용해서 찍고 guest를 끝까지 돌면서 guest[i][0] 의 지점들을 다 보면서 min값을 찾아낸다.
        4. 그리고서 한번 더 돌면서 min 값이랑 같은 맨 처음 지점으로 taxi를 이동시켜야하는데 , 이때 tempMove의 taxi 위치를 변경 시키면서 taxi point도 수정한다.
        5. 근데 이 taxi의 point를 수정하기 이전에 min 값이 -2라면 바로 fail = true 로 설정한다. 그리고 한다음 copy를 진행한다. 물론 taxi 위치를 변경 하기 이전에 이전의 taxi point의 값을 -2로 바꾸어주어야함
         */
        bfs();
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < m; i++){
            if(min > moveMap[guestInfo[i][0].y][guestInfo[i][0].x]){
                if(map[guestInfo[i][0].y][guestInfo[i][0].x] != -2) {
                    min = moveMap[guestInfo[i][0].y][guestInfo[i][0].x];
                }
            }
        }
        int distance = min;
//        System.out.println(min);
        if(min == -2 || fuel < distance){ // 이거는 짜피 taxi 가 못가는 공간이 있다면 짜피 안됨 , 벽이 중간에 무너지는 것도 아니니까 이미 min 값이 -2 가 있으면 못 가는 공간에 있는 것이니 fail임
            fail = true;
            return;
        }
//        System.out.println("----------------");
//        for(int c = 0; c < n; c++){
//            for(int j = 0; j < n; j++){
//                System.out.print(moveMap[c][j] + " ");
//            }
//            System.out.println();
//        }
        Point newTaxiPoint = null;
        Loop1:
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(min == moveMap[i][j] && map[i][j] != -2){
//                    System.out.println(i + " " + j);
                    newTaxiPoint = new Point(i , j);
                    break Loop1;
                }
            }
        }
        fuel -= distance;
        tempMoveMap[taxi.y][taxi.x] = -2;
        taxi = new Point(newTaxiPoint.y, newTaxiPoint.x);
        tempMoveMap[taxi.y][taxi.x] = -3;
//        System.out.println("taxiPoint : " + taxi.y + "," + taxi.x);
        copy(); // taxi 위치 옮기고 copy까지 완료
    }
    public static void destination(){
        /*
        1. 해당 손님에 맞게 목적지를 데려다줌
        2. bfs를 이용해야함
        3. 이번에는 해당 지금 taxi 위치에 있는 숫자를 확인한 후 그 인덱스로 가야함 물론 이렇게 확인한 다음에 태운 손님은 지워야함
        4. 그리고 해당 손님의 번호를 이용해서 guestInfo[number][1] 의 point를 확인해서 거기 값을 확인한다 이것도 -2다? 그러면 바로 fail
        5. 혹은 distance가 fuel < distance 다 ? 그러면 그것도 지워야함
        6. 그리고 위에서 처럼 taxi point를 수정해야함
        7. 위에랑 똑같이 그런 다음에 copy만 해주면 끝
         */
        if(fail){
            return;
        }
        bfs();
        int guestNumber = map[taxi.y][taxi.x];
//        System.out.println("y : " + taxi.y + " x : " + taxi.x);
//        System.out.println("guestNumber : " + guestNumber);
        Point desPoint = guestInfo[guestNumber][1];
        int distance = moveMap[desPoint.y][desPoint.x];
        if(distance == -2 || fuel < distance){
            fail = true;
            return;
        }
//        System.out.println("----------------");
//        for(int c = 0; c < n; c++){
//            for(int j = 0; j < n; j++){
//                System.out.print(tempMoveMap[c][j] + " ");
//            }
//            System.out.println();
//        }
        map[taxi.y][taxi.x] = -2;
        fuel -= distance;
        tempMoveMap[taxi.y][taxi.x] = -2;
        taxi = new Point(desPoint.y, desPoint.x);
//        System.out.println("taxiPoint : " + taxi.y + "," + taxi.x);
        tempMoveMap[taxi.y][taxi.x] = -3;
        fuel += distance * 2;
//        System.out.println(distance + " " + fuel);
//        System.out.println("taxiPoint : " + taxi.y + "," + taxi.x);
        copy(); // taxi 위치 옮기고 copy까지 완료
    }
    public static class QueuePoint{
        int y;
        int x;
        int value;
        public QueuePoint(int y , int x, int value){
            this.y = y;
            this.x = x;
            this.value = value;
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
    public static void copy(){
        /*
        1. moveMap에 이동경로로 더럽혀진 것을 tempMoveMap에 있는 원래 정보로 copy해 놓음
        2. 그럴려면 계속 moveMap에서 정해지면 tempMoveMap의 택시의 위치는 변경이 되어야함
         */
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                moveMap[i][j] = tempMoveMap[i][j];
                visited[i][j] = 0;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        fuel = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        moveMap = new int[n][n];
        tempMoveMap = new int[n][n];
        guestInfo = new Point[m][2];
        visited = new int[n][n];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                int value = Integer.parseInt(st.nextToken());
                if(value == 1){ //벽일 때
                    map[i][j] = -1;
                }
                else{ //빈칸 일 때
                    map[i][j] = -2;
                }
                tempMoveMap[i][j] = map[i][j];
            }
        } //빈칸일 때 -2 , 벽일 때 -1이 있음
        st = new StringTokenizer(input.readLine());
        int taxiY = Integer.parseInt(st.nextToken()) - 1;
        int taxiX = Integer.parseInt(st.nextToken()) - 1;
        tempMoveMap[taxiY][taxiX] = -3; //택시 위치
        taxi = new Point(taxiY , taxiX);
        copy();
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < 2; j++){
                int y = Integer.parseInt(st.nextToken()) - 1;
                int x = Integer.parseInt(st.nextToken()) - 1;
                guestInfo[i][j] = new Point(y , x);
                if(j == 0){
                    map[y][x] = i;
                }
            }
        }
//        bfs();
//        for(int i = 0; i < n; i++){
//            for(int j = 0; j < n; j++){
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }
        for(int i = 0; i < m; i++){
            guest();
            destination();
//            System.out.println("----------------");
//            for(int c = 0; c < n; c++){
//                for(int j = 0; j < n; j++){
//                    System.out.print(tempMoveMap[c][j] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println("----------------");
//            for(int c = 0; c < n; c++){
//                for(int j = 0; j < n; j++){
//                    System.out.print(map[c][j] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println("y : " + taxi.y + " x : " + taxi.x);
//            System.out.println(map[taxi.y][taxi.x]);
            if(fail){
                System.out.println(-1);
                break;
            } else if(i == m - 1){
                System.out.println(fuel);
            }
        }
    }
}
