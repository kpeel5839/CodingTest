import java.util.*;
import java.io.*;

// 1405 : 미친 로봇
/*
-- 전제조건
통제가 불가능한 미친 로봇이 있고
방문하지 않았던 곳만 계속 방문하는 경우에 로봇의 움직임이 단순하다고 한다.
이 경우에 로봇의 이동 경로가 단순할 확률을 구하는 프로그램을 작성하시오.
-- 틀 설계
로봇의 방향마다 게속 확률을 구해주고 (가는 방향마다)
그리고서 만일 로봇의 이동방향이 단순하다고 하면
result 에다가 더해준다.
double result = 0 으로 계속 로봇의 움직임에다가 확률을 계속 곱해주면서 가다가
성공하면 result += value 를 더해주는 형식으로 간다.
prob 를 확률로 저장하고
방향과 확률을 동일하게 할 수 있도록
동일한 순서로 작성한다.
bfs가 아닌 dfs로 주변 탐색을 진행할 것이고
이동한 횟수가 N번이 되면 더해주면 된다.
이미 간 방향을 간다면 ? 거기서 return 해주니 , 로봇의 이동이 단순할 때에만 처리가 되기 때문에
result += value 에서 따로 무슨 동작을 추가적으로 할 필요는 없다.

-- 해맸던 점
prob 값을 다 안받았었음
그래서 살짝 헷갈리고 나머지는 그냥 구현하였음
String.format을 사용하는 방법을 알게 되었음
 */
public class Main {
    public static int n;
    public static double result = 0;
    public static int[] prob = new int[4] , dy = {0 , 0 , 1 , -1} , dx = {1 , -1 , 0 , 0};
    public static boolean[][] visited = new boolean[30][30]; // 최대 이동하는 횟수가 14번이니까 15 중간에서 시작하면 절대 밖으로 나가지 않을 크기로 잡아준다.
    // 최대 이동이 15에서 시작해서 14칸 이동하면 29이니까 30에 걸린다. 그러니까 30 30 사이즈는 쌉가능
    public static void dfs(int y, int x, int count , double value){
        /*
        그냥 계속 visited
         */
        if(count == n){
            result += value;
            return;
        }

        for(int i = 0; i < 4; i++){
            int ny = y + dy[i];
            int nx = x + dx[i];
            if(!visited[ny][nx]){
                visited[ny][nx] = true;
                dfs(ny , nx , count + 1 , value * 0.01 * prob[i]);
                visited[ny][nx] = false;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken());

        for(int i = 0; i < 4; i++){
            prob[i] = Integer.parseInt(st.nextToken());
        }

        visited[15][15] = true;
        dfs(15 , 15 , 0 , 1.0);

        System.out.println(String.format("%.10f" , result));
    }
}
