import java.util.*;
import java.io.*;

// 2228 : 구간 나누기

/*
-- 전제조건
N 개의 수로 이루어진 1차원 배열이 존재한다 , 이 배열에서 M 개의 구간을 선택해서 구간에 속한 수들의 총 합이 최대가 되도록 하려고한다.

각 구간들은 한 개 이상의 연속된 수들로 이루어져 있어야 하고
인접해서 골라서는 안된다.
그리고 정확히 M개의 구간이 있어야 한다.

N개의 수들이 주어졌을 때 , 답을 구하는 프로그램을 작성하시오.
-- 틀설계
N 은 100 개의 수로 이루어져 있고,
구간의 개수가 정해져있다.

이것도 그냥 Top-Down 으로 찾아낼 수 있을 것 같긴한데..
dp 로 지금까지 선택한 구간의 수가 있을 때
현재로서 더 선택해서 얻을 수 있는 구간의 수
이렇게 하면 되지 않을까?

일단 N과 구간의 개수 M을 받자.
그 다음에 dfs 로 해결해나갈 것인데
dp로는 이런식으로 구성할 것이다.
dp[인덱스][선택한 구간의 수] 로 진행을 하고
여기서 이제 뒤로 진행하면서 , 선택해 나가는 것이다.
근데 구간을 꼭 맞게 선택해야 하니까 조건으로 구간을 맞지 않게 선택했을 때에는 그냥 return 해 버린다.
 */
public class Main {
    public static int N , M , MIN = -100_000_000 , limit;
    public static int[][] dp;
    public static boolean[][] visited;
    public static int[] arr;
    public static int dfs(int idx , int select){
        // 일단 이전 구간에서 다 sum 값을 구하고서 다음으로 그냥 넘기는 것

        if(select == M) return 0;
        if(N <= idx) return MIN;

        // 이 3문장이 너무나도 이해가 가질 않음... 왜 이렇게 해야만 되지? 이전처럼 하면 안되는 것이지 왜?
        if(visited[select][idx]) return dp[select][idx];

        visited[select][idx] = true;
        dp[select][idx] = dfs(idx + 1 , select);

        int sum = 0;
        // 구간을 선택하지 않고 , 넘기는 경우도 존재해야 함
        for(int i = idx; i < N; i++){
            // 구간을 선택할 때의 더할 값을 계속 구해나감
            sum += arr[i];
            // 구간을 선택하지 않는 경우와 , 선택하는 경우로 나뉨
            dp[select][idx] = Math.max(dp[select][idx] , dfs(i + 2 , select + 1) + sum);
        }

        return dp[select][idx];
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        dp = new int[M][N];
        arr = new int[N];
        visited = new boolean[M][N];
        limit = N / M;

        for(int i = 0; i < 2; i++) Arrays.fill(dp[i] , MIN);

        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(input.readLine());
        }

        System.out.println(dfs(0 , 0));
    }
}

