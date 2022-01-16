import java.util.*;
import java.io.*;

// 11049 : 행렬 곱셈 순서
/*
-- 전제 조건
행렬들이 주어진다.
행렬을 인접한 애들끼리 곱하는데 어떠한 순서대로 곱해야지 가장 곱셈 연산의 수가 적은지 구하라
구한 뒤에 그 횟수를 출력하면 됨
-- 틀 설계
일단 입력을 받고
matrix 에다가 각각의 행렬의 정보를 저장한다.
그리고 dp 에다가 최소비용들을 저장할 것인데
dp[r][c] 라고 했을 때 r은 start , c는 end라고 생각하면 된다
ABC 이렇게 있고 dp[1][2] 는 행렬 곱 BC를 하였을 때 최소 곱셈 수라고 생각하면 되낟.
그러니까 r - c 가 이 행렬 곱의 길이라고 할 수 있음
그래서 1 부터 최종적으로 n - 1까지 가야한다. 왜냐하면 n = 3 , ABC 0 1 2 라고 하면 dp[0][2]가 최종 값이 되어야 하는 것이다 그럼으로 최종 값일 때 start , end 의 차이가 n - 1 인 것을 보아
start , end 가 n - 1까지 차이가 날 떄까지 연산을 진행해주면 되는 것이다.
그래서 그러고서 그 안에서 start <= i <= end , dp[start][end] = dp[start][i] + dp[i + 1][end] 와 이 두개의 행렬을 곱할 때의 생기는 값을 더해서 현재 dp[start][end] 와 더 작은 값을 선택해서 집어넣으면 된다.
-- 해맸던 점
calDp 함수 호출하는 반복문에서 또 j++로 해야하는데 i++로 해서 잘못되었었음
 */
public class Main {
    public static int n;
    public static int[][] matrix , dp;
    public static void calDp(int start , int end){
        /*
        start 와 end 를 받아서 , start 와 end 사이에 [start][start] + [start + 1][end] , [start][start + 1] + [start + 2][end] , 이런식으로 start + n == end 가 될때까지
        계속 값을 구해주면서 dp의 값을 변경하는 함수
        dp[start][start + n] + dp[start + n  + 1][end] + matrix[start][0] * matrix[start + n + 1][0] * matrix[end][1] 이런식으로 구하면 된다.
         */
        for(int i = start; i < end; i++){
            int value = dp[start][i] + dp[i + 1][end] + matrix[start][0] * matrix[i + 1][0] * matrix[end][1];
            if(value < dp[start][end]){
                dp[start][end] = value;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());

        matrix = new int[n][2];
        dp = new int[n][n];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            matrix[i][0] = Integer.parseInt(st.nextToken());
            matrix[i][1] = Integer.parseInt(st.nextToken());
        }

        for(int i = 0; i < n; i++){
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            dp[i][i] = 0;
        }

        for(int i = 1; i < n; i++){
            for(int j = 0; j + i < n; j++){
                calDp(j , j + i);
            }
        }

//        for(int i = 0 ; i < n; i++){
//            for(int j = 0; j < n; j++){
//                System.out.print(dp[i][j] + " ");
//            }
//            System.out.println();
//        }

        System.out.println(dp[0][n - 1]);
    }
}
