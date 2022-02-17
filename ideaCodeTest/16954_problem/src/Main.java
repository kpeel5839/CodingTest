import java.util.*;
import java.io.*;

// 16954 : 움직이는 미로 탈출
/*
--전제 조건
8 * 8 체스판에서 , 벽이 아래로 내려오는데
내가 먼저 움직이고 , 벽이 내려온다 , 플레이어가 있는 곳에 벽이 내려오면 플레이어는 죽는다.
그리고 벽이 더 움직일 수 없는 행 , 즉 맨 끝 행으로 오게 되면 사라진다.
이 경우에 , 플레이어가 탈출이 가능할까?
가능하면 1 , 불가능하면 0을 출력하라 , 캐릭터는 8 방향 다 움직일 수 있고 , 서 있을 수도 있다.
그러니까 캐릭터의 가능한 움직임은 , 9가지이다.

그리고 벽은 맨 끝에 도달하자 말자 없어지는 것이 아니다.
........
........
........
........
........
.#######
#.......
........
이 경우가 가능한 이유는 플레이어가 대각으로 움직이는 것이 가능하기 때문이다.

벽이 맨 끝에 도달하자 말자 없어지는 것이 아닌 것의 증거가 이 사례이다.
........
........
........
........
#.......
.#######
#.......
........
여기서 만약 벽이 맨 끝에 도달하자말자 없어지는 것이 아니라면 , 플레이어가 오른쪽으로 가서
벽이 부서지고 그 다음 왼쪽 , 그 다음 오른쪽으로 가면 모든 벽을 피하게 되면서 , 끝 지점까지 갈 수 있지만
그 것이 아니니 , 벽은 맨 끝에 도달하자말자 부서지는 것이 아니다.

--틀 설계
이거는 어떨 까? 짜피 모든 벽은 8칸이 떨어지면 끝난다
즉 , 벽의 경우의 수는? 8가지이다.
즉 , 정확히 말하면 최소 벽이 존재하는 경우는 8가지라는 것이다
만일 맨 끝에 있는 벽이 내려온다고 가정해보자.

1번째 경우
########
........
........
........
........
........
........
........

2번째 경우
........
########
........
........
........
........
........
........

3번째 경우
........
........
########
........
........
........
........
........

4번째 경우
........
........
........
########
........
........
........
........

5번째 경우
........
........
........
........
########
........
........
........

6번째 경우
........
........
........
........
........
########
........
........

7번째 경우
........
........
........
........
........
........
########
........

8번째 경우
........
........
........
........
........
........
........
########

9번째 경우
........
........
........
........
........
........
........
........

즉 여기서 볼 수 있는 것은 , 플레이어가 , 모든 것을 고려하였을 때 , 9번째 상황까지 버텼다? (정확히 말하면 , 2번째 경우가 1초부터이니 , 9번째 경우는 8초이다)

그래서 쨋든 Point 클래스를 만들어서 , 좌표와 , value = time (버틴 시간) 을 해서 , 처음에는 0으로 시작하고,
움직이면 1을 추가해서 현재의 상황에 맞춰서 얘가 value >= 8 이면? 시마이다. 그러면 바로 1출력하고 , 그렇지 않다면 0을 출력하면 된다.

그래서 3차원 배열을 만들어서
wall[][][] 이렇게 만들어서 = new char[9][8][8] 이렇게 만들어놓고서 [0] = 초기 맵 , [8] 은 아무것도 없고 . 만 찍혀있는 맵으로 관리를 한다.
솔직히 아무것도 없는 거 안 만들어도 될 듯 new char[8][8][8] 이렇게 관리하고 한칸씩 내린 것들 관리해서 value 값으로 현재 map을 정하고,
그 다음에 가능한 움직임으로 가면 될 듯

그리고 현재 상황에서 움직일 수 있는 곳으로 간 다음에 꼭 해줘야 하는 것이 , 지금 queue 에서 뽑아 낸 값이 , 벽이랑 같이 존재하냐임,
그러니까 Point point = queue.poll() wall[point.value][point.y][point.x] == '#' 이면 얘는 바로 나가리

이런식으로 진행하면 될 듯
 */
public class Main {
    public static int n = 8;
    public static boolean success = false;
    public static char[][][] mapList = new char[n][n][n];
    public static boolean[][][] visited = new boolean[n][n][n];
    public static int[] dx = {0 , 1 , 1 , 1 , 0 , -1 , -1 , -1} , dy = {-1 , -1 , 0 , 1 , 1 , 1 , 0 , -1};
    public static class Point{
        int y;
        int x;
        int time;
        public Point(int y, int x){
            this.y = y;
            this.x = x;
        }
        public Point(int y, int x ,int time){
            this.y = y;
            this.x = x;
            this.time = time;
        }
    }
    public static void setMapList(int index){
        /*
        여기서 8가지의 경우의 맵들을 만들 것임 , 초기 initMap을 통해서
        현재 넘어온 번째 수를 이용해서 , 바로 이전 것을 queue 에다가 담은 다음에
        queue 의 값들을 다 row + 1 씩하면서 현재 넘어온 번째 수 map 에다가 찍는다.
        그러면 될 듯
         */
        Queue<Point> queue = new LinkedList();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(mapList[index - 1][i][j] == '#') queue.add(new Point(i , j));
            }
        }

        while(!queue.isEmpty()){
            Point point = queue.poll();
            if(point.y + 1 >= n) continue; // 바깥으로 나가는 거
            // 아니면 다 찍어 지금 mapList 에다가
            mapList[index][point.y + 1][point.x] = '#'; // 아래다가 찍어보리
        }
    }
    public static void bfs(){
        /*
        0 , 0 에서 시작해서 mapList 보고서 갈 수 있는 지 확인해서 가고
        만일 value >= 8 이다? 그러면 바로 success = true 로 하고
        바로 탈출한다.
        visited 필요 없고 , 있어서는 안된다 오히려
         */

        Queue<Point> queue = new LinkedList();
        queue.add(new Point( n - 1,0  , 0)); //시작 위치는 맨아래에서 맨 왼

        while(!queue.isEmpty()){
            Point point = queue.poll();
            if(point.time >= n){ // 8초 이상 버티면 바로 탈출 쌉가능
                success = true;
                break;
            }

            if(visited[point.time][point.y][point.x]) continue;
            visited[point.time][point.y][point.x] = true;

            if(mapList[point.time][point.y][point.x] == '#') continue; // 지금 서있는 곳이 지옥이면 나가리
            for(int i = 0; i < 9; i++){
                if(i == 8){ // 가만히 서있기 이거는 체크 할 필요가 없다 , 이미 가능한 곳으로 이동하였고 , 내가 서있는 곳에 #이 오는 경우는 위에서 걸러냈기 때문에 , 이것은 걸릴 일이 x
                    queue.add(new Point(point.y , point.x , point.time + 1));
                    continue;
                }
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(check(ny , nx) || mapList[point.time][ny][nx] == '#') continue; // 지금 가려는 곳이 # 이면 못가 (현재 time의 map을 봐야함 , 내가 움직이고 벽이 움직이니까)
                queue.add(new Point(ny , nx , point.time + 1)); // 이동하고 , time + 1 해서 , 다른 map을 탐사
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                for(int c = 0; c < n; c++){
                    mapList[i][j][c] = '.';
                }
            }
        }

        for(int i = 0; i < n; i++){
            String string = input.readLine();
            for(int j = 0; j < n; j++){
                mapList[0][i][j] = string.charAt(j);
            }
        }

        for(int i = 1; i < n; i++){
            setMapList(i);
        }

//        for(int i = 0; i < n; i++){
//            mapPrint(i);
//        }

        bfs();

        if(success) System.out.println(1);
        else System.out.println(0);
    }

    public static boolean check(int y , int x){
        if(y < 0 || y >= n || x < 0 || x >= n) return true; // 범위 넘어감
        return false; // 범위 안넘어감
    }

    public static void mapPrint(int index){
        System.out.println(index + 1 + "번째 맵");
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.print(mapList[index][i][j]);
            }
            System.out.println();
        }
    }
}
