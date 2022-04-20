import java.util.*;
import java.io.*;

// 1949 : 우수 마을

/*
-- 전제조건
트리가 주어지고,
우수마을로 선정된 마을들의 주민수의 총합을 최대로 해야 하는데,
그 경우 해당 조건을 만족해야 한다.

1. '우수 마을'로 선정된 마을 주민 수의 총 합을 최대로 해야 한다.
2. 마을 사이의 충돌을 방지하기 위해서, 만일 두 마을이 인접해 있으면 두 마을을 모두 '우수 마을'로 선정할 수는 없다. 즉 '우수 마을'끼리는 서로 인접해 있을 수 없다.
3. 선정되지 못한 마을에 경각심을 불러일으키기 위해서, '우수 마을'로 선정되지 못한 마을은 적어도 하나의 '우수 마을'과는 인접해 있어야 한다.

이러한 조건들을 만족해야 한다.
이러할 떄 , 우수마을의 최대합은?
-- 틀설계
이전에 했던 문제들과 일맥상통한다.
즉 케이스가 이런식으로 나뉘게 된다.

1. 이전 노드가 선택된 경우 -> 이 경우는 현재 노드가 선택될 수 없다.
2. 이전 노드의 이전 노드가 선택이 되고 , 이전 노드는 선택이 되지 않은 경우 -> 현재 노드가 선택될 수도 안될 수도 있다.
3. 이전 노드의 이전 노드가 선택이 되지 않고 , 이전 노드도 선택이 되지 않은 경우 -> 현재 노드를 무조건적으로 선택해야 한다.

이런식으로 3가지의 케이스를 나눌 수 있을 것 같고
현재 노드를 선택할 수 없는 경우 , 그래서 dp 도 3가지의 방향을 두고 dp[0 ~ 2][i] 이런식으로
그 다음에 위의 케이스에 따라서 케이스 번호 - 1 로 넘기면 될 것 같다.

말을 하다 보니까 생각이 정리 되었다.
3가지 케이스로 나누어서 dp를 선언해야 하는 이유도
예를 들어서 현재 노드가 선택이 무조건 되어야 하는 경우 , 혹은 선택될 수 없는 경우로 넘어오는 경우
2번째 케이스로 실행되면 안되기 때문이다.

-- 해맸던 점
오히려 내가 정한 3번째 케이스를 고려하였을 때는
답이 틀리게 나온다..

아 알겠다.

애초에 내가 3번째 케이스를 고려하지 않아도 되는 이유는
그렇게 계산이 되겠지 , 되는데
그 값은 선택이 되지를 않는다 , 즉 max 값이 아니라는 말이다.

이거는 그냥 이전에 풀었던 최대독립집합과 같은 맥락의 문제가 되는데 ,
3번 째 케이스를 고려하지 않아도 되는 이유는 , 짜피 Top - Down 방식으로 푸는데
분명 3번째 케이스에 위배되는 경우? 분명히 존재한다.

하지만 , 그 경우는 Math.max 로 인해서 , 걸러진다라는 것이다.
그렇게 해서 진행이 되면(위의 3번째 케이스를 고려하지 않으면)
예를 들어서 이런식으로 진행이 될 수도 있다.
O - X - X - X - O

이런 식으로 진행이 되고 ,
그러면 이거는 3번째 케이스에 위배된다.

하지만 , 이거는 선택되지 않는다.
왜냐? 최대값이 아니니까
이렇게도 경우가 진행되지만 이런 경우도 분명히 존재할 것이다.

O - X - O - X - O

or

O - X - X - O - X

이런식으로 진행될것이다.
그러면 당연하게 어느것이 선택이 될 거이냐?
만일 4번째 값이 완전 크다면 지금까지 진행한 사례중 3번째
O - X - X - O - X

그게 아니라면
O - X - X - X - O
O - X - O - X - O
중 당연하게도 다 똑같이 고르고 하나를 더 고른 아래 케이스가 골라진다
즉 이말은 무엇이냐

모든 경우 다 실행되도 상관없다라는 것이다
하지만 , 다 실행되어도 결국 선택되는 것은
주변에 인접한 우수마을이 무조건 적으로 존재하는 것
즉 , 선택을 안하는 노드가 3개 이상이 연속되지 않는 경우가 최대 값이 될 수밖에 없는 것이다
위와 같은 상황에서도 볼 수 있 듯 , 3개를 선택하지 않고서 고르는 경우와
그냥 하나 더 선택하는 경우 , 당연히 이득은 하나 더 선택하는 경우고 , 이 말은 즉 어떠한 노드를 고르기 위해서 3개 이상을 뛰어넘을 필요가 없다라는 것이다.

그렇기 때문에 , 3가지 케이스를 다 고려하면 오히려 실행이 되지 않는 , 혹은 방해가 되는 부분이 분명히 존재할 것이다 , 그러니까 틀렸겠지

그래서 이런 점들을 잘 고려해서 다음 문제도 한번 잘 풀어보자.

그래서 오히려 3가지 조건을 다 고려하면 틀린다라는 것을 증명하기 위한 , 사례를 한번 봐보자

3가지 케이스일 때
[0, 0, 13000, 2000, 2000, 0, 7000, 0]
[0, 14000, 0, 6000, 2000, 2000, 7000, 7000]
[0, 14000, 12000, 0, 1000, 2000, 0, 7000]

2가지 케이스일 때
[0, 13000, 13000, 2000, 2000, 0, 7000, 0]
[0, 14000, 13000, 6000, 2000, 2000, 7000, 7000]
[0, 0, 0, 0, 0, 0, 0, 0]

내가 봤을 때는 오히려 dp[0 ~ 2] 라서 값을 못찾아서 더 그런 거 아닐 까
dp[0 ~ 1]로 줄이고 한번 더 가보자.

이건 그냥 오히려 조건 추가하면 맞을 수가 없다...
그냥 이렇게 결론내자.
 */
public class Main {
    public static int[][] dp;
    public static int[] vertex;
    public static List<ArrayList<Integer>> graph = new ArrayList<>();
    public static int N;

    // p == 부모 노드 , c == 현재 노드 , status == 현재 case
//    public static int dfs(int p , int c , int status){
//        /*
//        status 0 == 이전 노드가 선택되어 선택될 수 없는 경우
//        status 1 == 이전 노드가 선택되지 않았지만 , 그 이전 노드가 선택되어서 현재 선택하지 않아도 되는 경우
//        status 2 == 이전 노드가 선택되지 않고 , 그 이전 노드도 선택되지 않아서 현재 선택해야 하는 경우
//
//        그냥 케이스만 3개로 나누고
//        dp[0 ~ 1]로만 해보자.
//        선택하는 경우와 , 선택하지 않는 경우는 그냥 하나의 dp[0 ~ 1] 로만 , 근데 이렇게 로직 구성하면 과조건 지대로임
//         */
//
//        // 일단 어떠한 정점이든 선택하는 것이 무조건 이득이니까 , 0이면 방문안한 것임
//       if(dp[status == 2 ? 1 : status][c] != 0) return dp[status == 2 ? 1 : status][c];
//
//       boolean leap = true;
//
//       // 선택될 수도 있고 , 안될 수도 있는 경우에서의 합을 저장할 변수
//       int res1 = 0;
//       int res2 = 0;
//
//       for(Integer next : graph.get(c)){
//
//           if(next != p){
//
//               // 방문할 노드가 하나라도 있는 순간 leap 노드는 아니다.
//               leap = false;
//
//               if(status == 0){
//                   // 지금 나는 누군가와 연결되어 있기 때문에 선택을 하지 못한 것이니 , status 는 1로 넘길 수 있음
//                   dp[status][c] += dfs(c , next , 1);
//               }
//
//               else if(status == 1){
//
//                   // 이제 선택하는 경우와 , 선택하지 않는 경우가 존재함
//
//                   // 선택하는 경우
//                   res1 += dfs(c , next , 0);
//
//                   // 현재 노드까지 선택이 되지 않아서 다음 노드는 무조건 선택해야 하는 경우
//                   res2 += dfs(c , next , 2);
//               }
//
//               else{
//
//                   // 무조건 선택해야 하는 경우
//                   dp[1][c] += dfs(c , next , 0);
//               }
//           }
//       }
//
//
//       // leap 노드에 도달하였을 때
//       if(leap){
//
//           // 현재 노드가 선택될 수 없는 경우
//           if(status == 0)
//               dp[status][c] = 0;
//
//           // 현재 노드가 선택될 수도 안될 수도 있는 경우 , 근데 리프노드의 경우는 추후에 노드가 더 없기 때문에 이전의 노드가 선택이 되지 않았을 때 , 선택하는 것이 무조건적으로 좋음
//           else
//               dp[1][c] = vertex[c];
//
//       }
//
//       // leap 노드가 아닐 때
//       else{
//
//           if(status == 1){
//               // 선택하는 경우와 , 선택하지 않는 경우중 큰 것을 취한다.
//               dp[status][c] = Math.max(res1 + vertex[c] , res2);
//           }
//
//           // 이번 노드를 무조건 적으로 선택하였기 때문에 , vertex[c] 를 더 해줌
//           else if(status == 2)
//               dp[1][c] += vertex[c];
//
//       }
//
//       return dp[status == 2 ? 1 : status][c];
//
//    }

    public static int dfs(int p , int c , int status){
        /*
        status 0 == 이전 노드가 선택되어 선택될 수 없는 경우
        status 1 == 이전 노드가 선택되지 않았지만 , 그 이전 노드가 선택되어서 현재 선택하지 않아도 되는 경우

         */

        // 일단 어떠한 정점이든 선택하는 것이 무조건 이득이니까 , 0이면 방문안한 것임
        if(dp[status][c] != 0) return dp[status][c];

        boolean leap = true;

        // 선택될 수도 있고 , 안될 수도 있는 경우에서의 합을 저장할 변수
        int res1 = 0;
        int res2 = 0;

        for(Integer next : graph.get(c)){

            if(next != p){

                // 방문할 노드가 하나라도 있는 순간 leap 노드는 아니다.
                leap = false;

                // 선택할 수 없는 경우
                if(status == 0){
                    dp[status][c] += dfs(c , next , 1);
                }

                // 선택할 수도 하지 않을수도 있는 경우
                else{

                    // 선택을 하는 경우
                    res1 += dfs(c , next , 0);

                    // 선택을 하지 않는 경우
                    res2 += dfs(c , next , 1);
                }
            }
        }


        // leap 노드에 도달하였을 때
        if(leap){

            // 현재 노드가 선택될 수 없는 경우
            if(status == 0)
                dp[status][c] = 0;

            // 현재 노드가 무조건 적으로 선택되어야 하는 경우
            else
                dp[status][c] = vertex[c];

        }

        // leap 노드가 아닐 때
        else{
            if(status == 1){
                // 선택을 하는 경우와 , 선택하지 않는 경우 , 두 경우를 따로 값을 저장하여 고려함
                dp[status][c] = Math.max(res1 + vertex[c] , res2);
            }
        }

        return dp[status][c];
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());

        dp = new int[2][N + 1];
        vertex = new int[N + 1];

        st = new StringTokenizer(input.readLine());
        graph.add(new ArrayList<>());

        for(int i = 1; i <= N; i++){
            graph.add(new ArrayList<>());
            vertex[i] = Integer.parseInt(st.nextToken());
        } // vertex 정보 받음

        // 간선 정보들 받음
        for(int i = 0; i < N - 1; i++){
            st = new StringTokenizer(input.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        // 처음의 케이스는 선택될 수도 있고 , 안될 수도 있는 2번 케이스이기에 status == 1 로 넘긴다.
        System.out.println(Math.max(dfs(-1 , 1 , 1 ) , dfs(-1 , 1 , 0)));

//        for(int i = 0; i < dp.length; i++) System.out.println(Arrays.toString(dp[i]));
    }
}
