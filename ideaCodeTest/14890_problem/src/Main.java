import java.util.*;
import java.io.*;

public class Main {
    public static int n, l;
    public static int[][] map;
    public static int[][] bridge;
    public static int[][] visited;
    public static int count = 0;
    public static int[] dx = {0, 1};
    public static int[] dy = {1, 0}; //내려가는 것과 오른쪽으로만 할 것임 , 0 == 아래 , 1 == 오른쪽
    public static int end;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());
        end = n;

        map = new int[n][n];
        bridge = new int[n][n];
        visited = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(input.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < n; i++) {
            checkBridge(0, i, 0); //이거는 아래로 내려가는 것들
            clear();
            checkBridge(i, 0, 1); //이거는 오른쪽으로 가는 것들
            clear();
        }
        System.out.println(count); //가능한 bridge 출력
    } // 3 2 2 2

    public static void clear(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                bridge[i][j] = 0; // bridge는 1이 놓이면 그것이 경사로라고 생각하자
                visited[i][j] = 0;
            }
        }
    }

    public static void checkBridge(int y ,int x , int dir){
        //내려가는 방향 , 오른쪽으로 가는 방향 2방향이 존재함
        //돌아가는 경우 , 그냥 앞으로 쭉 가능 경우가 존재함
        //돌아가는 경우는 자신보다 큰 것을 만났을 경우이고
        //앞으로 그냥 쭉 가는 경우는 자신과 같은 것을 만났다거나 , 혹은 자신보다 작은 것을 만나는 경우임
        int now = map[y][x];
        int stack = 0;
        while(x + dx[dir] != end && y + dy[dir] != end){ //끝을 만날때까지 진행함 컨셉 자체를 끝까지 가면 성공하는 걸로 가자 요 while에서 빠져 나가면 끝나는 걸로 살아남은 거니까
            x += dx[dir];
            y += dy[dir]; //일단 앞으로 갈 수 있다는 것을 확인했으니까 앞으로 감. 다리를 놓는 상태와 아닌 상태를 계속 검사해야함
            if(now == map[y][x]){ //앞으로 왔는데 now 가 똑같다? 그냥 감
                if(stack == 0) { //현재 다리를 놓는 상태가 아니라면
                    continue;
                }
                else{ //현재 다리를 놓는 상태라면
                    if(bridge[y][x] == 0) {
                        bridge[y][x] = 1;
                        visited[y][x] = 1;
                        stack--;
                    }
                    else{
                        return;
                    }
                }
            }
            else if(now > map[y][x]){ //작은 것을 만나는 경우를 먼저 해보자 , 작은 것을 만나는 경우는 그냥 앞으로 전진해야함 now가 나보다 작은 상태임 지금
                if(Math.abs(now - map[y][x]) >= 2 || stack != 0 || bridge[y][x] != 0){ //현재 다른 값을 만났을 때 2를 초과한다면? 그냥 바로 return
                    return;
                }
                now = map[y][x]; //now를 현재 계단으로 초기화
                bridge[y][x] = 1; //일단 계단을 놓는 것.
                visited[y][x] = 1;
                stack = l - 1; //내가 한 계단을 높여놓고 stack을 올려줌 (계단을 놓는 상태로 만든 것)
            }
            else if(now < map[y][x]){ //큰 것을 만나는 경우 돌아가야한다.
                if(visited[y][x] != 0){
                    now = map[y][x];
                    continue;
                }
                if(Math.abs(now - map[y][x]) >= 2 || stack != 0){ //현재 다른 값을 만났을 때 2를 초과한다면? 그냥 바로 return
                    return;
                } //3 2 2 2 3 이런 경우일 때 l만큼 돌아가주는 거임
                visited[y][x] =1;
                x -= dx[dir] * l;
                y -= dy[dir] * l; //돌아가줌
                if(x < 0 || y < 0) return;
                if(bridge[y][x] != 0) return;
                now = map[y][x];
                bridge[y][x] = 1;
                stack = l - 1;
            }
        }
        if(stack == 0){ //다리를 놓던 상황에서 빠져나온게 아닌 것
            count++;
        }
    }
}
