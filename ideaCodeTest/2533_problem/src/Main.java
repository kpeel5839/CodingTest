import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

// 2533 : 사회망 서비스 SNS

/*
-- 전제조건
얼리어답터는 , 주변에 아이디어가 전파되기 이전에 이미 그 정보를 알고 있어서 주변에다가 아이디어를 전파하는 사람을 의미한다.
그래서 트리가 주어질 때 , 얼리어답터의 최소의 수를 구하여

-- 틀설계
트리의 정점의 개수는 최대 100만개가 주어진다.
일단 트리를 준 이유는 분명하게 , 사이클이 존재하는 경우 , 혹은 해당 정점까지 경로가 여러개인 경우를 배제해야 하는 경우의 문제라는 것을 의미한다.
그래서 이 문제를 어떻게 dynamic 하게 빠르게 풀 수 있을까라는 고민을 해보자.

일단 처음에 해본 생각은 이전 노드가 선택되지 않았다라면 , 선택을 하는 방법을 선택하였다.
하지만 , 이것은 아니라는 생각을 하게 되었음.

왜냐하면 O - X - X - O 이런식의 트리가 있다고 했을 때 , 연속으로 X 가 두개이지만 양쪽에 O 가 존재한다는 사실이 있음
그러면 여기서 고려해야 할 사항은 , 이전 노드가 연결이 되어있냐라는 것이고 , 이것을 어떻게 고려할 수 있을까..

그럼 본인의 이후 상황만을 고려하는 것은 어떨까?
이것도 dp[2][N] 으로 만들고,
0 은 본인이 이미 다른 노드와 연결이 되어있다라는 가정하에 진행
1 은 본인이 다른 노드와 연결이 되어있지 않다라는 가정하에 진행

이런식으로 dp를 구성해보면 어떨까?
즉 위의 경우는 이런식으로 표현이 가능하다.
이전 노드가 얼리어답터냐 , 아니냐로 표현이 가능하다.
 */
public class Main {
    public static int[][] dp;
    public static List<ArrayList<Integer>> graph = new ArrayList<>();
    public static int N;

    public static int dfs(int p , int c , int pre){
        /*
        아얘 이러한 가정에서 시작하자.
        일단 당연하게 p로 다시 못가고
        이전에 선택되지 않았으면 무조건 적으로 선택되어야 한다고 가정해보자.
         */

        if(dp[pre][c] != 0) return dp[pre][c];

        // leap node 에 대한 처리를 안해주었음
        boolean leap = true;

        int res1 = 0;
        int res2 = 0;

        for(Integer next : graph.get(c)){
            if(next != p){ // 가려는 노드가 부모가 아니라면
                leap = false;
                // 가는데 , 여기서 이전 노드가 얼리어답터가 아닌 경우였더라면 , 본인이 무조건 선택되어야 하고
                // 만일 얼리어답터였다면 , 선택해도 되고 , 선택하지 않아도 된다.
                if(pre == 0){ // 이전이 얼리어답터 o
                    res1 += dfs(c , next , 0); // 현재 것을 선택하는 경우
                    res2 += dfs(c , next , 1); // 현재 것을 선택하지 않는 경우
                }
               else{ // 이전이 얼리어답터 x
                    dp[pre][c] += dfs(c , next , 0);
                }
            }
        }

        if(!leap) {
            if (pre == 1) dp[pre][c] += 1;
            else {
                dp[pre][c] = Math.min(res1 + 1, res2);
            }
        }

        else{
            if(pre == 0) dp[pre][c] = 0;
            else dp[pre][c] = 1;
        }

        return dp[pre][c];
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());

        dp = new int[2][N + 1];

        for(int i = 0; i <= N; i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < N - 1; i++){
            st = new StringTokenizer(input.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        System.out.println(dfs(-1 , 1 , 0));
//        for(int i = 0; i < 2; i++) System.out.println(Arrays.toString(dp[i]));
    }
}
