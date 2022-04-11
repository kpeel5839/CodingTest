import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main2 {
    // 각 remove 를 하고 가는 경우와 , remove 를 하지 않는 경우를 저장하기 위한 dp
    public static int[][] dp;
    public static int[] arr;
    public static int N , res = Integer.MIN_VALUE;
    public static final int MIN = -100_000_000;
    public static int dfs(int idx , int rm , int end){
        /*
        여기서는 idx == N 에 도달하면 끝내고
        그리고 , 만일 이미 dp[rm][idx] 가 존재한다면 반환하고
        아니면 그냥 진행하는데
        진행할때에도 3가지 방향으로 분기하면 된다.
        일단 선택안하고 삭제하는 경우
        그리고 선택하는 경우
        그리고 선택하지 않고 끝내는 경우로 존재한다.
         */

        if(idx == N) return 0;

        // 끝낸다는 조건으로 이전에 호출을 다시한 경우 return 0 를 하여 값을 반환한다.
        if(end == 1) return 0;

        if(dp[rm][idx] != MIN) return dp[rm][idx];

        // 선택하는 경우
        dp[rm][idx] = Math.max(dp[rm][idx] , dfs(idx + 1 , rm , 0) + arr[idx]);

        // 지금 여기서 끝내는 경우 (선택하고 끝내는 경우만 진행한다 , 아얘 선택하지 않는 경우는 없어야 하기 때문에)
        dp[rm][idx] = Math.max(dp[rm][idx] , dfs(idx + 1 , rm , 1) + arr[idx]);

        // 선택하지 않고 끝냄
//        if(rm == 0) dp[rm][idx] = Math.max(dp[rm][idx] , dfs(idx + 1 , 1 , 1));

        // 삭제하고 다음으로 넘어가는 경우
        if(rm == 0) dp[rm][idx] = Math.max(dp[rm][idx] , dfs(idx + 1 , 1 , 0));

        // 선택하지 않는 경우
        return dp[rm][idx];
    }
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        dp = new int[2][N];
        arr = new int[N];

        for(int i = 0; i < 2; i++) Arrays.fill(dp[i] , MIN);
        st = new StringTokenizer(input.readLine());
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for(int i = 0; i < N; i++){
            // idx 와 , remove 를 했는지를 넘기는 상태
            res = Math.max(res , dfs(i , 0 , 0));
        }

        System.out.println(res);
    }
}