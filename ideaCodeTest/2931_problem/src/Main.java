import java.util.*;
import java.io.*;

// 2931 : 가스관

/*
-- 전제조건
m 에서 z로 가기 위해서 가스관들을 다 연결해놨는데
가스관 들은 | - + 1 2 3 4 블록들이 있다
여기서 하나를 뺐는데 , 이 뺀 위치와 어떠한 블록이 빠졌는지 출력하라
하나의 정답만 존재하는 경우가 주어진다.
-- 틀 설계
0 = 위 , 1 = 오른쪽 , 2 = 아래 , 3 = 왼쪽
일단 가스관들의 특징을 알아야 한다.
'|' 블록은 가던 방향을 유지하는 성향이 있다 예를 들어서 2로 가는 방향이였다면 2로
'-' 도 똑같다 방향을 그대로 유지한다. 근데 이제 얘내들의 특징은 dir % 2 == 1 이고 - 는 | 는 dir % 2 == 0이다.
그리고 + 방향은 이전에 온 dir을 제외하고 어디든지 가기가 가능하다.

1 블록은 0 -> 1 로 바꿔준다.
3 블록은 1 -> 0 으로 바꿔준다.

2 블록은 2 -> 1 로 바꿔준다.
4 블록은 1 -> 2 로 바꿔준다.

그리고 + 는 무조건 4방향 다 연결 되어 있어야 한다고 할 수 있다.
그냥 + 가 있으면 무지성으로다가 4방향 다 존재하는지 확인한다.

자 그러면 블록 하나하나를 찾는 과정을 생각해보자.

아래로 내려갔다 , 근데 블록이 없다
|
.
2

이런식으로 존재한다고 해보자. 이럴 때 .으로 갔을 때 좌표를 저장한다.
일단 파이프의 흐름대로 갔는데 없다? 그러면 그 좌표를 기록해둬야 한다.

그리고서 그 해당 좌표에서 주변을 탐색한다.
저 경우에서는 2가 아래에 있다, 그러면 방향을 그대로 유지해야 한다는 말이다.

그렇게 되면 dir % 2 == 0 이면서 방향을 유지할 수 있는 | 블록이 와야한다.

- 블록도 똑같다
-.- 이렇게 있다고 가정해보자
그러면 불완전한 좌표가 나왔을 때 저장해놓고 , 주변을 탐색한다.
근데 - 가 있다 내 현재 방향에서
그러면 여기로 가는 것이 맞다.

그러면 블럭은 dir % 2 == 1 이면서 방향을 유지할 수 있는 - 블록이 와야한다.

만일
.|.
-.4
.23

이렇게 존재한다고 가정해보자.
그러면 .칸이 불완전하다 근데 4방향을 탐색했더니 4방향에 다있다 칸들이
그러면 무조건 +가 들어가는 것이다.

그러니까 그냥 . 좌표를 만나면 자기가 지나온 곳을 제외한 방향을 탐색해야 하는데
이때 본인의 방향도 매우 중요하다
..-
.|.
이렇게 있다고 가정하였을 때 0,1 에 .에는 1번 블록이 들어와야 한다.
이 경우에는 3으로 들어왔을 때 본인의 2 방향에 블록이 있으니까
1번 블록을 택해야 한다.
3 , 2 라고 했을 때 1번블록을 선택해야 하고 혹은 0 , 1

-..
.|.
이 경우에는 1 , 2 일 때 4블록을 선택해야하고
올라가는 경우에는 0 , 3인 경우

.|.
..- 방향이 2 , 1 일 때 2번 블록 혹은 3 , 0 인 경우

.|.
-.. 방향이 2 , 3 일 때 3번 블록을 선택해야한다 혹은 1 , 0 인 경우

모아서 봐보자
1 - 3 , 2 or 0 , 1
2 - 3 , 0 or 2 , 1
3 - 2 , 3 or 1 , 0
4 - 0 , 3 or 1 , 2
이런 식으로 각자의 특성이 존재

+는 4방향 중에 이전 방향 제외하고 3개 다 존재하면 +

- , | 방향은 본인이 진행하던 방향에 존재하면
있는 것이고 - 인지 | 인지 확인하는 방법은 dir % 2 == 0 이면 | , else 이면 - 이다.

이제는 그러면 Z , M 두개의 좌표를 저장해놓는다.
그 다음에 M에서 시작하면서 , 4방향을 탐색하면서 , 가능한 곳으로 간다.
가능한 곳이 없다면?

그러면 그 때 이제 지금 가지고 있는 방향을 이용해서 , 그 쪽 방향으로 가서 주변을 탐색한다.
근데 dfs로 하는데 현재의 파이프를 보고 가서 방향을 본다.

예를 들어서 M에서 모든 방향으로 탐색을 해서 , 여러 방향으로 탐색을 한다.
그러면 이제 가능한 곳 , 즉 . 이 아닌 곳으로 이동할 때 해당 방향을 가지고 간다라는 개념으로 가야한다.

이제 +라고 함은 모든 방향으로 가기가 가능하고
내가 가지고 있는 방향을 가지고 그것을 이용해서 진행해야 하는 것이다.

-- 해맸던 점
존니 짜증나게 개 해맸던 반례조차도 없던 문제였음
이건 내가 dfs로 풀었기 때문에 발생한 일이였음
만일 +가 있어서 여러개의 방향으로 분기했다고 가정했을 때
왼쪽부터 먼저 실행된다고 가정하고 , 꼭 아래쪽에 파이프가 비어있다고 가정하였을 때
dfs이니까 z에 먼저 도달하게 되고 , Z에 도달해서 또 계속 실행이 되어야하는데
따로 Z일때 처리를 하지 않았으니 dir 의 기본값인 0 , 즉 윗방향으로 진행하게 되서 indexError 가 나게 되었다
그래서 + 로 인해서 진행방향이 분기되고 , 파이프가 비어있지 않은쪽으로 먼저 가게 되서 Z까지 도달하면서 인덱스 에러가 난거였음

3 4
..1Z
1-+4
M.23

위에 해맸던 점에 대해서 반례임 , Z에 대한 처리를 하지 않으면 저러한 반례가 있다.
 */
public class Main {
    public static int w , h;
    public static char[][] map;
    public static boolean[][] visited;
    public static Point result = null , m , z;
    public static char resPipe;
    public static int[][] pipeInputAble = {{0 , 2} , {1 , 3} , {0 , 3} , {2 , 3} , {1 , 2} , {1 , 0}};
    public static int[][] pipeOutputAble = {{0 , 0 , 2, 2} , {1 , 1 , 3 , 3} , {0 , 1 , 3 , 2} , {2 , 1 , 3 , 0} , {1 , 0 , 2 , 3} , {1 , 2 , 0 , 3}}; //output able 이 아닌 변환 시켜주는 것은 어떨까?
    public static char[] pipe = {'|' , '-' , '1' , '2' , '3' , '4'};
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static class Point{
        int y;
        int x;
        public Point(int y, int x ){
            this.y = y;
            this.x = x;
        }
    }
    public static void dfs(Point position ,int preDir){
        /*
        현재의 파이프만이 중요하다 지나 온 길은 중요하지 않고
        현재의 파이프와 다음에 있는 파이프가 중요하기 때문에
        position , preDir 만 가지고 있으면 된다.

        1 - 3 , 2 or 0 , 1
        2 - 3 , 0 or 2 , 1
        3 - 2 , 3 or 1 , 0
        4 - 0 , 3 or 1 , 2

        '-' - dir % 2 == 1 이면서 방향이 똑같을 때
        '|' - dir % 2 == 0 이면서 방향이 똑같을 때

        그리고 다음 포지션으로 넘어갈 때에는 현재 블록을 보고서 다음 위치를 정해준다.

        그리고 다음 포지션으로 갔을 때 '.' 이면 이제 거기서 주변을 탐색하고
        거기서 result 에다가 position 을 집어넣고 , 온 위치를 제외하고
        온 방향을 제외하고 count 했을 때 count == 3 이면 무조건 + 이고
        올 수 있는

        찾는 경우가 아닌 파이프가 연결이 되어있어서 , 가는 경우에는 | , - , 1 , 2 , 3 , 4 정도는 배열로 처리해놓는 것도 나쁘지 않을 것 같다.
        배열로 들어가는 것과 나가는 것을 저장해놓는다.
        그러면 preDir 을 보고 현재 블럭을 봐서 2차원 배열에서 선택을 하면 될 것 같다.
        근데 이제 + 가 문제이다. +를 만났을 때에는 그냥 배열로 처리 말고 따로 처리하는 것으로 하자
        preDir 만 제외하고 가면 되니깐

        근데 이제 .을 만났을 때
        .-.-4
        ..-.-

        이렇게 되어있을 때
        .에는 -가 들어가야한다.

        근데 여기서 어떻게 판단할 수 있을 까..
        주변 방향을 탐색해서 들어갈 수 있는 위치를 찾고
        preDir과 현재 가는 방향을 구해서 하면 될 것 같긴하다.

        정리를 하면 갈때에는 그냥 preDir을 알고 현재 문자를 아니까
        pipe[] 로 지금 현재 pipe 인덱스 찾고
        preDir을 찾으면 output 방향을 구할 수 있다(+ 는 따로)
        그 다음에 그렇게 해서 움직이면 되고

        그 다음에는 .을 만났을 때가 문제이다.
        . 을 만났을 때에는 가능한 방향들을 살펴본다.
        그 다음에 inputAble을 봐서 , 방향마다 가능한 칸들을 체크한다.
        그런데 가능한 칸이 여러개이다? 그러면 +이다.
        아니면 가능한 칸을 저장해놓고 , preDir과 같이 탐색한다.

        그러면 찾은 인덱스 값을 넣으면 될 것 같다.
         */
        visited[position.y][position.x] = true;

//        System.out.println("y : " + position.y + " x : " + position.x);
        if(map[position.y][position.x] == '.'){ // 칸을 찾았을 때
            /*
            일단 해당 지점을 찾았으니 4방향을 다 탐색해본다.
            일단 4방향을 다 탐색해보면서 가능한 dir들을 list에다가 담는다.
            그런 다음에 list.size == 1 이 아니다? 그러면 무조건 + 가 resPipe 이고
            list.size == 1이다. 그러면 그 dir과 preDir을 통해서 일치하는 것을 찾는다.
            6개의 파이프들을 돌면서 , pipeOutputAble[i][0] 혹은 2번과 일치하면 결정된 dir이 [1] , [3] 과 일치하다는 결과가 나오면
            그 파이프를 골라서 resPipe에다가 집어넣는다.

            그리고 + 가 들어갈게 아니라면 무조건 다 한구멍으로만 나오기 때문에 , 가능한 dir은 무조건 하나다
             */
            result = new Point(position.y , position.x);
            List<Integer> dirList = new ArrayList<>();
            for(int i = 0; i < 4; i++){
                if((preDir + 2) % 4 == i) continue;
                int ny = position.y + dy[i];
                int nx = position.x + dx[i]; // 확인할 칸을 지정
                if(ny < 0 || ny >= h || nx < 0 || nx >= w) continue; // 확인하는데 범위를 벗어날 수도 있으니까 처리
                // 파이프가 가르키는 방향으로만 가면 벗어날 리가 없는데 이것은 그냥 주변을 탐색하는 것이니까 이렇게 처리해주어야 한다.
                for(int j = 0; j < 6; j++){
                    if(pipe[j] == map[ny][nx]){
                        if(pipeInputAble[j][0] == i || pipeInputAble[j][1] == i){
                            dirList.add(i);
                            break;
                        } // 거기 파이프로 가는 게 가능하면 , 추가
                    }
                }
                if(map[ny][nx] == '+'){ // 가려는 방향이 +면 무조건 됨
                    dirList.add(i);
                }
            }

//            System.out.println("preDir : " + preDir);
//            System.out.println("y : " + result.y + " x: " + result.x);
//            System.out.println(dirList);

            if(dirList.size() == 1){
                // 여기서는 내가 현재 가진 preDir , dir을 이용해서
                // preDir 을 OutputAble[i][0] or OutputAble[i][2] 와 비교해서 preDir 이 맞으면  1 혹은 3을 확인한다.
                int dir = dirList.get(0);
                for(int i = 0; i < 6; i++){
                    if(pipeOutputAble[i][0] == preDir){
                        if(pipeOutputAble[i][1] == dir){
                            resPipe = pipe[i];
                            break;
                        }
                    }
                    if(pipeOutputAble[i][2] == preDir){
                        if(pipeOutputAble[i][3] == dir){
                            resPipe = pipe[i];
                            break;
                        }
                    }
                }
            }else{
                resPipe = '+'; // size == 1 이 아니라면 무조건 + 임
            }
            return;
        }

        // 그냥 진행해야 할 때
        // 현재 문자에 따라 무조건 아웃풋 방향으로 간다. preDir을 이용해서
        // 근데 이제 +일 때에는 특별하게 처리를 해준다.
        if(map[position.y][position.x] != '+') {
            int dir = 0;
            for (int i = 0; i < 6; i++) {
                if (pipe[i] == map[position.y][position.x]) { // 현재 파이프 모양 찾았을 때
                    if(pipeOutputAble[i][0] == preDir){
                        dir = pipeOutputAble[i][1];
                        break;
                    }
                    if(pipeOutputAble[i][2] == preDir){
                        dir = pipeOutputAble[i][3];
                        break;
                    }
                }
            }
            if(map[position.y][position.x] != 'Z') dfs(new Point(position.y + dy[dir] , position.x + dx[dir]) , dir);
        }else{
            for(int i = 0; i < 4; i++){
                if((preDir + 2) % 4 == i) continue;
                int ny = position.y + dy[i];
                int nx = position.x + dx[i];
                if(ny < 0 || ny >= h || nx < 0 || nx >= w) continue;
                if(!visited[ny][nx]) dfs(new Point(ny , nx) , i);
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        map = new char[h][w];
        visited = new boolean[h][w];

        for(int i = 0; i < h; i++){
            String string = input.readLine();
            for(int j = 0; j < w; j++){
                map[i][j] = string.charAt(j);
                if(map[i][j] == 'M'){
                    m = new Point(i , j);
                }
                if(map[i][j] == 'Z'){
                    z = new Point(i , j);
                }
            }
        }

        visited[m.y][m.x] = true;
        Loop:
        for(int i = 0; i < 4; i++){ // 여기서도 처음에 갈 수 있는 방향을 탐색해야 한다.
            // 위에서 한 것 처럼 inputAble을 보면서 해당 map[ny][nx]를 보고
            //
            int ny = m.y + dy[i];
            int nx = m.x + dx[i];
            if(ny < 0 || ny >= h || nx < 0 || nx >= w || map[ny][nx] == '.') continue;
            if(map[ny][nx] != '+') {
                for(int j = 0; j < 6; j++){
                    if(pipe[j] == map[ny][nx]){
                        if(pipeInputAble[j][0] == i || pipeInputAble[j][1] == i){ // 지금 현재 i 방향이 해당 ny , nx에 있는 파이프의 인풋이 될 수 있는지 확인해준다.
                            break;
                        }else{ // 인풋이 될 수 없을때에는 이 방향은 걸러야 한다.
                            continue Loop;
                        }
                    }
                }
            }
            if(map[ny][nx] != 'Z') dfs(new Point(ny , nx) , i);
        }

        System.out.println((result.y + 1) + " " + (result.x + 1) + " " + resPipe);
    }
}
