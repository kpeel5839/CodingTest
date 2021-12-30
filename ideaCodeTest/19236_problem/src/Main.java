import java.util.*;
import java.io.*;

//19236 : 청소년 상어
/*
-- 전제 조건
1. 일단 4 * 4 칸에서 무조건 시작하고 물고기들은 각자의 방향을 가진다.
2. (x, y)로 표현된다 , 즉 x = row , y = col 임
3. 물고기들은 각자 8개의 방향을 가진다 , 대각도 포함
4. 물고기는 각자의 번호를 가지고 있다. 1번부터 16번까지, 모든 칸의 물고기에 번호가 매겨져 있는 것임
5. 먼저 물고기가 움직이는데 움직이는 순서는 물고기의 번호 순서이다(오름차순)
6. 물고기가 이동하려는데 갈 수 없는 공간이다? 즉 , 상어가 있거나 범위를 벗어나는 경우 , 그 경우에는 갈 수 있을때까지 45도씩 반시계 방향으로 회전시킨다.
7. 그리고 물고기가 이동을 하면 물고기 서로의 위치를 swap 한다.
8. 물고기가 이동을 다하면 상어가 이동
9. 상어는 자신의 방향으로 움직일 수 있고 물고기가 있는 칸으로만 움직일 수 있다 , 여러칸을 움직일 수 있으며 최종 칸의 물고기만 잡아먹고 방향을 가진다.
10. 이 과정을 계속 반복한다. 물고기 이동 -> 상어 이동 -> 물고기 이동 .... (상어가 움직일 수 있는 칸이 없을 때까지)
11. 초기에는 상어가 0,0의 물고기를 잡아먹은 다음에 물고기들이 이동을 시작한다.

-- 틀 설계
1. 입력을 받는다(첫째는 상어의 번호 , 둘째는 상어의 방향) dir은 받은 값의 -1을 해서 받는다. (fish 클래스로 받는다. 물고기의 번호와 방향을 가지도록 -1은 shark의 번호)
2. 물고기의 이동을 담당하는 fishMove함수를 선언 (swap 함수를 이용해서 물고기의 위치를 바꾼다)
3. dfs안에서 Math.max(ans , score)로 계속 최댓값을 찾는다.
4. 그러면 일단 상어가 초기 상태 즉 score = 초기 (0, 0) 물고기의 번호로 호출한다, 즉 현재 map dfs(map , map[0][0]) 이런식으로 호출한다.
5. 그래서 dfs에서 해결하는 방법밖에 없다. deepcopy를 시도해봐야할 듯
*/

/*
-- 해맸던 점
1. 전체적인 설계는 되게 잘되었음
2. 근데 이제 Shark[][] map을 다룰 때 안의 shark를 그대로 쓰는 바보같은 짓을 해서 주소가 겹쳐서 게속 그거 때문에 틀림
3. 설명하면 Shark shark = map[y][x] 를 해버려서 난 이게 deepCopy인줄 알았지만 이것을 shark.dir = 0 이렇게 변경해버리면
4. shark가 저장되어 있는 고유주소 즉 레퍼런스가 변경되어서 틀렸던 것임
 */
public class Main {
    public static int[] dx = {0, -1 , -1 , -1 , 0 , 1 , 1 , 1} , dy = {-1 , -1 , 0 , 1 , 1 , 1 , 0 , -1}; //1 부터 8까지 있는데 12시부터 시작해서 반시게 방향으로 회전
    public static int ans = 0;
    public static void dfs(Shark[][] map , int score , boolean end){
        /*
        1. 일단 먼저 받은 map을 deepcopy를 한다 왜냐하면 자신의 map을 가지고 있어야한다.
        2. deepcopy를 한다음에 fishMove에 넘겨서 본인의 map을 move시킨다.
        3. 본인이 움직일 수 없을 때 그때 end를 true로 보내면 끝낼 수 있다.
        4. 그리고 본인이 fishMove는 dfs를 불렀을 때 호출하고 , 실제로 이제 자신이 움직일 칸을 정해서 dfs로 넘길때에는 dfs(sharkMove(deepMap , 원래 위치 , 이동할 위치) = 상어가 움직인 맵을 반환하고 , score는 내가계산해서 넘긴다.)
        5. 만일 본인이 이동이 되는데 비어있는 것은 부른 다음에 dfs를 호출할 때 true를 넣어서 call 하면 된다.
         */
        if(end == true){
//            System.out.println("score : " + score);
//            for(int i = 0; i < map.length; i++){
//                for(int j = 0; j < map[i].length; j++){
//                    if(map[i][j] == null){
//                        System.out.print("n" + " ");
//                    }
//                    else {
//                        System.out.print(map[i][j].number + " ");
//                    }
//                }
//                System.out.println();
//            }
            ans = Math.max(ans , score);
            return;
        }
        Shark[][] deepMap = new Shark[4][4];
        for(int i = 0; i < map.length; i++){
            System.arraycopy(map[i] , 0 , deepMap[i] , 0 , map[i].length);
        }
        fishMove(deepMap);
        Point sharkPoint = null;
        for(int i = 0; i < deepMap.length; i++){
            for(int j = 0; j < deepMap[i].length; j++){
//                if(deepMap[i][j] != null && deepMap[i][j].number == 15){
//                    System.out.println("number 15 fish dir : " + deepMap[i][j].dir);
//                }
                if(deepMap[i][j] != null && deepMap[i][j].number == -1){
                    sharkPoint = new Point(i , j);
                }
            }
        }
//        System.out.println("Score : " + score);
//        for(int i = 0; i < deepMap.length; i++){
//            for(int j = 0; j < deepMap[i].length; j++){
//                if(deepMap[i][j] == null){
//                    System.out.print("n" + " ");
//                }
//                else {
//                    System.out.print(deepMap[i][j].number + " ");
//                }
//            }
//            System.out.println();
//        }
        int dir = deepMap[sharkPoint.y][sharkPoint.x].dir;
//        System.out.println(dir);
        int moveCount = 1;
        while(true){
            int ny = sharkPoint.y + (moveCount * dy[dir]);
            int nx = sharkPoint.x + (moveCount * dx[dir]);
            if(ny < 0 || ny >= 4 || nx < 0 || nx >= 4){
                ans = Math.max(ans , score);
                break;
            }
            if(deepMap[ny][nx] == null){
                dfs(sharkMove(deepMap , sharkPoint.y , sharkPoint.x , ny , nx) , score , true);
            }
            else{
                dfs(sharkMove(deepMap , sharkPoint.y , sharkPoint.x , ny , nx) , score + deepMap[ny][nx].number , false);
            }
            moveCount++;
        }
    }
    public static void fishMove(Shark[][] map){
        /* - not deep , swallow copy
        1. 받은 map을 move시킨다 deepCopy 필요 x 그 dfs가 가지고 있는 map을 변경 할 것이니까
        2. 16짜리 길이의 array를 선언해서 sharkList[map[i][j].number - 1] = point(j , i) 로 집어넣는다.
        3. 그러고서 for(int i = 0;i < sharkList.length; i++){ ~ } 이런식으로 fish들을 움직이는 과정을 진행한다.
        4. swap은 그냥 fishMove내에서 반복문 내에서 진행하는 게 나을 듯 하다.
        5. 이렇게 하면 끝
         */
        Point[] fishList = new Point[16];
        for(int i = 0;i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(map[i][j] != null && map[i][j].number != -1){
                    fishList[map[i][j].number - 1] = new Point(i , j);
                }
            }
        }
        for(int i = 0; i < 16; i++){
            /*
            1. fish들이 움직이는 과정을 담당해야함
            2. null이 아닌 배열을 만나면 움직여야 할것임
            3. 움직이기전에 지금 현재 shark 의 정보를 현재 배열의 point 값을 가지고서 빼낸다.
            4. 빼낸다음 while(true)로 자신이 갈 수 있는지 판단을 진행한다.
            5. 갈 수 있는 방향이 정해진다면 그 방향에 있는 shark의 정보를 temp에다가 담고
            6. 거기다가 넣고 현재 내 배열에다가 temp를 집어 넣는다. 그리고서 그 [temp.number - 1] = 나의 point를 집어넣는다.
             */
            if(fishList[i] == null){
                continue;
            }
            Shark shark = new Shark(map[fishList[i].y][fishList[i].x].number , map[fishList[i].y][fishList[i].x].dir);
            int dir = shark.dir;
            int ny = 0;
            int nx = 0;
            while(true){
                ny = fishList[i].y + dy[dir];
                nx = fishList[i].x + dx[dir];
                if(ny < 0 || ny >= 4 || nx < 0 || nx >= 4 || (map[ny][nx] != null && map[ny][nx].number == -1)){
                    dir = dir + 1 < 8 ? dir + 1 : 0;
                }else{
                    break;
                }
            }
            shark.dir = dir;
            Shark temp = map[ny][nx] != null ? new Shark(map[ny][nx].number , map[ny][nx].dir) : null;
            map[ny][nx] = shark;
            map[fishList[i].y][fishList[i].x] = temp;
            if(temp != null) {
                fishList[temp.number - 1] = new Point(fishList[i].y, fishList[i].x);
            }
        }
//        System.out.println("after fishMove");
//        for(int i = 0; i < map.length; i++){
//            for(int j = 0; j < map[i].length; j++){
//                if(map[i][j] == null){
//                    System.out.print("n" + " ");
//                }
//                else {
//                    System.out.print(map[i][j].number + " ");
//                }
//            }
//            System.out.println();
//        }
    }
    public static class Point{
        int y;
        int x;
        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
        @Override
        public String toString(){
            return "y : " + y + " x : " + x;
        }
    }
    public static Shark[][] sharkMove(Shark[][] map , int cY , int cX , int nY, int nX){
        /* - deep copy
        1. 내가 받은 map을 deepCopy 한 다음에 상어를 움직여서 반환한다 deepCopy해서 변경한 맵을
        2. deepcopy를 진행 한 다음에 y, x에 있는 shark를 만큼 이동 시킨다.
        3. 그리고 move시킨 map을 반환한다.
         */
        Shark[][] deepMap = new Shark[4][4];
        for(int i = 0; i < map.length; i++){
            System.arraycopy(map[i] , 0 , deepMap[i] , 0 , map[i].length);
        }
        Shark shark = new Shark(deepMap[cY][cX].number , deepMap[cY][cX].dir);
        shark.number = -1;
        if(deepMap[nY][nX] != null) {
            shark.dir = deepMap[nY][nX].dir;
        }
        deepMap[cY][cX] = null;
        deepMap[nY][nX] = shark;
        return deepMap;
    }
    public static class Shark{
        //dir은 0 ~ 7 이다.
        int number;
        int dir;
        public Shark(int number , int dir){
            this.number = number;
            this.dir = dir;
        }
//        @Override
//        public int compareTo(Shark other){
//            if(this.number > other.number){
//                return 1;
//            }
//            else{
//                return -1;
//            }
//        }
    }
    public static void main(String[] args) throws IOException{
        /*
        1. 물고기들의 정보를 어떤식으로 저장할 것인가 dfs 를 사용해야 한다 그것을 고려해서 물고기들을 관리해야함
         */
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        Shark[][] map = new Shark[4][4];

        for(int i = 0; i < 4; i++){
            st = new StringTokenizer(input.readLine());
            int index = 0;
            for(int j = 0; j < 4; j++){
                map[i][index++] = new Shark(Integer.parseInt(st.nextToken()) , Integer.parseInt(st.nextToken()) - 1);
            }
        }
        int score = map[0][0].number;
        map[0][0] = new Shark(-1 , map[0][0].dir); //0 , 0 에 있던 물고기의 방향을 가지고 shark 두두둥장
        dfs(map , score , false);
        System.out.println(ans);
    }
}
