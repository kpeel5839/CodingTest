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

결국 푼 방법
일단 dfs로 선택을 할 때 조합을 중복없이 짜기 위해서
map 배열을 1차원적으로 펴서 구성하였다.
5 * 5 배열이니까 짜피 1차원 배열로 표현할 때 있어서 0 ~ 4 는 1번째 행 , 5 ~ 9 는 2번째 행... 이렇게 가면 된다.
그렇게 해서 1차원 배열로 풀어냈고 , 조합을 선택할 때에도 주변에 있는 것들을 조합으로 선택하는 것이 아닌
지금 이전에 고른 수보다 더 큰 수를 이번 depth 에 고르는 경우로 모든 경우를 시도해서 조합을 만들음
예를들어서 [1 , 2 , 3 , 4 ,5 .... ] 이렇게 가거나 혹은 [1 , 5 , 9 , 14 , 15 ..] 이렇게 말이다.
주변에 있는 것으로 조합을 선택하기 보다는 그냥 아무 인덱스나 골라서 조합에다가 집어넣은 뒤
공주 4명 이상 , 7명이 결성이 되면 거기서 이제 check 하는 형식으로 가야한다 , 이 애들이 다 인접한 애들인지
그래서 결과적으로 조합을 만든다(붙어있든 말든 상관안하고 , 내가 선택한 것은 visited에다가 선택했다고 체크 후 , selected 배열에다가 몇번째 인덱스 애를 선택했는지 저장)
-> 해당 조합을 check 함수에다가 넘긴다. -> check 함수는 이것을 받아서 지금 받은 이 조합이 인접한지만 확인한다 , 이전에 조합할 때 visited[index] = 1 처리 했으니까 여기 기준으로
-> 그래서 7명이 다 붙어있다는 것을 확인하면 ?(bfs로 돌렸을 때 count = 7 이 되면) 올바른 조합이라고 하고 , 아니면 인접하지 않은 것이니 return false 를 한다.
-> 그래서 return true 를 받은 조합만 더해서 결과를 출력한다.

생각해보면 내가 이전에 지나온 곳을 탐색하지 않은 처음 접근 방식과 매우 유사하다,
중복된 경우를 나오지 않게 하기 위해서 , 지금 현재의 depth 이상만 탐색하는 부분에서 매우 흡사하다.

근데 앞으로 이런 문제를 더 풀어보면서 더 많은 생각을 해봐야겠다.
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