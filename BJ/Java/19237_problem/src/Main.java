import java.util.*;
import java.io.*;

//19237 : 어른 상어
/*
-- 전제 조건
1. 상어만 남음 물고기 없음
2. 상어들이 움직이는데 각자 냄새를 남김
3. 1번 상어는 가장 강력한 상어라서 모두를 쫓아 낼 수 있음
4. 상어는 각자 자신의 냄새를 k만큼 남김 k는 입력으로 주어짐
5. 상어가 이동 방향을 결정할 때 먼저 인접한 칸 중 아무 냄새가 없는 칸의 방향으로 잡는다. (이것도 자신의 바라보는 방향의 우선순위 중 첫번째 방향으로 이동한다.)
6. 만일 아무 냄새가 없는 칸이 없으면 자신의 냄새가 나는 칸으로 이동한다. (그니깐 우선순위가 - 아무 냄새가 없는 칸 > 자신의 냄새가 있는 칸 이런것임 , 그니깐 자신의 냄새가 있는 칸으로 가는 경우에는 아무냄새도 없는 칸이 없을 경우임
7. 그니까 바라보는 방향과 실제로 움직이는 방향은 엄연히 여기서 다름
8. 1번 상어가 가장 강한 이유는 상어가 격자 하나에 몰려있을 때에는 가장 번호가 낮은 상어가 다 잡아먹어서 그럼
9. 이런식으로 흘러갔을 때 1번 상어만 남게 되는 상황까지의 시간

-- 틀 설계
1. 입력을 받는다. (입력은 3차원 배열로 만들고 상어의 번호 , 0번부터 시작하도록 한다 방향도 0번부터 시작할 수 있도록 - sharkDir[M][4][4] 이런식으로 선언하고 순서대로 집어넣자)
2. for 문으로 1000번을 돌리는데 계속 확인하면서 1번 상어만 남았는지 확인한다 , 만약 1000번을 시도하고서도 1번만 남지 않았으면 -1을 출력한다. (중간에 끝나면 해당 i + 1을 출력하면 된다.)
3. move함수를 만들어서 실제로 거기서 움직임을 담당하면 될 듯하다.
4. 배열은 3개를 만든다 일단 상어가 어떻게 남았는지 확인할 map , 그리고 움직일 때 상어들이 모두 움직일 수 있도록 돕는 tempMap , 그리고 상어의 향기를 저장할 smell 함수를 만들어놓는다.
5. smell은 상어가 그 위치에 이동할 때 거기다가 number , k 로 남겨놔야함 (smell을 down 시키는 함수도 만들어놓으면 좋을 듯)
6. 이런식으로 답을 출력하면 될 듯
*/
public class Main {
    public static int n , m , k;
    public static int[] dx = {0 , 0 , -1 , 1} , dy = {-1 , 1 , 0 , 0};
    public static int[][][] sharkDir;
    public static Shark[][] map , tempMap;
    public static Smell[][] smell;
    public static void move(){
        /*
        1. 현재 어떤 shark의 정보를 주면 어디 방향으로 가야할 지 정보를 주는 decideDir 함수 구현 완료
        2. 상어가 움직인 다음 smellWeek까지 완료
        3. 상어를 tempMap에다가 움직인 다음 clean 하고 tempMap에서 현재 map에서 옮길 수 있도록 clean 도 구현완료
        4. 함수는 구현 되었으니 순서만 정하면 됨
        5. for문으로 map을 다 돌면서 shark가 있으면 decideDir을 호출함 (필요한 정보를 주며)
        6. 그러면서 해당 방향으로 상어를 옮김 tempMap에다가 그림 , 물론 decideDir로 받은 dir로 바꿔야함 (근데 이미 상어가 있는 경우에는 number를 비교해서 안되면 없앰)
        7. 그리고 다 움직이면 smellWeek 을 호출하고 해당 tempMap에서 map으로 옮기기전에 map을 clean함
        8. clean하면서 tempMap에서 map으로 옮길 때 map 에다가 옮기면서 tempMap 현재 상어 있는 위치 null로 만들고 smell에다가 추가함 number , k를
         */
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n ;j++){
                if(map[i][j] != null){
                    int dir = decideDir(map[i][j].number , map[i][j].dir , i , j);
                    int ny = i + dy[dir];
                    int nx = j + dx[dir];
                    if(tempMap[ny][nx] == null){
                        tempMap[ny][nx] = new Shark(map[i][j].number , dir);
                    }
                    else if(tempMap[ny][nx] != null && tempMap[ny][nx].number > map[i][j].number){
                        tempMap[ny][nx] = new Shark(map[i][j].number , dir);
                    }
                }
            }
        }
        smellWeek();
        clean();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(tempMap[i][j] != null){
                    map[i][j] = new Shark(tempMap[i][j].number , tempMap[i][j].dir);
                    smell[i][j] = new Smell(tempMap[i][j].number , k);
                    tempMap[i][j] = null;
                }
            }
        }
    }
    public static void smellWeek(){
        /*
        1. 상어가 갈 방향을 정하고 움직일 때 smell을 다 down 시켜줄 함수
         */
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(smell[i][j] != null){
                    if(smell[i][j].remain == 1){
                        smell[i][j] = null;
                    }
                    else{
                        smell[i][j] = new Smell(smell[i][j].number , smell[i][j].remain - 1);
                    }
                }
            }
        }
    }
    public static int decideDir(int number , int dir , int y, int x){
        /*
        1. 해당 상어가 가야할 방향을 정해서 반환
        2. 일단 지금 현재 상어의 번호와 바라보는 번호 , 상어의 위치를 받는다.
        3. 상어 위치에 현재 주변의 null이 있나 찾아본다 null이 있다면 현재 바라보는 방향에서 우선 순위대로 찾는다 순서대로 sharkDir[number - 1][dir][0 ~ 3] 으로 null인 것을 찾으면 되고
        4. null인 것이 없으면 sharkDir[number - 1][dir][0 ~ 3]을 쭉 찾으면서 자신의 number를 가진 색깔을 찾아서 거기 방향으로 던져준다.
         */
        boolean nullFind = false;
        for(int i = 0; i < 4; i++){
            int ny = y + dy[i];
            int nx = x + dx[i];
            if(ny < 0 || ny >= n || nx < 0 || nx >= n){
                continue;
            }
            if(smell[ny][nx] == null){
                nullFind = true;
                break;
            }
        }
        int result = 0;
        if(nullFind){
            for(int i = 0; i < 4; i++){
                int ny = dy[sharkDir[number - 1][dir][i]] + y;
                int nx = dx[sharkDir[number - 1][dir][i]] + x;
                if(ny < 0 || ny >= n || nx < 0 || nx >= n){
                    continue;
                }
                if(smell[ny][nx] == null){
                    result = sharkDir[number - 1][dir][i];
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < 4; i++) {
                int ny = dy[sharkDir[number - 1][dir][i]] + y;
                int nx = dx[sharkDir[number - 1][dir][i]] + x;
                if (ny < 0 || ny >= n || nx < 0 || nx >= n) {
                    continue;
                }
                if(smell[ny][nx].number == number){
                    result = sharkDir[number - 1][dir][i];
                    break;
                }
            }
        }
        return result;
    }
    public static void clean(){
        /*
        1. tempMap에서 상어의 결과를 옮길때 현재의 map을 지우고 tempMap에서 옮길 것이다.
        2. 그니까 결국 요기서는 map을 지우면 됨
         */
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                map[i][j] = null;
            }
        }
    }
    public static boolean check(){
        /*
        1. check를 해서 1만 있는 것이 아니면 false 즉 , 아직 안끝남을 의미하고
        2. 1만 있다면 여기서 false를 반환하지 않았을 테니 true 를 반환하게 된다 그러면 게임 끝남
         */
        for(int i = 0; i < n; i++){
            for(int j = 0;j < n; j++){
                if(map[i][j] != null){
                    if(map[i][j].number != 1){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static class Shark{
        int number;
        int dir;
        public Shark(int number , int dir){
            this.number = number;
            this.dir = dir;
        }
    }
    public static class Smell{
        int number;
        int remain;
        public Smell(int number , int remain){
            this.number = number;
            this.remain = remain;
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

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new Shark[n][n];
        tempMap = new Shark[n][n];
        smell = new Smell[n][n];
        Point[] sharkPoint = new Point[m];
        sharkDir = new int[m][4][4];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                int number = Integer.parseInt(st.nextToken());
                if(number == 0){
                    continue;
                }
                sharkPoint[number - 1] = new Point(i , j);
                smell[i][j] = new Smell(number , k);
            }
        }

        st = new StringTokenizer(input.readLine());
        for(int i = 0; i < m; i++){
            map[sharkPoint[i].y][sharkPoint[i].x] = new Shark(i + 1 , Integer.parseInt(st.nextToken()) - 1);
        }
        int index = 0;
        for(int i = 0; i < m * 4; i++){
            if(i != 0 && i % 4 == 0){
                index++;
            }
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < 4; j++){
                sharkDir[index][i % 4][j] = Integer.parseInt(st.nextToken()) - 1;
            }
        } //방향까지 다입력받음

        for(int i = 0; i < 1000; i++){
            /*
            1. 각 실행할 때마다 상어가 어떻게 살아있냐를 봐야함 map을 확인
            2. 그러면서 중간에 1마리 남으면 바로 거기서 i + 1 출력하고
            3. 만약 1000 됐는데도 안되면 바로 -1을 출력
             */
            move();
            if(check()){
                System.out.println(i + 1);
                break;
            }
            if(i == 999){
                System.out.println(-1);
            }
        }
    }
}