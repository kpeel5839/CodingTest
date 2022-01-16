import java.util.*;
import java.io.*;

// 11049 : 행렬 곱셈 순서
/*
-- 전제 조건
행렬들이 주어진다.
행렬을 인접한 애들끼리 곱하는데 어떠한 순서대로 곱해야지 가장 곱셈 연산의 수가 적은지 구하라
구한 뒤에 그 횟수를 출력하면 됨
-- 틀 설계
일단 n * n 인 크기의 배열을 선언한다.
그러고서 y 축의 인덱스는 시작 행렬 , x 축의 인덱스는 끝 행렬이라고 한다.
예를 들어서 (1, 2)라고 하였고 행렬이 ABCD가 있다고 하였을 때에는
(1, 2)는 BC의 곱셈연산의 최소 값을 나타낸다.
그러면 일단 처음에 (0, 0) , (1, 1) , (2, 2) , (3, 3)은 당연히 0일 것이다 본인이랑 곱하는 것이니
그러면 이제 다음으로 부터는 (0, 1) , (1 , 2) , (2, 3) 이다 그러면 이것을 행렬로 나타내면 AB , BC , CD 를 나타낸다.
그러면 일단 이 위에 칸의 최소값은 그냥 저 두개의 행렬을 곱할때 곱셈연산의 수이다. 이런식으로 전처리를 하고
이후에 (0 , 2) , (1 , 3) 이렇게 가게 되는데 그러면 ABC , BCD 이렇게 되는 것이다
그러면 이전에 AB ,BC ,CD의 최소 곱셈 연산수를 구했으니 ABC 를 보자면 (AB)C 이렇게 하냐 아니면 A(BC) 이렇게 하는 것을 보는 것이다.
그러면 일단 AB 의 곱셈 연산수와 AB 행렬 * C를 곱할 때 곱셈연산수와 A * BC 연산수를 보면 된다.
그때 matrix 배열을 사용할 수 있다 matrix 배열은 각자의 n , n + 1 의 배열을 이용해서 n 행렬의 행과 열을 저장하는 배열이다.
그래서 이것을 이용해서 각자 맞게 연산을 하여서 dp[0][n - 1] 값이 결과가 되게 된다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(input.readLine());

        int[][] dp = new int[n][n];
        int[] matrix = new int[n + 1];
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            if(i == n - 1){
                matrix[i++] = Integer.parseInt(st.nextToken());
                matrix[i] = Integer.parseInt(st.nextToken());
            }else{
                matrix[i] = Integer.parseInt(st.nextToken());
            }
        }

        int height = n - 2;

        for(int i = 1; i < n; i++){
            int x = i;
            int y = 0;
            for(int j = 0; j <= height; j++){
                int left = dp[y][x - 1] + matrix[y] * matrix[x] * matrix[x + 1];
                int bottom = dp[y + 1][x] + matrix[y] * matrix[y + 1] * matrix[x + 1];
                if(left < bottom){
                    dp[y++][x++] = left;
                }else{
                    dp[y++][x++] = bottom;
                }
            }
            height--;
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println(dp[0][n - 1]);

    }
}
