import java.util.*;
import java.io.*;

// 12852 : 1로 만들기 2
/*
--전제조건
어떠한 수가 주어졌을 떄
그 수를 3 혹은 2로 나누거나 1을 빼서
1로 만드는 가장 최솟값을 출력하는데
그 횟수와
최소한의 연산으로 1을 만드는 과정에서 거쳐갔던 숫자들을 출력
--틀 설계
일단 당연히 dp이다.
예를 들어서 10이라는 숫자가 있다고 가정하자
10 -> 9 -> 3 -> 1 이다.
내가 만약에 dfs로 해결하려면 ?
일단 나눠지는 수 3 , 2 , 1 을 다 시도하면서 해볼 것이다. 10 에서 시작해서 각자의 score를 가지고
for(int i = 1; i <= 3; i++)로 dfs (score ..)로 해결 할 것이다.
여기서 아이디어를 얻어오면?
0 1 2 3 4 5 6 7 8 9 10
0 0 1 1 2 3 2 3 3 2 3
설계는 일단 1에서 부터 시작하는데 1은 바로 이니까 0으로 하고
이제 나오는 인덱스는 3가지의 경우를 비교할 것이다. 일단 2로 나눠지는 경우 3으로 나눠지는 경우
그리고 -1을 한 경우 이때 dp배열에 있는 수 중에서 가장 낮은 것을 골라서 거기에다가 + 1을 할 것이다.
그 다음에 dp 목표 숫자까지 가면 이제 10에서 부터 해당 경로를 구해야함
그러면 여기에서도 2 , 3 , -1 를 하면서 자신보다 1이 낮은 것을 고르는 것임
그러면서 순서대로 출력하면 된다. 만일 그 인덱스 값이 본인의 지금 값보다 1 낮다?
그러면 거기로 가면 된다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(input.readLine());
        int[] dp = new int[n + 1];
        for(int i = 2; i <= n; i++){
            /*
            여기서 일단 2 , 3 , -1을 연산을 시도하면서 해당 dp 인덱스가 더 작은지 계속 시도함
             */
            int min = Integer.MAX_VALUE;
            if(i % 2 == 0){
                min = Math.min(min , dp[i / 2]);
            }
            if(i % 3 == 0){
                min = Math.min(min , dp[i / 3]);
            }
            min = Math.min(min , dp[i - 1]);
            dp[i] = min + 1;
        }
        int index = n;
        System.out.println(dp[n]);
        while(true){
            System.out.print(index + " ");
            if(index == 1){
                break;
            }
            int value = dp[index];
            int tempIndex = 0;
            if(index % 2 == 0){
                if(value - 1 == dp[index / 2]) tempIndex = index / 2;
            }
            if(index % 3 == 0){
                if(value - 1 == dp[index / 3]) tempIndex = index / 3;
            }
            if(value - 1 == dp[index - 1]) tempIndex = index - 1;

            if(tempIndex == 0){
                tempIndex = 1;
            }

            index = tempIndex;
        }
    }
}
