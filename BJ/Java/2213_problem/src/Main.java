import java.util.*;
import java.io.*;

// 2213 : 트리의 독립집합

/*
-- 전제조건
독립집합이란 서로 인접하지 않은 쌍을 의미한다.
현재 트리가 주어지고 , 정점의 가중치가 주어졌을 때  ,
최대 독립 집합을 구하여 , 집합의 가중치를 출력하고 , 집합에 소속된 정점을 오름차순으로 출력한다.
-- 틀설계
일단 그냥 어떠한 정점이든 루트가 될 수 있으니
1번을 루트로 잡자(트리의 특성)

일단 인접하지 않은 정점들을 고르기 위해서는,
dfs 로 생각을 해야할 것 같은데
이전에 선택했다면? 이전 것을 선택했다고 넘기는 것이다.
인자를 하나 두어서 현재 것을 선택하였으면 , 1로
아니면 0으로 넘기는 것이 좋을 것 같다.
그러면 현재 해당 인자가 1이라면 선택할 수가 없고,
0이라면 선택하거나 하지 않을 수가 있다.

선택하지 않는 경우는 무조건 0으로 넘기면 된다.

일단 그리고 트리라는 것을 고려해서,
하나의 정점에서 쭉 뻗어나가는 즉 단방향으로 진행할 수 있다라는 것을 알 수 있다.

visited 로 관리하기 보다는 본인의 부모로 가지 않도록 조정을 하자.
일단 당연하게도 선택하는 경우와 , 선택하지 않는 경우
선택하는 경우는 무조건 적으로 2단계 이상을 내려가야 하고
선택하지 않는 경우는 다음 것도 바로 선택이 가능하다.

근데 이제 어느정도 전제조건은 만족하였으니
가장 중요한 , 속도를 위한 방문처리가 중요하다.

일단 내가 가정한 것을 실행해보자.(일단 잘 되는지 확인을 해야지 , 방문처리도 되기 떄문에)
근데 이게 속한 정점도 출력해야 되서 살짝 애매하네..

와 그래도 맞았음...

-- 결론
내 알고리즘은 현재
이전에 값을 선택하지 않았을 때에는 , 얻을 수 있는 최대값과
이전에 값을 선택하였을 때 , 즉 현재 선택하지 못하는 경우의 최대값을 가져와서
그것으로 dynamic programming 을 진행하였다.

그래서 , 이렇게 해서 , 최대값을 얻어낻긴 했지만,
경로 추적이 문제 였다.

그래서 질문 게시판에서 힌트를 얻었는데
그 힌트는 바로 , dp[0][i] , dp[1][i] 의 차이점을 이용하는 것이다.
내 알고리즘은 무조건 적으로 dp[0][i] 이라고 해서 무조건 선택하는 것은 아니였다.

하지만,  dp[0][i] , dp[1][i] 이 두개의 값들 중
만일 dp[0][i] == dp[1][i] 라면?
내가 이전에 전제조건으로 설정했던 것과 같이 dp[0][i] 이라고 하더라도
선택하지 않은 것이다.
그래서 이 값을 보고서 , dp[0][i] 가 더 크다면 ? 무조건 적으로 i를 선택한 것이고
만약에 값이 더 작거나 같다면 ? 선택하지 않은 것이다.
솔직히 값이 작은 경우는 모르겠다. 같은 경우만 존재할 것 같은 느낌이다.

그래서 결국은 해당 값을 선택하였을 때의 값과 , 선택하지 않았을 때의 값은
이 이전의 값에서 서로의 value 가 차이날 지언정 dp 배열의 컨셉 자체가 최대값을 취하는 것이기에
만일 dp[1][i] 와 같이 아얘 선택하지 못하는 경우와 , dp[0][i] 와 같이 자발적으로 선택하지 않을 수 있는 경우에서의
값이 같다면 dp[0][i] 는 자발적으로 i 정점을 선택하지 않은 값이라는 것을 알 수 있다.

그래서 계속해서 dp[0][i] , dp[1][i] 와 , 이전에 값을 선택하였는지 안선택하였는지를 판단하에
(왜냐하면 이전 값을 선택했는지 안했는지 판단을 하지 않는다면 , max 값을 취할 때 , 해당 정점을 선택한 정점은 모두 출력이됨)
그래서 이렇게 값을 쭉 모은다음에
sort 를 진행하여서 출력하면 답을 얻을 수 있다.

그리고 , 가장 핵심사항은 , dp[0][0] 은 차있는데 , 무조건 왜냐하면 , 선택하지 않는 것도 여기서 자발적으로 가능하니까
하지만 dp[1][0] 은 채울 수가 없다. 왜냐하면 dp[0][0] 은 선택하지 않는 경우와 , 선택하는 경우 , 즉 루트노드이기도 하고 두가지의 선택지가 다 가능하다보니까
dp[1][0] 을 사용할 이유가 없다 , 하지만 위의 전제조건이라면 dp[1][0] 을 이용해서 0 번 루트를 선택하였는지 안하였는지의 대한 정보가 필요하니까
해당 정점이 선택되지 않는다는 전제조건인 dfs(-1 , 1 , 1) 을 한번 더 호출함으로써 dp[1][0] 을 채울 수가 있다.

이런식으로 하여 정답을 받았음

한눈에 볼 수 있게 , dp[0][i] , dp[1][i] 을 비교하였을 때 , 서로의 차이를 보면 정확하게 , 선택을 한 정점이라면 dp[0][i] - dp[1][i] == vertex[i] 임
그러고 선택하지 않은 경우라면 동일하다 값이

140 130 60 20 20 70 70
130 130 20 20  0 70  0

생각을 해내면 어떻게 보면 되게 당연한 결과이다.
항상 최대값들만 취하는 dp 에서 , dp[0][i] 은 선택하거나 선택하지 않는것 , dp[1][i] 는 무조건 이 정점은 선택하지 않는 경우에서의 최대값이니
dp[0][i] == dp[1][i] 값이 같다라면 선택을 하지 않은 경우이고 , 다르다라면 해당 정점을 선택한 것이다.
그리고 c 값을 이용하여서 , 이 해당 지점이 최대 독립 집합에 포함될 수 있는 값인지에 대한 여부도 체크한다.

조금 더 자세하게 생각을 해보면
한 정점에서 넘길 수 있는 값은 0 혹은 1이다.
0 에서는 0 혹은 1을 넘길 수 있고
1 에서는 0을 넘길 수 있다.

그래서 0 -> 1 번째 정점에서
선택을 한 경우와
선택을 하지 않은 경우
선택을 못한 경우를 살펴보자.

일단 선택을 한 경우
이 경우는 이전 값이 0이 었다.
그래서 0에서는 선택을 해서 다음에 1로 넘길 수 있다.

선택을 하지 않은 경우
이 경우는 선택을 안해서 다음에 0으로 넘길 수 있다.

선택을 하지 못한 경우
이 경우는 선택을 하지 않은 경우와 넘어간 값은 같다.

그러면 이렇게 3가지의 경우를 나눴을 때 , 선택한 경우는 현재의 dp[0][i] 값은 달라질 수 있다.
그 대신 dp[1][i + 1] 값이 다음에 정해진다

선택을 하지 않은 경우 dp[0][i + 1] 로 넘길 수 있다.
그리고 선택을 그냥 하지 못하는 경우 이 경우는 선택한 경우와 같다.

즉 선택을 하면 dp[0][i] 의 값은 달라지지만
dp[0][i + 1] 의 값과 , dp[1][i + 1] 의 값에는 영향을 주지 못한다.
일단 무엇보다 dp[0][i + 1]은 무조건 선택이 가능한 경우에서 주어지므로 , 최대값을 취할 수밖에 없고
dp[1][i + 1] 도 이 값을 선택하지 않고서의 최대값을 취할 수 있다.
, 즉 그냥 dp[0][i + 1] == dp[1][i + 1] 인 경우는 이 정점을 선택하지 않은 것이고
dp[0][i + 1] > dp[1][i + 1] 인 경우는 이 정점을 선택한 것이다.

즉 진짜 한줄 결론은 dp[1][i] 는 해당 정점을 선택을 하지 못하는 경우가 있을 때의 최대값을 취하기 위함이니 ,
dp[0][i] 보다 무조건 작거나 같다 , 그렇기 떄문에 이러한 연산이 가능하다 , dp[1][i]는 dp[0][i] 보다 무조건 작거나 같을 수밖에 없다라는 것이 포인트이다.
(그냥 선택범위도 더 좁고 , 이미 이전에 선택되었을 경우를 대비해서 존재하는 인덱스라서)
 */
public class Main {
    // a = 부모 , b = 현재 , c = 이전에 선택하였는지
    public static int dfs(int a , int b , int c){
        /*
        종료 조건은 더 이상 탐색할 노드가 없을 때이다.
        그럴 때에는 어떤식으로 값을 반환해야 할까?
        선택하지 않는 경우와 , 선택하는 경우가 있다.
        하지만 return을 그렇게 할 수가 없다.
        return 은 한번하면 끝난다..

        만일 끝에 노드가 더 없다.
        즉 , 본인이 리프노드라는 것을 알면
        b를 -1로 넘기자
        그러면 그것이 종료조건이 되는 것이다.

        생각해보니까 , 비용을 저장할 것이 필요하다.
        Top - down 형식을 이용하여서,
        끝에서부터 연산을 진행한다고 생각해보자.

        일단은 dp 배열을 이용하여서 , 값을 저장해보자.

        음..
        어떻게 해야할까

        왜 내가 이것을 해결하지 못할까,
        3가지의 케이스이다. 선택하지 못하는 경우(이전에 선택을 해서)
        선택하는 경우 , 선택하지 않는 경우..
        이렇게 3가지 케이스로 나뉘게 되고,

        내가 생각했던 것은 약간 1차원 적인 것이고
        여기서는 2차원 적인 느낌이다.

        왜냐하면 본인의 하단에 노드가 여러개가 있을 수도 있기 때문이다.
        그렇기에 , 본인이 받은 값에다가 + vertex[b] 를 해서 하는 것이 아닌
        하단의 dp[b] 들의 합을 받아야 한다.

        즉 , 선택하는 경우든 , 선택하지 않는 경우든 , 본인의 값에다가 값을 더해야 한다.
        그리고서 비교를 진행해야 할 것 같다.
        선택하는 경우, 혹은 선택하지 않는 경우
        말로 표현하기 너무 어려우니까 한번 구현을 해보자.

        선택하지 않은 경우의 최대값과,
        선택한 경우의 최대 값을 나누면 어떨까
        즉 , 이전 값에서 선택을 하였을 때에는 다르게 반환을 해야한다라는 생각에서 비롯한 것이다.

        무엇인가 가닥이 갑자기 잡혔다.
        해당 지점에서의 선택하지 않았을 경우 해당 지점까지 온 경우에서의 최대값과
        그렇지 않은경우의 최대값을 노드에 저장하면 된다.
        */

        // 정점을 탐색을 왔는데 , 이미 c 값에 기반하여서 , 값이 정해져있다면? 바로 return 하면 된다.
        if(dp[c][b] != 0) return dp[c][b];

        boolean leap = true;

        int res1 = 0;
        int res2 = 0;

        for(Integer next : graph.get(b)){
            if(next !=a) {
                // 리프 노드인지 판단.
                leap = false;

                // 이전에 선택한 경우에서 , 즉 지금 현재 선택하지 못하는 경우에서의 선택지
                if (c == 1) dp[c][b] += dfs(b, next, 0);
                else {

                    // 해당 노드를 선택하는 경우
                    res1 += dfs(b , next , 1);

                    // 해당 노드를 선택하지 않는 경우
                    res2 += dfs(b , next , 0);
                }
            }
        }

        if(c == 0){
            dp[c][b] = Math.max(res1 + vertex[b] , res2);
        }

        if(leap){
            // 마지막 정점은 이러하다.
            if(c == 1) dp[c][b] = 0;
            else dp[c][b] = vertex[b];
        }

        return dp[c][b];
    }

    // a == 부모 , b == 현재 , c == 이전에것을 선택했냐 안했냐
    public static void pathTrace(int a , int b , int c){
        /*
        경로를 어떻게 어떻게 추적할 수 있을까?

        일단 해당 dp[c][b] 의 값은
        현재 값에서 선택하거나 선택하지 않았을 때의 , 최대값이다.
        즉 , 선택될 수도 안될 수도 있지만 , 해당 값을 가지고 있다라는 것이다.
        즉 , 이 값을 보고 알 수 있나?

        알았다...
        질문 게시판에 있는 힌트를 보고 알았는데
        dp[0][a] , dp[1][a] 을 보고
        더 큰 것을 선택하는 것이다
        만일 dp[1][a] 가 만일 더 크다면
        선택한 것이고
        그렇지 않다면 이것을 선택하지 않은 것이다.
        그리고 , 이전 지점을 선택했냐 안했냐에 따라서도 달라진다.
        즉 현재 값을 보고 선택했냐 안했냐를 따지고,
        만일 이전 값을 선택했다라면 , 그냥 지나간다.
         */

        boolean select = false;

        // 선택한 값이 더 높고 , 이전에 선택하지 않았을 때
        if(dp[0][b] != dp[1][b] && c == 0){
            result.add(b);
            select = true;
        }

        for(Integer next : graph.get(b)){
            if(next != a){
                if(select) pathTrace(b , next , 1);
                else pathTrace(b , next , 0);
            }
        }
    }

    public static int N;
    public static List<ArrayList<Integer>> graph = new ArrayList<>();
    public static int[] vertex , parent;
    public static int[][] dp;
    public static List<Integer> result = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());

        vertex = new int[N + 1];
        parent = new int[N + 1];
        dp = new int[2][N + 1];

        st = new StringTokenizer(input.readLine());

        graph.add(new ArrayList<>());
        for(int i = 1; i <= N; i++){
            vertex[i] = Integer.parseInt(st.nextToken());
            graph.add(new ArrayList<>());
            parent[i] = i; // 부모를 등록
        }

        for(int i = 0; i < N - 1; i++){
            st = new StringTokenizer(input.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        // 일단 실험
        dfs(-1 , 1 , 0);
        dfs(-1 , 1 , 1);

//        for(int i = 0; i < 2; i++) System.out.println(Arrays.toString(dp[i]));
        int max = dp[0][1];

        pathTrace(-1 , 1 , 0);

        Collections.sort(result);

        output.write(max + "\n");
        for(Integer vertex : result){
            output.write(vertex + " ");
        }

        output.flush();
        output.close();
    }
}