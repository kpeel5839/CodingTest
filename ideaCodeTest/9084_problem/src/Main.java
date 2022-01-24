import java.util.*;
import java.io.*;

// 9084 : 동전
/*
-- 전제조건
우리나라 동전에는 1원 , 5원 , 10원, 50원, 100원, 500원이 있다.
이 동전들로는 정수의 금액을 만들 수 있고 그 방법은 여러개가 있다
동전의 종류가 주어질 때에 주어진 금액을 만드는 모든 방법을 세는 프로그램을 작성하시오

입력으로는 테스트 케이스의 개수가 주어지고 , 테스트 케이스의 첫 번째 줄에는 동전의 가지 수 N이 주어지고
두번 째 줄에는 N가지 동전의 각 금액이 오름차순으로 정렬되어 주어진다.
각 금액은 공백으로 구분되고 세번째 줄에는 N가지 동전으로 만들어야 할 금액이 주어진다.
-- 틀 설계
테스트 케이스의 개수를 받고 for문에 들어가고
n이 들어오면 inputNumber[n + 1]을 선언한다.
그리고 그 다음부터 들어오는 입력들을 배열에 저장한다.
그러고서 M이 들어오면 dp[m + 1]로 만들고 inputNumber를 순회하면서
dp[inputNumber]++ 물론 inputNumber > m 이 아닌 상황에서 그 다음에 inputNumber + 1 부터 m 까지 진행하면서 dp[i] + dp[i - inputNumber]를 해준다.
이 이유는 이전에 풀었던 동전 1문제와 같이 1 2가 들어온다고 가정하였을 때 1로만 만들 수 있는 경우(순서가 상관이 없다고 할때)
그 다음에 2로만 숫자를 이룰 수 있는 dp[inputNumber]++를 해주고 다음 부터 dp[i] + dp[i - inputNumber] 를 해주면서
i - inputNumber에 존재하는 경우에다가 2를 더한 경우를 본인의 경우의 수에다가 추가하는 원리이다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int t = Integer.parseInt(input.readLine());

        for(int i = 0; i < t; i++){
            int n = Integer.parseInt(input.readLine());
            int[] inputNumber = new int[n + 1];
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                inputNumber[j] = Integer.parseInt(st.nextToken());
            }
            int m = Integer.parseInt(input.readLine());
            int[] dp = new int[m + 1];
            for(int j = 0; j < n; j++){
                if(m >= inputNumber[j]) {
                    dp[inputNumber[j]]++;
                    for (int c = inputNumber[j] + 1; c <= m; c++){
                        dp[c] = dp[c] + dp[c - inputNumber[j]];
                    }
                }
            }
            output.write(dp[m] + "\n");
        }
        output.flush();
        output.close();
    }
}
