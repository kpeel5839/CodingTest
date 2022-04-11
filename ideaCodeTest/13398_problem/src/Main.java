import java.util.*;
import java.io.*;

// 13398 : 연속합 2

/*
-- 전제조건
수열이 하나가 주어진다고 했을 때 , 거기서 연속된 값을 선택해서
제일 큰 결과값을 도출해내라 , 수를 하나 제거해도 되고 , 안해도 된다 , 쨌든 제일 큰 수를 뽑아내기만 하면 된다.

-- 틀설계
이 문제도 그냥 Top-Down 으로 쉽게 풀 수 있을 것 같다.
일단 첫번째를 선택하고 , dp[n] 을 선언하고
여기서 가장 n 번째에서 얻을 수 있는 가장 큰 값은 몇이다라고 하는 것이다.
그러면 일단 제일 끝까지 도달하여야 한다.
이미 수를 제거한 경우와 , 제거하지 않은 경우로 나누자.
그리고 수를 제거한 경우는 수를 제거할 수 없고
수를 제거하지 않은 경우는 수를 제거할 수 있도록 하자.
dfs 로 쉽게 해결해보자.

나 완전 이상하게 짰었네...
완전 틀린 해결방법이였구나

내가 한 방법은 무조건 끝까지 진행한다.
그러니까 수를 제거하나 , 안하나 , 그것을 진행하고 무조건 시작점에서
끝까지 진행하게 된다. 그것이 문제였다.

그냥 dp로 진행하게 되었음
dp[i][0] 은 삭제를 진행하지 않은 최대 값이다.
dp[i][1] 은 삭제를 진행한 최대값이다.
그래서
dp[i][0] = Math.max(dp[i - 1][0] + arr[i] , arr[i]) 값으로 정의가 가능하다
본인은 무조건적으로 포함시키고 , 뒤에 애들을 선택하였을 때와
그리고 , 현재 본인만 선택했을 때의 최대값을 고르는 것이다.
제거를 하거나 하지 않은 최대 값은 이러하다.
제거를 하게 되면 , dp[i - 1][0] 이고
제거를 하지 않게 되면 dp[i - 1][1] + arr[i] 이다.
dp[i][1] = Math.max(dp[i - 1][0] , dp[i - 1][1] + arr[i])

여기서 구해지는 dp 중에 최대값을 구하면 된다.
순차적으로 진행하기 때문에 , 구하는 중에 답을 구할 수 있따.

-- 결론
이 문제를 처음에 잘 못 접근했던 것이 일단 끝까지 더하는 것이 아닌 것이라는 것을 알기 전에도
dp 를 구성할 때 , 삭제를 이미 한 상태 , 안한 상태를 고려하지 못했었고,

그래서 결국 bottom-up 형식의 dp 설계 방식을 보았음
그래서 이 문제는 , 누적합을 구할 때 , 해당 자리까지 더한 것에 본인을 더한것이 크냐 아니면 작냐라는 기준을 두었고,
그리고 , 이제 절대로 삭제하지 않음을 보장하는 결과 처리와 , 삭제할 수도 있고 , 안할 수도 있음을 보장하는 배열을
삭제하는 경우는 삭제하지 않음을 보장하는 결과의 값을 가져오고(최대값) , 아닌 경우에는
삭제하는 경우도 있는 최댓값을 가져오고 본인의 arr[i] 를 더하는 식으로 진행을 하였다.

문제 풀이 방식은 이러하고 , 내가 해맸던 점은 , 크기가 1일 때 , 답이 이상하게 출력이 나오는 것을 신경쓰지 못하였었다.
 */
public class Main {
    public static int[][] dp;
    public static int[] arr;
    public static int N , res = Integer.MIN_VALUE;
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        dp = new int[2][N];
        arr = new int[N];

        st = new StringTokenizer(input.readLine());
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        res = arr[0];
        dp[0][0] = arr[0];
        dp[1][0] = arr[0]; // 무조건 하나는 선택해야 하니까 arr를 집어넣는다.

        // 아얘 선택하지 않는 경우를 방지하기 위해서 N - 1 까지만 진행
        for(int i = 1; i < N; i++){
            // 이전 것 중에 최대 값을 고르고 본인을 더하던가 , 이전게 너무 안좋으면(음수이면) 본인만 선택
            dp[0][i] = Math.max(dp[0][i - 1] + arr[i] , arr[i]);
            // 삭제를 진행할 수 있는 dp[1][i] 인데 , 여기서는 삭제를 하는 경우와 , 안하는 경우로 나뉜다.
            dp[1][i] = Math.max(dp[0][i - 1] , dp[1][i - 1] + arr[i]);
            res = Math.max(res , Math.max(dp[0][i] , dp[1][i]));
        }

//        for(int i = 0; i < 2; i++) System.out.println(Arrays.toString(dp[i]));
        System.out.println(res);
    }
}