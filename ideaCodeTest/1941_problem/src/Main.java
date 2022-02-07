import java.util.*;
import java.io.*;

// 1941 : 소문난 칠공주

/*
-- 전제조건
총 25명의 여학생의 자리 배치도가 주어졌을 때,
S가 이다솜 파인데 , 이다솜파와 임도연가 나뉜다 , 근데 여기서 이다솜파가 우위를 점해야하는데,
이럴려면 파를 형성해야 한다.
그래서 소문난 칠공주를 결합하였는데,
이 경우에 이다솜파와 임도연파가 적절히 섞여서 7명의 인접한 그룹을 만들면 된다.
7명의 자리는 가로나 세로로 인접해야 한다 , (bfs 로)
그리고 꼭 이다솜파로만 이루어져 있어야 하는 것은 아니기에 임도연 파가 섞여있어도 된다.
근데 , 임도연파가 우위를 점하면 안되기 때문에 7공주 파에서 적어도 4명 이상은 임도연 파여야 한다.
학생들의 자리 배치도가 주어질 때 , 소문난 칠공주를 구성할 수 있는 경우의 수를 출력하시오.
-- 틀 설계
왼쪽과 , 위를 탐색하지 않는다면?
그런다면 중복된 경우를 탐색하지 않지 않을까?
왜냐하면 왼쪽 위는 이미 이전에 해결이 되었으니까,
오른쪽 , 아래만 탐색하는 경우를 진행해보자.

YYYYY
YYYYS
YSYYS
YSYYS
SSYYS
S에서만 시작하면 될 것이라고 생각했지만,
오른쪽 , 아래로만 움직일 수 있기 때문에 위에 상황과 같은 반례이다.

근데 또 위와 아래만 탐색하면 안되는 상황이 발생하였음, 위와 같은 경우는 위랑 아래만 탐색하면 안된다,
그렇다고 4방향을 다해버리면? 중복된 방문이 가능하다.

그러면 dfs만 가능한 것일까?

dfs로 진행을 하는데 visited 를 게속 유지를 한다.

그 다음에 내가 시작한 위치만을 영구 visited 처리를 한다.

그리고 dfs로 이동할 때에는 영구 visited 한 곳은 못가게 처리를 하는 것이다.
말이 안되는 것 같지만 한번 해보자.

결국 답을 봤음..
그냥 저 자리 배열을 하나씩 돌면서 그냥 조합 하나하나씩 다해본다.
그 다음에 select 한 것에서 bfs 시작해서
고른 거 인접했는지 확인하는 것임 , 생각보다 쉬운 문제였는데
왜 그렇게 할 생각을 못했을 까 , dfs로 주변 탐색할 생각만 했지
 */
public class Main {
    public static int n = 5 , ans = 0;
    public static Queue<Integer> queue;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0} , visited = new int[n * n], checkVisited , selected = new int[7];
    public static char[][] map = new char[n][n];

    public static void dfs(int depth , int count , int countS){
        /*
        count == 7 이 되면 끝내고
        혹은 끝까지 depth 가 도달했을 때 끝낸다 y == n && x == 0
        그리고 반복문은 for(int i = 0; i < 2; i++) 로 진행하고
        선택 혹은 선택하지 않음을 고른다.
         */
        if(count == 7){
            if(countS >= 4){
                if (check()) {
                    ans++;
                }
            }
            return;
        }

//        System.out.println(depth);

        for(int i = depth; i < n * n; i++){
            visited[i] = 1;
            selected[count] = i;
            if(map[i / 5][i % 5] == 'S') dfs(i + 1 , count + 1 , countS + 1);
            else dfs(i + 1 , count + 1 , countS);
            visited[i] = 0;
        }
    }
    public static boolean check(){
        queue = new LinkedList<>();
        queue.add(selected[0]);
        checkVisited = new int[n * n];
        int count = 0;
        while (!queue.isEmpty()) {
            Integer position = queue.poll();
//            System.out.println(position);
            int y = position / 5;
            int x = position % 5;
            for(int i = 0; i < 4; i++){
                int ny = y + dy[i];
                int nx = x + dx[i];
                if(ny < 0 || ny >= n || nx < 0 || nx >= n || checkVisited[ny * 5 + nx] == 1 || visited[ny * 5 + nx] == 0) continue;
                count++;
                checkVisited[ny * 5 + nx] = 1;
                queue.add(ny * 5 + nx);
            }
        }
        if(count == 7) return true;
        else return false;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        for(int i = 0; i < n; i++){
            String string = input.readLine();
            for(int j = 0; j < n; j++){
                map[i][j] = string.charAt(j);
            }
        }

        dfs(0 , 0 , 0);
        System.out.println(ans);
    }
}