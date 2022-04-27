import java.util.*;
import java.io.*;

// 1956 : 운동
/*
-- 전체 설계
다시 시작점으로 돌아오는 가장 최단거리의 사이클을 찾으려고 한다.
최단 거리의 사이클을 찾아서 거리를 출력하라.
-- 틀 설계
일단 플로이드 워샬 알고리즘은 , dp 형식의 알고리즘이다.
어떠한 k 지점이 있다고 하였을 때 , dp[i][k] + dp[k][j] , dp[i][j] , 즉 i 에서 j 로 향할 때 ,
해당 경로를 경유하는 것이 낫나 아니면 직빵으로 직진하는 것이 낫냐 이것이 관건이다.

이러면서 하나하나씩 갱신해가는 형식의 알고리즘이다.
결국 내가 생각한 점은 , dp[i][i] 이 지점에서 , 원래 같은 경우는 [i][i] 같은 지점은 0이다 , 왜냐하면 본인을 통하는 경로는 없으니까,
근데 , 그게 아니라면 ? 본인을 통하는 지점이 필요하다면 , dp[i][i] 는 몇으로 초기화 해야 할 까?
또 , dp[i][i] 를 0이 아닌 값으로 초기화하면 , dp[i][i] + dp[i][j] 같은 경우는 어떻게 해야할까?
이 경우는 dp[i][j] 이니까 짜피 , 본인을 즉 , k가 k == i || j == k 이면 넘어가는 것이 좋을 것 같다.
i == i 인 경우는 사실 저런 경우를 그냥 넘어가도록 처리하면 , 사실 INF 값으로 처리해도 된다.
그러면 이제 만일 사이클이 돌아오는 경우를 생각해보자 , dp[i][j] + dp[j][i] 이거나
dp[i][k] + dp[k][j] + dp[j][i] 인 경우가 다 반사일 것이다.
dp[i][j] + dp[j][i] 이 두 경우를 어떻게 발생시킬 것이냐 이게 관건일 것 같은데,
이 경우는 i == j 이고 일때 짜피 발생할 것 같다. 그럼 이 경우에 , 당연하게도 값이 갱신 되지 않을까?
그리고 첫번째 경우도 한번 생각해보자 , 이것도 될 것 같다
결국 , dp[i][i] + dp[i][j] 같은 상황 즉 k == i || j == k 같은 상황만 , 고려하면 될 것 같다.
짜피 min 값 집어넣으면 되는데 , 이 경우에 INF 값을 넘어서 , 값이 음수로 변해버릴 것 같아서 그냥
진행하는 게 나을 것 같다.

-- 결론
경유하는 경로가 있나 확인하고 , 경로가 없는 곳들은 INF 값으로 초기화를 해놓는다. (dist[i][i] 같은 경우도 경로가 없으니 INF)
그리고 dp[i][k] + dp[k][j] 같은 플로이드 워샬의 주축이 되는 연산에서 , INF + ~ or ~ + INF or INF + INF 인 경우는 값이 음수로 나와 정확하지 않게 된다.
그래서 dist[i][k] == INF || dp[k][j] == INF 인 경우에는 연산을 하지 않고 넘어간다 , dp[i][j] == INF 인 경우는 짜피 더하기 하는 것도 아니고 , Math.min(dp[i][j] , dp[i][k] + dp[k][j])
연산을 하게 된다면 , k 지점을 경유하는 지점이 있는 것이니 막으면 오히려 안된다 , 그래서 이런 식으로 dp[i][i] 를 INF 로 초기화 해놓고서,
본인에게 돌아오는 지점을 구할 수 있고 , 플로이도 워샬 알고리즘의 특성상으로 계속 최소값으로 초기화하게 되면서 , 본인에게 돌아오는 가장 최소의 사이클을 찾는다.
0으로는 못한다 , 왜냐하면 , 애초에 distance 값에 0이 없으니까 , 설령 0이 있다고 한들 , dist[i][i] == 0 이면 그게 맞는 값이다 , 쩃든
이렇게 하게 되면 본인에게 돌아오는 최소 비용의 사이클을 찾을 수 있고 이렇게 하면 for(int i = 1; i <= V; i++) ans = Math.min(ans , dist[i][i]) 를 이용해서 최소의 사이클을 찾을 수 있다.
 */
public class Main {
    public static int toInt(String number){
        return Integer.parseInt(number);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int V = toInt(st.nextToken());
        int E = toInt(st.nextToken());
        int INF = Integer.MAX_VALUE;

        int[][] dist = new int[V + 1][V + 1];

        for(int i = 1; i <= V; i++) Arrays.fill(dist[i] , INF);

        for(int i = 0; i < E; i++){
            st = new StringTokenizer(input.readLine());
            dist[toInt(st.nextToken())][toInt(st.nextToken())] = toInt(st.nextToken());
        }

//        for(int i = 1; i <= V; i++) System.out.println(Arrays.toString(dist[i]));

        for(int k = 1; k <= V; k++){
            for(int i = 1; i <= V; i++){
                for(int j = 1; j <= V; j++){
                    if(dist[i][k] == INF || dist[k][j] == INF) continue; // 해당 경로가 없을 때에만 넘어가고
                    dist[i][j] = Math.min(dist[i][j] , dist[i][k] + dist[k][j]); // 경로가 있을 때에는 , 그대로 진행하면
                } // 경유하는 경로가 있을때에 그대로 진행한다 , i -> j 가 없더라도 경유하는 경로가 있는 거니까
            }
        }

//        System.out.println("ans");
//        for(int i = 1; i <= V; i++) System.out.println(Arrays.toString(dist[i]));

        int ans = INF;

        for(int i = 1; i <= V; i++){
            ans = Math.min(ans , dist[i][i]); // 다시 본인으로 돌아오는 지점을 검사해서 , 그대로 INF 면 없는 것 (사이클이)
        }

        System.out.println(ans == INF ? -1 : ans);

    }
}
