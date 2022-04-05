import java.util.*;
import java.io.*;

// 2616 : 소형 기관차
/*
-- 전제 조건
기차가 앞으로 진행하고 있는데
만일 해당 기차가 멈출 경우를 고려하여서 , 소형 기관차 3대를 준비해놨다.
이 기차들은 각각 끌 수 있는 칸의 개수는 정해져있고,
만일 나눠서 끌고간다고 했을 때 , 무조건 한 기차는 연속적으로 끌고 가야 한다.

그래서 해당 기관차 3개와 , 칸에 있는 사람들의 수와 , 한 소형 기관차가 끌 수 있는 칸의 개수가 주어졌을 때
가장 많은 사람을 데리고 갈 수 있는 경우를 구하시오.
-- 틀 설계
누적합까지는 생각을 했었다.
그리고 무조건 그리디하게 , 많은 칸을 끌어야 된다고 생각한다 , 왜냐하면 칸에 있는 사람들의 수가 음수는 아니니까

그래서 부분합을 주어진 끌 수 있는 최대 칸으로 계산해보면
75 90 60 40 75 105

해당 누적합을 구하는 방법은 굉장히 쉽다.
일단 arr.length - size(소형 기관차가 끌 수 있는 개수) - 1 만큼의 크기의 배열을 선언해준다음에
일단 size 만큼 더해주면서 이제 하나씩 진행하면서 index 증가시키고 arr[index] 를 더해주고 arr[index - size] 를 빼주면 된다.
그러면 굉장히 쉽게 누적합을 구할 수 있고 이제 여기서 어떻게 선택을 하냐가 중요하다.
일단 , 지금 선택한 방법은
75 + 60 + 105 를 골랐다.
그렇기 때문에 240 명을 데리고 갈 수 있는데 어떻게 이렇게 선택했을까

일단 지금으로서 생각하는 방법은 최근에 많이 사용했던 top-down 이다.
dp[현재 인덱스][현재 선택한 기차 개수] 를 하면 이제 가장 많이 고를 수 있는 방법을 채택하는 것이다.

만일 없다면 그냥 진행한다.

만일 끝까지 도달하였을 때 , 3대를 다 고르지 않았다면 return 하지 않는다.

일단 내가 하려는 방법에서 가장 중요한 것은 앞으로 고를 수 있는 최대 개수를 고르는 것이다.
여기까지 이렇게 선택하고 왔다면 , 여기서 부터 얼마나 고를 수 있냐 이게 관건인데..

만일 dp[현재 인덱스][현재 선택한 기차 개수] 가 없다면
int res 를 하나 만들어서
뒤로 진행하는 것이다.

이제 당연하게도 선택을 하는 경우와 선택을 하지 않는 경우로 나뉘게 된다.
그러면 이제 선택하게 되면 무조건 적으로 index 를 size 만큼 뛰어야 한다.

이렇게 하면 될 것 같은데, 아니다...

나는 이런 느낌으로 가고 싶다. dp[index][select] 로 해서 처음에는 당연히 dp[0][0] 으로 넘어가게 되고
dp[0][0] 이 의미하는 것은 여기서부터 얻을 수 있는 최대 값을 의미한다.

일단 그럴려면 dp[index][3] 이라면 무조건 0을 반환해야 한다.
아무것도 더 선택할 수 있는 게 없으니까

나머지는 그냥 dp[index][select] += dfs(index + 1 , select) 이거나
dp[index][select] += dfs(index + SIZE , select + 1) 로 넘기면 된다.

-- 결론
코드를 작성하다가 , 컨셉에 대해서 완전하게 이해하게 되었는데 (되게 신기한 경우다 , 어느정도 생각을 정리하고 들어가긴 했지만 , 코드를 쓰다가 생각이 확립이 되었다.)
일단 무조건 많은 칸을 선택해야 한다는 사실은 결과론 적으로 보았을 때 , 어느정도 맞았다라고 할 수 있다.
근데 , 무조건 다 선택하는 경우를 고려하지는 않았다.
즉 dfs 를 진행하면서 무조건 3개의 기차를 다 할당시키려고 하지는 않았다.
dp = new int[M][3]; 으로 진행하면서 , 누적합으로 구한 배열만 다 돌도록 하였다.

일단 전제 자체는 이러하다 , dp[index][select] 값이 의미하는 것은 현재 이렇게 선택하였을 때 , 앞으로 더 선택해서 끌고 갈 수 있는 고객들의 최대값을 의미한다.
그리고 , 해당칸을 선택하면 적어도 K - 1 개의 뒤에칸들은 선택하지 못한다라는 전제를 넣었다.
이것들은 누적합을 나타냈기 때문이다.
그렇기 때문에 선택한 경우는 K 만큼 점프를 뛰어서 선택을 하게 해주었다.

그래서 이러한 전제를 가지고 dp[index][select]가 정의되어 있다면 (이미 해당 칸에서 추가로 구할 수 있는 최대 고객수를 알고 있는 것이다.)
바로 그냥 이전에 본인을 호출한 애한테 나 이만큼 다 선택할 수 있어요 라고 반환을 하면 된다.

이렇게 되면 , dp[index][select] 가 의미하는 앞으로 선택할 수 있는 최대값을 의미하게 되는 것이다.
근데 완전 본인을 선택하지 않고 뒤에 칸들만 추가적으로 이용해서 dp[index][select] 를 구하려면
dfs(index + 1 , select) 이렇게 뒤로 보내야 한다.
그리고 만일 본인을 선택하고 , 뒤에 칸들을 추가적으로 이용해서 dp[index][select] 를 구하려면
dfs(index + K , select + 1) + sum[index] 로 구할 수 있다.
즉 한 지점에서(index) 두개의 방향, 즉 분기점이 존재하고 , 선택 중에 큰 값을 취하면 된다.(가장 많은 고객을 구해야 하기 때문에)

그래서 dp[index][select] = Math.max(dfs(index + 1 , select) , dfs(index + K , select + 1) + sum[index]) 라는 결론이 나오게 되는 것이다.
그래서 이렇게 구한 현재 본인의 dp[index][select]를 본인을 호출한 넘한테 반환하면 된다.

그렇게 되면 dp[0][0] 은 결국 정답을 아무것도 선택하지 않은 상태의 0번째 인덱스에서의 최대값을 구한다 , 즉 답을 구하게 되는 것이다.

이것이 가능한 이유는 일단 dfs 를 호출하게 되면서 엄청나게 많은 분기를 하게 된다. 그것은 뒤로 선택할 수 있는 모든 경우의 수이다.
그래서 본인의 뒤에서 시도하는 모든 경우의 수에서 최대값이니 , 즉 현 상황에서 취득할 수 있는 최대값이라는 결론이 나오게 된다.
 */
public class Main {
    public static int N , M , K;
    public static int[] arr , sum;
    public static int[][] dp;
    public static int dfs(int index , int select){
        /*
        만일 index > M 이라면 일단 종료 시켜야 한다.
        select 가 남았더라도 , 선택을 못하니까 , select == 3 이여도 똑같음

        그리고 만일 현재 dp[index][select] 즉 , 현재 index 에서 현재 고른 기차수에서
        앞으로 더 구할 수 있는 최대값이 확립이 되어 있다면? 그것을 반환하면 된다.

        그리고 이제 고르는 구문인데
        현재를 선택하지 않는 경우가 더 크거나 , 아니면 선택하는 경우가 더 크냐를 보면 된다.
         */

        if(index >= M || select == 3) return 0;

        // 이미 dp[index][select] 즉 지금 어떻게 선택했냐에 따라서 앞으로 선택할 수 있는 최대 값을 안다면 이 값을 바로 반환한다.
        if(dp[index][select] != 0) return dp[index][select];

        // 선택하는 경우와 , 선택하지 않는 경우 중 , 어떤 것이 앞으로 더 많은 것을 선택할 수 있는지 확인이 가능하다.
        // 맨 마지막의 경우도 정리 할 수 있다 , 여기까지 온 것은 select 가 남아있는 것이니까 , 현재 마지막 것을 select 하면서 현재로서 더 고를 수 있는 최대 값을 반환하는 것이다.
        dp[index][select] = Math.max(dfs(index + 1 , select) , dfs(index + K , select + 1) + sum[index]);

        // 현재 index 에서 select 개수 만큼 선택하였을 때 , 앞으로 고를 수 있는 최대 값을 반환한다.
        return dp[index][select];
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        M = N - K + 1;

        st = new StringTokenizer(input.readLine());
        arr = new int[N];
        dp = new int[M][3];

        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        K = Integer.parseInt(input.readLine());
        sum = new int[M];

        int value = 0;
        for(int i = 0; i < K; i++){
            value += arr[i];
        }

        sum[0] = value;
        for(int i = K , j = 1; i < N; i++ , j++){
            value = value + arr[i] - arr[i - K];
            sum[j] = value;
        }

        System.out.println(dfs(0 , 0));
    }
}