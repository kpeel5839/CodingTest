import java.util.*;
import java.io.*;

// 2098 : 외판원 순회
/*
-- 전체 설계
한 외판원이 어느 한 도시에서 출발해서 , N개의 도시를 모두 거쳐서 다시 원래 도시로 돌아오는 순회 여행 경로를 계획하려고 한다.
단 , 한번 갔던 도시로는 다시 갈 수 없다 ,(맨 마지막에 여행을 출발했던 도시로 돌아오는 것은 예외)
이런 여행 경로는 여러가지가 있을 수 있는데 , 가장 적은 비용을 들이는 여행 계획을 세워라.
-- 틀 설계
일단 첫번째로 알아야 할 점은 , 최소비용을 가지는 사이클은 항상 모든 지점을 지난다,
즉 이 말은 어떠한 정점을 선택하더라도 , 최소비용의 사이클을 찾알 때에는 무조건 해당 정점으로 다시 들어온다는 것이고
그 경우에서의 최소비용은 어떠한 정점으로 시작해도 똑같다 , 짜피 다 돌아서 돌아오는 것이니까
원을 생각하면 쉬운데 , 원의 둘레는 어디서 시작해도 다 똑같다 이것과 비슷하게 생각하면 된다.

그래서 dfs 를 통해서 비트마스킹과 , 해당 정점을 memorization 하는 방식으로 진행하면 된다.
일단 map을 받고
그 다음에, 0번쨰에서 bitmask 값을 1로 주고 시작한다,
이 경우는 dp[1][0] 으로 시작하는 것인데,
이게 의미하는 것은 현재 0일때 , 최소비용을 구하는 것이다.
즉 dp[bitmask][now] 이 경우는 현재 now 정점일 때 , 해당 도시들을 거쳐왔을 경우에 최소비용이다.

이렇게 하려면 일단 , tsp(1 , 0) 으로 시작해서
맨 마지막 , 모든 도시를 다 방문해서 다시 0번째 정점으로 돌아와야할 때에 대한 처리와
그에 대한 예외처리를 할 수 있는데 항상 순회할 수 있는 경우만 입력으로 주어지기 때문에 답이 정해지지 않는 경우는 없다.

그래서 그렇게 가고 , 만일 dp[bitmask][now] 값이 이미 존재한다면 ? 구할 필요가 없다 왜냐하면 ,
이미 이 정점은 출발비용까지 다시 돌아가는 최소비용을 찾아서 담아두고 있는 상태이기 때문이다.

그래서 이것은 가볍게 넘어가고
그리고서 , 이제 아직까지 선택하지 않은 정점으로 넘어가게 될 건데
이 경우에는 if(bitmask&(1<<i) == 0 인 경우와 , map[now][i] != 0) 이 아닌 경우 실행할 수 있고

또 안에서 dp[bitmask][now] = Math.min(dp[bitmask][now] , tsp(visited | 1 << i , i) + map[now][i]); 로 구할 수 있다.

그리고서 , 이렇게 최종적으로 다하고 , return dp[bitmask][now] 를 해주면 된다.

그러면 재귀적으로 돌고돌아서 최종적으로 dp[1][0] 의 결과를 반환하게 될 것이고
이 경우는 즉, 0번을 선택하였을 때 , 최소비용이라고 할 수 있다.

정리하자면 사이클을 고려해서 , 어떤 정점에서 출발하는 지 상관없이 , 무조건 0번 정점으로 돌아오는 경우를 가정하고
모든 도시를 다 돌았을 때 0번 정점으로 가지 못하는 경로가 있다면 그 정점은 유효하지 않으니 , 버린다.
그리고 bitmask 로 하나하나 정점을 돌아보면서 , dfs를 실행하면서 , 지속해서 dp[bitmask][now] 값을
현재의 값을 계속 갱신한다 , 최소비용으로 그렇게 해서 , 결국 최소비용을 얻어내게 된다.
for문에서 bitmask 를 이용해서 모든 정점을 돌아보는 점을 이용한 것이다.
그러니까 예를 들어서 , 0001 이렇게 시작했으면 , 0011 , 0111 , 1111 , 1011 , 1111 , 1001 , 1011 ... 이런식으로 dfs 적인 특성을 이용해서
결과를 구하는 것이다 , 즉 dfs , bitmask , dp 의 특성을 이용하여서 , 결과를 도출해내는 문제라고 할 수 있다.

-- 해맸던 점
dp 값을 세팅하지 않아서 , 0을 반환하였었고,
INF 값으로 바꾸니까 dp 값들이 이미 존재하는 것처럼 memorization 부분에서 오류가 나서
dp[vitmask][now] != INF 로 바꾸니 잘 되었음
 */
public class Main {
    public static int[][] dp , map;
    public static int n;
    public static final int INF = 100000000; // 또 Inf 값 넣으면 괜히 더했을 때 음수되면서 값 이상해질 수 있으니까
    public static int tsp(int bitmask , int now){
        /*
        처음에는 모든 도시를 다 돌았을 때에 대한 처리를 해주고
        그 다음에 이미 방문한 정점인 지 체크를 해준다.
        그 다음에는 현재의 정점에서 모든 정점으로 가봤을 때의 경우를 실행해주면서
        최소 값으로 초기화를 해준다.

        그 다음에 마지막에 계속해서 , 이전 호출들이 최소값을 구할 수 있도록, dp 값을 반환해준다.
         */
        if(bitmask == (1 << n) - 1){
            if(map[now][0] == 0) return INF;
            return map[now][0];
        }

        // memorization
        if(dp[bitmask][now] != INF){ // 내가 지정해놓은 값이 아닌 , 다른 값이 되어있으면 , 이미 0번 까지의 최소경로를 구한 것 , 그러니까 반환
            return dp[bitmask][now];
        }

        for(int i = 1; i < n; i++){
            if(now != i && (bitmask & (1 << i)) == 0 && map[now][i] != 0){
                dp[bitmask][now] = Math.min(dp[bitmask][now] , tsp(bitmask | 1 << i , i) + map[now][i]);
            }
        }

        return dp[bitmask][now];
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());

        map = new int[n][n];
        dp = new int[1 << n][n];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < (1 << n); i++){
            Arrays.fill(dp[i] , INF);
        }

        System.out.println(tsp(1,0)); // 무지성으로 0번으로 시작하는 것 , 이게 사이클이 존재한다는 하에 시작정점은 상관없다는 것의 산물이다.
    }
}