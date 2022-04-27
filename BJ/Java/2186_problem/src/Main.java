import java.util.*;
import java.io.*;

// 2186 : 문자판
/*
-- 전체 설계
문자가 있고 , 해당 문자를 경로를 가면서 채운 경우의 수를 출력한다.
k 가 주어지면 , 최대 k 칸을 갈 수가 있다.
이 경우에 , 문자판을 순회하면서 , 단어를 만드는 경우를 모두 구하시오.

-- 틀 설계
그냥 첫번째 글자로 시작해야한다.
첫 번째 글자로 시작하면서 1로 시작한다. 인덱스 값을,
그리고 주변을 탐색하는데 , 여기서 중요한 점은 , 그냥 1칸씩만 탐색해보는 것이 아니라 한 방향으로
본인이 가진 갈 수 있는 최대거리로 , 가면서 result 에다가 더한다.

그리고 종료조건은 이것이다.
string.length - 1 == depth && map[i][j] == string.charAt(depth)
일 때 1을 리턴하면 된다.

그리고 dp[i][j] != -1 && map[i][j] == string.charAt(depth)
라면 여기서는 또 dp[i][j] 값을 반환해주고

그것도 아니라면 본인이 갈 수 있는 방향으로 다 가보면서 , 경우의 수들을 모으고
본인 칸의 return dp[i][j][depth] = result; 를 해준다.

 */
public class Main {
    public static char[][] map;
    public static int[][][] dp;
    public static int H , W , K;
    public static int ans = 0;
    public static String goal;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};

    public static int dfs(int y, int x, int depth){
        /*
        현재의 좌표와 depth 만 있으면 된다.
        그러면 현재 내게 주어진 단어에서 , 맞는 것들만 찾으면 된다.

        그래서 일단 끝내기 조건 , 즉 단어를 완성하는 것을 찾은 경우는
        goal.length() - 1 == depth && map[y][x] == goal.charAt(depth);
        일 떄 끝내기 조건이고

        dp[y][x][depth] != -1 && map[y][x] == goal.charAt(depth)

        그리고 주변에다가 다 dfs로 던지면서 결과값들 모아서 , 마지막에
        return dp[y][x][depth] = result; 해야함
         */

        if(goal.length() - 1 == depth){
            return 1; // 내가 짜피 같지 않은 애들은 거른다 , 그러니까 , map[ny][nx] != goal.charAt(depth)인 경우는 존재하지 않는다.
        }

        if(dp[y][x][depth] != -1) return dp[y][x][depth];

        int result = 0;

        for(int i = 0; i < 4; i++){
            for(int j = 1; j <= K; j++){
                int ny = y + dy[i] * j;
                int nx = x + dx[i] * j;

                if(check(ny , nx) || map[ny][nx] != goal.charAt(depth + 1)) continue;

                // 범위를 벗어나고 , 다음 글자와 다른애들은 걸렀으니까 , 이제 다음문자인 애들만 온다.
                result += dfs(ny , nx, depth + 1);
            }
        }

        return dp[y][x][depth] = result;
    }
    public static boolean check(int y, int x){
        if(y < 0 || y >= H || x < 0 || x >= W) return true; // 범위를 넘은 경우
        return false; // 범위를 넘지 않은 경우
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new char[H][W];

        for(int i = 0; i < H; i++){
            String string = input.readLine();
            for(int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j);
            }
        }

        goal = input.readLine();
        dp = new int[H][W][goal.length()]; // dp 를 3차원으로 관리하는 이유는 , 해당 글자가 동일한 글자가 반복된다고 가정해보면

        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                Arrays.fill(dp[i][j] , -1);
            }
        }

//        for(int i = 0; i < H; i++){
//            for(int j = 0; j < W; j++){
//                for(int c = 0; c < goal.length(); c++){
//                    System.out.print(dp[i][j][c] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }

        // 그러면 만약에 얘가 HWHW 인데 , 해당 위치의 H가 2번째로 올때에는 경우가 3개가 존재한다고 하자, 그러면 첫번째 글자에는 포함이 안된다. 여기서 HW 만 이어지면
        // 얘는 장땡이기 때문이다 , 만일 이 위치에서 다시 H로 시작하게 되면 분명히 혼선이 빚어질 것이다. 얘는 분명히 , 3번째 글자일 떄 , 3가지의 경우인데 , 마치 첫번째 글자로 여기를 선택해도
        // 3가지의 경우의 수가 가능한 것 처럼 말이다 , 그래서 3차원 배열을 유지해 , 여기가 몇번째 글자로 왔을 때 , 끝까지 단어를 만들 수 있는 경우의 수는 이렇다라는 것을 보여줘야 한다.
        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                if(goal.charAt(0) == map[i][j]){
                    ans += dfs(i , j , 0); // 0으로 시작해야함 , 이 위치에서 시작하기 때문에 , 이 위치에서 쭉쭉 뻗어나가야함
                }
            }
        }

        System.out.println(ans);
    }
}