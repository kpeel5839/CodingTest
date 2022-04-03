import java.util.*;
import java.io.*;

// 5557 : 1학년
/*
-- 전제 조건
숫자가 순서대로 주어졌을 때 , '+' , '-' 를 써서
20을 넘기지 않고 , 음수를 만드지 않는 식으로 식을 구성하는 경우가 몇개의 경우의 수인지 구하여라
-- 틀 설계
dp로 long 선언하면 되고,
순서대로 숫자를 탐색하는데
뒤에 숫자와 더하거나 빼면서 진행하게 된다.
그리고 dp = new int[20][N] 으로 선언해서
어떤 수에서 다음 수로 넘어갈 때 , 연산과 동시에 index 를 증가시켜서 방문 처리를 해준다.

일단 , 그래서 dfs 와 방문처리를 적절히 섞어서,
총 횟수를 구하면 될 것 같다.

-- 해맸던 점
문제를 잘 못 읽어서 , 지금까지의 연산 결과가 맨 마지막 숫자와 같아야 한다라는 점을 간과 하였고,
sum + arr[index + 1] or sum - arr[index + 1] 을 진행하면서
sum 값을 처음에 arr[0] 으로 주지 않아서 살짝 해맸다.
그리고 arr[index + 1] 값이 음수도 있다는 점을 간과하고 , + 하면 무조건 20보다 큰 경우만 고려 , - 하면 무조건 0 보다 미만이 되는 경우를 고려한게 해맨 이유였다.
 */
public class Main {
    public static long[][] dp;
    public static int[] arr;
    public static int N;
    public static long dfs(int index , int sum){
        /*
        일단 조건이 20을 초과하면 안되고,
        0 미만이면 안된다 , 즉 더했을 떄 , sum 값이 해당 값으로 되면 continue 를 해야 한다.

        index == N 이면 return 1 을 해준다 , 방법 하나 추가됐다라는 의미로

        그리고 이제 다음 수로 넘어갈 떄에는 현재 index 와 index + 1 을 더하거나 빼는 경우로 가는데 이 경우 20을 넘냐 안넘냐를 보고
        진행하게 된다 , (넘으면 안넘기고 , 음수가 되어도 안넘긴다)

        이런식으로 계속 해당하는 dp[sum][index] 에서 경우의 수를 구하고 return 한다
        왜냐하면 dp[sum][index] 는 이전에 본인을 호출한 놈에게 넘겨야 한다.(이런식으로 넘겼을 때 이 만큼의 가짓수가 존재한다라고)

        그래서 결론은 index == N 이면 return 1
        그리고 뒤로 넘길때 , 0 ~ 20 의 범위 고려하면서 쌓이는 방법의 가짓수를 dp[sum][index] 에다가 저장
        그리고 다 구하면 dp[sum][index] (지금까지의 합이 sum 이고 , index 번째 연산을 실행해야 할 때의 가짓수)

        그리고 중요한 것은 만일 dp[sum][index] 가 이미 존재한다면? return 을 해주면 된다.

        문제를 잘 못 알았었다.
        등호까지 마무리 해서 맞아야 한다.
        즉 맨 마지막 숫자가 맞아야 한다.
         */

        // 탐색하는 수가 , 맨 마지막 수에 도달하면 끝내준다 , 더 연산할 숫자가 없으니까

//        System.out.println(sum);

        if(index == N - 2){
            if(arr[N - 1] == sum) return 1;
            else return 0;
        }

        if(dp[sum][index] != 0) return dp[sum][index]; // 한번이라도 성공한 적이 있으면 dp[sum][index] 가 바뀌었을테니까 return

        if(0 <= sum + arr[index + 1] && sum + arr[index + 1] <= 20) dp[sum][index] += dfs(index + 1 , sum + arr[index + 1]);

        if(0 <= sum - arr[index + 1] && sum - arr[index + 1] <= 20) dp[sum][index] += dfs(index + 1 , sum - arr[index + 1]);

        return dp[sum][index];
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        dp = new long[21][N];
        arr = new int[N];

        st = new StringTokenizer(input.readLine());

        // arr[i] 에다가 순서대로 집어넣음
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(dfs(0, arr[0]));
    }
}
