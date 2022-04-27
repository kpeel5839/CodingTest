import java.util.*;
import java.io.*;

//10942 : 팰린드롬?
/*
--전제조건
팰린드롬 놀이를 해본다.
홍준이는 자연수 N개를 칠판에 적는다 , 그 다음 명우에게 질문을 총 M번 한다.
각 질문은 두 정수 S와 E로 나타낼 수 있으며 S번째 수부터 E번째 까지 수가 팰린드롬을 이루는 지 물어보며,
명우는 각 질문에 대해 팰린드롬이다 또는 아니다를 말해야한다.
팰린 드롬 수는 그냥 읽어도 거꾸로 읽어도 똑같은 수를 의미한다
그러니까 여기서 1 부터 3 까지 라고 하면 1 2 1 3 1 2 1 -> 1 2 1 이니까
여기서 그러면 1 2 1 은 그냥 읽어도 거꾸로 읽어도 1 2 1 이나 팰린드롬이고
2 5 인 경우는 2 1 3 1 이기 때문에 그냥 읽으면 2 1 3 1 이고 거꾸로 읽으면 1 3 1 2 이기 때문에
다르기에 팰린드롬이 아니라고 할 수가 있다.
--틀설계
질문의 개수가 100만개이다.
근데 수열의 크기는 1 ~ 2000 이다.
만일 질문의 크기가 1 2000 짜리가 100만개이다? 거기서 하나하나 보면서 하게 된다면?
절대로 성공할 수 없다.
그러면 [1][1] ~ [1][2000] 을 다 구하게 된다?
그러면 그것도 2000 * 2000 연산의 횟수는 ? 4000000만 번을 연산하게 된다.
이렇게 해서 연산을 한번 해보면 어떨까?
ans[n + 1][n + 1] 을 선언한 뒤에
ans[1][1] ~ ans[2000][2000] 이렇게 돌면서 팰린드롬 여부를 다 저장하는 것이다.
그 다음에 1 3 이렇게 들어오면 ans[1][3] 을 조회하면 끝난다.
그러면 이제 팰린드롬 연산을 해야 한다. 만일 1의 크기부터 2000의 크기 그러면 평균적으로 1000 번의 연산을
400만번 그렇게 되면 400/0000/0000 400억 번의 연산을 해야한다. 하나씩 조회하는 것은 말이 안된다.
ans[n + 1][n + 1] 을 하더라도 이 400만번의 연산까지는 괜찮지만 하나의 인덱스를 돌 때마다 i ~ j 까지의 배열을 다 순회하면 절대로 시간내에 해결 할 수 없을 것이다.
자 그러면 대충 ans[1][1] ~ ans[1][7] 을 어떻게 팰린드롬 연산을 할 수 있을까 , 효율적으로 그것을 한번 해보자.

ans[1][1] - 1 (o)
ans[1][2] - 1 2 (x)
ans[1][3] - 1 2 1(o)
ans[1][4] - 1 2 1 3 (x)
ans[1][5] - 1 2 1 3 1 (x)
ans[1][6] - 1 2 1 3 1 2 (x)
ans[1][7] - 1 2 1 3 1 2 1 (o)

ans[2][2] - 2 (o)
ans[2][3] - 2 1 (x)
ans[2][4] - 2 1 3 (x)
ans[2][5] - 2 1 3 1 (x)
ans[2][6] - 2 1 3 1 2 (o)
ans[2][7] - 2 1 3 1 2 1 (x)

ans[3][3] - 1 (o)
ans[3][4] - 1 3 (x)
ans[3][5] - 1 3 1 (o)
ans[3][6] - 1 3 1 2 (x)
ans[3][7] - 1 3 1 2 1 (x)

ans[4][4] - 3 (o)
ans[4][5] - 3 1 (x)
ans[4][6] - 3 1 2 (x)
ans[4][7] - 3 1 2 1 (x)

ans[5][5] - 1 (o)
ans[5][6] - 1 2 (x)
ans[5][7] - 1 2 1 (o)

ans[6][6] - 2 (o)
ans[6][7] - 2 1 (x)

ans[7][7] - 1 (o)

이것에서 알 수 있는 사실 , i == j 이면 팰린드롬이다. (당연하지만)
일단 1 번째 ans[1] 에서 가장 큰 팰린드롬을 찾는다, 그 다음에 이것은 짜피 팰린드롬이다.
이 값이 만일 홀 수라면? 가운데 값을 기준으로 1 - 7 이니 1 3 , 5 7 로 나누게 되면 이것도 팰린드롬일 수밖에 없는 것
만일 짝수라면 ? 그냥 반으로 나눠도 팰린드롬인 것이다.
가장 큰 지점에서 팰린드롬을 찾게 된다면 그 지점을 기준으로 ans를 채울 수 있을 것 같다.
일단 모두 찾을 수는 없을 수도 있지만 한번 재귀적으로 해결해보자.

1 2 1 3 3 1 2 이 경우에서도 한번 고려해보자.

ans[1][1] - 1 (o)
ans[1][2] - 1 2 (x)
ans[1][3] - 1 2 1 (o)
ans[1][4] - 1 2 1 3 (x)
ans[1][5] - 1 2 1 3 3 (x)
ans[1][6] - 1 2 1 3 3 1 (x)
ans[1][7] - 1 2 1 3 3 1 2 (x)

ans[2][2] - 2 (o)
ans[2][3] - 2 1 (x)
ans[2][4] - 2 1 3 (x)
ans[2][5] - 2 1 3 3 (x)
ans[2][6] - 2 1 3 3 1 (x)
ans[2][7] - 2 1 3 3 1 2 (o)

ans[3][3] - 1 (o)
ans[3][4] - 1 3 (x)
ans[3][5] - 1 3 3 (x)
ans[3][6] - 1 3 3 1 (x)
ans[3][7] - 1 3 3 1 2 (x)

ans[4][4] - 3 (o)
ans[4][5] - 3 3 (o)
ans[4][6] - 3 3 1 (x)
ans[4][7] - 3 3 1 2 (x)

ans[5][5] - 3 (o)
ans[5][6] - 3 1 (x)
ans[5][7] - 3 1 2 (x)

ans[6][6] - 1 (o)
ans[6][7] - 1 2 (x)

ans[7][7] - 2 (o)

재귀로 해결 할 수 있는 문제가 아니였음
예를 들어서 1 로만 이루어져 있는 경우에서 , 가장 큰 팰린드롬을 재귀 적으로 해결한다고 하더라도
그냥 모두다가 팰린드롬인데 재귀적으로 할 필요가 없다라는 것이다.
재귀적으로도 불가능하고 (물론 재귀적으로 가능하겠지만 내 방법으로는 안된다.)

일단 팰린드롬일 경우를 그냥 찾는 경우를 고려해보자.
1 2 1 3 1 2 1
이거를 팰린드롬인 지 어떻게 알 수 있을 까? queue에다가 넣고 , 거꾸로 읽으면서 빼는 것이다.
큐에 이런식으로 쌓이게 될 것이다. <- queue 출구 1 2 1 3 1 2 1  <- 들어오는 입구
FIFO 의 형식이다 , first in first out , 그러니까 queue에서 순서대로 나오면 그대로인 것이다.
그러면 이제 큐를 하나하나 빼면서 거꾸로 읽는 것이다 , 그래서 이 순서가 동일하다? 그러면 팰린드롬이다.

근데 이제 이것을 빠르게 해야한다. 지금은 생각이 안난다.

-- 해맸던 점
결국 답을 봤고
문제를 푸는 과정에서 해맸던 점은 , numberList를 인덱스 1부터 안받아서 살짝 헷갈렸음
 */
public class Main {
    public static int[] numberList;
    public static int[][] dp;
    public static void dpCal(int start, int end){
        /*
        그냥 start , end 의 차이가 1이라면 , 그냥 두개가 같은지 다른지만 보고 start , end에 넣어주고
        그게 아니라면 dp[start + 1][end - 1] == 1 && dp[start] == dp[end] 이렇게 해주면 됨
         */
//        System.out.println("start : " + start + " end : " + end);
        if(end - start == 1){
            if(numberList[start] == numberList[end]) dp[start][end] = 1;
        }else{
            if(numberList[start] == numberList[end] && dp[start + 1][end - 1] == 1) dp[start][end] = 1;
//            System.out.println("dp[start + 1][end - 1] : " + dp[start + 1][end - 1]);
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        /*
        일단 입력을 받고 해당 배열을 받는다.
        그리고 팰린드롬 dp 연산을 시작한다.
        일단 dp[i][j] 로 표현하고 이것의 의미는 i ~ j 까지가 팰린드롬이냐는 것이다.
        dp[i][j] , 경우에서 i == j 인 경우는 무조건 팰린드롬이다 , 왜냐하면 한자리니까
        그리고 2자리로 가게되면 두개가 서로 같아야한다.
        즉 2자리 이상부터는 시작자리 == 끝자리 , 그리고 dp[시작자리 + 1][끝자리 - 1] 가 팰린드롬이여야 하는 것이다.
        하지만 2자리는 가운데 숫자가 없기 때문에 2자리가 다 같아야하는 것이다.
        3 자리 이상부터는 처음과 끝이 같다면 ? 가운데에 있는 dp[시작자리 + 1][끝자리 - 1] 가 팰린드롬이면 되는 것이다.
        시작자리 == 끝자리여야 하는 이유는 만일 시작자리만 제외하고 팰린드롬을 확인하면 시작자리 , dp[시작자리 + 1][끝자리] 로 하게되면
        dp[시작자리 + 1][끝자리]의 끝자리가 같지 않으면 팰린드롬이 아니기 때문이다.
        그래서 길이가 1부터 n까지 진행한다음에
        전처리를 완료하면 입력을 받으면서 바로바로 출력하면 된다.
        dp[i][j] = 1 이면 팰린드롬 , 0 이면 아닌 걸로 하고서 전처리 과정을 하면된다.
         */

        int n = Integer.parseInt(input.readLine());

        numberList = new int[n + 1];
        dp = new int[n + 1][n + 1];

        st = new StringTokenizer(input.readLine());

        for(int i = 1; i <= n; i++){
            numberList[i] = Integer.parseInt(st.nextToken());
        }

        for(int i = 1; i <= n; i++){
            dp[i][i] = 1;
        }

        for(int i = 1; i < n; i++){
            for(int j = 1; j + i <= n; j++){
                dpCal(j , j + i);
            }
        }

//        for(int i = 1; i <= n; i++) {
//            System.out.println(Arrays.toString(dp[i]));
//        }

        int m = Integer.parseInt(input.readLine());
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(input.readLine());
            output.write(dp[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] + " ");
        }

        output.flush();
        output.close();
    }
}
