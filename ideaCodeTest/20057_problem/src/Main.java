import java.util.*;
import java.io.*;

/*
20057 : 마법사 상어와 토네이도
-- 전제 조건
1. N은 무조건 홀수로 주어짐
2. 가운데에서 시작해서 토네이도가 돌아감
3. %로 쓰여져있는 곳에 모래가 가고 남은 것이 a위치로 이동
4. 토네이도가 해당 위치로 갈 때 일어나는 일 , 그리고 토네이도의 시작 지점은 무조건 0인 듯
5. 밖으로 나간 모래의 양을 출력
-- 틀 설계
1. 입력을 받는다.
2. 토네이도를 이동시키는 순서대로 토네이도의 이동지점을 계속 찍어주는 move 함수를 구현
3. move함수의 좌표를 주면 모래들을 이동시키는 함수를 구현
4. dir은 8방향으로 하고 토네이도가 이동한 방향에 따라서 주변에다가 %로 모래를 퍼트리는 방식으로 구현해야 할 듯
-- 해맸던 점
1. 설계는 완벽했음
2. 그냥 사소한 % 실수랑 , map[y][x]를 깎는 것 , 그리고 map[ny][nx]에 추가하는 것에서 사소한 실수로 인해서 해맸음
 */
public class Main {
    public static int send = 0, n;
    public static int[][] map;
    public static int[] dx = {0 , -1 , -1 , -1 , 0 , 1 , 1 , 1}, dy = {-1 , -1 , 0 , 1 , 1 , 1 , 0 , -1}; //8방향으로 위 부터 반시계 방향으로
    public static void move(){
        /*
        1. dir대로 이동을 명령해서 토네이도가 돌 수 있도록 하는 함수
        2. 토네이도 구현은 일단 출발 지점에서 2번씩 게속 i만큼 dir에 따라서 이동시킬 것임
        3. i 는 n까지 구현이 될 것 근데 짜피 맨마지막에 1, 1 갈때 n - 1칸 밖에 못가고 끝남 , 이 해당 tornado 지점이 그냥 밖으로 나간다? 다음 이동 지점이? 그러면 끝내면 된다.
        4. 그리고 dir은 계속 첨에는 2 , 4 , 6 , 0 ... 이런식으로 되어야함 2번씩
         */
        int y = n / 2;
        int x = y; //y , x tornado 의 스타트 지점
        int dir = 2;
        Loop:
        for(int i = 1; i <= n; i++){
            for(int c = 0; c < 2; c++) { //i 만큼 이동이 2번씩 번갈아가면서 가고 dir도 계속 바뀜
                for (int j = 0; j < i; j++) { //2번씩 , dir은 계속 증가시켜줘야 할 듯 dir = 2
                    int ny = y + dy[dir];
                    int nx = x + dx[dir];
                    if(ny < 0 || ny >= n || nx < 0 || nx >= n){
                        break Loop;
                    }
                    spread(ny , nx , dir);
                    y = ny;
                    x = nx;
//                    System.out.println("y : " + y + " x : " + x);
//                    System.out.println("----------");
//                    for(int v = 0; v < n; v++){
//                        for(int w = 0; w < n; w++){
//                            System.out.print(map[v][w] + " ");
//                        }
//                        System.out.println();
//                    }
                }
                dir = (dir + 2) % 8;
            }
        }
    }

    public static void spread(int y, int x, int dir){
        /*
        1. 토네이도가 이동한 지점의 정보를 주면은 해당 모래를 흩날리도록 하는 함수
        2. 격자 밖으로 나가는 모래의 양은 계속 모래에다가 더해줘야함
        3. 이제 spread 되는 것들만 잘 계산해주면 됨 floor 연산으로 퍼센테이지 계산 잘하면 됨
        4. 원래 있던 지점은 상관 없고 그냥 spread만 잘 처리하면 됨
        5. 흩 뿌리는 지점은 일단 dir기준으로 하면 다 똑같으니 dir기준으로 처리하면 됨 예를 들어서 위에를 처리해야 한다고 하면 현재 방향에서 -2를 해주면 되고 이런식으로
        6. 위 = -2 , 아래 = +2 , 오른쪽 상단 대각 = -3 , 왼쪽 상단 대각 = -1 , 왼쪽 하단 대각 = +1 , 오른쪽 하단 대각 = +3 , 그리고 자신의 방향
        7. 순서대로 정리하면 -3 부터 3까지 존재함 , 그리 각자 처리해야 할 개수만 다를 뿐
        8. 다 뿌려주면서 해당 지점의 모래를 - 해주어야 함 그리고 그 남은 것을 맨 마지막에 a 자리로 옮겨야함 해당 자리를 지우면서
         */
        int value = map[y][x];
        for(int i = -3; i <= 3; i++){ //만일 2인데 -3하면 7이 나와야함 5 인데 +3하면 0이 나와야함
            int innerDir = dir + i < 0 ? 8 + (dir + i) : (dir + i) % 8;
//            System.out.println("innerDir : " + innerDir + " " + " dir : " + dir);
            if(i == -3 || i == 3){ // 1%
                int innerValue = (int)Math.floor(value * 0.01);
                int ny = y + dy[innerDir];
                int nx = x + dx[innerDir];
                map[y][x] -= innerValue;
                if(ny < 0 || ny >= n || nx < 0 || nx >= n){
                    send += innerValue;
                    continue;
                }
                map[ny][nx] += innerValue;
            }else if(i == -2 || i == 2){ // 2% , 7%
                int innerValue = 0;
                for(int j = 1; j <= 2; j++) {
                    int ny = y + (j * dy[innerDir]);
                    int nx = x + (j * dx[innerDir]);
                    if(j == 1) {
                        innerValue = (int)Math.floor(value * 0.07);
                    }else{
                        innerValue = (int)Math.floor(value * 0.02);
                    }
                    map[y][x] -= innerValue;
                    if (ny < 0 || ny >= n || nx < 0 || nx >= n) {
                        send += innerValue;
                        continue;
                    }
                    map[ny][nx] += innerValue;
                }
            }else if(i == -1 || i == 1){ // 10%
                int innerValue = (int)Math.floor(value * 0.10);
                int ny = y + dy[innerDir];
                int nx = x + dx[innerDir];
                map[y][x] -= innerValue;
                if(ny < 0 || ny >= n || nx < 0 || nx >= n){
                    send += innerValue;
                    continue;
                }
                map[ny][nx] += innerValue;
            }else if(i == 0){ // 5% , 2칸 떨어짐
                int innerValue = (int)Math.floor(value * 0.05);
                int ny = y + 2 * dy[innerDir];
                int nx = x + 2 * dx[innerDir];
                map[y][x] -= innerValue;
                if(ny < 0 || ny >= n || nx < 0 || nx >= n){
                    send += innerValue;
                    continue;
                }
                map[ny][nx] += innerValue;
            }
        }
        int ny = y + dy[dir];
        int nx = x + dx[dir];
        value = map[y][x];
        if(ny < 0 || ny >= n || nx < 0 || nx >= n){
            send += value;
        }else{
            map[ny][nx] += value;
        }
        map[y][x] = 0;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());
        map = new int[n][n];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        move();
        System.out.println(send);
    }
}