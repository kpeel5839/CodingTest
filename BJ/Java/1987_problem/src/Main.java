import java.util.*;
import java.io.*;

// 1987 : 알파벳
/*
-- 전제조건
세로 r , 가로 c칸으로 된 표 모양의 보드에서 각 칸에는 대문자 알파벳이 하나씩 적혀있다.
좌측 상단에서 시작하고 , 이 칸도 움직인 위치로 친다.
말은 상하 좌우로 인접한 4칸으로 움직일 수 있는데 같은 알파벳을 다시 밟을 수 없다(이미 밟았던 알파벳을 밟아서는 안됨
이랬을 때 가장 많이 가는 경우의 이동 칸은?
-- 틀 설계
ascii code 대문자 알파벳 A 부터 Z까지 90이다.
4방향을 계속 검사하면서 움직인다.
항상 하던 bfs를 dfs와 접목한 느낌으로 가면 된다.
 */
public class Main {
    public static int r , c , ans = 0;
    public static int[] alphabet = new int[91] , dy = {-1 , 0 , 1 , 0} , dx = {0 , 1 , 0 , -1}; // 알파벳 아스키 코드를 고려해서 90까지 인덱스를 만들어놓음
    public static char[][] map;
    public static void dfs(int y , int x , int score){
        /*
        계속 진행하는데 일단 for(int i = 0; i < 4;i ++) 이런식으로 4방향으로 항상 진행을 해주는데
        범위를 넘어가거나 그러면 잘라주고
        그리고 그게 되는데 가려는 알파벳의 방문 여부가 1이면 거기서도 그만 간다.
        그리고 dfs를 갔다가 돌아오면 , 무조건 해당 방문했던 알파벳을 다시 0으로 만들어줘야 한다.
        그 이유는 당연히 alphabet을 전역변수로 공유하고 있기 때문에 본인이 갔었던 흔적을 지워줘야하기 떄문이다.
        그래서 for(int i = 0; i < 4; i++) 로 4방향을 돌면서 적절하게 조건문으로 가지를 쳐내고
        방문하는 곳은 alphabet[map[ny][nx]] = 1; dfs(ny , nx , score + 1); alphabet[map[ny][nx]] = 0;
        으로 해준다 근데 이제 답을 도출해내는 과정은 그냥 같으면 가는 방향다 4방향 다 실패하면 score를 뽑아내는 것이
        더 효율적이지만 , 귀찮으니까 그냥 함수 맨 아래에다가 코드를 적어놔서 , 그냥 모든 score를 다 ans = Math.max(ans , score) 하게 한다.
        그래도 상관은 없음
         */

        for(int i = 0; i < 4; i++){
            int ny = y + dy[i];
            int nx = x + dx[i];
            if(ny < 0 || ny >= r || nx < 0 || nx >= c || alphabet[map[ny][nx]] == 1) continue;
            alphabet[map[ny][nx]] = 1;
            dfs(ny , nx , score + 1);
            alphabet[map[ny][nx]] = 0;
        }

        ans = Math.max(ans , score);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        map = new char[r][c];
        for(int i = 0; i < r; i++){
            String string = input.readLine();
            for(int j = 0; j < c; j++){
                map[i][j] = string.charAt(j);
            }
        }

        alphabet[map[0][0]] = 1;
        dfs(0 , 0 , 1);
        System.out.println(ans);
    }
}
