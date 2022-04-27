import java.util.*;
import java.io.*;

// 16724 : 피리 부는 사나이

/*
-- 전제조건
각 격자마다 정해진 방향이 있다.
그 격자를 따라서 사람들은 움직여야 한다.
이제 이 사람들이 정해진 방향대로 움직일 때 모두 safe zone 으로 들어갈 수 있도록 하는 safe zone 의 최소 개수를 출력해라.

-- 틀설계
처음에 이제 bfs 로 생각을 하였고 , 그것은 맞는 것 같아서 계속 생각했다
근데 , 해당 격자가 정해진 방향이 존재하고 , 그 방향을 따라가보니까 무엇인가 이렇게 하면 되겠다라는 생각이 들었다.

일단 , 사이클이 존재하는 경우 , 이 사람들은 여기 안에서 시작하면 safe zone 이 여기 안에 하나만 존재하면 되겠다라는 생각을 했다.
이제 , 그 생각과 이어진 경우가 , 만일 다른 지점에서 시작한 사람들이 이 사이클의 범위로 들어오는 경우?
이 경우도 , 당연하게 원래 있던 safe zone 으로 들어가면 된다.

그래서 이러한 결론이 나왔음 , 일단 사이클 내에 safe zone 은 무조건 존재 해야 한다.
그래서 , 사이클을 판단할 수 있도록 , map 에다가 dfs 함수를 호출 할 때마다.
value 를 올려서 값을 보내어서 , 몇번째로 실행됬는지에 대해서 파악 하고,
만일 움직이다가 같은 value 값을 만나게 되면 사이클이 있다라는 결론에 도달하였음

그리고 만일 다른 value 값을 만나게 되면 , 사이클이 존재하는 곳으로 들어갔거나 , 혹은 사이클로
들어간 경로로 들어간거구나 , 그러니 얘는 따로 safe zone 이 필요하지 않다

그래서 한줄 결론은 이러한 결론이 나왔음 사이클 내에서는 무조건적으로 safe zone 이 하나가 필요하다 ,
그리고 다른 지점에서 해당 사이클로 들어오는 경우는 safe zone 이 당연하게도 필요 없다 , 근데 만일 다른 곳에 사이클이 존재한다라면?
여기 safe zone 에 들어오지 못한다 , 그렇기 때문에 사이클 마다 safe zone 이 존재해야 하고 , 거기에 이어진 애들은 따로 safe zone 이 필요 없다.

그래서 이런식으로 코딩을 할 수 있다.
dir 로 각 map 의 방향들을 입력한다.
그 다음에 , 실질적으로 bfs 로 map 에다가 value 를 입력하면서 방문 처리를 하면서
이미 방문처리가 되어 있는 애들을 발견하면 , 이제 거기서 멈추는데 , 그 때 값이 같냐 다르냐에 따라서
res ++ 할지 말지를 결정하면 될 것 같다.

그래서 bfs 함수와 dir , map 변수가 필요하다.

역시나 쉽게 맞았다.
분리집합 이런 거 필요 없고 , 처음에 생각한 게 맞았음
 */
public class Main {
    public static int[][] dir , map;
    public static int H , W ,res = 0 , val = 1;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};

    public static void dfs(int y , int x , int value){
        /*
        여기서는 bfs를 진행하는데 , 거기서 끝지점을 만나면 , 그 끝지점의 값이
        현재 value 와 같으면 res++ , 아니면 그냥 다음 bfs 진행하면 된다.

        그리고 사실 queue 가 필요가 없었네
        그냥 dir 에 현재 dir[y][x] 숫자대로 현재 y , x 에다가 y + dy[dir[y][x]] , x + dx[dir[y][x]] 하면 된다.
         */

        // 끝을 만났을 때
        if(map[y][x] != -1){
            if(value == map[y][x]) res++; // 사이클이 형성된 경우에만 res++ 해준다.
            return;
        }

        // 끝이 아닌 경우에는 현재 value 값 넣으면 된다.
        map[y][x] = value;
        dfs(y + dy[dir[y][x]] , x + dx[dir[y][x]] , value); // 여기서 현재 dir[y][x] 에 있는 방향대로 진행한다.
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        map = new int[H][W];
        dir = new int[H][W];

        for(int i = 0; i < H; i++){
            String string = input.readLine();
            for(int j = 0; j < W; j++){
                char character = string.charAt(j);
                dir[i][j] = character == 'D' ? 2 : character == 'U' ? 0 : character == 'L' ? 3 : 1;
            }
        }

        for(int i = 0; i < H; i++) Arrays.fill(map[i] , -1);

        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                if(map[i][j] == -1) dfs(i , j , val++);
            }
        }

        System.out.println(res);
    }
}
